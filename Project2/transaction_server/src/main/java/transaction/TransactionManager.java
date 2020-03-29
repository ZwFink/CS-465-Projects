/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import java.util.*;
import java.io.*;
import java.net.*;
import transaction.comm.Message;
import transaction.comm.MessageTypes;
import transaction_server.TransactionServer;


/**
 *
 * @author caleb, kenny
 */
public class TransactionManager extends MessageType
{
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static int transCounter = 0;
    
    public ArrayList<Transaction> getTransactions()
    {
    	return transactions;
    }
    
    public void runTransaction(Socket client)
    {
    	(new TransactionManagerWorker(client)).start();
    }
    
    public class TransactionManagerWorker extends Thread
    {
    	Socket client = null;
	ObjectInputStream readFrom = null;
	ObjectOutputStream writeTo = null;
	Message message = null;
	
	Transaction transaction = null;
	int accountNumber = 0;
	int balance = 0;
	
	boolean activeTrans = true;
	
	private TransactionManagerWorker(Socket client)
	{
	   this.client = client;
	   
	   try
	   {
	   	readFrom = new ObjectInputStream(client.getInputStream());
		writeTo = new ObjectOutputStream(client.getOutputStream());
                
                //check for client input, if its the tansaction close then end this loop
                
                //Handle client requests using accountManager and LockManager
                
                //Write back to client results of transaction
                
                //Return to top of loop to wait for next client input
	   }
	   catch(IOException e)
	   {
	   	System.out.println("transaction failed");
		e.printStackTrace();
		System.exit(1);
	   }
	}
	
	@Override
	public void run()
	{
		while(activeTrans)
		{
			try
			{
			   message = (Message) readFrom.readObject();
			}
			catch (IOException | ClassNotFoundException e)
			{
			   System.out.println("Transaction could not be read");
			   System.exit(1);
			}
			
			switch (message.getType())
			{
			   case OPEN_TRANSACTION:
			   //A new transaction was created so this case should parse it
                               
			   synchronized (transactions)
			   {
                              transCounter++;
                              //Create a new BLANK transaction to be filled out later
                              Transaction newTransaction = new Transaction(transCounter);
			      transactions.add(newTransaction);
			   }
			   
			   try
			   {
			     writeTo.writeObject(transaction.getID());
			   }
			   catch(IOException e)
			   {
			      System.out.println("OPEN TRANSACTION ERROR");
			   }
			   
			   transaction.log("Open transaction #" + transaction.getID());
			   
			   break;
			   
	          case CLOSE_TRANSACTION:
		  	
			TransactionServer.lockManager.unlock(transaction);
			transactions.remove(transaction);
			
			try
			{
			   readFrom.close();
			   writeTo.close();
			   client.close();
			   activeTrans = false;
			}
			catch(IOException e)
			{
			   System.out.println("CLOSE TRANSACTION ERROR");
			}
			
			transaction.log("Close transaction #" + transaction.getID());
			
			if (TransactionServer.transactionView())
			{
			   System.out.println(transaction.getLog());
			}
			
			break;
			
		case READ_REQUEST:
		
			accountNumber = (Integer) message.getContent();
			transaction.log("READ_REQUEST -> account # " + accountNumber + "->" + transaction.getID());
			balance = TransactionServer.accountManager.read(accountNumber, transaction);
			
			try
			{
			  writeTo.writeObject((Integer) balance);
			}
			catch(IOException e)
			{
			  System.out.println("READ REQUEST ERROR");
			}
			
			transaction.log("READ_REQUEST" + accountNumber);
			
			break;
			
		case WRITE_REQUEST:
		
			Object[] content = (Object[]) message.getContent();
			accountNumber = ((Integer) content[0]);
			balance = ((Integer) content[1]);
			transaction.log("Write Request" + accountNumber);
			balance = TransactionServer.accountManager.write(accountNumber, transaction, balance);
			
			try
			{
			  writeTo.writeObject((Integer) balance);
			}
			
			catch(IOException e)
			{
			  System.out.println("WRITE REQUEST ERROR");
			}
			
			transaction.log("WRITE REQUEST" + accountNumber);
			
			break;
			
		default:
			System.out.println("Warning message types not implemented");
			
		
			
		
			
			
			 }
		}
	}
	
    // Handles a transaction by calling an Account Manager to handle alterations to the account
	
	// Threads respond to messages from a clients proxy
		// spawns off threads handles clients locking
    
    /*
    Third object does work in a semantical area deals with the transactions.  

Workers open up one connection then don’t shut it down makes it easier. 

		Request -> open connection and keep it open
		
		Reads and writes handled by transaction worker.
				Threads are used

				Transaction manager extends thread
					Socket
					Inputstream	outputstream
					Message
			
					Pass in client start the thread

Take care of read and write requests.
	Write creates new object
	Acc num
		Balance
	Trans log
		Balance
Try - catch

	Read
		Acc#
		Transaction
Try-catch

Finally close all the open sockets read,write,client, Boolean-false

    */
}
