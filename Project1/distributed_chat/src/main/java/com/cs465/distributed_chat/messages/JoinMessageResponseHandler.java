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
public class JoinMessageResponseHandler implements MessageResponseHandler 
{
	
	List<NodeInfo> memberList;

	public JoinMessageResponseHandler( List<NodeInfo> membershipList )
	{
		this.memberList = membershipList;			
	}

	public void handleResponse( final MessageType responseMessage )
	{
		this.memberList.add( responseMessage.getInfo() );
	}
}
