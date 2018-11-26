package main.java.entity;


import java.util.*;

import javafx.util.Pair;
import java.lang.Math;
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
    protected List<ArrayList<Delivery>> cluster() throws ClusteringException {
    	
        // process Kmeansclustering multiple times and keep the best one
        
        
        return null;
    }
    
    protected List<ArrayList<Delivery>> KmeansClustering(List<Delivery> deliveryList) {
    	List<ArrayList<Delivery>> deliveriesDistribution;
    	
    	List<Pair<Double,Double>> barycenters = new ArrayList<Pair<Double,Double>>(nbDeliveryMan);
    	for (int barycenterIndex = 0 ; barycenterIndex < nbDeliveryMan ; barycenterIndex++) {
    		addRandomBarycenter(barycenters);
    	}
    	
    	List<ArrayList<Delivery>> newDistribution = KmeansClusteringStep(barycenters);
    	List<ArrayList<Delivery>> oldDistribution = newDistribution; //// MDR FAUT CHANGER CA !!!!!!!!!!!!!!!!!!!!!!!! CA VA BUGGER SALE DEBILE METNALT
    	// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH
    	// OUBLIE PAS D'ENLEVER CETTE CONNERIE
    	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH
    	    	
    	// FAIRE DES ETAPES DE CLUSTERING TANT QUE C'EST PAS SATISFAISANT EN RECALCULANT LES BARYCENTRES
    	while (evaluateCluster(newDistribution, oldDistribution)) {
    		
    		barycenters = calculateBarycenters(newDistribution);

    		oldDistribution = newDistribution;
    		newDistribution = KmeansClusteringStep(barycenters);
    	}
    	
    	
    	
    	// REEQUILIBRER LA DISTRIBUTION
    	return null;
    }
    
    protected List<ArrayList<Delivery>> KmeansClusteringStep(List<Pair<Double,Double>> barycenters) {
    	List<ArrayList<Delivery>> deliveriesDistribution = new ArrayList<ArrayList<Delivery>>(nbDeliveryMan);
    	for (Delivery currentDelivery : deliveryList) {
    		Double currentDistanceToBarycenter = Double.MAX_VALUE;
    		Integer currentDistributionIndex = 0;
    		for (Pair<Double,Double> barycenter : barycenters) {
    			Double latitudeDifference = currentDelivery.getPosition().getLatitude()-barycenter.getKey();
    			Double longitudeDifference = currentDelivery.getPosition().getLongitude()-barycenter.getValue();
    			Double newDistanceToBarycenter = Math.sqrt(latitudeDifference*latitudeDifference+longitudeDifference*longitudeDifference);
    			if (newDistanceToBarycenter<currentDistanceToBarycenter) {
    				currentDistributionIndex = barycenters.indexOf(barycenter);
    				currentDistanceToBarycenter = newDistanceToBarycenter;
    			}
    		}
    		// CA VA BUGGER DE OUF SALE DEBILE MENTALE CONGENITALE DE SA MERE LA PUTE        FAIS GAFFE AU GETINDEX?????????
    		deliveriesDistribution.get(currentDistributionIndex).add(currentDelivery);
    	}
    	return deliveriesDistribution;
    }
    
    protected List<Pair<Double,Double>> calculateBarycenters(List<ArrayList<Delivery>> currentDistribution){
    	List<Pair<Double,Double>> barycenters = new ArrayList<Pair<Double,Double>>(currentDistribution.size());
    	for (ArrayList<Delivery> deliveries : currentDistribution) {
    		
    		Double averageLatitude = (double)0;
    		Double averageLongitude = (double)0;
    		for (Delivery delivery : deliveries) {
    			averageLatitude += delivery.getPosition().getLatitude();
    			averageLongitude += delivery.getPosition().getLongitude();
    		}
    		averageLatitude = averageLatitude/(deliveries.size());
    		averageLongitude = averageLongitude/(deliveries.size());
    		
    		Pair<Double,Double> barycenter = new Pair<Double,Double>(averageLatitude,averageLongitude);
    		barycenters.add(barycenter);
    	}
    	return barycenters;
    }
    
    protected void addRandomBarycenter(List<Pair<Double,Double>> barycenters) {
    	Random r = new Random();
        Integer randomIndex = r.nextInt(nbDeliveryMan);
        Delivery randomDelivery = deliveryList.get(randomIndex);
        Pair<Double,Double> barycenter = new Pair<Double,Double>(randomDelivery.getPosition().getLatitude(),randomDelivery.getPosition().getLongitude());
    	if (!barycenters.contains(barycenter)) {
    		barycenters.add(barycenter);
    	} else {
    		addRandomBarycenter(barycenters);
    	}
    }
    
    protected boolean evaluateCluster(List<ArrayList<Delivery>> newDistribution, List<ArrayList<Delivery>> oldDistribution) {
    	if (newDistribution.equals(oldDistribution)) {
    		return true;
    	} else {
    		return false;
    	}
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
    public void calculateCircuits(int nbDeliveryman) throws MapNotChargedException, DeliveryListNotCharged, ClusteringException {
    	
    	if(nbDeliveryman>0){
    		this.nbDeliveryMan = nbDeliveryman;
    	}
    	List<Delivery>[] groupedDeliveries;
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