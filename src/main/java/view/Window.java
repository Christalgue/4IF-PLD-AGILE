package main.java.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextField;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import main.java.controller.Controller;
import main.java.entity.AtomicPath;
import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.utils.PopUpType;


public class Window extends JFrame{
	
	private Controller controller;
	private GraphicView graphicView;
	private TextualView textualView;
	
	protected JLabel messageField;
	
	private static JFileChooser chooser;

	protected static ButtonsListener buttonsListener;
	protected static MouseListener mouseListener;	
	
	protected static final String LOAD_MAP = "Charger un plan";
	protected static final String LOAD_DELIVERY_OFFER = "Charger une demande de livraison";
	protected static final String CALCULATE_CIRCUITS = "Calculer les tournees";
	protected static final String ADD_DELIVERY = "Ajouter une livraison";
	protected static final String DELETE_DELIVERY = "Supprimer la livraison";
	protected static final String MOVE_DELIVERY = "Deplacer la livraison";
	protected static final String CONTINUE_CALCULATION = "Continuer le calcul des tournees";
	protected static final String STOP_CALCULATION = "Arreter le calcul des tournees";
	
	protected static TextField setNameOfMap;
	protected static TextField setNameOfDeliveryList;
	protected static TextField numberOfDeliveryMen;
	
	protected static PopUp popUp;
	
	protected static JButton loadDeliveryList;
	protected static JButton calculateCircuitButton;
	protected static JButton addDeliveryButton;
	protected static JButton deleteDeliveryButton;
	protected static JButton moveDeliveryButton;
	
	protected static final int windowWidth = 1280;
	protected static final int windowHeight = 720;
	protected static final int buttonPanelHeight =50;
	protected static final int graphicWidth = 1030;
	protected static final int messageFieldHeight = 40;
	protected static final int buttonHeight = 40;
	protected static final int buttonSpace = 10;
	
	
	protected static final int pathWidth = 3;
	
	protected JTree textualViewTree;
	protected DefaultMutableTreeNode treeRoot;
	
	protected Delivery selectedNode;
	protected Delivery hoverNode;
	
	/**
	 * Default constructor
	 */
	public Window () {
		
	}
	
	/**
	 * Create the application.
	 */
	public Window (Controller controller){
		
		this.controller = controller;
		
		buttonsListener = new ButtonsListener(controller);
		CircuitManagement circuitManagement = controller.getCircuitManagement();
		
		//////////////////////////////CREATE THE MAIN WINDOW//////////////////////////////
		setControllerWindow(this);
		
		//////////////////////////////CREATE THE HEADER PANEL/////////////////////////////
		JPanel buttonPanel = new JPanel();
		fillButtonPanel(buttonPanel);
		
		//////////////////////////////CREATE THE GRAPHIC VIEW//////////////////////////////
		
		this.graphicView = new GraphicView (circuitManagement, windowHeight-buttonPanelHeight-messageFieldHeight, graphicWidth, pathWidth);
		setGraphicView(this.graphicView);
		mouseListener = new MouseListener(controller, this.graphicView, this);
		this.graphicView.addMouseListener(mouseListener);
		this.graphicView.addMouseMotionListener(mouseListener);
		
		//////////////////////////////CREATE THE TEXTUAL VIEW/////////////////////////////
		
		this.treeRoot = createTree();
		this.textualViewTree = new JTree (this.treeRoot);
		this.textualView = new TextualView (circuitManagement, windowHeight-buttonPanelHeight, windowWidth-graphicWidth, this.textualViewTree);
		setTextualView(this.textualView);
		
		//////////////////////////////CREATE THE MESSAGE FIELD/////////////////////////////
		
		this.messageField = new JLabel();
		setMessageField(this.messageField);
		
		//////////////////////////////ADD PANELS TO THE WINDOW//////////////////////////////
		this.getContentPane().add(buttonPanel);
		this.getContentPane().add(this.textualView);
		this.getContentPane().add(this.messageField);
		this.getContentPane().add(this.graphicView);
		
		////////////////////////////// MAKE THE FRAME VISBLE AND PAINTABLE ////////////////////////////////
		this.setVisible(true);
		
		this.graphicView.setGraphics();
		
	}
	
	private static DefaultMutableTreeNode createTree() {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		return root;
	}

	//////////////////////////////PUT COMPOSANTS IN PLACE/////////////////////////////
	private static void setMessageField(JLabel messageField) {
		messageField.setSize( graphicWidth, messageFieldHeight);
		messageField.setLocation(0, buttonPanelHeight);
		messageField.setOpaque(true);
		messageField.setForeground(Color.WHITE);
		messageField.setBackground(Color.GREEN);
		messageField.setText("Veuillez selectionner un plan a charger");
	
	}

	public static void setControllerWindow(Window window) {
		
		window.setLayout(null);
		window.setSize(windowWidth, windowHeight);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setTitle("Deliver'IF");
		window.setResizable(false);
		window.setLocationRelativeTo(null);
	
	}
	
