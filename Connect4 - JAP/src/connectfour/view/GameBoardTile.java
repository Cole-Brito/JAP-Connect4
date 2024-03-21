package connectfour.view;

import javax.swing.*;
import java.awt.*;

public class GameBoardTile extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//declaring asset variables 
	/** Default empty tile slot*/
	ImageIcon emptyTile = new ImageIcon(getClass().getResource("/images/emptyTile.png"));
	/** Default player 1 tile slot*/
	ImageIcon redTile = new ImageIcon(getClass().getResource("/images/redTile.png"));
	/** Default player 2 tile slot*/
	ImageIcon blackTile = new ImageIcon(getClass().getResource("/images/blackTile.png"));
	
	private TileState state;
	private short column;
	private short row;
	protected ImageIcon player1Icon;
	protected ImageIcon player2Icon;
	protected ImageIcon emptyIcon;
	
	public GameBoardTile(int row, int column) {
		this.row = (short) row;
	    this.column = (short) column;
	    emptyIcon = new ImageIcon(getClass().getResource("/images/emptyTile.png"));
	    player1Icon = new ImageIcon(getClass().getResource("/images/redTile.png"));
	    player2Icon = new ImageIcon(getClass().getResource("/images/blackTile.png"));
	    setPreferredSize(new Dimension(52, 52));
	}

	public void updateState(TileState state) {
		this.state = state;
		switch (state) {
		case PLAYER_1:
			setIcon(player1Icon);
			break;
		case PLAYER_2:
			setIcon(player2Icon);
			break;
		case DEFAULT:
			setIcon(emptyIcon);;
			break;
		}
	}
	
	public void setP1Icon(ImageIcon icon) {
		player1Icon = icon;
	}
	
	public void setP2Icon(ImageIcon icon) {
		player2Icon = icon;
	}
	
	public void setDefaultIcon(ImageIcon icon) {
		setIcon(emptyIcon);
	}
	
	public short getRow() {
        return row;
    }

    public short getColumn() {
        return column;
    }
}
