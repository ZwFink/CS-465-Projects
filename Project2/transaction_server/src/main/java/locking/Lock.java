package locking;

import java.util.Vector;
import transaction.TransID;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Handles Low Level Locking
 * @author caleb
 */
public class Lock 
{
    private LockType lockType;
    private Vector holders;
    private Object object;
    //Has wait and notify
    
    //has function "aquire"
    public synchronized void aquire(TransID trans, LockType aLockType)
    {
        //while another transaction holds the lock in confilciting mode
        while(false /*STUB*/)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
            }
        }
        
        if(holders.isEmpty())
        {
            holders.addElement(trans);
            lockType = aLockType;
        } else if(true /*STUB*/) //if another transaction holds a lock then share it
                {
                }
    }
    
    public synchronized void release(TransID trans)
    {
        holders.removeElement(trans);
        //set LockType to None
        notifyAll();
    }
}
