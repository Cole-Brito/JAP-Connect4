/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.model;

/**
 * Represents the Game Board using a 2d array of tile states.
 * Contains methods for updating the board and checking win states.
 */
public class GameBoard {
	
	/** 2d array of tile states representing the game board */
	private int[][] tiles;
	
	/** The number of rows on the game board */
	final static int NUM_ROWS = 6;
	/** The number of columns on the game board */
	final static int NUM_COLUMNS = 7;
	
	/**
	 * Instantiate the board with a new array of tiles
	 */
	public GameBoard() {
		this.tiles = new int[NUM_ROWS][NUM_COLUMNS];
	}
	
	/**
	 * Set array of tiles to new tiles being passed in (i.e. restarting, loading games)
	 * @param tiles The array of tile states
	 */
	public void setBoardState(int[][] tiles) {
		this.tiles = tiles;
	}
	
	/**
	 * Update the tile state for a specific row or column
	 * @param row The row index of the tile to update
	 * @param column The column index of the tile to update
	 * @param state The new state of the tile
	 */
	public void setTileState(int row, int column, int state){
		this.tiles[row][column] = state;
	}
	
	/**
	 * Attempts to place a tile in the specified column.
	 * @param column The column to place the tile in
	 * @param player The player to place for (ie. 1 or 2)
	 * @return The row the tile was placed in, or -1 if the column was full
	 */
	public int setTileInColumn(int column, int player) {
		int row = getFirstEmptyRow(column);
		if (row != -1) {
			setTileState(row, column, player);
		}
		return row;
	}
	
	/**
	 * Check if a player has won (ie. 4 tiles in a row).
	 * Does not check for draws.
	 * @param row The row that the new tile was placed at.
	 * @param column The column that the new tile was placed at.
	 * @param player The player that placed the tile (ie. 1 or 2)
	 * @return true if the player won, false otherwise
	 */
	public boolean checkWinStates(int row, int column, int player) {
		int minC = Math.max(column - 3, 0);
		int maxC = Math.min(column + 4, NUM_COLUMNS);
		int minR = Math.max(row - 3, 0);
		int maxR = Math.min(row + 4, NUM_ROWS);
		
		System.out.printf("minC: %d, maxC: %d, minR: %d, maxR: %d\n", minC, maxC, minR, maxR);
		//Checking left and right of clicked tile
		System.out.printf("c = %d, count = %d; c < %d; ++c", minC, 0, maxC);
		for(int c = minC, count = 0; c < maxC; ++c) {
			if (tiles[row][c] == player) {
				++count;
				System.out.println("Horizontal " + count);
				if (count >= 4) {
					System.out.println("Horizontal win");
					return true;
				}
			} else {
				count = 0;
			}
		}
		
		//Checking verticals
		for(int r = minR, count = 0; r < maxR; ++r) {
			if (tiles[r][column] == player) {
				++count;
				System.out.println("Vertical " + count);
				if (count >= 4) {
					return true;
				}
			} else {
				count = 0;
			}
		}
		//Checking Diagonal top-left to bottom-right		
		for(int diff = -3, count = 0; diff < 4; ++diff) {
			// Don't check if bounds are out of range
			if (row+diff < 0 || row+diff >= NUM_ROWS || column+diff < 0 || column+diff >= NUM_COLUMNS){
				continue;
			}
			
			if (tiles[row+diff][column+diff] == player) {
				++count;
				System.out.println("Diag1 " + count);
				if (count >= 4) {
					return true;
				}
			}
			else {
				count = 0;
			}
		}
		
		// Checking diagonal bottom-left to top-right
		for(int diff = -3, count = 0; diff < 4; ++diff) {
			// Don't check if bounds are out of range
			if (row-diff < 0 || row-diff >= NUM_ROWS || column+diff < 0 || column+diff >= NUM_COLUMNS){
				continue;
			}
			
			if (tiles[row-diff][column+diff] == player) {
				++count;
				System.out.println("Diag2 " + count);
				if (count >= 4) {
					return true;
				} 
			}
			else {
				count = 0;
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
	public int getTileState(int row, int column) {
		return tiles[row][column];
	}
	
	/**
	 * Gets the highest empty row in a column.
	 * Note that this checks from the bottom up, so row 0
	 * is the highest row (ie. last row to be placed in).
	 * @param column The column to check
	 * @return The row index of the first empty row, or -1 if column was full.
	 */
	public int getFirstEmptyRow(int column) {
		for (int i = NUM_ROWS -1; i >= 0; --i) {
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
		for(int i = 0; i < NUM_COLUMNS; ++i) {
			if (tiles[0][i] == 0) {
				return false;
			}
		}
		
		return true;
	}
}
