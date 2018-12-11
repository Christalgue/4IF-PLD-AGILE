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

	private int repositoryRadius;
	
	/**
	 * 
	 * @param colorDelivery 		The color of the delivery
	 * @param deliveryRadius			The width of the road
	 */
	public DeliveryView (Color deliveryColor, Color repositoryColor, int deliveryRadius, int repositoryRadius, GraphicView graphicView) {
		this.deliveryColor = deliveryColor;
		this.repositoryColor = repositoryColor;
		this.deliveryRadius = deliveryRadius;
		this.repositoryRadius = repositoryRadius;
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
		int deliveryIndex = 0;
		
		if ( deliveryList != null) {
		
			for( Delivery entry : deliveryList ) {
			    
				Node node = entry.getPosition();		
				drawDelivery( g, node,deliveryIndex, deliveryRadius );
				
				if (b) {
					
					drawDelivery( g, node,deliveryIndex, repositoryRadius );
					g.setColor(deliveryColor);
					b = false;
				}
				 deliveryIndex++;
				
			}
		}
		
	}


	public void drawDelivery( Graphics2D g, Node node, int deliveryIndex, int radius) {

		Point point = graphicView.nodeToPoint( node );
		g.fillOval((int) (point.getX()- radius/2) ,(int) (point.getY()- radius/2), radius,radius);
		g.drawString(""+deliveryIndex+"", (int) point.getX()-3, (int) point.getY()-6);
		
	}
	
}


