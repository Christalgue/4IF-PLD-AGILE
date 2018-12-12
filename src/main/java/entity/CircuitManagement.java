package main.java.entity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javafx.util.Pair;
import main.java.exception.DeliveriesNotLoadedException;
import main.java.exception.DijkstraException;
import main.java.exception.ForgivableXMLException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.ManagementException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.Deserializer;

// TODO: Auto-generated Javadoc
/**
 * The Class CircuitManagement is used as an entry point into the model for the controller. It manages all the model (load the map, load the delivery list create the circuits)
 */
public class CircuitManagement extends Observable{

    /** The current map. */
    private Map currentMap;

    /** The number of delivery-men. The default value is 3*/
    private int nbDeliveryMan = 3;

    /** The circuits list. */
    private List<Circuit> circuitsList;

    /** The delivery list. */
    private List<Delivery> deliveryList;





    /**
     * Load delivery list.
     *
     * @param filename the path to the file containing all deliveries
     * @throws LoadDeliveryException when the deserializer couldn't properly load the delivery list 
     */
    public void loadDeliveryList(String filename) throws LoadDeliveryException {
    	try {
    		this.deliveryList= new ArrayList<Delivery>(Deserializer.loadDeliveries(filename, this.currentMap));
		} catch (Exception e) {
			throw new LoadDeliveryException(e.getMessage());
		}
        
    }

    /**
     * Gets the current map.
     *
     * @return the current map
     */
    public Map getCurrentMap() {
    	return currentMap;
    }
    
    /**
	 * Sets the circuits list.
	 *
	 * @param circuitsList the new circuits list
	 */
	public void setCircuitsList(List<Circuit> circuitsList) {
		this.circuitsList = circuitsList;
	}
    
    /**
     * Gets the circuits list.
     *
     * @return the circuits list
     */
    public List<Circuit> getCircuitsList(){
    	return circuitsList;
    }
    
    
    /**
     * Gets the delivery list.
     *
     * @return the delivery list
     */
    public List<Delivery> getDeliveryList() {
		return deliveryList;
	}

	/**
	 * Sets the delivery list.
	 *
	 * @param deliveryList the new delivery list
	 */
	public void setDeliveryList(List<Delivery> deliveryList) {
		this.deliveryList = deliveryList;
	}
	
	/**
	 * Gets the repository.
	 *
	 * @return the repository
	 * @throws NoRepositoryException when no repository is found in the delivery list
	 */
	protected Repository getRepository() throws NoRepositoryException{
		for(Delivery delivery : this.deliveryList){
			if(delivery.getClass() == Repository.class){
				return (Repository)delivery;
				
			}
		}
		throw new NoRepositoryException("CircuitManagement.java : problem when getting the repository in deliveryList");
	}

	/**
	 * Gets the number of delivery-men.
	 *
	 * @return the number of delivery-men.
	 */
	public int getNbDeliveryMan() {
		return nbDeliveryMan;
	}

	/**
	 * Sets the number delivery-men.
	 *
	 * @param nbDeliveryMan the new number delivery-men
	 */
	public void setNbDeliveryMan(int nbDeliveryMan) {
		this.nbDeliveryMan = nbDeliveryMan;
	}

	/**
	 * Load map. (load all nodes and bows)
	 *
	 * @param filename the filename
	 * @throws LoadMapException the load map exception
	 * @throws ForgivableXMLException when a problem occurred while loading the map but without avoiding it to be created
	 */
    public void loadMap(String filename) throws LoadMapException, ForgivableXMLException {
    	Map map = new Map();
        try {
        	map.load(filename);
            currentMap = map;
		} catch (ForgivableXMLException e) {
			currentMap = map;
			throw e; 
		} catch (LoadMapException e) {
			throw e; 
		}
    }

