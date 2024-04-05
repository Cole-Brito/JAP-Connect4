package connectfour.model.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.GameState;
import connectfour.model.Player;
import connectfour.model.PlayerManager;

public class NetworkManager {
	/** The singleton instance of NetworkManager */
	private static NetworkManager _instance;
	/**
	 * Gets the singleton instance of NetworkManager.
	 * Constructs a new NetworkManager only the first time it is called.
	 * @return The instance of NetworkManager
	 */
	public static NetworkManager getInstance() {
		if (_instance == null) {
			_instance = new NetworkManager();
		}
		return _instance;
	}
	
	private static int MIN_PORT_NUMBER = 1024;
	private static int MAX_PORT_NUMBER = 65535;
	
	public enum SessionType{
		OFFLINE,
		HOST,
		CLIENT
	}
	
	private SessionType sessionType = SessionType.OFFLINE;
	
	/**
	 * The Server Socket connection, if this application is hosting a network game.
	 * Note - it is used in mutual exclusion with clientSocket
	 */
	private ServerSocket serverSocket;
	
	/**
	 * The Client Socket connection, if this application is joining a network game.
	 * Note - it is used in mutual exclusion with serverSocket
	 */
	private Socket clientSocket;
	
	/**
	 * Private constructor for NetworkManager singleton.
	 * Sets initial states.
	 */
	private NetworkManager() {
		
	}
	
	public SessionType getSessionType() {
		return sessionType;
	}
	
	/**
	 * Attempts to open the server socket on a given port
	 * @param port
	 * @return
	 */
	public int openServerSocket(int port) {
		if (sessionType == SessionType.CLIENT) {
			if (clientSocket.isConnected()) {
				//TODO: Leave previous server connection
				
				try {
					clientSocket.close();
				} catch (IOException e) {
					System.err.println("Failed to close client socket");
				}
			}
		}
		
		if (serverSocket == null) {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.err.println("Failed to open server socket on port: " + port);
				return -1;
			}
		}
		else if (serverSocket.getLocalPort() == port) {
			System.out.println("Attempt to reopen server on same socket");
		}
		else {
			
		}
				
		return serverSocket.getLocalPort();
	}
	
	public void closeServerSocket() {
		if (sessionType == sessionType.HOST) {
			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					
					serverSocket.close();
				} catch (IOException e) {
					System.err.println("Failed to close serverSocket");
				}
			}
		}
	}
	
	public void closeClientSocket() {
		
	}
	
	public void handleIncomingMessage(NetworkMessage message, Player sender) {
		if (sessionType == SessionType.HOST) {
			
		}
		else if (sessionType == SessionType.CLIENT) {
			switch (message.opcode) {
			case HELLO:
				
				break;
			case PLAYER_JOIN:
			{
				var playerUpdate = (PlayerUpdateNetworkMessage)message;
				if (playerUpdate != null) {
					PlayerManager.getInstance().addNetworkPlayer(playerUpdate.username, playerUpdate.uID, null);
				}
			}
				break;
			case PLAYER_LEAVE:
			{
				var playerUpdate = (PlayerUpdateNetworkMessage)message;
				if (playerUpdate != null) {
					PlayerManager.getInstance().removePlayer(playerUpdate.uID);
				}
			}
				break;
			case PLAYER_UPDATE:
			{
				var playerUpdate = (PlayerUpdateNetworkMessage)message;
				if (playerUpdate != null) {
					//TODO: rename player or promote (/demote) player to active player
				}
			}
				break;
			case GAMEBOARD_UPDATE_ALL:
			{
				var gameUpdate = (GameUpdateNetworkMessage)message;
				if (gameUpdate != null) {
					//TODO: 
				}
			}
				break;
			case GAMEBOARD_UPDATE_ONE:
			{
				var gameUpdate = (GameUpdateNetworkMessage)message;
				if (gameUpdate != null) {
					//TODO: update one tile
				}
			}
				break;
			case GAMESTATE_UPDATE:
			{
				var gameUpdate = (GameUpdateNetworkMessage)message;
				if (gameUpdate != null && gameUpdate.gameState != null && 
						gameUpdate.gameState >= 0 && gameUpdate.gameState < GameState.values().length) {
					GameState newState = GameState.values()[gameUpdate.gameState];
					GameManager.getInstance().updateGameState(newState);
				}
				else {
					System.err.println("GAMESTATE_UPDATE message received with invalid gameState");
				}
			}
				break;
			case MESSAGE_CREATE:
			{
				var messageUpdate = (ChatNetworkMessage)message;
				if (messageUpdate != null && messageUpdate.message != null) {
					ChatManager.getInstance().addMessage(messageUpdate.message, sender);
				}
				else {
					System.err.println("MESSAGE_CREATE message received with invalid payload");
				}
			}
				break;
			case SESSION_TERMINATE:
				//TODO: close session
				break;
			default:
				break;
				
			}
		}
	}
	
	private void onMessageReceived() {
		try {
			var bytes = clientSocket.getInputStream().readAllBytes();
			//var message = (NetworkMessage)bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
