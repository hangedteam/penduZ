package com.sdz.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;

public class ClientHandler implements Runnable {
	Message msg;
	AsynchronousSocketChannel[] clients = null;
	// ObjectInputStream in = null;
	ObjectOutputStream out = null;
	int allowedAttempts = 0;
	Model model;

	public ClientHandler(AsynchronousSocketChannel[] cls) {
		clients = cls;
		model = new Model();
	}

	@Override
	public void run() {
		System.out.println("run");
		// Message start = new Message(Message.NEW_GAME, model.getMot());
		for (int i = 0; i < clients.length; i++) {
			try {
				System.out.println(clients[i].isOpen());
				out = new ObjectOutputStream(Channels.newOutputStream(clients[i]));
				System.out.println("avant writeObject");
				out.writeObject("-1");
				System.out.println("apres");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
