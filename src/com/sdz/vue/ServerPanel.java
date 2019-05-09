package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sdz.model.ClientHandler;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

public class ServerPanel extends ZContainer implements Observer {

	private Dimension dimension = new Dimension();
	private int port = 0;
	private JButton bouton;
	private TextField playerField;
	private Observable mod;
	// private Controler controler;
	private boolean error = false;
	private Choice mode;
	private Fenetre fen;
//	private JPanel content;

	public ServerPanel(Dimension dim, Observable mod, boolean error, Fenetre fen) {
		super(dim);
		System.out.println("creating server panel");
		dimension = dim;
		this.mod = mod;
		this.error = error;
		// this.controler = new Controler(mod);
		this.fen = fen;
		initPanel();
	}

	protected void initPanel() {
		System.out.println("init serverpanel");
		// this.panel.removeAll();

//		content = new JPanel();
//		content.setPreferredSize(new Dimension(500, 500));
//		content.setBackground(Color.blue);
//		content.setFont(arial);

		Label playerLabel = new Label("How many players do you want : ");
		playerLabel.setBounds(50, 100, 100, 30);
		this.panel.add(playerLabel);

		playerField = new TextField("1");
		playerField.setBounds(50, 100, 200, 30);
		this.panel.add(playerField);

		Label modeLabel = new Label("What game mode do you want : ");
		modeLabel.setBounds(50, 100, 100, 30);
		this.panel.add(modeLabel);

		mode = new Choice();
		mode.setBounds(100, 100, 75, 75);
		mode.add("Mode 1");
		mode.add("Mode 2");
		mode.add("Mode 3");
		this.panel.add(mode);

		BoutonListener bl = new BoutonListener();
		this.bouton = new JButton("Create server");
		bouton.addActionListener(bl);
		bouton.setAlignmentX(100);
		this.panel.add(bouton);

		if (error) {
			System.out.println("error");
			JTextArea errors = new JTextArea();
			errors.setBackground(Color.white);
			errors.setFont(dialog);
			errors.setText("\n\n\n\n\n\nerreur erreur erreur erreur erreur erreur erreur");
			errors.setEditable(false);
			this.panel.add(errors, BorderLayout.SOUTH);
		}

//		this.panel.add(content);

		// this.panel.revalidate();

	}

	public boolean check() {
		System.out.println("check");
		return false;
	}

	public class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("bouton");
			action();
			List<ServerPanel> sl = fen.getServerList();
			sl.add(getObject());
			fen.setServerList(sl);

		}

	}

	public ServerPanel getObject() {
		System.out.println("getObject");
		// TODO Auto-generated method stub
		return this;
	}

	public void action() {
		System.out.println("action");
//		if(!check()) {
//			System.out.println("check false");
//			fen.serverCreation(dimension,mod,true,fen.getFenetre());
//			return;
//		}

		System.out.println("check true");
		int numberPlayer = 2;
		try {
			numberPlayer = Integer.parseInt(playerField.getText());// check
		} catch (Exception e1) {
			JPanel conteneur = new JPanel();
			conteneur.removeAll();
			conteneur.add(new ServerPanel(dimension, mod, true, fen).getPanel(), BorderLayout.CENTER);
			conteneur.revalidate();
			return;
		}

		try (AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()) {
			server.bind(null);
			// server.bind(new InetSocketAddress("127.0.0.1",1234));
			SocketAddress localAD = server.getLocalAddress();
			setPort(((InetSocketAddress) localAD).getPort());
			System.out.println("creation du serveur sur le port :" + getPort());
			// String ad = ((InetSocketAddress)localAD).getAddress().toString();
			JOptionPane.showMessageDialog(null,
					"Vous avez lancé un serveur à l'adresse 127.0.0.1 sur le port " + getPort(), "Serveur ouvert",
					JOptionPane.INFORMATION_MESSAGE);
			AsynchronousSocketChannel[] clients = new AsynchronousSocketChannel[numberPlayer];
			for (int i = 1; i <= numberPlayer; i++) {
				System.out.println("boucle for " + i);
				Future<AsynchronousSocketChannel> acceptCon = server.accept();
				clients[i - 1] = acceptCon.get();
				System.out.println(clients[i - 1].getLocalAddress());
				if ((clients[i - 1] != null) && (clients[i - 1].isOpen())) {
					System.out.println("New Client ");
					if (i == numberPlayer) {
						JOptionPane.showMessageDialog(null, "Tous les joueurs sont connectés.",
								"Lancement de la partie", JOptionPane.INFORMATION_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "Il y a " + i + "joueurs connectés.",
								"En attente de joueurs", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				// clients[i-1].close();
			}
			ClientHandler clientHandler = new ClientHandler(clients);
			System.out.println("avant run");
			clientHandler.run();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void update(String mot, int pts, String imgPath, int nbreMot) {
		System.out.println("update");
		// TODO Auto-generated method stub

	}

	@Override
	public void restart(String word) {
		System.out.println("restart");
		// TODO Auto-generated method stub

	}

	@Override
	public void showScore(Score[] list) {
		System.out.println("showscore");
		// TODO Auto-generated method stub

	}

	@Override
	public void accueil() {
		System.out.println("accueil");

	}

	public int getPort() {
		System.out.println("getport");
		return port;
	}

	public void setPort(int port) {
		System.out.println("setport");
		this.port = port;
	}

}
