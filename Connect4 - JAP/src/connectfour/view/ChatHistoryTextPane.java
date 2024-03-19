/**
 * Java Application Program - Connect Four
 * Authors:	Paul Squires
 * 			Cole Brito
 * Modified: Feb. 10, 2024
 */

package connectfour.view;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import connectfour.model.ChatManager;

/**
 * A JTextPane designed to render text to a chat box, one message at a time.
 * Separate messages can be styled using unique TextStyles in this class.
 */
public class ChatHistoryTextPane extends JTextPane {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A reference to the StyledDocument generated and used by this Text Pane
	 */
	private final StyledDocument styleDoc;
	
	private final ChatManager chatManager;
	
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
		
		initStyles();
		this.setEditable(false);
		styleDoc = getStyledDocument();
		
		//TEMP - for testing chat box
		this.addText("[System]: Chat History\n", TextStyle.SYSTEM);
		this.addText("[Player]: Test Message\nTest\n", TextStyle.PLAYER1);
	}
	
	/**
	 * Adds a string message  to the chat history text area, with the given style.
	 * Does not implicitly add newline to messages.
	 * Message is added to the end of the text area.
	 * @param message The string to add to the chat text area
	 * @param style The style to use
	 */
	public void addText(String message, TextStyle style) {
		try {
			styleDoc.insertString(styleDoc.getLength(), message, styleDoc.getStyle(style.name()));
		} catch (BadLocationException e) {
			// TODO Output to log file
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a string message  to the chat history text area, with the default style.
	 * Does not implicitly add newline to messages.
	 * Message is added to the end of the text area.
	 * @param message The string to add to the chat text area
	 */
	public void addText(String message) {
		addText(message, TextStyle.DEFAULT);
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
		StyleConstants.setBackground(style, new Color(200, 200, 225));
		style = this.addStyle(TextStyle.PLAYER2.name(), defaultStyle);
		style = this.addStyle(TextStyle.SPECTATOR.name(), defaultStyle);
		
	}
}
