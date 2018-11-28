package main.java.view;

import main.java.entity.Circuit;

import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import main.java.entity.AtomicPath;
import main.java.entity.Bow;
import main.java.entity.Node;
import main.java.entity.Point;


import java.awt.*;


public class CircuitView extends JPanel {
	
	/**
     * Default constructor
     */
    public CircuitView() {
    }
    
	private GraphicView graphicView;
    
    /**
     * The width of the circuit
     */
    private int roadWidth;
    
    /**
     * @param graphicView The graphic view where the circuit is drawn
     * @param color The color of the circuit
     * @param width The width of the circuit
     */
    public CircuitView( GraphicView graphicView, int width) {
    	this.graphicView= graphicView;
    	this.roadWidth = width;
    }
    
    /**
     * 
     * @return The width of the circuit
     */
    protected int getRoadWidth() {
    	return roadWidth;
    }

	protected void paintCircuit ( Graphics2D g, Circuit circuit, Color color ) {
		
		
		super.paintComponent(g);
		g.setColor(color);
		
		for( AtomicPath entry : circuit.getPath()) {
		    
			for ( Bow bow: entry.getPath()) {
				
				drawBow(g, bow);
				
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

}
