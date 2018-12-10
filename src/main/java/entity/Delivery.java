package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Delivery extends Observable{

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
    protected Node position;

    /**
     * 
     */
    protected int duration;

    /**
     * 
     */
    protected Calendar hourOfArrival;
    
	/**
     * 
     */
    protected Calendar hourOfDeparture;
    

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
		hourOfDeparture = (Calendar) hourOfArrival.clone();
		hourOfDeparture.add(Calendar.SECOND, duration);
	}

	public Calendar getHourOfDeparture() {
		return hourOfDeparture;
	}

	@Override
	public String toString() {
		return "Delivery [position=" + position.getId() + ", duration=" + duration +"]";
	}
	
	
}