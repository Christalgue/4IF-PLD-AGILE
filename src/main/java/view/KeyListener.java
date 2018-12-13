package main.java.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.controller.Controller;

/**
 * The listener interface for receiving key events.
 * The class that is interested in processing a key
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addKeyListener</code> method. When
 * the key event occurs, that object's appropriate
 * method is invoked.
 *
 * @see KeyEvent
 */
public class KeyListener extends KeyAdapter {

	/** The controller. */
	private Controller controller;
	
	/** The zoom enable. */
	private boolean zoomEnable = false;
	
	/** The unzoom enable. */
	private boolean unZoomEnable = false;
	
	/** The shift enable. */
	private boolean shiftEnable = false;

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
	 * 
	 *  Called by the key listener each time a key is pressed.
 	 *	Interesting keys are SUPPR, CRTL+Z, CTRL+Y, Z, D and the arrows.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// Test if the ctrl key is pressed
		if((e.getModifiers() & KeyEvent.CTRL_MASK)==0 ){
			switch( keyCode ) { 
				
				// When the user wants to go up on the map using the key arrow up.
		        case KeyEvent.VK_UP:
		        	if (shiftEnable) {
		        		// Do an upward shift of 50 pixels.
			        	controller.getWindow().verticalShift(-50);
			        	controller.getWindow().enableResetScaleButton();
		        	}
		            break;
		            
		        // When the user wants to go up on the map using the key arrow down.
		        case KeyEvent.VK_DOWN:
		        	if (shiftEnable) {
		        		// Do a downward shift of 50 pixels.
		        		controller.getWindow().verticalShift(50);
		        		controller.getWindow().enableResetScaleButton();
		        	}
		            break;
		            
		        // When the user wants to go up on the map using the key arrow left.
		        case KeyEvent.VK_LEFT:
			        if (shiftEnable) {
			        	// Do a left shift of 50 pixels.
			        	controller.getWindow().horizontalShift(-50);
			        	controller.getWindow().enableResetScaleButton();
		        	}
		            break;
		            
		        // When the user wants to go up on the map using the key arrow right.
		        case KeyEvent.VK_RIGHT :
		        	if (shiftEnable) {
		        		// Do a right shift of 50 pixels.
		        		controller.getWindow().horizontalShift(50);
		        		controller.getWindow().enableResetScaleButton();
		        	}
		            break;
		            
		        // When the user wants to zoom on the map using the key Z.
		        case KeyEvent.VK_Z:
		        	if (zoomEnable) {
		        		controller.getWindow().zoom();
		        		controller.getWindow().enableResetScaleButton();
		        	}
		        	break;
		        	
	        	// When the user wants to unzoom on the map using the key D.
		        case KeyEvent.VK_D:
		        	if(unZoomEnable) {
			        	controller.getWindow().unZoom();
			        	controller.getWindow().enableResetScaleButton();
		        	}
		        	break;
		     }
		}
		
		// Test if the CTRL key is pressed to know if it is a undo with a CTRL+Z or a redo with a CTRL+Y.
		if (keyCode== KeyEvent.VK_Z && ((e.getModifiers() & KeyEvent.CTRL_MASK)!=0 )) {
			controller.undo();
			
		} else if (keyCode == KeyEvent.VK_Y && ((e.getModifiers() & KeyEvent.CTRL_MASK)!=0 )) {
			controller.redo();
		} 
	}

	/**
	 * Enable shifting on the graphic view.
	 *
	 * @param shift the boolean used to enable or not the shift
	 */
	public void shiftEnable(boolean shift ) {
		shiftEnable = shift;
	}

	/**
	 * Enable zooming on the graphic view.
	 *
	 * @param zoom the boolean used to enable or not the zoom
	 */
	public void zoomEnable(boolean zoom) {
		zoomEnable = zoom;
	}

	/**
	 * Enable unzooming on the graphic view.
	 *
	 * @param unZoom the boolean used to enable or not the unzoom
	 */
	public void unZoomEnable(boolean unZoom) {
		unZoomEnable = unZoom;
	}
}
