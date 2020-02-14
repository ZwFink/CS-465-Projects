/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

/*
 *
 * @author kenny
 * Graphical user interface for chat application.
 * Displays a window to begin the chat.
 * 
 * Closes the window when leave_chat is reached.
 * 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 


public class chatWindow extends JPanel implements ActionListener {
    protected JTextField inputArea;
    protected JTextArea chatArea;
    
 
    public chatWindow() {
        super(new GridBagLayout());
 
        inputArea = new JTextField(50);
        inputArea.addActionListener(this);
 
        chatArea = new JTextArea(20, 20);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(inputArea);
 
        //Add Components to this panel.
        GridConstraints c = new GridConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridConstraints.HORIZONTAL;
        add(chatArea, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
 
    public void actionPerformed(ActionEvent event) {
        String userInput = inputArea.getText();
        chatArea.append(userInput + "\n");
        inputArea.selectAll();
 
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }
 
    /*
     * Display the GUI
     */
    private static void createAndShowGUI() 
    {     
        //Create window with title
        JFrame frame = new JFrame("Chat Application");
        
        // set to exit on close 
        //ToDo: incorporate leaveing the chat with leave_chat
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //add duplicate window
        frame.add(new chatWindow());
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
       
        //Response thread creation
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}



/*
package message;


public interface MessageTypes
{
    public static final int JOIN = 1;
    public static final int JOINED = 2;
    public static final int LEAVE = 3;
    public static final int HASLEFT = 4;
}

*/

