package main.java.view;

import java.awt.Color;
import java.awt.TextField;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;
import main.java.entity.Node;


public class Window extends JFrame{
	
	private Controller controller;
	private CircuitManagement circuitManagement;
	private GraphicView graphicView;
	protected static final String LOAD_MAP = "Charger un plan";
	protected static final String LOAD_DELIVERY_OFFER = "Charger une demande de livraison";
	protected static final String CALCULATE_CIRCUITS = "Calculer les tournees";
	protected static final String ADD_DELIVERY = "Ajouter une livraison";
	protected static final String CONTINUE_CALCULATION = "Continuer le calcul des chemins";
	protected static final String STOP_CALCULATION = "Arr�ter le calcul des chemins";
	
	protected static TextField setNameOfMap;
	protected static TextField setNameOfDeliveryList;
	protected static TextField numberOfDeliveryMen;
	
	protected static JButton loadDeliveryList;
	protected static JButton calculateCircuitButton;
	protected static JButton addDeliveryButton;
	
	private static JFileChooser chooser;
	
	protected static ButtonsListener buttonsListener;
	protected static MouseListener mouseListener;
	
	protected static final int windowWidth = 1280;
	protected static final int windowHeight = 720;
	protected static final int buttonPanelHeight =50;
	protected static final int graphicWidth = 1080;
	
	protected static final int pathWidth = 3;
	
	protected static final Color selectedColor = Color.GREEN;

	/**
	 * Default constructor
	 */
	public Window () {
		
	}
	
	/**
	 * Create the application.
	 */
	public Window (CircuitManagement circuitManagement, Controller controller){
		this.circuitManagement = circuitManagement;
		this.controller = controller;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		Controller controller = new Controller(new CircuitManagement());
		buttonsListener = new ButtonsListener(controller);
		
		//////////////////////////////CREATION OF THE MAIN WINDOW//////////////////////////////
		
		setControllerWindow(controller.getWindow());	
		
		//////////////////////////////CREATE THE HEADER PANEL/////////////////////////////
		JPanel buttonPanel = new JPanel();
		fillButtonPanel(buttonPanel);
		
		//////////////////////////////CREATE THE GRAPHIC VIEW//////////////////////////////
		
		controller.getWindow().graphicView = new GraphicView (controller.getWindow().circuitManagement, windowHeight-buttonPanelHeight, graphicWidth, pathWidth);
		setGraphicView(controller.getWindow().graphicView);
		mouseListener = new MouseListener(controller, controller.getWindow().graphicView,controller.getWindow());
		controller.getWindow().addMouseListener(mouseListener);
		
		//////////////////////////////CREATE THE TEXTUAL PANEL/////////////////////////////
		JPanel textualPanel = new JPanel();
		fillTextualPanel(textualPanel);
		
		//////////////////////////////ADD PANELS TO THE WINDOW//////////////////////////////
		controller.getWindow().getContentPane().add(buttonPanel);
		controller.getWindow().getContentPane().add(textualPanel);
		controller.getWindow().getContentPane().add(controller.getWindow().graphicView);
		
		////////////////////////////// MAKE THE FRAME VISBLE AND PAINTABLE ////////////////////////////////
		controller.getWindow().setVisible(true);
		
		controller.getWindow().graphicView.setGraphics();
		
	}
	
	public static void getDeliveryMenNumber() {
		
		buttonsListener.setDeliveryMenNumber(Integer.parseInt(numberOfDeliveryMen.getText()));
		
	}
	
	public void drawMap() {
		graphicView.removeAll();
		graphicView.update(graphicView.getGraphic());
		graphicView.paintMap(graphicView.getGraphic());
	}
	
	public void drawDeliveries() {
		drawMap();
		graphicView.paintDeliveries(graphicView.getGraphic());
	}
	
	public void drawCircuits() {
		drawDeliveries();
		graphicView.paintCircuits(graphicView.getGraphic());
	}
	
	public void nodeSelected( Node node) {
		graphicView.paintSelectedNode(graphicView.getGraphic(), node, selectedColor);
	}
	
	public static void setControllerWindow( Window window) {
		
		window.setLayout(null);
		window.setSize(windowWidth, windowHeight);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setTitle("Deliver'IF");
		window.setResizable(false);
		window.setLocationRelativeTo(null);
	
	}
	
	private static void setGraphicView(GraphicView graphicView) {
		
		graphicView.setLocation(0, buttonPanelHeight);
		graphicView.setLayout(null);
		graphicView.setBackground(Color.LIGHT_GRAY);
		graphicView.setSize(graphicWidth, windowHeight-buttonPanelHeight);
		
	}
	
	public static void fillButtonPanel(JPanel buttonPanel) {
		
		buttonPanel.setSize(windowWidth, buttonPanelHeight);
		buttonPanel.setBackground(Color.WHITE);
		
		JButton loadMapButton = new JButton(LOAD_MAP);
		loadMapButton.addActionListener(buttonsListener);
		buttonPanel.add(loadMapButton);
		
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
		buttonPanel.add(numberOfDeliveryMen);
	
		/*JButton undoButton = new JButton("Retour");
		buttonPanel.add(undoButton);
		undoButton.addActionListener(buttonsListener);*/
		
		calculateCircuitButton = new JButton(CALCULATE_CIRCUITS);
		calculateCircuitButton.addActionListener(buttonsListener);
		calculateCircuitButton.setEnabled(false);
		buttonPanel.add(calculateCircuitButton);
	}
	
	private static void fillTextualPanel(JPanel textualPanel) {
		textualPanel.setSize(windowWidth-graphicWidth, windowHeight-buttonPanelHeight);
		textualPanel.setLocation(graphicWidth, buttonPanelHeight);
		textualPanel.setBackground(Color.GREEN);
		
		addDeliveryButton = new JButton(ADD_DELIVERY);
		addDeliveryButton.addActionListener(buttonsListener);
		addDeliveryButton.setVisible(false);
		textualPanel.add(addDeliveryButton);
	}
	
	protected String getFile () {
			
		chooser = new JFileChooser();
		int returnValue = chooser.showOpenDialog(controller.getWindow());
		String filePath = "";
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			filePath = chooser.getSelectedFile().getAbsolutePath();
		}
		return filePath;
	}

}
