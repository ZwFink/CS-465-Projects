package com.cs465.distributed_chat;

import java.io.*;
import java.net.*;
import java.lang.Integer;

/**
 * create a node to initialize base connections on main device.
 * @author kenkl
 */
public class MainClass 
{
   private static int ARGC_CREATE_CHAT = 3;
   private static int ARGC_JOIN_CHAT = 5;
   public static void main(String[] args) throws IOException, InterruptedException 
   {
       String defaultIP = InetAddress.getLocalHost().toString();
       int selfPort = 0;
       InetAddress selfIP = InetAddress.getLocalHost();
       if( !( args.length == ARGC_CREATE_CHAT 
	       || args.length == ARGC_JOIN_CHAT 
	    ) 
	 )
       {
	    System.out.println("ERROR: Wrong arguments were given. \n\n"
		    +          "USAGE: (Starting a chat) \n"
		    + "java -jar jar_name my_ip my_port my_name \n\n"
		    + "Where jar_name is the name of the jar executable, "
		    + "my_ip is the IP of the node that is connecting "
		    + "e.g. 127.0.01., port is the port to use when communicating."
		    + "\n\nUSAGE: (Joining an existing chat) \n"
		    + "java -jar jar_name my_ip my_port my_name join_ip join_port \n\n"
		    + "Where join_ip is the IP of the node to connect to, and "
		    + "join_port is the port to contact join_ip on.\n\n" 
	    );
	    return;
       }
       
       
       String logicalName = selfIP.getHostName();
       Node node = new Node(selfIP, selfPort, logicalName);
       //System.out.println("NODEINFO:" + node);
       //System.out.println("IP" + selfIP);
       
   }
   
     
}