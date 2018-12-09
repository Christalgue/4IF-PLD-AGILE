package main.java.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		if (e.getKeyCode()== KeyEvent.VK_Z && ((e.getModifiers() & KeyEvent.CTRL_MASK)!=0 )) {
			System.out.println("Ctrl z");
			controller.undo();
			
		} else if (e.getKeyCode()== KeyEvent.VK_Y && ((e.getModifiers() & KeyEvent.CTRL_MASK)!=0 )) {
			System.out.println("Ctrl y");
			controller.redo();
		} else {
			System.out.println("bonjour");
		}
		//controller.keyPressed(e.getKeyCode());
	}

}
