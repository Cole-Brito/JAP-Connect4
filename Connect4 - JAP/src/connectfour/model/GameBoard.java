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
	
	public boolean checkWinStates(short row, short column, short player) {
		int minC = Math.max(column - 3, 0);
		int maxC = Math.min(column + 3, NUM_COLUMNS);
		int minR = Math.max(row - 3, 0);
		int maxR = Math.min(row + 3, NUM_ROWS);
		
		//Checking left and right of clicked tile
		for(int c = minC, count = 0; c < maxC; ++c) {
			if (tiles[row][c] == player) {
				++count;
				if (count >= 4) {
					return true;
				}
			} else {
				count = 0;
			}
		}
		
		//Checking verticals
		for(int r = minR, count = 0; r < maxR; ++r) {
			if (tiles[column][r] == player) {
				++count;
				if (count >= 4) {
					return true;
				}
			} else {
				count = 0;
			}
		}
		//Checking Diagonal
		for(int c = minC, r = minR, count = 0; c < maxC && r < maxR; ++r, ++c) {
			if (tiles[r][c] == player) {
				++count;
				if (count >= 4) {
					return true;
				} else {
					count = 0;
				}
			}
		}
		
		return false;
	}

	public int getTileState(short row, short column) {
		return tiles[row][column];
	}
}
