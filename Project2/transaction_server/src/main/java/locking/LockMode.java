/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locking;

/**
 *
 * @author caleb, kenny
 */
public enum LockType
{
    // need three different types of locks
    // one for no lock
    EMPTY,
    
    // one for write lock
    WRITE,
    
    // one for read lock
    READ
    
}
