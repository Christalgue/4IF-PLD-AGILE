package main.java.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;

public class GraphicView extends JPanel {

	private double heightScale;
	private double widthScale;

	protected double originLat;
	protected double originLong;

	private int viewHeight;
	private int viewWidth;

	private CircuitManagement circuitManagement;
	private Graphics2D g;

	private MapView mapView;
	private CircuitView circuitView;
	private DeliveryView deliveryView;

	private int width;
	private int deliveryRadius;

	private static Color nodeColor = Color.WHITE;

	//private Color color[] = { Color.CYAN, Color.BLUE, Color.GRAY, Color.ORANGE, Color.PINK };
	
	protected double minLat;
	protected double maxLong;
	
	private int horizontalOffset =0;
	private int verticalOffset =0;
	private Window window;

	/**
	 * Create the graphic view where the map will be drawn in Window windows
	 * 
	 * @param circuitManagement the CircuitManagement
	 */
	public GraphicView(CircuitManagement circuitManagement, int viewHeight, int viewWidth, int width, 
			Window window ) {

		super();

		this.viewHeight = viewHeight-50;
		this.viewWidth = viewWidth-20;
		this.width = width;
		this.deliveryRadius = 3*width;
		
		this.circuitManagement = circuitManagement;
		this.window = window;

		mapView = new MapView(nodeColor, width, this);
		circuitView = new CircuitView(this, width);
		deliveryView = new DeliveryView(window.deliveryColor, window.repositoryColor, deliveryRadius, this);

	}

	protected void calculateScale(CircuitManagement circuitManagement) {

		HashMap<Long, Node> nodeMap = circuitManagement.getCurrentMap().getNodeMap();

		double minLat = 0;
		double maxLat = 0;
		double minLong = 0;
		double maxLong = 0;

		boolean firstNode = true;

		double currentLat;
		double currentLong;

		for (Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
			Node node = entry.getValue();

			if (firstNode) {
				minLat = node.getLatitude();
				maxLat = node.getLatitude();
				minLong = node.getLongitude();
				maxLong = node.getLongitude();
				firstNode = false;
			}

			currentLat = node.getLatitude();
			currentLong = node.getLongitude();

			if (currentLat > maxLat)
				maxLat = currentLat;
			if (currentLat < minLat)
				minLat = currentLat;

			if (currentLong > maxLong)
				maxLong = currentLong;
			if (currentLong < minLong)
				minLong = currentLong;

		}

		heightScale = (double) ((maxLat - minLat) / (double) viewHeight);
		widthScale = (double) ((maxLong - minLong) / (double) viewWidth);

		originLat = maxLat;
		originLong = minLong;
		this.minLat = minLat;
		this.maxLong = maxLong;

		PointUtil.range = 5.0 * Math.min(heightScale, widthScale);
		
		horizontalOffset = 0;
		verticalOffset =0;
	}

	public Node pointToNode(Point point) {
		return PointUtil.pointToNode(point, circuitManagement);
	}

	public Point nodeToPoint(Node node) {

		Point p = new Point(((node.getLongitude() - originLong) / widthScale)+10 - horizontalOffset,
				((originLat - node.getLatitude()) / heightScale)+10 -verticalOffset);
		return p;

	}

	public Point pointToLatLong(Point point) {
		Point p = new Point((point.getX()-10+horizontalOffset) * widthScale + originLong, -(point.getY()-10+verticalOffset) * heightScale + originLat);
		return p;

	}

	/**
	 * Methode appelee a chaque fois que VueGraphique doit etre redessinee
	 */
	public void paintComponent() {

		paintMap();

		paintCircuits();

		paintDeliveries();

	}

	
	public void paintMap() {
		this.removeAll();
		this.update(g);
		mapView.paintMap(g, circuitManagement.getCurrentMap());
	}

	public void paintDeliveries() {
		deliveryView.paintDeliveries(g, circuitManagement.getDeliveryList());
	}

	public void paintCircuits() {

		int circuitIndex = 0;
		if(circuitManagement.getCircuitsList()!=null) {
			for (Circuit entry : circuitManagement.getCircuitsList()) {
				Color circuitColor = window.colors.get(circuitIndex);
				
				if ( circuitColor  != null) {
					//circuitView.paintCircuit(g, entry, color[colorIndex % color.length]);
					circuitView.paintCircuit(g, entry, circuitColor);
				} else {
					circuitColor = new Color ((int)(Math.random()*200),(int)(Math.random()*200), (int)(Math.random()*200));
					window.colors.put(circuitIndex, circuitColor);
					circuitView.paintCircuit(g, entry, circuitColor);
				}
				circuitIndex++;
	
			}
		}
		paintDeliveries();
	}

	protected void setGraphics() {
		g = (Graphics2D) this.getGraphics();
	}

	@Override
	public String toString() {
		return "GraphicView [heightScale=" + heightScale + ", widthScale=" + widthScale + ", originLat=" + originLat
				+ ", originLong=" + originLong + ", viewHeight=" + viewHeight + ", viewWidth=" + viewWidth
				+ ", circuitManagement=" + circuitManagement + ", mapView=" + mapView + ", circuitView=" + circuitView
				+ ", deliveryView=" + deliveryView + ", color=" +  "]";
	}

	public void paintSelectedNode(Delivery delivery, boolean clicked) {

		if (clicked) {
			g.setColor(window.selectedColor);
		} else {
			g.setColor(window.hoverColor);
		}
		
		if (delivery.getDuration() == -1) {
			mapView.drawNode(g, delivery.getPosition());
		} else {
			deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery), deliveryRadius );
		}
	}

	public void unPaintNode ( Delivery delivery) {
		
		if (delivery.getDuration() == -1) {
			g.setColor(nodeColor);
			mapView.drawNode(g, delivery.getPosition());
		} else {
			g.setColor(window.deliveryColor);
			deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery), deliveryRadius);

		}
	}


	public void paintSelectedCircuit(int circuitIndex) {
		circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuitIndex), window.selectedColor);
	}

	public void unPaintCircuit(int selectedCircuit) {

		g.setColor(window.deliveryColor);
		circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(selectedCircuit), window.colors.get(selectedCircuit));

	}

	public void paintSelectedCircuit(int circuit, boolean clicked) {
		
		if (clicked) {
			circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuit),window.selectedColor);
		} else {
			circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuit),window.hoverColor);
		}
		
	}

	public void zoom() {
		heightScale = heightScale/2;
		widthScale = widthScale/2;
		paintComponent();

	}
	
	public void unZoom (){
		heightScale = heightScale*2;
		widthScale = widthScale*2;
		paintComponent();
		
	}

	public void horizontalShift(int right) {
		horizontalOffset +=right;
		paintComponent();
	}

	public void verticalShift(int down) {
		verticalOffset +=down;
		paintComponent();
	}
	
	public void shift(int right, int down) {
		horizontalOffset +=right;
		verticalOffset +=down;
		paintComponent();
	}

	
}
