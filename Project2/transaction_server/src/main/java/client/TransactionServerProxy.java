/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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
        }
        catch
        {
            System.out.println("Unable to open transaction");
        }
    }

    int read(int accountFrom) 
    {
        try
        {
           
            
            // need to handle messages
        }
        catch
        {
            System.out.println("Unable to read transaction");
        }.
    }

    void write(int accountTo, int toBalance) {
        try
        {
           
            // need to handle messages
        }
        catch
        {
            System.out.println("Unable to write transaction");
        }
    }

    void closeTransaction() {
        try
        {
           
            
            // need to handle messages
        }
        catch
        {
            System.out.println("Unable to close transaction");
        }
    }
}
