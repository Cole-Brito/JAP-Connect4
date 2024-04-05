/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 5, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.model.network;

public class PlayerUpdateNetworkMessage extends NetworkMessage {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The player's username. May be null.
	 */
	public String username;
	
	/**
	 * The player's UUID. Should never be null.
	 */
	public String uID;
	
	/**
	 * The player's new state. May be null.
	 * <list>
	 * <li>1 = player 1</li>
	 * <li>2 = player 2</li>
	 * <li>0 = default (spectator)</li>
	 * </list>
	 */
	public Integer playerState;
	
	/**
	 * Create a new PlayerUpdateNetworkMessage
	 * @param opcode {@link NetworkMessage#Opcode} The message opcode
	 * @param userID The UUID of the player. Should not be empty or null.
	 * @param username The username of the player. Leave null if not updating.
	 * @param playerState The active state of the player. Leave null if not updating.
	 */
	public PlayerUpdateNetworkMessage(Opcode opcode, String userID, String username, Integer playerState) {
		super(opcode);
		this.uID = userID;
		this.username = username;
		this.playerState = playerState;
	}
}
