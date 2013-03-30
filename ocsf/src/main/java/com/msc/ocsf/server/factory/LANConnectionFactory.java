package com.msc.ocsf.server.factory;

import java.net.Socket;

import com.msc.ocsf.server.AbstractServer;
import com.msc.ocsf.server.ConnectionToClient;
import com.msc.ocsf.server.LANConnectionToClient;

/**
 * Represents a Concrete Connection Factory which creates sub types of
 * {@link ConnectionToClient} class. This creates specific client connections
 * and returns them back to the users.
 * 
 * 
 */
public class LANConnectionFactory implements AbstractConnectionFactory {
	/**
	 * creates and returns a Connection which is used in a LAN environment to
	 * connect with the chat application.
	 */
	public ConnectionToClient createConnection(ThreadGroup threadGroup,
			Socket clientSocket, AbstractServer server) throws Exception {
		return new LANConnectionToClient(threadGroup, clientSocket, server);
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
