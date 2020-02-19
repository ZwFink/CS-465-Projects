/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.io.Serializable;

/**
 *
 * @author zane
 */
public class JoinNotificationMessage implements MessageType, Serializable
{
	final NodeInfo joiningNodeInfo;

	public JoinNotificationMessage( final NodeInfo joiningNode )
		throws IllegalArgumentException
	{
		try
		{
			joiningNodeInfo = new NodeInfo( joiningNode );	
		}
		catch( UnknownAddressException e )
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


	
}
