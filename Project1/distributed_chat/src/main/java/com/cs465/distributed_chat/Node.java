package com.cs465.distributed_chat;

// A Java program for a Client 
import com.cs465.distributed_chat.messages.*;
import java.net.*; 
import java.io.*; 
import java.util.LinkedList;

public class Node 
{ 
	// initialize socket and input output streams 
	private DataInputStream inputMessage = null; 
	private DataOutputStream outputMessage = null; 
        private Thread sender = null;
        private Receiver receiver = null;
        private LinkedList nodeInfoList = null;
        private NodeInfo selfNode = null;
                     
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
    selfNode = new NodeInfo(address, port, name);
    nodeInfoList.add(selfNode);
        
    //Create a receiver thread and hand it this Node
    receiver = new Receiver(this);
        
    //start the receiver thread.
    receiver.start();
    
        
        //Sender thread
        sender = new Thread()
        {
            @Override
            
            public void run()
            {
                //Wait until the user trys to send a message
                    //This should be some from of string input
                    //The string typed by the user should start with one of these:
                        //join
                        //chat
                        //leave
                      //Toss any other input out and inform the user
                
                //Check the message to see if is a join or leave message
                    //If join message
                        //parse out the ip and port that the user is trying to join
                        //create a socket to connect
                        //add an input and output stream
                        //create a new join request message object
                        //send the join request message through the input stream
                            //as a string using .toString()
                        //wait and read a reply through the output stream
                        //set the current node list of ip and ports 
                          //of the node list that came back from the output 
                
                        //go back to waitinng for a user message
                
                    //If leave message
                        //Create a loop through the node list of ip and ports
                            //create a new socket on port at index I
                            //send the leave message through the socket
                            //close down the socket
                        //Close down this node and all threads
                
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

public NodeInfo getSelf()
{
    return this.selfNode;
}

public LinkedList getInfoList()
{
    return this.nodeInfoList;
}

public DataOutputStream getOut()
{
    return this.outputMessage;
}

public DataInputStream getInput()
{
    return this.inputMessage;
}

public void addNodeInfo(NodeInfo adding)
{
    nodeInfoList.add(adding);
}

public void removeNodeInfo(NodeInfo removing)
{
    nodeInfoList.add(removing);
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

