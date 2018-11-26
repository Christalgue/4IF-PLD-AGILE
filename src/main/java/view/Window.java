package main.java.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.TextField;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTree;
import java.awt.Canvas;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

// https://examples.javacodegeeks.com/desktop-java/ide/eclipse/eclipse-windowbuilder-tutorial/

public class Window {
	
	protected final static String LOAD_MAP = "Charger un plan";
	protected static final String LOAD_DELIVERY_OFFER = "Charger une demande de livraison";
	protected static final String CALCULATE_CIRCUITS = "Calculer les tournees";
	protected static final String UNDO = "Undo";
	protected static final String ADD_DELIVERY = "Ajouter une livraison";
	protected static final String VALIDATE_ADD = "Valider l'ajout";
	protected static final String CANCEL_ADD = "Annuler l'ajout";
	protected static final String MOVE_DELIVERY = "Deplacer la livraison";
	protected static final String VALIDATE_MOVE = "Valider le deplacement";
	protected static final String CANCEL_MOVE = "Annuler le deplacement";
	protected static final String DELETE_DELIVERY = "Supprimer la livraison";
	protected static final String VALIDATE_DELETE = "Valider la suppression";
	protected static final String CANCEL_DELETE = "Annuler la suppression";

	private JFrame frame;
	
	private ArrayList<JButton> buttons;
	private JLabel messageFrame;
	//private GraphicView graphicView;
	//private TextualView textualView;
	private ButtonsListener buttonsListener;
	private MouseListener mouseListener;
	private KeyListener keyListener;

	private final String[] buttonsTitles = new String[]{LOAD_MAP, LOAD_DELIVERY_OFFER, 
			CALCULATE_CIRCUITS, UNDO, ADD_DELIVERY, VALIDATE_ADD,
			CANCEL_ADD, MOVE_DELIVERY, VALIDATE_MOVE, CANCEL_MOVE,DELETE_DELIVERY, VALIDATE_DELETE, CANCEL_DELETE};
	
	protected static final int graphicViewHeight = 400;
	protected static final int graphicViewWidth = 400;
	
	private final int messageFrameHeight = 80;
	//private final int largeurVueTextuelle = 400;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
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
	/*public Window( CircuitManagement circuitManagement, Controller controller ){
		
		setLayout(null);
		createButtons(controller);
		
		/*messageFrame = new JLabel();
		messageFrame.setBorder(BorderFactory.createTitledBorder("Messages..."));
		getContentPane().add(messageFrame);*/
		
		/*graphicView = new graphicView (CircuitManagement circuitManagement, this);
		
		//textualView = new VueTextuelle(plan, this);
		
		mouseListener = new MouseListener(controller,GraphicView,this);
		
		addMouseListener(mouseListener);
		addKeyListener = new keyListener(controller);
		addKeyListener(keyListener);
		
		initialize();
		setVisible(true);
	}*/

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1280, 720);
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
		btnChargerUneDemande.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnChargerUneDemande);
		
		JTextArea txtrNombreDeLivreurs = new JTextArea();
		txtrNombreDeLivreurs.setText("Nombre de livreurs :");
		panel.add(txtrNombreDeLivreurs);
		
		TextField textFieldNbLivreurs = new TextField();
		textFieldNbLivreurs.setText("12");
		panel.add(textFieldNbLivreurs);
		
		JButton btnRetour = new JButton("Retour");
		panel.add(btnRetour);
		
		JButton btnCalculDesTournees = new JButton("Calcul des tourn�es");
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
			new DefaultMutableTreeNode("Liste des tournées") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					node_1 = new DefaultMutableTreeNode("Tournée 1");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Durée totale : 1h45min"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 4"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tournée 2");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Durée totale : 2h"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 4"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tournée 3");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Durée totale : 1h53min"));
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
		frame.getContentPane().add(canvas, BorderLayout.WEST);
	}

}
