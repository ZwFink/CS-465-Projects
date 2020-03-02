/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

/**
 * A handler for a JoinRequestMessage. When a node receives a JoinRequestMessage,
 * it should create this JoinRequestMessageHandler. This class will send the 
 * JoinResponse message to the node that is requesting to join, and will 
 * add the joining node to its member list. 
 * @author zane
 */
public class JoinRequestMessageHandler implements MessageHandler
{
	/**
	 * The node that is responding to the request.
	 */
	final NodeInfo respondingNode;

	/**
	 * The neighborlist of the node that received the JoinRequestMessage.
	 * This neighborList contains all of the NodeInfo for the nodes on the 
	 * network.
	 */
	final LinkedList<NodeInfo> neighborList;

	/**
	 * A socket that is bound to the requestingNode. 
	 * The JoinResponse message will be sent to the joining node 
	 * through this socket.
	 */
	Socket connectedSocket;

	/**
	 * Argument constructor.
	 * @param respondingNode The node that is responding to the JoinRequest.
	 * @param neighborList The neighbor list of the node that received 
	 * 	  the JoinRequest message. This will be sent to the joining node.
	 * @param connectedSocket A socket that is connected to the node that 
	 * 	  is joining the network.
	 * @note Upon IOError, the JoiningNode is NOT added to the neighborList of 
	 *       the node that received the JoinRequest, as it is assumed 
	 *       that the node disconnected unexpectedly. Otherwise, the JoiningNode
	 *       will be added to the neighborList
	 * @note When handleMessage is called, this class will send a 
	 *       JoinResponse to the joining node.
	 */
	public JoinRequestMessageHandler( 
					  final NodeInfo respondingNode,
					  final LinkedList<NodeInfo> neighborList,
		                          Socket connectedSocket 
	                                )
	{
		this.respondingNode = respondingNode;
		this.neighborList = neighborList;
		this.connectedSocket = connectedSocket;
	}

	/**
	 * Handle the JoinRequestMessage. 
	 * Adds the joining node to the neighbor list of the node that received 
	 * the JoinRequest, sends a JoinResponse to the node that is requesting 
	 * to join the network.
	 * @param responseMessage 
	 */
	@Override
	public void handleMessage( final MessageType responseMessage )
	{

		JoinRequestMessage joinReq =
			(JoinRequestMessage) responseMessage;
		final NodeInfo requestingNode = responseMessage.getInfo();

		try
		( 
			ObjectOutputStream writer 
				= new ObjectOutputStream( 
					connectedSocket.getOutputStream() 
				);
		)
		{
			MessageType response 
				= new JoinResponseMessage( 
					this.neighborList,
					this.respondingNode
				);

			writer.writeObject( response );
			this.neighborList.add( requestingNode );
			
		}
		catch( IOException e )
		{
			this.neighborList.remove( requestingNode );
		}
		
	}
}
