/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction_server;

import client.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertyHandler;

/**
 *
 * @author caleb
 */
public class Main 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        String propertiesFile = "General.properties";
        int numTestAccounts = 3; //Number of the accounts in the system
        int maxBalance = 500; //Max balance an account starts at (decides highest possible random transaction value)
        int numTransactions = 3; // Number of random transactons to perform
        int numClients = 3; // Number of client threads made
        int startPort = 2081; //Port number to start at to give client
        int serverPort = 2080;
        String defaultHost = "127.0.0.1"; //default ip of the testing host for the clients
        //Try to load propety file to replace hard coded defaults
        try
        {
            PropertyHandler propHand = new PropertyHandler(propertiesFile);
            numTestAccounts = Integer.parseInt(propHand.getProperty("NUMBER_ACCOUNTS"));
            maxBalance = Integer.parseInt(propHand.getProperty("INITIAL_BALANCE"));
            numTransactions = Integer.parseInt(propHand.getProperty("NUMBER_TRANSACTIONS"));
            numClients = Integer.parseInt(propHand.getProperty("NUMBER_CLIENTS"));
            startPort = Integer.parseInt(propHand.getProperty("CLIENT_PORT"));
            serverPort = Integer.parseInt(propHand.getProperty("SERVER_PORT"));
            defaultHost = propHand.getProperty("HOST");
            
        } catch (IOException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Property file called "+propertiesFile+" failed to load, switching to defaults");
        }
        
        
        // Create a new Transaction Server
        TransactionServer tranServer = new TransactionServer(defaultHost, serverPort);
        // Create a new Client and proxy //THREAD Client to have multiple clients at same time
        for(int i = 0; i < numClients; i++)
        {
            Client newClient = new Client(defaultHost,
                    startPort + i,
                    numTestAccounts,
                    maxBalance,
                    numTransactions);
            newClient.start();
        }
    }
    
}
