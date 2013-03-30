package com.msc.ocsf.server.factory;

import java.net.Socket;

import com.msc.ocsf.server.AbstractServer;
import com.msc.ocsf.server.ConnectionToClient;

/**
 * This is the Abstract Factory class which defines an interface to create
 * {@link ConnectionToClient} sub types.
 * 
 * 
 */
public interface AbstractConnectionFactory {
	/**
	 * Factory method which creates {@link ConnectionToClient} sub types.
	 * 
	 * @return
	 */
	public ConnectionToClient createConnection(ThreadGroup threadGroup,
			Socket clientSocket, AbstractServer server) throws Exception;
}
