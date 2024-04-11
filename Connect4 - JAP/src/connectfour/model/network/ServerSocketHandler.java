package connectfour.model.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketHandler extends Thread {
	
	/** The state of this server socket */
	public enum ServerState{
		/** The socket is open and listening for messages */
		RUNNING,
		/** The sockets is closed */
		STOPPED
	}

	/** The server socket that listens for new connections */
	public ServerSocket server;
	/** The port this socket is open on */
	private int port;
	/** The current state of the server socket */
	private ServerState serverState;
	/** A list of all client sockets connected to this server */
	private List<ClientSocketHandler> clientSockets;
	
	/**
	 * Creates a new ServerSocketHandler with the given port number.
	 * Does not start the server immediately,
	 * @param port The port for this server to listen on.
	 */
	public ServerSocketHandler(int port) {
		this.port = port;
		clientSockets = new ArrayList<>();
	}
	
	/**
	 * Runs this server thread and listens for incoming client connections
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			server = new ServerSocket(port);
			System.out.println("Server listening on port: " + port);
			while(this.getServerState() != ServerState.STOPPED && !server.isClosed()) {
				Socket client = server.accept();
				var clientHandler = new ClientSocketHandler(client);
				clientSockets.add(clientHandler);
				clientHandler.start();
				clientHandler.sendMessage(NetworkManager.getInstance().createHelloMessage());
				System.out.println("Accepted Client Socket: " + client.getLocalAddress() + ", " + port);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Gets the current server state
	 * @return serverState
	 */
	public synchronized ServerState getServerState() {
		return this.serverState;
	}
	
	/**
	 * Sets the current server state
	 * @param state the new server state
	 */
	public synchronized void setServerState(ServerState state) {
		this.serverState = state;
	}
	
	/**
	 * Broadcasts object data across all connected clients
	 * @param message
	 */
	public synchronized void broadcastMessage(Object message) {
		for (var client: clientSockets) {
			if (client.isAlive() && !client.socket.isClosed()) {
				client.sendMessage(message);
			}
		}
	}
	
	/**
	 * Closes this server socket
	 */
	public void closeServerSocket() {
		setServerState(ServerState.STOPPED);
		try {
			this.server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a single client connection from this server
	 * @param client
	 */
	public void closeClientSocket(ClientSocketHandler client) {
		if (clientSockets.remove(client)) {
			client.closeClientSocket();
		}
	}

	/**
	 * Get the port this server is listening on
	 * @return port number
	 */
	public int getPort() {
		return server.getLocalPort();
	}
}
