package main.java.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import main.java.entity.Bow;
//import main.java.entity.Map;
import main.java.entity.Node;
import main.java.entity.Point;

/**
 * The Class MapView paint the map in the graphicView panel
 */
public class MapView  {
	
	/** The graphic view. */
	private GraphicView graphicView;
	
	/** The color of the road and nodes. */
	private Color roadColor;
	
	/** The width of the road. */
	private int roadWidth;
	
	/** The node radius. */
	private int nodeRadius;
	
	/**
	 * Default constructor.
	 */
	public MapView () {
		
	}
	
	/**
	 * Instantiates a new map view.
	 *
	 * @param roadColor 		the color of the road and nodes
	 * @param roadWidth 			the width of the road
	 * @param graphicView the graphic view
	 */
	public MapView(Color roadColor, int roadWidth, GraphicView graphicView) {
		this.roadColor = roadColor;
		this.roadWidth = roadWidth;
		this.nodeRadius = roadWidth*2;
		this.graphicView = graphicView;
	}
	
	
	/**
	 * Paint the map, based on circuitManagement's map.
	 *
	 * @param g the graphics
	 * @param map the map
	 */
	protected void paintMap ( Graphics2D g, main.java.entity.Map map ) {
		
		g.setColor(roadColor);
		
		HashMap<Long,Set<Bow>> bowMap = map.getBowMap();
		HashMap<Long, Node> nodeMap = map.getNodeMap();
		
		// For each node of the map
		for( Map.Entry<Long, Set<Bow>> entry : bowMap.entrySet()) {
		    
			Long nodeID = entry.getKey();
			Node node = nodeMap.get(nodeID);
			
			// It is drawn
			drawNode( g, node);
			
			Set <Bow> bows = entry.getValue();

			// Then every bow which start's node is this node is drawn
		    for ( Bow bowEntry : bows) {
		    	
		    	graphicView.drawBow( bowEntry);
		    	
		    }
		}
		
	}
	
	
	/**
	 * Paint a node.
	 *
	 * @param g the graphics
	 * @param node the node
	 */
	public void drawNode( Graphics2D g, Node node) {
		
		// The node coordinates are converted from latitude/longitude to pixels
		// and the node is drawn with a color of roadColor and a radius of nodeRadius
		Point point = graphicView.nodeToPoint( node );
		g.fillOval((int) (point.getX()- nodeRadius/2) ,(int) (point.getY()- nodeRadius/2), nodeRadius,nodeRadius);
	}

}
