package com.msc.ocsf.server;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Simple Echo Server which deals with the messages sent by the Client by merely
 * printing them into the console.
 * 
 * @author Ravindra Name: Ravindra Sampath Ranwala ID: 138227T
 */
public class EchoServer extends AbstractServer {
	private static final Logger logger = Logger.getAnonymousLogger();
	// private ObservableServer server;
	/**
	 * The string sent to the observers when the server has closed.
	 */
	public static final String SERVER_CLOSED = "#OS:Server closed.";

	/**
	 * The string sent to the observers when the server has started.
	 */
	public static final String SERVER_STARTED = "#OS:Server started.";

	private ServerConsole serverConsole;

	/**
	 * Constructs an {@link EchoServer} instance associated with the given port
	 * number.
	 * 
	 * @param port
	 */
	public EchoServer(int port) {
		super(port);
		serverConsole = new ServerConsole(this);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// Specific Functionality only confined to the #login <loginId> messages
		// sent from the Client when Connects.
		if (String.valueOf(msg).split(" ")[0].equalsIgnoreCase("#login")) {
			/*
			 * Then DO the special Functionality here. Saving the Login Id here.
			 * Check if this #login command is the first command when login. if
			 * so proceed.
			 */
			if (client.getInfo(String.valueOf(msg).split(" ")[1]) == null) {
				client.setInfo(String.valueOf(msg).split(" ")[1], msg);
				try {
					client.sendToClient(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*
			 * If this #login is NOT the first command, then send an ERROR
			 * message to the client. If the Client has already logged in sends
			 * an ERROR message back to him.
			 */
			else {
				try {
					client.sendToClient("ERROR: Already sent the #login command !");
				} catch (IOException e) {
					// Logging the Exception message here.
					logger.info(e.getMessage());
				}
			}
		}
		/*
		 * If NOT a #login message then, ensure that the client has been #logged
		 * in before sending any other message to the server by merely fetching
		 * the client infor which would be set if the client has #logged in
		 * Before @ the first Connection.
		 */
		else if (client.getInfo(String.valueOf(msg).split(" ")[0].replace('>',
				' ').trim()) != null) {
			/*
			 * Already logged in, so proced with the message sent from the
			 * client.
			 */
			this.sendToAllClients(String.valueOf(msg));
		} else if (client.getInfo(String.valueOf(msg).split(" ")[0].replace(
				'>', ' ').trim()) == null) {
			/*
			 * If the Client has NOT logged in @ the FIRST Connect before
			 * sending any messages to the server merely Notify the client
			 * before closing the Connection to the SERVER.
			 */
			try {
				client.sendToClient("Please Loggin @ FIRST Connect Before sending any messages to the SERVER...");
				// merely CLOSING the Connection with the Client.
				client.close();
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
	}

	@Override
	public void serverStarted() {
		// Delegates the call to the Observable Server.
		// server.serverStarted();
		System.out.println(SERVER_STARTED);

	}

	@Override
	synchronized protected void serverStopped() {
		// Delegates the call to the Observable Server.
		// server.serverStopped();
		System.out.println(SERVER_CLOSED);
		// Stop Listening for new Conections here.
		stopListening();
	}

	@Override
	protected void serverClosed() {
		this.serverStopped();
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		System.out
				.println("Client: " + client.getInetAddress() + " Connected.");
	}

	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println(client.getInetAddress() + " Disconnected.");
	}

	/**
	 * Method to handle user inputs at the Server side Console. This takes the
	 * decision whether <br />
	 * the input message is just a plain Text OR command using the '#'symbol.
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void handleMsgFromServerUi(String msg) throws IOException {
		if (msg.startsWith("#")) {
			// If it is a command, then Evaluate it.
			evalCmd(msg);
		} else {
			System.out.println("SERVER MSG > " + msg.toString());
			this.sendToAllClients("SERVER MSG > " + msg);
		}
	}

	// method to evaluate the input command.
	private void evalCmd(String cmd) throws IOException {
		if (cmd.equalsIgnoreCase("#quit")) {
			// Clean up all the resources before quit the program.
			/*
			 * merely stops listening to new Clients, close the socket and
			 * terminate existing client sessions.
			 */
			close();
			// Displays the message in the Server Console.
			serverConsole.quit();
		} else if (cmd.equalsIgnoreCase("#stop")) {
			// Causes the server to STOP listening for new Clients.
			stopListening();
		} else if (cmd.equalsIgnoreCase("#close")) {
			// Stop Listening to new Clients and also disconnects existing
			// clients.
			close();
		} else if (cmd.split(" ")[0].trim().equalsIgnoreCase("#setport")) {
			// Check whether the Server is CLOSED First.
			if (this.getServerSocket() == null) {
				// If so, set the PORT merely after extracting the PORT number
				// from the Input command.
				String portStr = cmd.split(" ")[1].replace("<", "")
						.replace(">", "").trim();
				int port = Integer.valueOf(portStr);
				this.setPort(port);
			}
		} else if (cmd.equalsIgnoreCase("#start")) {
			// First check whether the SERVER is STOPPED.
			if (this.isReadyToStop()) {
				// If so START listening for new Clients.
				this.listen();
			}
		} else if (cmd.equalsIgnoreCase("#getport")) {
			// Merely displays the current port #.
			System.out.println("Current Port #: " + this.getPort());
		}
	}
}
