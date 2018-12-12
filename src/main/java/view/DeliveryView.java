package main.java.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import main.java.entity.Delivery;
//import main.java.entity.Map;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.entity.Repository;

/**
 * The Class DeliveryView paint the circuits in the graphicView panel
 */
public class DeliveryView {
	
	/** The graphic view. */
	private GraphicView graphicView;
	
	/**
	 * Default constructor.
	 */
	public DeliveryView () {
		
	}
	
	/** The color of the delivery. */
	private Color deliveryColor;
	
	/** The repository color. */
	private Color repositoryColor;
	
	/** The radius of the delivery. */
	private int deliveryRadius;

	/** The repository radius. */
	private int repositoryRadius;
	
	/**
	 * Instantiates a new delivery view.
	 *
	 * @param deliveryColor the delivery color
	 * @param repositoryColor the repository color
	 * @param deliveryRadius 		The width of the road
	 * @param repositoryRadius the repository radius
	 * @param graphicView the graphic view
	 */
	public DeliveryView (Color deliveryColor, Color repositoryColor, int deliveryRadius, int repositoryRadius, GraphicView graphicView) {
		this.deliveryColor = deliveryColor;
		this.repositoryColor = repositoryColor;
		this.deliveryRadius = deliveryRadius;
		this.repositoryRadius = repositoryRadius;
		this.graphicView = graphicView;
	}
	
	
	/**
	 * Paint the deliveries.
	 *
	 * @param g the graphics
	 * @param deliveryList the delivery list
	 */
	protected void paintDeliveries ( Graphics2D g,  List<Delivery> deliveryList ) {
		
		g.setColor(repositoryColor);
		
		int deliveryIndex = 0;
		
		// If the deliveries list isn't empty
		if ( deliveryList != null) {
		
			// For each delivery in it
			for( Delivery entry : deliveryList ) {
			    
				Node node = entry.getPosition();		
				
				// If it is the repository, paint it in repositoryColor with repositoryRadius
				if (entry instanceof Repository) {
					
					drawDelivery( g, node,deliveryIndex, repositoryRadius );
					g.setColor(deliveryColor);
				
				// Else, paint it in deliveryColor with deliveryRadius
				}else {
					drawDelivery( g, node,deliveryIndex, deliveryRadius );
				}
				 deliveryIndex++;
				
			}
		}
		
	}


	/**
	 * Paint a delivery and its index.
	 *
	 * @param g the graphics
	 * @param node the delivery node to paint
	 * @param deliveryIndex the delivery index
	 * @param radius the radius of the delivery to paint
	 */
	public void drawDelivery( Graphics2D g, Node node, int deliveryIndex, int radius) {
		
		// Paint the delivery node
		Point point = graphicView.nodeToPoint( node );
		g.fillOval((int) (point.getX()- radius/2) ,(int) (point.getY()- radius/2), radius,radius);
		
		// Paint the delivery index
		g.drawString(""+deliveryIndex+"", (int) point.getX()-3, (int) point.getY()-6);
		
	}
	
}


