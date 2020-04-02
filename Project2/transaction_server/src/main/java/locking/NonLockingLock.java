/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locking;

import accounts.Account;
import transaction.Transaction;

/**
 * A lock that doesn't actually do any locking. 
 * This is useful when it is desirable to demonstrate the 
 * system when no locking is used.
 * @author zane
 */
public class NonLockingLock implements Lock, LockType
{
    Account dummy = new Account(0, 0);
    /**
     * "Acquire" a lock--does nothing.
     */
    @Override
    public synchronized void acquire( Transaction trans, LockMode aLockType )
    {
    }
    
    public Account getAcc()
    {
        return dummy;
    }

    public void setAcc( Account account )
    {

    }


    /**
     * Release the lock for transaction. -- Does nothing.
     * @param trans 
     */
    @Override
    public synchronized void release(Transaction trans)
    {
    }
    
    /**
     * Determine whether there is a conflict on this lock.
     * Because this lock doesn't actually lock anything,
     * no conflict is possible.
     * @return False, because no conflict is possible when no locking is done.
     */
    @Override
	public boolean isConflict( Transaction trans, 
                               LockMode lockType 
                             )
    {
        return false;
    }
    

    /**
     * Return the type of lock this is.
     * @return lockType.NON_LOCKING_LOCK
     */
    public lockType getType()
    {
	    return LockType.lockType.NON_LOCKING_LOCK;
    }

    @Override
    public boolean isEmpty()
    {
        return true;
    }

    /**
     * Determine whether this lock is held by a transaction.
     * @param trans The transaction to test.
     * @return False, because this lock doesn't lock and thus can't be held.
     */
    public synchronized boolean isHeldBy( Transaction trans )
    {
        return false;
    }

    /**
     * Get the mode of the lock.
     * @return EMPTY lock mode.
     */
    public LockMode getMode()
    {
        return LockMode.EMPTY;
    }

}