    /**
     * Clustering algorithm, performing K-means clustering algorithm and if the
     * clusters are unbalanced in terms of cardinality, balance those clusters.
     *
     * @return a list containing all clusters
     * @throws NoRepositoryException when no repository is found in the delivery list
     */
    public List<ArrayList<Delivery>> cluster() throws NoRepositoryException {
    	
        // process the Kmeansclustering method multiple times and keep the best one
    	
    	List<Delivery> deliveriesToDistribute = new ArrayList<Delivery>(deliveryList);
    	try {
			deliveriesToDistribute.remove(getRepository());
		} catch (NoRepositoryException e) {
			e.printStackTrace();
			throw new NoRepositoryException("CircuitManagement.java : no repository found in cluster()");
		}
    	
    	Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> distribution = KmeansClustering(nbDeliveryMan, deliveriesToDistribute);
    	// Balance the distribution
    	if (!checkClusterValidity(distribution.getKey())) {
    		distribution = balanceDistribution(distribution);
    	}
  	    	
		// Add repository to each circuit
		for (ArrayList<Delivery> cluster : distribution.getKey()) {
			try {
				cluster.add(getRepository());
			} catch (NoRepositoryException e) {
				e.printStackTrace();
				throw new NoRepositoryException("CircuitManagement.java : no repository found in cluster()");
			}
		} 
        return distribution.getKey();
    }
    
    /**
     * K-means clustering algorithm. 
     *
     * @param numberOfClusters the expected number of clusters
     * @param deliveriesToCluster all deliveries to partition in clusters
     * @return a pair containing the clusters and their respective barycenters.
     */
    public Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> KmeansClustering(int numberOfClusters, List<Delivery> deliveriesToCluster) {
    	
    	List<Pair<Double,Double>> barycenters = new ArrayList<Pair<Double,Double>>(numberOfClusters);
    	for (int barycenterIndex = 0 ; barycenterIndex < numberOfClusters ; barycenterIndex++) {
    		addRandomBarycenter(barycenters, deliveriesToCluster);
    	}
    	
    	List<ArrayList<Delivery>> newDistribution = KmeansClusteringStep(barycenters, deliveriesToCluster);
    	List<ArrayList<Delivery>> oldDistribution = null; //// HOW TO INITIALIZE????
    	    	
    	while (evaluateCluster(newDistribution, oldDistribution)) {
    		
    		barycenters = calculateBarycenters(newDistribution);
    		
    		oldDistribution = newDistribution;
    		newDistribution = KmeansClusteringStep(barycenters, deliveriesToCluster);
    	}
    	
    	Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> Distribution = new Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>>(newDistribution, barycenters);
    	
    	return Distribution;
    }
    
