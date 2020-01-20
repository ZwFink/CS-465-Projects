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
        
        
        
        try 
        {
            ServerSocket ss = new ServerSocket(port);
            ss.setSoTimeout(100000);
            System.out.println("Waiting for Client");
            Socket clientSocket = ss.accept();
            EchoThread echoT = new EchoThread(clientSocket);
            echoT.run();
            System.out.println("Echo is running.");
        }
        catch(IOException e)
        {
            System.out.println("Bad port, try again");
            System.exit(1);
        }
    }
}
