package locking;

import java.util.Vector;
import transaction.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Handles Low Level Locking
 * @author caleb
 */
//Has wait and notify
public class Lock 
{    
    private LockType lockType = LockType.EMPTY;
    private Vector holders;
    private Object object;
    
    
    //has function "aquire"
    public synchronized void aquire(Transaction trans, LockType aLockType)
    {
        //while another transaction holds the lock in confilciting mode
        while(false /*STUB*/)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
            }
        }
        
        if(holders.isEmpty())
        {
            holders.addElement(trans);
            lockType = aLockType;
        } else if(true /*STUB*/) //if another transaction holds a lock then share it
                {
                }
    }
    
    public synchronized void release(Transaction trans)
    {
        holders.removeElement(trans);
        //set LockType to None
        if(holders.isEmpty())
        {
            lockType = LockType.EMPTY;
        }
        notifyAll();
    }
    
    //Checks if the object is in confict due to lock
    public synchronized boolean isConflict()
    {
        return false;//STUB
    }
}
