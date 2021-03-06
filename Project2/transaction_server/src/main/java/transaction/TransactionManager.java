/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import accounts.AccountManager;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import locking.LockManager;
import transaction.comm.Message;
import transaction_server.TransactionServer;

/**
 * A transaction manager that handles a transaction
 * from beginning to end.
 * @author caleb, kenny
 */
public class TransactionManager
{

    /**
     * A list of transactions that are managed by this.
     */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Return the transactions that have been managed up to this point.
     * @return the list of transactions that have been managed up to this point.
     * @note this method is not synchronized.
     */
    public ArrayList<Transaction> getTransactions()
    {
        return transactions;
    }

    /**
     * Run a transaction for a client.  
     * @param client A socket that is connected to a client
     */
    public void runTransaction(Socket client)
    {
        (new TransactionManagerWorker(client)).start();
    }

    /**
     * A worker thread that manages the message operations of a 
     * transaction.
     */
    public class TransactionManagerWorker extends Thread
    {

        public AccountManager accMan;
        public LockManager lockMan;

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
            accMan = TransactionServer.accMan;
            lockMan = TransactionServer.lockMan;
        }

        @Override
        public void run()
        {
            try
            {
                //get input and output streams
                writeTo = new ObjectOutputStream(client.getOutputStream());
                readFrom = new ObjectInputStream(client.getInputStream());
                
            } catch (IOException e)
            {
                System.out.println("transaction failed");
                e.printStackTrace();
                System.exit(1);
            }

            while (activeTrans)
            {
                try
                {
                    message = (Message) readFrom.readObject();
                } catch (IOException | ClassNotFoundException e)
                {
                    e.printStackTrace();
                    System.out.println("Transaction could not be read");
                    try
                    {
                        client.close();
                        readFrom.close();
                        writeTo.close();
                    } catch (IOException ex)
                    {
                        Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    activeTrans = false;
                    //System.exit(1);
                    continue;
                }

                Object[] content; // Object list for the content of the msg being parsed

                switch (message.getType())
                {
                    case OPEN_TRANSACTION:
			    //A new transaction was created so this case should parse it
                        
			synchronized (transactions)
                        {
                            //Create a new BLANK transaction to be filled out later
                            Transaction newTransaction = new Transaction(transactions.size(),
                                (int) message.getContent()[ 0 ] );
                            transactions.add(newTransaction);
                            transaction = newTransaction;
                        }

                try
                {
                    writeTo.writeObject( transaction.getID() );
                }
                catch( IOException e )
                {
                    e.printStackTrace();
                }

                transaction.log("Open transaction #" + transaction.getID());
                
                break;

                    case CLOSE_TRANSACTION:

                        synchronized(transactions)
                        {
                            TransactionServer.lockMan.unsetLock(transaction);
                            transaction.finish();
                        }

                        try
                        {
                            writeTo.writeObject(0);
                            readFrom.close();
                            writeTo.close();
                            client.close();
                            activeTrans = false;
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                            System.out.println("CLOSE TRANSACTION ERROR");
                        }

                        transaction.log("Close transaction #" + transaction.getID());

                        if (TransactionServer.transactionView)
                        {
                            System.out.println(transaction.getLog());
                        }

                        break;

                    case READ_REQUEST:
                        content = message.getContent();
                        accountNumber = ((Integer) content[0]);
                        transaction.log("READ_REQUEST -> account # " + accountNumber + ", transaction " + transaction.getID());
                        transaction.setAccount( accountNumber );
                        transaction.setType( "READ" );
                        balance = TransactionServer.accMan.handleTrans(transaction);

                        try
                        {
                            writeTo.writeObject(balance);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                            System.out.println("READ REQUEST ERROR");
                        }

                        break;

                    case WRITE_REQUEST:

                        content = message.getContent();
                        transaction.setType("WRITE");
                        accountNumber = ((Integer) content[0]);
                        transaction.setAccount( accountNumber );
                        balance = ((Integer) content[1]);
                        transaction.setValue( balance );
                        transaction.log("Write Request account " + accountNumber 
                        + " writing " + balance );
                        balance = TransactionServer.accMan.handleTrans(transaction);
                        try
                        {
                            writeTo.writeObject(balance);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                            System.out.println("READ REQUEST ERROR");
                        }

                        break;

                    default:
                        System.out.println("Warning message type not implemented");

                }
            }
        }

    }
}