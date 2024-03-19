package connectfour.model;

public class GameBoard {
	private short[][] tiles;
	
	public GameBoard() {
		this.tiles = new short[6][7];
	}
	
	//set array of tiles to new tiles being passed in (i.e. restarting, loading games)
	public void setBoardState(short[][] tiles) {
		this.tiles = tiles;
	}
	
	public void setTileState(short row, short column, short state){
		this.tiles[row][column] = state;
	}
	
	public boolean checkWinStates(short row, short column, short player) {
		
		return false;
	}
}
