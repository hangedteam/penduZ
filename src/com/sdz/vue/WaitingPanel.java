package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sdz.controler.Controler;
import com.sdz.model.Model;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WaitingPanel extends ZContainer implements Observer{

	private Dimension dimension = new Dimension();
	private int port = 0;
	private JButton bouton;
	private TextField portField;
	private TextField playerField;
	private Observable mod;
	private Controler controler;
	private int playersNeeded;
	private Choice mode;
	private Fenetre fen;

	public WaitingPanel(Dimension dim, Observable mod, int playersNeeded, Fenetre fen){
		super(dim);
		dimension = dim;
		this.mod = mod;
		this.playersNeeded = playersNeeded;
		this.controler = new Controler(mod);
		this.fen = fen;
		initPanel();
	}

	protected void initPanel(){
		
		//Observable model = new Model();
		fen.setVisible(true);
		
		JPanel leftContent = new JPanel();

		Dimension dim = new Dimension(410, 200);

		JPanel body = new JPanel();
		body.setPreferredSize(dim);
		body.setBackground(Color.white);
		
		Label playerLabel = new Label("We are waiting for more players ... ");  
		playerLabel.setBounds(50,100, 100,30);    
		body.add(playerLabel);
		
		Label p2Label = new Label("We need "+playersNeeded+" players ");  
		p2Label.setBounds(50,100, 100,30);    
		body.add(p2Label);
	
		
		//Label playerLabel = new Label("Waiting for %d players",playersNeeded);  
		//playerLabel.setBounds(50,100, 100,30);    
		//body.add(playerLabel);

		leftContent.add(body, BorderLayout.CENTER);
		leftContent.setBackground(Color.white);

		this.panel.add(leftContent);
		
		

	}

	class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("bouton");

		}
	}

	@Override
	public void update(String mot, int pts, String imgPath, int nbreMot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void restart(String word) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showScore(Score[] list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accueil() {
		// TODO Auto-generated method stub

	}


}
