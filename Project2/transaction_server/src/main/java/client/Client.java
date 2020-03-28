package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Connects to the Proxy Server and issues commands
 * Commands: Open, Read, Write, Close
 * @author caleb, kenny
 */
public class Client extends Thread
{
    //save host and port number as variables
    private String host;
    private int port;
    private int totalAccounts;
    private int maxBalance;
    private int randTransNum;
    
    public Client()
    {
        host = "127.0.0.1";
        port = 2080;
        totalAccounts = 3;
        maxBalance = 100;
        randTransNum = 10;
    }
    
    // Opens a proxy server
    @Override
    public void run()
    {        
        for(int i = 0; i < randTransNum; i++)
        {
            randomReadWrite(totalAccounts, maxBalance);
        }
    }
        
    public void randomReadWrite(int numberAccounts, int initialBalance)
    {
        TransactionServerProxy transaction = new TransactionServerProxy(host, port);
        int transID = transaction.openTransaction();
        System.out.println("transaction number" + transID + " started");
                
        int accountFrom = (int) Math.floor(Math.random() * numberAccounts);
        int accountTo = (int) Math.floor(Math.random() * numberAccounts);
        int amount = (int) Math.ceil(Math.random() * initialBalance);
        int balance;
        System.out.println("\n transaction # " + transID + ", $ " + amount + " from " + accountFrom + " to " + accountTo);
                
        balance = transaction.read(accountFrom);
        int fromBalance = balance - amount;
        transaction.write(accountFrom, fromBalance);
                
        balance = transaction.read(accountTo);
        int toBalance = balance + amount;
        transaction.write(accountTo, toBalance);
                
        transaction.closeTransaction();
                
        System.out.println("Transaction # " + transID + " Completed ");
    }
}
  
  
  
/**  
        try
        {
            //connect to the server given the host and port number
            Socket socket = new Socket(host, port);
            
            //set up read and write streams
            ObjectInputStream read = new ObjectInputStream(socket.getInputStream);
            ObjectOutputStream write = new ObjectOutputStream(socket.getInputStream);
            
            // handle transactions & proper locks
                //reading -- viewing account
                //writing
                    //Depositing
                    //Withdrawling
        }
    }
    
    
        // Issues read and write commands
    // Closes the proxy server
}

**/
