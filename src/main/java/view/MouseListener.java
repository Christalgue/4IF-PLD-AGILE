package main.java.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import main.java.controller.Controller;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;

public class MouseListener extends MouseAdapter {

	private Controller controller;
	private GraphicView graphicView;
	//private TextualView textualView;
	private Window window;
	private Point lastClickedPoint;

	public MouseListener(Controller controller, GraphicView graphicView, Window window){
	//public MouseListener(Controller controller, GraphicView graphicView, TextualView textualView, Window window){
		this.controller = controller;
		this.graphicView = graphicView;
		//this.textualView = textualView;
		this.window = window;
	}

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

	public void mousePressed (MouseEvent evt) {
		
		if ( evt.getButton() == MouseEvent.BUTTON1) { 
			Point p = new Point (evt.getX(), evt.getY());
			lastClickedPoint = p;
		}
	}
	
	public void mouseReleased(MouseEvent evt) {
		
		if (lastClickedPoint != null) {
			Point releasedPoint = new Point (evt.getX(), evt.getY());
			graphicView.shift((int)(-lastClickedPoint.getX()+ releasedPoint.getX()),(int)(-lastClickedPoint.getY()+ releasedPoint.getY()));
		}
		
	}
	
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		
		Point p = new Point ( evt.getX(), evt.getY());
		p = graphicView.pointToLatLong(p);
		controller.mouseMoved(p);
	}

}
