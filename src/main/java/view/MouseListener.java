package main.java.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.controller.Controller;
import main.java.entity.Point;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving mouse events.
 * The class that is interested in processing a mouse
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMouseListener<code> method. When
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
	
	/** The window. */
	private Window window;
	
	/** The last clicked point. */
	private Point lastClickedPoint;

	
	/**
	 * Instantiates a new mouse listener.
	 *
	 * @param controller the controller
	 * @param graphicView the graphic view
	 * @param window the window
	 */
	public MouseListener(Controller controller, GraphicView graphicView, Window window){
		this.controller = controller;
		this.graphicView = graphicView;
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evt) { 
		// Called by MouseAdapter each time the mouse is clicked
		// If it's a left click, the controller is worn
		if ( evt.getButton() == MouseEvent.BUTTON1) { 
			Point p = new Point (evt.getX(), evt.getY());
			p = graphicView.pointToLatLong(p);
			controller.leftClick(p);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
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
	 */
	@Override
	public void mouseReleased(MouseEvent evt) {
		
		int x = evt.getX();
		int y = evt.getY();
		
		if (lastClickedPoint != null ) {
			
			int lastX = (int)lastClickedPoint.getX();
			int lastY = (int)lastClickedPoint.getY();
			
			if (y < lastY-10 ||  y > lastY+10 || x > lastX +10 || x < lastX -10) {
		
			graphicView.shift((int)(lastClickedPoint.getX()- x),(int)(lastClickedPoint.getY()- y));
			}
			controller.getWindow().enableResetScaleButton();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent evt) {
		
		Point p = new Point ( evt.getX(), evt.getY());
		p = graphicView.pointToLatLong(p);
		controller.mouseMoved(p);
		
	}
}
