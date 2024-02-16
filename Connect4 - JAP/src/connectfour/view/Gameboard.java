package connectfour.view;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.*;
import javax.swing.border.Border;
public class Gameboard {
	
	JButton[][] tile = new JButton[6][7];
	//declaring asset variables 
	ImageIcon emptyTile = new ImageIcon(getClass().getResource("/images/emptyTile.png"));
	ImageIcon redTile = new ImageIcon(getClass().getResource("/images/redTile.png"));
	ImageIcon blackTile = new ImageIcon(getClass().getResource("/images/blackTile.png"));
	
	/**
	 * generates a grid of 52 x 52 pixel buttons holding an image of an empty tile space.
	 * @param content - The panels this is being added to
	 * @param c - the constrains from the gridbagLayout
	 */
	protected void generateTileGrid(JPanel content, GridBagConstraints c) {
		//Grid of buttons 	
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					tile[i][j] = new JButton(emptyTile);
					tile[i][j].setBorder(blackLine);
					c.gridx = j;
					c.gridy = i;
					content.add(tile[i][j], c);
				}
			}
	}
	
}
