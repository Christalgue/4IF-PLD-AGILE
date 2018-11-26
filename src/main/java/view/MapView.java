package main.java.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import main.java.entity.Bow;
//import main.java.entity.Map;
import main.java.entity.Node;
import main.java.entity.Point;


import java.awt.*;


public class MapView extends JPanel {
	
	private GraphicView graphicView;
	
	/**
	 * Default constructor
	 */
	public MapView () {
		
	}
	
	/**
     * The color of the road
     */
	private Color colorRoad;
	
	/**
     * The width of the road
     */
	private int roadWidth;
	
	private int nodeRadius;
	
	/**
	 * 
	 * @param colorRoad 		The color of the road
	 * @param roadWidth 			The width of the road
	 */
	public MapView(Color colorRoad, int roadWidth, int nodeRadius, GraphicView graphicView) {
		this.colorRoad = colorRoad;
		this.roadWidth = roadWidth;
		this.nodeRadius = nodeRadius;
		this.graphicView = graphicView;
	}
	
	/**
	 * 
	 * @return The color of the road
	 */
	protected Color getColorRoad () {
		return colorRoad;
	}
	
	/**
	 * 
	 * @return The width of the road
	 */
	public int getWidth() {
		return roadWidth;
	}
	
	
	/**
	 * 
	 * @return The radius of the node
	 */
	public int getRadius() {
		return nodeRadius;
	}	
	
	protected void paintMap ( Graphics2D g, main.java.entity.Map map ) {
		
		
		super.paintComponent(g);
		g.setColor(colorRoad);
		
		HashMap<Long,Set<Bow>> bowMap = map.getBowMap();
		HashMap<Long, Node> nodeMap = map.getNodeMap();
		
		
		for( Map.Entry<Long, Set<Bow>> entry : bowMap.entrySet()) {
		    
			Long nodeID = entry.getKey();
			Node node = nodeMap.get(nodeID);
			
			drawNode( g, node);
			
			Set <Bow> bows = entry.getValue();

		    for ( Bow bowEntry : bows) {
		    	
		    	drawBow( g, bowEntry);
		    	
		    }
		}
		
	}
	
	public void drawBow( Graphics2D g, Bow bow) {
		
		Node startNode = bow.getStartNode();
		Node endNode = bow.getEndNode();
		
		Point start = graphicView.nodeToPoint( startNode );
		Point end = graphicView.nodeToPoint ( endNode);
		
		g.setStroke(new BasicStroke(roadWidth));
        g.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
		
	}
	
	public void drawNode( Graphics2D g, Node node) {
		
		Point point = graphicView.nodeToPoint( node );
		g.fillOval((int) (point.getX()- nodeRadius/2) ,(int) (point.getY()- nodeRadius/2), nodeRadius,nodeRadius);
		
	}
	
}
