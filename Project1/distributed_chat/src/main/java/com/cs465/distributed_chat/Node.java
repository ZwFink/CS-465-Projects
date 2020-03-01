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
        private Socket socketTalker;
        private Node userNode;
                     
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
    //nodeInfoList.add(selfNode);
        
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
                try
                {
                    // begin server given port that is passed in.
                    ServerSocket serverSocket = new ServerSocket(port);
                    
                    while (true)
                    {
                        nodeInfoList = userNode.getInfoList();
                        
                        //Wait until the user trys to send a message
                        Socket socketTalker = serverSocket.accept();
                        System.out.println("Connected \n " );
                      
                        //create input and output streams to handle data
                        DataOutputStream outputMessage = new DataOutputStream(socketTalker.getOutputStream());
                        DataInputStream inputMessage = new DataInputStream(socketTalker.getInputStream());
                             
                        //prompt user to send message
                        System.out.println("User is sending a message-  \n" );
                                               
                        //Make a buffer reader to read in data
                        BufferedReader read = new BufferedReader(new InputStreamReader(inputMessage));
                        
                        //cast the data into a string
                        String entry = read.toString();   
                        
                        outputMessage.write(("Connected To Server \n" + entry).getBytes());

                        while( (entry = read.readLine()) != null)
                        {
                            String message = entry;
                            
                            System.out.println(message);
                            
                        }
                        
                        outputMessage.writeBytes( entry + "\n" );
                        //Record the input as a String as long as bytes read is more than 
                        
                        //System.out.println(input); // testing
                        
                        //Try to read in the object from the string input
                       
                   
                        //Check the recieved message
                        //IF NULL == Bad Message
                        //Check the recieved message
                        //IF the message is a Join message
                        if(entry == "join")
                        {
                            //Send the ip/port list of this node
                             NodeInfo newNode = new NodeInfo(address, port, name);
                             nodeInfoList.add(newNode);
                             System.out.println(socketTalker + " has joined");
                        }
                       
                        //IF the message is a normal message
                        if(entry != "leave")
                        {
                            //Display the message to the user
                            outputMessage.write(entry);
                           
                            
                              
                        }
                        
                        //IF the message is a Leave message
                        if(entry == "leave")
                        {
                            //Remove the ip/port of the leaver from this nodes list
                            NodeInfo newNode = null;
                            
                            closeConnection(socketTalker);
                            
                            numConnections --;
                        }

                        
                        //Close out the socket of the person "talking" and go back to the start of the loop
                        closeConnection(socketTalker);
                    }
                    
                }
                
                catch(UnknownHostException i)
                {
                    System.out.println(i);
                }
                
                catch(IOException ioe)
                {
                    System.out.println(ioe);
                }
               
                      //This should be some from of string input     
                      //The string typed by the user should start with one of these:
                        //join
                        //chat
                        //leave
                      //Toss any other input out and inform the user
                        //Update Node info from user Node for this thread
                
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

        
        
        
        
private void closeConnection(Socket socket)
{
    try
    {
        socketTalker.close();
    }
    catch(IOException exception)
    {
       System.out.println("error closing the socket" + exception);         
    }
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
