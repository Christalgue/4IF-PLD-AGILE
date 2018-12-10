package main.java.entity;


import java.util.*;

import javafx.util.Pair;
import java.lang.Math;
import main.java.exception.*;
import main.java.utils.Deserializer;

// TODO: Auto-generated Javadoc
/**
 * The Class CircuitManagement.
 */
public class CircuitManagement extends Observable{

    /**
     * Default constructor.
     */
    public CircuitManagement() {
    }

    /** The current map. */
    private Map currentMap;

    /** The nb delivery man. */
    private int nbDeliveryMan;

    /** The circuits list. */
    private List<Circuit> circuitsList;

    /** The delivery list. */
    private List<Delivery> deliveryList;





    /**
     * Load delivery list.
     *
     * @param filename the filename
     * @throws LoadDeliveryException the load delivery exception
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
	 * @throws NoRepositoryException the no repository exception
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
	 * Gets the nb delivery man.
	 *
	 * @return the nb delivery man
	 */
	public int getNbDeliveryMan() {
		return nbDeliveryMan;
	}

	/**
	 * Sets the nb delivery man.
	 *
	 * @param nbDeliveryMan the new nb delivery man
	 */
	public void setNbDeliveryMan(int nbDeliveryMan) {
		this.nbDeliveryMan = nbDeliveryMan;
	}

	/**
	 * Load map.
	 *
	 * @param filename the filename
	 * @throws LoadMapException the load map exception
	 */
    public void loadMap(String filename) throws LoadMapException {
        try {
			this.currentMap = new Map(filename);
		} catch (LoadMapException e) {
			throw e; 
		}
    }

    /**
     * K-means clustering algorithm.
     *
     * @return the list
     * @throws ClusteringException the clustering exception
     */
    public List<ArrayList<Delivery>> cluster() throws ClusteringException {
    	
        // process the Kmeansclustering method multiple times and keep the best one
    	
    	List<Delivery> deliveriesToDistribute = new ArrayList<Delivery>(deliveryList);
    	try {
			deliveriesToDistribute.remove(getRepository());
		} catch (NoRepositoryException e) {
			// TODO Auto-generated catch block
			System.out.println("PAS DE REPOSITORY!!!");
			e.printStackTrace();
		}
    	
    	List<ArrayList<Delivery>> distribution = KmeansClustering(nbDeliveryMan, deliveriesToDistribute);
    	// REBALANCE THE DISTRIBUTION
    	int iteration = 1;
    	while (iteration<1000 && !checkClusterValidity(distribution)) {
    		/*
    		System.out.println("-------------------------------------------");
    		System.out.println("Iteration : "+iteration);
    		System.out.println("Validity : "+checkClusterValidity(distribution));
    		for (ArrayList<Delivery> cluster : distribution) {
    			System.out.println(cluster.size()+" : ");
    			for (Delivery del : cluster) {
    				System.out.println("Livraison : "+del.getPosition().getId());
    			}
    			System.out.println("");
    		}
    		System.out.println("-------------------------------------------");
    		System.out.println("");
    		*/
    		iteration++;
    		distribution = KmeansClustering(nbDeliveryMan, deliveriesToDistribute);
    	}
    	if (iteration==1000) {
    		System.out.println("PROBLEME DE DIVERGENCE DES CLUSTERS!!! (LA SOLUTION TROUVEE N'EST PAS OPTIMALE)");
    	} /*else {
    		System.out.println("Nombre d'iteration : "+iteration);
    	}*/

    	/*
		System.out.println("-------------------------------------------");
		System.out.println("Iteration : "+iteration);
		System.out.println("Validity : "+checkClusterValidity(distribution));
		for (ArrayList<Delivery> cluster : distribution) {
			System.out.println(cluster.size()+" : ");
			for (Delivery del : cluster) {
				System.out.println("Livraison : "+del.getPosition().getId()+" ("+del.getPosition().getLatitude()+"/"+del.getPosition().getLongitude()+")");
			}
			System.out.println("");
		}
		System.out.println("-------------------------------------------");
		System.out.println("");
    	*/
    	
		// Add repository to each circuit
		for (ArrayList<Delivery> cluster : distribution) {
			try {
				cluster.add(getRepository());
			} catch (NoRepositoryException e) {
				// TODO Auto-generated catch block
				System.out.println("PAS DE REPOSITORY!!!");
				e.printStackTrace();
			}
		} 
		
        return distribution;
    }
    
