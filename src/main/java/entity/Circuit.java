package main.java.entity;

import java.util.*;

import main.java.exception.TSPLimitTimeReachedException;
import main.java.tsp.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Circuit.
 */
public class Circuit extends Observable {

	
	/**
	 * Default constructor.
	 */
	public Circuit() {
	}

	/** The circuit length. */
	private double circuitLength;

	/** The path. */
	private List<AtomicPath> path = null;

	/** The delivery list. */
	private List<Delivery> deliveryList;
	
	
	/** The repository SVG. */
	private Repository repositorySVG = null;
	
	/** The all paths SVG. */
	private HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathsSVG = null;
	
	/** The initial all paths SVG. */
	private HashMap<Delivery, HashMap<Delivery, AtomicPath>> initialAllPathsSVG = null;
	
	/** The tsp. */
	protected TSP1 tsp;

	/** The calculation is finished. */
	protected boolean calculationIsFinished = false;

	/** The circuit ID. */
	protected int circuitID;
	
	/**
	 * Constructor.
	 *
	 * @param deliveries the deliveries
	 * @param repository the repository
	 * @param allPaths the all paths
	 */
	public Circuit(List<Delivery> deliveries, Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) {
		this.tsp = new TSP1();
		this.deliveryList = deliveries;
		this.repositorySVG = repository;
		this.allPathsSVG = allPaths;
	}
	
	/**
	 * Creates the circuit.
	 *
	 * @throws TSPLimitTimeReachedException the TSP limit time reached exception
	 */
	public void createCircuit(/*Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths*/) 
			throws TSPLimitTimeReachedException {
		try {
			calculateTrackTSP(this.repositorySVG, this.allPathsSVG, false);
		} catch (TSPLimitTimeReachedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}
		this.calculationIsFinished = true;
		this.circuitLength = calculateLength();
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
	 * Calculate track TSP.
	 *
	 * @param repository the repository
	 * @param allPaths the all paths
	 * @param continueInterruptedCalculation the continue interrupted calculation
	 * @throws TSPLimitTimeReachedException the TSP limit time reached exception
	 */
	protected void calculateTrackTSP(Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, boolean continueInterruptedCalculation) throws TSPLimitTimeReachedException {
		//this.tsp = new TSP1();
		TSPLimitTimeReachedException timeException = null;
		try {
			if(!continueInterruptedCalculation) {
				initialAllPathsSVG = allPaths;
			}
			tsp.searchSolution(1000, repository, allPaths, null, continueInterruptedCalculation);
		} catch (TSPLimitTimeReachedException e) {
			// TODO Auto-generated catch block
			///e.printStackTrace();
			
			this.calculationIsFinished = false;
			//saveCurrentStateForCalculation(repository, allPaths);
			
			timeException = e;
			//throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			Delivery bestSolution[];
			bestSolution = this.tsp.getBestSolution();
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
		if (timeException!= null) {
			throw timeException;
			
		}
		this.tsp.setBestSolution(null);
		
		
	}
	/*private void saveCurrentStateForCalculation(Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) {
		this.allPathsSVG = allPaths;
		this.repositorySVG = repository;
	}*/
	
	/**
	 * Continue calculation.
	 *
	 * @throws TSPLimitTimeReachedException the TSP limit time reached exception
	 */
	public void continueCalculation() throws TSPLimitTimeReachedException {
		//load the save and continue the calculation.
		calculateTrackTSP(repositorySVG, allPathsSVG, true);
	}

	/**
	 * Removes the delivery.
	 *
	 * @param position the position
	 */
	protected void removeDelivery(int position) {
		this.deliveryList.remove(position);
	}

	/**
	 * Adds the delivery.
	 *
	 * @param deliveryToAdd the delivery to add
	 * @param position the position
	 */
	protected void addDelivery(Delivery deliveryToAdd, int position) {
		this.deliveryList.add(position, deliveryToAdd);
	}
	
	/**
	 * Adds the atomic path.
	 *
	 * @param pathToAdd the path to add
	 * @param position the position
	 */
	protected void addAtomicPath(AtomicPath pathToAdd, int position) {
		this.path.add(position, pathToAdd);
	}
	
	/**
	 * Removes the atomic path.
	 *
	 * @param position the position
	 */
	protected void removeAtomicPath(int position) {
		this.path.remove(position);
	}

	/**
	 * Gets the circuit length.
	 *
	 * @return the circuit length
	 */
	public double getCircuitLength() {
		return circuitLength;
	}

	/**
	 * Sets the circuit length.
	 *
	 * @param circuitLength the new circuit length
	 */
	protected void setCircuitLength(double circuitLength) {
		this.circuitLength = circuitLength;
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
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	private void setPath(List<AtomicPath> path) {
		this.path = path;
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
	 * Check node in circuit.
	 *
	 * @param nodeTested the node tested
	 * @return the int
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
	 * Gets the repository SVG.
	 *
	 * @return the repository SVG
	 */
	protected Repository getRepositorySVG() {
		return repositorySVG;
	}

	/**
	 * Sets the repository SVG.
	 *
	 * @param repositorySVG the new repository SVG
	 */
	protected void setRepositorySVG(Repository repositorySVG) {
		this.repositorySVG = repositorySVG;
	}

	/**
	 * Gets the all paths SVG.
	 *
	 * @return the all paths SVG
	 */
	protected HashMap<Delivery, HashMap<Delivery, AtomicPath>> getAllPathsSVG() {
		return allPathsSVG;
	}

	/**
	 * Sets the all paths SVG.
	 *
	 * @param allPathsSVG the all paths SVG
	 */
	protected void setAllPathsSVG(HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathsSVG) {
		this.allPathsSVG = allPathsSVG;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return this.circuitID;
	}
	
	

}