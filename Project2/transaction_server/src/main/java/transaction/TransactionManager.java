/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

/**
 *
 * @author caleb
 */
public class TransactionManager 
{
    // Handles a transaction by calling an Account Manager to handle alterations to the account
    
    /*
    Third object does work in a semantical area deals with the transactions.  

Workers open up one connection then don’t shut it down makes it easier. 

		Request -> open connection and keep it open
		
		Reads and writes handled by transaction worker.
				Threads are used

				Transaction manager extends thread
					Socket
					Inputstream	outputstream
					Message
			
					Pass in client start the thread

Take care of read and write requests.
	Write creates new object
	Acc num
		Balance
	Trans log
		Balance
Try - catch

	Read
		Acc#
		Transaction
Try-catch

Finally close all the open sockets read,write,client, Boolean-false

    */
}
