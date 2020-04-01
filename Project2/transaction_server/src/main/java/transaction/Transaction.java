/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import java.util.ArrayList;
import locking.Lock;
import transaction_server.TransactionServer;

/**
 * Holds low level values
 * pertinent to a transaction.
 * @author caleb, kenny
 */
public class Transaction 
{
    /**
     * The unique identifier of this transaction.
     */
    private int transID;

    /**
     * The locks that are held by this transaction.
     */
    private ArrayList<Lock> locks = null;

    /**
     * A log containing messages that have been output as part of this 
     * transaction's operations.
     */
    private StringBuffer log = new StringBuffer("");

    /**
     * The type of transaction this is.
     */
    private String transType = "READ"; //Default as read
    private int accountNum; //holds index to account being read/write
    private int value; //holds value to write to the account (direct write over)
    private boolean isDone;
    
    /**
     * Argument constructor for the transaction.
     * @param transID The ID for this transaction.
     * @param transType The type of transaction.
     * @param accountNum The number of the account being modified by the 
     * transaction.
     */
    public Transaction(int transID, String transType, int accountNum, int value)
    {
        this.transID = transID;
        this.locks = new ArrayList();
        this.transType = transType;
        this.accountNum = accountNum;
        this.value = value;
        isDone = false;
    }

    public Transaction( int transID, int accountNum )
    {
        this.accountNum = accountNum;
        this.transID = transID;
        this.locks = new ArrayList();
    }

    /**
     * Constructor with just a transaction ID.
     * @param transID The ID of this transaction.
     */
    public Transaction(int transID)
    {
        this.transID = transID;
        this.locks = new ArrayList();
    }

    public void resetLocks()
    {
        this.locks = new ArrayList();
    }
    
    /**
     * Retrieve this transaction's ID.
     * @return the ID of this transaction
     */
    public int getID()
    {
        return transID;
    }
    
    /**
     * 
     * @return all of the locks held by this transaction
     */
    public ArrayList<Lock> getLocks()
    {
        return locks;
    }
    
    /**
     * Add a lock to this transaction.
     * @param lock The lock to add.
     */
    public void addLock(Lock lock)
    {
        locks.add(lock);
    }
    
    /**
     * Add a string to the log
     * @param logString The string to add.
     */
    public void log (String logString)
    {
//        log.append("\n").append(logString);
        
//        if (!TransactionServer.transactionView)
//        {
//            System.out.println("Transaction # " + this.getID() + " " + logString);
//        }
        System.out.println( logString );
     }
     
    /**
     * Get this transaction's log.
     * @return The log for this transaction.
     */
     public StringBuffer getLog() 
     {
         return log;
     }
     
     public int getValue() 
     {
         return value;
     }
     public void setValue(int value) 
     {
         this.value = value;
     }
     
     public int getAccount() 
     {
         return accountNum;
     }
     public void setAccount(int accountNum) 
     {
         this.accountNum = accountNum;
     }
     
     public String getType() 
     {
         return transType;
     }
     public void setType(String transType) 
     {
         this.transType = transType;
     }
     
     public void finish() 
     {
         isDone = true;
     }
     public boolean getState() 
     {
         return isDone;
     }

     @Override
     public boolean equals( Object other )
     {
         return this.transID == ((Transaction)other).transID;
     }

     @Override 
     public int hashCode()
     {
         return Integer.hashCode( this.transID );
     }
                   
 }
