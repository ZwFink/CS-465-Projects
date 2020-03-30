/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.comm;

/**
 * Represents the base message that is sent over the network. 
 * @author caleb johnson
 */
public class Message implements MessageTypes
{
    /**
     * The type of message this is.
     */
    private MsgType type;

    /**
     * The message's contents.
     */
    private Object[] content;
    
    /**
     * Argument constructor.
     * @param type What this message will be.
     * @param content The contents of the message that will be sent.
     */
    public Message(MsgType type, Object[] content)
    {
        this.type = type;
        this.content = content;
    }
    
    /**
     * 
     * @return This message's type.
     */
    public MsgType getType()
    {
        return this.type;
    }
    
    /**
     * 
     * @return This message's content
     */
    public Object[] getContent()
    {
        return this.content;
    }
}
