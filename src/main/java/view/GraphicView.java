package main.java.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.entity.Repository;
import main.java.utils.PointUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class GraphicView.
 */
public class GraphicView extends JPanel {

	/** The height scale. */
	private double heightScale;
	
	/** The width scale. */
	private double widthScale;
	
	/** The default width scale. */
	private double defaultWidthScale;
	
	/** The default height scale. */
	private double defaultHeightScale;

	/** The origin lat. */
	protected double originLat;
	
	/** The origin long. */
	protected double originLong;

	/** The view height. */
	private int viewHeight;
	
	/** The view width. */
	private int viewWidth;

	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The g. */
	private Graphics2D g;

	/** The map view. */
	private MapView mapView;
	
	/** The circuit view. */
	private CircuitView circuitView;
	
	/** The delivery view. */
	private DeliveryView deliveryView;

	/** The width. */
	private int width;
	
	/** The delivery radius. */
	private int deliveryRadius;
	
	/** The repository radius. */
	private int repositoryRadius;

	/** The node color. */
	private static Color nodeColor = Color.WHITE;

	//private Color color[] = { Color.CYAN, Color.BLUE, Color.GRAY, Color.ORANGE, Color.PINK };
	
	/** The min lat. */
	protected double minLat;
	
	/** The max long. */
	protected double maxLong;
	
	/** The horizontal offset. */
	private int horizontalOffset =0;
	
	/** The vertical offset. */
	private int verticalOffset =0;
	
	/** The window. */
	private Window window;

	/**
	 * Create the graphic view where the map will be drawn in Window windows.
	 *
	 * @param circuitManagement the CircuitManagement
	 * @param viewHeight the view height
	 * @param viewWidth the view width
	 * @param width the width
	 * @param window the window
	 */
	public GraphicView(CircuitManagement circuitManagement, int viewHeight, int viewWidth, int width, 
			Window window ) {

		super();

		this.viewHeight = viewHeight-50;
		this.viewWidth = viewWidth-20;
		this.width = width;
		this.deliveryRadius = 3*width;
		this.repositoryRadius = 6*width;
		
		this.circuitManagement = circuitManagement;
		this.window = window;

		mapView = new MapView(nodeColor, width, this);
		circuitView = new CircuitView(this, width);
		deliveryView = new DeliveryView(window.deliveryColor, window.repositoryColor, deliveryRadius, repositoryRadius, this);

	}

	/**
	 * Calculate scale.
	 *
	 * @param circuitManagement the circuit management
	 */
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
		
		this.defaultHeightScale = heightScale;
		this.defaultWidthScale = widthScale;

		originLat = maxLat;
		originLong = minLong;
		this.minLat = minLat;
		this.maxLong = maxLong;

		PointUtil.range = 5.0 * Math.min(heightScale, widthScale);
		
