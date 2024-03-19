package connectfour.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatManager {
	
	private static ChatManager _instance;
	public static ChatManager getInstance() {
		if (_instance == null) {
			_instance = new ChatManager();
		}
		return _instance;
	}
	
	private final List<String> messageHistory;
	private final PropertyChangeSupport propertyChangedSupport;
	
	/**
	 * The structure used in {@link PropertyChangeEvent}
	 */
	public class MessageEventValue{
		public final String message;
		public final int id;
		
		public MessageEventValue(String newMessage, int messageID) {
			message = newMessage;
			id = messageID;
		}
	}
	
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
		String formattedMessage = String.format("[p:%s]:%s", sender, message);
		MessageEventValue oldValue = null;
		if (messageHistory.size() > 0) {
			oldValue = new MessageEventValue(messageHistory.get(messageHistory.size() - 1), messageHistory.size() - 1);
		}
		messageHistory.add(formattedMessage);
		propertyChangedSupport.firePropertyChange("messageHistory", oldValue,
				new MessageEventValue(formattedMessage, messageHistory.size() - 1));
	}
	
	/**
	 * <p>Adds a message to messageHistory using "System" as the sender.
	 * System messages are prefixed with [s:System]</p>
	 * This function invokes a PropertyChangeEvent on messageHistory
	 * @param message The message to add
	 */
	public void addSystemMessage(String message) {
		String formattedMessage = String.format("[s:System]:%s", message);
		messageHistory.add(formattedMessage);
		MessageEventValue oldValue = null;
		if (messageHistory.size() > 0) {
			oldValue = new MessageEventValue(messageHistory.get(messageHistory.size() - 1), messageHistory.size() - 1);
		}
		messageHistory.add(formattedMessage);
		propertyChangedSupport.firePropertyChange("messageHistory", oldValue,
				new MessageEventValue(formattedMessage, messageHistory.size() - 1));
	}
	
	/**
	 * Gets the list of all messages as a readonly list
	 * @return
	 */
	public List<String> getMessageHistory(){
		return Collections.unmodifiableList(messageHistory);
	}
	
	/**
	 * Registers a PropertyChangeListener to responds to changes to messageHistory.
	 * Event will be fired when a new message is added.<br>
	 * Note that the PropertyChangeEvent will use {@link MessageEventValue} as old and new values.
	 * 
	 * @param listener The {@link PropertyChangeListener} that responds to new messages being added
	 */
	public void registerPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangedSupport.addPropertyChangeListener("messageHistory", listener);
	}
}
