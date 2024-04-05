package connectfour.model.network;

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
	 * @param opcode {@link NetworkMessage#Opcode} The message opcode
	 * @param message The chat message that was sent
	 */
	public ChatNetworkMessage(Opcode opcode, String message) {
		super(opcode);
		this.message = message;
	}

}
