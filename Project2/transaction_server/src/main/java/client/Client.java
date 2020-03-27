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
public class Client extends thread
{
    //save host and port number as variables
    string host = "127.0.0.1";
    private final int port = 2080;
    private int index = 0;
    
    //hold the current number of transactions
    private int numTrans = 0;
    
    // Opens a proxy server
    @Override
    public void run()
    {
        // get the transaction id
        for (index = 0; index < numTrans; index++)
        
        new Tread()
        {
            @Override
            public void run()
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
                fromBalance = balance - amount;
                transaction.write(accountFrom, fromBalance);
                
                balance = transaction.read(accountTo);
                toBalance = balance + amount;
                transaction.write(accountTo, toBalance);
                
                transaction.closeTransaction();
                
                System.out.println("Transaction # " + transID + " Completed ");
            
        }
     } .start();
        
        // create loop to handle # of transactions
  
  
  
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
