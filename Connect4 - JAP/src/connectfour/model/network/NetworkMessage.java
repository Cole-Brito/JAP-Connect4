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

import java.io.Serializable;

/**
 * A object intended to be sent over the network to client sockets.
 * Contains an Opcode to determine which type of message this is.
 */
public class NetworkMessage implements Serializable {
	
	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The type of Network message. Used to determine how to treat the payload.
	 */
	public enum Opcode{
		/** Initial message, updates full list of players and board states */
		HELLO,
		/** A new player has connected to server */
		PLAYER_JOIN,
		/** A player has disconnected */
		PLAYER_LEAVE,
		/** A player has their state or username updated */
		PLAYER_UPDATE,
		/** A single tile has updated */
		GAMEBOARD_UPDATE_ONE,
		/** The entire board should be updated */
		GAMEBOARD_UPDATE_ALL,
		/** The game state has changed */
		GAMESTATE_UPDATE,
		/** A chat message was sent */
		MESSAGE_CREATE,
		/** The server has closed */
		SESSION_TERMINATE
	}
	
	/** The Opcode (or message type) of the message */
	public Opcode opcode;
	
	/**
	 * Creates a new NetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 */
	public NetworkMessage(Opcode opcode) {
		this.opcode = opcode;
	}
}
