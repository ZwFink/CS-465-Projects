/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import java.io.Serializable;
import com.cs465.distributed_chat.NodeInfo;

/**
 * A JoinRequestMessage is a message that is sent from a node 
 * that wishes to join a network to a node that is already on the 
 * network. 
 * @author zane
 */
public class JoinRequestMessage implements MessageType, Serializable
{
	/**
	 * Information about the node wishing to join the network.
	 * Each node that receives this message should add this info to 
	 * its member list.
	 */
	private final NodeInfo joiningNodeInfo;
	
	/**
	 * Get the type of this message.
	 * @return JOIN_REQUEST message type
	 */
	@Override
	public MessageType.MessageTypes getType()
	{
		return MessageType.MessageTypes.JOIN_REQUEST;	
	}

	/**
	 * Initialize this message.
	 * @param info The info for the node that is crafting the 
	 *        JoinRequest message. Typically, this will be 
	 *        the node that is about to send the message.
	 */
	public JoinRequestMessage( NodeInfo info )
	{
		this.joiningNodeInfo = info;	
	}
}
