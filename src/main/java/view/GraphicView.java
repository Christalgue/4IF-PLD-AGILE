package main.java.view;

import java.util.Map;
import java.util.Observable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observer;
import java.util.Set;

import javax.swing.JPanel;

import com.sun.corba.se.spi.activation.Repository;

import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;

public class GraphicView extends JPanel {

	private double heightScale;
	private double widthScale;

	private double originLat;
	private double originLong;

	private int viewHeight;
	private int viewWidth;

	private main.java.entity.CircuitManagement circuitManagement;
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

	/**
	 * Create the graphic view where the map will be drawn in Window windows
	 * 
	 * @param circuitManagement the CircuitManagement
	 */
	public GraphicView(main.java.entity.CircuitManagement circuitManagement, int viewHeight, int viewWidth, int width) {

		super();

		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
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

		System.out.println("heightScale " + heightScale);
		System.out.println("widthScale " + widthScale);
		System.out.println("originLat " + originLat);
		System.out.println("originLong " + originLong);
		System.out.println("minLat " + minLat);
		System.out.println("maxLong " + maxLong);

		PointUtil.range = 5.0 * Math.min(heightScale, widthScale);
	}

	public Node pointToNode(Point point) {
		return PointUtil.pointToNode(point, circuitManagement);
	}

	public Point nodeToPoint(Node node) {

		Point p = new Point((node.getLongitude() - originLong) / widthScale,
				(originLat - node.getLatitude()) / heightScale);
		return p;

	}

	public Point pointToLatLong(Point point) {
		Point p = new Point(point.getX() * widthScale + originLong, -(point.getY()) * heightScale + originLat);
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
		System.out.println(widthScale);
		mapView.paintMap(g, circuitManagement.getCurrentMap());
	}

	public void paintDeliveries() {
		deliveryView.paintDeliveries(g, circuitManagement.getDeliveryList());
	}

	public void paintCircuits() {

		int colorIndex = 0;

		for (Circuit entry : circuitManagement.getCircuitsList()) {

			circuitView.paintCircuit(g, entry, color[colorIndex % color.length]);
			colorIndex++;

		}

		paintDeliveries();
	}

	/*
	 * protected Graphics2D getGraphic() { return g; }
	 */

	/*
	 * protected void setGraphics() { System.out.println("setGraphics : " +
	 * this.getGraphics()); g = (Graphics2D) this.getGraphics();
	 * System.out.println(g); }
	 */

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
		mapView.drawNode(g, delivery.getPosition());
	}

	public void unPaintNode ( Delivery delivery) {
		
		if (delivery.getDuration() == -1) {
			g.setColor(nodeColor);
		} else {
			g.setColor(deliveryColor);
		}
		
		mapView.drawNode(g, delivery.getPosition());
	}

	public void paintSelectedCircuit(Circuit circuit) {
		circuitView.paintCircuit(g, circuit, selectedColor);
	}

}
