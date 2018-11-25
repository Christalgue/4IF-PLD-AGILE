package main.java.view;

import main.java.entity.Circuit;

public class CircuitView {
	
	/**
     * Default constructor
     */
    protected CircuitView() {
    }
    
    /**
     * The color of the circuit
     */
    private String color;
    
    /**
     * The width of the circuit
     */
    private int width;
    
    /**
     * 
     * @param color The color of the circuit
     * @param width The width of the circuit
     */
    protected CircuitView(String color, int width) {
    	this.color = color;
    	this.width = width;
    }
    
    /**
     * 
     * @return The color of the circuit
     */
    protected String getColor() {
    	return color;
    }
    
    /**
     * 
     * @return The width of the circuit
     */
    protected int getWidth() {
    	return width;
    }
    
    /**
     * 
     * @param circuit The circuit to write in the Json
     */
    protected void toJson (Circuit circuit) {
    	
    }
}
