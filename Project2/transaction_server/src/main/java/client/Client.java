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
    private String serverHost;
    private int serverPort;
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
    
    public Client(String host,
            int port,
            String serverHost,
            int serverPort,
            int totalAccounts,
            int maxBalance,
            int randTransNum)
    {
        this.host = host;
        this.port = port;
        this.host = serverHost;
        this.port = serverPort;
        this.totalAccounts = totalAccounts;
        this.maxBalance = maxBalance;
        this.randTransNum = randTransNum;
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
        TransactionServerProxy transaction = new TransactionServerProxy(host, port, serverHost, serverPort);
        int transID = transaction.openTransaction();
                
        int accountFrom = (int) Math.floor(Math.random() * numberAccounts);
        int accountTo = (int) Math.floor(Math.random() * numberAccounts);

        while( accountTo == accountFrom )
        {
            accountFrom = (int) Math.floor(Math.random() * numberAccounts);
            accountTo = (int) Math.floor(Math.random() * numberAccounts);
        }
        int amount = (int) Math.ceil(Math.random() * initialBalance);
        int balance;
        System.out.println("\n transaction #" + transID + "transferring $" + amount + " from account #" + accountFrom + " to account #" + accountTo);
        balance = transaction.read(accountFrom);
        int fromBalance = balance - amount;
        transaction.write(accountFrom, fromBalance);
                
        balance = transaction.read(accountTo);
        int toBalance = balance + amount;
        transaction.write(accountTo, toBalance);
                
        transaction.closeTransaction();
    }
}