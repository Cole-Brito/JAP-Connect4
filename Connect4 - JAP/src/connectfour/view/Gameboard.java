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
public class Gameboard {
	
	JButton[][] tile = new JButton[6][7];
	//declaring asset variables 
	ImageIcon emptyTile = new ImageIcon(getClass().getResource("/Connect4Assets/emptyTile.png"));
	ImageIcon redTile = new ImageIcon(getClass().getResource("/Connect4Assets/redTile.png"));
	ImageIcon blackTile = new ImageIcon(getClass().getResource("/Connect4Assets/blackTile.png"));
	
	/**
	 * generates a grid of 52 x 52 pixel buttons holding an image of an empty tile space.
	 * @param content - The panels this is being added to
	 * @param c - the constrains from the gridbagLayout
	 */
	protected void generateTileGrid(JPanel content, GridBagConstraints c) {
		//Grid of buttons 	
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
			for (int i = 0; i < tile.length; i++) {
				for (int j = 0; j < tile[i].length; j++) {
					tile[i][j] = new JButton(emptyTile);
					tile[i][j].setBorder(blackLine);
					tile[i][j].addActionListener(actionListener);
					c.gridx = j;
					c.gridy = i;
					content.add(tile[i][j], c);
				}
			}
	}
	
	ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            // Find the source button in the tile 2D array
            for (int i = 0; i < tile.length; i++) {
                for (int j = 0; j < tile[i].length; j++) {
                    if (tile[i][j] == source) {
                        System.out.println("Button at row " + i + ", column " + j + " was clicked.");
                        tile[i][j].setIcon(blackTile); //Temp, testing the listener
                        return;
                    }
                }
            }
        }
    };
	
}
