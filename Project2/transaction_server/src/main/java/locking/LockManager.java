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
                foundLock.setItem( trans.getAccount() );

                locks.put( account, foundLock );

                if( lockCreator.isLocking() )
                {
                    trans.log( "[LockManager.setLock] " +
                        toUpperCase(
                            lockModeToString( lockType )
                        ) +
                         " Lock created for account #" +
                        + account.getNumber() 
                    );
                }
                
            }
        }

        foundLock.acquire( trans, lockType );
        trans.addLock( foundLock );
    }
    
    // Used for close transaction 
    public void unsetLock( Transaction trans )
    {
        
        synchronized(this)
        {
        for( Lock l : trans.getLocks() )
        {
            l.release( trans );
        }

        }
        trans.resetLocks();
    }

    /**
     * Turn a lock mode into a string equivalent
     * @param mode The lock mode tog et a string for.
     * @return A string representation of the lock mode.
     */
    private String lockModeToString( LockMode mode )
    {
        if( mode == LockMode.EMPTY )
        {
            return "empty";
        }
        else if( mode == LockMode.READ )
        {
            return "read";
        }
        return "write";
    }

    private String toUpperCase( String in )
    {
        return in.substring(0,1).toUpperCase() + in.substring(1).toLowerCase();
    }
}
