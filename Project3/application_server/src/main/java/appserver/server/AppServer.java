/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.server;

import appserver.comm.ConnectivityInfo;
import appserver.comm.Message;
import appserver.comm.MessageTypes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertyHandler;

/**
 * Details from BBLearn:
    * Remember that the application server is just passing through a client's
    * request to the satellite server that was selected by the application server's
    * load balancing manager. That means that how a job is processed is really
    * transparent to the client. That said, all it takes to set up a client is to
    * change the properties file to be that of the application server, instead of
    * the satellite server.
    *
    * The value that the application server adds, compared to a satellite server,
    * is distributing jobs across a number of registered satellite servers, i.e.
    * load balancing. In order to achieve this, the application server is supported
    * by two managers:

       * A satellite manager, which keeps track of all the satellites that have
       * registered. For that to work, you need to: Implement functionality in class
       * Satellite that allows a satellite server to register itself with the
       * application server, providing its connectivity information (i.e. It's name,
       * IP and port number). Implement functionality in the application server that
       * responds to satellite registration requests and forwards the information
       * received to its satellite manager. Please note that this is the matching part
       * to the functionality implemented in the satellite discussed in the prior
       * bullet point. 

       * A load balancing manager. This manager can potentially
       * implement any load balancing policy. It responds to requests from the
       * application server asking for the next satellite to do a job. For simplicity,

    * I want you to implement a "round-robin" policy. Whenever a satellite server
    * registers with the client, the load manager is also informed, so that it
    * knows about the existence of the new satellite server.
    * 
 * @author caleb johnson
 */
public class AppServer extends Thread  
{
    private ConnectivityInfo serverInfo = new ConnectivityInfo();
    private SatelliteManager satManager;
    private int nextSat = 0;
    private String log = "";
    
    public AppServer(String appPropertiesFile)
    {
        PropertyHandler serverProperties = null;
        try
        {
            serverProperties = new PropertyHandler( appPropertiesFile );
        }
        catch( FileNotFoundException e )
        {
            System.out.println( "[AppServer.init] ERROR: Configuration file not found." );
            e.printStackTrace();
            System.exit( 1 );
        }
        catch( IOException e )
        {
            System.out.println( "[AppServer.init] ERROR: An unknown IO exception occurred." );
            e.printStackTrace();
            System.exit( 1 );
        }
        //Load in properties
        serverInfo.setHost( serverProperties.getProperty( "HOST" ) );
        serverInfo.setPort( Integer.parseInt( serverProperties.getProperty( "PORT" ) ) );
        satManager = new SatelliteManager();
    }
    
    @Override
    public void run() 
    {
         ServerSocket appSocket = null;
        try
        {
            appSocket = new ServerSocket(serverInfo.getPort());
            // Wait for clients or sat servers for infinity
            while(true)
            {
                System.out.println("[AppServer.run] Waiting for connections on Port #" + serverInfo.getPort());
                Socket newCon = appSocket.accept();
                System.out.println("[AppServer.run] A connection was established!");
                //Get the given message to tell if its client or satellite
                ObjectOutputStream writeToNet = new ObjectOutputStream(newCon.getOutputStream());
                ObjectInputStream readFromNet = new ObjectInputStream(newCon.getInputStream());
                Message message = null;
                
                // reading message
                // ...
                try
                {
                    message = (Message) readFromNet.readObject();
                } catch (IOException | ClassNotFoundException ex)
                {
                    System.err.println("[AppServer.Thread.run] Error occurred: " + ex.toString() );
                            return;
                }
                if(message == null)
                {
                    System.err.println("[AppServer.run] Error occurred, message was NULL");
                    return;
                }
                switch (message.getType()) 
                {
                    // Check if its a client
                    case MessageTypes.JOB_REQUEST:
                        //Hand off the client to the current "next satellite"
                        ConnectivityInfo sat = this.satManager.getSatellites().get( nextSat );
                        WorkerThread jobHandler = new WorkerThread(writeToNet, sat, message);
                        jobHandler.start();
                        //increment "next sat" If at the end of server list reset counter to 0
                        nextSat++;
                        nextSat %= this.satManager.getSatellites().size();
;
                        break;
                    // Check if its a server
                    case MessageTypes.REGISTER_SATELLITE:
                        //Add it to the list
                        if(message.getContent() != null)
                        {
                            ConnectivityInfo newSat = (ConnectivityInfo) message.getContent();
                            // NOTE: The load balancer is also aware of this 
                            // new satellite because they share a pointer to satmanager
                            this.satManager.addSatellite( newSat );
                        }
                        else
                        {
                            System.err.println("[AppServer.run] Message content from satellite was null");
                            writeToNet.writeBoolean(false);
                            writeToNet.flush();
                            newCon.close();
                            return;
                        }
                        writeToNet.writeBoolean(true);
                        writeToNet.flush();
                        newCon.close();
                        break;
                    default:
                        System.err.println("[AppServer.run] Unknown Message Type");
                        newCon.close();
                        break;
                }
                System.out.print(log + "\n");
                //Go back to waiting at top of loop
            }
        } catch (IOException ex)
        {
            System.err.println("[AppServer.run] Failed to setup server socket: " + ex.toString() );
        }
    }

