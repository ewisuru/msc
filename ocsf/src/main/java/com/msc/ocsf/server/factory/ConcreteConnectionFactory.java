package com.msc.ocsf.server.factory;

import java.io.IOException;
import java.net.Socket;

import com.msc.ocsf.server.AbstractServer;
import com.msc.ocsf.server.ConnectionToClient;
import com.msc.ocsf.server.SpecificConnectionToClient;

/**
 * Represents a Concrete Connection Factory which creates sub types of
 * {@link ConnectionToClient} class. This creates specific client connections
 * and returns them back to the users.
 * 
 * @author Ravindra
 * 
 */
public class ConcreteConnectionFactory implements AbstractConnectionFactory {

	/**
	 * Creates and returns a sub type of {@link ConnectionToClient} class.
	 */
	public ConnectionToClient createConnection(ThreadGroup threadGroup,
			Socket clientSocket, AbstractServer server) throws IOException {
		return new SpecificConnectionToClient(threadGroup, clientSocket, server);
	}
}
