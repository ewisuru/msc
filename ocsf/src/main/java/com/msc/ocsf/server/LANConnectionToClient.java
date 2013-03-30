package com.msc.ocsf.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a Concrete Connection Factory which creates sub types of
 * {@link ConnectionToClient} class. This creates specific client connections
 * and returns them back to the users.
 * 
 * 
 */
public class LANConnectionToClient extends ConnectionToClient {

	/**
	 * Constructs a {@link LANConnectionToClient} instance used to clients who
	 * log in to the chat applications using the LAN connection.
	 * 
	 * @param group
	 *            Thread group to which this client belong.
	 * @param clientSocket
	 *            client socket stream.
	 * @param server
	 *            server with which this client associates.
	 * @throws IOException
	 */
	public LANConnectionToClient(ThreadGroup group, Socket clientSocket,
			AbstractServer server) throws IOException {
		super(group, clientSocket, server);
	}

}
