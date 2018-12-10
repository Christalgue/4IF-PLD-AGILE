package main.java.entity;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Repository.
 */
public class Repository extends Delivery {

    /**
     * Default constructor.
     */
    public Repository() {
    }
    
    /**
     * Instantiates a new repository.
     *
     * @param position the position
     * @param hourOfDeparture the hour of departure
     */
    public Repository(Node position, Calendar hourOfDeparture) {
    	this.position = position;
    	this.duration = 0;
    	this.hourOfDeparture = hourOfDeparture;
    	this.hourOfArrival = hourOfDeparture;
    }

}