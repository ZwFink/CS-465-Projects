/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A MessageHandler for the JoinResponse class. 
 * A node receives a JoinResponse message from the node it sends a JoinRequest
 * to. This class handles this JoinResponse by adding all of the nodes in the 
 * network to the member list of the joining node, and then sending a JoinNotification
 * message to each other node on the network. 
 * @author zane
 */
public class JoinResponseMessageHandler implements MessageHandler
{

	/**
	 * The memberList of the node that is joining the network.
	 * This list will be populated by this class.
	 */
	ArrayList<NodeInfo> joiningNodeMemberList;

	/**
	 * Information about the node joining the network.
	 */
	NodeInfo joiningNode;

	/**
	 * Argument constructor, saves the data necessary to 
	 * respond to a JoinResponse.
	 * @param joiningNodeMemberList The membership list of the node that 
	 *        is joining the network. This list will be populated by 
	 *       handleMessage
	 * @param joiningNodeInfo The information for the node joining the network.
	 */
	public JoinResponseMessageHandler( 
		ArrayList<NodeInfo> joiningNodeMemberList,
		NodeInfo joiningNodeInfo
	)
	{
		this.joiningNodeMemberList = joiningNodeMemberList;		
		this.joiningNode = joiningNodeInfo;
	}

	/**
	 * Handle the join notification message. 
	 * Adds every node on the network to the member list of the node that 
	 * is joining, and sends a JoinNotificationMessage to each node that's 
	 * on the network.
	 * @param responseMessage The joinNotification message that will be 
	 *        handled.
	 */
	@Override
	public void handleMessage( MessageType responseMessage ) 
	{
		JoinResponseMessage msg = (JoinResponseMessage) responseMessage;

		// add all of the nodes in the network to the member list
		for( final NodeInfo currentNode : msg.getList() )
		{
			try 
			{
				this.joiningNodeMemberList
					.add( new NodeInfo( currentNode ) );
			} 
			catch (UnknownHostException ex) 
			{
				Logger.getLogger(
					JoinResponseMessageHandler.class.getName())
					.log(Level.SEVERE, null, ex);
			}
		}

		JoinNotificationMessage notif = 
			new JoinNotificationMessage( this.joiningNode );

		// sends to every node in the network, we want to 
		// let everyone know that we're here!
		MessageSender.sendMessageWithoutResponse( notif,
			this.joiningNodeMemberList.iterator() 
		);
	}


	
}
