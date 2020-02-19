/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.io.Serializable;
import java.net.UnknownHostException;

/**
 * A join notification is sent from a joining node to the other 
 * nodes that are on a network, letting these nodes know that they should 
 * add the node their membership list. 
 * @author zane
 */
public class JoinNotificationMessage implements MessageType, Serializable
{
	/**
	 * Information about the node that is joining the server.
	 */
	final NodeInfo joiningNodeInfo;

	/**
	 * Argument constructor, creates a JoinNotificationMessage from 
	 * information about the node that will be joining the network.
	 * @param joiningNode Information about the node that will be joining 
	 *        the network.
	 * @throws IllegalArgumentException Upon UnknownHostException thrown by 
	 *         NodeInfo's copy constructor.
	 */
	public JoinNotificationMessage( final NodeInfo joiningNode )
		throws IllegalArgumentException
	{
		try
		{
			joiningNodeInfo = new NodeInfo( joiningNode );	
		}
		catch( UnknownHostException e )
		{
			throw new 
			IllegalArgumentException( "Unable to copy NodeInfo" );
		}
	}


	/**
	 * Get the type of this message.
	 * @return JOIN_NOTIFICATION message type
	 */
	@Override
	public MessageType.MessageTypes getType()
	{
		return MessageType.MessageTypes.JOIN_NOTIFICATION;	
	}

	/**
	 * Retrieve the information about the node that sent the 
	 * message.
	 * @return NodeInfo about the node sending a message.
	 */
	public NodeInfo getInfo()
	{
		return this.joiningNodeInfo;	
	}
	
}
