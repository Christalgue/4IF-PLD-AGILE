package main.java.view;

import java.awt.Color;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;


// https://examples.javacodegeeks.com/desktop-java/ide/eclipse/eclipse-windowbuilder-tutorial/

public class Window extends JFrame{
	
	private Controller controller;
	private CircuitManagement circuitManagement;
	private GraphicView graphicView;
	
	protected final static String LOAD_MAP = "Charger un plan";
	protected static final String LOAD_DELIVERY_OFFER = "Charger une demande de livraison";
	protected static final String CALCULATE_CIRCUITS = "Calculer les tournees";
	
	protected static TextField setNameOfMap;
	protected static TextField setNameOfDeliveryList;
	protected static TextField numberOfDeliveryMen;
	
	protected static ButtonsListener buttonsListener;

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
		
		final int windowWidth = 1280;
		final int windowHeight = 720;
		final int buttonPanelHeight = 50;
		final int graphicWidth = 1080;
		
		//CircuitManagement aCircuitManagement = new CircuitManagement();
		Controller controller = new Controller(new CircuitManagement());
		
		//Window window = new Window(aCircuitManagement, aController);
		
		/*try {
			window.circuitManagement.loadMap("resources/xml/grandPlan.xml");
		} catch (LoadMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			window.circuitManagement.loadDeliveryList("resources/xml/dl-grand-12.xml");
		} catch (LoadDeliveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			window.circuitManagement.calculateCircuits(3);
		} catch (MapNotChargedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DeliveryListNotCharged e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClusteringException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DijkstraException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoRepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		//////////////////////////////CREATION OF THE MAIN WINDOW//////////////////////////////
		
		
		controller.getWindow().setLayout(null);
		
		controller.getWindow().setSize(windowWidth, windowHeight);
		controller.getWindow().setDefaultCloseOperation(EXIT_ON_CLOSE);
		controller.getWindow().setTitle("Deliver'IF");
		controller.getWindow().setResizable(false);
		controller.getWindow().setLocationRelativeTo(null);
		
		
		buttonsListener = new ButtonsListener(controller.getWindow().controller);
		
		
		//////////////////////////////CREATE THE HEADER PANEL/////////////////////////////
		JPanel buttonPanel = new JPanel();
		buttonPanel.setSize(windowWidth, buttonPanelHeight);
		buttonPanel.setBackground(Color.WHITE);
		
		setNameOfMap = new TextField();
		setNameOfMap.setText("resources/xml/petitPlan.xml");
		buttonPanel.add(setNameOfMap);
		
		JButton loadMapButton = new JButton(LOAD_MAP);
		loadMapButton.addActionListener(buttonsListener);
		buttonPanel.add(loadMapButton);
		/*loadMapButton.setLocation(10, 10);
		loadMapButton.setSize(100, 30);*/
		
		setNameOfDeliveryList = new TextField();
		setNameOfDeliveryList.setText("resources/xml/dl-petit-3.xml");
		buttonPanel.add(setNameOfDeliveryList);
		
		JButton loadDeliveryList = new JButton(LOAD_DELIVERY_OFFER);
		loadDeliveryList.addActionListener(buttonsListener);
		buttonPanel.add(loadDeliveryList);
		
		JTextArea labelNumberOfDeliveryMen = new JTextArea();
		labelNumberOfDeliveryMen.setText("Nombre de livreurs :");
		labelNumberOfDeliveryMen.setEditable(false);
		buttonPanel.add(labelNumberOfDeliveryMen);
		
		numberOfDeliveryMen = new TextField();
		numberOfDeliveryMen.setText("2");
		buttonPanel.add(numberOfDeliveryMen);
		
		/*JButton undoButton = new JButton("Retour");
		buttonPanel.add(undoButton);
		undoButton.addActionListener(buttonsListener);*/
		
		JButton calculateCircuitButton = new JButton(CALCULATE_CIRCUITS);
		calculateCircuitButton.addActionListener(buttonsListener);
		buttonPanel.add(calculateCircuitButton);
		
		//////////////////////////////CREATE THE GRAPHIC VIEW//////////////////////////////
		controller.getWindow().graphicView = new GraphicView (controller.getWindow().circuitManagement, windowHeight-buttonPanelHeight, graphicWidth);
		controller.getWindow().graphicView.setLocation(0, buttonPanelHeight);
		controller.getWindow().graphicView.setLayout(null);
		controller.getWindow().graphicView.setBackground(Color.LIGHT_GRAY);
		controller.getWindow().graphicView.setSize(graphicWidth, windowHeight-buttonPanelHeight);
		
		
		//////////////////////////////CREATE THE CIRCUIT///////////////////////////////////
		//CircuitView circuitView = new CircuitView (graphicView, 5);
		
		//////////////////////////////CREATE THE TEXTUAL PANEL/////////////////////////////
		JPanel textualPanel = new JPanel();
		textualPanel.setSize(windowWidth-graphicWidth, windowHeight-buttonPanelHeight);
		textualPanel.setLocation(graphicWidth, buttonPanelHeight);
		textualPanel.setBackground(Color.GREEN);
		
		//////////////////////////////ADD PANELS TO THE WINDOW//////////////////////////////
		controller.getWindow().getContentPane().add(buttonPanel);
		controller.getWindow().getContentPane().add(textualPanel);
		controller.getWindow().getContentPane().add(controller.getWindow().graphicView);
		
		//////////////////////////////PAINT THE GRAPHIC VIEW////////////////////////////////
		controller.getWindow().setVisible(true);
		
		controller.getWindow().graphicView.setGraphics();
		
	}
	
	public static void getMapName() {
		
		buttonsListener.setMap(setNameOfMap.getText());
		
	}
	
	public static void getDeliveryListName() {
		
		buttonsListener.setDeliveryList(setNameOfDeliveryList.getText());
		
	}
	
	public static void getDeliveryMenNumber() {
		
		buttonsListener.setDeliveryMenNumber(Integer.parseInt(numberOfDeliveryMen.getText()));
		
	}
	
	public void drawMap() {
		//System.out.println(graphicView);
		graphicView.paintComponentMap();
	}
	
	public void drawDeliveries() {
		graphicView.paintComponentDeliveries();
	}
	
	public void drawCircuits() {
		graphicView.paintComponentCircuits();
	}
	
}
