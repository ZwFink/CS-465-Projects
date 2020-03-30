package transaction_server;

import accounts.AccountManager;
import locking.LockManager;
import transaction.TransactionManager;
import java.io.*;
import java.util.*;
import java.nat.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This server handles the requests from the proxy server
 * Utilizes the following
 *      AccountManager
 *      TransactionManager
 *      LockManager
 * @author caleb, kenny
 */
public class TransactionServer 
{
    public static TransactionManager transMan;
    public static AccountManager accMan;
    public static LockManager lockMan;
    serverSocket serverSocket;
    public final int portNumber = 2080;
    public static boolean transactionView = true;
    //Creates a server socket
	
  public TransactionServer()
  {
    transMan = new TransactionManager();
    lockMan = new LockManager();
    accMan = new AccountManager();
   
    try
    {
	    serverSocket = new Serversocket(portNumber);
    }
	catch(IOExcepotion e)
	{
		System.out.println("Unable to connect to the server");
	}
	
		
    //Waits for proxy ==> .accept()
    public void run()
    {
	    while(true)
            System.out.println("Waiting for connections");
	    Socket socket = serverSocket.accept();
	    System.out.println("Connection Established");
	    (new ServerThread(socket)).start();
    //Create input and output streams
	
	readFrom = new ObjectInputStream(client.getInputStream());
	writeTo = new ObjectOutputStream(client.getOutputStream());
	    
	@Override
	public void run()
	{
		try
		{
		
	
        //Handles transaction //THREADS
            // Calls a transaction manager
        //Closes ONLY when client says to
    
    // option when locking isnt used. 
    
    	//Simple run while true 
		//Run trans accept socket
		//Transmanager.runtrans(socket accept)
   
    }

    public static boolean transactionView()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  

    
    
}
