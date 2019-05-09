package com.sdz.model;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;

import javax.swing.JButton;

import com.sdz.observer.Observable;
import com.sdz.vue.ClientPanel;
import com.sdz.vue.Fenetre;

public class ConnexionHandler implements Runnable {

	private Word mot = null;
	int score = 0;
	AsynchronousSocketChannel conn;
	ObjectInputStream in;
	ObjectOutputStream out;
	Fenetre fen = null;
	Observable model;
	Dimension dim;
	private JButton bouton;
	ClientPanel panel;

	public ConnexionHandler(AsynchronousSocketChannel connP, Fenetre fenP, Dimension d, Observable mod,
			ClientPanel clientPanel) {
		System.out.println("connexionHandler creation, socket channel : " + connP.toString());
		conn = connP;
		fen = fenP;
		model = mod;
		dim = d;
		panel = clientPanel;
		try {
			out = new ObjectOutputStream(Channels.newOutputStream(conn));
			in = new ObjectInputStream(Channels.newInputStream(conn));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("run");
		while (conn.isOpen()) {
			try {
				System.out.println("avant de lire l'objet");
				// Message message = (Message) in.readObject();
				Object s = in.readObject();
				System.out.println("apres");
				if (s != null) {
					System.out.println(s.toString());
					if (Integer.parseInt(s.toString()) == -1) {
//						JPanel content = new JPanel();
//						content.setPreferredSize(new Dimension(500, 500));
//						content.setBackground(Color.blue);
						System.out.println("here");
//						this.bouton = new JButton("Launch the game");
//						bouton.setBackground(Color.red);
//						bouton.addActionListener(new ActionListener() {
//							public void actionPerformed(ActionEvent e) {
//								System.out.println("bouton");

//						conteneur.removeAll();
//						// model = new Model(mot);
//						initModel();
//						GamePanel gp = new GamePanel(size, model);
//						model.addObserver(gp);
//						conteneur.add(gp.getPanel(), BorderLayout.CENTER);
//						conteneur.revalidate();

						fen.gamePanel();
//						fen.initGamePanel();
//						fen.joinGame();
//							}
//						});
						// bouton.setAlignmentX(100);
						System.out.println("here24");
						// content.add(bouton);
						System.out.println("here25");
						// panel.add(content);
//						fen.dispose();
						System.out.println("here2");
//						Fenetre f = new Fenetre();
//						f.setVisible(true);
//						System.out.println("here3");
//						f.initGamePanel();
//						System.out.println("here4");
					}
//					System.out.println("message recu par le client : " + message.flag);
//					if (message.flag == Message.NEW_GAME) {
//						System.out.println("new game");
//						fen.initGamePanel(message.getMot());
//					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
