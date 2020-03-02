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
     * used for sending messages to other 
     */
    @Override
    public void run()
    {
        //Get the user of the node that created this thread to open sockects
        LinkedList NodeInfo;
        NodeInfo = userNode.getInfoList();
        NodeInfo senderNode;
        senderNode = userNode.getSelf();
        int port = 2080;
        
        try
        {
            
            Scanner inputScan = new Scanner(System.in);
            
            System.out.println("\n  To join the chat type \n"
                                + "join -> ip address -> port # \n");
            

            while (true)
            {    
                //Update the node info list just in case it changed since last time
                NodeInfo = userNode.getInfoList();
                                   
                //Get input from chat user
                System.out.println("<Chat>: " );                       
                //This should be some from of string input  
                String input = inputScan.nextLine();       
                
               // System.out.println("input is " + input);       // Testing 
               
                String lowerIn = input.toLowerCase();

                // split the input into segments
                String[] inputArr = input.split(" ");
                
                //join 127.0.0.1 2080
                
                if("join".equals(inputArr[0]))
                {
                    //If join message
                    //parse out the ip and port that the user is trying to join
                    int indexPort = 0;
                    
                    InetAddress joinIP = null;
                    
                //    System.out.println("Made it here!  1"); 
                    
                   try
                   {
                        // get the address part
                        joinIP = InetAddress.getByName(inputArr[1]);
                                
                        //get the port part
                        indexPort = Integer.parseInt(inputArr[2]);
                        
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
                            otherNode = new Socket(joinIP, indexPort);
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
                        outputMessage.writeChars(newJoin.toString());
                        
                      //  System.out.println("Made it here! 4");
                   
                    
                            
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
                            ObjectOutputStream outputMessage = new ObjectOutputStream(indexSock.getOutputStream());
                                    
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
                            ObjectOutputStream outputMessage = new ObjectOutputStream(indexSock.getOutputStream());
                                
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

