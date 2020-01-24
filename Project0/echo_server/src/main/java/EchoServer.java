/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
/**
 *
 * @author zane, caleb johnson
 */
public class EchoServer 
{
    public static void main(String[] args)
    {
        
        int port = 2080;
        if (args.length != 1) 
        {
            System.out.println("No port given, setting to default: " + port);
        }
        else
        {
            port = Integer.parseInt(args[0]);
        }
        
        Boolean socketEstablished = false;
        
        try
        {
            ServerSocket ss = new ServerSocket(port);
            ss.setSoTimeout(60000); //Server Timeout after 1 minute
            socketEstablished = true;
            
            while(!ss.isClosed())
            {
                System.out.println("Waiting for Client");
                Socket clientSocket = ss.accept();
                System.out.println("Client Found");
                (new Thread(new EchoThread(clientSocket))).start();
                System.out.println("New Echo is running.");
            }
            System.out.println("Server Closed");
        }
        catch(IOException e)
        {
            if(socketEstablished)
            {
                System.out.println("Server Timeout");
                System.exit(0);
            }
            else
            {
                System.out.println("Bad port, try again");
                System.exit(1);
            }
        }
    }
}