		horizontalOffset = 0;
		verticalOffset =0;
	}

	/**
	 * Point to node.
	 *
	 * @param point the point
	 * @return the node
	 */
	public Node pointToNode(Point point) {
		return PointUtil.pointToNode(point, circuitManagement);
	}

	/**
	 * Node to point.
	 *
	 * @param node the node
	 * @return the point
	 */
	public Point nodeToPoint(Node node) {

		Point p = new Point(((node.getLongitude() - originLong) / widthScale)+10 - horizontalOffset,
				((originLat - node.getLatitude()) / heightScale)+10 -verticalOffset);
		return p;

	}

	/**
	 * Point to lat long.
	 *
	 * @param point the point
	 * @return the point
	 */
	public Point pointToLatLong(Point point) {
		Point p = new Point((point.getX()-10+horizontalOffset) * widthScale + originLong, -(point.getY()-10+verticalOffset) * heightScale + originLat);
		return p;

	}

	/**
	 * Methode appelee a chaque fois que VueGraphique doit etre redessinee.
	 */
	public void paintComponent() {

		paintMap();

		paintCircuits();

		paintDeliveries();

	}

	
	/**
	 * Paint map.
	 */
	public void paintMap() {
		this.removeAll();
		this.update(g);
		mapView.paintMap(g, circuitManagement.getCurrentMap());
	}

	/**
	 * Paint deliveries.
	 */
	public void paintDeliveries() {
		deliveryView.paintDeliveries(g, circuitManagement.getDeliveryList());
	}

	/**
	 * Paint circuits.
	 */
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

	/**
	 * Sets the graphics.
	 */
	protected void setGraphics() {
		g = (Graphics2D) this.getGraphics();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	@Override
	public String toString() {
		return "GraphicView [heightScale=" + heightScale + ", widthScale=" + widthScale + ", originLat=" + originLat
				+ ", originLong=" + originLong + ", viewHeight=" + viewHeight + ", viewWidth=" + viewWidth
				+ ", circuitManagement=" + circuitManagement + ", mapView=" + mapView + ", circuitView=" + circuitView
				+ ", deliveryView=" + deliveryView + ", color=" +  "]";
	}

	/**
	 * Paint selected node.
	 *
	 * @param delivery the delivery
	 * @param clicked the clicked
	 */
	public void paintSelectedNode(Delivery delivery, boolean clicked) {

		if (clicked) {
			g.setColor(window.selectedColor);
		} else {
			g.setColor(window.hoverColor);
		}
		
		if (delivery.getDuration() == -1) {
			mapView.drawNode(g, delivery.getPosition());
		} else {
			
			if ( delivery instanceof Repository ) {
				deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery), repositoryRadius );				
			} else {	
				deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery), deliveryRadius );
			}
		}
	}

	/**
	 * Un paint node.
	 *
	 * @param delivery the delivery
	 */
	public void unPaintNode ( Delivery delivery) {
		
		if (delivery.getDuration() == -1) {
			g.setColor(nodeColor);
			mapView.drawNode(g, delivery.getPosition());
		} else {
			
			if ( delivery instanceof Repository ) {
				g.setColor(window.repositoryColor);;
				deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery), repositoryRadius );				
			} else {
				g.setColor(window.deliveryColor);
				deliveryView.drawDelivery(g, delivery.getPosition(), circuitManagement.getDeliveryIndex(delivery), deliveryRadius);
			}
		}
	}


	/**
	 * Paint selected circuit.
	 *
	 * @param circuitIndex the circuit index
	 */
	public void paintSelectedCircuit(int circuitIndex) {
		circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuitIndex), window.selectedColor);
	}

	/**
	 * Un paint circuit.
	 *
	 * @param selectedCircuit the selected circuit
	 */
	public void unPaintCircuit(int selectedCircuit) {

		g.setColor(window.deliveryColor);
		circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(selectedCircuit), window.colors.get(selectedCircuit));

	}

	/**
	 * Paint selected circuit.
	 *
	 * @param circuit the circuit
	 * @param clicked the clicked
	 */
	public void paintSelectedCircuit(int circuit, boolean clicked) {
		
		if (clicked) {
			circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuit),window.selectedColor);
		} else {
			circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuit),window.hoverColor);
		}
		
	}

	/**
	 * Zoom.
	 */
	public void zoom() {
		heightScale = heightScale/2;
		widthScale = widthScale/2;
		paintComponent();

	}
	
	/**
	 * Un zoom.
	 */
	public void unZoom (){
		heightScale = heightScale*2;
		widthScale = widthScale*2;
		paintComponent();
		
	}

	/**
	 * Horizontal shift.
	 *
	 * @param right the right
	 */
	public void horizontalShift(int right) {
		horizontalOffset +=right;
		paintComponent();
	}

	/**
	 * Vertical shift.
	 *
	 * @param down the down
	 */
	public void verticalShift(int down) {
		verticalOffset +=down;
		paintComponent();
	}
	
	/**
	 * Shift.
	 *
	 * @param right the right
	 * @param down the down
	 */
	public void shift(int right, int down) {
		horizontalOffset +=right;
		verticalOffset +=down;
		paintComponent();
	}
	
	/**
	 * Reset default values.
	 */
	public void resetDefaultValues() {
		heightScale = defaultHeightScale;
		widthScale = defaultWidthScale;
		horizontalOffset = 0;
		verticalOffset = 0;
	}
}
