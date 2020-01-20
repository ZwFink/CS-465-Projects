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
        if (args.length != 1) 
        {
            System.out.println("Please give a port number as an argument");
            System.exit(1);
        }
        
        int port = Integer.parseInt(args[0]);
        
        try 
        {
            ServerSocket ss = new ServerSocket(port);
            Socket clientSocket = ss.accept();
            EchoThread echoT = new Echothread(clientSocket);
            echoT.run();
        }
        catch(IOException e)
        {
            System.out.println("Bad port, try again");
            System.exit(1);
        }
    }
}
