package connectfour.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

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
	/**
	 * 
	 * @param
	 */
	protected GameInfo() {
		super();
		LayoutManager layout = new GridLayout(12,12);
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(220, 100));
		setBorder(blackLine);
		setBackground(infoBlue);
		JLabel P1 = new JLabel("Player 1: " + player1);
		add(P1);
		JLabel P2 = new JLabel("Player 2: " + player2);
		add(P2);
		
	}
}
