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
    private List<Circuit> circuitsList;

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
    
    public List<Circuit> getCircuitsList(){
    	return circuitsList;
    }
    
    
    public List<Delivery> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<Delivery> deliveryList) {
		this.deliveryList = deliveryList;
	}
	
	protected Repository getRepository() throws NoRepositoryException{
		for(Delivery delivery : this.deliveryList){
			if(delivery.getClass() == Delivery.class){
				return (Repository)delivery;
				
			}
		}
		throw new NoRepositoryException("CircuitManagement.java : problem when getting the repository in deliveryList");
	}

	public int getNbDeliveryMan() {
		return nbDeliveryMan;
	}

	public void setNbDeliveryMan(int nbDeliveryMan) {
		this.nbDeliveryMan = nbDeliveryMan;
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
    
    public List<ArrayList<Delivery>> KmeansClustering() {
    	
    	List<Pair<Double,Double>> barycenters = new ArrayList<Pair<Double,Double>>(nbDeliveryMan);
    	for (int barycenterIndex = 0 ; barycenterIndex < nbDeliveryMan ; barycenterIndex++) {
    		addRandomBarycenter(barycenters);
    	}
    	
    	System.out.println("Affichage des barycentres : ");
		for (Pair<Double,Double> barycenter : barycenters) {
			System.out.println("Barycentre : "+barycenter.getKey()+" (lat) / "+barycenter.getValue()+" (long)");
		}
		System.out.println("");
    	
    	List<ArrayList<Delivery>> newDistribution = KmeansClusteringStep(barycenters);
    	List<ArrayList<Delivery>> oldDistribution = null; //// COMMENT INITIALISER????
    	    	
    	while (evaluateCluster(newDistribution, oldDistribution)) {
    		
    		barycenters = calculateBarycenters(newDistribution);
    		
    		System.out.println("Affichage des barycentres : ");
    		for (Pair<Double,Double> barycenter : barycenters) {
    			System.out.println("Barycentre : "+barycenter.getKey()+" (lat) / "+barycenter.getValue()+" (long)");
    		}
    		System.out.println("");
    		
    		
    		oldDistribution = newDistribution;
    		newDistribution = KmeansClusteringStep(barycenters);
    		
    	}
    	
    	// REEQUILIBRER LA DISTRIBUTION
    	List<ArrayList<Delivery>> deliveriesDistribution = newDistribution;
    	return deliveriesDistribution;
    }
    
    public List<ArrayList<Delivery>> KmeansClusteringStep(List<Pair<Double,Double>> barycenters) {
    	List<ArrayList<Delivery>> deliveriesDistribution = new ArrayList<ArrayList<Delivery>>(nbDeliveryMan);
    	for(int distributionIndex = 0 ; distributionIndex < nbDeliveryMan ; distributionIndex++) {
    		ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    		deliveriesDistribution.add(deliveries);
    	}
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
    
    public void addRandomBarycenter(List<Pair<Double,Double>> barycenters) {
    	Random r = new Random();
        Integer randomIndex = r.nextInt(deliveryList.size());
        Delivery randomDelivery = deliveryList.get(randomIndex);
        System.out.println("Delivery prise : "+randomDelivery.getPosition().getId());
        Pair<Double,Double> barycenter = new Pair<Double,Double>(randomDelivery.getPosition().getLatitude(),randomDelivery.getPosition().getLongitude());
    	if (!barycenters.contains(barycenter)) {
    		barycenters.add(barycenter);
    	} else {
    		addRandomBarycenter(barycenters);
    	}
    }
    
    protected boolean evaluateCluster(List<ArrayList<Delivery>> newDistribution, List<ArrayList<Delivery>> oldDistribution) {
    	if (newDistribution.equals(oldDistribution)) {
    		return false;
    	} else {
    		return true;
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
     * @throws DijkstraException 
     * @throws NoRepositoryException 
     */
    public void calculateCircuits(int nbDeliveryman) throws MapNotChargedException, DeliveryListNotCharged, ClusteringException, DijkstraException, NoRepositoryException {
    	
    	if(nbDeliveryman>0){
    		this.nbDeliveryMan = nbDeliveryman;
    	}
    	List<ArrayList<Delivery>> groupedDeliveries;
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
    	if(groupedDeliveries.size()>0){
    		this.circuitsList = new ArrayList<Circuit>();
    		Repository repository = null;
    		HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths = new HashMap<Delivery,HashMap<Delivery,AtomicPath>>();
    		try {
				repository = getRepository();
			} catch (NoRepositoryException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				throw e1;
			}
    		if(repository != null){
    			allPaths.put(repository, this.currentMap.findShortestPath(repository, this.deliveryList));
    		}
    		for(List<Delivery> arrivalDeliveries : groupedDeliveries){
    			for(Delivery departureDelivery : arrivalDeliveries){
    				try {
						allPaths.put(departureDelivery, this.currentMap.findShortestPath(departureDelivery, arrivalDeliveries));
					} catch (DijkstraException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						throw e;
					}
    			}
    			this.circuitsList.add(new Circuit(arrivalDeliveries, allPaths));
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