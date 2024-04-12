/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 12, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.PlayerManager;
import connectfour.model.network.NetworkManager;
import connectfour.model.network.NetworkManager.SessionType;
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
	 * Sends a message to the ChatManager or across the network
	 * @param message The chat message being sent
	 */
	private void sendChatMessage(String message) {
		if (NetworkManager.getInstance().getSessionType() == SessionType.CLIENT) {
			NetworkManager.getInstance().sendChatMessage(message);
		}
		else if (NetworkManager.getInstance().getSessionType() == SessionType.HOST) {
			chatManager.addMessage(message, PlayerManager.getInstance().getLocalPlayer1());
		}
		else {
			chatManager.addMessage(message, GameManager.getInstance().getActivePlayer());
		}
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
			sendChatMessage(inputText);
		}
		textInputField.clearInputText();
	}
	
}
