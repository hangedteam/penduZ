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
import javax.swing.JPanel;

import com.sdz.controler.Controler;
import com.sdz.model.Model;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ServerPanel extends ZContainer implements Observer{

	private Dimension dimension = new Dimension();
	private int port = 0;
	private JButton bouton;
	private TextField portField;
	private TextField playerField;
	private Observable mod;
	private Controler controler;
	private boolean error = false;
	private Choice mode;
	private Fenetre fen;
	private JPanel leftContent;

	

	
	public ServerPanel(Dimension dim, Observable mod, boolean error, Fenetre fen){
		super(dim);
		this.mod = mod;
		this.error = error;
		this.controler = new Controler(mod);
		this.fen = fen;
		initPanel();
	}

	protected void initPanel(){
		 leftContent = new JPanel();

		/*		JPanel head = new JPanel();
		head.setPreferredSize(new Dimension(410, 100));
		Label serverLabel = new Label("Creation du serveur de jeu");
		serverLabel.setPreferredSize(new Dimension(300, 20));

		head.add(serverLabel, BorderLayout.NORTH);*/

		//System.out.println("Size : " + this.dimension.getWidth());
		//Dimension dim = new Dimension((int)(this.dimension.getWidth()/2), (int)this.dimension.getHeight());

		Dimension dim = new Dimension(410, 200);

		JPanel body = new JPanel();
		body.setPreferredSize(dim);
		body.setBackground(Color.white);

		Label playerLabel = new Label("How many players do you want : ");  
		playerLabel.setBounds(50,100, 100,30);    
		body.add(playerLabel);

		playerField = new TextField("2");  
		playerField.setBounds(50,100, 200,30);   
		body.add(playerField); 

		Label modeLabel = new Label("What game mode do you want : ");  
		modeLabel.setBounds(50,100, 100,30);    
		body.add(modeLabel);

		mode = new Choice();  
		mode.setBounds(100,100, 75,75);  
		mode.add("Mode 1");  
		mode.add("Mode 2");  
		mode.add("Mode 3");  
		body.add(mode);  

		Label portLabel = new Label("What is the port you want to use : ");  
		portLabel.setBounds(50,100, 100,30);    
		body.add(portLabel);

		portField = new TextField("1234");  
		portField.setBounds(50,100, 200,30);   
		body.add(portField); 

		BoutonListener bl = new BoutonListener();
		this.bouton = new JButton("Create server");
		/*bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				//Fenetre fenetre = new Fenetre(mod);
		 		fen.waitForPlayers(5);
				conteneur.removeAll();
				//conteneur.removeAll();
				//conteneur.add(new WaitingPanel(dim, mod, 5).getPanel(), BorderLayout.CENTER);
				//conteneur.revalidate();
				//initModel();
			}	    	
	    });*/
		bouton.addActionListener(bl);
		body.add(bouton);

		if(error) {
			System.out.println("error");
			// afficher erreur
		}

		leftContent.add(body, BorderLayout.CENTER);
		leftContent.setBackground(Color.white);

		this.panel.add(leftContent);

	}

	public class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("bouton");
			int port = 1234, numberPlayer = 2;/////////
			try{
				port = Integer.parseInt(portField.getText());
				numberPlayer = Integer.parseInt(playerField.getText());// check
			}
			catch(Exception e1) {
				JPanel conteneur = new JPanel();
				conteneur.removeAll();
				conteneur.add(new ServerPanel(dimension, mod,true,fen).getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				return;
			}
			System.out.println("creation du serveur sur le port :"+port);
			try (AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()) {
				server.bind(new InetSocketAddress("127.0.0.1", port));
				AsynchronousSocketChannel[] clients = null;
				for (int i = 0 ; i < numberPlayer ; i++) {
					System.out.println("boucle for "+i);
					Future<AsynchronousSocketChannel> acceptCon = server.accept();
					//Fenetre fenetre = new Fenetre(mod);
					leftContent.removeAll();
					fen.waitForPlayers(numberPlayer - i);
			 		
			 		//getPanel().removeAll();
			 		//fen.getConteneur().removeAll();
					
					clients[i] = acceptCon.get();
					if ((clients[i]!= null) && (clients[i].isOpen())) {
					//if (true) {
						System.out.println("New Client ");
						//Fenetre fenetre2 = new Fenetre(mod);
				 		//fen.waitForPlayers(numberPlayer - i);
						//fen.getConteneur().removeAll();
					}
					//clients[i].close();
				}


			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
