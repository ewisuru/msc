package com.msc.ocsf.server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.msc.ocsf.common.ChatIF;

/**
 * This class represents a Server side console where users of the Server
 * interact with. Server Console marely sits and waits till the User enters an
 * Input message to process. This is just a Boundary class which is used to get
 * the <br />
 * INPUT From the End-User.
 * 
 * @author Ravindra Name: Ravindra Sampath Ranwala ID: 138227T
 */
public class ServerConsole implements ChatIF {
	private static Logger logger = Logger.getAnonymousLogger();
	private EchoServer echoServer;
	private boolean quitStatus;

	/**
	 * Constructs a {@link ServerConsole} instance by passing an
	 * {@link EchoServer} into it.<br/ >
	 * Also it sets the quit status to FALSE.
	 * 
	 * @param echoServer
	 */
	public ServerConsole(EchoServer echoServer) {
		this.echoServer = echoServer;
		quitStatus = false;
	}

	public void display(Object msg) {
		try {
			echoServer.handleMsgFromServerUi(msg.toString());
		} catch (IOException e) {
			// Logging the Exception here.
			logger.info(e.getMessage());
		}
	}

	/**
	 * Wait for user input till the quit status is set to TRUE.
	 */
	public void accept() {
		while (!quitStatus) {
			Scanner scanner = new Scanner(System.in);
			String inputMsg = scanner.nextLine();
			display(inputMsg);
		}
	}

	/**
	 * Sets the quit status to true. This needs to be invoked before quit the
	 * application.
	 * 
	 */
	public void quit() {
		quitStatus = true;
		logger.info("Quiting the Server Console ....");
	}

	public static void main(String[] args) {
		try {
			ServerConsole serverConsole = new ServerConsole(new EchoServer(
					20001));
			serverConsole.getEchoServer().listen();
			serverConsole.getEchoServer().serverStarted();
			serverConsole.accept();
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

	public EchoServer getEchoServer() {
		return echoServer;
	}

	public boolean isQuitStatus() {
		return quitStatus;
	}

}