	private static void setGraphicView(GraphicView graphicView) {
		
		graphicView.setLocation(0, buttonPanelHeight+messageFieldHeight);
		graphicView.setLayout(null);
		graphicView.setBackground(Color.LIGHT_GRAY);
		graphicView.setSize(graphicWidth, windowHeight-buttonPanelHeight-messageFieldHeight);
		
	}
	
	private static void setTextualView(TextualView textualView) {
		
		textualView.setLocation(graphicWidth, buttonPanelHeight);
		textualView.setBackground(Color.WHITE);
		textualView.setSize(windowWidth-graphicWidth, windowHeight-buttonPanelHeight);
		textualView.setLayout(null);
		
		addDeliveryButton = new JButton(ADD_DELIVERY);
		addDeliveryButton.addActionListener(buttonsListener);
		addDeliveryButton.setVisible(true);
		addDeliveryButton.setEnabled(false);
		addDeliveryButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		addDeliveryButton.setSize(windowWidth-graphicWidth-20, buttonHeight);
		textualView.add(addDeliveryButton);
	
		deleteDeliveryButton = new JButton(DELETE_DELIVERY);
		deleteDeliveryButton.addActionListener(buttonsListener);
		deleteDeliveryButton.setVisible(true);
		deleteDeliveryButton.setEnabled(false);
		deleteDeliveryButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight*2+buttonSpace+50));
		deleteDeliveryButton.setSize(windowWidth-graphicWidth-20, buttonHeight);
		textualView.add(deleteDeliveryButton);	
	
		moveDeliveryButton = new JButton(MOVE_DELIVERY);
		moveDeliveryButton.addActionListener(buttonsListener);
		moveDeliveryButton.setVisible(true);
		moveDeliveryButton.setEnabled(false);
		moveDeliveryButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight+50));
		moveDeliveryButton.setSize(windowWidth-graphicWidth-20, buttonHeight);
		textualView.add(moveDeliveryButton);
			
	}
	
	public static void fillButtonPanel(JPanel buttonPanel) {
		
		buttonPanel.setSize(windowWidth, buttonPanelHeight);
		buttonPanel.setBackground(Color.WHITE);
		
		setNameOfMap = new TextField();
		setNameOfMap.setText("resources/xml/moyenPlan.xml");
		buttonPanel.add(setNameOfMap);
		
		JButton loadMapButton = new JButton(LOAD_MAP);
		loadMapButton.addActionListener(buttonsListener);
		buttonPanel.add(loadMapButton);
		
		setNameOfDeliveryList = new TextField();
		setNameOfDeliveryList.setText("resources/xml/dl-moyen-9.xml");
		setNameOfDeliveryList.setEditable(true);
		buttonPanel.add(setNameOfDeliveryList);
		
		loadDeliveryList = new JButton(LOAD_DELIVERY_OFFER);
		loadDeliveryList.addActionListener(buttonsListener);
		loadDeliveryList.setEnabled(false);
		buttonPanel.add(loadDeliveryList);
		
		JTextArea labelNumberOfDeliveryMen = new JTextArea();
		labelNumberOfDeliveryMen.setText("Nombre de livreurs :");
		labelNumberOfDeliveryMen.setEditable(false);
		buttonPanel.add(labelNumberOfDeliveryMen);
		
		numberOfDeliveryMen = new TextField();
		numberOfDeliveryMen.setText("1");
		numberOfDeliveryMen.setEditable(true);
		buttonPanel.add(numberOfDeliveryMen);
	
		/*JButton undoButton = new JButton("Retour");
		buttonPanel.add(undoButton);
		undoButton.addActionListener(buttonsListener);*/
		
		calculateCircuitButton = new JButton(CALCULATE_CIRCUITS);
		calculateCircuitButton.addActionListener(buttonsListener);
		calculateCircuitButton.setEnabled(false);
		buttonPanel.add(calculateCircuitButton);
	}
	
	//////////////////////////////GET DATA FROM WINDOW/////////////////////////////
	protected String getFile () {
			
		chooser = new JFileChooser();
		int returnValue = chooser.showOpenDialog(controller.getWindow());
		String filePath = "";
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			filePath = chooser.getSelectedFile().getAbsolutePath();
		}
		return filePath;
	}

	//// Alternatives aux gestionnaires de fichiers
	public static void getDeliveryMenNumber() {
		
		buttonsListener.setDeliveryMenNumber(Integer.parseInt(numberOfDeliveryMen.getText()));
		
	}
	
	public static void getMapName() {
		
		buttonsListener.setMap(setNameOfMap.getText());
		
	}
	/////
	
	public static void getDeliveryListName() {
		
		buttonsListener.setDeliveryList(setNameOfDeliveryList.getText());
		
	}
	
	//////////////////////////////CHANGE DISPLAYED TEXT/////////////////////////////
	public void setTextualViewBorderTitle( String string) {	
		textualView.setBorder(BorderFactory.createTitledBorder(string));
	}
	
	public void setMessage( String string) {
		messageField.setBackground(Color.GREEN);
		messageField.setText(string);
	}
	
	public void setErrorMessage( String string) {
		messageField.setBackground(Color.RED);
		messageField.setText(string);
	}
	//////////////////////////////DRAW COMPOSANTS/////////////////////////////
	public void drawMap() {
		graphicView.removeAll();
		graphicView.update(graphicView.getGraphics());
		graphicView.paintMap();
	}
	
	public void drawDeliveries() {
		drawMap();
		graphicView.paintDeliveries();
		textualView.fillDeliveryTree();
	}
	
	public void drawCircuits() {
		drawDeliveries();
		graphicView.paintCircuits();
		textualView.fillCircuitTree();
	}
	
	public void nodeSelected(Delivery delivery) {
		if(selectedNode!=null){
			graphicView.unPaintNode( selectedNode);
		}
		
		graphicView.paintSelectedNode( delivery, true);
		hoverNode = null;
		selectedNode = delivery;
	}
	
	public void circuitSelected(Delivery selectedDelivery) {

		if ( selectedDelivery.getDuration() != -1 ) {
			for (Circuit circuit : controller.getCircuitManagement().getCircuitsList()) {
					
				for ( Delivery delivery :circuit.getDeliveryList()) {
					if ( selectedDelivery.getPosition()== delivery.getPosition()) {
						graphicView.paintSelectedCircuit(circuit); 
					}
				}
			}
		}
	}
	
	public void nodeHover(Delivery delivery) {
		//Mouse exit a node
		if(delivery == null && hoverNode!=null) {
			graphicView.unPaintNode( hoverNode);
			hoverNode = delivery;
		}
		//Cannot hover a selected node
		else if (selectedNode == null || (delivery != null && delivery.getPosition().getId()!=selectedNode.getPosition().getId())){
			//Mouse enter a node
			if(delivery!=null && hoverNode == null) {
				graphicView.paintSelectedNode( delivery, false);
			}
			//Mouse pass from one node to another
			else if(delivery!=null && delivery.getPosition().getId()!=hoverNode.getPosition().getId()) {
				graphicView.unPaintNode( hoverNode);
				graphicView.paintSelectedNode( delivery, false);
			}
			hoverNode = delivery;
		}
	}
	
	public void fillDeliveryTree() {
		textualView.fillDeliveryTree();
	}
	
	//////////////////////////////BUTTON ACTIVATION/////////////////////////////
	public void enableButtonLoadDeliveriesList() {
		loadDeliveryList.setEnabled(true);
	}
	
	public void enableButtonCalculateCircuit() {
		calculateCircuitButton.setEnabled(true);
	}	
	
	public void enableButtonAddDelivery() {
		addDeliveryButton.setVisible(true);
		addDeliveryButton.setEnabled(true);
	}
	
	public void enableButtonDeleteDelivery() {
		deleteDeliveryButton.setVisible(true);;
		deleteDeliveryButton.setEnabled(true);
	}
	
	public void enableButtonMoveDelivery() {
		moveDeliveryButton.setVisible(true);;
		moveDeliveryButton.setEnabled(true);
	}

	
	//////////////////////////////BUTTON DESACTIVATION/////////////////////////////
	public void disableButtonLoadDeliveriesList() {
		loadDeliveryList.setEnabled(false);
	}
	
	public void disableButtonCalculateCircuit() {
		calculateCircuitButton.setEnabled(false);
	}	
	
	public void disableButtonAddDelivery() {
		addDeliveryButton.setEnabled(false);
	}
	
	public void disableButtonDeleteDelivery() {
		deleteDeliveryButton.setEnabled(false);
	}
	
	public void disableButtonMoveDelivery() {
		moveDeliveryButton.setEnabled(false);
	}
	
	public int getPopUpValue(PopUpType message, Window window) {
		
		return popUp.displayPopUp(message, window);
	}
	
	public void manageAddPopUpValue(int userChoice) {
		if (userChoice == 0) {
			try {
				controller.validateAdd();
			} catch (ManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			controller.cancelAdd();
		}
	}
	
	public void manageDeletePopUpValue(int userChoice) {
		if (userChoice == 0) {
			try {
				controller.validateDelete();
			} catch (ManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			controller.cancelDelete();
		}
	}
	
	/* Need to change the signature of validateDuration with a string 
	 * public void manageDurationPopUpValue(String inputValue) {
		if (inputValue != "") {
			try {
				controller.validateDuration(inputValue);
			} catch (ManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			controller.cancelDuration();
		}
	}*/
	
	public void manageMovePopUpValue(int userChoice) {
		if (userChoice == 0) {
			try {
				controller.validateMove();
			} catch (ManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			controller.cancelMove();
		}
	}
	
	 public void manageContinuePopUpValue(int userChoice) {
		if (userChoice == 0) {
			controller.continueCalculation(false);
		} else if (userChoice == 1) {
			controller.continueCalculation(true);
		}
	}

}
