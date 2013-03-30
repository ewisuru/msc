package com.msc.ocsf.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a specific implementation of the {@link ConnectionToClient} class.
 * 
 * 
 */
public class InternetConnectionToClient extends ConnectionToClient {

	/**
	 * Constructs a specific sub type of the {@link ConnectionToClient} class.
	 * 
	 * @param group
	 *            Thread group to which this belongs.
	 * @param clientSocket
	 *            client socket stream.
	 * @param server
	 *            server with which the client connects.
	 * @throws IOException
	 *             If any Error occurs while execution.
	 */
	public InternetConnectionToClient(ThreadGroup group, Socket clientSocket,
			AbstractServer server) throws IOException {
		super(group, clientSocket, server);
	}

	/**
	 * handles the message sent by the client.
	 * 
	 * @param message
	 * @return
	 */
	public boolean handleMessageFromClient(Object message) {
		return true;
	}

}
