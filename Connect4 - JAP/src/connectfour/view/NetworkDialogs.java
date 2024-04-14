/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 11, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connectfour.model.PlayerManager;
import connectfour.model.network.NetworkManager;

/**
 * Class for displaying the dialogs used to establish a network connection
 * @author Cole Brito
 *
 */
public class NetworkDialogs {

	/**
	 * Method to get the users desired name and port number
	 */
	public void diplayHostSetup() {
		JTextField playerNameField = new JTextField(10);
		JTextField portNumberField = new JTextField(10);
		
		JPanel hostPanel = new JPanel();
		JLabel playerLbl = new JLabel("Name: ");
		JLabel portLbl = new JLabel("Port: ");
		JLabel errorLbl = new JLabel("");
		errorLbl.setForeground(Color.red);
		
		hostPanel.add(playerLbl);
		hostPanel.add(playerNameField);
		hostPanel.add(portLbl);
		hostPanel.add(portNumberField);
		hostPanel.add(errorLbl);
		
		int result;
		do {
	        result = JOptionPane.showConfirmDialog(null, hostPanel, "Host Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        // Checking for if all fields are filled and if port number is within range
	        if (result == JOptionPane.OK_OPTION) {
	            if (playerNameField.getText().isEmpty()) {
	                errorLbl.setText("Name cannot be empty");
	            } else if (portNumberField.getText().isEmpty()) {
	                errorLbl.setText("Port number cannot be empty");
	            } else if (!isValidPort(portNumberField.getText())) {
	                errorLbl.setText("Port number must be between " + NetworkManager.MIN_PORT_NUMBER +
	                		" and " + NetworkManager.MAX_PORT_NUMBER);
	            } else {
	            	// Clearing error label
	                errorLbl.setText("");
	            }
	        }
	    } while (result == JOptionPane.OK_OPTION && (!errorLbl.getText().isEmpty()));
		 
		if (result == JOptionPane.OK_OPTION) {
			String playerName = playerNameField.getText(); // have to change player labels still
	        PlayerManager.getInstance().updatePlayerName(PlayerManager.getInstance().getLocalPlayer1(), playerName);
	        int portNumber = Integer.parseInt(portNumberField.getText());
	        if (NetworkManager.getInstance().openServerSocket(portNumber) < 0) {
	        	JOptionPane.showMessageDialog(null, "Could not open server on port " + portNumber);
    		}
		 }
	}
	
	/**
	 * Method to get the users desired name and the ip and port number they wish to connect to
	 */
	public void displayConnectionSetup() {
		JTextField playerNameField = new JTextField(10);
		JTextField portNumberField = new JTextField(10);
		JTextField ipAddressField = new JTextField(10);
		
		JPanel connectionPanel = new JPanel();
		JLabel playerLbl = new JLabel("Name: ");
		JLabel portLbl = new JLabel("Port: ");
		JLabel ipLbl = new JLabel("IP: ");
		JLabel errorLbl = new JLabel("");
		errorLbl.setForeground(Color.red);
		
		connectionPanel.add(playerLbl);
		connectionPanel.add(playerNameField);
		connectionPanel.add(portLbl);
		connectionPanel.add(portNumberField);
		connectionPanel.add(ipLbl);
		connectionPanel.add(ipAddressField);
		connectionPanel.add(errorLbl);
		
		int result;
		do {
	        result = JOptionPane.showConfirmDialog(null, connectionPanel, "Host Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        // Checking for if all fields are filled and if port number is within range
	        if (result == JOptionPane.OK_OPTION) {
	            if (playerNameField.getText().isBlank()) {
	                errorLbl.setText("Name cannot be empty");
	            } else if (portNumberField.getText().isBlank()) {
	                errorLbl.setText("Port number cannot be empty");
	            } else if (ipAddressField.getText().isBlank()) {
	                errorLbl.setText("Ip address cannot be empty");
	            } else if (!isValidPort(portNumberField.getText())) {
	                errorLbl.setText("Port number must be between " + NetworkManager.MIN_PORT_NUMBER +
	                		" and " + NetworkManager.MAX_PORT_NUMBER);
	            } else {
	            	// Clearing error label
	                errorLbl.setText("");
	            }
	        }
	    } while (result == JOptionPane.OK_OPTION && (!errorLbl.getText().isEmpty()));
		 
		if (result == JOptionPane.OK_OPTION) {
	        String playerName = playerNameField.getText();
	        PlayerManager.getInstance().updatePlayerName(PlayerManager.getInstance().getLocalPlayer1(), playerName.trim());
	        int portNumber = Integer.parseInt(portNumberField.getText());
	        String ipAddress = ipAddressField.getText();
	       if (!NetworkManager.getInstance().openClientSocket(ipAddress, portNumber)) {
	        	JOptionPane.showMessageDialog(null, "Could not connect to server: " + ipAddress + ":" + portNumber);
	       }
	        
		}
	}
	
	/**
	 * Helper method to test if the port number is within accepted range
	 * @param portText - the port number to be tested
	 * @return true if the port is valid
	 */
	private boolean isValidPort(String portText) {
	    try {
	        int portNumber = Integer.parseInt(portText);
	        return (portNumber <= NetworkManager.MAX_PORT_NUMBER && portNumber >= NetworkManager.MIN_PORT_NUMBER);
	    } catch (NumberFormatException e) {
	    	
	        return false;
	    }
	}
}
