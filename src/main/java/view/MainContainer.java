package main.java.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.TextField;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Panel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JTree;
import java.awt.ScrollPane;
import javax.swing.JMenuBar;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JList;
import java.awt.Canvas;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

// https://examples.javacodegeeks.com/desktop-java/ide/eclipse/eclipse-windowbuilder-tutorial/

public class MainContainer {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainContainer window = new MainContainer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainContainer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 821, 499);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton btnChargeUnPlan = new JButton("Charger un plan");
		btnChargeUnPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnChargeUnPlan);
		
		JButton btnChargerUneDemande = new JButton("Charger une demande de livraison");
		panel.add(btnChargerUneDemande);
		
		JTextArea txtrNombreDeLivreurs = new JTextArea();
		txtrNombreDeLivreurs.setText("Nombre de livreurs :");
		panel.add(txtrNombreDeLivreurs);
		
		TextField textFieldNbLivreurs = new TextField();
		textFieldNbLivreurs.setText("12");
		panel.add(textFieldNbLivreurs);
		
		JButton btnRetour = new JButton("Retour");
		panel.add(btnRetour);
		
		JButton btnCalculDesTournees = new JButton("Calcul des tournées");
		btnCalculDesTournees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnCalculDesTournees);
		
		//To change icons for the tree
		//https://stackoverflow.com/questions/20691946/set-icon-to-each-node-in-jtree
		JTree tree = new JTree();
		tree.setBorder(new LineBorder(new Color(0, 0, 0)));
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Liste des tourn\u00E9es") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					node_1 = new DefaultMutableTreeNode("Tourn\u00E9e 1");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Dur\u00E9e totale : 1h45min"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 4"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tourn\u00E9e 2");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Dur\u00E9e totale : 2h"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 4"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tourn\u00E9e 3");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Dur\u00E9e totale : 1h53min"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
						node_1.add(node_2);
					add(node_1);
				}
			}
		));
		frame.getContentPane().add(tree, BorderLayout.EAST);
		
		Canvas canvas = new Canvas();
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
	}

}
