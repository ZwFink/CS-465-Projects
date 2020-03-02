package com.cs465.distributed_chat;

import java.io.*;
import java.net.*;

/**
 * create a node to initialize base connections on main device.
 * @author kenkl
 */
public class MainClass {
   public static void main(String[] args) throws IOException, InterruptedException 
   {
       int selfPort = 0;
       InetAddress selfIP = null;
       if(args.length < 2)
       {
           System.out.println("Please give an IP and a Port");
       }
       else
       {
           selfPort = Integer.parseInt(args[1]);
           selfIP = InetAddress.getByAddress(args[0].getBytes());
       }
       
       String logicalName = selfIP.getHostName();
       Node node = new Node(selfIP, selfPort, logicalName);
       //System.out.println("NODEINFO:" + node);
       //System.out.println("IP" + selfIP);
       
   }
   
     
}