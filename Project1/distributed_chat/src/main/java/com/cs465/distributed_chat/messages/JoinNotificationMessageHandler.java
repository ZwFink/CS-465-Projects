/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.util.List;

/**
 * This class handles received JoinNotification messages.
 * @author zane
 */
public class JoinNotificationMessageHandler implements MessageHandler 
{
	
	/**
	 * The memberList of the node that received the JoinNotification
	 * message.
	 */
	List<NodeInfo> memberList;

	/**
	 * Argument constructor, saves the reference to the node that is 
	 * joining the network.
	 * @param membershipList The member list of the node that wishes to 
	 * join the network.
	 */
	public JoinNotificationMessageHandler( List<NodeInfo> membershipList )
	{
		this.memberList = membershipList;			
	}

	/**
	 * Method to handle the joinNotification message.
	 * Adds the NodeInfo of the joining node to the member list of the node 
	 * that received the request.
	 * @param responseMessage The JoinNotification message that was received.
	 */
	@Override
	public void handleMessage( final MessageType responseMessage )
	{
		this.memberList.add( responseMessage.getInfo() );
	}
}
