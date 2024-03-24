package connectfour.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * 
 * @author Cole Brito, Paul Squires
 *
 */
public class GameInfoPanel extends JPanel{

	/** Player 1 name*/
	String player1 = "Player 1";
	/** Player 2 name*/
	String player2 = "Player 2";
	/** Current P1 wins*/
	int player1Wins = 0;
	/** Current P2 wins*/
    int player2Wins = 0;
    
    /** Store Border used in this UI*/
	Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
	/**Used for panels that display game info (chat box, game info box, turn signifier)*/
	Color infoBlue = new Color(190, 205, 230);

    /** Player1 Label*/
	JLabel player1Label;

    /** Player 2 Label*/
    JLabel player2Label;

    /** Total game time label*/
    JLabel gameTimerLabel;

    /** Current turn time label*/
    JLabel turnTimerLabel;

    /** Player 1 Win Label*/
    JLabel player1WinsLabel;

    /** Player 2 Win Label*/
    JLabel player2WinsLabel;

    /** Player 1 Icon holder*/
    JLabel player1Icon;
    
    /** Player 2 Icon holder*/
    JLabel player2Icon;
    
	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Creates the JPanel that displays information about the game and players
	 * 
	 */
	protected GameInfoPanel() {
		super(new GridLayout(4, 2));
        setPreferredSize(new Dimension(220, 100));
        setBorder(blackLine);
        setBackground(infoBlue);
		
		player1Label = new JLabel("Player 1: " + player1);
        player2Label = new JLabel("Player 2: " + player2);
        gameTimerLabel = new JLabel("Game time: ");
        turnTimerLabel = new JLabel("Turn time: ");
        player1WinsLabel = new JLabel("Player 1 Wins: " + player1Wins);
        player2WinsLabel = new JLabel("Player 2 Wins: " + player2Wins);
        player1Icon = new JLabel(new ImageIcon(createTeamIcon(Color.RED)));
        player2Icon = new JLabel(new ImageIcon(createTeamIcon(Color.BLACK))); 

        add(player1Label);
        add(player1Icon);
        add(player2Label);
        add(player2Icon);
        add(gameTimerLabel);
        add(player1WinsLabel);
        add(turnTimerLabel);
        add(player2WinsLabel);
    
	}
	
	/**
	 * Method to create a new BufferImage of a 5x5 pixel colored square
	 * @param color - The color of the piece the player controls
	 * @return - returns the 5x5 pixel image of the players piece color
	 */
	 private Image createTeamIcon(Color color) {
		Image image = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setColor(color);
		g2d.fillRect(0, 0, 5, 5);
		g2d.dispose();
		return image;
    }
	
	/**
	 * sets the players chosen name
	 * @param name - Name that player 1 has entered
	 */
	public void setPlayer1(String name) {
        this.player1 = name;
        player1Label.setText("Player 1: " + name);
    }

	/**
	 * sets the players chosen name
	 * @param name - Name that player 2 has entered
	 */
    public void setPlayer2(String name) {
        this.player2 = name;
        player2Label.setText("Player 2: " + name);
    }

    /**
     * Sets the number of times the player has won
     * @param wins - Total wins achieved by player1
     */
    public void setPlayer1Wins(int wins) {
        this.player1Wins = wins;
        player1WinsLabel.setText("Player 1 Wins: " + wins);
    }

    /**
     * Sets the number of times the player has won
     * @param wins - Total wins achieved by player2
     */
    public void setPlayer2Wins(int wins) {
        this.player2Wins = wins;
        player2WinsLabel.setText("Player 2 Wins: " + wins);
    }

    /**
     * updates the turnTimer label with the current turn time
     * @param time - time taken during the current turn
     */
    public void setGameTimer(String time) {
        gameTimerLabel.setText("Game time: " + time);
    }

    /**
     * updates the gameTimer label with the current game time
     * @param time - time taken during the current game
     */
    public void setTurnTimer(String time) {
        turnTimerLabel.setText("Turn time: " + time);
    }
}
	
