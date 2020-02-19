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
 * A chat message contains a payload and information about the 
 * node sending the message. 
 * @author zane
 */
public class ChatMessage implements MessageType, Serializable 
{
	/**
	 * The chat message that was sent by a user.
	 */
	final String chatPayload;

	/**
	 * Information about the node that sent the message.
	 */
	final NodeInfo sendingNode;
	
	public ChatMessage( final NodeInfo sender,
			    final String payload 
	                  ) throws IllegalArgumentException
	{

		try
		{
			this.sendingNode = new NodeInfo( sender );
		}
		catch( UnknownHostException e )
		{
			throw new 
			IllegalArgumentException( "Unable to copy NodeInfo");
		}

		this.chatPayload = payload;
		
	}

	/**
	 * Get the type of this message.
	 * @return CHAT_MESSAGE message type
	 */
	@Override
	public MessageType.MessageTypes getType()
	{
		return MessageTypes.CHAT_MESSAGE;	
	}

	/**
	 * Get the string message this object contains 
	 * @return The chat payload of this object.
	 */
	public String getMessage()
	{
		return this.chatPayload;	
	}

	/**
	 * Retrieve the information about the node that sent the 
	 * message.
	 * @return NodeInfo about the node sending a message.
	 */
	public NodeInfo getInfo()
	{
		return this.sendingNode;	
	}
}
