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
 * @author caleb, kenny
 */
public class Transaction 
{
    private int transID;
    private ArrayList<Lock> locks = null;
    private StringBuffer log = new StringBuffer("");
    private String transType = "READ"; //Default as read
    private int accountNum; //holds index to account being read/write
    private int value; //holds value to write to the account (direct write over)
    
    Transaction(int transID, String transType, int accountNum, int value)
    {
        this.transID = transID;
        this.locks = new ArrayList();
        this.transType = transType;
        this.accountNum = accountNum;
        this.value = value;
    }
    
    public int getID()
    {
        return transID;
    }
    
    public ArrayList<Lock> getLocks()
    {
        return locks;
    }
    
    public void addLock(Lock lock)
    {
        locks.add(lock);
    }
    
    public void log (String logString)
    {
        log.append("\n").append(logString);
        
        if (!TransactionServer.transactionView)
        {
            System.out.println("Transaction # " + this.getID() + " " + logString);
        }
     }
     
     public StringBuffer getLog() 
     {
         return log;
     }
     
     public int getValue() 
     {
         return value;
     }
     
     public int getAccount() 
     {
         return accountNum;
     }
     
     public String getType() 
     {
         return transType;
     }
                   
 }
    // note from otte
        // make transaction a bit more elaborate attach messages to the transactions
        // put comments with transactions themselves
    
    //Holds a reference to the account it wants to perform a transaction
    //Defines itself as Read OR Write
    //POSSIBILITY ==> Holds its own locks, suggested by Otte

