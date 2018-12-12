package main.java.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.entity.Repository;
import main.java.utils.PointUtil;

/**
 * The Class GraphicView is a panel that graphically display the map, the deliveries and the circuits.
 */
public class GraphicView extends JPanel {
	
	/** The window. */
	private Window window;
	
	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The map view. */
	private MapView mapView;
	
	/** The circuit view. */
	private CircuitView circuitView;
	
	/** The delivery view. */
	private DeliveryView deliveryView;	
	

	/** The graphics. */
	private Graphics2D g;

	/** The path width. */
	private int width;
	
	/** The delivery radius. */
	private int deliveryRadius;
	
	/** The repository radius. */
	private int repositoryRadius;

	/** The basic node color. */
	private static Color nodeColor = Color.WHITE;

	
	/** The current height scale. */
	private double heightScale;
	
	/** The current width scale. */
	private double widthScale;
	
	/** The default width scale. */
	private double defaultWidthScale;
	
	/** The default height scale. */
	private double defaultHeightScale;

	
	/** The horizontal offset, zero means that the map is horizontally at the center of the view. */
	private int horizontalOffset =0;
	
	/** The vertical offset, zero means that the map is vertically at the center of the view. */
	private int verticalOffset =0;
	
	
	/** The latitude of the view's origin point, or maximum latitude of a map's point. */
	protected double originLat;
	
	/** The longitude of the view's origin point, or minimum longitude of a map's point. */
	protected double originLong;
	
	/** The minimum latitude of a map's node. */
	protected double minLat;
	
	/** The maximum longitude of a map's node. */
	protected double maxLong;

	
	/** The view height. */
	private int viewHeight;
	
	/** The view width. */
	private int viewWidth;
	
	/**
	 * Create the graphic view where the graphic components will be drawn in Window window.
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
		
		// Set a margin to display by default the whole map in the view
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
	 * Calculate default scales based on extreme coordinates of map's nodes. 
	 * Set the offsets to their default value, zero.
	 * With theses default values, the map is centered and entirely displayed in the view.
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

		// For each node in the circuitManagement's map, we look if 
		// their coordinates are the map's extreme one 
		for (Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
			
			Node node = entry.getValue();
			
			// Initialize extreme coordinates with the first encountered node
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
		
		// Default scales are calculated based on extreme coordinates difference
		// and view dimensions
		heightScale = (double) ((maxLat - minLat) / (double) viewHeight);
		widthScale = (double) ((maxLong - minLong) / (double) viewWidth);
		
		this.defaultHeightScale = heightScale;
		this.defaultWidthScale = widthScale;

		originLat = maxLat;
		originLong = minLong;
		this.minLat = minLat;
		this.maxLong = maxLong;

		// The range (tolerance on a node selection) is set depending on the default scales 
		PointUtil.range = 5.0 * Math.min(heightScale, widthScale);
		
		horizontalOffset = 0;
		verticalOffset =0;
	}

	/**
	 * Get the node corresponding to a point which coordinates 
	 * are expressed in latitude and longitude.
	 *
	 * @param point the point
	 * @return the node
	 */
	public Node pointToNode(Point point) {
		return PointUtil.pointToNode(point, circuitManagement);
	}

	/**
	 * Convert a node to a point which coordinates are expressed in pixels
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
	 * Convert a point's coordinates from pixels to latitude and longitude
	 *
	 * @param point the point
	 * @return the point
	 */
	public Point pointToLatLong(Point point) {
		
		Point p = new Point((point.getX()-10+horizontalOffset) * widthScale + originLong, -(point.getY()-10+verticalOffset) * heightScale + originLat);
		return p;

	}

	/**
	 * Paint every graphic component of the view
	 */
	public void paintComponent() {

		paintMap();

		paintCircuits();

		paintDeliveries();

	}
	
	/**
	 * Empty the view, then paint the map in it.
	 */
	public void paintMap() {
		this.removeAll();
		this.update(g);
		mapView.paintMap(g, circuitManagement.getCurrentMap());
	}

	/**
	 * Paint the deliveries in the view.
	 */
	public void paintDeliveries() {
		deliveryView.paintDeliveries(g, circuitManagement.getDeliveryList());
	}

	/**
	 * Paint each circuit in a different color in the view.
	 */
	public void paintCircuits() {

		int circuitIndex = 0;
		
		// If the circuitManagement's circuitsList isn't empty
		if(circuitManagement.getCircuitsList()!=null) {
			
			// For each circuit in it
			for (Circuit entry : circuitManagement.getCircuitsList()) {
				
				Color circuitColor = window.colors.get(circuitIndex);
				
				// If it already has a defined color, paint it
				if ( circuitColor  != null) {
					circuitView.paintCircuit(g, entry, circuitColor);
				
				// Else, create a random color, put it in window's color HashMap,
				// then paint the circuit in it
				} else {
					circuitColor = new Color ((int)(Math.random()*200),(int)(Math.random()*200), (int)(Math.random()*200));
					window.colors.put(circuitIndex, circuitColor);
					circuitView.paintCircuit(g, entry, circuitColor);
				}
				
				circuitIndex++;
	
			}
		}
		
		// Repaint deliveries so that they are displayed on top of the circuits
		paintDeliveries();
	}

