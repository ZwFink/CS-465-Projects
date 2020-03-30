package locking;

import accounts.Account;
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
        int accNum = trans.getAccount();
        trans.log( "[LockingLock.acquire] Attempt to acquire a " + 
            lockModeToString( lockingMode ) +
            " lock for account # " + accNum + "."
        );

        //while another transaction holds the lock in confilciting mode
        while( isConflict( trans, lockingMode ) )
        {
            try
            {
                trans.log( "[LockingLock.acquire] Conflict in acquiring a " +
                    lockModeToString( lockingMode ) +
                    " lock for account # " + accNum + "."
                );
                wait();
                trans.log( "[LockingLock.acquire] Notify for lock for account # " + accNum + "."
                );
            }
            catch(InterruptedException e)
            {
            }
        }
        this.lockType = lockingMode;
        
        if(holders.isEmpty())
        {

            trans.log( "[LockingLock.acquire] " + 
                "Successfully added a " +
                lockModeToString( lockingMode ) +
                " lock for account # " + accNum + 
                " as the sole owner."
            );

            holders.add(trans);
            lockType = lockingMode;

        } 
        else if( !holders.isEmpty() )
        {
            if( !this.isHeldBy( trans ) )
            {
                trans.log( "[LockingLock.acquire] " +
                    "Successfully added a " +
                    lockModeToString( lockingMode ) +
                    " lock for account # " + accNum +
                    "."
                );

                holders.add( trans );
            }
            // the lock needs to be promoted 
            else if( this.isHeldBy( trans )
                     && this.lockType == LockMode.READ
                     && lockingMode == LockMode.WRITE 
                   )
            {
                trans.log( "[LockingLock.acquire] " +
                    "Attempting to promote a " +
                    lockModeToString( lockingMode ) +
                    " lock for account # " + accNum +
                    "."
                );
                promote( trans ); 
            }
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
        
        trans.log( "[LockingLock.release] " +
            "Removing a " +
            lockModeToString( this.lockType ) +
            " lock for account # " + trans.getAccount() +
            "."
        );
        
        //set LockMode to None
        if(holders.isEmpty())
        {
            lockType = LockMode.EMPTY;
        }
        notifyAll();
    }
    
    /**
     * Determine whether this lock is in conflict with another.
     * @param t The transaction that is attempting to 
     * @return True if a read-write or write-write conflict will occur by 
     *         granting the lock, false otherwise (read-write)
     */
    @Override
    public boolean isConflict( Transaction t, 
                               LockMode lockType
    )
    {
        // read-read
        if( holders.isEmpty())
        {

        t.log( "[LockingLock.isConflict] " +
            "No conflict found for " +
            "account # " + t.getAccount() +
            "." 
        );
            return false;
        }
        else if( ( this.holders.size() == 1 && this.holders.contains( t )
            && lockType == LockMode.WRITE && this.lockType == LockMode.READ)
            )
        {
            t.log( "[LockingLock.isConflict] " +
                "No conflict found for lock promotion" +
                "account # " + t.getAccount() +
                "."
            );
            return false;
            
        }
        else if(lockType == LockMode.READ && this.lockType == LockMode.READ )
        {
            t.log( "[LockingLock.isConflict] " +
                "No conflict found for read on read" +
                "account # " + t.getAccount() +
                "."
            );
            return false;
            
        }

        t.log( "[LockingLock.isConflict] " +
            "Conflict found for " +
            "account # " + t.getAccount() +
            "." 
        );

        // covers write-write and write-read cases
        return true;
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
     * Otherwise, this method sets the lock type accordingly 
     * and attempts to acquire the lock.
     */
    public void promote( Transaction trans ) 
    {
        if( this.lockType == LockMode.READ )
        {
            this.acquire( trans, LockMode.WRITE );
        }
    }

    /**
     * Return the mode of this lock.
     * @return Either READ, WRITE, or EMPTY
     */
    public LockMode getMode()
    {
        return this.lockType;
    }

    public Object getItem()
    {
        return this.object;
    }

    public void setItem( Object newObject )
    {
        this.object = newObject;
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

}