    /**
     * Inner Class [WorkerThread] This thread processes a client (web browser)
     * request. In the meantime the web server can accept other clients.
     */
    class WorkerThread extends Thread 
    {
        Socket client = null;
        Socket servSock = null;
        ConnectivityInfo serConInfo;
        ObjectOutputStream writeToClient = null;
        Message message = null;
        Object resultReturn = null;
        ObjectInputStream readFromSat = null;
        ObjectOutputStream writeToSat = null;

        /**
         * The Constructor
         */
        WorkerThread(ObjectOutputStream writeToClient, ConnectivityInfo serConInfo, Message message) 
        {
            this.writeToClient = writeToClient;
            this.serConInfo = serConInfo;
            this.message = message;
        }
        
        @Override
        public void run() 
        {
            //Setup server connection
            tPrint("[AppServer.WorkerThread.run] Connection to satellite at port: " + serConInfo.getPort());
            try
            {
                servSock = new Socket(serConInfo.getHost(), serConInfo.getPort());
            } catch (IOException ex)
            {
                tPrint("[AppServer.WorkerThread.run] Error occurred, creation of socket to satellite failed");
                return;
            }
            if(servSock == null)
            {
                tPrint("[AppServer.WorkerThread.run] Error, sat server socket was null" );
                return;
            }
            if(servSock.isConnected())
            {
                tPrint("[AppServer.WorkerThread.run] Server Connected!" );
            }
            else
            {
                tPrint("[AppServer.WorkerThread.run] ERROR: Satellite Connection failed" );
            }
            
            tPrint("[AppServer.WorkerThread.run] Making streams for port: " + serConInfo.getPort());
            try
            {
                //setting up object streams
                //Satillite
                writeToSat = new ObjectOutputStream(servSock.getOutputStream());
                tPrint("[AppServer.WorkerThread.run] Write made for port: " + serConInfo.getPort());
                readFromSat = new ObjectInputStream(servSock.getInputStream());
                tPrint("[AppServer.WorkerThread.run] Read made for port: " + serConInfo.getPort());
            } 
            catch (IOException ex)
            {
                tPrint("[AppServer.WorkerThread.run] Error occurred: " + ex.toString() );
                return;
            }
            catch (Exception ex)
            {
                tPrint("[AppServer.WorkerThread.run] Unknown Error occurred: " + ex.toString() );
                return;
            }
            
            
            //Write the message to the satellite
            tPrint("[AppServer.WorkerThread.run] sending client request to port: " + serConInfo.getPort());
            try
            {
                writeToSat.writeObject(message);
                writeToSat.flush();
            } catch (IOException ex)
            {
                tPrint("[AppServer.WorkerThread.run] Error occurred, write to satellite failed");
                return;
            }
            catch (Exception ex)
            {
                tPrint("[AppServer.WorkerThread.run] Unknown Error occurred: " + ex.toString() );
                return;
            }
            
            //Get back the reply and push it to the client
            //Take clients request and push it to the assigned server
            // reading message
            tPrint("[AppServer.WorkerThread.run] getting return message from port: " + serConInfo.getPort());
            try
            {
                resultReturn = readFromSat.readObject();
            } catch (IOException | ClassNotFoundException ex)
            {
                tPrint("[AppServer.WorkerThread.run] Error occurred: " + ex.toString() );
                        return;
            }
            catch (Exception ex)
            {
                tPrint("[AppServer.WorkerThread.run] Unknown Error occurred: " + ex.toString() );
                return;
            }
            
            if(resultReturn == null)
            {
                tPrint("[AppServer.WorkerThread.run] Error occurred, message was NULL");
                return;
            }
            //Write the return back to the client
            tPrint("[AppServer.WorkerThread.run] Sending result to client");
            try
            {
                writeToClient.writeObject(resultReturn);
                writeToClient.flush();
                
            } catch (IOException ex)
            {
                tPrint("[AppServer.WorkerThread.run] Error occurred, write to client failed");
                return;
            }
            
            //END OF RUN
            tPrint("[AppServer.WorkerThread.run] END OF JOB");
            try
            {
                writeToClient.close();
                readFromSat.close();
                writeToSat.close();
                servSock.close();
            } catch (IOException ex)
            {
                Logger.getLogger(AppServer.class.getName()).log(Level.SEVERE, null, ex);
                tPrint("[AppServer.WorkerThread.run] Error occurred, failed to close sockets");
            }
        }
    }
    
    //Method for getting a satillite from the list
    public void tPrint(String out)
    {
        log = log + "\n\n" + out;
    }
    
    public static void main(String[] args) {
        // start the satellite
        String appPropStr = "";
        //To avoid improper argument erros and allow ease of use
        if(args.length != 1)
        {
            //Forces use of defualt 3 if aguments given is none/not the right amount
            System.err.print("Properties files not given using testing defaults\n");
            appPropStr = "../../config/Server.properties";
        }
        else
        {
            //Grabs argument values and assigns them to appropriatly named strings
            appPropStr = args[0];
        }
        AppServer appServer = new AppServer(appPropStr);
        appServer.start();
    }
}
