package com.msc.ocsf.client;

import java.util.Observable;
import java.util.Observer;

/**
 * Registers for state changes in {@link ObservableClient}.
 * 
 */
public class MessageHandlerObserver implements Observer {

	/**
	 * Constructs a {@link MessageHandlerObserver} instance which listens for
	 * state changes in {@link ObservableClient}
	 * 
	 * @param client
	 */
	public MessageHandlerObserver(Observable client) {
		client.addObserver(this);
	}

	public void update(Observable o, Object msg) {
		// Processing the interested message.
		System.out.println(msg);
	}

}
