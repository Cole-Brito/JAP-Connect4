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

public class ChatTextInputField extends JPanel {
	
	private final JTextField inputTextField;
	private final JButton sendButton;
	
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
		this.add(inputFieldBox);
	}
	
	public void registerSendButtonActionListener(ActionListener listener){
		sendButton.addActionListener(listener);
	}
	
	public String getInputText() {
		return inputTextField.getText();
	}
	
	public void clearInputText(){
		inputTextField.setText("");
	}
}
