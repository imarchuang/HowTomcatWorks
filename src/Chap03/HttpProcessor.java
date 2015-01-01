package Chap03;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Chap02.Request;
import Chap02.Response;
import Chap02.ServletProcessor1;
import Chap02.StaticResourceProcessor;

public class HttpProcessor {
	public void process(Socket socket) {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new SocketInputStream(socket.getInputStream(), 2048);
			output = socket.getOutputStream();
			
			// create Request object and parse
			Request request = new Request(input);
			request.parse();
			
			// create Response object
			Response response = new Response(output);
			response.setRequest(request);
			
			// check if this is a request for a servlet or a static resource
			if (request.getUri().startsWith("/servlet/")) {
				ServletProcessor1 processor = new ServletProcessor1();
				processor.process(request, response);
			}
			else {
				StaticResourceProcessor processor = new StaticResourceProcessor();
				processor.process(request, response);
			}				
			socket.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
