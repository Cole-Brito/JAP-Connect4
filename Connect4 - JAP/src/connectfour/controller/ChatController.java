/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.ChatManager;
import connectfour.view.ChatTextInputField;

/**
 * Controller to respond to actions from the Chat Window (text input/send button).
 * Relays actions to the ChatManager.
 */
public class ChatController implements ActionListener {

	/** Stored reference to the ChatManager */
	private final ChatManager chatManager;
	
	/** 
	 * The input text field on te UI. Stored so the field can be cleared
	 * when the chat message is sent using Send button or Enter key.
	 */
	private final ChatTextInputField textInputField;
	
	/**
	 * Instantiate a new ChatController and register action listeners.
	 * @param inputField The ChatTextInputField that this controller responds to.
	 */
	public ChatController(ChatTextInputField inputField) {
		chatManager = ChatManager.getInstance();
		textInputField = inputField;
		textInputField.registerTextInputActionListener(this);
		textInputField.registerSendButtonActionListener(this);
	}

	/**
	 * Responds to ActionEvents when registered as a listener.
	 * Assumes listener is registered to a JTextField and JButton.
	 * @param e ActionEvent data
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String inputText = textInputField.getInputText();
		if (!inputText.isBlank())
		{
			chatManager.addMessage(inputText, "temp"); //TODO: Get active player
		}
		textInputField.clearInputText();
	}
	
}
