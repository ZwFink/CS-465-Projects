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
import locking.LockManager;
import locking.LockMode;
import transaction.Transaction;
import transaction_server.TransactionServer;

/**
 *
 * @author caleb, kennny
 */
public class AccountManager
{

    int numberOfAccounts = 0;
    int transID;
    private static ArrayList<Account> accountList;
    private LockManager lockMan;

    /**
     * Account manager preforms all the necessary handling for the account class
     * Works on the accounts initializes full set of accounts needed Provides
     * access to accounts give number -> get account Shields all the accounts
     * takes high level read write requests
     */
    public AccountManager(ArrayList<Account> accountList)
    {
        accountList = new ArrayList<>();
        for (Account newAcc : accountList)
        {
            numberOfAccounts++;
            accountList.add(newAcc);
        }
        this.lockMan = TransactionServer.lockMan;
    }

    //Constructor specific for preference file use
    public AccountManager(int accountAmount, int startBal, LockManager lockMan)
    {
        accountList = new ArrayList<>();
        for (int i = 0; i < accountAmount; i++)
        {
            Account newAcc = new Account(startBal, numberOfAccounts);
            numberOfAccounts++;
            accountList.add(newAcc);
        }
        this.lockMan = lockMan;
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
        if (trans.getType().equals("WRITE"))
        {
            lockMan.setLock( accountList.get(trans.getAccount()), 
                trans, LockMode.WRITE 
            );
            accountList.get(trans.getAccount()).setBalance(trans.getValue());
            
            //Return the new balance set
            
            int newBalance = accountList.get(trans.getAccount()).getBalance();
            
            trans.log( "Account " + 
                Integer.toString( trans.getAccount() )
            + " new balance: " + newBalance);
            //lockMan.unsetLock( trans );
            return newBalance;
        } else //Treat any other type as a READ
        {
            lockMan.setLock( accountList.get(trans.getAccount()), 
                trans, LockMode.READ 
            );

            Account desiredAcc = accountList.get(trans.getAccount());
            //lockMan.unsetLock( trans );
            return desiredAcc.getBalance();
        }
    }

    /**
     * Get the total balance of all the accounts managed by the manager.
     *
     * @return The sum of all the account balances in the account manager.
     */
    public int getBalanceSum()
    {
        int sum = 0;
        for (Account acc : accountList)
        {
            sum += acc.getBalance();
        }
        return sum;
    }

    public int getAccNum()
    {
        return numberOfAccounts;
    }
    
    public ArrayList<Account> getAccList()
    {
        return accountList;
    }
    
    public Account getAccountByID(int id)
    {
        return accountList.get(id);
    }

}
