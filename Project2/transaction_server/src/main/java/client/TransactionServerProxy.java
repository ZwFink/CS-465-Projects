/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaction.comm.Message;
import static transaction.comm.MessageTypes.MsgType.*;


/**
 * Shields Client from Server
 * Acts on behalf of Client
 * @author caleb
 */
public class TransactionServerProxy
{
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private ObjectOutputStream writeTo = null;
    private ObjectInputStream readFrom = null;
    private String host;
    private int port;
    private int transID = 0;
    ServerSocket server;
    String serverHost;
    int serverPort;
    
    TransactionServerProxy(String host, int port, String serverHost, int serverPort) 
    {
        this.host = host;
        this.port = port;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
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
            socket = new Socket();
            
            InetAddress inetAddress=InetAddress.getByName(serverHost);  
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);  
            socket.connect(socketAddress, serverPort);


            readFrom = new ObjectInputStream(socket.getInputStream());
            writeTo = new ObjectOutputStream(socket.getOutputStream());
            
            //Create some dummy data for content
            Object[] newContent = {0, 0};
            
            // need to handle messages
            Message openTrans = new Message(OPEN_TRANSACTION, newContent);
            
            //push message object thorugh output
            writeTo.writeObject(openTrans);
            transID = (int) readFrom.readObject();
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("Unable to open transaction");
        }
        
        return transID;
    }

    int read(int accountFrom) 
    {
        try
        {    
            //Create some dummy data for contents second part
            Object[] newContent = {accountFrom, 0};
            
            //Create message
            Message readRequest = new Message(READ_REQUEST, newContent);
            
            //Send message
            writeTo.writeObject(readRequest);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Unable to perfrom read transaction");
        }
        
        //Wait for response
        int balance = -1;
        try
        {
            balance = (int) readFrom.readObject();
        } catch (IOException ex)
        {
            ex.printStackTrace();
            Logger.getLogger(TransactionServerProxy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
            Logger.getLogger(TransactionServerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return balance;
    }

    void write(int accountTo, int toBalance) 
    {
        try
        {    
            //Create some dummy data for contents second part
            Object[] newContent = {accountTo, toBalance};
            
            //Create message
            Message writeRequest = new Message(WRITE_REQUEST, newContent);
            
            //Send message
            writeTo.writeObject(writeRequest);
            readFrom.readObject();
        }
        catch(IOException | ClassNotFoundException e )
        {
            e.printStackTrace();
            System.out.println("Unable to perfrom read transaction");
        }
        
    }

    void closeTransaction() 
    {
        try
        {       
            //Create some dummy data for content
            Object[] newContent = {0, 0};
            //make message
            Message closeTrans = new Message(CLOSE_TRANSACTION, newContent);
            
            writeTo.writeObject(closeTrans);
            readFrom.readObject();
            
            //close the connection
            socket.close();
        }
        catch(IOException | ClassNotFoundException e )
        {
            e.printStackTrace();
            System.out.println("Unable to close transaction");
        }
    }
}
