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

	private static Color nodeColor = Color.WHITE;
	private static Color selectedColor = Color.GREEN;
	private static Color hoverColor = Color.BLUE;
	private static Color deliveryColor = Color.RED;
	private static Color repositoryColor = Color.ORANGE;

	private Color color[] = { Color.CYAN, Color.BLUE, Color.GRAY, Color.ORANGE, Color.PINK };
	
	protected double minLat;
	protected double maxLong;

	/**
	 * Create the graphic view where the map will be drawn in Window windows
	 * 
	 * @param circuitManagement the CircuitManagement
	 */
	public GraphicView(CircuitManagement circuitManagement, int viewHeight, int viewWidth, int width) {

		super();

		this.viewHeight = viewHeight-50;
		this.viewWidth = viewWidth-20;
		this.width = width;

		this.circuitManagement = circuitManagement;

		mapView = new MapView(nodeColor, width, this);
		circuitView = new CircuitView(this, width);
		deliveryView = new DeliveryView(deliveryColor, repositoryColor, width, this);

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
	}

	public Node pointToNode(Point point) {
		return PointUtil.pointToNode(point, circuitManagement);
	}

	public Point nodeToPoint(Node node) {

		Point p = new Point(((node.getLongitude() - originLong) / widthScale)+10,
				((originLat - node.getLatitude()) / heightScale)+10);
		return p;

	}

	public Point pointToLatLong(Point point) {
		Point p = new Point((point.getX()-10) * widthScale + originLong, -(point.getY()-10) * heightScale + originLat);
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
		calculateScale(circuitManagement);
		mapView.paintMap(g, circuitManagement.getCurrentMap());
	}

	public void paintDeliveries() {
		deliveryView.paintDeliveries(g, circuitManagement.getDeliveryList());
	}

	public void paintCircuits() {

		int colorIndex = 0;
		if(circuitManagement.getCircuitsList()!=null) {
			for (Circuit entry : circuitManagement.getCircuitsList()) {
	
				circuitView.paintCircuit(g, entry, color[colorIndex % color.length]);
				colorIndex++;
	
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
				+ ", deliveryView=" + deliveryView + ", color=" + Arrays.toString(color) + "]";
	}

	public void paintSelectedNode(Delivery delivery, boolean clicked) {

		if (clicked) {
			g.setColor(selectedColor);
		} else {
			g.setColor(hoverColor);
		}
		
		if (delivery.getDuration() == -1) {
			mapView.drawNode(g, delivery.getPosition());
		} else {
			deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery));
		}
	}

	public void unPaintNode ( Delivery delivery) {
		
		if (delivery.getDuration() == -1) {
			g.setColor(nodeColor);
			mapView.drawNode(g, delivery.getPosition());
		} else {
			g.setColor(deliveryColor);
			deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery));

		}
	}

	public void paintSelectedCircuit(Circuit circuit) {
		circuitView.paintCircuit(g, circuit, selectedColor);
	}

	public void paintSelectedCircuit(int circuitIndex) {
		circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuitIndex), selectedColor);
	}

	public void unPaintCircuit(Circuit selectedCircuit) {

		g.setColor(deliveryColor);
		circuitView.paintCircuit(g, selectedCircuit, Color.RED);

	}

	public void paintSelectedCircuit(Circuit circuit, boolean clicked) {
		
		if (clicked) {
			circuitView.paintCircuit(g, circuit,selectedColor);
		} else {
			circuitView.paintCircuit(g, circuit,hoverColor);
		}
		
	}

}
