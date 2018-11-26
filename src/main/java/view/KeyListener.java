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
		//controller.keyPressed(e.getKeyCode());
	}

}
