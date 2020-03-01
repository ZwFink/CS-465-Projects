package com.cs465.distributed_chat;

// A Java program for a Client 
import com.cs465.distributed_chat.messages.*;
import java.net.*; 
import java.io.*; 
import java.util.LinkedList;
import java.util.Scanner;

public class Node 
{ 
	// initialize socket and input output streams 
	private DataInputStream inputMessage = null; 
	private DataOutputStream outputMessage = null; 
        private Thread sender = null;
        private Receiver receiver = null;
        private LinkedList<NodeInfo> nodeInfoList = null;
        private NodeInfo selfNode = null;
        public boolean active = true; // Boolean used to tell if the node has stopped
                     
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
    nodeInfoList = new LinkedList<>();
        
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
                    
                    Scanner inputScan = new Scanner(System.in);

                    while (true)
                    {
                        //Get input from chat user
                        System.out.println("<Chat>: " );
                        String input = inputScan.nextLine();
                        
                        //This should be some from of string input     
                        //The string typed by the user should start with one of these:
                        //join
                        //leave
                        //IF it doesn't treat it like regular chat
                        //Toss any other input out and inform the user
                           //Update Node info from user Node for this thread
                        
                        String lowerIn = input.toLowerCase();
                        
                        if(lowerIn.startsWith("join"))
                        {
                            //If join message
                            //parse out the ip and port that the user is trying to join
                            String[] inputArr = input.split(" ");
                            int joinPort = 0;
                            InetAddress joinIP = null;
                            try
                            {
                                joinIP = InetAddress.getByAddress(inputArr[1].getBytes());
                                joinPort = Integer.parseInt(inputArr[2]);
                            }
                            catch(ArrayIndexOutOfBoundsException e)
                            {
                                System.out.println("Please provide an IP and port to join");
                                //Go back to asking for input
                                continue;
                            }
                            //create a socket to connect
                            Socket otherNode = new Socket(joinIP, joinPort);
                            //add an input and output stream
                            DataInputStream inputMessage = new DataInputStream(otherNode.getInputStream());
                            DataOutputStream outputMessage = new DataOutputStream(otherNode.getOutputStream());
                            //create a new join request message object
                            JoinRequestMessage newJoin = new JoinRequestMessage(selfNode);
                            //send the join request message through the input stream
                                //as a string using .toString()
                            outputMessage.writeChars(newJoin.toString());
                            //wait and read a reply through the output stream
                            //Make a buffer
                            byte[] buffer = new byte[1024];
                            int bytes_read = 0;
                        
                            //Read in a message that comes through, note .read() halts program till info is received
                            bytes_read = inputMessage.read(buffer, 0, buffer.length);
                            
                            //Record the input as a String as long as bytes read is more than 0
                            String reply = "";
                            if(bytes_read > 0)
                            {
                                reply = new String(buffer, 0, bytes_read);
                            }
                            else
                            {
                                reply = "Error, bad byte length recieved";
                            }
                        
                            //Print data to log for testing purposes
                            System.err.println("User: Received " + bytes_read
                                    + " bytes, displaying reply="
                                    + reply );
                            
                            //Convert the return message into a join response message object
                            //Try to read in the object from the string input
                            JoinResponseMessage messageRec = null;
                            try
                            {
                                FileInputStream fileIn = new FileInputStream(input);
                                ObjectInputStream objectInputStream = new ObjectInputStream(fileIn);
                                //Get our recieved object
                                Object recObject = objectInputStream.readObject();
                                messageRec = (JoinResponseMessage) recObject;
                                objectInputStream.close();
                            }
                            catch(ClassNotFoundException e)
                            {
                                //Close out the socket of the person "replying"
                                otherNode.close();
                                continue;//handle the error by going to the top of the loop
                            }
                            
                            //set the current node list of ip and ports 
                                //of the node list that came back from the output 
                            nodeInfoList = messageRec.getList();
                        }
                        else
                        {
                            if(lowerIn.startsWith("leave"))
                            {
                                //If leave message
                                //Create a loop through the node list of ip and ports
                                int index = 0;
                                while(index < nodeInfoList.size())
                                {
                                    //create a socket to connect
                                    NodeInfo indexNodeInfo = (NodeInfo) nodeInfoList.get(index);
                                    //Get IP and Port from node at index I
                                    InetAddress indexIP = indexNodeInfo.getIPAddress();
                                    int indexPort = indexNodeInfo.getPort();
                                    //Create a new socket to the node
                                    Socket indexSock = new Socket(indexIP, indexPort);
                                    //add an output stream
                                    DataOutputStream outputMessage = new DataOutputStream(indexSock.getOutputStream());
                                    //send the leave message through the socket
                                    LeaveMessage leaveMsg = new LeaveMessage(selfNode);
                                    //send the Leave message through the input stream
                                        //as a string using .toString()
                                    outputMessage.writeChars(leaveMsg.toString());
                                    //close down the socket
                                    indexSock.close();
                                    //Move to next node in list
                                    index++;
                                }
                                    
                                active = false;
                                //Close down this node and all threads
                            }
                            
                            else //Not a properly Flagged Message == Regular chat
                            {
                                //Create a loop through the node list of ip and ports
                                int index = 0;
                                while(index < nodeInfoList.size())
                                {
                                    //create a socket to connect
                                    NodeInfo indexNodeInfo = (NodeInfo) nodeInfoList.get(index);
                                    //Get IP and Port from node at index I
                                    InetAddress indexIP = indexNodeInfo.getIPAddress();
                                    int indexPort = indexNodeInfo.getPort();
                                    //Create a new socket to the node
                                    Socket indexSock = new Socket(indexIP, indexPort);
                                    //add an output stream
                                    DataOutputStream outputMessage = new DataOutputStream(indexSock.getOutputStream());
                                    //send the chat message through the socket
                                    ChatMessage chatMsg = new ChatMessage(selfNode, input);
                                    //send the chat message through the input stream
                                        //as a string using .toString()
                                    outputMessage.writeChars(chatMsg.toString());
                                    //close down the socket
                                    indexSock.close();
                                    //Move to next node in list
                                    index++;
                                }
                                
                            }
                        }//Done handling message
                       
                       //go back to waitinng for a user message
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
                    
            }
        };

        //Begin sender Thread      
        sender.start();
        
        // End of Initiallization for Node.
        
}


/**
 * Transform a string into a message.
 * @pre message contains either the string "leave", or a string 
 *      representing a chat message the user is trying to send.
 * @param message The string message a user is trying to send.
 *        If, when trimmed and transformed to lower case, the message 
 *        does not equal ''leave'', the message will be assumed to be 
 *        a chat message
 * @return Either a LeaveMessage, or a ChatMessage, depending upon the 
 * 	   format of the message input by the user.
 */
public MessageType stringToMessage( final String message )
{
	final String lowerCaseMsg = message.trim().toLowerCase();

	if( lowerCaseMsg.equals( "leave" ) )
	{
		return new LeaveMessage( this.selfNode );	
	}

	// assume this is a chat message
	// send the original message, we don't want to 
	// change what the user is trying to send
	return new ChatMessage( this.selfNode,
				message 
	                       );
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

//End of class
}
