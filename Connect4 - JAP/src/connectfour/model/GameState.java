package connectfour.model;

public enum GameState {
	PLAYER_1_TURN(1),
	PLAYER_2_TURN(2),
	PLAYER_1_WIN,
	PLAYER_2_WIN,
	DRAW,
	DEFAULT;
	
	private final int playerID;
	
	public int getPlayerID() {
		return playerID;
	}
	
	private GameState() {
		playerID = -1;
	}
	
	private GameState(int playerID) {
		this.playerID = playerID;
	}
}
