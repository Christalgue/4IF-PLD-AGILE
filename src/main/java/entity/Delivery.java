package main.java.entity;


import java.util.Calendar;
import java.util.Observable;

/**
 * The Class Delivery.
 */
public class Delivery extends Observable{

    /**
     * Default constructor.
     */
    public Delivery() {
    }
    
    /**
     * Instantiates a new delivery.
     *
     * @param position the position
     * @param duration the duration
     */
    public Delivery(Node position, int duration) {
    	this.position = position;
    	this.duration = duration;
    }

	/** The Node on which the Delivery is. */
    protected Node position;

    /** The duration. */
    protected int duration;

    /** The hour of arrival. */
    protected Calendar hourOfArrival;
    
	/** The hour of departure. */
    protected Calendar hourOfDeparture;
    

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Node getPosition() {
		return position;
	}
    

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Gets the hour of arrival.
	 *
	 * @return the hour of arrival
	 */
	public Calendar getHourOfArrival() {
		return hourOfArrival;
	}

	/**
	 * Sets the hour of arrival.
	 *
	 * @param hourOfArrival the new hour of arrival
	 */
	public void setHourOfArrival(Calendar hourOfArrival) {
		this.hourOfArrival = hourOfArrival;
		hourOfDeparture = (Calendar) hourOfArrival.clone();
		hourOfDeparture.add(Calendar.SECOND, duration);
	}

	/**
	 * Gets the hour of departure.
	 *
	 * @return the hour of departure
	 */
	public Calendar getHourOfDeparture() {
		return hourOfDeparture;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Delivery [position=" + position.getId() + ", duration=" + duration +"]";
	}
	
	
}