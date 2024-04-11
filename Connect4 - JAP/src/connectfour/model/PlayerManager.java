/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import connectfour.model.GameManager.GameBoardPropertyChangedEvent;

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
	
	/** 
	 * Default local player 1, should never be null 
	 * Also used as local player in network games
	 */
	private Player localPlayer1;
	/** Default local player 2, should never be null */
	private Player localPlayer2;
	
	/** The default name to use for local player 1 */
	private static final String PLAYER1_DEFAULT_NAME = "Player1";
	/** The default name to use for local player 2 */
	private static final String PLAYER2_DEFAULT_NAME = "Player2";
	
	/** PropertyChangeSupport for notifying listeners when player list changes */
	private final PropertyChangeSupport propertyChangedSupport;

	/**
	 * Private constructor for PlayerManager singleton.
	 * Sets default players.
	 */
	private PlayerManager() {
		this.players = new ArrayList<>();
		this.propertyChangedSupport = new PropertyChangeSupport(this);
		addDefaultPlayers();
	}
	
	/**
	 * This method takes the name and the type of player
	 * @param userName - name of the user being created 
	 * @param type - the type of player being created 
	 * @return the player that was added, or null if no player was added
	 */
	public Player addPlayer(String userName, PlayerType type) {
		Player player = new Player(userName, type);
		if(players.add(player)) {
			onPlayerListChanged(null, player);
			return player;
		}
		return null;
	}
	
	/**
	 * Adds a remote player with the given socket
	 * @param userName - name of the user being created 
	 * @param id - the GUID of the player
	 * @return the player that was added, or null if no player was added
	 */
	public Player addNetworkPlayer(String username, String id) {
		if (playerExists(id)) {
			System.out.println("Player already exists with given UUID: " + username + ", " + id);
			System.out.println("Expected behaviour during Hello Network Message");
			//TODO:
			for(var p: players) {
				System.out.println(p.getName() + ": " + p.getPlayerID() + ", " + p.getPlayerType());
			}
			return getPlayer(id);
		}
		Player player = new Player(username, id, PlayerType.NETWORK);
		if(players.add(player)) {
			onPlayerListChanged(null, player);
			return player;
		}
		return null;
	}
	
	/**
	 * Removes a player with the given UUID
	 * @param id The String representation of the UUID of the player
	 * @return the player if they were removed, or null
	 */
	public Player removePlayer(String id) {
		if (id == null) {
			return null;
		}
		var player = getPlayer(id);
		if (player != null)
		{
			players.remove(player);
			onPlayerListChanged(player, null);
			return player;
		}
		return null;
	}
	
	/**
	 * Updates the username of a player with the given ID, and also notifies PropertyChangeListeners
	 * @param uID The UUID of the player
	 * @param newUsername the new username for the player
	 */
	public void updatePlayerName(String uID, String newUsername) {
		var player = getPlayer(uID);
		if (player != null && !player.getName().equals(newUsername)) {
			player.setName(newUsername);
			onPlayerUpdated(player);
		}
		else {
			System.out.println("Player not found in updatePlayerName or player name was the same: " + 
					uID + ": " + newUsername);
		}
	}
	
	/**
	 * Updates the username of a given player, and also notifies PropertyChangeListeners
	 * @param player The player to update
	 * @param newUsername the new username for the player
	 */
	public void updatePlayerName(Player player, String newUsername) {
		updatePlayerName(player.getPlayerID().toString(), newUsername);
	}
	
	/**
	 * Gets a player from their UUID
	 * @param uID The UUID of the player
	 * @return The player if found, or null otherwise
	 */
	public Player getPlayer(String uID) {
		for (var player: players) {
			try {
				if (player.getPlayerID().equals(UUID.fromString(uID))) {
					return player;
				}				
			}
			catch (Exception ex) {
				System.err.println("Exception caught when getting player from UUID: " + ex.getMessage());
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
	 * Gets the default local player for player slot 1
	 * @return localPlayer1
	 */
	public Player getLocalPlayer1() {
		return localPlayer1;
	}
	
	/**
	 * Gets the default local player for player slot 2
	 * @return localPlayer2
	 */
	public Player getLocalPlayer2() {
		return localPlayer2;
	}
	
	/**
	 * Resets the player list and sets initial players to only the local client player
	 * TODO: May not be required, local players should not interfere with network
	 */
	public void setDefaultNetworkPlayers() {
		this.players.clear();
		this.players.add(localPlayer1);
	}
	
	/**
	 * Resets the player list and sets initial players to only the local client player
	 */
	public void setDefaultLocalPlayers() {
		this.players.clear();
		this.players.add(localPlayer1);
		this.players.add(localPlayer2);
	}

	/**
	 * Adds default local players for player 1 and 2.
	 */
	private void addDefaultPlayers(){
		localPlayer1 = (new Player(PLAYER1_DEFAULT_NAME, PlayerType.LOCAL));
		localPlayer2 = (new Player(PLAYER2_DEFAULT_NAME, PlayerType.LOCAL));
		players.add(localPlayer1);
		players.add(localPlayer2);
	}
	
	/**
	 * Check if a player exists in the player list
	 * @param uID The UUID of the player
	 * @return true if a player with uID was found, false otherwise
	 */
	private boolean playerExists(String uID) {
		for (var player: players) {
			if (player.getPlayerID().equals(UUID.fromString(uID))) {
				return true;
			}
		}
		return false;
	}
	
	
/********** PropertyChanged fields and methods **********/
	
	/** Property Name used to notify PropertyChange events when a player is added or removed */
	public static final String PLAYER_LIST_PROPERTY_NAME = "PlayerList";
	/** Property Name used to notify PropertyChange events when a player state changes */
	public static final String PLAYER_UPDATE_PROPERTY_NAME = "PlayerUpdate";
	
	/**
	 * Registers a PropertyChangeListener to responds to changes to this model.
	 * Event will be fired upon changes to Player List, Players' usernames, etc.<br>
	 * 
	 * @param propertyName The name of the property for the listener to respond to.
	 * @param listener The {@link PropertyChangeListener} that responds to Player List being changed.
	 */
	public void registerPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangedSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * Notify PropertyChangeListeners when the player list changes
	 * @param oldPlayer The original player if they are being removed
	 * @param newPlayer The new player if they are being added
	 */
	public void onPlayerListChanged(Player oldPlayer, Player newPlayer) {
		propertyChangedSupport.firePropertyChange(PLAYER_LIST_PROPERTY_NAME, oldPlayer, newPlayer);
	}
	
	/**
	 * Notify PropertyChangeListeners when the player list changes
	 * @param player The player being updated
	 */
	public void onPlayerUpdated(Player player) {
		propertyChangedSupport.firePropertyChange(PLAYER_UPDATE_PROPERTY_NAME, null, player);
	}
}
