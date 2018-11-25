package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Delivery {

    /**
     * Default constructor
     */
    public Delivery() {
    }
    
    /**
     * 
     */
    public Delivery(Node position, int duration) {
    	this.position = position;
    	this.duration = duration;
    }

    /**
     * 
     */
    private Node position;

    /**
     * 
     */
    private int duration;

    /**
     * 
     */
    private Calendar hourOfArrival;
    
	/**
     * 
     */
    private Calendar hourOfDeparture;
    

    public Node getPosition() {
		return position;
	}
    

	public int getDuration() {
		return duration;
	}

	public Calendar getHourOfArrival() {
		return hourOfArrival;
	}

	public void setHourOfArrival(Calendar hourOfArrival) {
		this.hourOfArrival = hourOfArrival;
	}

	public Calendar getHourOfDeparture() {
		return hourOfDeparture;
	}

	public void setHourOfDeparture(Calendar hourOfDeparture) {
		this.hourOfDeparture = hourOfDeparture;
	}
}