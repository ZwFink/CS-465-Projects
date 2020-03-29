package locking;

import java.util.ArrayList;
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
public class LockingLock implements Lock, LockType
{    
    private LockMode lockType = LockMode.EMPTY;
    private ArrayList holders;
    private Object object;
    

    //has function "aquire"
    public synchronized void acquire(Transaction trans, LockMode aLockType)
    {
        //while another transaction holds the lock in confilciting mode
        while(aLockType != LockMode.EMPTY /*STUB*/)
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
            holders.add(trans);
            lockType = aLockType;
        } else if(true /*STUB*/) //if another transaction holds a lock then share it
                {
                }
    }
    
    public synchronized void release(Transaction trans)
    {
        holders.remove(trans);
        //set LockMode to None
        if(holders.isEmpty())
        {
            lockType = LockMode.EMPTY;
        }
        notifyAll();
    }
    
    //Checks if the object is in confict due to lock
    public synchronized boolean isConflict()
    {
        return false;//STUB
    }

    public lockType getType()
    {
	return LockType.lockType.LOCKING_LOCK;	    
    }
}