    /**
     * Kmeans clustering.
     *
     * @param numberOfClusters the number of clusters
     * @param deliveriesToCluster the deliveries to cluster
     * @return the list
     */
    public List<ArrayList<Delivery>> KmeansClustering(int numberOfClusters, List<Delivery> deliveriesToCluster) {
    	
    	List<Pair<Double,Double>> barycenters = new ArrayList<Pair<Double,Double>>(numberOfClusters);
    	for (int barycenterIndex = 0 ; barycenterIndex < numberOfClusters ; barycenterIndex++) {
    		addRandomBarycenter(barycenters,numberOfClusters, deliveriesToCluster);
    	}
    	
    	/*
    	System.out.println("Affichage des barycentres : ");
		for (Pair<Double,Double> barycenter : barycenters) {
			System.out.println("Barycentre : "+barycenter.getKey()+" (lat) / "+barycenter.getValue()+" (long)");
		}
		System.out.println("");
    	*/
		
    	List<ArrayList<Delivery>> newDistribution = KmeansClusteringStep(barycenters, deliveriesToCluster);
    	List<ArrayList<Delivery>> oldDistribution = null; //// HOW TO INITIALIZE????
    	    	
    	while (evaluateCluster(newDistribution, oldDistribution)) {
    		
    		barycenters = calculateBarycenters(newDistribution);
    		
    		/*
    		System.out.println("Affichage des barycentres : ");
    		for (Pair<Double,Double> barycenter : barycenters) {
    			System.out.println("Barycentre : "+barycenter.getKey()+" (lat) / "+barycenter.getValue()+" (long)");
    		}
    		System.out.println("");
    		*/
    		
    		oldDistribution = newDistribution;
    		newDistribution = KmeansClusteringStep(barycenters, deliveriesToCluster);
    	}
    	
    	return newDistribution;
    }
    
    /**
     * Kmeans clustering step.
     *
     * @param barycenters the barycenters
     * @param deliveriesToCluster the deliveries to cluster
     * @return the list
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
     * Calculate barycenters.
     *
     * @param currentDistribution the current distribution
     * @return the list
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
     * Adds the random barycenter.
     *
     * @param barycenters the barycenters
     * @param numberOfClusters the number of clusters
     * @param deliveries the deliveries
     */
    public void addRandomBarycenter(List<Pair<Double,Double>> barycenters, int numberOfClusters, List<Delivery> deliveries) {
    	Random r = new Random();
        Integer randomIndex = r.nextInt(deliveries.size());
        Delivery randomDelivery = deliveries.get(randomIndex);
        //System.out.println("Random Delivery prise (barycenter) : "+randomDelivery.getPosition().getId());
        Pair<Double,Double> barycenter = new Pair<Double,Double>(randomDelivery.getPosition().getLatitude(),randomDelivery.getPosition().getLongitude());
    	if (!barycenters.contains(barycenter)) {
    		barycenters.add(barycenter);
    	} else {
    		addRandomBarycenter(barycenters, numberOfClusters, deliveries);
    	}
    }
    
