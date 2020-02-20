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
 * currently closing the window terminates the
 * TODO: Closes the window when leave_chat is reached.
 * 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 


public class chatWindow extends JPanel implements ActionListener 
{
    
    protected JTextField inputArea;
    protected JTextArea chatArea;
    
    /**
     * Initialize the chat window with desired size values.
     */
    public chatWindow()
    {
        super(new GridBagLayout());
           
        // Where users will input information
        inputArea = new JTextField(40);
        inputArea.addActionListener(this);
 
        //Area where text will be displayed to the users
        chatArea = new JTextArea(20, 20);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(inputArea);
 
        //Add Components to this panel.
        GridBagConstraints container = new GridBagConstraints();
        container.gridwidth = GridBagConstraints.REMAINDER;
 
        container.fill = GridBagConstraints.HORIZONTAL;
        add(chatArea, container);
 
        container.fill = GridBagConstraints.BOTH;
        container.weightx = 1.0;
        container.weighty = 1.0;
        
        add(scrollPane, container);
    }
 
    @Override
    public void actionPerformed(ActionEvent event)
    {
        String userInput = inputArea.getText();
        chatArea.append(userInput + "\n");
        inputArea.selectAll();
 
        //Make sure the new text is visible, even if there
        
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }
 
    /*
     * Display the GUI
     */
    private static void showGUI() 
    {     
        //Create window with title of program
        JFrame frame = new JFrame("Chat Application");
        
        // set to exit on close 
        //ToDo: incorporate leaveing the chat with leave_chat
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //add duplicate window
        frame.add(new chatWindow());       
        frame.pack();
        frame.setVisible(true);
    }
 
    /**
     * run GUI with main method
     * @param args 
     */
    public static void main(String[] args) {
       
        //Response thread creation
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });
    }
}

