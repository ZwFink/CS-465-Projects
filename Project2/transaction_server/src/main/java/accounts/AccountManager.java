/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import locking.LockType;
import transaction.Transaction;

/**
 *
 * @author caleb, kennny
 */
public class AccountManager 
{
	// need input and output streams to handle reads/writes
	DataInputStream read = null;
	DataOutputStream write = null;
	int numberOfAccounts = 0;
	int transID;
	LockType lockType; 
        private static final ArrayList<Account> accountList = new ArrayList<>();
	
	
      /**
	* Account manager preforms all the necessary handling for the account class
	* 
	*
	*/
	public AccountManager(ArrayList<Account> accountList)
	{
		
            // only implements read and write operations

            // Works on the accounts initializes full set of accounts needed

            //Provides access to accounts give number -> get account

            //Read – Write

            //Write on → tentative data/committed data
                                    //Ex which accounts were read from


            //Shields all the accounts takes high lever read write requests
                //translates to how read and write are being done.

            //Aware of locking needs to try to acquire a lock
                    //Either in the read or write

            //Write – accnum,  Tran transaction, balance
               // Variables

                    //New object with account number
                    //Lock
                    //Get balance

	}
    
}
