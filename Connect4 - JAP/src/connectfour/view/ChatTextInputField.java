/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JPanel that contains input field and send button for chat messages.
 */
public class ChatTextInputField extends JPanel {
	
	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/** The input field for entering chat messages */
	private final JTextField inputTextField;
	/** The button used to send chat messages */
	private final JButton sendButton;
	
	/**
	 * Construct the chat panel and all child elements.
	 */
	public ChatTextInputField() {		
		var inputFieldBox = Box.createHorizontalBox();
		inputTextField = new JTextField();
		inputTextField.setPreferredSize(new Dimension(155, 20));
		inputTextField.setText("enter message...");
		inputFieldBox.add(inputTextField);
		
		sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(40,20));
		sendButton.setMargin(new Insets(0,  0,  0,  0));
		sendButton.setFont(new Font("arial", Font.PLAIN, 12));
		
		inputFieldBox.add(sendButton);
		//sendButton.setAction();
		this.add(inputFieldBox);
	}
	
	/**
	 * Add an ActionListener to respond to the text input field
	 * @param listener The ActionListener that responds to JTextField actions
	 */
	public void registerTextInputActionListener(ActionListener listener){
		inputTextField.addActionListener(listener);
	}
	
	/**
	 * Add an ActionListener to respond to the send button
	 * @param listener The ActionListener that responds to JButton actions
	 */
	public void registerSendButtonActionListener(ActionListener listener){
		sendButton.addActionListener(listener);
	}
	
	/**
	 * Gets the text currently in the input text field.
	 * @return inputTextField text
	 */
	public String getInputText() {
		return inputTextField.getText();
	}
	
	/**
	 * Clear the text in the input text field.
	 */
	public void clearInputText(){
		inputTextField.setText("");
	}
}
