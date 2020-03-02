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
import java.util.Iterator;

/**
 *
 * @author zane
 */
public class MessageSender 
{

	/**
	 * Send a message to a single recipient, where no response is expected.
	 * @param toSend A message to send to
	 * @param recipient The NodeInfo for the node the message is being sent 
	 *                  to.
	 * @throws IOException if the message cannot be sent.
	 */
	public static void 
	sendToSingleRecipientWithoutResponse( final MessageType toSend,
				              final NodeInfo recipient
	                                    )
		throws IOException
	{
		try
			(
			Socket clientSocket =
				new Socket( recipient.getIPAddress(),
					recipient.getPort()
				);
			ObjectOutputStream writer =
				new ObjectOutputStream(
					clientSocket.getOutputStream()
				);
			)
		{
			writer.writeObject( toSend );
		}
		// throw the exception. The advantage of handling the exception 
		// this way is that the try-with-resources statement guarantees
		// that our socket and outputstreams are closed.
		catch( IOException e )
		{
			throw new IOException( e.getMessage() );
		}
	}
	
	/**
	 * Send a message to each recipient that is in a list.
	 * @param toSend The message that is to be sent to the recipients.
	 * @param recipients An iterator pointing to a list of NodeInfo 
	 *        recipients of the message
	 * @note If a message cannot be sent successfully to a recipient,
	 *       that recipient will be removed from the iterator.
	 */
	public static void sendMessageWithoutResponse( final MessageType toSend, 
		Iterator recipients
	)
	{
		while( recipients.hasNext() )
		{
			final NodeInfo currentRecipient
				= (NodeInfo) recipients.next();
			try
			{
				sendToSingleRecipientWithoutResponse( toSend,
					currentRecipient 
				);
			}
			catch(IOException e)
			{
				recipients.remove();
			}
		}
		
	}
	
	/**
	 *
	 * @param toSend
	 * @return
	 */
	public static MessageType sendMessageWithResponse( final MessageType toSend,
		MessageHandler responseHandler 
	)
	{
		return null;
	}
	
	
	
}
