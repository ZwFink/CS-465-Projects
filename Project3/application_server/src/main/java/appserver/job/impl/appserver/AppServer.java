/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.job.impl.appserver;

import appserver.comm.Message;
import appserver.satellite.Satellite;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import web.GenericServer;
import web.SimpleWebServer;

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
    ArrayList<Socket> satServers;
    int nextSat = 0;
    
    public AppServer(String appPropertiesFile)
    {
        //Load in properties
    }
    
    @Override
    public void run() 
    {
        // Wait for clients or sat servers for infinity
        // Check if its a server
            //Add it to the list

        // Check if its a client
            //Hand off the client to the current "next sat"
            //increment "next sat" If at the end of server list reset counter to 0
        
        //Go back to waiting at top of loop
    }

    /**
     * Inner Class [WorkerThread] This thread processes a client (web browser)
     * request. In the meantime the web server can accept other clients.
     */
    class WorkerThread extends Thread 
    {
        Socket client = null;
        int serverID = -1;
        ObjectInputStream readFromClient = null;
        ObjectOutputStream writeToClient = null;
        Message message = null;

        /**
         * The Constructor
         */
        WorkerThread(Socket client, int serverID) 
        {
            this.client = client;
            this.serverID = serverID;
        }
        
        @Override
        public void run() 
        {
            //Setup server connection
            Socket server = getServer(serverID);
            ObjectInputStream readFromSat = null;
            ObjectOutputStream writeToSat = null;
            try
            {
                // setting up object streams
                //Satillite
                readFromSat = new ObjectInputStream(server.getInputStream());
                writeToSat = new ObjectOutputStream(server.getOutputStream());
                //Client
                readFromClient = new ObjectInputStream(client.getInputStream());
                writeToClient = new ObjectOutputStream(client.getOutputStream());;
            } catch (IOException ex)
            {
                System.err.println("[AppServer.WorkerThread.run] Error occurred: " + ex.toString() );
                return;
            }
            //Take clients request and push it to the assigned server
                //TODO
            //Get back the reply and push it to the client
                //TODO
        }
    }
    
    //Method for addeing a satillite to the list
    public void addServer(Socket server)
    {
        this.satServers.add(server);
    }
    
    //Method for getting a satillite from the list
    public Socket getServer(int id)
    {
        return satServers.get(id);
    }
    
    public static void main(String[] args) {
        // start the satellite
        String appPropStr = "";
        //To avoid improper argument erros and allow ease of use
        if(args.length != 1)
        {
            //Forces use of defualt 3 if aguments given is none/not the right amount
            System.err.print("Properties files not given using testing defaults\n");
            appPropStr = "../../config/AppServer.properties";
        }
        else
        {
            //Grabs argument values and assigns them to appropriatly named strings
            appPropStr = args[0];
        }
        AppServer appServer = new AppServer(appPropStr);
        appServer.run();
    }
}
