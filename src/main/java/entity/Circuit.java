package main.java.entity;


import java.util.*;
import main.java.tsp.*;

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
     * Constructor
     * @param repository TODO
     * @param ListDelivery 
     * @param AtomicPath[][]
     */
    public Circuit(List<Delivery> deliveries, Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) {
    	this.deliveryList = deliveries;
    	calculateTrackTSP(repository, allPaths);
    	this.circuitLength = calculateLength();
    }

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
     * @param repository 
     * @param AtomicPath[][]
     */
    protected void calculateTrackTSP(Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) {
        TSP1 tsp = new TSP1();
        tsp.searchSolution(Integer.MAX_VALUE, repository, allPaths, null);
        Delivery bestSolution[];
        bestSolution = tsp.getBestSolution();
        List<Delivery> deliveriesOrdered;
        for(int indexBestSolution = 0; indexBestSolution < bestSolution.length; indexBestSolution++){
        	//deliveriesOrdered
        }
        
    	
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

	public List<AtomicPath> getPath() {
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