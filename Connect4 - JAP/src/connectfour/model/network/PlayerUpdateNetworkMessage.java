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
 * A NetworkMessage representing changes to a Player.
 * Contains The player's name, UUID, and if the player is Player 1, Player 2, or a spectator.
 */
public class PlayerUpdateNetworkMessage extends NetworkMessage {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/** State ID to use for player 1 */
	public static final int PLAYER_1_STATE = 1;
	/** State ID to use for player 2 */
	public static final int PLAYER_2_STATE = 2;
	/** State ID to use for default (spectator) players */
	public static final int PLAYER_DEFAULT_STATE = 0;
	
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
	 * <br>
	 * <br>1 = player 1
	 * <br>2 = player 2
	 * <br>0 = default (spectator)
	 */
	public Integer playerState;

	/**
	 * Create a new PlayerUpdateNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
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
