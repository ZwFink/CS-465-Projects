
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zane
 */
public class EchoThread implements Runnable
{
    /*
    * The socket connected to a client.
    */
    public Socket clientSocket;
    
    /**
     * Initialize the EchoThread with a Socket 
     * that is bound to a client.
     * @param clientSocket A Socket object that is bound 
     * to a client. 
     */
    public EchoThread( Socket clientSocket )
    {
        this.clientSocket = clientSocket;
    }
    
    public EchoThread()
    {
        clientSocket = null;
    }
    
    /*
    * JavaDoc here.
    */
    public void run() 
    {
        try (
        PrintWriter toClient 
                = new PrintWriter( clientSocket.getOutputStream(), true);
        BufferedReader fromClient 
                = new BufferedReader( 
                        new InputStreamReader( clientSocket.getInputStream() 
                        ) 
                )
            )
        {
            char charFromClient;
            MessageState message = new MessageState();
            
            while( !message.quitReached() )
            {
                charFromClient = (char) fromClient.read();
                
                message.processChar( charFromClient );
                
                if( message.processedString() )
                {
                    String clientString = message.getString();
                    toClient.println( clientString );
                            
                }
            }
            
        }
        catch( Exception e )
        {
            System.out.println( "Unknown exception occurred" );
        }
        
    }
    
}
