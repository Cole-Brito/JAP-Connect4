package connectfour.model.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketHandler extends Thread {
	
	public enum ServerState{
		RUNNING,
		STOPPED
	}

	public ServerSocket server;
	private int port;
	
	private ServerState serverState;
	
	private List<ClientSocketHandler> clientSockets;
	
	public ServerSocketHandler(int port) {
		this.port = port;
		clientSockets = new ArrayList<>();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			server = new ServerSocket(port);
			System.out.println("Server listening on port: " + port);
			while(this.getServerState() != ServerState.STOPPED) {
				Socket client = server.accept();
				var clientHandler = new ClientSocketHandler(client);
				clientSockets.add(clientHandler);
				clientHandler.start();
				System.out.println("Accepted Client Socket: " + client.getLocalAddress() + ", " + port);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public synchronized ServerState getServerState() {
		return this.serverState;
	}
	
	public synchronized void setServerState(ServerState state) {
		this.serverState = state;
	}
	
	public void closeServerSocket() {
		setServerState(ServerState.STOPPED);
		try {
			this.server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeClientSocket(ClientSocketHandler client) {
		if (clientSockets.remove(client)) {
			client.closeClientSocket();
		}
	}

	public int getPort() {
		return server.getLocalPort();
	}
}
