package com.cs465.distributed_chat;

// A Java program for a Client 
import java.net.*; 
import java.io.*; 
import java.util.LinkedList;

public class Node 
{ 
	// initialize socket and input output streams 
	private Socket socket = null; 
        private ServerSocket serverSocket = null;
	private DataInputStream inputMessage = null; 
	private DataOutputStream outputMessage = null; 
        private Thread sender = null;
        private Thread receiver = null;
        private LinkedList nodeInfoList = null;
                     
/**
 * Create a node that will become the user.
 * 
 * @param address users device address / must be unique
 * @param port number to connect to
 * @param name logical name of node
 * @throws IOException
 * @throws InterruptedException 
 */
public Node(InetAddress address, int port, String name) throws IOException, InterruptedException 
{
    //Use given info to add self to the list of ips and ports
    NodeInfo selfNode = new NodeInfo(address, port, name);
    nodeInfoList.add(selfNode);
    
   // while(true)
    //{
        //System.out.println("Accepting Clients");
        
        receiver = new Thread()
        {
            @Override
            
            public void run()
            {
                try
                {
                    // begin server given port that is passed in.
                    serverSocket = new ServerSocket(port);
                    
                    socket = new Socket(address, port);
                    System.out.println("User Connected- " + socket + " -is the users socket information");
                    
                    inputMessage = new DataInputStream(socket.getInputStream());
                    outputMessage = new DataOutputStream(socket.getOutputStream());
                    
                    //Check the recieved message
                        //IF the message is a Join message
                            //Send the ip/port list of this node back
                        //IF the message is a Join NOTIFY message
                            //Append given ip/port of the message to list of this node
                        //IF the message is a normal message
                            //Display the message to the user
                        //IF the message is a Leave message
                            //Remove the ip/port of the leaver from this nodes list
                }
                
                catch(UnknownHostException i)
                {
                    System.out.println(i);
                }
                
                catch(IOException ioe)
                {
                    System.out.println(ioe);
                }
            }
        };
        //start the receiver thread.
        receiver.start();
        
        //Sender thread
        sender = new Thread()
        {
            @Override
            
            public void run()
            {
                //Wait until the user trys to send a message
                
                //Check the message to see if is a join or leave message
                    //If join message
                        //parse out the ip and port that the user is trying to join
                        //create a socket to connect
                        //add an input and output stream
                        //send the join message through the input stream
                        //wait and read a reply through the output stream
                        // set the current node list of ip and ports 
                            //of the node list that came back from the output 
                
                        //go back to waitinng for a user message
                
                    //If leave message
                        //Create a loop through the node list of ip and ports
                            //create a new socket on port at index I
                            //send the leave message through the socket
                            //close down the socket
                        //Close down this node
                
                    //If normal message
                        //Create a loop through the node list of ip and ports
                            //create a new socket on port at index I
                            //send the message through the socket
                            //close down the socket
                        //go back to waitinng for a user message
            }
        };

        //Begin sender Thread      
        sender.start();
        
        // End of Initiallization for Node0 / Starting node.
        
}


/**
 * Method to handle new incoming socket connections and set
 * @param socket
 * @throws IOException
 * @throws InterruptedException 
 */
 private static void handleSocket(Socket socket) throws IOException, InterruptedException
{
    ServerSocket serverSocket = new ServerSocket();
    InputStream inputMessage = socket.getInputStream();
    OutputStream output = socket.getOutputStream();

    BufferedReader read = new BufferedReader(new InputStreamReader(inputMessage));
    String entry;
    output.write(("Connected To Server \n").getBytes());

    while( (entry = read.readLine()) != null)
    {
        String message = "user typed: " + entry + "\n";

        output.write(message.getBytes());

    } 
    socket.close();
}
       
}


    
/*
    // establish initial socket connection 
    // this socket connection will become Node0
    try
    { 
        // variable for getting devices IP address
        InetAddress currentDevice = InetAddress.getLocalHost();
        
        // Creates a stream socket and connects it to
        // the specified port number at the specified IP address
        socket = new Socket(currentDevice, port);
        
        //Create server socket connection 
        serverSocket = new ServerSocket(port);
 
        
//Needs to be in the reciever thread        
        while(true)
        {
            System.out.println("Accepting Clients");
            socket = serverSocket.accept();
            
//////////////////////////////////////////////////////////////
        
        sender = new Thread()          
        {
            @Override
            public void run()
            {
                try
                {
                    handleSocket(socket);
                }
                
                catch (IOException | InterruptedException e)
                {   
                    e.printStackTrace();
                }
            }
        };
        sender.start();
        }
         
	  } 
                // catch Statements for Error Handleing
		catch(UnknownHostException unknownMessage) 
		{ 
			System.out.println(unknownMessage); 
		} 
		catch(IOException ioeMessage) 
		{ 
			System.out.println(ioeMessage); 
		} 

		// Close all connections
		try
		{ 
			inputMessage.close(); 
			outputMessage.close(); 
			socket.close(); 
		} 
                // Catch closing connection exceptions
		catch(IOException ioeMessage) 
		{ 
			System.out.println(ioeMessage); 
		} 
	} 

*/

