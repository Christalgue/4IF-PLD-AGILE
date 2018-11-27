package main.java.view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;


// https://examples.javacodegeeks.com/desktop-java/ide/eclipse/eclipse-windowbuilder-tutorial/

public class Window extends JFrame{
	
	private Controller controller;
	private CircuitManagement circuitManagement;

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
		final int graphicWidth = 900;
		
		CircuitManagement aCircuitManagement = new CircuitManagement();
		Controller aController = new Controller(aCircuitManagement);
		
		Window window = new Window(aCircuitManagement, aController);
		
		try {
			window.circuitManagement.loadMap("resources/xml/petitPlan.xml");
		} catch (LoadMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			window.circuitManagement.loadDeliveryList("resources/xml/dl-petit-3.xml");
		} catch (LoadDeliveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFrame windowTest = new JFrame();
		windowTest.setVisible(true);
		windowTest.setLayout(null);
		
		windowTest.setSize(windowWidth, windowHeight);
		windowTest.setDefaultCloseOperation(EXIT_ON_CLOSE);
		windowTest.setTitle("Deliver'IF");
		windowTest.setResizable(false);
		windowTest.setLocationRelativeTo(null);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setSize(windowWidth, buttonPanelHeight);
		buttonPanel.setBackground(Color.WHITE);
		
		GraphicView graphicView = new GraphicView (window.circuitManagement, windowHeight-buttonPanelHeight, graphicWidth);
		graphicView.setLocation(0, buttonPanelHeight);
		graphicView.setLayout(null);
		graphicView.setBackground(Color.LIGHT_GRAY);
		graphicView.setSize(graphicWidth, windowHeight-buttonPanelHeight);
		
		JPanel textualPanel = new JPanel();
		textualPanel.setSize(windowWidth-graphicWidth, windowHeight-buttonPanelHeight);
		textualPanel.setLocation(graphicWidth, buttonPanelHeight);
		textualPanel.setBackground(Color.GREEN);
		
		windowTest.getContentPane().add(buttonPanel);
		windowTest.getContentPane().add(textualPanel);
		windowTest.getContentPane().add(graphicView);
		
		graphicView.setGraphics();
		graphicView.paintComponent();
		
		
	}
}
