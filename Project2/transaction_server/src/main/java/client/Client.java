package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Connects to the Proxy Server and issues commands
 * Commands: Open, Read, Write, Close
 * @author caleb
 */
public class Client extends thread
{
    //save host and port number as variables
    string host = "127.0.0.1";
    private final int port = 2080;
    
    // Opens a proxy server
    public void run()
    {
        // get the transaction id
        
        // create loop to handle # of transactions
        
        try
        {
            //connect to the server given the host and port number
            Socket socket = new Socket(host, port);
            
            //set up read and write streams
            ObjectInputStream read = new ObjectInputStream(socket.getInputStream);
            ObjectOutputStream write = new ObjectOutputStream(socket.getInputStream);
            
            // handle transactions & proper locks
                //reading -- viewing account
                //writing
                    //Depositing
                    //Withdrawling
        }
    }
    
    
        // Issues read and write commands
    // Closes the proxy server
}
