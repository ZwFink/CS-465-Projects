package transaction_server;

import accounts.AccountManager;
import locking.LockManager;
import transaction.TransactionManager;
import java.io.*;
import java.util.*;
import java.nat.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This server handles the requests from the proxy server Utilizes the following
 * AccountManager TransactionManager LockManager
 * No threading, only one server is needed to handle all clients as only one client may be on at a time
 * @author caleb, kenny
 */
public class TransactionServer
{

    public static TransactionManager transMan;
    public static AccountManager accMan;
    public static LockManager lockMan;
    public ServerSocket serverSocket;
    public String host;
    public int portNumber;
    public int accNumber;
    public boolean locking;
    public static boolean transactionView = true;
    //Creates a server socket

    public TransactionServer(String host, int port, int accNum, boolean locking)
    {
        this.locking = locking;
        this.accNumber = accNum;
        this.host = host;
        this.portNumber = port;

        transMan = new TransactionManager();
        lockMan = new LockManager(locking);
        accMan = new AccountManager();

        try
        {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e)
        {
            System.out.println("Unable to connect to the server");
        }

        //Stays open forever
        while (true /*STUB*/)
        {
            //Waits for proxy ==> .accept()
            System.out.println("Waiting for connections");
            Socket client = serverSocket.accept();
            System.out.println("Connection Established");
            
            try
            {
                //Got a client, give them to a new server worker thread and go back to waiting for a new client
                //TODO
                ObjectInputStream readFrom = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream writeTo = new ObjectOutputStream(client.getOutputStream());
            } catch (IOException ex)
            {
                Logger.getLogger(TransactionServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public class TransactionServerWorker extends Thread
    {
    }
}
