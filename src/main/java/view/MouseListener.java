package main.java.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.controller.Controller;
import main.java.entity.Point;

/**
 * The listener interface for receiving mouse events.
 * The class that is interested in processing a mouse
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMouseListener</code> method. When
 * the mouse event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MouseEvent
 */
public class MouseListener extends MouseAdapter {

	/** The controller. */
	private Controller controller;
	
	/** The graphic view. */
	private GraphicView graphicView;
	
	/** The last clicked point. */
	private Point lastClickedPoint;

	
	/**
	 * Instantiates a new mouse listener.
	 *
	 * @param controller the controller
	 * @param graphicView the graphic view
	 */
	public MouseListener(Controller controller, GraphicView graphicView){
		this.controller = controller;
		this.graphicView = graphicView;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 * Function called by MouseAdapter each time the mouse is clicked
	 * If it's a left click, the coordinates of the clicked point converted to
	 * latitude/longitude are sent to the controller
	 */
	@Override
	public void mouseClicked(MouseEvent evt) { 
		if ( evt.getButton() == MouseEvent.BUTTON1) { 
			Point p = new Point (evt.getX(), evt.getY());
			p = graphicView.pointToLatLong(p);
			controller.leftClick(p);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 * Function called each time the mouse is pressed
	 * If it's a left click, the coordinates of the clicked point are stored
	 * in lastClickedPoint
	 */
	@Override
	public void mousePressed (MouseEvent evt) {
		
		if ( evt.getButton() == MouseEvent.BUTTON1) { 
			Point p = new Point (evt.getX(), evt.getY());
			lastClickedPoint = p;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 * Function called each time the mouse is released
	 * Calculate the difference between the releasing point and the last clicked point
	 * and apply a shift on the graphicView depending on it. The shift will occur
	 * only if the difference is at least of 10
	 */
	@Override
	public void mouseReleased(MouseEvent evt) {
		
		int x = evt.getX();
		int y = evt.getY();
		
		if (lastClickedPoint != null ) {
			
			int lastX = (int)lastClickedPoint.getX();
			int lastY = (int)lastClickedPoint.getY();
			
			if (y < lastY-10 ||  y > lastY+10 || x > lastX +10 || x < lastX -10) {
				graphicView.shift((int)(lastX- x),(int)(lastY- y));
			}
			
			controller.getWindow().enableResetScaleButton();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
	 * Function called each time the mouse is moved
	 * The coordinates of the hovered point converted to
	 * latitude/longitude are sent to the controller
	 */
	@Override
	public void mouseMoved(MouseEvent evt) {
		
		Point p = new Point ( evt.getX(), evt.getY());
		p = graphicView.pointToLatLong(p);
		controller.mouseMoved(p);
		
	}
}
