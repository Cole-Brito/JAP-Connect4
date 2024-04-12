/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import connectfour.model.timer.ControllableTimer;

import javax.swing.*;
import javax.swing.border.Border;

import connectfour.model.GameManager;
import connectfour.model.Player;
import connectfour.model.PlayerManager;
import connectfour.model.locale.LanguageSet;
import connectfour.model.locale.LocaleChangeListener;


/**
 * JPanel that contains game information, including player names, win counts, and timer displays.
 */
public class GameInfoPanel extends JPanel implements PropertyChangeListener, LocaleChangeListener{

	/** Player 1 name*/
	Player player1;
	/** Player 2 name*/
	Player player2;
	/** Current P1 wins*/
	int player1Wins = 0;
	/** Current P2 wins*/
    int player2Wins = 0;
    
    /** Localized word for Player */
    private String localizedPlayerText = "Player";
    /** Localized word for Wins */
    private String localizedWinText = "Wins";
    
    /** Store Border used in this UI*/
	Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
	/**Used for panels that display game info (chat box, game info box, turn signifier)*/
	Color infoBlue = new Color(190, 205, 230);

    /** Player1 Label*/
	JLabel player1Label;

    /** Player 2 Label*/
    JLabel player2Label;

    /** Total game time label*/
    TimerDisplayLabel gameTimerLabel;

    /** Current turn time label*/
    TimerDisplayLabel turnTimerLabel;

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
	public GameInfoPanel() {
		super(new GridLayout(4, 2));
        setPreferredSize(new Dimension(220, 100));
        setBorder(blackLine);
        setBackground(infoBlue);
		
		player1Label = new JLabel(localizedPlayerText + " 1: " + "Player 1");
        player2Label = new JLabel(localizedPlayerText + " 2: " + "Player 2");
        gameTimerLabel = new TimerDisplayLabel("Game time: ");
        turnTimerLabel = new TimerDisplayLabel("Turn time: ");
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
	 * @param p1 The player to set as player 1
	 */
	public void setPlayer1(Player p1) {
        this.player1 = p1;
        if (player1 != null) {
        	player1Label.setText(localizedPlayerText + " 1: " + p1.getName());        	
        }
        else {
        	player1Label.setText("N/A");
        }
    }

	/**
	 * sets the players chosen name
	 * @param p2 The player to set as player 2
	 */
    public void setPlayer2(Player p2) {
        this.player2 = p2;
        if (player2 != null) {
        	player2Label.setText(localizedPlayerText + " 2: " + p2.getName());        	
        }
        else {
        	player2Label.setText("N/A");
        }
    }

    /**
     * Sets the number of times the player has won
     * @param wins - Total wins achieved by player1
     */
    public void setPlayer1Wins(int wins) {
        this.player1Wins = wins;
        player1WinsLabel.setText(localizedPlayerText + " 1 " + localizedWinText + ": " + wins);
    }

    /**
     * Sets the number of times the player has won
     * @param wins - Total wins achieved by player2
     */
    public void setPlayer2Wins(int wins) {
        this.player2Wins = wins;
        player2WinsLabel.setText(localizedPlayerText + " 2 " + localizedWinText + ": " + wins);
    }

    /**
     * updates the turnTimer label with the current turn time
     * @param elapsed - time taken during the current turn
     */
    public void setGameTimer(int elapsed) {
        gameTimerLabel.setText("Game time: " + elapsed);
    }

    /**
     * updates the gameTimer label with the current game time
     * @param time - time taken during the current game
     */
    public void setTurnTimer(int time) {
        turnTimerLabel.setText("Turn time: " + time);
    }
    
    /**
     * Gets the game timer label
     * @return gameTimerLabel
     */
    public TimerDisplayLabel getGameTimer() {
    	return gameTimerLabel;
    }
    
    /**
     * Gets the turn timer label
     * @return turnTimerLabel
     */
    public TimerDisplayLabel getTurnTimer() {
    	return turnTimerLabel;
    }

	/**
	 * PropertyChange event for when GameManager updates player win counts.
	 */
    @Override
	public void propertyChange(PropertyChangeEvent evt) {
    	
		switch(evt.getPropertyName()) {
		case GameManager.GAME_WIN_COUNT_PROPERTY_NAME:
		{
			var newValue = (GameManager.GameWinCountChangedEvent)evt.getNewValue();
			if (newValue != null) {
				setPlayer1Wins(newValue.player1WinCount);
				setPlayer2Wins(newValue.player2WinCount);
				return;
			}
		}
		break;
		case GameManager.GAME_PLAYER1_CHANGE_PROPERTY_NAME:
		{
			var player = (Player)evt.getNewValue();
			setPlayer1(player);
		}
		break;
		case GameManager.GAME_PLAYER2_CHANGE_PROPERTY_NAME:
		{
			var player = (Player)evt.getNewValue();
			setPlayer2(player);
		}
		break;
		case PlayerManager.PLAYER_UPDATE_PROPERTY_NAME:
		{
			var player = (Player)evt.getNewValue();
			if (player != null) {
				if (player.equals(player1)) {
					setPlayer1(player1);
				}
				else if (player.equals(player2)) {
					setPlayer2(player);					
				}
				return;
			}
		}
		break;
		default:
			System.err.println("Property was null or unexpected type in GameInfoPanel#propertyChange"
					+ " for property name: " + evt.getPropertyName());
			break;
		}
	}

    /**
     * Update GameInfo when locale changes
     */
	@Override
	public void onLocaleChanged(LanguageSet newLanguage) {
		gameTimerLabel.setBaseText(newLanguage.getKeyword("GameInfoPanel.GameTime") + ": ");
		turnTimerLabel.setBaseText(newLanguage.getKeyword("GameInfoPanel.TurnTime") + ": ");
		localizedPlayerText = newLanguage.getKeyword("GameInfoPanel.Player");
		localizedWinText = newLanguage.getKeyword("GameInfoPanel.Wins");
		setPlayer1Wins(player1Wins);
		setPlayer2Wins(player1Wins);
		setPlayer1(player1);
		setPlayer2(player2);
	}
}
	
