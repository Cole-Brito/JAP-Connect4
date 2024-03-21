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
	
	/**
	 * Attempts to place a tile in the specified column.
	 * @param column The column to place the tile in
	 * @param player The player to place for (ie. 1 or 2)
	 * @return The row the tile was placed in, or -1 if the column was full
	 */
	public short setTileInColumn(short column, short player) {
		short row = getFirstEmptyRow(column);
		if (row != -1) {
			setTileState(row, column, player);
		}
		return row;
	}
	
	//TODO: change return to enum or short to account for draws (ie. EMPTY, P1WIN, P2WIN, DRAW
	// may have overlap with GameManager enums
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
		//Checking Diagonal top-left to bottom-right
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
		
		// Checking diagonal bottom-left to top-right
		for(int c = minC, r = maxR - 1, count = 0; c < maxC && r >= 0; --r, ++c) {
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

	/**
	 * Gets the tile state for a specific tile
	 * @param row The row index of the tile
	 * @param column The column index of the tile
	 * @return The tile state (ie. 1 for player1, 2 for player2, 0 for empty)
	 */
	public short getTileState(short row, short column) {
		return tiles[row][column];
	}
	
	/**
	 * Gets the highest empty row in a column.
	 * Note that this checks from the bottom up, so row 0
	 * is the highest row (ie. last row to be placed in).
	 * @param column The column to check
	 * @return The row index of the first empty row, or -1 if column was full.
	 */
	public short getFirstEmptyRow(short column) {
		for (short i = NUM_ROWS -1; i > 0; --i) {
			if (tiles[i][column] == 0) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Checks if the board is full by checking each highest column for a tile.
	 * @return true if all columns are full, or false if any columns are empty.
	 */
	public boolean isBoardFull() {
		for(short i = 0; i < NUM_COLUMNS; ++i) {
			if (tiles[0][i] == 0) {
				return false;
			}
		}
		
		return true;
	}
}
