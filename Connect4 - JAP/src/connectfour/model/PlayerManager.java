package connectfour.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerManager {
	
	private static PlayerManager _instance;
	public static PlayerManager getInstance() {
		if (_instance == null) {
			_instance = new PlayerManager();
		}
		return _instance;
	}
	
	private ArrayList<Player> players;
	
	private static final String PLAYER1_DEFAULT_NAME = "Player1";
	private static final String PLAYER2_DEFAULT_NAME = "Player2";
	
	 private PlayerManager() {
        this.players = new ArrayList<>();
        addDefaultPlayers();
	 }
	 
	/**
	 * This method takes the name and the type of player
	 * @param userName - name of the user being created 
	 * @param type - the type of player being created 
	 */
	public boolean addPlayer(String userName, PlayerType type) {
		Player player = new Player(userName, type);
		
		return players.add(player);
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	 private void addDefaultPlayers(){
		 players.add(new Player(PLAYER1_DEFAULT_NAME, PlayerType.LOCAL));
		 players.add(new Player(PLAYER2_DEFAULT_NAME, PlayerType.LOCAL));
	 }
}
