
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
    
    public void run()
    {
    }
    
}
