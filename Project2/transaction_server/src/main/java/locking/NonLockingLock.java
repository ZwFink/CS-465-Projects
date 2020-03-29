/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locking;

import transaction.Transaction;

/**
 * A lock that doesn't actually do any locking. 
 * This is useful when it is desirable to demonstrate the 
 * system when no locking is used.
 * @author zane
 */
public class NonLockingLock implements Lock, LockType
{
	
    /**
     * "Acquire" a lock--does nothing.
     */
    @Override
    public synchronized void acquire( Transaction trans, LockMode aLockType )
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
    public synchronized boolean isConflict()
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
}