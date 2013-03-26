package com.msc.ocsf.client;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents a Custom Chat Client for the given requirement. All the Business
 * logivs related to chat client are implemented here. This is a control class
 * which handles Business logic of the Chat Client and also interacts with the
 * Server.
 * 
 * Name: Ravindra Sampath Ranwala ID: 138227T
 * 
 * @author Ravindra
 * 
 */
public class ChatClient extends AbstractClient {
	private ClientConsole clientUI;
	final static Logger logger = Logger.getAnonymousLogger();
	private String loginId;

	public ChatClient(String loginId, String host, int port) {
		super(host, port);
		this.loginId = loginId;
		clientUI = new ClientConsole(this);
		// Sending Login information to the Server When Client Connects.
		sendLoginInfoToServer();
	}

	private void sendLoginInfoToServer() {
		try {
			sendToServer("#login " + this.loginId);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		clientUI.display(msg.toString());
	}

	public void handleMessageFromClientUI(String msg) {
		try {
			if (msg.startsWith("#")) {
				// If it is a command, then Evaluate it.
				evalCmd(msg);

			} else {
				// If it is NOT a command, then merely send it to the Server.
				/*
				 * Makesure to send the client loginId so that the Server can
				 * manage the client state properly.
				 */
				sendToServer(loginId + "> " + msg);
			}
		} catch (IOException e) {
			clientUI.display("Could not send nessage to the server. Terminating client.");
			logger.info(e.getMessage());
			quit();
		}
	}

	// method to evaluate the input command.
	private void evalCmd(String cmd) throws IOException {
		if (cmd.equalsIgnoreCase("#quit")) {
			// Clean up all the resources before quit the program.
			closeConnection();
			// merely invoke the quit method here.
			quit();
		} else if (cmd.equalsIgnoreCase("#logoff")) {
			// merely disconnect from the Server.
			closeConnection();
		} else if (cmd.split(" ")[0].trim().equalsIgnoreCase("#sethost")) {
			// Check whether the client is Logged off.
			if (isReadyToStop()) {
				// If so set the Host here.
				String host = cmd.split(" ")[1].replace("<", "")
						.replace(">", "").trim();
				setHost(host);
			} else {
				System.out.println("ERROR: Client is already Logged In.");
			}
		} else if (cmd.split(" ")[0].trim().equalsIgnoreCase("#setport")) {
			// Check whether the Client is Logged off.
			if (isReadyToStop()) {
				// If so st the port here.
				String port = cmd.split(" ")[1].replace("<", "")
						.replace(">", "").trim();
				setPort(Integer.parseInt(port));
			} else {
				System.out.println("Error: Client is already Logged in.");
			}
		} else if (cmd.equalsIgnoreCase("#login")) {
			// If Client is NOT already Connected, then Reconnect.
			if (isReadyToStop()) {
				openConnection();
			}
			// else display an ERROR mesage.
			else {
				System.out
						.println("ERROR: Client is already Connected with the Server.");
			}
		} else if (cmd.equalsIgnoreCase("#gethost")) {
			System.out.println("Current Host # : " + getHost());
		} else if (cmd.equalsIgnoreCase("#getport")) {
			System.out.println("Current Port # : " + getPort());
		}
	}

	public void quit() {
		clientUI.quit();
	}

	@Override
	protected void connectionClosed() {
		System.out.println("Connection to the Server CLOSED.");
	}

	@Override
	protected void connectionException(Exception exception) {
		/*
		 * Merely delegate the call to the connectionClosed method which cleans
		 * up the resources.
		 */
		connectionClosed();
	}

}
