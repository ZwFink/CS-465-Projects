package transaction_server;

import accounts.AccountManager;
import locking.LockManager;
import transaction.TransactionManager;
import java.io.*;
import java.util.*;
import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaction.Transaction;
import utils.PropertyHandler;


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
public class TransactionServer extends Thread
{

    public static TransactionManager transMan;
    public static AccountManager accMan;
    public static LockManager lockMan;
    public ServerSocket serverSocket;
    public String host;
    public int portNumber;
    public int numClients;
    public static boolean transactionView = true;
    //Creates a server socket

    public TransactionServer(String host, int port)
    {
        this.host = host;
        this.portNumber = port;

        transMan = new TransactionManager();
        
        //Try to read out properties and pass them to the managers
        int numAccounts = 3;
        int initialBal = 100;
        boolean locking = true;
        try
        {
            PropertyHandler propHand = new PropertyHandler("General.properties");
            numAccounts = Integer.parseInt(propHand.getProperty("NUMBER_ACCOUNTS"));
            initialBal = Integer.parseInt(propHand.getProperty("INITIAL_BALANCE"));
            locking = Boolean.parseBoolean(propHand.getProperty("APPLY_LOCKING"));
            numClients = Integer.parseInt(propHand.getProperty("NUMBER_CLIENTS"));
            
        } catch (IOException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Property file called properties.txt failed to load, switch to defaults");
        }
        
        accMan = new AccountManager(numAccounts, initialBal);
        lockMan = new LockManager(locking); 
    }
    
    @Override
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Unable to connect to the server");
        }

        int handledClients = 0;


        int initialSum = accMan.getBalanceSum();
        System.out.println( "Initial total balance: " + initialSum );

        //Stays open forever
        while ( handledClients < numClients )
        {
            //Waits for proxy ==> .accept()
            System.out.println("Waiting for connections");
            Socket client = null;
            try
            {
                client = serverSocket.accept();
            } catch (IOException ex)
            {
                Logger.getLogger(TransactionServer.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Server failed to accept client.");
                continue;
            }
            System.out.println("Connection Established");

            //Got a client, give them to a new worker thread and go back to waiting for a new client
            transMan.runTransaction(client);
            System.out.println("Transaction Running");
            if(client.isClosed())
            {
                handledClients++;
            }
        }
        
        ArrayList<Transaction> transactions = transMan.getTransactions();
        int numTrans = transactions.size();
        System.out.println( "Total Transactions: " + numTrans );
        /**/
        boolean transRemaining = true;
        while(transRemaining)
        {
            transRemaining = false;
            for(Transaction tran : transactions)
            {
                if(!tran.getState())
                    {
                        transRemaining = true;
                    }
            
            }
        }
        
        for(Transaction tran : transactions)
            {
                System.out.println(tran.getLog());
            }
        

        int endingSum = accMan.getBalanceSum();
        System.out.println( "Ending total balance: " + endingSum );
        System.out.println( "Total money lost in the ether: " 
            + Integer.toString(endingSum - initialSum ));
        
        
        try
        {
            serverSocket.close();
        } catch (IOException ex)
        {
            Logger.getLogger(TransactionServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
