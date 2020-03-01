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
       InetAddress selfIP = InetAddress.getLocalHost();
       int selfPort = 2080;
       String logicalName = selfIP.getHostName();
       Node node = new Node(selfIP, selfPort, logicalName);
       //System.out.println("NODEINFO:" + node);
       //System.out.println("IP" + selfIP);
       
   }
   
     
}