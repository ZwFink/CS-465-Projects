package locking;

import accounts.Account;
import java.util.HashMap;
import java.util.Map;
import transaction.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Handles High Level locking
 * @author caleb
 */
public class LockManager 
{
    private HashMap<Account,Lock> locks;

    private LockFactory lockCreator;
    //ALTERNATIVE ==> Have each transaction hold its own locks
    // pseudo code in the book
	
    //Handles the demands for a lock and their release and aquisition
    
    /**
     * Constructor of LockManager that determines whether 
     * locking should be applied or not.
     * @param applyLocking Boolean specifying whether locking should be 
     *        applied to account access or not. If true, any read/write 
     *        operations will acquire non-conflicting exclusive access.
     *        Otherwise, no locking will be done.
     */
    public LockManager( boolean applyLocking ) 
    {
        locks = new HashMap();

        if( applyLocking )
        {
            this.lockCreator 
                = new LockFactory( LockType.lockType.LOCKING_LOCK );
        }
        else
        {
             this.lockCreator 
                = new LockFactory( LockType.lockType.NON_LOCKING_LOCK );
        }
        
    }
    /*
    High level holds 
			Set lock / release lock
    */
    public void setLock(Object object, Transaction trans, LockMode lockType)
    {
        Lock foundLock = null;
        synchronized (this) 
        {
            // find lock associated with the object
            // if there isn't one  create it and add it to the hashtable
        }
        foundLock.acquire(trans, lockType);
    }
    
    // Used for close transaction 
    public synchronized void unLock(Transaction trans)
    {
        Enumeration e = lockList.elements();
        while(e.hasMoreElements())
        {
            Lock aLock = (Lock) (e.nextElement());
            if(true /*STUB trans is a holder of this lock */)
            {
                aLock.release(trans);
            }
        }
    }
}
