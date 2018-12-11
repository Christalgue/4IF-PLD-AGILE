package main.java.entity;

import java.util.Calendar;

/**
 * The Class Repository. Used to check if a Delivery is the Repository before editing it because we don't want the repository to be editable.
 */
public class Repository extends Delivery {
    
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