package connectfour.view;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.*;
import javax.swing.border.Border;

public class GameInfo extends JPanel{

	String player1 = "";
	String player2 = "";
	Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
	//Used for panels that display game info (chat box, game info box, turn signifier)
	Color infoBlue = new Color(190, 205, 230);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected GameInfo() {
		super();
	}
	
	protected GameInfo(GridBagConstraints c) {
		GameInfo panel = new GameInfo();
		panel.setBorder(blackLine);
		panel.setBackground(infoBlue);
		JLabel P1 = new JLabel("Player 1: " + player1);
		c.gridx = 20;
		c.gridy = 8;
		add(P1);
		JLabel P2 = new JLabel("Player 2: " + player2);
		c.gridx = 12;
		c.gridy = 2;
		add(P2);
		
	}
}
