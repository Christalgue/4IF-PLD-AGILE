package main.java.view;

import main.java.entity.Delivery;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import main.java.entity.Bow;
//import main.java.entity.Map;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.entity.Delivery;

import java.awt.*;


public class DeliveryView extends JPanel {
	
	private GraphicView graphicView;
	
	/**
	 * Default constructor
	 */
	public DeliveryView () {
		
	}
	
	/**
     * The color of the delivery
     */
	private Color deliveryColor;
	
	private Color repositoryColor;
	
	/**
     * The radius of the delivery
     */
	private int deliveryRadius;
	
	/**
	 * 
	 * @param colorDelivery 		The color of the delivery
	 * @param deliveryRadius			The width of the road
	 */
	public DeliveryView (Color deliveryColor, Color repositoryColor, int deliveryRadius, GraphicView graphicView) {
		this.deliveryColor = deliveryColor;
		this.repositoryColor = repositoryColor;
		this.deliveryRadius = deliveryRadius;
		this.graphicView = graphicView;
	}
	
	/**
	 * 
	 * @return The color of the road
	 */
	protected Color getDeliveryColor () {
		return deliveryColor;
	}
	
	/**
	 * 
	 * @return The width of the road
	 */
	public int getDeliveryRadius() {
		return deliveryRadius;
	}
	
	protected void paintDeliveries ( Graphics2D g,  List<Delivery> deliveryList ) {
		
		g.setColor(repositoryColor);
		
		boolean b = true;
		
		for( Delivery entry : deliveryList ) {
		    
			Node node = entry.getPosition();		
			drawDelivery( g, node);
			
			if (b) {
				g.setColor(deliveryColor);
				b = false;
			}
			
		}
		
	}

	public void drawDelivery( Graphics2D g, Node node) {
		
		Point point = graphicView.nodeToPoint( node );
		g.fillOval((int) (point.getX()- deliveryRadius/2) ,(int) (point.getY()- deliveryRadius/2), deliveryRadius,deliveryRadius);
		
	}
	
}


