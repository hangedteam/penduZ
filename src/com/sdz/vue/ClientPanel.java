package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sdz.controler.Controler;
import com.sdz.model.Model;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ClientPanel extends ZContainer implements Observer{

	private Dimension dimension = new Dimension();
	private int port = 0;
	private JButton bouton;
	private TextField portField;
	private TextField addressField;
	private Observable mod;
	//private Controler controler;
	private boolean error = false;
	private Choice mode;
	private Fenetre fen;
	private JPanel content;

	

	
	public ClientPanel(Dimension dim, Observable mod, Fenetre fen){
		super(dim);
		dimension = dim;
		this.mod = mod;
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
		
		Label addressLabel = new Label("Address : ");  
		addressLabel.setBounds(50,100, 100,30);    
		content.add(addressLabel);

		addressField = new TextField("");  
		addressField.setBounds(50,100, 200,30);   
		content.add(addressField); 
		
		Label portLabel = new Label("Port : ");  
		portLabel.setBounds(50,100, 100,30);    
		content.add(portLabel);

		portField = new TextField("");  
		portField.setBounds(50,100, 200,30);   
		content.add(portField); 

		BoutonListener bl = new BoutonListener();
		this.bouton = new JButton("Rejoindre");
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


		System.out.println("check true");
		int port = 0;
		String address;
		try{
			port = Integer.parseInt(portField.getText());
			address = addressField.getText();
		}
		catch(Exception e1) {
			return;
		}
		
		try (AsynchronousSocketChannel client = AsynchronousSocketChannel.open()) {
	         Future<Void> result = client.connect(new InetSocketAddress(address, port));
	         //Future<Void> result = client.connect(new InetSocketAddress("127.0.0.1",1234));
//	         result.get();
//	         String str= sc.next();
//	         ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
//	         Future<Integer> writeval = client.write(buffer);
//	         System.out.println("Writing to server: "+str);
//	         writeval.get();
//	         buffer.flip();
//	         Future<Integer> readval = client.read(buffer);
//	         readval.get();
//	         System.out.println("Received from server: " + new String(buffer.array()));
//	         
//	         buffer.clear();
	      }
	      catch (Exception e) {
	         e.printStackTrace();
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
