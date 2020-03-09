package locking;

import java.util.Enumeration;
import java.util.Hashtable;
import transaction.TransID;

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
    private Hashtable lockList;
    //ALTERNATIVE ==> Have each transaction hold its own locks
    // pseudo code in the book
	
    //Handles the demands for a lock and their release and aquisition
    
    /*
    High level holds 
			Set lock / release lock
    */
    public void setLock(Object object, TransID trans, LockType lockType)
    {
        Lock foundLock = null;
        synchronized (this) 
        {
            // find lock associated with the object
            // if there isn't one  create it and add it to the hashtable
        }
        foundLock.aquire(trans, lockType);
    }
    
    // Used for close transaction 
    public synchronized void unLock(TransID trans)
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