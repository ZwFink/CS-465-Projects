/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

/**
 * Holds low level values
 * @author caleb
 */
public class Transaction 
{
    int transID;
    ArrayList<Lock> locks = null;
    
    StringBuffer log = new StringBuffer("");
    
    Transaction(int transID)
    {
        this.transID = transID;
        this.locks = new ArrayList();
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
        log.append("\n).append(logString);
        
        if (!TransactionSErver.transactionView)
        {
            System.out.println("Transaction # " + this.getID() + " " + logString);
        }
     }
     
     public StringBuffer getLog() 
     {
         return log;
     }
                   
 }
                       
    // note from otte
        // make transaction a bit more elaborate attach messages to the transactions
        // put comments with transactions themselves
    
    //Holds a reference to the account it wants to perform a transaction
    //Defines itself as Read OR Write
    //POSSIBILITY ==> Holds its own locks, suggested by Otte

