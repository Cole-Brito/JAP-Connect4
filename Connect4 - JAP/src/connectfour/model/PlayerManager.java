/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tracks a list of Players and their Player Types
 * @author Paul
 *
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
		//TODO: Add logic for validating network players
		return players.add(player);
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
}
