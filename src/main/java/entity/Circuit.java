package main.java.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import main.java.exception.TSPLimitTimeReachedException;
import main.java.tsp.TSP1;
import main.java.tsp.TemplateTSP;

/**
 * The Class Circuit represents the trip that a delivery-man have to accomplish.
 */
public class Circuit extends Observable {

	
	/**
	 * Default constructor.
	 */
	public Circuit() {
	}

	/** The circuit's length. */
	private double circuitLength;
	
	/** The speed. */
	private double speed;
	
	/** The circuit duration. */
	private double circuitDuration;

	/** The path. */
	private List<AtomicPath> path = null;

	/** The delivery list. */
	private List<Delivery> deliveryList;
	
	/** The repository. */
	private Repository repositorySVG = null;
	
	/** 
	 * All the AtomicPath between each Delivery in the deliveryList.
	 * The first entry is the starting Delivery, the second entry is the ending Delivery,
	 * the value associated is the AtomicPath between those two deliveries.
	 *  */
	private HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathsSVG = null;

	
	/** 
	 * The instance of tsp used to calculate the best circuit. 
	 * @see TemplateTSP
	 * */
	protected TSP1 tsp;

	/** The calculation is finished. */
	protected boolean calculationIsFinished = false;
	
	/**
	 * Constructor.
	 *
	 * @param deliveries the deliveries
	 * @param repository the repository
	 * @param allPaths all the paths between each delivery
	 * @param speed the speed of the delivery man
	 */
	public Circuit(List<Delivery> deliveries, Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, double speed) {
		this.tsp = new TSP1();
		this.speed = speed;
		tsp.setSpeed(speed);
		this.deliveryList = deliveries;
		this.repositorySVG = repository;
		this.allPathsSVG = allPaths;
	}
	
	/**
	 * Creates the circuit by searching the best one possible, using its TSP instance
	 * 
	 * @see calculateTrackTSP
	 *
	 * @throws TSPLimitTimeReachedException when the limitTime is reached before the calculation is finished
	 */
	public void createCircuit() 
			throws TSPLimitTimeReachedException {
		try {
			calculateTrackTSP(this.repositorySVG, this.allPathsSVG, false);
		} catch (TSPLimitTimeReachedException e) {
			this.circuitLength = calculateLength();
			this.circuitDuration = calculateDuration();
			throw e;
		}
		this.calculationIsFinished = true;
		this.circuitLength = calculateLength();
		this.circuitDuration = calculateDuration();
	}

	/**
	 * Calculate length.
	 *
	 * @return the double
	 */
	protected double calculateLength() {
		double result = 0;
		for (AtomicPath segment : this.path) {
			result += segment.getLength();
		}
		return result;
	}
	
	/**
	 * Calculate duration.
	 *
	 * @return the double
	 */
	protected double calculateDuration() {
		double result = circuitLength/speed;
		for(Delivery d : deliveryList) {
			result += d.getDuration();
		}
		return result;
	}

	/**
	 * Calculate the best circuit possible.
	 * Uses the TSP instance to search the best solution and no matter if the limit time is reached this method 
	 * reorganize the deliveryList for it to be in the right order. And using that order it creates the path.
	 *
	 * @param repository the repository
	 * @param allPaths all the paths between each delivery
	 * @param continueInterruptedCalculation true if the method is called after having interrupted the calculation at least once
	 * @throws TSPLimitTimeReachedException when the limitTime is reached before the calculation is finished
	 */
	protected void calculateTrackTSP(Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths,
			boolean continueInterruptedCalculation) throws TSPLimitTimeReachedException {
		TSPLimitTimeReachedException timeException = null;
		try {
			tsp.searchSolution(500, repository, allPaths, continueInterruptedCalculation);
		} catch (TSPLimitTimeReachedException e) {
			this.calculationIsFinished = false;
			timeException = e;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			Delivery bestSolution[];
			bestSolution = this.tsp.getBestSolution();
			List<Delivery> deliveriesOrdered = new ArrayList<Delivery>();
			List<AtomicPath> finalPath = new ArrayList<AtomicPath>();
			// add the first Delivery to the deliveryList before the loop to avoid out of bounds exception
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
		if (timeException!= null) {
			throw timeException;
			
		}
	}
	
	/**
	 * Continue the calculation from where it stopped.
	 *
	 * @throws TSPLimitTimeReachedException when the limitTime is reached before the calculation is finished
	 */
	public void continueCalculation() throws TSPLimitTimeReachedException {
		//load the save and continue the calculation.
		calculateTrackTSP(repositorySVG, allPathsSVG, true);
	}

	/**
	 * Removes the delivery that is at the index = position of the deliveryList .
	 *
	 * @param position the index of the delivery in the list
	 */
	protected void removeDelivery(int position) {
		this.deliveryList.remove(position);
	}

	/**
	 * Adds a delivery to the deliveryList at the index = position.
	 *
	 * @param deliveryToAdd the delivery to add
	 * @param position its position in the list
	 */
	protected void addDelivery(Delivery deliveryToAdd, int position) {
		this.deliveryList.add(position, deliveryToAdd);
	}
	
	/**
	 * Adds the atomic path to the path, at the index = position.
	 *
	 * @param pathToAdd the path to add
	 * @param position the position where we want it to be
	 */
	protected void addAtomicPath(AtomicPath pathToAdd, int position) {
		this.path.add(position, pathToAdd);
	}
	
	/**
	 * Removes the atomicPath from path at the chosen position in the list.
	 *
	 * @param position the position
	 */
	protected void removeAtomicPath(int position) {
		this.path.remove(position);
	}

	/**
	 * Gets the circuit's length.
	 *
	 * @return the circuit's length
	 */
	public double getCircuitLength() {
		return circuitLength;
	}

	/**
	 * Sets the circuit's length.
	 *
	 * @param circuitLength the new circuit's length
	 */
	protected void setCircuitLength(double circuitLength) {
		this.circuitLength = circuitLength;
	}
	
	/**
	 * Gets the circuit's duration.
	 *
	 * @return the circuit's duration
	 */
	public double getCircuitDuration() {
		return circuitDuration;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public List<AtomicPath> getPath() {
		return path;
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
	protected void setDeliveryList(List<Delivery> deliveryList) {
		this.deliveryList = deliveryList;
	}
	
	/**
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	protected void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Check if a node belong to the circuit.
	 *
	 * @param nodeTested the node tested
	 * @return the position of the Node in the circuit, or -1 if not contained
	 */
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

	/**
	 * Gets the saved repository.
	 *
	 * @return the saved repository
	 */
	protected Repository getRepositorySVG() {
		return repositorySVG;
	}

	/**
	 * Sets the saved repository.
	 *
	 * @param repositorySVG the new saved repository
	 */
	protected void setRepositorySVG(Repository repositorySVG) {
		this.repositorySVG = repositorySVG;
	}

	/**
	 * Gets all the paths saved.
	 *
	 * @return the paths saved
	 */
	protected HashMap<Delivery, HashMap<Delivery, AtomicPath>> getAllPathsSVG() {
		return allPathsSVG;
	}

	/**
	 * Sets all the paths saved.
	 *
	 * @param allPathsSVG the paths saved
	 */
	protected void setAllPathsSVG(HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathsSVG) {
		this.allPathsSVG = allPathsSVG;
	}


}