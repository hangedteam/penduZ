package com.sdz.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.AsynchronousSocketChannel;

public class ClientHandler implements Runnable {
	Message msg;
	AsynchronousSocketChannel[] clients = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	int allowedAttempts = 0;

}
