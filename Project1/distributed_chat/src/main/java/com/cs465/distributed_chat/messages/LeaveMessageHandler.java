/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.util.ArrayList;

/**
 * A handler class for when a LeaveMessage is received.
 * Removes the information of the leaving node from a node's member list.
 * @author zane
 */
public class LeaveMessageHandler implements MessageHandler 
{
	/**
	 * The member list of the node that received the 
	 * LeaveMessage.
	 */
	ArrayList<NodeInfo> receivingNodeMemberList;

	/**
	 * Argument constructor.
	 * @param receivingNodeMemberList The member list of the node that 
	 * received the LeaveMessage.
	 */
	public LeaveMessageHandler( ArrayList<NodeInfo> receivingNodeMemberList )
	{
		this.receivingNodeMemberList = receivingNodeMemberList;		
	}

	/**
	 * Handle a LeaveMessage.
	 * @param responseMessage The LeaveMessage that was received.
	 * @post The information of the leaving node is no longer found in 
	 *       this class's receivingNodeMemberList
	 */
	@Override
	public void handleMessage( MessageType responseMessage ) 
	{
		LeaveMessage msg = (LeaveMessage) responseMessage;
		NodeInfo leavingNode = msg.getInfo();

		this.receivingNodeMemberList.remove( leavingNode );
	}
	
}
