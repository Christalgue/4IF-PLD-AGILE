package main.java.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import main.java.entity.AtomicPath;
import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.Node;
import main.java.entity.Point;


// TODO: Auto-generated Javadoc
/**
 * The Class CircuitView.
 */
public class CircuitView extends JPanel {
	
	/**
	 * Default constructor.
	 */
    public CircuitView() {
    }
    
	/** The graphic view. */
	private GraphicView graphicView;
    
    /** The width of the circuit. */
    private int roadWidth;
    
    /** The arrow length. */
    private int arrowLength;
    
    /** The arrow width. */
    private int arrowWidth;
    
    /**
     * Instantiates a new circuit view.
     *
     * @param graphicView The graphic view where the circuit is drawn
     * @param width The width of the circuit
     */
    public CircuitView( GraphicView graphicView, int width) {
    	this.graphicView= graphicView;
    	this.roadWidth = width;
    	this.arrowLength = 2*width;
    	this.arrowWidth = 2*width;
    }
    
    /**
     * Gets the road width.
     *
     * @return The width of the circuit
     */
    protected int getRoadWidth() {
    	return roadWidth;
    }

	/**
	 * Paint circuit.
	 *
	 * @param g the g
	 * @param circuit the circuit
	 * @param color the color
	 */
	protected void paintCircuit ( Graphics2D g, Circuit circuit, Color color ) {
		
		int atomicPathIndex = 0;
		Point start;
		Point end;
		boolean arrow = false;
		
		super.paintComponent(g);
		g.setColor(color);
		
		if(circuit != null && circuit.getPath() != null) {
			for( AtomicPath entry : circuit.getPath()) {
			    if(entry != null) {
			    	for ( Bow bow: entry.getPath()) {
					
					drawBow(g, bow);
					
					
					if (arrow) {
						start = graphicView.nodeToPoint( bow.getStartNode());
						end = graphicView.nodeToPoint ( bow.getEndNode());
						drawArrow(g, start, end);
					}
					
					arrow = !arrow;
					
					/*atomicPathIndex++;
					
					if ( atomicPathIndex == entry.getPath().size() ) {	
						
						start = graphicView.nodeToPoint( bow.getStartNode());
					 	end = graphicView.nodeToPoint ( bow.getEndNode());
						drawArrow(g, start, end);
						atomicPathIndex =0;
					}*/
					
					
			    	}
			    }
				
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
	 * Draw arrow.
	 *
	 * @param g the g
	 * @param start the start
	 * @param end the end
	 */
	public void drawArrow( Graphics2D g, Point start, Point end) {
		
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
		double orthoPointY= endY + sinus*arrowLength;
		
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
		
		//g.fillPolygon( xPoints, yPoints, 3);
		
		g.setStroke(new BasicStroke(roadWidth));
        g.draw(new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]));
        g.draw(new Line2D.Double(xPoints[0], yPoints[0], xPoints[2], yPoints[2]));
		
		
	}

}
