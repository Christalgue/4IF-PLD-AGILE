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

/**
 * The Class CircuitView paint the circuits in the graphicView panel
 */
public class CircuitView {
    
	/** The graphic view. */
	private GraphicView graphicView;
    
    /** The width of the circuit. */
    private int roadWidth;
    
    /** The arrow's head length. */
    private int arrowLength;
    
    /** The arrow's head width. */
    private int arrowWidth;
    
	/**
	 * Default constructor.
	 */
    public CircuitView() {
    }
    
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
	 * Paint a circuit.
	 *
	 * @param g the graphics
	 * @param circuit the circuit to paint
	 * @param color the color in which the circuit is painted
	 */
	protected void paintCircuit ( Graphics2D g, Circuit circuit, Color color ) {
		
		Point start;
		Point end;
		boolean arrow = false;
		
		g.setColor(color);
		
		// If the circuit's path isn't empty
		if(circuit != null && circuit.getPath() != null) {
			
			for( AtomicPath entry : circuit.getPath()) {
			    
				if(entry != null) {
			    	
					// For each one of its bow
			    	for ( Bow bow: entry.getPath()) {
					
			    		// It is painted
						graphicView.drawBow( bow);
			    		
			    		// An arrow is painted at the end of half of the bows
						if (arrow) {
							start = graphicView.nodeToPoint( bow.getStartNode());
							end = graphicView.nodeToPoint ( bow.getEndNode());
							drawArrow(g, start, end);
						}
						
						arrow = !arrow;
					
			    	}
			    }
			}
		}
		
	}	
	
	/**
	 * Draw an arrow.
	 *
	 * @param g the graphics
	 * @param start the start of the bow at the end of which the arrow is painted
	 * @param end the end of the bow at the end of which the arrow is painted
	 */
	public void drawArrow( Graphics2D g, Point start, Point end) {
				
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
		
		g.setStroke(new BasicStroke(roadWidth));
        g.draw(new Line2D.Double(endX, endY, point1X, point1Y));
        g.draw(new Line2D.Double(endX, endY, point2X, point2Y));
		
	}

}
