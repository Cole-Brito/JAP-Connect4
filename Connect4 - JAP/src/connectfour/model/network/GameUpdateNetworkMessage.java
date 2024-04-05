package connectfour.model.network;

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
	 * Array of tiles states.
	 * May be null.
	 */
	public Integer[] gameBoard;
	
	/**
	 * Create a new GameUpdateNetworkMessage
	 * @param opcode {@link NetworkMessage#Opcode} The message opcode
	 * @param gameState the new gameState. Leave null if unchanged.
	 * @param gameBoard the updated array of tile states. Leave null if unchanged.
	 */
	public GameUpdateNetworkMessage(Opcode opcode, Integer gameState, Integer[] gameBoard) {
		super(opcode);
		this.gameState = gameState;
		this.gameBoard = gameBoard;
	}
}
