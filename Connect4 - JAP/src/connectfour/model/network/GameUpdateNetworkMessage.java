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
 * A NetworkMessage that can represent a change to game state, game board, or win counts
 */
public class GameUpdateNetworkMessage extends NetworkMessage {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Integer representing the GameState enum of the GameManager.
	 * May be null.
	 */
	public Integer gameState;
	
	/**
	 * Array of tile states.
	 * May be null.
	 */
	public int[][] gameBoard;
	
	/**
	 * The row of a single tile update
	 */
	public Integer row;
	
	/**
	 * The column of a single tile update
	 */
	public Integer column;
	
	/**
	 * The state of a single tile update
	 */
	public Integer state;
	
	/**
	 * Win count for player 1
	 */
	public Integer player1WinCount;

	/**
	 * Win count for player 2
	 */
	public Integer player2WinCount;
	
	/**
	 * Create a new GameUpdateNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 * @param gameState the new gameState. Leave null if unchanged.
	 * @param gameBoard the updated array of tile states. Leave null if unchanged.
	 */
	public GameUpdateNetworkMessage(Opcode opcode, Integer gameState, int[][] gameBoard) {
		super(opcode);
		this.gameState = gameState;
		this.gameBoard = gameBoard;
	}
	
	/**
	 * Create a new GameUpdateNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 * @param row The row index of a tile state update
	 * @param column The column index of a tile state update
	 * @param state The new tile state
	 */
	public GameUpdateNetworkMessage(Opcode opcode, Integer row, Integer column, Integer state) {
		super(opcode);
		this.row = row;
		this.column = column;
		this.state = state;
	}
	
	/**
	 * Create a new GameUpdateNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 * @param gameState the new gameState. Leave null if unchanged.
	 * @param gameBoard the updated array of tile states. Leave null if unchanged.
	 * @param p1wins the player 1 win count
	 * @param p2wins the player 2 win count
	 */
	public GameUpdateNetworkMessage(Opcode opcode, Integer gameState, int[][] gameBoard, Integer p1wins, Integer p2wins) {
		this(opcode, gameState, gameBoard);
		this.player1WinCount = p1wins;
		this.player2WinCount = p2wins;
	}
}
