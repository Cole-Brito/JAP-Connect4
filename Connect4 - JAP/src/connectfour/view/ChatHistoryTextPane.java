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

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.PlayerManager;

/**
 * A JTextPane designed to render text to a chat box, one message at a time.
 * Separate messages can be styled using unique TextStyles in this class.
 */
public class ChatHistoryTextPane extends JTextPane implements PropertyChangeListener {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A reference to the StyledDocument generated and used by this Text Pane
	 */
	private final StyledDocument styleDoc;
	
	/** Reference to the ChatManager that this text pane uses to populate chat messages */
	private final ChatManager chatManager;
	
	private final Pattern chatIdentifierRegex = Pattern.compile("\\[(?<type>\\w):(?<uID>[\\w\\-]+):(?<name>[\\w]+)\\]:(?<message>.*)");
	
	/**
	 * Text styles used to render text on this Text Pane
	 */
	public enum TextStyle{
		
		/**
		 * The Default text style
		 */
		DEFAULT,
		
		/**
		 * Style used for system messages (ie. not from a player)
		 */
		SYSTEM,
		
		/**
		 * Style used for Player 1 messages
		 */
		PLAYER1,
		
		/**
		 * Style used for Player 2 messages
		 */
		PLAYER2,
		
		/**
		 * Style used for other user messages
		 */
		SPECTATOR;
	}

	/**
	 * Create a new ChatHistoryTextPane.
	 * Also initializes Text Styles to use when adding messages.
	 */
	ChatHistoryTextPane(){
		chatManager = ChatManager.getInstance();
		chatManager.registerPropertyChangeListener(this);
		
		initStyles();
		this.setEditable(false);
		styleDoc = getStyledDocument();
		
		//TEMP - for testing chat box
		//this.addText("[System]: Chat History\n", TextStyle.SYSTEM);
		//this.addText("[Player]: Test Message\nTest\n", TextStyle.PLAYER1);
	}
	
	/**
	 * Adds a string message  to the chat history text area, with the given style.
	 * Implicitly adds newline to messages.
	 * Message is added to the end of the text area.
	 * @param message The string to add to the chat text area
	 * @param style The style to use
	 */
	public void addText(String message, TextStyle style) {
		try {
			styleDoc.insertString(styleDoc.getLength(), message + "\n", styleDoc.getStyle(style.name()));
			this.setCaretPosition(styleDoc.getLength());
		} catch (BadLocationException e) {
			// TODO Output to log file
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a string message  to the chat history text area, with the default style.
	 * Implicitly adds newline to messages.
	 * Message is added to the end of the text area.
	 * @param message The string to add to the chat text area
	 */
	public void addText(String message) {
		addText(message, TextStyle.DEFAULT);
	}
	
	/**
	 * Adds a string message  to the chat history text area, detecting the sender
	 * based on the prefix on the string and applying the appropriate style.
	 * Implicitly adds newline to messages.
	 * Message is added to the end of the text area.
	 * @param message The string to add to the chat text area
	 */
	public void addTextFromSender(String message) throws IllegalArgumentException, IllegalStateException {
		var matcher = chatIdentifierRegex.matcher(message);
		if(matcher.matches()) {
			switch(matcher.group("type")) {
			case "p":
				var uid = matcher.group("uID");
				var player = PlayerManager.getInstance().getPlayer(uid);
				if (GameManager.getInstance().getPlayer1().equals(player)) {
					addText(matcher.group("name") + ": " + matcher.group("message"), TextStyle.PLAYER1);
				}
				else if (GameManager.getInstance().getPlayer2().equals(player)) {
					addText(matcher.group("name") + ": " + matcher.group("message"), TextStyle.PLAYER2);
				}
				else {
					addText(matcher.group("name") + ": " + matcher.group("message"), TextStyle.SPECTATOR);
				}
				break;
			case "s":
				addText(matcher.group("name") + ": " + matcher.group("message"), TextStyle.SYSTEM);
				break;
			default:
				addText(message, TextStyle.DEFAULT);
				System.out.println("Message didn't contain sender type: " + message);
				break;
			}
		}
		else {
			
			addText(message, TextStyle.DEFAULT);
			System.out.println("Message didn't match chat pattern: " + message);
		}
	}
	
	/**
	 * Initializes text styles to be used by the StyledDocument
	 */
	private void initStyles() {
		Style defaultStyle = this.addStyle(TextStyle.DEFAULT.name(), null);
		
		Style style = this.addStyle(TextStyle.SYSTEM.name(), defaultStyle);
		StyleConstants.setItalic(style, true);
		StyleConstants.setBackground(style, Color.LIGHT_GRAY);
		
		style = this.addStyle(TextStyle.PLAYER1.name(), defaultStyle);
		StyleConstants.setBackground(style, new Color(225, 200, 200));
		style = this.addStyle(TextStyle.PLAYER2.name(), defaultStyle);
		StyleConstants.setBackground(style, new Color(225, 225, 225));
		style = this.addStyle(TextStyle.SPECTATOR.name(), defaultStyle);
		
	}

	/**
	 * PropertyChange event that is called when ChatManager updates model
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName() == "messageHistory"){
			ChatManager.MessageEventValue newValue = (ChatManager.MessageEventValue)evt.getNewValue();
			if (newValue != null)
			{
				try {
					this.addTextFromSender(newValue.message);					
				}
				catch (Exception e) {
					System.err.println("Failed to add text to ChatHistory: " + e.getMessage());
				}
				//this.addText(newValue.message, TextStyle.DEFAULT);
			}
		}
	}
}
