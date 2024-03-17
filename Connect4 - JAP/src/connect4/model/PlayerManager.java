package connect4.model;

import java.util.ArrayList;

public class PlayerManager {
	private ArrayList<Player> players;
	
	/**
	 * This method takes the name and the type of player
	 * @param userName - name of the user being created 
	 * @param type - the type of player being created 
	 */
	public void addPlayer(String userName, PlayerType type) {
		Player player = new Player();
		player.setName(userName);
		player.setPlayerType(type);
		
		players.add(player);
	}
}
