/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 5, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Tracks a list of Players and their Player Types
 */
public class PlayerManager {
	
	/** The singleton instance of PlayerManager */
	private static PlayerManager _instance;
	/**
	 * Gets the singleton instance of PlayerManager.
	 * Constructs a new PlayerManager only the first time it is called.
	 * @return The instance of PlayerManager
	 */
	public static PlayerManager getInstance() {
		if (_instance == null) {
			_instance = new PlayerManager();
		}
		return _instance;
	}
	
	/** The list of players */
	private ArrayList<Player> players;
	
	/** The default name to use for local player 1 */
	private static final String PLAYER1_DEFAULT_NAME = "Player1";
	/** The default name to use for local player 2 */
	private static final String PLAYER2_DEFAULT_NAME = "Player2";

	/**
	 * Private constructor for PlayerManager singleton.
	 * Sets default players.
	 */
	private PlayerManager() {
		this.players = new ArrayList<>();
		addDefaultPlayers();
	}
	
	/**
	 * This method takes the name and the type of player
	 * @param userName - name of the user being created 
	 * @param type - the type of player being created 
	 * @return true if the player was added successfully
	 */
	public boolean addPlayer(String userName, PlayerType type) {
		Player player = new Player(userName, type);
		return players.add(player);
	}
	
	/**
	 * Adds a remote player with the given socket
	 * @param userName - name of the user being created 
	 * @param id - the GUID of the player
	 * @return true if the player was added successfully
	 */
	public boolean addNetworkPlayer(String username, String id, Socket socket) {
		if (playerExists(id)) {
			System.err.println("Attempt to add player with conflicting UUID: " + username + ", " + id);
			return false;
		}
		Player player = new Player(username, id, PlayerType.NETWORK, socket);
		return players.add(player);
	}
	
	/**
	 * Removes a player with the given UUID
	 * @param id The String representation of the UUID of the player
	 * @return true if the player was removed successfully
	 */
	public boolean removePlayer(String id) {
		if (id == null) {
			return false;
		}
		return players.removeIf(p -> p.getPlayerID().equals(UUID.fromString(id)));
	}
	
	/**
	 * Gets a player from their UUID
	 * @param uID The UUID of the player
	 * @return The player if found, or null otherwise
	 */
	public Player getPlayer(String uID) {
		for (var player: players) {
			if (player.getPlayerID().equals(UUID.fromString(uID))) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Gets the list of players
	 * @return readonly list of players
	 */
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/**
	 * Adds default local players for player 1 and 2.
	 */
	private void addDefaultPlayers(){
		players.add(new Player(PLAYER1_DEFAULT_NAME, PlayerType.LOCAL));
		players.add(new Player(PLAYER2_DEFAULT_NAME, PlayerType.LOCAL));
	}
	
	private boolean playerExists(String uID) {
		for (var player: players) {
			if (player.getPlayerID().equals(UUID.fromString(uID))) {
				return true;
			}
		}
		return false;
	}
}
