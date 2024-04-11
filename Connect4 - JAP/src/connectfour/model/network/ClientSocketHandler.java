package connectfour.model.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import connectfour.model.Player;
import connectfour.model.network.ServerSocketHandler.ServerState;

public class ClientSocketHandler extends Thread{
	
	/**
	 * The state of this client socket. Separate from thread state.
	 */
	public enum ClientState{
		/** The socket is open and listening for messages */
		RUNNING,
		/** The sockets is closed */
		STOPPED
	}
	
	/** The socket this client sends and receives data on */
	public Socket socket;
	
	/** The client socket state */
	private ClientState clientState;
	/** The player that this socket is associated with */
	private Player player;
	
	/**
	 * Creates a new ClientSocketHandler with a given socket.
	 * Socket is received from ServerSocketHandler
	 * @param socket The client socket
	 */
	public ClientSocketHandler(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Runs this socket thread, causing socket to listen for input messages
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		setClientState(ClientState.RUNNING);
		while(clientState != ClientState.STOPPED && !socket.isClosed()) {
			try {
				ObjectInputStream istream = new ObjectInputStream(socket.getInputStream());
				var data = istream.readObject();
				NetworkManager.getInstance().onMessageReceived(data, this);
			}
			catch (IOException ex) {
				System.err.println(ex.getMessage());
				setClientState(ClientState.STOPPED);
			}
			catch (Exception ex) {
				//TODO: Don't stop client if an Exception is thrown which doesn't impede operations
				System.err.println(ex.getMessage());
				setClientState(ClientState.STOPPED);
			}
		}
	}
	
	/**
	 * Gets this socket's state
	 * @return clientState
	 */
	public synchronized ClientState getClientState() {
		return this.clientState;
	}
	
	/**
	 * Sets this socket's state
	 * @param state The new socket state
	 */
	public synchronized void setClientState(ClientState state) {
		this.clientState = state;
	}
	
	/**
	 * Gets the player associated with this client socket
	 * @return Player
	 */
	public synchronized Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Sets the player associated with this client socket
	 * @param player
	 */
	public synchronized void setPlayer(Player player) {
		this.player = player;
		System.out.println("Set clientSocket player: " + player.toString());
	}
	
	/**
	 * Writes an object to the sockets output stream
	 * @param data The object to serialize and send
	 */
	public synchronized void sendMessage(Object data) {
		try {
			ObjectOutputStream ostream = new ObjectOutputStream(socket.getOutputStream());
			ostream.writeObject(data);
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}	
	}
	
	/**
	 * Close this socket connection
	 */
	public void closeClientSocket() {
		setClientState(ClientState.STOPPED);
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
