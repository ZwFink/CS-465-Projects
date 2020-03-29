/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locking;

/**
 * Describes the lock type of a lock. 
 * A lock can be either two things:
 * - locking: Operations related to concurrency control result in 
 *            the locking/unlocking of access to resources. 
 * - Non-locking: Operations related to concurrency control
 *   result in no-operations
 * @author zane
 */
public interface LockType 
{
	public enum lockType
	{
		LOCKING_LOCK,
		NON_LOCKING_LOCK
	}
	public lockType getType();
}
