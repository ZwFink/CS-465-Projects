/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.client;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
import appserver.job.Job;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import utils.PropertyHandler;

/**
 * A client that submits requests to compute fibonacci numbers to 
 * the server.
 * @author zane
 */
public class FibonacciClient implements MessageTypes, Runnable 
{
    String host = null;
    int port = 0;
    int fibNumber = 0;

    Properties properties;

    public FibonacciClient( String serverPropertiesFile, int fibNumber )
    {
        try
        {
            properties = new PropertyHandler( serverPropertiesFile );
            this.fibNumber = fibNumber;
            
            host = properties.getProperty( "HOST" );
            host = "127.0.0.1";
            port = Integer.parseInt( properties.getProperty( "PORT" ) );

        }
        catch( Exception ex )
        {
            System.out.println( "[FibonacciClient.FibonacciClient] Unknown exception "
                + "occurred. ");
            ex.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            Socket server = new Socket( host, port );
            String classString = "appserver.job.impl.Fibonacci";
            Integer n = new Integer( fibNumber );

            Job job = new Job( classString, n );
            Message message = new Message( JOB_REQUEST, job );

            ObjectOutputStream writeToNet = new ObjectOutputStream(server.getOutputStream());
            writeToNet.writeObject(message);

            ObjectInputStream readFromNet = new ObjectInputStream(server.getInputStream());
            Integer result = (Integer) readFromNet.readObject();
            System.out.println( "fib(" + n.toString() + 
                ") RESULT: " + result );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }
        
    }

    public static void main( String[] args )
    {
        String propertiesFilePath = "";
        if( args.length == 1 )
        {
            propertiesFilePath = args[ 0 ];
        }
        else
        {
            propertiesFilePath = "../../config/Server.properties";
        }

        Thread[] threads = new Thread[ 46 ];
        for( int i = 46; i > 0 ; i-- )
        {
            int idx = i - 1;
            threads[ idx ] = 
            ( new Thread( new FibonacciClient( propertiesFilePath, i ) ) );
            threads[ idx ].start();

            try
            {
                Thread.sleep( 1000 );
            }
            catch( InterruptedException e )
            {

            }
        }

        for( int i = 46; i > 0; i-- )
        {
            int idx = i - 1;
            try
            {
                threads[ idx ].join();
            }
            catch( InterruptedException e )
            {
                System.out.println( "[FibonacciClient.Main] Interrupted exception " + 
                    "when attempting to join threads."
                );
                e.printStackTrace();
            }
        }
    }
    
}
