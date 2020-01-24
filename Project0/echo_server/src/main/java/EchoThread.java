
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        this.setSocket( clientSocket );
    }
    
    /**
     * Default constructor, initializes 
     * this object's client socket to null.
     */
    public EchoThread()
    {
        clientSocket = null;
    }
    
    /**
     * Set the socket for this object.
     * @param clientSocket The socket to set.
     * @post this.clientSocket = clientSocket
     */
    public final void setSocket( Socket clientSocket )
    {
        this.clientSocket = clientSocket;
    }
    
    /*
    * Run the thread, handling the client connection.
    * @pre This has been initialized with a Socket connected
    *      to a client.
    * @post The client has been handled, according to the 
    *       echoServer specification.
    * @post The client connection is closed.
    * @post The client socket of this is null.
    */
    public void run() 
    {
        try (
        DataOutputStream toClient 
                = new DataOutputStream( clientSocket.getOutputStream() );
        DataInputStream fromClient 
                = new DataInputStream( 
                        clientSocket.getInputStream() 
                        ) 
                
            )
        {
            char charFromClient;
            MessageState message = new MessageState();
            
            while( !message.quitReached() )
            {
                charFromClient = (char) fromClient.readByte();
               
                message.processChar( charFromClient );
                
                if( message.charIsSendable( message.getLastChar() ) )
                {
                    toClient.write( charFromClient );
                }
                
            }
            
            this.clientSocket.close();
            this.clientSocket = null;
            
        }
        catch( Exception e )
        {
            System.out.println( "Unknown exception occurred" );
        }
        
    }
    
}
