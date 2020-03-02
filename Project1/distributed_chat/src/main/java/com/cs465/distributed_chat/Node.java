package com.cs465.distributed_chat;

// A Java program for a Client 
import com.cs465.distributed_chat.messages.*;
import java.net.*; 
import java.io.*; 
import java.util.LinkedList;


public class Node 
{
    // initialize socket and input output streams 
    private final DataInputStream inputMessage = null; 
    private final DataOutputStream outputMessage = null; 
    private Sender sender = null;
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
    
    sender = new Sender(this);
         
    //Begin sender Thread      
    sender.start();
    
    //start the receiver thread.
    receiver.start();
     
    // End of Initiallization for Node threads
        
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

public void setNodeInfo(LinkedList newList)
{
    nodeInfoList = newList;
}

//End of class
}
