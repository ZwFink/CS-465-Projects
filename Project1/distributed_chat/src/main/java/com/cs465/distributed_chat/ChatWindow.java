package com.cs465.distributed_chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow extends JFrame
{
       JPanel panel = new JPanel();
       JLabel label = new JLabel();
       JTextField textField = new JTextField(30);
       JTextArea textArea = new JTextArea(20,30);

       public ChatWindow(Node node)
       {
              setTitle("Chat App");
              setVisible(true);
              setSize(400, 200);
              setDefaultCloseOperation(EXIT_ON_CLOSE);

              panel.add(textField);
              panel.add(textArea);


              textField.addActionListener(new ActionListener()
              {
                     public void actionPerformed(ActionEvent e)
                     {
                           String input = textField.getText();
                           
                          
                           System.out.println(node);
                     }
              });
                 
              panel.add(label);
              add(panel);

       }
       
       
       
       

       public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
       {
           
       String defaultIP = InetAddress.getLocalHost().toString();
       int selfPort = 0;
       InetAddress selfIP = InetAddress.getLocalHost();
       if(args.length < 2)
       {
            System.out.println("IP and Port arguments not given, usng default IP: " 
                                + defaultIP);
            Scanner portScan = new Scanner(System.in);
            System.out.println("Please give a Port number to use: ");
            String portStr = portScan.nextLine();     
            selfPort = Integer.parseInt(portStr);
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
       
        ChatWindow t = new ChatWindow(node);
       
   
             
       }
}