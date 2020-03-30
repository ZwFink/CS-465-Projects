package locking;

import java.util.ArrayList;
import transaction.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A class that implements acquisition and release of a lock
 * for an object. 
 * @author caleb
 */
public class LockingLock implements Lock, LockType
{    

    /**
     * The current mode of this lock.
     */
    private LockMode lockType = LockMode.EMPTY;

    /**
     * The holders of this lock.
     */
    private ArrayList holders;

    /**
     * The object that is being held by the lock.
     */
    private Object object;

    /**
     * Default initialization of the lock.
     */
    public LockingLock()
    {
        lockType = LockMode.EMPTY;
        holders = new ArrayList();
        object = null;
    }
    

    /**
     * Acquire the lock in a way such that conflicting operations 
     * cannot be done.
     * @param trans The transaction that is acquiring a lock.
     * @param aLockType The type of lock that is requested.
     * @note This method will block until the lock can be acquired. 
     */
    @Override
    public synchronized void acquire( Transaction trans, LockMode lockingMode )
    {
        //while another transaction holds the lock in confilciting mode
        while( isConflict( trans, lockingMode ) )
            {
                
            }
        while( lockingMode != LockMode.EMPTY /*STUB*/)
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
            lockType = lockingMode;
        } else if(true /*STUB*/) //if another transaction holds a lock then share it
                {
                }
    }
    
    /**
     * Release the lock from the object that is being held
     * @param trans The transaction that requested the lock.
     */
    @Override
    public synchronized void release( Transaction trans )
    {
        holders.remove(trans);
        //set LockMode to None
        if(holders.isEmpty())
        {
            lockType = LockMode.EMPTY;
        }
        notifyAll();
    }
    
    /**
     * Determine whether this lock is in conflict with another.
     * @return 
     */
    @Override
    public synchronized boolean isConflict( Transaction t, 
                                            LockMode lockType
    )
    {
        return false;//STUB
    }

    /**
     * Determine whether this lock is held by a transaction. 
     * @param trans the transaction to test
     * @return True if trans is in this lock's list of holders,
     *         false otherwise.
     */
    public synchronized boolean isHeldBy( Transaction trans )
    {
        return holders.contains( trans ); 
    }

    /**
     * Get the type of this lock.
     * @return lockType.LOCKING_LOCK
     */
    @Override
    public lockType getType()
    {
        return LockType.lockType.LOCKING_LOCK;	    
    }

    /**
     * Promote this from a read lock to a write lock.
     * If this is not a read lock, this method is a noop.
     */
    public void promote()
    {
        if( this.lockType == LockMode.READ )
        {
            this.lockType = LockMode.WRITE; 
        }
    }
}
