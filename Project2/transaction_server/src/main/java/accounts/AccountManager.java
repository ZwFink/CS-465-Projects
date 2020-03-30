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
import locking.LockMode;
import transaction.Transaction;

/**
 *
 * @author caleb, kennny
 */
public class AccountManager 
{
	int numberOfAccounts = 0;
	int transID;
        private static ArrayList<Account> accountList;
	
	
      /**
	* Account manager preforms all the necessary handling for the account class
	* Works on the accounts initializes full set of accounts needed
	* Provides access to accounts give number -> get account
        * Shields all the accounts takes high level read write requests
	*/
	public AccountManager(ArrayList<Account> accountList)
	{
            accountList = new ArrayList<>();
            for(Account newAcc : accountList)
            {
                numberOfAccounts++;
                accountList.add(newAcc);
            }
	}
        
        //Constructor specific for preference file use
        public AccountManager(int accountAmount, int startBal)
	{
            accountList = new ArrayList<>();
            for(int i = 0; i < accountAmount; i++)
            {
                Account newAcc = new Account(startBal, numberOfAccounts);
                numberOfAccounts++;
                accountList.add(newAcc);
            }
	}
        
        public AccountManager()
	{
            ArrayList<Account> accountList = new ArrayList<>();
	}
        
        public void addAccount(int newBalance)
        {
            Account newAcc = new Account(newBalance, numberOfAccounts);
            numberOfAccounts++;
            accountList.add(newAcc);
        }
        
        public int handleTrans(Transaction trans)
        {
            if(trans.getType().equals("WRITE"))
            {
                accountList.get(trans.getAccount()).setBalance(trans.getValue());
                //Return the new balance set
                return accountList.get(trans.getAccount()).getBalance();
            }
            else //Treat any opther type as a READ
            {
                Account desiredAcc = accountList.get(trans.getAccount());
                return desiredAcc.getBalance();
            }
        }
    
}
