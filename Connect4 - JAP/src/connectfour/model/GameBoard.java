package connectfour.model;

public class GameBoard {
	private short[][] tiles;
	
	public GameBoard() {
		tiles = new short[6][7];
	}
	
	public void setBoardState(short[][] tiles) {
		
	}
	
	public void setTileState(short row, short column, short state){
		
	}
	
	public boolean checkWinStates(short row, short column, short player) {
		
		return false;
	}
}
