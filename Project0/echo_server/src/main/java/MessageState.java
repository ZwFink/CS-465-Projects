/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zane
 */
public class MessageState 
{
    private String currentMessage;
    
    public void processChar( char in )
    {
        
    }
    
    public boolean quitReached()
    {
        return true;
    }
    
    public boolean processedString()
    {
        return true;
    }
    
    public String getString() throws Exception
    {
        return currentMessage;
    }
}
