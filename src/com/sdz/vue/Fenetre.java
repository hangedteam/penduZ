package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sdz.model.Model;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

public class Fenetre extends JFrame implements Observer{

	private JMenuBar menu = null;

	  private JMenu fichier = null;
	  private JMenuItem newServer = null;
	  private JMenuItem joinGame = null;
	  private JMenuItem quitter = null;
	  private JMenu apropos = null;
	  private JMenuItem apropos2 = null;
	  private JMenuItem rules = null;
	  private JPanel conteneur = new JPanel();
	  private Dimension size;
	  private Observable model;

	  
	  private char[] lettreTab = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
              'j',
              'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
              't',
              'u', 'v', 'w', 'x', 'y', 'z'};
	   
	  

	public Fenetre(Observable obs){
		System.out.println("Fenetre");
		this.setTitle("Le pendu");
	    this.setSize(900, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    
	    this.model = obs;
	    this.model.addObserver(this);
	    this.size = new Dimension(this.getWidth(), this.getHeight());
	    
	    menu = new JMenuBar();

	    fichier = new JMenu("Fichier");
	    fichier.setMnemonic('f');
	    //fichier.addActionListener(this);

	    newServer = new JMenuItem("Nouveau serveur");
	    //newServer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
	   newServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				conteneur.removeAll();
				conteneur.add(new ServerPanel(size, model, false, getFenetre()).getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				//initModel();
			}	    	
	    });

	    joinGame = new JMenuItem("Rejoindre une partie");
	    //joinGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
	    joinGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				conteneur.removeAll();
				conteneur.add(new ScorePanel(size, model.getScores()).getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				model.reset();
			}
	    });

	    quitter = new JMenuItem("Quitter");
	    quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,KeyEvent.CTRL_MASK));
	    quitter.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		System.exit(0);
	    	}
	    });

	    fichier.add(newServer);
	    fichier.add(joinGame);
	    fichier.addSeparator();
	    fichier.add(quitter);

	    apropos = new JMenu("À propos");
	    apropos.setMnemonic('o');
	    //apropos.addActionListener(this);

	    rules = new JMenuItem("Règles du jeu");
	    rules.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				conteneur.removeAll();
				conteneur.add(new RulesPanel(size).getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				model.reset();
			}	    	
	    });

	    apropos.add(rules);

	    menu.add(fichier);
	    menu.add(apropos);
	    
	    this.conteneur.setPreferredSize(this.size);
	    this.conteneur.setBackground(Color.white);
	    this.conteneur.add(new AccueilPanel(this.size).getPanel());
	    this.setContentPane(this.conteneur);
	    
	    this.setJMenuBar(menu);
	}
	
	public void showScore(Score[] list){
		System.out.println("showScore");
		conteneur.removeAll();
		conteneur.add(new ScorePanel(this.size, list).getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
		model.reset();
	}
	
	public void accueil(){
		System.out.println("Mise à jour de l'accueil…");
		conteneur.removeAll();
		conteneur.add(new AccueilPanel(size).getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
	}
	
	public void waitForPlayers(int nbP){
		System.out.println("En attente de joueur …");
		//cont.removeAll();
		conteneur.removeAll();
		conteneur.add(new WaitingPanel(size, model, nbP,this).getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
	}

	public Fenetre getFenetre() {
		return this;
	}
	
	private void initModel(){
		System.out.println("initModel");
		this.model = new Model();
		this.model.addObserver(this);
	}
	public void update(String mot, int pts, String imgPath, int nbreMot) {}
	public void restart(String word) {}

	public JPanel getConteneur() {
		return conteneur;
	}

}
