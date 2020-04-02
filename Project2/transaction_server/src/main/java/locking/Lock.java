/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locking;

import accounts.Account;
import transaction.Transaction;

/**
 * An interface that different types of locks must implement. 
 * Provides methods for locking, unlocking, and conflict management.
 * @author zane
 */
public interface Lock 
{
	/**
	 * Acquire a lock for a transaction. 
	 * @param trans the transaction that is requesting the lock.
	 * @param aLockType The type of lock the transaction is requesting.
	 */
	public void acquire( Transaction trans, LockMode aLockType );

    public void setAcc( Account account );

	/**
	 * Release all locks that are held by a transaction.
	 * @param trans The transaction whose locks should be released.
	 */
	public void release( Transaction trans );

	/**
	 * Determine whether 
	 * @return 
	 */
	public boolean isConflict( Transaction trans, 
                               LockMode lockingMode 
                             );


    public boolean isHeldBy( Transaction trans );

    /**
     * Get the mode a lock is currently in.
     * @return READ, WRITE, or EMPTY.
     */
    public LockMode getMode();

    /**
     * Return the item held by the lock.
     * @return The item held by the lock, if any.
     * Otherwise, null.
     */
    public Account getAcc();

    public boolean isEmpty();
	
}
