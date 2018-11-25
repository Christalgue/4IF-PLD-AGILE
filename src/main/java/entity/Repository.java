package main.java.entity;

import java.util.*;

/**
 * 
 */
public class Repository extends Delivery {

    /**
     * Default constructor
     */
    public Repository() {
    }
    
    public Repository(Node position, Calendar hourOfDeparture) {
    	this.position = position;
    	this.duration = 0;
    	this.hourOfDeparture = hourOfDeparture;
    	this.hourOfArrival = hourOfDeparture;
    }

}