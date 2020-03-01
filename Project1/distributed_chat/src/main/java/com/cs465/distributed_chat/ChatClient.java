/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat;

import java.io.InputStream;

/**
 * The chat client handles messages that a client wishes to send to other 
 * members of the chat. 
 * @author zane
 */
public class ChatClient 
{
	private Node userNode;

	public ChatClient( Node user )
	{
		this.userNode = user;	
	}

	public void runSynchronuously( InputStream clientInputStream )
	{
		while( true )
		{
			
		}
	}


	
}
