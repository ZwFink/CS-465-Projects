/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locking;

/**
 * A factory to produce locks on-demand.
 * @author zane
 */
public class LockFactory 
{
	/**
	 * The type of lock that will be produced by this factory.
	 */
	private LockType.lockType productionLockType;

	/**
	 * Create a factory that will produce a certain type of lock.
	 * @param lockTypeToProduce The type of lock that will be produced
	 *        by this factory.
	 */
	public LockFactory( LockType.lockType lockTypeToProduce )
	{
		this.productionLockType = lockTypeToProduce;	
	}

	/**
	 * Retrieve a lock of the type that was set by this factory.
	 * @return An appropriate lock, as determined by the initialization of 
	 *         the factory.
	 */
	public LockType getLock()
	{
		if( this.productionLockType == LockType.lockType.LOCKING_LOCK )
		{
			return new LockingLock();	
		}
		return new NonLockingLock();
	}
	
}
