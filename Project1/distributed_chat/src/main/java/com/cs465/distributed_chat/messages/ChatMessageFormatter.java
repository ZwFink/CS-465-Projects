/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

/**
 * A class that formats chat messages before they are displayed. 
 * This class takes a 
 * @author zane
 */
public class ChatMessageFormatter 
{
	
	/**
	 * Format a chat message. Returns:
	 * [SendingNodeLogicalName]: Message
	 * where SendingNodeLogicalName is the logical name of the node 
	 * that sent the message.
	 * @param toFormat The message to format.
	 * @return The formatted chat message.
	 */
	public String formatMessage( final ChatMessage toFormat )
	{
		String message = toFormat.getMessage();
		String senderName = toFormat.getInfo().getName();
		
		return "[" + senderName + "]: " + message;
	}
}
