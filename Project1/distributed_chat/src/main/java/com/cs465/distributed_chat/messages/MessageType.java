/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

/**
 * Interface specifying different types of messages.
 * A message that implements this interface must 
 * 
 */
public interface MessageType 
{
     enum MessageTypes
    {
     JOIN_REQUEST,
     JOIN_RESPONSE,
     JOIN_NOTIFICATION,
     CHAT_MESSAGE,
     LEAVE_MESSAGE
    }
    
    MessageTypes getType();
}
