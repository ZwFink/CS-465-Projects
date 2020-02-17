/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.*;

/**
 *
 * @author kenklawitter
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int port = 2003;
        
        try
        {   
            ServerSocket serverSocket = new ServerSocket(port);
        
        while(true)
        {
            System.out.println("Accepting Clients");
            Socket userSocket = serverSocket.accept();
           
            Thread connectionThread = new Thread()               
            {
                @Override
                public void run()
                {
                    try
                    {
                        handleClientSocket(userSocket);
                    }
                      catch (IOException e)
                    {   
                        e.printStackTrace();
                    }
                    
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
            }
        };
            connectionThread.start();        
        }     
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    
//Loops to keep thread open for multiple connections.
    
private static void handleClientSocket(Socket userSocket) throws IOException, InterruptedException
{
    InputStream input = userSocket.getInputStream();
    OutputStream output = userSocket.getOutputStream();

    BufferedReader read = new BufferedReader(new InputStreamReader(input));
    
    String entry;
    
    output.write(("Connected To Server \n").getBytes());
    
    while( (entry = read.readLine()) != null)
    {
        String message = "user typed: " + entry + "\n";
        output.write(message.getBytes());
    }
    
    userSocket.close();
}
    
}
