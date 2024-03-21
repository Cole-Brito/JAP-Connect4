package connectfour.model;

/**
 * 
 * @author Cole Brito
 * This class is used for creating player objects 
 */
public class Player {
	private String username;
	private PlayerType playerType;
	
	Player(String name, PlayerType type){
		this.username = name;
		this.playerType = type;
	}
	
	public void setName(String name) {
		this.username = name;
	}
	
	public void setPlayerType(PlayerType type) {
		this.playerType = type;
	}
}