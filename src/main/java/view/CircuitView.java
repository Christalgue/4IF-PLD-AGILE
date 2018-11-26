package main.java.view;

import main.java.entity.Circuit;
import java.awt.Color;

public class CircuitView {
	
	/**
     * Default constructor
     */
    public CircuitView() {
    }
    
    /**
     * The color of the circuit
     */
    private Color color;
    
    /**
     * The width of the circuit
     */
    private int width;
    
    /**
     * 
     * @param color The color of the circuit
     * @param width The width of the circuit
     */
    public CircuitView(Color color, int width) {
    	this.color = color;
    	this.width = width;
    }
    
    /**
     * 
     * @return The color of the circuit
     */
    protected Color getColor() {
    	return color;
    }
    
    /**
     * 
     * @return The width of the circuit
     */
    protected int getWidth() {
    	return width;
    }
}
