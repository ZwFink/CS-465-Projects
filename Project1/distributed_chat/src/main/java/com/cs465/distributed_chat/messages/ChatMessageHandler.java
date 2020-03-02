/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import java.io.PrintWriter;

/**
 * A handler for the ChatMessage class. 
 * Outputs a formatted version of the ChatMessage to a location specified by 
 * a PrintWriter.
 * @author zane
 */
public class ChatMessageHandler implements MessageHandler
{
	/**
	 * A formatter for the chat message.
	 */
	final ChatMessageFormatter messageFormatter;

	/**
	 * The printwriter the message will be output to
	 */
	PrintWriter messageDest;


	/**
	 * Argument constructor. Sets this class up to handle 
	 * a ChatMessage.
	 * @param format A class that will format the message that is written
	 *        to the PrintWriter
	 * @param messageDestination The location to print the message to.
	 */
	public ChatMessageHandler( final ChatMessageFormatter format,
			           PrintWriter messageDestination
	                         )
	{
		this.messageFormatter = format;	
		this.messageDest = messageDestination;
	}

	/**
	 * Handle a chat message that has been received.
	 * @param responseMessage The chat message that was received.
	 * @note Writes a line to the PrintWriter that was specified in this 
	 *       class' constructor. This message will be formatted by this 
	 *       class' message formatter. 
	 */
	@Override
	public void handleMessage(MessageType responseMessage) 
	{
		ChatMessage msg = (ChatMessage) responseMessage;
		String formattedMsg = this.messageFormatter.formatMessage( msg );

		this.messageDest.println( formattedMsg );
	}
	
}
