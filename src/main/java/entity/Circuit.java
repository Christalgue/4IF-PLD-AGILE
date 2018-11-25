package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Circuit {

    /**
     * Default constructor
     */
    public Circuit() {
    }

    /**
     * Constructor
     * @param ListDelivery 
     * @param AtomicPath[][]
     */
    public Circuit(List<Delivery> deliveries, AtomicPath[][] allPaths) {
    	this.deliveryList = deliveries;
    	calculateTrackTSP(allPaths);
    }
    
    
    /**
     * 
     */
    private double circuitDuration;

    /**
     * 
     */
    private List<AtomicPath> path;

    /**
     * 
     */
    private List<Delivery> deliveryList;


    /**
     * 
     */
    protected void calculateDuration() {
        // TODO implement here
    }

    /**
     * @param AtomicPath[][]
     */
    protected void calculateTrackTSP(AtomicPath[][] allPaths) {
        // TODO implement here
    }

    /**
     * @param Map 
     * @param Delivery
     */
    protected void Remove(Delivery deliveryToRemove) {
        // TODO implement here
    }

    /**
     * @param Delivery 
     * @param nextToDelivery
     */
    protected void Add(Delivery deliveryToAdd, Delivery nextToDelivery) {
        // TODO implement here
    }

	protected double getCircuitDuration() {
		return circuitDuration;
	}

	protected void setCircuitDuration(double circuitDuration) {
		this.circuitDuration = circuitDuration;
	}

	protected List<AtomicPath> getPath() {
		return path;
	}

	private void setPath(List<AtomicPath> path) {
		this.path = path;
	}

	protected List<Delivery> getDeliveryList() {
		return deliveryList;
	}

	protected void setDeliveryList(List<Delivery> deliveryList) {
		this.deliveryList = deliveryList;
	}
    
    

}