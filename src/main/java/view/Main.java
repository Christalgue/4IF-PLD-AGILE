package main.java.view;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;

public class Main {
	
	public static void main(String[] args) {
		
		Controller controller = new Controller(new CircuitManagement());
		
	}

}
