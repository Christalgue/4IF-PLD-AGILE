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

import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;

public class GraphicView extends JPanel implements Observer {
	
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
	
	Color color [] = { Color.CYAN, Color.BLUE, Color.GRAY, Color.GREEN, Color.PINK };
	
	/**
	 * Create the graphic view where the map will be drawn in Window windows
	 * @param circuitManagement the CircuitManagement
	 */
	public GraphicView(main.java.entity.CircuitManagement circuitManagement, int viewHeight, int viewWidth, int width) {
		
		super();
		
		circuitManagement.addObserver(this); // this observe circuitManagement
		
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		this.width = width;
	
		this.circuitManagement = circuitManagement;
	
		mapView = new MapView (Color.WHITE, width, 2*width, this);
		circuitView = new CircuitView(this, width);
		deliveryView = new DeliveryView (Color.RED, Color.ORANGE, 3*width, this);
		
	}
	
	
	protected void calculateScale (main.java.entity.CircuitManagement circuitManagement) {

		double minLat = Double.MAX_VALUE;
		double maxLat = Double.MIN_VALUE;
		double minLong = Double.MAX_VALUE;
		double maxLong = Double.MIN_VALUE;
		
		double currentLat;
		double currentLong;
		
		HashMap<Long, Node> nodeMap = circuitManagement.getCurrentMap().getNodeMap();
		
		for(Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
		    Node node = entry.getValue();
		    
		    currentLat = node.getLatitude();
		    currentLong = node.getLongitude();
		    
		    if ( currentLat > maxLat )
		    	maxLat = currentLat;
		    else if (currentLat < minLat )
		    	minLat = currentLat;
		    	
		    if ( currentLong > maxLong )
		    	maxLong = currentLong;
		    else if ( currentLong < minLong)
		    	minLong = currentLong; 
		}
		
		heightScale = (double) ((maxLat - minLat) /(double) viewHeight);
		widthScale = (double) ((maxLong-minLong)/ (double) viewWidth);
		
		originLat = maxLat;
		originLong = minLong;
		
		/*System.out.println("heightScale " + heightScale);
		System.out.println("widthScale " + widthScale);
		System.out.println("originLat " + originLat);
		System.out.println("originLong " + originLong);
		System.out.println("minLat " + minLat);
		System.out.println("maxLong " + maxLong);*/
	}


	public Point nodeToPoint( Node node ) {
		
		Point p = new Point ((node.getLongitude()- originLong)/widthScale, (originLat - node.getLatitude())/heightScale );
		return p;
	
	}
	
	public Point pointToLatLong( Point point ) {
		
		
		Point p = new Point ( point.getX()*widthScale + originLong, -point.getY()*heightScale + originLat);
		return p;
		
	}
	
	
	public Node pointToNode( Point point ) {
		
		HashMap<Long, Node> nodeMap = circuitManagement.getCurrentMap().getNodeMap();
		
		
		for( Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
		    
			Node currentNode = entry.getValue();
			
			if ( currentNode.getLongitude() == point.getX() && currentNode.getLatitude() == point.getY()  )
				return currentNode;
				
		}
		 return null;
		
	}
	
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		repaint();
	}

	
	/**
	 * Methode appelee a chaque fois que VueGraphique doit etre redessinee
	 */
	public void paintComponent(Graphics2D g2D) {
		
		paintMap (g);
		
		paintCircuits(g);
		
		paintDeliveries(g);
		
	}
	
	public void paintMap (Graphics2D g2D) {
		calculateScale(circuitManagement);
		System.out.println(widthScale);
		mapView.paintMap(g2D, circuitManagement.getCurrentMap());
	}
	
	public void paintDeliveries (Graphics2D g2D) {
		deliveryView.paintDeliveries(g2D,circuitManagement.getDeliveryList());
	}
	
	public void paintCircuits(Graphics2D g2D) {

		int colorIndex =0;
		
		for( Circuit entry : circuitManagement.getCircuitsList() ) {
		    
			circuitView.paintCircuit(g2D, entry, color[colorIndex%color.length]);
			colorIndex++;
			
		}
		
		paintDeliveries(g);
	}
	
	protected Graphics2D getGraphic() {
		return g;
	}
	
	protected void setGraphics() {
		System.out.println("setGraphics : " + this.getGraphics());
		g = (Graphics2D) this.getGraphics();
		System.out.println(g);
	}


	@Override
	public String toString() {
		return "GraphicView [heightScale=" + heightScale + ", widthScale=" + widthScale + ", originLat=" + originLat
				+ ", originLong=" + originLong + ", viewHeight=" + viewHeight + ", viewWidth=" + viewWidth
				+ ", circuitManagement=" + circuitManagement + ", g=" + g + ", mapView=" + mapView + ", circuitView="
				+ circuitView + ", deliveryView=" + deliveryView + ", color=" + Arrays.toString(color) + "]";
	}
	
	

}
