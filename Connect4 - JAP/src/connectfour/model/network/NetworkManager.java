package connectfour.model.network;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.GameManager.GameWinCountChangedEvent;
import connectfour.model.GameState;
import connectfour.model.Player;
import connectfour.model.PlayerManager;
import connectfour.model.PlayerType;
import connectfour.model.network.ClientSocketHandler.ClientState;
import connectfour.model.network.HelloNetworkMessage.PlayerPayload;
import connectfour.model.network.NetworkMessage.Opcode;

public class NetworkManager implements PropertyChangeListener {
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
	
	/**
	 * Valid network session types
	 */
	public enum SessionType{
		OFFLINE,
		HOST,
		CLIENT
	}
	
	/** The current session type */
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
	
	/**
	 * Gets the current session type
	 * @return {@link SessionType}
	 */
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
			closeClientSocket();
		}
		
		PlayerManager.getInstance().updatePlayerName(PlayerManager.getInstance().getLocalPlayer1().getPlayerID().toString(), "Host");
		if (serverSocket == null || serverSocket.server.isClosed()) {
			try {
				serverSocket = new ServerSocketHandler(port);
				serverSocket.start();
				this.sessionType = SessionType.HOST;
				PlayerManager.getInstance().setDefaultNetworkPlayers();
				GameManager.getInstance().resetToNetworkGameState(true);
			} catch (Exception e) {
				System.err.println("Failed to open server socket on port: " + port);
				e.printStackTrace();
				return -1;
			}
		}
		else {
			System.out.println("Server is already running");
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
			this.sessionType = SessionType.OFFLINE;
			PlayerManager.getInstance().setDefaultLocalPlayers();
			GameManager.getInstance().resetToLocalGameState();
		}
	}
	
	public boolean openClientSocket(String address, int port) {
		if (sessionType == SessionType.OFFLINE) {
			try {
				GameManager.getInstance().resetToNetworkGameState(false);
				PlayerManager.getInstance().setDefaultNetworkPlayers();
				PlayerManager.getInstance().updatePlayerName(PlayerManager.getInstance().getLocalPlayer1().getPlayerID().toString(), "Client");
				var socket = new Socket(address, port);
				clientSocket = new ClientSocketHandler(socket);
				clientSocket.start();
				this.sessionType = SessionType.CLIENT;
				System.out.println("Client socket opened on: " + socket.getInetAddress().getHostAddress() + ", " + port);
				var player = PlayerManager.getInstance().getLocalPlayer1();
				sendPlayerJoinMessage(player);
				//clientSocket.sendMessage(new PlayerUpdateNetworkMessage(Opcode.PLAYER_JOIN, 
				//		player.getPlayerID().toString(), player.getName(), null));
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PlayerManager.getInstance().setDefaultLocalPlayers();
		GameManager.getInstance().resetToLocalGameState();
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
			this.sessionType = SessionType.OFFLINE;
			PlayerManager.getInstance().setDefaultLocalPlayers();
			GameManager.getInstance().resetToLocalGameState();
		}
	}
	
	public void sendNetworkMessage(NetworkMessage message) {
		if (sessionType == SessionType.HOST) {
			serverSocket.broadcastMessage(message);
		}
		else if (sessionType == SessionType.CLIENT) {
			clientSocket.sendMessage(message);
		}
	}
	
	/**
	 * Handles incoming NetworkMessages based on the current session type and message opcode
	 * @param message The NetworkMessage received
	 * @param sender The socket that the message was received from
	 */
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
					var player = PlayerManager.getInstance().addNetworkPlayer(playerUpdate.username, playerUpdate.uID);
					sender.setPlayer(player);
					if (GameManager.getInstance().getPlayer2() == null) {
						GameManager.getInstance().setPlayer2(player);
						System.out.println("Player 2 set by PLAYER_JOIN: " + GameManager.getInstance().getPlayer2());
					}
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
				System.out.println("Client attempted to update full game board. Ignored by Server."); 
				if (sender.getPlayer() != null)
					System.err.println("Sender: " + sender.getPlayer().getName() + ", " + sender.getPlayer().getPlayerID().toString());
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
				System.out.println("Client attempted to update game state. Ignored by Server."); 
				if (sender.getPlayer() != null)
					System.err.println("Sender: " + sender.getPlayer().getName() + ", " + sender.getPlayer().getPlayerID().toString());
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
				System.out.println("Client attempted to terminate session. Ignored by Server."); 
				if (sender.getPlayer() != null)
					System.err.println("Sender: " + sender.getPlayer().getName() + ", " + sender.getPlayer().getPlayerID().toString());
				break;
			default:
				System.err.println("Server Received invalid Opcode.");
				if (sender.getPlayer() != null)
					System.err.println("Sender: " + sender.getPlayer().getName() + ", " + sender.getPlayer().getPlayerID().toString());
				break;
				
			}
		}
		else if (sessionType == SessionType.CLIENT) {
			switch (message.opcode) {
			case HELLO:
			{
				var hello = (HelloNetworkMessage)message;
				var gameManager = GameManager.getInstance();
				gameManager.updateGameState(hello.gameState);
				gameManager.updateGameBoardDirect(hello.gameBoard);
				gameManager.setPlayer1WinCount(hello.player1WinCount);
				gameManager.setPlayer2WinCount(hello.player2WinCount);
				addPlayersFromPayload(hello.players);
			}
				break;
			case PLAYER_JOIN:
			{
				var playerUpdate = (PlayerUpdateNetworkMessage)message;
				if (playerUpdate != null) {
					PlayerManager.getInstance().addNetworkPlayer(playerUpdate.username, playerUpdate.uID);
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
					System.out.println("PLAYER_UPDATE message: " + playerUpdate.uID + ", " + playerUpdate.username + ", " + playerUpdate.playerState);
					//TODO: rename player or promote (/demote) player to active player
					var player = PlayerManager.getInstance().getPlayer(playerUpdate.uID);
					if (player != null) {
						if (playerUpdate.username != null) {
							PlayerManager.getInstance().updatePlayerName(playerUpdate.uID, playerUpdate.username);
						}
						if (playerUpdate.playerState == PlayerUpdateNetworkMessage.PLAYER_1_STATE) {
							GameManager.getInstance().setPlayer1(player);
							System.out.println("Set player 1: " + player.getName());
						}
						else if (playerUpdate.playerState == PlayerUpdateNetworkMessage.PLAYER_2_STATE) {
							System.out.println("Set player 2: " + player.getName());
							GameManager.getInstance().setPlayer2(player);
						}
					}
					else {
						System.err.println("PLAYER_UPDATE could not find player in PlayerManager: " + 
							playerUpdate.uID + ": " + playerUpdate.username);
					}
				}
			}
				break;
			case GAMEBOARD_UPDATE_ALL:
			{
				var gameUpdate = (GameUpdateNetworkMessage)message;
				if (gameUpdate != null && gameUpdate.gameBoard != null) {
					GameManager.getInstance().updateGameBoardDirect(gameUpdate.gameBoard);
				}
			}
				break;
			case GAMEBOARD_UPDATE_ONE:
			{
				var gameUpdate = (GameUpdateNetworkMessage)message;
				if (gameUpdate != null) {
					if (gameUpdate.row == null || gameUpdate.column == null || gameUpdate.state == null) {
						System.err.println("GAMEBOARD_UPDATE_ONE received GameUpdateNetworkMessage with null row or column");
						System.out.println("GameUpdateNetworkMessage: [" + gameUpdate.row + ", " + gameUpdate.column + ", " + gameUpdate.state + "]");
					}
					else {
						GameManager.getInstance().updateGameTileDirect(gameUpdate.row, gameUpdate.column, gameUpdate.state);						
					}
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
					System.out.println("Message Create: " + messageUpdate.message);
					ChatManager.getInstance().addFormattedMessage(messageUpdate.message);
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
	
	/**
	 * Handles NetworkMessage data received from a client socket
	 * @param data The data that was sent, assumed to be a NetworkMessage
	 * @param sender The socket that the data was received from
	 */
	public void onMessageReceived(Object data, ClientSocketHandler sender) {
		try {
			var message = (NetworkMessage)data;
			if (message != null) {
				System.out.println("Message received: " + message.opcode);
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
	
	/**
	 * Sends a GameUpdateNetworkMessage over the network
	 * @param row The row index on game board to update. May be null
	 * @param column The column index on game board to update. Should not be null
	 * @param state The new state of the tile. May be null
	 */
	public void sendGameBoardUpdateMessage(Integer row, Integer column, Integer state) {
		var message = new GameUpdateNetworkMessage(Opcode.GAMEBOARD_UPDATE_ONE, row, column, state);
		System.out.println("Sending GameBoard update: [" + message.row + ", " + message.column + ", " + message.state + "]");
		sendNetworkMessage(message);
	}
	
	/**
	 * Sends a GameUpdateNetworkMessage over the network
	 * @param row The row index on game board to update. May be null
	 * @param column The column index on game board to update. Should not be null
	 * @param state The new state of the tile. May be null
	 */
	public void sendGameBoardFullUpdateMessage(int[][] gameBoard) {
		var message = new GameUpdateNetworkMessage(Opcode.GAMEBOARD_UPDATE_ALL, null, gameBoard);
		sendNetworkMessage(message);
	}
	
	/**
	 * Sends a GameUpdateNetworkMessage over the network
	 * @param state The new game state. May be null.
	 * @param p1Wins The updated player 1 win count. May be null.
	 * @param p2Wins The updated player 2 win count. May be null.
	 */
	public void sendGameStateUpdateMessage(GameState state, Integer p1Wins, Integer p2Wins) {
		var message = new GameUpdateNetworkMessage(Opcode.GAMESTATE_UPDATE, state.ordinal(), null, p1Wins, p2Wins);
		sendNetworkMessage(message);
	}
	
	/**
	 * Send a PlayerUpdateNetworkMessage to notify clients that a new player joined
	 * @param player The player that is joining
	 */
	public void sendPlayerJoinMessage(Player player) {
		if (player != null) {
			Integer playerState = 0;
			// We assume host is always player 1 for now
			if (sessionType == SessionType.HOST && player.equals(GameManager.getInstance().getPlayer2())) {
				playerState = 2;
			}
			var message = new PlayerUpdateNetworkMessage(Opcode.PLAYER_JOIN, 
					player.getPlayerID().toString(), player.getName(), playerState);
			sendNetworkMessage(message);
		}
	}
	
	/**
	 * Send a PlayerUpdateNetworkMessage to notify clients that a player disconnected
	 * @param player The player that is leaving
	 */
	public void sendPlayerLeaveMessage(Player player) {
		if (player != null) {
			var message = new PlayerUpdateNetworkMessage(Opcode.PLAYER_LEAVE, 
					player.getPlayerID().toString(), player.getName(), null);
			sendNetworkMessage(message);
		}
	}
	
	/**
	 * Send a PlayerUpdateNetworkMessage to notify clients that a player updated
	 * @param player The player being updated
	 * @param playerState The new state of the player, if applicable
	 */
	public void sendPlayerUpdateMessage(Player player, Integer playerState) {
		if (player != null) {
			var message = new PlayerUpdateNetworkMessage(Opcode.PLAYER_UPDATE, 
					player.getPlayerID().toString(), player.getName(), playerState);
			sendNetworkMessage(message);
		}
	}
	
	/**
	 * Sends a ChatNetworkMessage over the network
	 * @param chatMessage The chat message to send
	 */
	public void sendChatMessage(String chatMessage) {
		var message = new ChatNetworkMessage(Opcode.MESSAGE_CREATE, chatMessage);
		sendNetworkMessage(message);
	}
	
	/**
	 * Add a list of players from a PlayerPayload array received from a NetworkMessage
	 * @param payload The PlayerPayload array with the player info
	 */
	public void addPlayersFromPayload(HelloNetworkMessage.PlayerPayload[] payload) {
		if (payload == null) {
			System.out.println("PlayerPayload array was null in addPlayersFromPayload");
			return;
		}
		for(var p: payload) {
			addPlayerFromPayload(p);
		}
	}
	
	/**
	 * Add a player from a PlayerPayload received from a NetworkMessage
	 * @param payload The PlayerPayload with the player info
	 */
	public void addPlayerFromPayload(PlayerPayload payload) {
		if (payload == null) {
			System.out.println("PlayerPayload was null in addPlayerFromPayload");
			return;
		}
		var player = PlayerManager.getInstance().addNetworkPlayer(payload.username, payload.uID);
		if (player != null) {
			System.out.println("Added player: " + player.getName() + ": " + player.getPlayerID() + ", " + payload.playerState);
			if (payload.playerState == 1) {
				GameManager.getInstance().setPlayer1(player);
				System.out.println("Player 1 set from payload: " + GameManager.getInstance().getPlayer1());
			}
			else if (payload.playerState == 2) {
				GameManager.getInstance().setPlayer2(player);
				System.out.println("Player 2 set from payload: " + GameManager.getInstance().getPlayer2());
			}
		}
		else {
			System.out.println("Did not add player from payload: " + payload.uID + ": " + payload.username);
		}
	}
	
	/**
	 * Creates a message for the Hello opcode to send over the network
	 * @return HelloNetworkMessage with necessary data
	 */
	public HelloNetworkMessage createHelloMessage() {	
		var gameManager = GameManager.getInstance();
		var message = new HelloNetworkMessage(Opcode.HELLO, 
				gameManager.getGameState().ordinal(), 
				gameManager.getBoardState(),
				gameManager.getPlayer1WinCount(),
				gameManager.getPlayer2WinCount());
		List<PlayerPayload> payload = new ArrayList<>();
		for(var player: PlayerManager.getInstance().getPlayers()) {
			Integer playerState = 0;
			if (gameManager.getPlayer1().equals(player)) {playerState = 1;}
			else if (gameManager.getPlayer2().equals(player)) {playerState = 2;}
			payload.add(message.new PlayerPayload(player.getName(), 
					player.getPlayerID().toString(), playerState));
		}
		
		message.players = new PlayerPayload[payload.size()];
		message.players = payload.toArray(message.players);
		
		return message;
	}

	/**
	 * PropertyChange event that responds to Game State changes
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Only respond to model property changes if we are the Host.
		// This way we only send updates from the host.
		if (sessionType == SessionType.HOST) {
			switch(evt.getPropertyName()) {
			case GameManager.GAME_BOARD_TILE_PROPERTY_NAME:
			{
				var gameUpdate = (GameManager.GameBoardPropertyChangedEvent)evt.getNewValue();
				if (gameUpdate != null) {
					sendGameBoardUpdateMessage(gameUpdate.row, gameUpdate.column, gameUpdate.state);
				}
			}
				break;
			case GameManager.GAME_BOARD_FULL_PROPERTY_NAME:
			{
				var gameBoard = (int[][])evt.getNewValue();
				if (gameBoard != null) {
					sendGameBoardFullUpdateMessage(gameBoard);
				}
			}
				break;
			case GameManager.GAME_STATE_PROPERTY_NAME:
			{
				//var oldState = (GameState)evt.getOldValue();
				var newState = (GameState)evt.getNewValue();
				if (newState != null) {
					sendGameStateUpdateMessage(newState, null, null);
				}
			}
				break;
			case GameManager.GAME_WIN_COUNT_PROPERTY_NAME:
			{
				var winCountEvent = (GameWinCountChangedEvent)evt.getNewValue();
				if (winCountEvent != null) {
					sendGameBoardUpdateMessage(null, winCountEvent.player1WinCount, winCountEvent.player2WinCount);
				}
			}
				break;
			case GameManager.GAME_PLAYER1_CHANGE_PROPERTY_NAME:
			{
				var oldP1 = (Player)evt.getOldValue();
				var newP1 = (Player)evt.getNewValue();
				if (newP1 == null) {
					sendPlayerUpdateMessage(null, PlayerUpdateNetworkMessage.PLAYER_DEFAULT_STATE);
				}
				if (!newP1.equals(oldP1)) {
					sendPlayerUpdateMessage(newP1, PlayerUpdateNetworkMessage.PLAYER_1_STATE);
				}
			}
				break;
			case GameManager.GAME_PLAYER2_CHANGE_PROPERTY_NAME:
			{
				var oldP2 = (Player)evt.getOldValue();
				var newP2 = (Player)evt.getNewValue();
				if (newP2 == null) {
					sendPlayerUpdateMessage(null, PlayerUpdateNetworkMessage.PLAYER_DEFAULT_STATE);
				}
				else if (!newP2.equals(oldP2)) {
					sendPlayerUpdateMessage(newP2, PlayerUpdateNetworkMessage.PLAYER_2_STATE);
				}
			}
				break;
			case PlayerManager.PLAYER_LIST_PROPERTY_NAME:
			{
				Player oldPlayer = (Player)evt.getOldValue();
				Player newPlayer = (Player)evt.getNewValue();
				if (oldPlayer == null && newPlayer != null) {
					sendPlayerJoinMessage(newPlayer);
				}
				else if (oldPlayer != null && newPlayer == null) {
					sendPlayerLeaveMessage(oldPlayer);
				}
			}
				break;
			case PlayerManager.PLAYER_UPDATE_PROPERTY_NAME:
			{
				Player newPlayer = (Player)evt.getNewValue();
				if (newPlayer != null) {
					sendPlayerUpdateMessage(newPlayer, null);
				}
			}				
				break;
			case ChatManager.CHAT_HISTORY_PROPERTY_NAME:
			{
				var chatEvent = (ChatManager.MessageEventValue)evt.getNewValue();
				if (chatEvent != null) {
					sendChatMessage(chatEvent.message);					
				}
			}
				break;
			}
		}
	}
}
