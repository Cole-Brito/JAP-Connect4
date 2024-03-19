package connectfour.model;

import java.util.ArrayList;

public class PlayerManager {
	private ArrayList<Player> players;
	
	 public PlayerManager() {
        this.players = new ArrayList<>();
	 }
	 
	/**
	 * This method takes the name and the type of player
	 * @param userName - name of the user being created 
	 * @param type - the type of player being created 
	 */
	public boolean addPlayer(String userName, PlayerType type) {
		Player player = new Player();
		player.setName(userName);
		player.setPlayerType(type);
		
		return players.add(player);
	}
}
