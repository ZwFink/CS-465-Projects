/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.util.List;

/**
 *
 * @author zane
 */
public class JoinNotificationMessageHandler implements MessageHandler 
{
	
	List<NodeInfo> memberList;

	public JoinNotificationMessageHandler( List<NodeInfo> membershipList )
	{
		this.memberList = membershipList;			
	}

	@Override
	public void handleMessage( final MessageType responseMessage )
	{
		this.memberList.add( responseMessage.getInfo() );
	}
}