    /**
     * Associates all deliveries to their closest barycenter and updates all barycenters.
     * Each set of association to a barycenter results in a new cluster.
     *
     * @param barycenters the barycenters of the distribution
     * @param deliveriesToCluster all deliveries to partition in clusters
     * @return the list containing all clusters
     */
    public List<ArrayList<Delivery>> KmeansClusteringStep(List<Pair<Double,Double>> barycenters, List<Delivery> deliveriesToCluster) {
    	
    	List<ArrayList<Delivery>> deliveriesDistribution = new ArrayList<ArrayList<Delivery>>(barycenters.size());
    	for(int distributionIndex = 0 ; distributionIndex <  barycenters.size(); distributionIndex++) {
    		ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    		deliveriesDistribution.add(deliveries);
    	}
    	for (Delivery currentDelivery : deliveriesToCluster) {
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
    
    /**
     * Calculate new barycenters based on a distribution of deliveries in clusters.
     *
     * @param currentDistribution the current clustered distribution
     * @return a list containing all barycenters
     */
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
    
    /**
     * Adds a random barycenter.
     *
     * @param barycenters the list containing all barycenters
     * @param deliveries the list containing all deliveries
     */
    public void addRandomBarycenter(List<Pair<Double,Double>> barycenters, List<Delivery> deliveries) {
    	Random r = new Random();
        Integer randomIndex = r.nextInt(deliveries.size());
        Delivery randomDelivery = deliveries.get(randomIndex);
        Pair<Double,Double> barycenter = new Pair<Double,Double>(randomDelivery.getPosition().getLatitude(),randomDelivery.getPosition().getLongitude());
    	if (!barycenters.contains(barycenter)) {
    		barycenters.add(barycenter);
    	} else {
    		addRandomBarycenter(barycenters, deliveries);
    	}
    }
    
    /**
     * Evaluate a clustered distribution validity.
     * Checks if old and new distribution are different (return false if so).
     *
     * @param newDistribution the new clustered distribution
     * @param oldDistribution the old clustered distribution
     * @return true, if both distribution are equal
     */
    protected boolean evaluateCluster(List<ArrayList<Delivery>> newDistribution, List<ArrayList<Delivery>> oldDistribution) {
    	if (newDistribution.equals(oldDistribution)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    /**
     * Balance the clustered distribution.
     * Takes the most distant cluster and adds/remove deliveries until its cardinality is valid.
     * Then performs Kmeans algorithm on the rest of the deliveries.
     * Repeats those two steps until the distribution is full.
     *
     * @param distributionToBalance the distribution to balance, which contains all the clusters and their respective barycenters
     * @return the balanced clusters and their barycenters
     */
    protected Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> balanceDistribution(Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> distributionToBalance) {
    	List<ArrayList<Delivery>> balancedClusters = new ArrayList<ArrayList<Delivery>>();
    	List<Pair<Double,Double>> balancedBarycenters = new ArrayList<Pair<Double,Double>>();
    	Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> balancedDistribution = 
    			new Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>>(balancedClusters, balancedBarycenters);
    	
    	Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> distribution = distributionToBalance;
    	
    	while (balancedDistribution.getKey().size()!=distributionToBalance.getKey().size()) {
    		
    		Pair<ArrayList<Delivery>,List<Delivery>> clusterAndRestOfDeliveries = balanceMostDistantCluster(distribution.getKey(), distribution.getValue());
        	ArrayList<Delivery> cluster = clusterAndRestOfDeliveries.getKey();
        	List<Delivery> restOfDeliveries = clusterAndRestOfDeliveries.getValue();
        	
        	balancedDistribution.getKey().add(cluster);
        	balancedDistribution.getValue().add(new Pair<Double,Double>(0.0,0.0)); // TODO A changer
        	
        	// Make another Kmeansclustering on the rest of the deliveries
        	distribution = KmeansClustering(distributionToBalance.getKey().size()-balancedDistribution.getKey().size(), restOfDeliveries);
    	}
    	
    	return balancedDistribution;
    }
    
    /**
     * Return the most distant cluster from the center of all deliveries.
     *
     * @param distributionToBalance the current distribution of clusters
     * @param barycenters the barycenters of the current distribution
     * @return a pair containing the most distant cluster and the rest of the deliveries
     */
    protected Pair<ArrayList<Delivery>,List<Delivery>> balanceMostDistantCluster(List<ArrayList<Delivery>> distributionToBalance, List<Pair<Double,Double>> barycenters) {
    	    	
    	// calculate most distant barycenter (from the global barycenter) and the global barycenter
    	Pair<Pair<Double,Double>,Pair<Double,Double>> globalAndMostDistantBarycenters = calculateGlobalAndMostDistantBarycenter(distributionToBalance, barycenters);
    	Pair<Double,Double> mostDistantBarycenter = globalAndMostDistantBarycenters.getKey();
    	Pair<Double,Double> globalBarycenter = globalAndMostDistantBarycenters.getValue();
    	
    	// get most distant cluster from the center of all deliveries
    	ArrayList<Delivery> clusterToBalance = distributionToBalance.get(barycenters.indexOf(mostDistantBarycenter));



    	
    	// Build rest of deliveries for next iteration
    	List<Delivery> restOfDeliveries = new ArrayList<Delivery>();
    	
    	for (List<Delivery> cluster : distributionToBalance) {
    		for (Delivery delivery : cluster) {
    			if (!clusterToBalance.contains(delivery)) {
    				restOfDeliveries.add(delivery);
    			}
    		}
    	}
    	
    	Integer bottomAverageNumberOfDeliveries = (clusterToBalance.size()+restOfDeliveries.size())/distributionToBalance.size();
    	Integer topAverageNumberOfDeliveries = bottomAverageNumberOfDeliveries;
    	
    	if ((clusterToBalance.size()+restOfDeliveries.size())%distributionToBalance.size()!=0) {
    		topAverageNumberOfDeliveries += 1;
    	} 	
    	
    	if (clusterToBalance.size()>topAverageNumberOfDeliveries) {
    		// Remove closest deliveries to center of all deliveries
    		int numberOfDeliveriesToRemove = clusterToBalance.size()-topAverageNumberOfDeliveries;
    		for(int deliveryToRemoveIndex = 0 ; deliveryToRemoveIndex < numberOfDeliveriesToRemove ; deliveryToRemoveIndex++) {
    			removeDeliveryFromCluster(clusterToBalance,globalBarycenter,restOfDeliveries);
    		}
    	} else if (clusterToBalance.size()<bottomAverageNumberOfDeliveries) {
    		// Add closest deliveries to cluster (from remaining deliveries)
    		int numberOfDeliveriesToAdd = bottomAverageNumberOfDeliveries-clusterToBalance.size();
    		for (int deliveryToAddIndex = 0 ; deliveryToAddIndex < numberOfDeliveriesToAdd ; deliveryToAddIndex++) {
    			addDeliveryToCluster(clusterToBalance,mostDistantBarycenter,restOfDeliveries);
    		}
    	}
    	
    	Pair<ArrayList<Delivery>,List<Delivery>> clusterAndRestOfDeliveries = new Pair<ArrayList<Delivery>,List<Delivery>>(clusterToBalance,restOfDeliveries);
    	return clusterAndRestOfDeliveries;
    }
    
    // TODO more explanations with comments in the method about how it works ?
    /**
     * Calculates the distances between all barycenter and the center of all deliveries in order to return the most distant cluster.
     * 
     * @return the most distant barycenter and the center of all deliveries
     */
    protected Pair<Pair<Double,Double>,Pair<Double,Double>> calculateGlobalAndMostDistantBarycenter(List<ArrayList<Delivery>> distribution, List<Pair<Double,Double>> barycenters) {
    	
    	Double globalLatitude = (double)0;
    	Double globalLongitude = (double)0;
    	int numberOfDeliveries = 0;
    	for (ArrayList<Delivery> cluster : distribution) {
    		numberOfDeliveries += cluster.size();
    		for (Delivery delivery : cluster) {
    			globalLatitude += delivery.getPosition().getLatitude();
        		globalLongitude += delivery.getPosition().getLongitude();
    		}
    	}
    	globalLatitude = globalLatitude/numberOfDeliveries;
    	globalLongitude = globalLongitude/numberOfDeliveries;
    	Pair<Double,Double> globalBarycenter = new Pair<Double,Double>(globalLatitude,globalLongitude);
    	

    	Double greatestBarycentersDistance = (double)0;
    	Pair<Double,Double> mostDistantBarycenter = null;
    	for (Pair<Double,Double> currentBarycenter : barycenters) {
    		Double currentBarycenterLatitudeDifference = currentBarycenter.getKey()-globalBarycenter.getKey();
    		Double currentBarycenterLongitudeDifference = currentBarycenter.getValue()-globalBarycenter.getValue();
    		Double currentBarycenterDistance = Math.sqrt(currentBarycenterLatitudeDifference*currentBarycenterLatitudeDifference
    				+currentBarycenterLongitudeDifference*currentBarycenterLongitudeDifference);
    		if (currentBarycenterDistance>=greatestBarycentersDistance) {
    			greatestBarycentersDistance = currentBarycenterDistance;
    			mostDistantBarycenter = currentBarycenter;
    		}
    	}

    	Pair<Pair<Double,Double>,Pair<Double,Double>> globalAndMostDistantBarycenters = new Pair<Pair<Double,Double>,Pair<Double,Double>>(mostDistantBarycenter,globalBarycenter);
    	return globalAndMostDistantBarycenters;
    }
    
    /**
     * Adds a delivery to the cluster.
     * The delivery is selected according to its closeness to the cluster (actually to the barycenter)
     *
     * @param cluster the current cluster to balance
     * @param barycenters the all barycenters of the current distribution
     * @param restOfDeliveries the rest of deliveries
     */
    protected void addDeliveryToCluster(List<Delivery> cluster, Pair<Double,Double> barycenters, List<Delivery> restOfDeliveries) {
    	// get closest delivery to the barycenter
    	Double closestTotalDistance = Double.MAX_VALUE;
    	Delivery deliveryToAdd = null;
    	for (Delivery currentDelivery : restOfDeliveries) {
    		Double currentLatitudeDifference = barycenters.getKey()-currentDelivery.getPosition().getLatitude();
    		Double currentLongitudeDifference = barycenters.getValue()-currentDelivery.getPosition().getLongitude();
    		Double currentTotalDistance = Math.sqrt(currentLatitudeDifference*currentLatitudeDifference+currentLongitudeDifference*currentLongitudeDifference);
    		if (currentTotalDistance<closestTotalDistance) {
    			deliveryToAdd = currentDelivery;
    			closestTotalDistance = currentTotalDistance;
    		}
    	}
    	
    	// add the closest delivery to the cluster
    	cluster.add(deliveryToAdd);
    	restOfDeliveries.remove(deliveryToAdd);
    }
    
    /**
     * Remove a delivery from the cluster.
     * The delivery must be the closest to the center of all the deliveries.
     */
    protected void removeDeliveryFromCluster(List<Delivery> cluster, Pair<Double,Double> barycenter, List<Delivery> restOfDeliveries) {
    	// get closest delivery to the barycenter
    	Double closestTotalDistance = Double.MAX_VALUE;
    	Delivery deliveryToRemove = null;
    	for (Delivery currentDelivery : cluster) {
    		Double currentLatitudeDifference = barycenter.getKey()-currentDelivery.getPosition().getLatitude();
    		Double currentLongitudeDifference = barycenter.getValue()-currentDelivery.getPosition().getLongitude();
    		Double currentTotalDistance = Math.sqrt(currentLatitudeDifference*currentLatitudeDifference+currentLongitudeDifference*currentLongitudeDifference);
    		if (currentTotalDistance<closestTotalDistance) {
    			deliveryToRemove = currentDelivery;
    			closestTotalDistance = currentTotalDistance;
    		}
    	}
    	
    	// add the closest delivery to the cluster
    	cluster.remove(deliveryToRemove);
    	restOfDeliveries.add(deliveryToRemove);
    }
    
    /**
     * Check if distribution has same-size clusters.
     * @return the validity of all cluster's cardinality.
     */
    protected boolean checkClusterValidity (List<ArrayList<Delivery>> clustersToCheck) {
    	Integer bottomAverageNumberOfDeliveries = (deliveryList.size()-1)/nbDeliveryMan;
    	Integer topAverageNumberOfDeliveries = bottomAverageNumberOfDeliveries+1;
    	boolean isValid = true;
    	for (ArrayList<Delivery> cluster : clustersToCheck) {
    		if (cluster.size() != bottomAverageNumberOfDeliveries && cluster.size() != topAverageNumberOfDeliveries) {
    			isValid = false;
    		}
    	}
    	return isValid;
    }

    /**
     * generic method to call when calculation of the circuits is asked by HCI
     * call the cluster method and create the circuits one by one after having 
     *      calculated the atomic path between each delivery.
     *
     * @param nbDeliveryman the number delivery-men
     * @param continueInterruptedCalculation true if the method is called after having interrupted the calculation at least once
     * @throws MapNotChargedException when the map is not properly charged
     * @throws DeliveriesNotLoadedException when the delivery list is not properly charged
     * @throws DijkstraException when a problem happened during the dijkstra algorithm
     * @throws NoRepositoryException when no repository is found in the delivery list
     * @throws TSPLimitTimeReachedException when the limit time we set for the calculation is reached before the end of the calculation 
     */
    public void calculateCircuits(int nbDeliveryman, boolean continueInterruptedCalculation) throws MapNotChargedException, DijkstraException, NoRepositoryException, TSPLimitTimeReachedException, DeliveriesNotLoadedException {
    	
    	if(continueInterruptedCalculation == false) {
    		this.circuitsList = null;
        	if(nbDeliveryman>0){
        		this.nbDeliveryMan = nbDeliveryman;
        	}
        	List<ArrayList<Delivery>> groupedDeliveries;
        	if(this.currentMap.getNodeMap().isEmpty()){
        		throw new MapNotChargedException("Impossible to calculate the circuits if there is not any Map in the system");
        	} else if (this.deliveryList.isEmpty()){
        		throw new DeliveriesNotLoadedException("Impossible to calculate the circuits if there is not any Delivery in the system");
        	} else {
        		try {
    				groupedDeliveries = cluster();
    			} catch (NoRepositoryException e) {
    				throw e;
    			}
        	}
        	//check if there is more than 0 clusters
        	if(groupedDeliveries.size()>0){
        		this.circuitsList = new ArrayList<Circuit>();
        		Repository repository = null;
        		//create the HashMap containing all the paths between the repository and each delivery. This hashmap will be used to initialize the hashmap of each circuit
        		HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPathsWithJustRepository = new HashMap<Delivery,HashMap<Delivery,AtomicPath>>();
        		try {
    				repository = getRepository();
    			} catch (NoRepositoryException e1) {
    				throw e1;
    			}
        		if(repository != null){
        			allPathsWithJustRepository.put(repository, this.currentMap.findShortestPath(repository, this.deliveryList));
        		}
        		//for each cluster it creates a hashmap containing each delivery associated to an hashmap containing all the deliveries that could be accessed from
        		//the first delivery, and the AtomicPath to go there.
        		for(List<Delivery> arrivalDeliveries : groupedDeliveries){
        			HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths = new HashMap<Delivery,HashMap<Delivery,AtomicPath>>(allPathsWithJustRepository);
        			for(Delivery departureDelivery : arrivalDeliveries){
        				try {
        					if (departureDelivery!=repository) {
        						allPaths.put(departureDelivery, this.currentMap.findShortestPath(departureDelivery, arrivalDeliveries));
        					}
    					} catch (DijkstraException e) {
    						throw e;
    					}
        			}
        			Circuit circuit = new Circuit(arrivalDeliveries, repository, allPaths, 15.0/3.6);
        			this.circuitsList.add(circuit);
        		}
        		//calculate each circuit after having created the instances to avoid the nullpointer exceptions in the view.
        		for(Circuit circuit : this.circuitsList) {
        			try {
        				circuit.createCircuit();
    				} catch (TSPLimitTimeReachedException e) {
    					throw e;
    				} 
        		}
        	}
    	} else {
    		//if we are continuing the calculation we check for each circuit if its calculation is finished and if not we continue it.
    		for(Circuit circuitTested : this.circuitsList)
    		{
    			if(circuitTested.calculationIsFinished == false) {
    				circuitTested.continueCalculation();
    			}
    		}
    	}
    	
    }

	/**
	 * Check if a node is contained in the delivery list.
	 *
	 * @param nodeTested the node tested
	 * @return true, if successful
	 */
	public boolean checkNodeInDeliveryList(Node nodeTested){
		for(Delivery deliveryTested : this.deliveryList){
			if(deliveryTested.getPosition() == nodeTested)
				return true;
		}
		return false;
	}
	
	/**
	 * Gets the index of the circuit that contains a delivery using the delivery to identify it.
	 *
	 * @param delivery the delivery
	 * @return the index of the circuit containing the delivery. -1 if no circuit contains it.
	 */

	public int getCircuitIndexByDelivery( Delivery delivery) {
		
		int circuitIndex =0;
		if ( circuitsList != null) {
			for(Circuit circuitTested : this.circuitsList){
				for ( Delivery deliveryTested : circuitTested.getDeliveryList()) {
					if ( deliveryTested.getPosition() == delivery.getPosition())
						return circuitIndex;
				}
				circuitIndex++;
			}
		}
		return -1;
		
	}
	
	/**
	 * Gets the index of the circuit that contains a node using the node to identify it
	 *
	 * @param delivery the delivery
	 * @return the index of the circuit containing the nodes. -1 if no circuit contains it.
	 */
	public int getCircuitIndexByNode ( Delivery delivery) {
		int circuitIndex =0;
		for(Circuit circuitTested : this.circuitsList){		
			for ( AtomicPath path : circuitTested.getPath()) {
				if(path != null) {
					for ( Bow bow : path.getPath()) {
						if (bow.getStartNode() != null && bow.getStartNode()== delivery.getPosition())
							return circuitIndex;
					}
				}
			}
			circuitIndex++;
		}
		return -1;
	}
	
	/**
	 * Gets the circuit using its index in the circuits lists.
	 *
	 * @param index the index
	 * @return the circuit if found, null if not
	 */
	public Circuit getCircuitByIndex( int index) {
		
		int circuitIndex =0;
		
		for(Circuit circuitTested : this.circuitsList){
			if(circuitIndex == index ) {
				return circuitTested;
			}
			circuitIndex++;
		}
		
		return null;
	}

	
	/**
	 * Gets the delivery's index in the delivery list.
	 *
	 * @param delivery the delivery
	 * @return the delivery's index or -1 if it's not contained in the delivery list
	 */
	public int getDeliveryIndex (Delivery delivery) {
		
		int deliveryIndex =0;
		
		for(Delivery deliveryTested : this.deliveryList){
			if(deliveryTested.getPosition() == delivery.getPosition()) {
				return deliveryIndex;
			}
			deliveryIndex++;
		}
		
		return -1;
	}
	
	/**
	 * Gets the delivery using its index in the delivery lists.
	 *
	 * @param index the index
	 * @return the delivery or null if there is no delivery at this index
	 */
	public Delivery getDeliveryByIndex (int index) {
		
		int deliveryIndex =0;
		
		for(Delivery deliveryTested : this.deliveryList){
			if(deliveryIndex == index ) {
				return deliveryTested;
			}
			deliveryIndex++;
		}
		
		return null;
	}
	
	// TODO merging with getDeliveryByNode ?
	/**
	 * Checks if a node is also a delivery.
	 *
	 * @param nodeTested the node tested
	 * @return the delivery at this node if there is one, TODO What the fuck is that return if the node is not a delivery ?
	 */
	public Delivery isDelivery(Node nodeTested){
		for(Delivery deliveryTested : this.deliveryList){
			if(deliveryTested.getPosition() == nodeTested)
				return deliveryTested;
		}
		return new Delivery (nodeTested, -1);
	}
	
	
	/**
	 * Checks if a node is also a the repository.
	 *
	 * @param nodeTested the node tested
	 * @return true if its the repository, false if not
	 */
	public boolean isRepository (Node nodeTested){
		try {
			if(getRepository().getPosition() == nodeTested) {
				return true;
			} else {
				return false;
			}
		} catch (NoRepositoryException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Adds the delivery after the delivery present at the selected node in the circuit that contains it. It does it by calling the generic add method specifying
	 * that we want to modify the delivery list
	 *
	 * @param nodeDelivery the node where the new delivery will be
	 * @param duration the duration of the delivery
	 * @param previousNode the previous node
	 */
	public void addDelivery (Node nodeDelivery, int duration, Node previousNode) {
		addDelivery(nodeDelivery, duration, previousNode, true);
	}
	
	/**
	 * Generic method to add a delivery at the node selected after the previous node selected, precising if we want to change the delivery list or not by doing so.
	 *
	 * @param nodeDelivery the node delivery
	 * @param duration the duration
	 * @param previousNode the previous node
	 * @param changeDeliveryList if we modify the delivery list by adding the delivery TODO check if correct because sounds weird
	 */
	private void addDelivery (Node nodeDelivery, int duration, Node previousNode, boolean changeDeliveryList) {
		Delivery delivery = new Delivery (nodeDelivery, duration);
		
		int position;
		if(circuitsList!=null && circuitsList.size()!=0) {
			for (Circuit circuit : this.circuitsList) {
				if ((position=circuit.checkNodeInCircuit(previousNode))!=-1) {
					// we add the delivery to the list and we delete the atomicPath between the previous delivery and the next one
					circuit.addDelivery(delivery, (position+1));
					circuit.removeAtomicPath(position);
					
					// we get the previous and the next delivery
					Delivery previousDelivery = circuit.getDeliveryList().get(position);
					
					// we build the lists that we will send to the Dijstra algorithm to find the shortest paths we want
					List<Delivery> newDeliveryList = new ArrayList<Delivery>();
					newDeliveryList.add(previousDelivery);
					newDeliveryList.add(delivery);
					
					try {
						HashMap<Delivery,AtomicPath> deliveryPrevious = this.currentMap.findShortestPath(previousDelivery, newDeliveryList);
						circuit.addAtomicPath(deliveryPrevious.get(delivery), (position));
					} catch (DijkstraException e) {
						// TODO does we really catch this Exception here ? Or do we let it be thrown higher in the execution stack ?
						e.printStackTrace();
					}
					
					//If a node other than the repository follows the delivery added
					if(position+2!=circuit.getDeliveryList().size())
					{
						Delivery nextDelivery = circuit.getDeliveryList().get((position+2));
						
						List<Delivery> nextDeliveryList = new ArrayList<Delivery>();
						nextDeliveryList.add(delivery);
						nextDeliveryList.add(nextDelivery);
						
						try {
							HashMap<Delivery,AtomicPath> deliveryNew = this.currentMap.findShortestPath(delivery, nextDeliveryList);
							circuit.addAtomicPath(deliveryNew.get(nextDelivery), (position+1));
						} catch (DijkstraException e) {
							// TODO Same as previously
							e.printStackTrace();
						}
					}
					else {
						Delivery nextDelivery = circuit.getDeliveryList().get((0));
						
						List<Delivery> nextDeliveryList = new ArrayList<Delivery>();
						nextDeliveryList.add(delivery);
						nextDeliveryList.add(nextDelivery);
						
						try {
							HashMap<Delivery,AtomicPath> deliveryNew = this.currentMap.findShortestPath(delivery, nextDeliveryList);
							circuit.addAtomicPath(deliveryNew.get(nextDelivery), (position+1));
						} catch (DijkstraException e) {
							// TODO Same as previously
							e.printStackTrace();
						}
					}
				}
			}
			if(changeDeliveryList)
				deliveryList.add(delivery);
		}
		else {
			deliveryList.add(delivery);
		}
		
	}
	
	/**
	 * Removes the delivery at the node selected from the delivery list. It does it by calling the generic remove method specifying that we want to modify the delivery list
	 *
	 * @param nodeDelivery the node delivery
	 * @throws ManagementException the management exception
	 */
	public void removeDelivery (Node nodeDelivery) throws ManagementException {	
		removeDelivery (nodeDelivery, true);
	}
	
	/**
	 * Generic method to remove the delivery at the node selected, precising if we want to change the delivery list or not by doing so.
	 *
	 * @param nodeDelivery the node selected where the delivery we want to remove is
	 * @param changeDeliveryList if we modify the delivery list by removing the delivery TODO check if correct because sounds weird
	 * @throws ManagementException  TODO explain when this exception is raised
	 */
	private void removeDelivery (Node nodeDelivery, boolean changeDeliveryList) throws ManagementException {		
		int position;
		if(circuitsList!=null && circuitsList.size()!=0) {
			for (Circuit circuit : this.circuitsList) {
				if ((position=circuit.checkNodeInCircuit(nodeDelivery))!=-1) {
					if (position != 0) {
						circuit.removeDelivery(position);
						circuit.removeAtomicPath(position);
						circuit.removeAtomicPath(position-1);
						
						// we get the previous and the next delivery
						Delivery previousDelivery = circuit.getDeliveryList().get(position-1);
						Delivery nextDelivery;
						if(position != circuit.getDeliveryList().size()){ //Not last delivery
							nextDelivery = circuit.getDeliveryList().get(position);
						}
						else{												//Last delivery
							nextDelivery = circuit.getDeliveryList().get(0);
						}
						
						// we build the lists that we will send to the Dijstra algorithm to find the shortest paths we want
						List<Delivery> nextDeliveryList = new ArrayList<Delivery>();
						nextDeliveryList.add(previousDelivery);
						nextDeliveryList.add(nextDelivery);
						
						
						try {
							HashMap<Delivery,AtomicPath> deliveryNew = this.currentMap.findShortestPath(previousDelivery, nextDeliveryList);
							circuit.addAtomicPath(deliveryNew.get(nextDelivery), (position-1));
						} catch (DijkstraException e) {
							// TODO does we really catch this Exception here ? Or do we let it be thrown higher in the execution stack ?
							e.printStackTrace();
						}
						
					} else {
						throw new ManagementException("You cannot remove a repository");
					}
				}
			}
			if(changeDeliveryList) {
				deliveryList.remove(isDelivery(nodeDelivery));
			}
		}
		else {
			deliveryList.remove(isDelivery(nodeDelivery));
		}
	}
	
	/**
	 * Move delivery.
	 *
	 * @param node the node
	 * @param previousNode the previous node
	 * @throws ManagementException the management exception
	 */
	public void moveDelivery(Node node, Node previousNode) throws ManagementException {
		Delivery delivery = isDelivery(node);
		removeDelivery(node,false);
		addDelivery(node, delivery.getDuration(), previousNode,false);
	}
	
	/**
	 * Gets the previous node in the circuit for the delivery placed at the node selected.
	 *
	 * @param node the node selected
	 * @return the previous node or null if there is no previous node
	 */
	public Node getPreviousNode(Node node) {
		int position;
		if(circuitsList!=null && circuitsList.size()!=0) {
			for (Circuit circuit : this.circuitsList) {
				if ((position=circuit.checkNodeInCircuit(node))!=-1) {
					for(AtomicPath atomicPath : circuit.getPath()) {
						if(node==atomicPath.getEndNode()) {
							return atomicPath.getStartNode();
						}
					}
				}
			}
		}
		return null;
	}
	

}