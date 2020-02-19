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
 * When a node wants to leave the network, it sends a leaving message to 
 * all of the nodes in its neighbor list. 
 * Nodes that receive a receive message should remove the node from their 
 * neighbor list before attempting to send another chat message.
 * @author zane
 */
public class LeaveMessage implements MessageType, Serializable
{

	/**
	 * Contains information about the node that wishes to leave the network
	 */
	private final NodeInfo leavingNodeInfo;

	/**
	 * Argument constructor.
	 * @param leavingNode Information about the node that will leave the 
	 *  		      network.
	 * @throws IllegalArgumentException Upon unsuccessful copy of the 
	 *         input leavingNode.
	 */
	public LeaveMessage( final NodeInfo leavingNode )
		throws IllegalArgumentException
	{
		try
		{
			this.leavingNodeInfo = new NodeInfo( leavingNode );
		}
		catch( UnknownHostException e )
		{
			throw new 
			IllegalArgumentException( "Unable to copy NodeInfo" );
		}
	}
	
	/**
	 * Get the type of this message
	 * @return LEAVE_MESSAGE message type
	 */
	@Override
	public MessageType.MessageTypes getType()
	{
		return MessageTypes.LEAVE_MESSAGE;	
	}

	/**
	 * Retrieve the information about the node that sent the
	 * message.
	 * @return NodeInfo about the node sending a message.
	 */
	public NodeInfo getInfo()
	{
		return this.leavingNodeInfo;	
	}
}
