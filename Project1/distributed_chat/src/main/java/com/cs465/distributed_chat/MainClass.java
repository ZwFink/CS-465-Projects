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
   private static int ARGC = 3;
   public static void main(String[] args) throws IOException, InterruptedException 
   {
       if( args.length != ARGC )
       {
	    System.out.println("ERROR: Wrong arguments were given. \n\n"
		    +          "USAGE: \n"
		    + "java -jar jar_name my_ip my_port my_name \n\n"
		    + "Where jar_name is the name of the jar executable, "
		    + "my_ip is the IP of the node that is connecting "
		    + "e.g. 127.0.01., port is the port to use when communicating.\n\n"
	    );
	    return;
       }
       
       
       InetAddress addr = InetAddress.getByName( args[ 0 ] );
       int port = Integer.parseInt( args[ 1 ] );
       String logicalName = args[ 2 ];

       Node node = new Node( addr , port, logicalName);
       node.startChatClient();
   }
   
     
}