package appserver.satellite;

import appserver.job.Job;
import appserver.comm.ConnectivityInfo;
import appserver.job.UnknownToolException;
import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import static appserver.comm.MessageTypes.UNREGISTER_SATELLITE;
import appserver.job.Tool;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
            System.out.println( "[Satellite.init] ERROR: Configuration file not found." );
            e.printStackTrace();
            System.exit( 1 );
        }
        catch( IOException e )
        {
            System.out.println( "[Satellite.init] ERROR: An unknown IO exception occurred." );
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
            Message message = new Message( UNREGISTER_SATELLITE, this );
            Socket appServer = new Socket(serverInfo.getHost(), serverInfo.getPort());
            ObjectOutputStream writeToServ = new ObjectOutputStream(appServer.getOutputStream());
            writeToServ.writeObject(message);
            
            // create server socket
            // ---------------------------------------------------------------
            // ...
            ServerSocket serverSocket = new ServerSocket(satelliteInfo.getPort());
            
            // start taking job requests in a server loop
            // ---------------------------------------------------------------
            // ...
            while (true) 
            {
                System.out.println("[Satellite.run] Waiting for connections on Port #" + satelliteInfo.getPort());
                Socket client = serverSocket.accept();
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
                System.err.println("[Satellite.Thread.run] Error occurred: " + ex.toString() );
                return;
            }
            
            // reading message
            // ...
            try
            {
                message = (Message) readFromNet.readObject();
            } catch (IOException | ClassNotFoundException ex)
            {
                System.err.println("[Satellite.Thread.run] Error occurred: " + ex.toString() );
                        return;
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
                    Job job = (Job) content;
                    String opSymbol = job.getToolName();
                    
                    ///Get Tool for the job using getToolObject()
                    Tool operator = null;
                    try
                    {
                        operator = getToolObject(opSymbol);
                    } catch (UnknownToolException | ClassNotFoundException | InstantiationException | IllegalAccessException ex)
                    {
                        System.err.println("[Satellite.Thread.run] Error occurred: " + ex.toString() );
                        return;
                    }
                    if(operator == null)
                    {
                        System.err.println("[Satellite.Thread.run] Error occurred, Tool was NULL");
                        return;
                    }
                
                    //Get the result using the tool
                    Object parameters = job.getParameters();
                    Object result = operator.go(parameters);
                    try
                    {
                        //return the result
                        writeToNet.writeObject(result);
                        writeToNet.flush();
                        //Close the job
                        jobRequest.close();
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
        //Try to get the Tool from the cache
        Tool toolObject;
        toolObject = (Tool) toolsCache.get(toolClassString);
        
        //The cache will return null if there is no tool to be found
        if (toolObject == null)
        {
            System.out.println("\nOperation: " + toolClassString);
            // Try to get the tool object
            try
            {
                Class toolClass = classLoader.loadClass(toolClassString);
                toolObject = (Tool) toolClass.newInstance();
            }
            catch(ClassNotFoundException e)
            {
                System.err.println("[SatelliteThread.getToolObject] Error:" 
                                    + toolClassString 
                                    + "Not Found");
                throw new ClassNotFoundException();
            }
            //Place the new found tool in the cache
            toolsCache.put(toolClassString, toolObject);
        }
        // Tool has been used already, thus inform the system
        else
        {
            System.out.println("Operation: " + toolClassString + " already in cache");
        }
        
        return toolObject;
    }

    public static void main(String[] args) {
        // start the satellite
        String satellitePropStr = "";
        String classLoaderPropStr = "";
        String serverPropStr = "";
        //To avoid improper argument erros and allow ease of use
        if(args.length != 3)
        {
            //Forces use of defualt 3 if aguments given is none/not the right amount
            System.err.print("3 Properties files not given using testing defaults\n");
            satellitePropStr = "../../config/Satellite.Earth.properties";
            classLoaderPropStr = "../../config/WebServer.properties";
            serverPropStr = "../../config/Server.properties";
        }
        else
        {
            //Grabs argument values and assigns them to appropriatly named strings
            satellitePropStr = args[0];
            classLoaderPropStr = args[1];
            serverPropStr = args[2];
        }
        Satellite satellite = new Satellite(satellitePropStr, classLoaderPropStr, serverPropStr);
        satellite.run();
    }
}
