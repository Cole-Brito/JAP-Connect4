package connectfour.model.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles the server socket on a separate thread
 */
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
	/** A lock object used to synchronize with this thread */
	private Object lockObject;
	
	/**
	 * Creates a new ServerSocketHandler with the given port number.
	 * Does not start the server immediately,
	 * @param port The port for this server to listen on.
	 * @param lockObject The lock Object for synchronizing with NetworkManager
	 */
	public ServerSocketHandler(int port, Object lockObject) {
		this.port = port;
		this.serverState = ServerState.STOPPED;
		clientSockets = new ArrayList<>();
		this.lockObject = lockObject;
	}
	
	/**
	 * Runs this server thread and listens for incoming client connections
	 */
	@Override
	public void run() {
		// Attempt to open server
		try {
			server = new ServerSocket(port);
			System.out.println("Server listening on port: " + port);
			setServerState(ServerState.RUNNING);
		} catch (Exception e) {
			closeServerSocket();
			e.printStackTrace();
		}
		
		// Notify lock monitor after server socket connects (or not)
		synchronized (lockObject) {
			lockObject.notifyAll();			
		}
		
		try {
			while(this.getServerState() != ServerState.STOPPED && !server.isClosed()) {
				Socket client = server.accept();
				var clientHandler = new ClientSocketHandler(client);
				clientSockets.add(clientHandler);
				clientHandler.start();
				clientHandler.sendMessage(NetworkManager.getInstance().createHelloMessage());
				System.out.println("Accepted Client Socket: " + client.getLocalAddress() + ", " + port);
			}
		} catch (IOException e) {
			// Output error if we didn't manually close server
			if (serverState != ServerState.STOPPED) {
				closeServerSocket();
				e.printStackTrace();				
			}
		} catch (Exception e) {
			//TODO: Do we want to close server here?
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
	 * @param message The NetworkMessage to send to all clients
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
			if (server != null && !server.isClosed()) {
				this.server.close();				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a single client connection from this server
	 * @param client The client socket to remove
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
