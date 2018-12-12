package main.java.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import main.java.entity.Delivery;
//import main.java.entity.Map;
import main.java.entity.Node;
import main.java.entity.Point;


// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryView.
 */
public class DeliveryView extends JPanel {
	
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
	 * Gets the delivery color.
	 *
	 * @return The color of the road
	 */
	protected Color getDeliveryColor () {
		return deliveryColor;
	}
	
	/**
	 * Gets the delivery radius.
	 *
	 * @return The width of the road
	 */
	public int getDeliveryRadius() {
		return deliveryRadius;
	}
	
	/**
	 * Paint deliveries.
	 *
	 * @param g the g
	 * @param deliveryList the delivery list
	 */
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


	/**
	 * Draw delivery.
	 *
	 * @param g the g
	 * @param node the node
	 * @param deliveryIndex the delivery index
	 * @param radius the radius
	 */
	public void drawDelivery( Graphics2D g, Node node, int deliveryIndex, int radius) {

		Point point = graphicView.nodeToPoint( node );
		g.fillOval((int) (point.getX()- radius/2) ,(int) (point.getY()- radius/2), radius,radius);
		g.drawString(""+deliveryIndex+"", (int) point.getX()-3, (int) point.getY()-6);
		
	}
	
}


