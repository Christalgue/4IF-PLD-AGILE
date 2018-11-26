package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Circuit extends Observable{

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
    	this.circuitLength = calculateLength();
    }
    
    
    /**
     * 
     */
    private double circuitLength;

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
    protected double calculateLength() {
        // TODO implement here
    	double result = 0;
    	for(AtomicPath segment : this.path){
    		result += segment.getLength();
    	}
    	return result;
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

	protected double getCircuitLength() {
		return circuitLength;
	}

	protected void setCircuitLength(double circuitLength) {
		this.circuitLength = circuitLength;
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