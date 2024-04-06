package connectfour.model.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.GameState;
import connectfour.model.Player;
import connectfour.model.PlayerManager;
import connectfour.model.network.ClientSocketHandler.ClientState;

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
	
	public static int MIN_PORT_NUMBER = 1024;
	public static int MAX_PORT_NUMBER = 65535;
	
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
	private ServerSocketHandler serverSocket;
	
	/**
	 * The Client Socket connection, if this application is joining a network game.
	 * Note - it is used in mutual exclusion with serverSocket
	 */
	private ClientSocketHandler clientSocket;
	
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
			if (clientSocket != null && clientSocket.getClientState() != ClientState.STOPPED) {
				//TODO: Leave previous server connection
				try {
					clientSocket.closeClientSocket();
				} catch (Exception e) {
					System.err.println("Failed to close client socket");
				}
			}
		}
		
		if (serverSocket == null) {
			try {
				serverSocket = new ServerSocketHandler(port);
				serverSocket.start();
			} catch (Exception e) {
				System.err.println("Failed to open server socket on port: " + port);
				return -1;
			}
		}
		else if (serverSocket.getPort() == port) {
			System.out.println("Attempt to reopen server on same socket");
		}
		else {
			
		}
				
		return -1;
	}
	
	public void closeServerSocket() {
		if (sessionType == sessionType.HOST) {
			if (serverSocket != null && !serverSocket.server.isClosed()) {
				try {
					serverSocket.closeServerSocket();
					System.out.println("Server socket closed");
				} catch (Exception e) {
					System.err.println("Failed to close serverSocket");
				}
			}
		}
	}
	
	public boolean openClientSocket(String address, int port) {
		if (sessionType == SessionType.OFFLINE) {
			try {
				var socket = new Socket(address, port);
				clientSocket = new ClientSocketHandler(socket);
				clientSocket.start();
				System.out.println("Client socket opened on: " + socket.getInetAddress().getHostAddress() + ", " + port);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void closeClientSocket() {
		if (sessionType == sessionType.CLIENT) {
			if (clientSocket != null && !clientSocket.socket.isClosed()) {
				try {
					clientSocket.closeClientSocket();
					System.out.println("Client socket closed");
				} catch (Exception e) {
					System.err.println("Failed to close clientSocket");
				}
			}
		}
	}
	
	public void handleIncomingMessage(NetworkMessage message, ClientSocketHandler sender) {
		if (sessionType == SessionType.HOST) {
			switch (message.opcode) {
			case HELLO:
				// Host should not receive Hello message
				break;
			case PLAYER_JOIN:
			{
				var playerUpdate = (PlayerUpdateNetworkMessage)message;
				if (playerUpdate != null) {
					var player = PlayerManager.getInstance().addNetworkPlayer(playerUpdate.username, playerUpdate.uID, null);
					sender.setPlayer(player);
				}
			}
				break;
			case PLAYER_LEAVE:
			{
				var playerUpdate = (PlayerUpdateNetworkMessage)message;
				if (playerUpdate != null) {
					var player = PlayerManager.getInstance().removePlayer(playerUpdate.uID);
					serverSocket.closeClientSocket(sender);
				}
			}
				break;
			case PLAYER_UPDATE:
			{
				var playerUpdate = (PlayerUpdateNetworkMessage)message;
				if (playerUpdate != null && sender.getPlayer() != null) {
					// Sender can only update themselves
					if (sender.getPlayer().getPlayerID().toString().equals(playerUpdate.uID)){
						PlayerManager.getInstance().updatePlayerName(null, null);					
					}
				}
			}
				break;
			case GAMEBOARD_UPDATE_ALL:
			{
				//Cannot be done by clients. Server will ignore message
				System.out.println("Client attempted to update full game board. Ignored by Server. " + 
						"Sender: " + sender.getPlayer().getName() + ", " + sender.getPlayer().getPlayerID().toString());
			}
				break;
			case GAMEBOARD_UPDATE_ONE:
			{
				var gameUpdate = (GameUpdateNetworkMessage)message;
				if (gameUpdate != null) {
					GameManager.getInstance().tryPlaceTile(gameUpdate.column, sender.getPlayer());
				}
			}
				break;
			case GAMESTATE_UPDATE:
			{
				//Cannot be done by clients. Server will ignore message
				System.out.println("Client attempted to update game state. Ignored by Server. " + 
						"Sender: " + sender.getPlayer().getName() + ", " + sender.getPlayer().getPlayerID().toString());
			}
				break;
			case MESSAGE_CREATE:
			{
				var messageUpdate = (ChatNetworkMessage)message;
				if (messageUpdate != null && messageUpdate.message != null) {
					ChatManager.getInstance().addMessage(messageUpdate.message, sender.getPlayer());
				}
				else {
					System.err.println("MESSAGE_CREATE message received with invalid payload");
				}
			}
				break;
			case SESSION_TERMINATE:
				//Cannot be done by clients. Server will ignore message
				System.out.println("Client attempted to terminate session. Ignored by Server. " + 
						"Sender: " + sender.getPlayer().getName() + ", " + sender.getPlayer().getPlayerID().toString());
				break;
			default:
				break;
				
			}
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
					ChatManager.getInstance().addMessage(messageUpdate.message, sender.getPlayer());
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
	
	public void onMessageReceived(Object data, ClientSocketHandler sender) {
		try {
			var message = (NetworkMessage)data;
			if (message != null) {
				handleIncomingMessage(message, sender);				
			}
			else {
				System.err.println("NetworkManager received message that not of type NetworkMessage");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
