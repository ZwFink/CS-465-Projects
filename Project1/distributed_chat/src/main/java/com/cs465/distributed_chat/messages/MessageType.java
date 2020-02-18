/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

/**
 * Interface specifying different types of messages.
 * A message that implements this interface must 
 * 
 */
public interface MessageType 
{
	/**
	 * Enum that implements the 
	 * different types of messages.
	 */
	public enum MessageTypes
	{
		/**
		 * A join request is a message that 
		 * is sent form a node joining the 
		 * network to a node that is already on 
		 * the network.
		 */
		JOIN_REQUEST,

		/**
		 * A join response is the response a node 
		 * sends to the node from which it received a 
		 * join request.
		 */
		JOIN_RESPONSE,

		/**
		 * A notification that is sent from a node
		 * that is joining a network to each other node
		 * on the network.
		 */
		JOIN_NOTIFICATION,

		/**
		 * A chat message containing a message payload.
		 */
		CHAT_MESSAGE,
		
		/**
		 * A message sent by a node that wishes to 
		 * leave the network.
		 */
		LEAVE_MESSAGE
	}
    
    MessageTypes getType();
}
