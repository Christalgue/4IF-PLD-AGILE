package main.java.view;

import java.util.Map;
import java.util.Observable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
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
	
	private int heightScale;
	private int widthScale;
	
	private double originLat;
	private double originLong;
	
	private int viewHeight;
	private int viewWidth;
	
	private main.java.entity.CircuitManagement circuitManagement;
	private Graphics2D g;
	
	private Color backgroundColor;
	
	private MapView mapView;
	private CircuitView circuitView;
	private DeliveryView deliveryView;
	
	Color color [] = { Color.RED, Color.BLUE, Color.GRAY, Color.GREEN, Color.PINK };
	
	/**
	 * Create the graphic view where the map will be drawn in Window windows
	 * @param circuitManagement the CircuitManagement
	 * @param windows the Window
	 */
	public GraphicView(main.java.entity.CircuitManagement circuitManagement, Window windows, int viewHeight, int viewWidth) {
		
		super();
		
		circuitManagement.addObserver(this); // this observe circuitManagement
		
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		
		setLayout(null);
		setBackground(backgroundColor);
		setSize(viewWidth, viewHeight);
		windows.getContentPane().add(this);
	
		this.circuitManagement = circuitManagement;
		calculateScale(circuitManagement);
	
		mapView = new MapView (Color.WHITE, 10, 10, this);
		circuitView = new CircuitView(this, 10 );
		deliveryView =new DeliveryView (Color.RED, 10, this);
		
		paintComponent(g);
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
		
		heightScale = (int) ((maxLat - minLat) /(double) viewHeight);
		widthScale = (int) ((maxLong-minLong)/ (double) viewWidth);
		
		originLat = maxLat;
		originLong = minLong;
		
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
	public void paintComponent(Graphics2D g) {
		
		super.paintComponent(g);
		
		mapView.paintMap(g, circuitManagement.getCurrentMap());
		
		int colorIndex =0;
		
		for( Circuit entry : circuitManagement.getCircuitsList() ) {
		    
			circuitView.paintCircuit(g, entry, color[colorIndex%color.length]);
			colorIndex++;
			
		}
		
		deliveryView.paintDeliveries(g,circuitManagement.getDeliveryList());
		
	}
	
	
	
	/**
	 * 
	 * @return The color of the background of the map
	 */
	protected Color getBackgroundColor() {
		return backgroundColor;
	}
	
	protected Graphics2D getGraphic() {
		return g;
	}

}
