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
    private char lastChar;
    private CurrentState state;
    
    private enum CurrentState
    {
      START_STATE,
      Q_REACHED,
      QU_REACHED,
      QUI_REACHED,
      QUIT_REACHED
    };
    
    public MessageState()
    {
        lastChar = '\0';
        state = CurrentState.START_STATE;
    }
    
    public void processChar( char in )
    {
        if( charIsSendable( in ) 
             && Character.isLowerCase( in )
          )
        {

            if( state == CurrentState.START_STATE && in == 'q'  )
            {
                state = CurrentState.Q_REACHED;
            }            
            else if( state == CurrentState.Q_REACHED && in == 'u'  )
            {
                state = CurrentState.QU_REACHED;
            }
            
            
            else
            {
                state = CurrentState.START_STATE;
            }
            
            lastChar = in;
        }
    }
    
    public boolean quitReached()
    {
        return true;
    }
   
    public char getLastChar()
    {
        return lastChar;
    }
    
    public void clear()
    {
        lastChar = '\0';
    }
    
   /*
    * Determine whether a character can be sent back to 
    * the client. 
    * @param test The character to test
    * @returns true if test is an upper or lower-case 
    *          letter of the English alphabet.
    */
    public boolean charIsSendable( char test )
    {
        int charInt = (int) test;
        boolean sendable = ( charInt >= 'a' && charInt <= 'z' )  
                || ( charInt >= 'A' && charInt <= 'Z' );
        
        return sendable;
    }
}
