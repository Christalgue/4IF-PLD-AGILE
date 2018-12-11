package main.java.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.plaf.synth.SynthSeparatorUI;

import main.java.controller.Controller;

public class KeyListener extends KeyAdapter {

	private Controller controller;

	public KeyListener(Controller controller){
		this.controller = controller;
	}
	
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
