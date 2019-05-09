package com.sdz.model;

public class Message {

	public static final int NEW_GAME = -1;

	public int flag;
	private Word mot;

	Message(int f, Word mot) {
		flag = f;
		this.mot = mot;
	}

	public Word getMot() {
		return mot;
	}

	public void setMot(Word mot) {
		this.mot = mot;
	}

}
