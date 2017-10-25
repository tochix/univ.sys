package com.ogb.anselm.univ.sys;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start of main");
	    
		if ((args.length < 1)) {
	    		System.err.println("Usage: java Server <port number>");
	    		System.exit(1);
	    }
	    
		int portNumber = Integer.parseInt(args[0]);
		//int portNumber = 8024;
	    ExecutorService executor = null;
	    PromptHandler promptHandler = new PromptHandler();
	    
	    try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
	    		executor = Executors.newFixedThreadPool(3);
	    		System.out.println("Waiting for clients");
	    		
	    		while (true) {
		        Socket clientSocket = serverSocket.accept();
		        Runnable worker = new RequestHandler(clientSocket, promptHandler);
		        executor.execute(worker);
	    		}
	    		
	    } catch (IOException e) {
	    		System.out.println("Exception caught when trying to listen on port "
	    				+ portNumber + " or listening for a connection");
	       System.out.println(e.getMessage());
	    } finally {
	    		if (executor != null) {
	    			executor.shutdown();
	    		}
	    }
	}
}
