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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sdz.controler.Controler;
import com.sdz.model.ClientHandler;
import com.sdz.model.Model;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
	//private Controler controler;
	private boolean error = false;
	private Choice mode;
	private Fenetre fen;
	private JPanel content;

	

	
	public ServerPanel(Dimension dim, Observable mod, boolean error, Fenetre fen){
		super(dim);
		dimension = dim;
		this.mod = mod;
		this.error = error;
		//this.controler = new Controler(mod);
		this.fen = fen;
		initPanel();
	}

	protected void initPanel(){
		//this.panel.removeAll();
		
		content = new JPanel();
		content.setPreferredSize(new Dimension(500,500));
		content.setBackground(Color.blue);
		content.setFont(arial);
		
		Label playerLabel = new Label("How many players do you want : ");  
		playerLabel.setBounds(50,100, 100,30);    
		content.add(playerLabel);

		playerField = new TextField("2");  
		playerField.setBounds(50,100, 200,30);   
		content.add(playerField); 

		Label modeLabel = new Label("What game mode do you want : ");  
		modeLabel.setBounds(50,100, 100,30);    
		content.add(modeLabel);

		mode = new Choice();  
		mode.setBounds(100,100, 75,75);  
		mode.add("Mode 1");  
		mode.add("Mode 2");  
		mode.add("Mode 3");  
		content.add(mode);  

		BoutonListener bl = new BoutonListener();
		this.bouton = new JButton("Create server");
		bouton.addActionListener(bl);
		bouton.setAlignmentX(100);
		content.add(bouton);

		if(error) {
			System.out.println("error");
			JTextArea errors = new JTextArea();
			errors.setBackground(Color.white);
			errors.setFont(dialog);
			errors.setText(	"\n\n\n\n\n\nerreur erreur erreur erreur erreur erreur erreur");
			errors.setEditable(false);
			content.add(errors, BorderLayout.SOUTH);
		}
			
		this.panel.add(content);
		
		//this.panel.revalidate();

	}
	
	public boolean check() {
		return false;
	}

	public class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("bouton");
			action();
		}
	}


	public void action() {

//		if(!check()) {
//			System.out.println("check false");
//			fen.serverCreation(dimension,mod,true,fen.getFenetre());
//			return;
//		}
		
		System.out.println("check true");
		int numberPlayer = 2;
		try{
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
			server.bind(null);
			//server.bind(new InetSocketAddress("127.0.0.1",1234));
			SocketAddress localAD = server.getLocalAddress();
			int p = ((InetSocketAddress)localAD).getPort();
			//String ad = ((InetSocketAddress)localAD).getAddress().toString();
			JOptionPane.showMessageDialog(null, "Vous avez lancé un serveur à l'adresse 127.0.0.1 sur le port "+p, "Serveur ouvert", JOptionPane.INFORMATION_MESSAGE);
			AsynchronousSocketChannel[] clients = new AsynchronousSocketChannel[numberPlayer];
			for (int i = 1 ; i <= numberPlayer ; i++) {
				System.out.println("boucle for "+i);
				Future<AsynchronousSocketChannel> acceptCon = server.accept();
				clients[i-1] = acceptCon.get();
				System.out.println(clients[i-1].getLocalAddress());
				if ((clients[i-1]!= null) && (clients[i-1].isOpen())) {
					System.out.println("New Client ");
					if (i == numberPlayer) {
						JOptionPane.showMessageDialog(null, "Tous les joueurs sont connectés.", "Lancement de la partie", JOptionPane.INFORMATION_MESSAGE);
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Il y a "+i+"joueurs connectés.", "En attente de joueurs", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				//clients[i-1].close();
			}
			ClientHandler clientHandler = new ClientHandler(clients);

		} catch (Exception e1) {
			e1.printStackTrace();
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
		

	}


}
