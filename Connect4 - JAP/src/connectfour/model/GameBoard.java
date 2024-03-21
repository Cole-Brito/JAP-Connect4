package connectfour.model;

public class GameBoard {
	private short[][] tiles;
	
	//Board should never be bigger or smaller than this
	final static short NUM_ROWS = 6;
	final static short NUM_COLUMNS = 7;
	
	public GameBoard() {
		this.tiles = new short[NUM_ROWS][NUM_COLUMNS];
	}
	
	//set array of tiles to new tiles being passed in (i.e. restarting, loading games)
	public void setBoardState(short[][] tiles) {
		this.tiles = tiles;
	}
	
	public void setTileState(short row, short column, short state){
		this.tiles[row][column] = state;
	}
	
	public boolean checkWinStates(short row, short column, Player player) {
		//Horizontal Check
		int tile = 1;
		int c = column;
		
		//Left check
		while (c > 0 && this.tiles[row][c - 1] == player) {
			tile++;
			c--;
		}
			c = column;
		
		//Right Check
		while (c < NUM_COLUMNS - 1 && tiles[row][c + 1] == player) { //Need a get playerID or something idk
			tile++;
			c++;
		}
		
		if (tile >= 4) {
			return true;
		}
		
		
		return false;
	}
}
