/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 * Shields Client from Server
 * Acts on behalf of Client
 * @author caleb
 */
public class TransactionServerProxy 
{

    private String host;
    private int port;
    TransactionServerProxy(String host, int port) 
    {
        this.host = host;
        this.port = port;
    }
    // Waits for a client to call for a open connection
        // Connects to the TransactionServer
        // Handles the clients requests
        // Processes replies from the TransactionServer
    // Closes socket connect to Transaction server if Client calls close()

    int openTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int read(int accountFrom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void write(int accountTo, int toBalance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void closeTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
