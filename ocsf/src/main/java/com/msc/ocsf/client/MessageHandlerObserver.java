package com.msc.ocsf.client;

import java.util.Observable;
import java.util.Observer;

public class MessageHandlerObserver implements Observer {

	public MessageHandlerObserver(Observable client) {
		client.addObserver(this);
	}

	public void update(Observable o, Object msg) {
		// Processing the interested message.
		System.out.println(msg);
	}

}
