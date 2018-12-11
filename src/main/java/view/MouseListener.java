package main.java.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

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
	
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		
		Point p = new Point ( evt.getX(), evt.getY());
		p = graphicView.pointToLatLong(p);
		controller.mouseMoved(p);
	}
	
/*    public void mouseWheelMoved(MouseWheelEvent e) {
        
        int notches = e.getWheelRotation();
        if (notches < 0) {
            System.out.println("ZOOOOM");
        } else {
            System.out.println("DEZOOOM");
        }
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            System.out.println("MROUUUUUW");
        } else { //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
            System.out.println("MYSTERYYY");
        }
     }*/

}
