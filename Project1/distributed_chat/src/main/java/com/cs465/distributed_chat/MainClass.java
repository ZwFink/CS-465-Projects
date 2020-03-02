package com.cs465.distributed_chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.Integer;

/**
 * create a node to initialize base connections on main device.
 * @author kenkl
 */
public class MainClass {
   public static void main(String[] args) throws IOException, InterruptedException 
   {
       String defaultIP = "127.0.0.1";
       int selfPort = 0;
       InetAddress selfIP = null;
       if(args.length < 2)
       {
            System.out.println("IP and Port arguments not given, usng default IP: " 
                                + defaultIP);
            Scanner portScan = new Scanner(System.in);
            System.out.println("Please give a Port number to use: ");
            String portStr = portScan.nextLine();     
            selfPort = Integer.parseInt(portStr);
            selfIP = InetAddress.getByName(defaultIP);
       }
       else
       {
           selfPort = Integer.parseInt(args[1]);
           selfIP = InetAddress.getByName(args[0]);
       }
       
       String logicalName = selfIP.getHostName();
       Node node = new Node(selfIP, selfPort, logicalName);
       //System.out.println("NODEINFO:" + node);
       //System.out.println("IP" + selfIP);
       
   }
   
     
}