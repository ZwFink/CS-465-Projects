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
     * Count of the number of transactions.
     */
    private static int transCounter = 0;

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
            System.out.println( "Got input stream");

            while (activeTrans)
            {
                try
                {
                    message = (Message) readFrom.readObject();
                } catch (IOException | ClassNotFoundException e)
                {
                    System.out.println("Transaction could not be read");
                    System.exit(1);
                }

                Object[] content; // Object list for the content of the msg being parsed

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
                        } catch (IOException e)
                        {
                            System.out.println("OPEN TRANSACTION ERROR");
                        }

                        transaction.log("Open transaction #" + transaction.getID());

                        break;

                    case CLOSE_TRANSACTION:

                        TransactionServer.lockMan.unsetLock(transaction);
                        transactions.remove(transaction);

                        try
                        {
                            readFrom.close();
                            writeTo.close();
                            client.close();
                            activeTrans = false;
                        } catch (IOException e)
                        {
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
                        transaction.log("READ_REQUEST -> account # " + accountNumber + "->" + transaction.getID());
                        balance = TransactionServer.accMan.handleTrans(transaction);

                        try
                        {
                            writeTo.writeObject(balance);
                        } catch (IOException e)
                        {
                            System.out.println("READ REQUEST ERROR");
                        }

                        transaction.log("READ_REQUEST " + accountNumber);

                        break;

                    case WRITE_REQUEST:

                        content = message.getContent();
                        accountNumber = ((Integer) content[0]);
                        balance = ((Integer) content[1]);
                        transaction.log("Write Request " + accountNumber);
                        balance = TransactionServer.accMan.handleTrans(transaction);

                        try
                        {
                            writeTo.writeObject(balance);
                        } catch (IOException e)
                        {
                            System.out.println("WRITE REQUEST ERROR");
                        }

                        transaction.log("WRITE REQUEST " + accountNumber);

                        break;

                    default:
                        System.out.println("Warning message types not implemented");

                }
            }
        }

    }
}