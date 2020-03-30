package locking;

import accounts.Account;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaction.Transaction;
import utils.PropertyHandler;

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
     * 
     */
    public LockManager(boolean applyLocking) 
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
    public void setLock( Account account, Transaction trans, LockMode lockType)
    {
        Lock foundLock = null;
        synchronized (this) 
        {
            foundLock = locks.get( account );

            if( foundLock == null )
            {
                foundLock = lockCreator.getLock();
                locks.put( account, foundLock );
            }
        }

        foundLock.acquire( trans, lockType );
    }
    
    // Used for close transaction 
    public synchronized void unsetLock( Transaction trans )
    {
        
        for( Map.Entry<Account,Lock> entry : locks.entrySet() )
        {
            Lock aLock = entry.getValue();

            if( aLock.isHeldBy( trans ) )
            {
                aLock.release(trans);
            }
        }
    }
}
