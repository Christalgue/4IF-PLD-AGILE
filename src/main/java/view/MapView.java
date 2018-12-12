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


// TODO: Auto-generated Javadoc
/**
 * The Class MapView.
 */
public class MapView extends JPanel {
	
	/** The graphic view. */
	private GraphicView graphicView;
	
	/**
	 * Default constructor.
	 */
	public MapView () {
		
	}
	
	/** The color of the road. */
	private Color colorRoad;
	
	/** The width of the road. */
	private int roadWidth;
	
	/** The node radius. */
	private int nodeRadius;
	
	/**
	 * Instantiates a new map view.
	 *
	 * @param colorRoad 		The color of the road
	 * @param roadWidth 			The width of the road
	 * @param graphicView the graphic view
	 */
	public MapView(Color colorRoad, int roadWidth, GraphicView graphicView) {
		this.colorRoad = colorRoad;
		this.roadWidth = roadWidth;
		this.nodeRadius = roadWidth*2;
		this.graphicView = graphicView;
	}
	
	/**
	 * Gets the color road.
	 *
	 * @return The color of the road
	 */
	protected Color getColorRoad () {
		return colorRoad;
	}
	
	/**
	 * Gets the width.
	 *
	 * @return The width of the road
	 */
	public int getWidth() {
		return roadWidth;
	}
	
	
	/**
	 * Gets the radius.
	 *
	 * @return The radius of the node
	 */
	public int getRadius() {
		return nodeRadius;
	}	
	
	/**
	 * Paint map.
	 *
	 * @param g the g
	 * @param map the map
	 */
	protected void paintMap ( Graphics2D g, main.java.entity.Map map ) {
		
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
	
	/**
	 * Draw bow.
	 *
	 * @param g the g
	 * @param bow the bow
	 */
	public void drawBow( Graphics2D g, Bow bow) {
		
		Node startNode = bow.getStartNode();
		Node endNode = bow.getEndNode();
		
		Point start = graphicView.nodeToPoint( startNode );
		Point end = graphicView.nodeToPoint ( endNode);
		
		g.setStroke(new BasicStroke(roadWidth));
        g.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
		
	}
	
	/**
	 * Draw node.
	 *
	 * @param g the g
	 * @param node the node
	 */
	public void drawNode( Graphics2D g, Node node) {
		
		/*if (node.getLongitude() == graphicView.originLong || node.getLongitude()== graphicView.maxLong || node.getLatitude()== graphicView.originLat || node.getLatitude()==graphicView.minLat) {
			g.setColor(Color.RED);
			Point point = graphicView.nodeToPoint( node );
			g.fillOval((int) (point.getX()- nodeRadius/2) ,(int) (point.getY()- nodeRadius/2), nodeRadius,nodeRadius);
			g.setColor(colorRoad);
		}else {*/
		
			Point point = graphicView.nodeToPoint( node );
			g.fillOval((int) (point.getX()- nodeRadius/2) ,(int) (point.getY()- nodeRadius/2), nodeRadius,nodeRadius);
		//}
	}
	
	
	/**
	 * Test arrow.
	 *
	 * @param g the g
	 */
	// pour tester
	public void testArrow(Graphics2D g) {
		
		g.setStroke(new BasicStroke(10));
        g.draw(new Line2D.Double(0, 0, 30, 30));
 
		g.setStroke(new BasicStroke(10));
        g.draw(new Line2D.Double(0, 640, 30, 610));        
        
		g.setStroke(new BasicStroke(10));
        g.draw(new Line2D.Double(1080, 0, 1050, 30));        
        
		g.setStroke(new BasicStroke(10));
        g.draw(new Line2D.Double(1080, 640, 1050, 610));
        
        g.setColor(Color.BLUE);
        
        drawArrow( g, new Point(0,0), new Point(30,30));  

        drawArrow( g, new Point(0,640), new Point(30,610));        
        
        drawArrow( g, new Point(1080,0), new Point(1050,30));
        
        drawArrow( g, new Point(1080,640), new Point(1050,610));
        
	}
	
	/**
	 * Draw arrow.
	 *
	 * @param g the g
	 * @param start the start
	 * @param end the end
	 */
	public void drawArrow( Graphics2D g, Point start, Point end) {
		
		int arrowLength = 10;
		int arrowWidth = 10;
		
		int xPoints [] = new int[3];
		int yPoints[] = new int[3];
		
		double startX = start.getX();
		double startY = start.getY();	
		double endX = end.getX();
		double endY = end.getY();
		
		double deltaX = startX - endX;
		double deltaY = startY - endY;
		
		double cosinus = deltaX / Math.sqrt(Math.pow(deltaX, 2)+ Math.pow(deltaY, 2) );
		double sinus = deltaY / Math.sqrt(Math.pow(deltaX, 2)+ Math.pow(deltaY, 2) );
		
		double orthoPointX = endX + cosinus*arrowLength;
		double orthoPointY = endY + sinus*arrowLength;
		
		double point1X = orthoPointX - sinus*arrowWidth;
		double point1Y = orthoPointY + cosinus*arrowWidth;
		
		double point2X = orthoPointX + sinus*arrowWidth;
		double point2Y = orthoPointY - cosinus*arrowWidth;
		
		xPoints[0] =(int) endX;
		yPoints[0] = (int) endY;
		
		xPoints[1]= (int) point1X; 
		yPoints[1]= (int)point1Y;
		
		xPoints[2]= (int) point2X;
		yPoints[2]= (int) point2Y;
		
		g.fillPolygon( xPoints, yPoints, 3);
			
	}

}
