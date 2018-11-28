package main.java.entity;

import java.util.*;

import main.java.exception.TSPLimitTimeReachedException;
import main.java.tsp.*;

/**
 * 
 */
public class Circuit extends Observable {

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
	
	
	private Repository repositorySVG = null;
	
	private HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathsSVG = null;

	/**
	 * Constructor
	 * @throws TSPLimitTimeReachedException 
	 * 
	 */
	public Circuit(List<Delivery> deliveries, Repository repository,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) throws TSPLimitTimeReachedException {
		this.deliveryList = deliveries;
		calculateTrackTSP(repository, allPaths, false);
		this.circuitLength = calculateLength();
	}

	/**
	 * 
	 */
	protected double calculateLength() {
		double result = 0;
		for (AtomicPath segment : this.path) {
			result += segment.getLength();
		}
		return result;
	}

	/**
	 * @param continueInterruptedCalculation 
	 * @throws TSPLimitTimeReachedException 
	 */
	protected void calculateTrackTSP(Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, boolean continueInterruptedCalculation) throws TSPLimitTimeReachedException {
		TSP1 tsp = new TSP1();
		try {
			tsp.searchSolution(10000, repository, allPaths, null, continueInterruptedCalculation);
		} catch (TSPLimitTimeReachedException e) {
			// TODO Auto-generated catch block
			///e.printStackTrace();
			saveCurrentStateForCalculation(repository, allPaths);
			
			throw e;
		} finally {
			Delivery bestSolution[];
			bestSolution = tsp.getBestSolution();
			List<Delivery> deliveriesOrdered = new ArrayList<Delivery>();
			List<AtomicPath> finalPath = new ArrayList<AtomicPath>();
			// add the first Delivery to the deliveryList
			deliveriesOrdered.add(bestSolution[0]);
			for (int indexBestSolution = 1; indexBestSolution < bestSolution.length; indexBestSolution++) {
				deliveriesOrdered.add(bestSolution[indexBestSolution]);
				HashMap<Delivery, AtomicPath> pathsFromLastDelivery = allPaths.get(bestSolution[indexBestSolution - 1]);
				finalPath.add(pathsFromLastDelivery.get(bestSolution[indexBestSolution]));
			}
			// add the last AtomicPath to the finalPath
			HashMap<Delivery, AtomicPath> pathsFromLastDeliveryOfTheList = allPaths.get(bestSolution[bestSolution.length-1]);
			finalPath.add(pathsFromLastDeliveryOfTheList.get(bestSolution[0]));

			this.deliveryList = deliveriesOrdered;
			this.path = finalPath;
		}
	}
	private void saveCurrentStateForCalculation(Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) {
		this.allPathsSVG = allPaths;
		this.repositorySVG = repository;
	}
	
	public void continueCalculation() throws TSPLimitTimeReachedException {
		//load the save and continue the calculation.
		calculateTrackTSP(repositorySVG, allPathsSVG, true);
	}

	/**
	 * @param Map
	 * @param Delivery
	 */
	protected void removeDelivery(int position) {
		this.deliveryList.remove(position);
	}

	/**
	 * @param Delivery
	 * @param nextToDelivery
	 */
	protected void addDelivery(Delivery deliveryToAdd, int position) {
		this.deliveryList.add(position, deliveryToAdd);
	}
	
	protected void addAtomicPath(AtomicPath pathToAdd, int position) {
		this.path.add(position, pathToAdd);
		
	}
	
	protected void removeAtomicPath(int position) {
		this.path.remove(position);
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

	public List<Delivery> getDeliveryList() {
		return deliveryList;
	}

	protected void setDeliveryList(List<Delivery> deliveryList) {
		this.deliveryList = deliveryList;
	}
	
	protected int checkNodeInCircuit(Node nodeTested) {
		int position;
		for(position=0; position < this.deliveryList.size(); position++){
			if(this.deliveryList.get(position).getPosition() == nodeTested) 
			{
				return position;
			}
		}
		return -1;
		
	}

}