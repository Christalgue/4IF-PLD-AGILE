package main.java.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import main.java.controller.Controller;
import main.java.entity.Node;
import main.java.entity.Point;

public class MouseListener extends MouseAdapter {

	private Controller controller;
	private GraphicView graphicView;
	//private TextualView textualView;
	private Window window;

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
			Point p = graphicView.pointToLatLong(new Point (evt.getX(), evt.getY()));
			Node n = graphicView.pointToNode(p);
			window.nodeSelected(n);
			System.out.println(n);
			/*if (p != null) {
				
				if(this.controller.circuitManagement.checkNodeInDeliveryList(n))
				{
					this.controller.leftClick(n, true);
				} else {
					this.controller.leftClick(n, false);
				}
			}*/
				//controleur.clic(p);*/
		}
	}

	public void mouseReleased(MouseEvent evt) {
		window.addDeliveryButton.setVisible(true);
	}
	
/*	private Point coordinates(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(fenetre, evt, vueGraphique);
		int x = Math.round((float)e.getX()/(float)vueGraphique.getEchelle());
		int y = Math.round((float)e.getY()/(float)vueGraphique.getEchelle());
		return PointFactory.creePoint(x, y);
	}
*/

}
