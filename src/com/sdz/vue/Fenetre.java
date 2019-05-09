package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.sdz.model.Model;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

public class Fenetre extends JFrame implements Observer {

	private JMenuBar menu = null;
	private JMenu fichier = null;
	private JMenuItem newServer = null;
	private JMenuItem joinGame = null;
	private JMenuItem gamePanel = null;
	private JMenuItem quitter = null;
	private JMenu apropos = null;
	private JMenuItem rules = null;
	private JMenu jouer = null;
	private JMenuItem lancerpartie = null;
	private JPanel conteneur = new JPanel();
	private Dimension size;
	private Observable model;
	private List<ServerPanel> serverList = new ArrayList<>();

//	private char[] lettreTab = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q','r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public Fenetre() {
		System.out.println("Fenetre");

		this.setTitle("Le pendu");
		this.setSize(900, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// this.model = new Model();
		// this.model.addObserver(this);
		this.size = new Dimension(this.getWidth(), this.getHeight());

		menu = new JMenuBar();

		fichier = new JMenu("Fichier");
		// fichier.setMnemonic('f');
		// fichier.addActionListener(this);

		newServer = new JMenuItem("Nouveau serveur");
		// newServer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		newServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				serverCreation(size, model, false, getFenetre());
			}
		});

		joinGame = new JMenuItem("Rejoindre une partie");
		// joinGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
		joinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				joinGame();
			}
		});

		// juste pour un test
		gamePanel = new JMenuItem("gamePanel");
		// joinGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
		gamePanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamePanel();
			}
		});

		quitter = new JMenuItem("Quitter");
		// quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
		// KeyEvent.CTRL_MASK));
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		fichier.add(newServer);
		fichier.add(joinGame);
		fichier.add(gamePanel);
		fichier.addSeparator();
		fichier.add(quitter);

//		apropos = new JMenu("À propos");
//		apropos.setMnemonic('o');
//		// apropos.addActionListener(this);
//
//		rules = new JMenuItem("Règles du jeu");
//		rules.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				conteneur.removeAll();
//				conteneur.add(new RulesPanel(size).getPanel(), BorderLayout.CENTER);
//				conteneur.revalidate();
//				model.reset();
//			}
//		});
//
//		apropos.add(rules);

		menu.add(fichier);
		// menu.add(apropos);

		this.conteneur.setPreferredSize(this.size);
		this.conteneur.setBackground(Color.yellow);
		this.conteneur.add(new AccueilPanel(this.size).getPanel());
		this.setContentPane(this.conteneur);

		this.setJMenuBar(menu);
	}

	public void showScore(Score[] list) {
		System.out.println("showScore");
		conteneur.removeAll();
		conteneur.add(new ScorePanel(this.size, list).getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
		model.reset();
	}

	public void accueil() {
		System.out.println("Mise à jour de l'accueil…");
		conteneur.removeAll();
		conteneur.add(new AccueilPanel(size).getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
	}

//	public void waitForPlayers(int nbP) {
//		System.out.println("En attente de joueur …");
//		// cont.removeAll();
//		conteneur.removeAll();
//		conteneur.add(new WaitingPanel(size, model, nbP, this).getPanel(), BorderLayout.CENTER);
//		conteneur.revalidate();
//	}

	public void gamePanel() {
		System.out.println("gamePanel");
		removeContent();
		// conteneur.removeAll();
		System.out.println("size : " + size + ", model : " + model);
		JPanel p = new GamePanel(size, model).getPanel();
		p.setBackground(Color.red);
		conteneur.add(p, BorderLayout.CENTER);
		conteneur.revalidate();
		this.repaint();

//		jouer = new JMenu("Jouer");
//
//		lancerpartie = new JMenuItem("Lancer la partie");
//		lancerpartie.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				conteneur.removeAll();
//				conteneur.add(new GamePanel(size, model).getPanel(), BorderLayout.CENTER);
//				conteneur.revalidate();
//				// model.reset();
//			}
//		});
//
//		jouer.add(lancerpartie);
	}

	public void joinGame() {
		System.out.println("joinGame");
		removeContent();
		// conteneur.removeAll();
		ClientPanel cp = new ClientPanel(size, model, getFenetre());
		JPanel pan = cp.getPanel();
		pan.setBackground(Color.blue);
		conteneur.add(pan, BorderLayout.CENTER);
		conteneur.revalidate();
		// this.setContentPane(this.conteneur);
		this.repaint();
		// model.reset();
	}

	public void removeContent() {
		System.out.println("removeContent");
		conteneur.removeAll();
		this.getContentPane().removeAll();
		this.repaint();
	}

	public void serverCreation(Dimension dim, Observable mod, boolean error, Fenetre fen) {
		System.out.println("Creation serveur …");
		removeContent();
		conteneur.removeAll();
		// conteneur.validate();
		ServerPanel sp = new ServerPanel(dim, mod, error, fen);
		JPanel pan = sp.getPanel();
		pan.setBackground(Color.red);
		conteneur.add(pan, BorderLayout.CENTER);
		conteneur.revalidate();
		// this.setContentPane(this.conteneur);
		// this.repaint();
	}

	public Fenetre getFenetre() {
		System.out.println("getFenetre");
		return this;
	}

	private void initModel() {
		System.out.println("initModel");
		this.model = new Model();
		this.model.addObserver(this);
	}

	public void update(String mot, int pts, String imgPath, int nbreMot) {
		System.out.println("update");
	}

	public void restart(String word) {
		System.out.println("restart");
	}

	public JPanel getConteneur() {
		System.out.println("getConteneur");
		return conteneur;
	}

	public void initGamePanel() {
		System.out.println("initGamePanel");
		conteneur.removeAll();
		// model = new Model(mot);
		initModel();
		GamePanel gp = new GamePanel(size, model);
		model.addObserver(gp);
		conteneur.add(gp.getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
		// initModel();

	}

	public List<ServerPanel> getServerList() {
		System.out.println("getServerList");
		return serverList;
	}

	public void setServerList(List<ServerPanel> sl) {
		System.out.println("setServerList");
		serverList = sl;
		System.out.println("Reading after modif the serverpanel list");
		for (ServerPanel sPanel : serverList) {
			System.out.println("port : " + sPanel.getPort());
		}
	}

}
