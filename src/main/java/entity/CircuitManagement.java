package main.java.entity;


import java.util.*;

import main.java.exception.*;
import main.java.utils.Deserializer;

/**
 * 
 */
public class CircuitManagement {

    /**
     * Default constructor
     */
    public CircuitManagement() {
    }

    /**
     * 
     */
    private Map actualMap;

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
     * 
     */
    private Deserializer chargingUnit;





    /**
     * @param filename 
     * @return
     * @throws LoadDeliveryException 
     */
    protected void loadDeliveryList(String filename) throws LoadDeliveryException {
    	try {
    		this.chargingUnit.loadDeliveries(filename, this.deliveryList, this.actualMap);
		} catch (Exception e) {
			throw new LoadDeliveryException(e.getMessage());
		}
        
    }

    /**
     * @param filename
     * @throws LoadMapException 
     */
    protected void loadMap(String filename) throws LoadMapException {
        try {
			this.actualMap = new Map(filename);
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
     */
    protected void calculateCircuits(int nbDeliveryman) throws MapNotChargedException, DeliveryListNotCharged, ClusteringException {
    	
    	if(nbDeliveryman>0){
    		this.nbDeliveryMan = nbDeliveryman;
    	}
    	List<Delivery>[] groupedDeliveries;
    	if(this.actualMap.getNodeMap().isEmpty()){
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
    		for(int indexCircuits=0; indexCircuits<groupedDeliveries.length; indexCircuits++){
    			List<Delivery> deliveryList = groupedDeliveries[indexCircuits];
    			AtomicPath allPaths[][] = new AtomicPath[deliveryList.size()][deliveryList.size()];

    			for(int indexDeliveryStart=0; indexDeliveryStart< deliveryList.size(); indexDeliveryStart++){
    				Delivery start = deliveryList.get(indexDeliveryStart);
    				try {
						allPaths[indexDeliveryStart] = this.actualMap.findShortestPath(start, deliveryList);
					} catch (DijkstraException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			circuitsList[indexCircuits] = new Circuit(deliveryList, allPaths);
    		}
    	}
    }

	

}