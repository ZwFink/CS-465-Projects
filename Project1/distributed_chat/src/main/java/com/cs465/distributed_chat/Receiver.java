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
                        //Update Node info from user Node for this thread
                        nodeInfoList = userNode.getInfoList();
                        
                        //Wait for a user to connect
                        Socket socketTalker = serverSocket.accept();
                        System.out.println("Another User is sending something- " 
                                + socketTalker 
                                + " -is the talking users socket information");
                        
                        //Create an input and output stream to handle this connection
                        DataInputStream inputMessage = new DataInputStream(socketTalker.getInputStream());
                        DataOutputStream outputMessage = new DataOutputStream(socketTalker.getOutputStream());
                        
                        //Make a buffer
                        byte[] buffer = new byte[1024];
                        int bytes_read = 0;
                        
                        //Read in a message that comes through, note .read() halts program till info is received
                        bytes_read = inputMessage.read(buffer, 0, buffer.length);
                        
                        //Record the input as a String as long as bytes read is more than 0
                        String input = "";
                        if(bytes_read > 0)
                        {
                            input = new String(buffer, 0, bytes_read);
                        }
                        else
                        {
                            input = "Error, bad byte length recieved";
                            System.out.println("Bad Byte Length");
                        }
                        
                        //Print data to log for testing purposes
                        System.err.println("Receiver: Received " + bytes_read
                                   + " bytes, displaying recieved data="
                                   + input );
                        System.out.println("Parsing Message");
                        
                        //Try to read in the object from the string input
                        MessageType messageRec = null;
                        try
                        {
                            ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
                            ObjectInput inObject = new ObjectInputStream(bis);
                            //Get our recieved object
                            Object recObject = inObject.readObject();
                            messageRec = (MessageType) recObject;
                            inObject.close();
                            bis.close();
                        }
                        catch(ClassNotFoundException e)
                        {
                            //Close out the socket of the person "talking" and go back to the start of the loop
                            socketTalker.close();
                            System.out.println("Bad Conversion");
                            continue;//handle the error by going to the top of the loop
                        }
                    
                        //Check the recieved message
                        //IF NULL == Bad Message
                        if(messageRec == null)
                        {
                            //Close out the socket of the person "talking" and go back to the start of the loop
                            socketTalker.close();
                            continue;//handle the error by going to the top of the loop
                        }
                        
                        //IF the message is a Join message
                        if(messageRec.getType() == MessageType.MessageTypes.JOIN_REQUEST)
                        {
                            //Transfrom the message into the join request type
                            JoinRequestMessage joinReq = (JoinRequestMessage) messageRec;
                            
                            //Send the ip/port list of this node back
                            JoinResponseMessage responseMsg = new JoinResponseMessage(nodeInfoList, selfNode);
                            outputMessage.writeChars(responseMsg.toString());
                        }
                        //IF the message is a Join NOTIFY message
                        if(messageRec.getType() == MessageType.MessageTypes.JOIN_NOTIFICATION)
                        {
                            //Transfrom the message into the join notification type
                            JoinNotificationMessage joinNotf = (JoinNotificationMessage) messageRec;
                            
                            //Append given ip/port of the message to list of this node
                            userNode.addNodeInfo(joinNotf.getInfo());
                        }
                        //IF the message is a normal message
                        if(messageRec.getType() == MessageType.MessageTypes.CHAT_MESSAGE)
                        {
                            //Transfrom the message into the chat message type
                            ChatMessage chatMsg = (ChatMessage) messageRec;
                            
                            //Display the message to the user
                            System.out.println(chatMsg.getMessage());
                        }
                        //IF the message is a Leave message
                        if(messageRec.getType() == MessageType.MessageTypes.LEAVE_MESSAGE)
                        {
                            //Transfrom the message into the leave message type
                            LeaveMessage leaveMsg = (LeaveMessage) messageRec;
                            //Remove the ip/port of the leaver from this nodes list
                            userNode.removeNodeInfo(leaveMsg.getInfo());
                        }

                        
                        //Close out the socket of the person "talking" and go back to the start of the loop
                        socketTalker.close();
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
