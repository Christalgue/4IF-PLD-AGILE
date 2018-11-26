package main.java.entity;


import java.util.*;

import main.java.exception.*;
import main.java.utils.Deserializer;

/**
 * 
 */
public class CircuitManagement extends Observable{

    /**
     * Default constructor
     */
    public CircuitManagement() {
    }

    /**
     * 
     */
    private Map currentMap;

    /**
     * 
     */
    private int nbDeliveryMan;

    /**
     * 
     */
    private Circuit[] circuitsList;

    /**
     * 
     */
    private List<Delivery> deliveryList;





    /**
     * @param filename 
     * @return
     * @throws LoadDeliveryException 
     */
    public void loadDeliveryList(String filename) throws LoadDeliveryException {
    	try {
    		this.deliveryList= new ArrayList<Delivery>(Deserializer.loadDeliveries(filename, this.currentMap));
		} catch (Exception e) {
			throw new LoadDeliveryException(e.getMessage());
		}
        
    }

    public Map getCurrentMap() {
    	return currentMap;
    }
    
    
    public List<Delivery> getDeliveryList() {
		return deliveryList;
	}

	protected void setDeliveryList(List<Delivery> deliveryList) {
		this.deliveryList = deliveryList;
	}

	/**
     * @param filename
     * @throws LoadMapException 
     */
    public void loadMap(String filename) throws LoadMapException {
        try {
			this.currentMap = new Map(filename);
		} catch (LoadMapException e) {
			throw e; 
		}
    }

    /**
     * K-means clustering algorithm
     * @return
     */
    protected List<Delivery>[] cluster() throws ClusteringException {
        // TODO implement here
        return null;
    }

    /**
     * generic method to call when calculation of the circuits is asked by GUI
     * call the cluster method and create the circuits one by one after having 
     *      calculated the atomic path between each delivery
     * @param nbDeliveryman
     * @throws MapNotChargedException 
     * @throws DeliveryListNotCharged 
     * @throws ClusteringException 
     * @throws DijkstraException 
     */
    public void calculateCircuits(int nbDeliveryman) throws MapNotChargedException, DeliveryListNotCharged, ClusteringException, DijkstraException {
    	
    	if(nbDeliveryman>0){
    		this.nbDeliveryMan = nbDeliveryman;
    	}
    	List<Delivery>[] groupedDeliveries;
    	//List<List<Delivery>> groupedDeliveries;
    	if(this.currentMap.getNodeMap().isEmpty()){
    		throw new MapNotChargedException("Impossible to calculate the circuits"
    				+ "if there is not any Map in the system");
    	} else if (this.deliveryList.isEmpty()){
    		throw new DeliveryListNotCharged("Impossible to calculate the circuits"
    				+ "if there is not any Delivery in the system");
    	} else {
    		try {
				groupedDeliveries = cluster();
			} catch (ClusteringException e) {
				throw e;
			}
    	}
    	if(groupedDeliveries.length>0){
    		this.circuitsList = new Circuit[groupedDeliveries.length];
    		HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPathes = new HashMap<Delivery,HashMap<Delivery,AtomicPath>>();
    		for(List<Delivery> arrivalDeliveries : groupedDeliveries){
    			for(Delivery departureDelivery : arrivalDeliveries){
    				try {
						HashMap<Delivery, AtomicPath> pairMap = this.currentMap.findShortestPath(departureDelivery, arrivalDeliveries);
					} catch (DijkstraException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						throw e;
					}
    			}
    		}
    	}
    	
    	// refactor multimap atomic path
    	/*if(groupedDeliveries.length>0){
    		this.circuitsList = new Circuit[groupedDeliveries.length];
    		for(int indexCircuits=0; indexCircuits<groupedDeliveries.length; indexCircuits++){
    			List<Delivery> deliveryList = groupedDeliveries[indexCircuits];
    			AtomicPath allPaths[][] = new AtomicPath[deliveryList.size()][deliveryList.size()];

    			for(int indexDeliveryStart=0; indexDeliveryStart< deliveryList.size(); indexDeliveryStart++){
    				Delivery start = deliveryList.get(indexDeliveryStart);
    				try {
						allPaths[indexDeliveryStart] = this.currentMap.findShortestPath(start, deliveryList);
					} catch (DijkstraException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			circuitsList[indexCircuits] = new Circuit(deliveryList, allPaths);
    		}
    	}*/
    }

	

}