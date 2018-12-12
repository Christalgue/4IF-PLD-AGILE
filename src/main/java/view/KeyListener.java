package main.java.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.controller.Controller;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving key events.
 * The class that is interested in processing a key
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addKeyListener<code> method. When
 * the key event occurs, that object's appropriate
 * method is invoked.
 *
 * @see KeyEvent
 */
public class KeyListener extends KeyAdapter {

	/** The controller. */
	private Controller controller;

	/**
	 * Instantiates a new key listener.
	 *
	 * @param controller the controller
	 */
	public KeyListener(Controller controller){
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Called by the key listener each time a key is pressed
		// Interesting keys are SUPPR, CRTL+Z, and numbers ( for delivery men number ) 
		int keyCode = e.getKeyCode();
	    
		if((e.getModifiers() & KeyEvent.CTRL_MASK)==0 ){
			switch( keyCode ) { 
		        case KeyEvent.VK_UP:
		        	controller.getWindow().verticalShift(-50);
		        	controller.getWindow().enableResetScaleButton();
		            break;
		        case KeyEvent.VK_DOWN:
		        	controller.getWindow().verticalShift(50);
		        	controller.getWindow().enableResetScaleButton();
		            break;
		        case KeyEvent.VK_LEFT:
		        	controller.getWindow().horizontalShift(-50);
		        	controller.getWindow().enableResetScaleButton();
		            break;
		        case KeyEvent.VK_RIGHT :
		        	controller.getWindow().horizontalShift(50);
		        	controller.getWindow().enableResetScaleButton();
		            break;
		        case KeyEvent.VK_Z:
		        	controller.getWindow().zoom();
		        	controller.getWindow().enableResetScaleButton();
		        	break;
		        case KeyEvent.VK_D:
		        	controller.getWindow().unZoom();
		        	controller.getWindow().enableResetScaleButton();
		        	break;
		        	
		     }
		}
		
		if (keyCode== KeyEvent.VK_Z && ((e.getModifiers() & KeyEvent.CTRL_MASK)!=0 )) {
			controller.undo();
			
		} else if (keyCode == KeyEvent.VK_Y && ((e.getModifiers() & KeyEvent.CTRL_MASK)!=0 )) {
			controller.redo();
		} 
	}

}
