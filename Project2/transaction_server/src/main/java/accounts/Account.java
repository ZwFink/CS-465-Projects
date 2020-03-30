/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts;

/**
 *
 * @author caleb
 */


// create an account object
// uses Accountmanager to handle Account data
public class Account 
{
    private int accountNumber;
    private int balance;
    
    public Account(int newBalance, int id)
    {
        balance = newBalance;
        accountNumber = id;
    }
    
    public int getNumber()
    {
        return this.accountNumber;
    }
    public int getBalance()
    {
        return this.balance;
    }
    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public boolean equals( Object other )
    {
        return this.accountNumber == ((Account)other).accountNumber;
    }
    public int hashCode()
    {
        return Integer.hashCode( this.accountNumber );
    }
}
