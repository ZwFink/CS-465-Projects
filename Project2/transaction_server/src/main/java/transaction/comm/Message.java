/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.comm;

/**
 *
 * @author caleb johnson
 */
public class Message implements MessageTypes
{
    private MsgType type;
    private Object[] content;
    
    public Message(MsgType type, Object[] content)
    {
        this.type = type;
        this.content = content;
    }
    
    public MsgType getType()
    {
        return this.type;
    }
    
    public Object[] getContent()
    {
        return this.content;
    }
}
