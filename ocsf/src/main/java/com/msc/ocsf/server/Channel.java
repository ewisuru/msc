package com.msc.ocsf.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class which represents a communication channel. This contains fields to
 * maintain the channel Id and a {@link List} of {@link ConnectionToClient}
 * instances associated with the channel.
 * 
 * @author Ravindra.ranwala
 * 
 */
public class Channel {
	private String channelId;

	private List<ConnectionToClient> registeredClients;

	public Channel(String channelId) {
		super();
		this.channelId = channelId;
		registeredClients = new ArrayList<ConnectionToClient>();
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * Adds a new client to the current {@link Channel} instance.
	 * 
	 * @param client
	 * @author Ravindra.ranwala
	 */
	public void addClientToChannel(ConnectionToClient client) {
		registeredClients.add(client);
	}

}
