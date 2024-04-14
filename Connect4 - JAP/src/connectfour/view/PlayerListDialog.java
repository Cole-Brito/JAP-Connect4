package connectfour.view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connectfour.model.PlayerManager;
import connectfour.model.PlayerType;
import connectfour.model.network.NetworkManager;
import connectfour.model.network.NetworkManager.SessionType;

public class PlayerListDialog {
	public void showPlayerList() {
		var playerManager = PlayerManager.getInstance();
		var p1 = playerManager.getLocalPlayer1();
		var p2 = playerManager.getLocalPlayer2();
		
		JPanel playerListPanel = new JPanel();
		var playerListVertical = Box.createVerticalBox();
		playerListPanel.add(playerListVertical);
		JLabel localPlayerLabel = new JLabel("Local Players: ");
		playerListVertical.add(localPlayerLabel);
		
		JTextField player1NameField = new JTextField(10);
		player1NameField.setText(p1.getName());
		JLabel player1Label = new JLabel("Player 1: ");
		var p1Field = Box.createHorizontalBox();
		p1Field.add(player1Label);
		p1Field.add(player1NameField);
		playerListVertical.add(p1Field);
		
		JTextField player2NameField = new JTextField(10);
		player2NameField.setText(p2.getName());
		JLabel player2Label = new JLabel("Player 2: ");
		var p2Field = Box.createHorizontalBox();
		p2Field.add(player2Label);
		p2Field.add(player2NameField);
		playerListVertical.add(p2Field);
		if (NetworkManager.getInstance().getSessionType() != SessionType.OFFLINE) {
			player2NameField.setEnabled(false);
		}
		
		JLabel networkPlayersLabel = new JLabel("Network Players: ");
		playerListVertical.add(networkPlayersLabel);
		
		for(var p: playerManager.getPlayers()) {
			if (p.getPlayerType() == PlayerType.NETWORK) {
				JLabel netPlayerLabel = new JLabel(p.getName());
				playerListVertical.add(netPlayerLabel);
			}
		}
		
		JLabel errorLabel = new JLabel("");
		playerListVertical.add(errorLabel);
		errorLabel.setForeground(Color.red);
		
		int result;
		do {
	        result = JOptionPane.showConfirmDialog(null, playerListPanel, "Player List", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        // Checking for if all fields are filled and if port number is within range
	        if (result == JOptionPane.OK_OPTION) {
	            if (player1NameField.getText().isBlank() || player2NameField.getText().isBlank()) {
	            	errorLabel.setText("Name cannot be empty");
	            }
	            else {
	            	// Clearing error label
	            	errorLabel.setText("");
	            }
	        }
	    } while (result == JOptionPane.OK_OPTION && (!errorLabel.getText().isBlank()));
		 
		if (result == JOptionPane.OK_OPTION) {
	        if (!p1.getName().equals(player1NameField.getText().trim())) {
	        	PlayerManager.getInstance().updatePlayerName(p1, player1NameField.getText().trim());
	        	if (NetworkManager.getInstance().getSessionType() == SessionType.CLIENT) {
	        		NetworkManager.getInstance().sendPlayerUpdateMessage(p1, null);
	        	}
	        }
	        if (!p2.getName().equals(player2NameField.getText().trim())) {
	        	PlayerManager.getInstance().updatePlayerName(p2, player2NameField.getText().trim());
	        }
		}
	}
}
