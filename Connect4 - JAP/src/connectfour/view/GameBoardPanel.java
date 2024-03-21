package connectfour.view;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
/**
 * Class to generate and control the behavior of the game board
 * @author Cole Brito, Paul Squires
 *
 */
public class GameBoardPanel {
	
	/** Array of tile JButtons*/
	GameBoardTile[][] tiles = new GameBoardTile[6][7];

	/**
	 * generates a grid of 52 x 52 pixel buttons holding an image of an empty tile space.
	 * @param content - The panels this is being added to
	 * @param c - the constrains from the gridbagLayout
	 */
	protected void generateTileGrid(JPanel content, GridBagConstraints c, ActionListener listener) {
		//Grid of buttons 	
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[i].length; j++) {
					tiles[i][j] = new GameBoardTile(i, j);
		            tiles[i][j].updateState(TileState.DEFAULT);
					tiles[i][j].setBorder(blackLine);
					tiles[i][j].addActionListener(listener);
					c.gridx = j;
					c.gridy = i;
					content.add(tiles[i][j], c);
				}
			}
	}
	
	/**
     * Registers an ActionListener for all GameBoardTiles.
     * @param listener The ActionListener to register.
     */
    public void registerTileActionListener(ActionListener listener) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].addActionListener(listener);
            }
        }
    }
//	/** Action Listener for testing tile placements*/
//	ActionListener actionListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JButton source = (JButton) e.getSource();
//            // Find the source button in the tile 2D array
//            for (int i = 0; i < tiles.length; i++) {
//                for (int j = 0; j < tiles[i].length; j++) {
//                    if (tiles[i][j] == source) {
//                        System.out.println("Button at row " + i + ", column " + j + " was clicked.");
//                        tiles[i][j].updateState(TileState.PLAYER_1); //Temp, testing the listener
//                        return;
//                    }
//                }
//            }
//        }
//    };
    
    public void updateBoardState(int column, int row, GameBoardTile state) {
    	this.tiles[row][column] = state;
    }
    
    public void updateFullBoard(short[][] tiles) {
    	
    }
	
}
