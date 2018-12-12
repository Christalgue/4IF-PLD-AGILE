package main.java.view;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;

/**
 * The Class Main.
 */
public class Main {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		Controller controller = new Controller(new CircuitManagement());
		
	}

}
