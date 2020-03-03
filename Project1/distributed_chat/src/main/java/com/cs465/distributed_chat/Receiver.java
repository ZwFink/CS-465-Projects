/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat;

import com.cs465.distributed_chat.messages.ChatMessage;
import com.cs465.distributed_chat.messages.JoinNotificationMessage;
import com.cs465.distributed_chat.messages.JoinRequestMessage;
import com.cs465.distributed_chat.messages.JoinResponseMessage;
import com.cs465.distributed_chat.messages.LeaveMessage;
import com.cs465.distributed_chat.messages.MessageType;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 *
 * @author caleb
 */
public class Receiver extends Thread
{
        private Node userNode;
        
    public Receiver(Node chatNode) 
    {
        // store parameters for later use
        userNode = chatNode;
        
    }
        
    public void run()
            {
                //Get the user of the node that created this thread to open sockects
                LinkedList nodeInfoList = userNode.getInfoList();
                NodeInfo selfNode = userNode.getSelf();
                int port = selfNode.getPort();
                
                try
                {
                    // begin server given port that is passed in.
                    ServerSocket serverSocket = new ServerSocket(port);
                    
                    while (true)
                    {
  
                        //Wait for a user to connect
                        Socket socketTalker = serverSocket.accept();
                        
                        //Update Node info from user Node for this thread
                        nodeInfoList = userNode.getInfoList();

                        //Create an input and output stream to handle this connection
                        ObjectOutputStream outputMessage = new ObjectOutputStream(socketTalker.getOutputStream());
                        ObjectInputStream inputMessage = new ObjectInputStream(socketTalker.getInputStream());
                        //System.out.println("Made input and output");
                        
                        MessageType messageRec = null;
                        
                        try
                        {
                            //System.out.println("getting object");
                            messageRec = (MessageType) inputMessage.readObject();
                            //System.out.println("got object");
                        }
                        catch(Throwable e)
                        {
                            System.out.println("Failed to convert message to object");
                            continue;
                        }
                        
                        //Check the recieved message
                        //IF NULL == Bad Message
                        if(messageRec == null)
                        {
                            //go back to the start of the loop
                            System.out.println("Message was null");
                            continue;//handle the error by going to the top of the loop
                        }
                        
                        //Record the input as a String
                        String input = messageRec.toString();
                        
                        
                        //IF the message is a Join message
                        if(messageRec.getType() == MessageType.MessageTypes.JOIN_REQUEST)
                        {
                            //Transfrom the message into the join request type
                            JoinRequestMessage joinReq = (JoinRequestMessage) messageRec;
                            
                            //Send the ip/port list of this node back
                            JoinResponseMessage responseMsg = new JoinResponseMessage(nodeInfoList, selfNode);
                            outputMessage.writeObject(responseMsg);
                        }
                        //IF the message is a Join NOTIFY message
                        if(messageRec.getType() == MessageType.MessageTypes.JOIN_NOTIFICATION)
                        {
                            //Transfrom the message into the join notification type
                            JoinNotificationMessage joinNotf = (JoinNotificationMessage) messageRec;
                            
                            
                            //Append given ip/port of the message to list of this node
                            userNode.addNodeInfo(joinNotf.getInfo());
                            //Update the node info list of this thread
                            nodeInfoList = userNode.getInfoList();
                            
                            NodeInfo notifingNode = (NodeInfo) joinNotf.getInfo();
                            System.out.println("New Node Joined");
                            System.out.println("New Node Info IP: " + notifingNode.getIPAddress().toString());
                            System.out.println("New Node Info Port: " + notifingNode.getPort());
                            //Reshow chat input test
                            System.out.println("<Chat>: ");
                        }
                        //IF the message is a normal message
                        if(messageRec.getType() == MessageType.MessageTypes.CHAT_MESSAGE)
                        {
                            //Transfrom the message into the chat message type
                            ChatMessage chatMsg = (ChatMessage) messageRec;
                            
                            NodeInfo talkerInfo = chatMsg.getInfo();
                            System.out.print("< " + talkerInfo.getIPAddress().toString());
                            System.out.print(" // " + talkerInfo.getPort() + ">: ");
                            //Display the message to the user
                            System.out.print(chatMsg.getMessage() + "\n");
                            //Reshow chat input test
                            System.out.println("<Chat>: ");
                        }
                        //IF the message is a Leave message
                        if(messageRec.getType() == MessageType.MessageTypes.LEAVE_MESSAGE)
                        {
                            //Transfrom the message into the leave message type
                            LeaveMessage leaveMsg = (LeaveMessage) messageRec;
                         
                            //Remove the ip/port of the leaver from this nodes list
                            userNode.removeNodeInfo(leaveMsg.getInfo());
                            //System.out.println("New List size: " + userNode.getInfoList().size());
                        }

                        //outputMessage.close();
                        //inputMessage.close();
                        //Close out the socket of the person "talking" and go back to the start of the loop
                        //socketTalker.close();
                    }
                    
                }
                
                catch(UnknownHostException i)
                {
                    System.out.println(i);
                }
                
                catch(IOException ioe)
                {
                    System.out.println(ioe);
                }
            }
}
