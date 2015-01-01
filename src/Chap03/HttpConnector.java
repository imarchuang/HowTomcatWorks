package Chap03;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {
	// shutdown command
	boolean stopped;
	private String scheme = "http";
	
	public String getScheme() {
		return this.scheme;
	}
	
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		// loop waiting for a request
		while (!stopped) {
			Socket socket = null;
			HttpProcessor processor = new HttpProcessor(this);
			processor.process(socket);
		}
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