	/**
	 * Sets the view's graphics.
	 */
	protected void setGraphics() {
		g = (Graphics2D) this.getGraphics();
	}

	/**
	 * Paint the node in selectedColor if it is clicked 
	 * or in hover color if it is only hovered.
	 *
	 * @param node the selected node
	 * @param clicked indicate if the node is clicked (true) or hovered (false)
	 */
	public void paintSelectedNode(Delivery node, boolean clicked) {

		// Set the color depending on clicked
		if (clicked) {
			g.setColor(window.selectedColor);
		} else {
			g.setColor(window.hoverColor);
		}
		
		// If delivery ins't a delivery, it is painted with default node radius 
		if (node.getDuration() == -1) {
			mapView.drawNode(g, node.getPosition());
		
		// Else it is painted with delivery or repository radius, depending of its type
		} else {
			
			if ( node instanceof Repository ) {
				deliveryView.drawDelivery(g, node.getPosition(), circuitManagement.getDeliveryIndex(node), repositoryRadius );				
			} else {	
				deliveryView.drawDelivery(g, node.getPosition(), circuitManagement.getDeliveryIndex(node), deliveryRadius );
			}
		}
	}

	/**
	 * Paint the node in the color it had before its selection or hovering
	 *
	 * @param node the node to unpaint
	 */
	public void unPaintNode ( Delivery node) {
		
		// If delivery isn't a delivery, it is painted in nodeColor with defaultRadius
		if (node.getDuration() == -1) {
			g.setColor(nodeColor);
			mapView.drawNode(g, node.getPosition());
		
		// Else, it is painted in repositoryColor or deliveryColor, with
		// deliveryRadius or repositoryRadius, respectively, depending on its type
		} else {
			
			if ( node instanceof Repository ) {
				g.setColor(window.repositoryColor);;
				deliveryView.drawDelivery(g, node.getPosition(), circuitManagement.getDeliveryIndex(node), repositoryRadius );				
			} else {
				g.setColor(window.deliveryColor);
				deliveryView.drawDelivery(g, node.getPosition(), circuitManagement.getDeliveryIndex(node), deliveryRadius);
			}
		}
	}

	/**
	 * Paint the circuit which index is circuit in selectedColor if it is clicked 
	 * or in hover color if it is only hovered.
	 *
	 * @param circuit the selected circuit
	 * @param clicked indicate if the circuit is clicked (true) or hovered (false)
	 */
	public void paintSelectedCircuit(int circuit, boolean clicked) {
		
		if (clicked) {
			circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuit),window.selectedColor);
		} else {
			circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuit),window.hoverColor);
		}
		
	}	

	/**
	 *  Paint the circuit which index is circuit in the color it had before its selection or hovering
	 *
	 * @param circuit the circuit to unpaint
	 */
	public void unPaintCircuit(int circuit) {

		g.setColor(window.deliveryColor);
		circuitView.paintCircuit(g, circuitManagement.getCircuitByIndex(circuit), window.colors.get(circuit));

	}


	/**
	 * Zoom on the view.
	 */
	public void zoom() {
		heightScale = heightScale/2;
		widthScale = widthScale/2;
		paintComponent();

	}
	
	/**
	 * Unzoom on the view.
	 */
	public void unZoom (){
		heightScale = heightScale*2;
		widthScale = widthScale*2;
		paintComponent();
		
	}

	/**
	 * Horizontal shift on the view.
	 *
	 * @param right the value of the horizontal shift to apply, right if positive, left if negative
	 */
	public void horizontalShift(int right) {
		horizontalOffset +=right;
		paintComponent();
	}

	/**
	 * Vertical shift on the view.
	 *
	 * @param down the value of the vertical shift to apply, down if positive, up if negative
	 */
	public void verticalShift(int down) {
		verticalOffset +=down;
		paintComponent();
	}
	
	/**
	 * Shift on the view.
	 *
	 * @param right the value of the horizontal shift to apply, right if positive, left if negative
	 * @param down the value of the vertical shift to apply, down if positive, up if negative
	 */
	public void shift(int right, int down) {
		horizontalOffset +=right;
		verticalOffset +=down;
		paintComponent();
	}
	
	/**
	 * Reset default values, displaying the whole map at the center of the view.
	 */
	public void resetDefaultValues() {
		heightScale = defaultHeightScale;
		widthScale = defaultWidthScale;
		horizontalOffset = 0;
		verticalOffset = 0;
	}

	/**
	 * Paint a bow.
	 *
	 * @param g the graphics
	 * @param bow the bow to draw
	 */
	public void drawBow( Bow bow) {
		
		Node startNode = bow.getStartNode();
		Node endNode = bow.getEndNode();
		
		// The start and end nodes of the bow are converted
		// from latitude/longitude to pixel
		Point start = nodeToPoint( startNode );
		Point end = nodeToPoint ( endNode);
		
		// And are the start and end point of a stroke
		// with a width of roadWidth and a color of roadColor
		g.setStroke(new BasicStroke(width));
        g.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
		
	}

}
