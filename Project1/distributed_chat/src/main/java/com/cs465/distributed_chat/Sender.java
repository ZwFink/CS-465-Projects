package com.cs465.distributed_chat;

import com.cs465.distributed_chat.messages.*;
import java.net.*; 
import java.io.*; 
import java.util.LinkedList;
import java.util.Scanner;

public class Sender extends Thread 
{ 
    // initialize socket and input output streams 

    private final LinkedList<NodeInfo> nodeInfoList = null;
    private final NodeInfo selfNode = null;
    public boolean active = true; // Boolean used to tell if the node has stopped
    private final Node userNode;
        
        
    public Sender(Node chatNode) 
    {
        // store parameters for later use
        userNode = chatNode;       
    }

    /**
     * Creates the Sender thread
     * used for sending messages to other nodes
     */
    @Override
    public void run()
    {
        //Get the user of the node that created this thread to open sockects
        LinkedList NodeInfo;
        NodeInfo = userNode.getInfoList();
        NodeInfo senderNode;
        senderNode = userNode.getSelf();
        
        try
        {
            Scanner inputScan = new Scanner(System.in);

            while (true)
            {
                //Get input from chat user
                System.out.println("<Chat>: " );
                        
                //This should be some from of string input  
                String input = inputScan.nextLine();
       
                String lowerIn = input.toLowerCase();
                
                //Update the node info list just in case it changed since last time
                NodeInfo = userNode.getInfoList();
                        
                if(lowerIn.startsWith("join"))
                {
                    //If join message
                    //parse out the ip and port that the user is trying to join
                    String[] inputArr = input.split(" ");
                    int joinPort = 0;
                    InetAddress joinIP = null;
                        
                    try
                    {
                        // get the address part
                        joinIP = InetAddress.getByAddress(inputArr[1].getBytes());
                                
                        //get the port part
                        joinPort = Integer.parseInt(inputArr[2]);
                    }
                    
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("Please provide an IP and port to join");
                        
                        //Go back to asking for input
                        continue;
                    }
                    
                    //create a socket to connect
                    try
                    {
                        Socket otherNode = new Socket(joinIP, joinPort);
                    }
                    catch(Throwable e)
                    {
                        System.out.println("Failed to create connection to node you tried to join");
                    }
                            
                    //add an input and output stream
                    DataInputStream inputMessage = new DataInputStream(otherNode.getInputStream());
                    DataOutputStream outputMessage = new DataOutputStream(otherNode.getOutputStream());
                            
                    //create a new join request message object
                    JoinRequestMessage newJoin = new JoinRequestMessage(senderNode);
                            
                    //send the join request message through the input stream
                        //as a string using .toString()
                    outputMessage.writeChars(newJoin.toString());
                            
                    //wait and read a reply through the output stream
                }
                        
                else
                {
                    //If leave message
                    if(lowerIn.startsWith("leave"))
                    {
                                
                        //Create a loop through the node list of ip and ports
                        int index = 0;
                        while(index < NodeInfo.size())
                        {
                            //create a socket to connect
                            NodeInfo indexNodeInfo = (NodeInfo) NodeInfo.get(index);
                            
                            //Get IP and Port from node at index I
                            InetAddress indexIP = indexNodeInfo.getIPAddress();
                            
                            int indexPort = indexNodeInfo.getPort();
                            
                            //Create a new socket to the node
                            Socket indexSock = new Socket(indexIP, indexPort);
                                    
                            //add an output stream
                            DataOutputStream outputMessage = new DataOutputStream(indexSock.getOutputStream());
                                    
                            //send the leave message through the socket
                            LeaveMessage leaveMsg = new LeaveMessage(senderNode);
                                    
                            //send the Leave message through the input stream
                                //as a string using .toString()
                            outputMessage.writeChars(leaveMsg.toString());
                                   
                            //close down the socket
                            indexSock.close();
                                    
                            //Move to next node in list
                            index++;
                        }
                                    
                      active = false;
                      //Close down this node and all threads
                    }
                            
                    else //Not a properly Flagged Message == Regular chat
                    {
                        //Create a loop through the node list of ip and ports
                        int index = 0;
                                
                        //iterate though the list of ip's
                        while(index < NodeInfo.size())
                        {
                            //create a socket to connect
                            NodeInfo indexNodeInfo = (NodeInfo) NodeInfo.get(index);
                                    
                            //Get IP and Port from node at index I
                            InetAddress indexIP = indexNodeInfo.getIPAddress();
                            int indexPort = indexNodeInfo.getPort();
                                    
                            //Create a new socket to the node
                            Socket indexSock = new Socket(indexIP, indexPort);
                                    
                            //add an output stream
                            DataOutputStream outputMessage = new DataOutputStream(indexSock.getOutputStream());
                                
                            //send the chat message through the socket
                            ChatMessage chatMsg = new ChatMessage(senderNode, input);
                                    
                            //send the chat message through the input stream
                                //as a string using .toString()
                            outputMessage.writeChars(chatMsg.toString());
                            
                            //close down the socket
                            indexSock.close();
                              
                            //Move to next node in list
                            index++;
                        }
                                
                    }
                }//Done handling message
                       
                       //go back to waitinng for a user message
            }
                    
        }
                
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
                    
    }
        
}

