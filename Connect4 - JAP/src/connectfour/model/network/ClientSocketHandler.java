package connectfour.model.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import connectfour.model.Player;
import connectfour.model.network.ServerSocketHandler.ServerState;

public class ClientSocketHandler extends Thread{
	
	public enum ClientState{
		RUNNING,
		STOPPED
	}
	
	public Socket socket;
	
	private ClientState clientState;
	/** The player that this socket is associated with */
	private Player player;
	
	public ClientSocketHandler(Socket socket) {
		this.socket = socket;
	}

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
	
	public synchronized ClientState getClientState() {
		return this.clientState;
	}
	
	public synchronized void setClientState(ClientState state) {
		this.clientState = state;
	}
	
	public synchronized Player getPlayer() {
		return this.player;
	}
	
	public synchronized void setPlayer(Player player) {
		this.player = player;
		System.out.println("Set clientSocket player: " + player.toString());
	}
	
	public synchronized void sendMessage(Object data) {
		try {
			ObjectOutputStream ostream = new ObjectOutputStream(socket.getOutputStream());
			ostream.writeObject(data);
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}	
	}
	
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
