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
			if(delivery.getClass() == Repository.class){
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
    public List<ArrayList<Delivery>> cluster() throws ClusteringException {
    	
        // process the Kmeansclustering method multiple times and keep the best one
    	
    	List<Delivery> deliveriesToDistribute = new ArrayList<Delivery>(deliveryList);
    	try {
			deliveriesToDistribute.remove(getRepository());
		} catch (NoRepositoryException e) {
			System.out.println("PAS DE REPOSITORY!!!");
			e.printStackTrace();
		}
    	
    	Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> distribution = KmeansClustering(nbDeliveryMan, deliveriesToDistribute);
    	int iteration = 1;
    	while (iteration<2 && !checkClusterValidity(distribution.getKey())) {
    		iteration++;
    		distribution = KmeansClustering(nbDeliveryMan, deliveriesToDistribute);
    		
    		/*System.out.println("--------------- AVANT ------------------");
    		System.out.println("Iteration : "+iteration);
    		System.out.println("Validity : "+checkClusterValidity(distribution.getKey()));
    		for (ArrayList<Delivery> cluster : distribution.getKey()) {
    			System.out.println("Cluster de taille "+cluster.size()+" : ");
    			for (Delivery del : cluster) {
    				System.out.println("Livraison : "+del.getPosition().getId());
    			}
    			System.out.println("");
    		}
    		System.out.println("----------------------------------------");
    		System.out.println("");*/
    		
    		// Balance the distribution
    		distribution = balanceDistribution(distribution);
    	}
    	if (iteration==1000) {
    		System.out.println("PROBLEME DE DIVERGENCE DES CLUSTERS!!! (LA SOLUTION TROUVEE N'EST PAS OPTIMALE)");
    	} /*else {
    		System.out.println("Nombre d'iteration : "+iteration);
    	}*/

    	
		System.out.println("-------------------------------------------");
		System.out.println("Iteration : "+iteration);
		System.out.println("Validity : "+checkClusterValidity(distribution.getKey()));
		for (ArrayList<Delivery> cluster : distribution.getKey()) {
			System.out.println(cluster.size()+" : ");
			for (Delivery del : cluster) {
				System.out.println("Livraison : "+del.getPosition().getId()+" ("+del.getPosition().getLatitude()+"/"+del.getPosition().getLongitude()+")");
			}
			System.out.println("");
		}
		System.out.println("-------------------------------------------");
		System.out.println("");
    	
    	
		// Add repository to each circuit
		for (ArrayList<Delivery> cluster : distribution.getKey()) {
			try {
				cluster.add(getRepository());
			} catch (NoRepositoryException e) {
				// TODO Auto-generated catch block
				System.out.println("PAS DE REPOSITORY!!!");
				e.printStackTrace();
			}
		} 
		
        return distribution.getKey();
    }
    
    /**
     * K-means clustering algorithm
     * @return
     */
    public Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> KmeansClustering(int numberOfClusters, List<Delivery> deliveriesToCluster) {
    	
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
    	
    	Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>> Distribution = new Pair<List<ArrayList<Delivery>>,List<Pair<Double,Double>>>(newDistribution, barycenters);
    	
    	return Distribution;
    }
    
    /**
     * K-means clustering Step algorithm
     * @return
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
     * K-means clustering barycenters calculation
     * @return
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
     * Get a random barycenter for initialization
     * @return
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
     * Check cluster changes after a KmeansStep
     * @return
     */
    protected boolean evaluateCluster(List<ArrayList<Delivery>> newDistribution, List<ArrayList<Delivery>> oldDistribution) {
    	if (newDistribution.equals(oldDistribution)) { // A CHANGER
    		return false;
    	} else {
    		return true;
    	}
    }
    
    /**
     * Balance all clusters
     * @return
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
        	
        	/*
        	System.out.println("Affichage cluster APRES EQUILIBRAGE : ");
        	for (Delivery d : cluster) {
        		System.out.println("--- Livraison : "+d.getPosition().getId());
        	}
        	
        	System.out.println("Affichage du reste des livraisons APRES EQUILIBRAGE : ");
        	for (Delivery d : restOfDeliveries) {
        		System.out.println("(Reste) Livraison : "+d.getPosition().getId());
        	}*/
        	
        	balancedDistribution.getKey().add(cluster);
        	balancedDistribution.getValue().add(new Pair<Double,Double>(0.0,0.0)); // A changer
        	
        	// Make another Kmeansclustering on the rest of the deliveries
        	distribution = KmeansClustering(distributionToBalance.getKey().size()-balancedDistribution.getKey().size(), restOfDeliveries);
    	}
    	
    	return balancedDistribution;
    }
    
    /**
     * Return most balanced cluster
     * @return
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
    	
    	//Integer bottomAverageNumberOfDeliveries = (deliveryList.size()-1)/nbDeliveryMan;
    	//Integer topAverageNumberOfDeliveries = bottomAverageNumberOfDeliveries;
    	
    	Integer bottomAverageNumberOfDeliveries = (clusterToBalance.size()+restOfDeliveries.size())/distributionToBalance.size();
    	Integer topAverageNumberOfDeliveries = bottomAverageNumberOfDeliveries;
    	
    	if ((clusterToBalance.size()+restOfDeliveries.size())%distributionToBalance.size()!=0) {
    		topAverageNumberOfDeliveries += 1;
    	}
    	
    	/*
    	System.out.println("------------------------------------------");
    	System.out.println("BOTTOM : "+bottomAverageNumberOfDeliveries);
    	System.out.println("TOP : "+topAverageNumberOfDeliveries);
    	System.out.println("Taille du cluste a equilibrer : "+clusterToBalance.size());
    	System.out.println("Nombre du reste des deliveries : "+restOfDeliveries.size());
    	System.out.println("le nouveau truc : "+(clusterToBalance.size()+restOfDeliveries.size()));
    	System.out.println("le nouveau nombre de clusters : "+distributionToBalance.size());
    	System.out.println("------------------------------------------");*/
    	
    	/*
    	System.out.println("Affichage du cluster AVANT EQUILIBRAGE : ");
    	for (Delivery d : clusterToBalance) {
    		System.out.println("Livraison : "+d.getPosition().getId());
    	}
    	
    	System.out.println("Affichage du reste des livraisons AVANT EQUILIBRAGE : ");
    	for (Delivery d : restOfDeliveries) {
    		System.out.println("(Reste) Livraison : "+d.getPosition().getId());
    	}*/
    	
    	/*System.out.println("Taille du cluster : "+clusterToBalance.size());
    	System.out.println("Borne sup : "+topAverageNumberOfDeliveries);
    	System.out.println("Borne inf : "+bottomAverageNumberOfDeliveries);
    	System.out.println("Nb de del man : "+nbDeliveryMan);
    	System.out.println("Nb de livr total : "+deliveryList.size());*/
    	
    	
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
    
    /**
     * Return the most distant barycenter
     * @return
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
    	
    	/*
    	System.out.println("Coordonnees du centre de gravite : ");
    	System.out.println("lat : "+globalLatitude);
    	System.out.println("long : "+globalLongitude);
    	*/
    	
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
    	/*
    	System.out.println("Coordonnees du barycentre : ");
    	System.out.println("lat : "+mostDistantBarycenter.getKey());
    	System.out.println("long : "+mostDistantBarycenter.getValue());
    	*/
    	Pair<Pair<Double,Double>,Pair<Double,Double>> globalAndMostDistantBarycenters = new Pair<Pair<Double,Double>,Pair<Double,Double>>(mostDistantBarycenter,globalBarycenter);
    	return globalAndMostDistantBarycenters;
    }
    
    /**
     * Check if distribution has same-size clusters
     * @return
     */
    protected void addDeliveryToCluster(List<Delivery> cluster, Pair<Double,Double> barycenter, List<Delivery> restOfDeliveries) {
    	// get closest delivery to the barycenter
    	Double closestTotalDistance = Double.MAX_VALUE;
    	Delivery deliveryToAdd = null;
    	for (Delivery currentDelivery : restOfDeliveries) {
    		Double currentLatitudeDifference = barycenter.getKey()-currentDelivery.getPosition().getLatitude();
    		Double currentLongitudeDifference = barycenter.getValue()-currentDelivery.getPosition().getLongitude();
    		Double currentTotalDistance = Math.sqrt(currentLatitudeDifference*currentLatitudeDifference+currentLongitudeDifference*currentLongitudeDifference);
    		if (currentTotalDistance<closestTotalDistance) {
    			deliveryToAdd = currentDelivery;
    			closestTotalDistance = currentTotalDistance;
    		}
    	}
    	
    	//System.out.println("Ajout de la livraison : "+deliveryToAdd.getPosition().getId());
    	// add the closest delivery to the cluster
    	cluster.add(deliveryToAdd);
    	restOfDeliveries.remove(deliveryToAdd);
    }
    
    /**
     * Remove a delivery from the cluster (the delivery that is the closest to the specified barycenter)
     * @return
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
    	
    	//System.out.println("Retirer la livraison : "+deliveryToRemove.getPosition().getId());
    	// add the closest delivery to the cluster
    	cluster.remove(deliveryToRemove);
    	restOfDeliveries.add(deliveryToRemove);
    }
    
    /**
     * Check if distribution has same-size clusters
     * @return
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
     *      calculated the atomic path between each delivery
     * @param nbDeliveryman
     * @param continueInterruptedCalculation TODO
     * @throws MapNotChargedException 
     * @throws DeliveryListNotCharged 
     * @throws ClusteringException 
     * @throws DijkstraException 
     * @throws NoRepositoryException 
     * @throws TSPLimitTimeReachedException 
     */
    public void calculateCircuits(int nbDeliveryman, boolean continueInterruptedCalculation) throws MapNotChargedException, LoadDeliveryException, ClusteringException, DijkstraException, NoRepositoryException, TSPLimitTimeReachedException {
    	
    	if(continueInterruptedCalculation == false) {

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
        			Circuit circuit = new Circuit(arrivalDeliveries);
        			try {
        				circuit.createCircuit( repository, allPaths);
        				System.out.println("salut");
        				System.out.println(circuit.getPath().toString());
					} catch (TSPLimitTimeReachedException e) {
						System.out.println(e.getMessage());
						throw e;
					} finally {
						this.circuitsList.add(circuit);
					}
        		}
        	}
    	} else {
    		for(Circuit circuitTested : this.circuitsList)
    		{
    			if(circuitTested.getRepositorySVG() != null && circuitTested.getAllPathsSVG() != null) {
    				circuitTested.continueCalculation();
    				circuitTested.setRepositorySVG(null);
    				circuitTested.setAllPathsSVG(null);
    			}
    		}
    	}
    	
    }

	public boolean checkNodeInDeliveryList(Node nodeTested){
		for(Delivery deliveryTested : this.deliveryList){
			if(deliveryTested.getPosition() == nodeTested)
				return true;
		}
		return false;
	}
	
	
	public void addDelivery (Node nodeDelivery, int duration, Node previousNode) {
		Delivery delivery = new Delivery (nodeDelivery, duration);
		
		int position;
		for (Circuit circuit : this.circuitsList) {
			if ((position=circuit.checkNodeInCircuit(previousNode))!=-1) {
				// on rajoute le delivery a la liste et on supprime l'atomic path entre le delivery precedent et suivant
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
		deliveryList.add(delivery);
		
	}
	
	public void removeDelivery (Node nodeDelivery) throws ManagementException {		
		int position;
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
					deliveryList.remove(delivery);
				} else {
					throw new ManagementException("You cannot remove a repository");
				}
			}
		}
		
	}
	
	

}