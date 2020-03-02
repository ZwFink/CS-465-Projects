/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

/**
 *
 * @author zane
 */
public interface MessageHandler 
{
	
	public void handleMessage( final MessageType responseMessage );
}
