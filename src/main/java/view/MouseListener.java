package main.java.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import main.java.controller.Controller;

public class MouseListener extends MouseAdapter {

	//private Controlleur controller;
	private GraphicView graphicView;
	//private TextualView textualView;
	private Window window;

	public MouseListener(Controller controller, GraphicView graphicView, Window window){
	//public MouseListener(Controller controller, GraphicView graphicView, TextualView textualView, Window window){
		//this.controller = controller;
		this.graphicView = graphicView;
		//this.textualView = textualView;
		this.window = window;
	}

	@Override
	public void mouseClicked(MouseEvent evt) { 
		// Called by MouseAdapter each time the mouse is clicked
		// If it's a left click, the controller is worn
		if ( evt.getButton() == MouseEvent.BUTTON1) { 
			/*Point p = coordonnees(evt);
			if (p != null)
				controleur.clic(p);*/
		}
	}

/*	public void mouseReleased(MouseEvent evt) {
		// Called each time the mouse is released
		// Send ....... to controller

	}*/
	
/*	private Point coordinates(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(fenetre, evt, vueGraphique);
		int x = Math.round((float)e.getX()/(float)vueGraphique.getEchelle());
		int y = Math.round((float)e.getY()/(float)vueGraphique.getEchelle());
		return PointFactory.creePoint(x, y);
	}
*/

}
