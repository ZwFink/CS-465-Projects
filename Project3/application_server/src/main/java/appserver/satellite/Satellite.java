package appserver.satellite;

import appserver.job.Job;
import appserver.comm.ConnectivityInfo;
import appserver.job.UnknownToolException;
import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import static appserver.comm.MessageTypes.REGISTER_SATELLITE;
import appserver.job.Tool;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertyHandler;

/**
 * Class [Satellite] Instances of this class represent computing nodes that execute jobs by
 * calling the callback method of tool a implementation, loading the tool's code dynamically over a network
 * or locally from the cache, if a tool got executed before.
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class Satellite extends Thread {

    private ConnectivityInfo satelliteInfo = new ConnectivityInfo();
    private ConnectivityInfo serverInfo = new ConnectivityInfo();
    private HTTPClassLoader classLoader = null;
    private Hashtable toolsCache = null;

    public Satellite(String satellitePropertiesFile, String classLoaderPropertiesFile, String serverPropertiesFile) {

        // read this satellite's properties and populate satelliteInfo object,
        // which later on will be sent to the server
        // ...
        PropertyHandler satelliteProperties = null;
        PropertyHandler classLoaderProperties = null;
        PropertyHandler serverProperties = null;

        try
        {
            satelliteProperties = new PropertyHandler( satellitePropertiesFile );
            classLoaderProperties = new PropertyHandler( classLoaderPropertiesFile );
            serverProperties = new PropertyHandler( serverPropertiesFile );
        }
        catch( FileNotFoundException e )
        {
            System.out.println( "ERROR: Configuration file not found." );
            e.printStackTrace();
            System.exit( 1 );
        }
        catch( IOException e )
        {
            System.out.println( "ERROR: An unknown IO exception occurred." );
            e.printStackTrace();
            System.exit( 1 );
        }

        
        satelliteInfo.setName( satelliteProperties.getProperty( "NAME" ) );
        satelliteInfo.setPort( Integer.parseInt( satelliteProperties.getProperty( "PORT" ) ) );
        satelliteInfo.setHost( "127.0.0.1" );

        // read properties of the application server and populate serverInfo object
        // other than satellites, the as doesn't have a human-readable name, so leave it out
        // ...
        serverInfo.setHost( serverProperties.getProperty( "HOST" ) );
        serverInfo.setPort( Integer.parseInt( serverProperties.getProperty( "PORT" ) ) );

        
        // read properties of the code server and create class loader
        // -------------------
        // ...
        int classLoaderPort = Integer.parseInt( classLoaderProperties.getProperty( "PORT" ) );
        classLoader = new HTTPClassLoader( classLoaderProperties.getProperty( "HOST" ),
                                           classLoaderPort
        );

        
        // create tools cache
        // -------------------
        // ...
        toolsCache = new Hashtable<String,Tool>();
        
    }

    @Override
    public void run() {

        try
        {
            // register this satellite with the SatelliteManager on the server
            // ---------------------------------------------------------------
            // ...
            System.out.println("Server Class Not Yet Implemented");
            
            // create server socket
            // ---------------------------------------------------------------
            // ...
            ServerSocket serverSocket = new ServerSocket(satelliteInfo.getPort());
            
            // start taking job requests in a server loop
            // ---------------------------------------------------------------
            // ...
            Socket client = null;
            while (true) 
            {
                System.out.println("[Satellite.run] Waiting for connections on Port #" + satelliteInfo.getPort());
                client = serverSocket.accept();
                System.out.println("[Satellite.run] A connection to a client is established!");
                SatelliteThread jobHandler = new SatelliteThread(client, this);
                jobHandler.start();
            }
        } catch (IOException ex)
        {
            Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // inner helper class that is instanciated in above server loop and processes single job requests
    private class SatelliteThread extends Thread {

        Satellite satellite = null;
        Socket jobRequest = null;
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        Message message = null;

        SatelliteThread(Socket jobRequest, Satellite satellite) {
            this.jobRequest = jobRequest;
            this.satellite = satellite;
        }

        @Override
        public void run() {
            
            try
            {
                // setting up object streams
                // ...
                readFromNet = new ObjectInputStream(jobRequest.getInputStream());
                writeToNet = new ObjectOutputStream(jobRequest.getOutputStream());
            } catch (IOException ex)
            {
                Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // reading message
            // ...
            try
            {
                message = (Message) readFromNet.readObject();
            } catch (IOException ex)
            {
                Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex)
            {
                Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(message == null)
            {
                System.err.println("[Stellite.run] Error occurred, message was NULL");
                return;
            }
            
            switch (message.getType()) {
                case JOB_REQUEST:
                    // processing job request
                    // ...
                    Object content = message.getContent();
                    //Get the operation symbol to know what to get
                    String contStr = content.toString();
                    String[] contentList = contStr.split(" ");
                    String opSymbol = contentList[1];
                    
                    ///Get Tool for the job using getToolObject()
                    Tool operator = null;
                    try
                    {
                        operator = getToolObject(opSymbol);
                    } catch (UnknownToolException ex)
                    {
                        Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex)
                    {
                        Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex)
                    {
                        Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex)
                    {
                        Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(operator == null)
                    {
                        System.err.println("[Stellite.run & Stellite.tool] Error occurred, Tool was NULL");
                        return;
                    }
                
                    //Get the result using the tool
                    Object result = operator.go(content);
                    try
                    {
                        //return the result
                        writeToNet.writeObject(result);
                        writeToNet.flush();
                    } catch (IOException ex)
                    {
                        Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    break;



                default:
                    System.err.println("[SatelliteThread.run] Warning: Message type not implemented");
            }
        }
    }

    /**
     * Aux method to get a tool object, given the fully qualified class string
     * If the tool has been used before, it is returned immediately out of the cache,
     * otherwise it is loaded dynamically
     */
    public Tool getToolObject(String toolClassString) throws UnknownToolException, ClassNotFoundException, InstantiationException, IllegalAccessException 
    {

        Tool toolObject;
        HTTPClassLoader toolLoader = new HTTPClassLoader(); //TODO pass port and host
        
        toolObject = (Tool) toolsCache.get(toolClassString);
        
        if (toolObject == null)
        {
            System.out.println("\nOperation: " + toolClassString);
            
            // Get the tool object
            Class toolClass = toolLoader.loadClass(toolClassString);
            toolObject = (Tool) toolClass.newInstance();
            toolsCache.put(toolClassString, toolObject);
        }
        // Tool has been used already
        else
        {
            System.out.println("Operation: " + toolClassString + " already in cache");
        }
        
        return toolObject;
    }

    public static void main(String[] args) {
        // start the satellite
        Satellite satellite = new Satellite(args[0], args[1], args[2]);
        satellite.run();
    }
}
