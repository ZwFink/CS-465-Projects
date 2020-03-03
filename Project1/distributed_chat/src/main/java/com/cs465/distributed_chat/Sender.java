package com.cs465.distributed_chat;

import com.cs465.distributed_chat.messages.*;
import java.net.*; 
import java.io.*; 
import java.util.LinkedList;
import java.util.Scanner;

public class Sender extends Thread 
{ 
    // initialize socket and input output streams 

    private LinkedList<NodeInfo> nodeInfoList = null;
    private NodeInfo senderNode = null;
    public boolean active = true; // Boolean used to tell if the node has stopped
    private final Node userNode;
        
        
    public Sender(Node chatNode) 
    {
        // store parameters for later use
        userNode = chatNode;       
    }

    /**
     * Creates the Sender thread
     * used for sending messages to other 
     */
    @Override
    public void run()
    {
        //Get the user of the node that created this thread to open sockects
        nodeInfoList = userNode.getInfoList();
        senderNode = userNode.getSelf();
        
        try
        {
            
            Scanner inputScan = new Scanner(System.in);
            
            System.out.println("\n  To join the chat type \n"
                                + "join -> ip address -> port # \n");
            

            while (true)
            {    
                //Update the node info list just in case it changed since last time
                nodeInfoList = userNode.getInfoList();
                                   
                //Get input from chat user
                System.out.println("<Chat>: " );                       
                //This should be some from of string input  
                String input = inputScan.nextLine();       
                
               // System.out.println("input is " + input);       // Testing 
               
                String lowerIn = input.toLowerCase();

                // split the input into segments
                String[] inputArr = input.split(" ");
                
                //join 127.0.0.1 2080
                
                if(lowerIn.startsWith("join"))
                {
                    //If join message
                    //parse out the ip and port that the user is trying to join
                    int joinPort = 0;
                    
                    InetAddress joinIP = null;
                    
                //    System.out.println("Made it here!  1"); 
                    
                   try
                   {
                        // get the address part
                        joinIP = InetAddress.getByName(inputArr[1]);
                                
                        //get the port part
                        joinPort = Integer.parseInt(inputArr[2]);
                        
                    //    System.out.println("join ip is: " + joinIP);
                   
                    //    System.out.println("user is connected");
                        
                     //   System.out.println("Made it here! 5");
                    }
                   
                    catch(UnknownHostException i)
                    {
                        System.out.println(i);
                        
                        System.out.println(i + "ERROR LOCATION 3");
                    }
                    
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("Please provide an IP and port to join");
                        
                     //   System.out.println("Made it here! 3");
                        
                        //Go back to asking for input
                        continue;
                    }
                        //create a socket to connect
                        Socket otherNode = null;
                        try
                        {
                            otherNode = new Socket(joinIP, joinPort);
                        }
                        catch(ConnectException e)
                        {
                            System.out.println("That IP and Port is not on the network");
                            continue;
                        }
                            
                        //add an input and output stream
                        ObjectOutputStream outputMessage = new ObjectOutputStream(otherNode.getOutputStream());
                        ObjectInputStream inputMessage = new ObjectInputStream(otherNode.getInputStream());
                        
                            
                        
                        //create a new join request message object
                        JoinRequestMessage newJoin = new JoinRequestMessage(senderNode);
                            
                        //send the join request message through the input stream
                            //as a string using .toString()
                        outputMessage.writeObject(newJoin);
                        
                      //  System.out.println("Made it here! 4");
                   
                   
                        //wait and read a reply through the output stream
                        JoinResponseMessage response = null;
                        try
                        {
                            response = (JoinResponseMessage) inputMessage.readObject();
                        }
                        catch(Throwable e)
                        {
                            System.out.println("Failed to convert response");
                        }
                        
                        if(response != null)
                        {
                            //update own list
                            userNode.setInfoList(response.getList());
                            userNode.addNodeInfo(response.getInfo());
                           
                            
                            //Iterate through new lst and notify all
                            //Make notify message
                            JoinNotificationMessage newNotf = new JoinNotificationMessage(senderNode);
                            
                            //Update the node info list of this thread
                            nodeInfoList = userNode.getInfoList();
                            
                            int index = 0;
                            while(index < nodeInfoList.size())
                            {
                                //create a socket to connect
                                NodeInfo indexNodeInfo = (NodeInfo) nodeInfoList.get(index);
                            
                                //Get IP and Port from node at index I
                                InetAddress indexIP = indexNodeInfo.getIPAddress();
                            
                                int indexPort = indexNodeInfo.getPort();
                            
                                //Create a new socket to the node
                                Socket indexSock = new Socket(indexIP, indexPort);
                                    
                                //add an output stream
                                ObjectOutputStream notifyMessage = new ObjectOutputStream(indexSock.getOutputStream());
                                
                                //send the Notify message through the input stream
                                //as a string using .toString()
                                notifyMessage.writeObject(newNotf);
                                   
                                //close down the socket
                                //notifyMessage.close();
                                //indexSock.close();
                                    
                                //Move to next node in list
                                index++;
                            }
                        }//End of notify
                        
                        //Inform user htey joined
                        System.out.println("Join Succeeded!");
                        
                }
                        
                else
                {
                    //If leave message
                    if(lowerIn.startsWith("leave"))
                    {
                                
                        //Create a loop through the node list of ip and ports
                        int index = 0;
                        while(index < nodeInfoList.size())
                        {
                            //create a socket to connect
                            NodeInfo indexNodeInfo = (NodeInfo) nodeInfoList.get(index);
                            
                            //Get IP and Port from node at index I
                            InetAddress indexIP = indexNodeInfo.getIPAddress();
                            
                            int indexPort = indexNodeInfo.getPort();
                            
                            //Create a new socket to the node
                            Socket indexSock = new Socket(indexIP, indexPort);
                                    
                            //add an output stream
                            ObjectOutputStream outputMessage = new ObjectOutputStream(indexSock.getOutputStream());
                                    
                            //send the leave message through the socket
                            LeaveMessage leaveMsg = new LeaveMessage(senderNode);
                                    
                            //send the Leave message through the input stream
                                //as a string using .toString()
                            outputMessage.writeObject(leaveMsg);
                                   
                            //close down the socket
                            //outputMessage.close();
                            //indexSock.close();
                                    
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
                        while(index < nodeInfoList.size())
                        {
                            //create a socket to connect
                            NodeInfo indexNodeInfo = (NodeInfo) nodeInfoList.get(index);
                                    
                            //Get IP and Port from node at index I
                            InetAddress indexIP = indexNodeInfo.getIPAddress();
                            int indexPort = indexNodeInfo.getPort();
                                    
                            //Create a new socket to the node
                            Socket indexSock = new Socket(indexIP, indexPort);
                                    
                            //add an output stream
                            ObjectOutputStream outputMessage = new ObjectOutputStream(indexSock.getOutputStream());
                                
                            //send the chat message through the socket
                            ChatMessage chatMsg = new ChatMessage(senderNode, input);
                                    
                            //send the chat message through the input stream
                                //as a string using .toString()
                            outputMessage.writeObject(chatMsg);
                            
                            //close down the socket
                            //outputMessage.close();
                            //indexSock.close();
                              
                            //Move to next node in list
                            index++;
                        }
                                
                    }
                }//Done handling message
                       
                       //go back to waiting for a user message
            }
                    
        }
        
        catch(UnknownHostException i)
        {
         //   System.out.println(i + "ERROR LOCATION 1");
        }
                
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
                    
    }
        
}

