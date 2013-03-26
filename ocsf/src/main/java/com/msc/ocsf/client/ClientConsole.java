package com.msc.ocsf.client;

import java.util.Scanner;

import com.msc.ocsf.common.ChatIF;

/**
 * Represents Client side user interface where clients of the OCSF are going to
 * interact with.<br />
 * It accepts user input from the console and also displays the messages <br />
 * sent by the server to the clients of the OCSF. This is merely a Boundary
 * class which is used <br />
 * to get an INPUT From the end-user.
 * 
 * @author Ravindra
 * Name: Ravindra Sampath Ranwala
 * ID: 138227T
 */
public class ClientConsole implements ChatIF {
	private ChatClient chatClient;
	private boolean quitStatus;

	/**
	 * Constructs a {@link ClientConsole} instance and initializes
	 * {@link ChatClient} instance field associated with it.
	 * 
	 * @param chatClient
	 */
	public ClientConsole(ChatClient chatClient) {
		this.chatClient = chatClient;
		quitStatus = false;
	}

	public void display(Object msg) {
		System.out.println(msg.toString());
	}

	/**
	 * Accepts the User Input from the Console till the application
	 * terminates/quits. Then call the handleMessageFromClientUI method in the
	 * {@link ClientConsole} instance<br />
	 * and passes the messege to the server via that.
	 * 
	 * @author Ravindra
	 */
	public void accept() {
		while (!quitStatus) {
			Scanner scanner = new Scanner(System.in);
			String inputMsg = scanner.nextLine();
			chatClient.handleMessageFromClientUI(inputMsg);
		}
	}

	/**
	 * Entry Point to the Client side application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String loginId = captureLoginId();
		// Continue only if the Login ID is NOT null or NOT Empty.
		if (loginId != null || loginId.length() > 1) {
			ClientConsole clientConsole = new ClientConsole(new ChatClient(
					loginId, "localhost", 20001));
			clientConsole.accept();
		} else {
			System.out.println("Invalid Login Id, TERMINSTING the Client ! ");
		}

	}

	private static synchronized String captureLoginId() {
		System.out.println("Please Enter Login ID - Mandatiry: ");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	/**
	 * Sets the Quit status for the client console. This method needs to be
	 * invoked prior to terminate the program.
	 * 
	 * @author Ravindra
	 */
	public void quit() {
		quitStatus = true;
	}
}