    /**
     * Evaluate cluster.
     *
     * @param newDistribution the new distribution
     * @param oldDistribution the old distribution
     * @return true, if successful
     */
    protected boolean evaluateCluster(List<ArrayList<Delivery>> newDistribution, List<ArrayList<Delivery>> oldDistribution) {
    	if (newDistribution.equals(oldDistribution)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    
    /**
     * Balance distribution.
     *
     * @param distributionToBalance the distribution to balance
     * @return the list
     */
    // NOT IN USE RIGHT NOW
    protected List<ArrayList<Delivery>> balanceDistribution(List<ArrayList<Delivery>> distributionToBalance) {
    	
    	Integer bottomAverageNumberOfDeliveries = deliveryList.size()/nbDeliveryMan;
    	Integer topAverageNumberOfDeliveries = bottomAverageNumberOfDeliveries;
    	
    	if (deliveryList.size()%nbDeliveryMan!=0) {
    		topAverageNumberOfDeliveries += 1;
    	}
    	
    	List<ArrayList<Delivery>> balancedDistribution = new ArrayList<ArrayList<Delivery>>(distributionToBalance);
    	
    	// Contains for each circuits, the number of delivery it is lacking / has in excess
    	List<Integer> distributionDifferences = new ArrayList<Integer>(distributionToBalance.size());
    	
    	// Calculate the differences of deliveries
    	for (ArrayList<Delivery> deliveries : distributionToBalance) {
    		if (deliveries.size()>=topAverageNumberOfDeliveries) {
    			distributionDifferences.add(deliveries.size()-topAverageNumberOfDeliveries);
    		} else {
    			distributionDifferences.add(deliveries.size()-bottomAverageNumberOfDeliveries);
    		}
    	}
    	
    	// ITERER sur les delivery restantes a traiter (refaire des KmeanClustering)
    	
    	return balancedDistribution;
    }
    
    /**
     * Check cluster validity.
     *
     * @param clustersToCheck the clusters to check
     * @return true, if successful
     */
    protected boolean checkClusterValidity (List<ArrayList<Delivery>> clustersToCheck) {
    	Integer bottomAverageNumberOfDeliveries = (deliveryList.size()-1)/nbDeliveryMan; // Passer plutot la liste sans l'entrepot
    	Integer topAverageNumberOfDeliveries = bottomAverageNumberOfDeliveries+1;
    	//System.out.println("Bornes valides : "+bottomAverageNumberOfDeliveries+"/"+topAverageNumberOfDeliveries);
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
     * @param nbDeliveryman the nb deliveryman
     * @param continueInterruptedCalculation TODO
     * @throws MapNotChargedException the map not charged exception
     * @throws LoadDeliveryException the load delivery exception
     * @throws ClusteringException the clustering exception
     * @throws DijkstraException the dijkstra exception
     * @throws NoRepositoryException the no repository exception
     * @throws TSPLimitTimeReachedException the TSP limit time reached exception
     */
    public void calculateCircuits(int nbDeliveryman, boolean continueInterruptedCalculation) throws MapNotChargedException, LoadDeliveryException, ClusteringException, DijkstraException, NoRepositoryException, TSPLimitTimeReachedException {
    	
    	if(continueInterruptedCalculation == false) {
    		this.circuitsList = null;
        	if(nbDeliveryman>0){
        		this.nbDeliveryMan = nbDeliveryman;
        	}
        	List<ArrayList<Delivery>> groupedDeliveries;
        	if(this.currentMap.getNodeMap().isEmpty()){
        		throw new MapNotChargedException("Impossible to calculate the circuits if there is not any Map in the system");
        	} else if (this.deliveryList.isEmpty()){
        		throw new LoadDeliveryException("Impossible to calculate the circuits if there is not any Delivery in the system");
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
        		HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPathsTemp = new HashMap<Delivery,HashMap<Delivery,AtomicPath>>();
        		try {
    				repository = getRepository();
    			} catch (NoRepositoryException e1) {
    				// TODO Auto-generated catch block
    				//e1.printStackTrace();
    				throw e1;
    			}
        		if(repository != null){
        			allPathsTemp.put(repository, this.currentMap.findShortestPath(repository, this.deliveryList));
        		}
        		for(List<Delivery> arrivalDeliveries : groupedDeliveries){
        			HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths = new HashMap<Delivery,HashMap<Delivery,AtomicPath>>(allPathsTemp);
        			for(Delivery departureDelivery : arrivalDeliveries){
        				try {
        					if (departureDelivery!=repository) {
        						allPaths.put(departureDelivery, this.currentMap.findShortestPath(departureDelivery, arrivalDeliveries));
        					}
    					} catch (DijkstraException e) {
    						// TODO Auto-generated catch block
    						//e.printStackTrace();
    						throw e;
    					}
        			}
        			Circuit circuit = new Circuit(arrivalDeliveries, repository, allPaths);
        			this.circuitsList.add(circuit);
        			System.out.println("circuit cr��");
        		}
        		for(Circuit circuit : this.circuitsList) {
        			try {
        				circuit.createCircuit();
        				//System.out.println(circuit.getPath().toString());
    				} catch (TSPLimitTimeReachedException e) {
    					//System.out.println(e.getMessage());
    					throw e;
    				} 
        		}
        	}
    	} else {
    		System.out.println("passage par CM_calculateCircuits");
    		for(Circuit circuitTested : this.circuitsList)
    		{
    			if(circuitTested.calculationIsFinished == false) {
    				circuitTested.continueCalculation();
    			}
    		}
    	}
    	
    }
    
    /**
     * Clean execution stacks.
     */
    public void cleanExecutionStacks() {
    	
    }

	/**
	 * Check node in delivery list.
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
	 * Gets the circuit by delivery.
	 *
	 * @param delivery the delivery
	 * @return the circuit by delivery
	 */
	public Circuit getCircuitByDelivery( Delivery delivery) {
		
		for(Circuit circuitTested : this.circuitsList){
			
			for ( Delivery deliveryTested : circuitTested.getDeliveryList()) {
				if ( deliveryTested.getPosition() == delivery.getPosition())
					return circuitTested;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Gets the circuit by index.
	 *
	 * @param index the index
	 * @return the circuit by index
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
	 * Gets the delivery index.
	 *
	 * @param delivery the delivery
	 * @return the delivery index
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
	 * Gets the delivery by index.
	 *
	 * @param index the index
	 * @return the delivery by index
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
	
	/**
	 * Checks if is delivery.
	 *
	 * @param nodeTested the node tested
	 * @return the delivery
	 */
	public Delivery isDelivery(Node nodeTested){
		for(Delivery deliveryTested : this.deliveryList){
			if(deliveryTested.getPosition() == nodeTested)
				return deliveryTested;
		}
		return new Delivery (nodeTested, -1);
	}
	
	/**
	 * Gets the delivery by node.
	 *
	 * @param nodeTested the node tested
	 * @return the delivery by node
	 */
	public Delivery getDeliveryByNode (Node nodeTested) {
		for (Delivery deliveryTested : this.deliveryList) {
			if (deliveryTested.getPosition() == nodeTested) {
				return deliveryTested;
			}
		}
		return null;
	}
	
	/**
	 * Adds the delivery.
	 *
	 * @param nodeDelivery the node delivery
	 * @param duration the duration
	 * @param previousNode the previous node
	 */
	public void addDelivery (Node nodeDelivery, int duration, Node previousNode) {
		addDelivery(nodeDelivery, duration, previousNode, true);
	}
	
	/**
	 * Adds the delivery.
	 *
	 * @param nodeDelivery the node delivery
	 * @param duration the duration
	 * @param previousNode the previous node
	 * @param changeDeliveryList the change delivery list
	 */
	private void addDelivery (Node nodeDelivery, int duration, Node previousNode, boolean changeDeliveryList) {
		Delivery delivery = new Delivery (nodeDelivery, duration);
		
		int position;
		if(circuitsList!=null && circuitsList.size()!=0) {
			for (Circuit circuit : this.circuitsList) {
				if ((position=circuit.checkNodeInCircuit(previousNode))!=-1) {
					// on rajoute le delivery � la liste et on supprime l'atomic path entre le delivery precedent et suivant
					circuit.addDelivery(delivery, (position+1));
					circuit.removeAtomicPath(position);
					
					// on recupere le delivery precedent et suivant
					Delivery previousDelivery = circuit.getDeliveryList().get(position);
					
					//on setup des listes pour pouvoir utiliser le findShortestPath
					List<Delivery> newDeliveryList = new ArrayList<Delivery>();
					newDeliveryList.add(previousDelivery);
					newDeliveryList.add(delivery);
					
					try {
						HashMap<Delivery,AtomicPath> deliveryPrevious = this.currentMap.findShortestPath(previousDelivery, newDeliveryList);
						circuit.addAtomicPath(deliveryPrevious.get(delivery), (position));
					} catch (DijkstraException e) {
						// TODO Auto-generated catch block
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
							// TODO Auto-generated catch block
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
							// TODO Auto-generated catch block
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
	 * Removes the delivery.
	 *
	 * @param nodeDelivery the node delivery
	 * @throws ManagementException the management exception
	 */
	public void removeDelivery (Node nodeDelivery) throws ManagementException {	
		removeDelivery (nodeDelivery, true);
	}
	
	/**
	 * Removes the delivery.
	 *
	 * @param nodeDelivery the node delivery
	 * @param changeDeliveryList the change delivery list
	 * @throws ManagementException the management exception
	 */
	private void removeDelivery (Node nodeDelivery, boolean changeDeliveryList) throws ManagementException {		
		int position;
		if(circuitsList!=null && circuitsList.size()!=0) {
			for (Circuit circuit : this.circuitsList) {
				if ((position=circuit.checkNodeInCircuit(nodeDelivery))!=-1) {
					if (position != 0) {
						Delivery delivery = circuit.getDeliveryList().get(position);
						circuit.removeDelivery(position);
						circuit.removeAtomicPath(position);
						circuit.removeAtomicPath(position-1);
						
						// on recupere le delivery precedent et suivant
						Delivery previousDelivery = circuit.getDeliveryList().get(position-1);
						Delivery nextDelivery;
						if(position != circuit.getDeliveryList().size()){ //Not last delivery
							nextDelivery = circuit.getDeliveryList().get(position);
						}
						else{												//Last delivery
							nextDelivery = circuit.getDeliveryList().get(0);
						}
						
						//on setup des listes pour pouvoir utiliser le findShortestPath
						List<Delivery> nextDeliveryList = new ArrayList<Delivery>();
						nextDeliveryList.add(previousDelivery);
						nextDeliveryList.add(nextDelivery);
						
						
						try {
							HashMap<Delivery,AtomicPath> deliveryNew = this.currentMap.findShortestPath(previousDelivery, nextDeliveryList);
							circuit.addAtomicPath(deliveryNew.get(nextDelivery), (position-1));
						} catch (DijkstraException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(changeDeliveryList)
							deliveryList.remove(delivery);
						
					} else {
						throw new ManagementException("You cannot remove a repository");
					}
				}
			}
		}
		else {
			System.out.println("bonjour");
			deliveryList.remove(getDeliveryByNode(nodeDelivery));
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
		
		Delivery delivery = getDeliveryByNode(node);
		System.out.println(delivery);
		removeDelivery(node,false);
		System.out.println(delivery);
		addDelivery(node, delivery.getDuration(), previousNode,false);
		
	}
	
	/**
	 * Gets the previous node.
	 *
	 * @param node the node
	 * @return the previous node
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