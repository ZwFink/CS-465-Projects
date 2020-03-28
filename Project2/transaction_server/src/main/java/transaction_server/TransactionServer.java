package transaction_server;

import accounts.AccountManager;
import locking.LockManager;
import transaction.TransactionManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This server handles the requests from the proxy server
 * Utilizes the following
 *      AccountManager
 *      TransactionManager
 *      LockManager
 * @author caleb
 */
public class TransactionServer 
{
    public static TransactionManager transMan;
    public static AccountManager accMan;
    public static LockManager lockMan;
    //Creates a server socket
    //Waits for proxy ==> .accept()
    //Create input and output streams
        //Handles transaction //THREADS
            // Calls a transaction manager
        //Closes ONLY when client says to
    
    // option when locking isnt used. 
    
    	//Simple run while true 
		//Run trans accept socket
		//Transmanager.runtrans(socket accept)
    public static boolean transactionView = true;

    
    
}
