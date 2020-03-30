/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transaction.comm.Message;


/**
 * Shields Client from Server
 * Acts on behalf of Client
 * @author caleb
 */
public class TransactionServerProxy
{
    private Socket socket = null;
    private ObjectOutputStream writeTo = null;
    private ObjectInputStream readFrom = null;
    private String host;
    private int port;
    private int transID = 0;
    
    TransactionServerProxy(String host, int port) 
    {
        this.host = host;
        this.port = port;
    }
    // Waits for a client to call for a open connection
        // Connects to the TransactionServer
        // Handles the clients requests
        // Processes replies from the TransactionServer
    // Closes socket connect to Transaction server if Client calls close()

    int openTransaction() 
    {
        try
        {
            socket = new Socket(host, port);
            readFrom = new ObjectInputStream(socket.getInputStream());
            writeTo = new ObjectOutputStream(socket.getOutputStream());
            
            // need to handle messages
            Message openTrans = new Message(OPEN_TRANSACTION);
        }
        catch(IOException e)
        {
            System.out.println("Unable to open transaction");
        }
        
        return transID;
    }

    int read(int accountFrom) 
    {
        try
        {    
            // need to handle messages
            Message readRequest = new Message(READ_REQUEST);
        }
        catch(IOException e)
        {
            System.out.println("Unable to read transaction");
        }
        
        return balance;
    }

    void write(int accountTo, int toBalance) 
    {
        try
        {    
            // need to handle messages
            Message writeRequest = new Message(WRITE_REQUEST);
        }
        catch(IOException e)
        {
            System.out.println("Unable to write transaction");
        }
        
        return balance;
    }

    void closeTransaction() 
    {
        try
        {       
            // need to handle messages
            Message closeTrans = new Message(CLOSE_TRANSACTION);
            
            writeTo.writeObject(closeMessage);
            
            //close the connection
            socket.close();
        }
        catch(IOException e)
        {
            System.out.println("Unable to close transaction");
        }
    }
}
