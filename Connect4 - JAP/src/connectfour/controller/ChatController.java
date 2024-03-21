package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.ChatManager;
import connectfour.view.ChatTextInputField;

public class ChatController implements ActionListener {

	private final ChatManager chatManager;
	private final ChatTextInputField textInputField;
	
	public ChatController(ChatManager manager, ChatTextInputField inputField) {
		chatManager = manager;
		textInputField = inputField;
		textInputField.registerTextInputActionListener(this);
		textInputField.registerSendButtonActionListener(this);
	}

	@Override
	/**
	 * Responds to ActionEvents when registered as a listener.
	 * Assumes listener is registered to a JTextField and JButton.
	 * @param e ActionEvent data
	 */
	public void actionPerformed(ActionEvent e) {
		String inputText = textInputField.getInputText();
		if (!inputText.isBlank())
		{
			chatManager.addMessage(inputText, "temp"); //TODO: Get active player
		}
		textInputField.clearInputText();
	}
	
}
