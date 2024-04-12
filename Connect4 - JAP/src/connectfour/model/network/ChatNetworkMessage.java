/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model.network;

/**
 * A network message containing a chat message as the payload.
 * Chat message is formatted with sender info when sent by the server.
 */
public class ChatNetworkMessage extends NetworkMessage {
	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The chat message to send
	 */
	public String message;

	/**
	 * Create a new GameUpdateNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 * @param message The chat message that was sent
	 */
	public ChatNetworkMessage(Opcode opcode, String message) {
		super(opcode);
		this.message = message;
	}

}
