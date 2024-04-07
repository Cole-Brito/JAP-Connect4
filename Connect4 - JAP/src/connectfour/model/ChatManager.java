/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tracks history of chat messages and senders
 */
public class ChatManager {
	
	/** The singleton instance of ChatManager */
	private static ChatManager _instance;
	
	/**
	 * Gets the singleton instance of ChatManager.
	 * Constructs a new ChatManager only the first time it is called.
	 * @return The instance of ChatManager
	 */
	public static ChatManager getInstance() {
		if (_instance == null) {
			_instance = new ChatManager();
		}
		return _instance;
	}
	
	/** The list of chat messages */
	private final List<String> messageHistory;
	/** PropertyChangeSupport for notifying listeners when messageHistory changes */
	private final PropertyChangeSupport propertyChangedSupport;
	
	/**
	 * Private constructor for ChatManager singleton.
	 */
	private ChatManager() {
		messageHistory = new ArrayList<>();
		propertyChangedSupport = new PropertyChangeSupport(this);
	}
	
	/**
	 * <p>Adds a message to messageHistory using a player as the sender.
	 * Player messages are prefixed with [p:PlayerName] where PlayerName is the sender.</p>
	 * This function invokes a PropertyChangeEvent on messageHistory
	 * @param message The message to add
	 * @param sender The name of the sender
	 */
	public void addMessage(String message, String sender) {
		String formattedMessage = String.format("[p:0:%s]:%s", sender, message);
		MessageEventValue oldValue = null;
		if (messageHistory.size() > 0) {
			oldValue = new MessageEventValue(messageHistory.get(messageHistory.size() - 1), messageHistory.size() - 1);
		}
		messageHistory.add(formattedMessage);
		propertyChangedSupport.firePropertyChange(CHAT_HISTORY_PROPERTY_NAME, oldValue,
				new MessageEventValue(formattedMessage, messageHistory.size() - 1));
	}
	
	public void addMessage(String message, Player sender) {
		String formattedMessage = "";
		if (sender != null) {
			formattedMessage = String.format("[p:%s:%s]:%s", sender.getPlayerID().toString(), sender.getName(), message);
		}
		else {
			formattedMessage = String.format("[p:%s:%s]:%s", "0", "null", message);			
		}
		MessageEventValue oldValue = null;
		if (messageHistory.size() > 0) {
			oldValue = new MessageEventValue(messageHistory.get(messageHistory.size() - 1), messageHistory.size() - 1);
		}
		messageHistory.add(formattedMessage);
		propertyChangedSupport.firePropertyChange(CHAT_HISTORY_PROPERTY_NAME, oldValue,
				new MessageEventValue(formattedMessage, messageHistory.size() - 1));
	}
	
	/**
	 * Adds a pre-formatted message to chat history.
	 * Should be called when receiving a formatted chat message from server.
	 * @param message The formatted message
	 */
	public void addFormattedMessage(String message) {
		MessageEventValue oldValue = null;
		if (messageHistory.size() > 0) {
			oldValue = new MessageEventValue(messageHistory.get(messageHistory.size() - 1), messageHistory.size() - 1);
		}
		messageHistory.add(message);
		propertyChangedSupport.firePropertyChange(CHAT_HISTORY_PROPERTY_NAME, oldValue,
				new MessageEventValue(message, messageHistory.size() - 1));
	}
	
	/**
	 * <p>Adds a message to messageHistory using "System" as the sender.
	 * System messages are prefixed with [s:System]</p>
	 * This function invokes a PropertyChangeEvent on messageHistory
	 * @param message The message to add
	 */
	public void addSystemMessage(String message) {
		String formattedMessage = String.format("[s:0:System]:%s", message);
		messageHistory.add(formattedMessage);
		MessageEventValue oldValue = null;
		if (messageHistory.size() > 0) {
			oldValue = new MessageEventValue(messageHistory.get(messageHistory.size() - 1), messageHistory.size() - 1);
		}
		messageHistory.add(formattedMessage);
		propertyChangedSupport.firePropertyChange(CHAT_HISTORY_PROPERTY_NAME, oldValue,
				new MessageEventValue(formattedMessage, messageHistory.size() - 1));
	}
	
	/**
	 * Gets the list of all messages as a readonly list
	 * @return readonly list of chat messages
	 */
	public List<String> getMessageHistory(){
		return Collections.unmodifiableList(messageHistory);
	}
	
	
	/********** PropertyChanged fields and methods **********/
	
	/** Property Name used to notify PropertyChange events when a message is added */
	public static final String CHAT_HISTORY_PROPERTY_NAME = "ChatMessageHistory";
	
	/**
	 * Registers a PropertyChangeListener to responds to changes to messageHistory.
	 * Event will be fired when a new message is added.<br>
	 * Note that the PropertyChangeEvent will use {@link MessageEventValue} as old and new values.
	 * 
	 * @param listener The {@link PropertyChangeListener} that responds to new messages being added
	 */
	public void registerPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		System.out.println(propertyName + " " + listener.toString());
		propertyChangedSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	
	/**
	 * The structure used in {@link PropertyChangeEvent}
	 */
	public class MessageEventValue{
		/** The chat message */
		public final String message;
		/** The ID of the message, used to validate that previous message was received. */
		public final int id;
		
		/**
		 * Construct a new MessageEventValue
		 * @param newMessage The chat message
		 * @param messageID The ID of this message
		 */
		public MessageEventValue(String newMessage, int messageID) {
			message = newMessage;
			id = messageID;
		}
	}
}
