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
	private List<AtomicPath> path = null;

	/**
	 * 
	 */
	private List<Delivery> deliveryList;
	
	
	private Repository repositorySVG = null;
	
	private HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathsSVG = null;
	
	private HashMap<Delivery, HashMap<Delivery, AtomicPath>> initialAllPathsSVG = null;
	
	protected TSP1 tsp;

	protected boolean calculationIsFinished = false;

	/**
	 * Constructor
	 * @throws TSPLimitTimeReachedException 
	 * 
	 */
	public Circuit(List<Delivery> deliveries, Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) {
		this.tsp = new TSP1();
		this.deliveryList = deliveries;
		this.repositorySVG = repository;
		this.allPathsSVG = allPaths;
	}
	
	public void createCircuit(/*Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths*/) 
			throws TSPLimitTimeReachedException {
		System.out.println("calcul circuit");
		try {
			calculateTrackTSP(this.repositorySVG, this.allPathsSVG, false);
		} catch (TSPLimitTimeReachedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.out.println(e.getMessage());
			throw e;
		}
		this.calculationIsFinished = true;
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
		//this.tsp = new TSP1();
		TSPLimitTimeReachedException timeException = null;
		try {
			//System.out.println("debut try");
			if(!continueInterruptedCalculation) {
				initialAllPathsSVG = allPaths;
			}
			System.out.println("calculateTrackTSP " + continueInterruptedCalculation);
			tsp.searchSolution(1000, repository, allPaths, null, continueInterruptedCalculation);
			//System.out.println("fin try");
		} catch (TSPLimitTimeReachedException e) {
			// TODO Auto-generated catch block
			///e.printStackTrace();
			//System.out.println(e.getMessage());
			
			this.calculationIsFinished = false;
			//saveCurrentStateForCalculation(repository, allPaths);
			
			timeException = e;
			//throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("fin recherche solution");
			Delivery bestSolution[];
			bestSolution = this.tsp.getBestSolution();
			List<Delivery> deliveriesOrdered = new ArrayList<Delivery>();
			List<AtomicPath> finalPath = new ArrayList<AtomicPath>();
			// add the first Delivery to the deliveryList
			deliveriesOrdered.add(bestSolution[0]);
			for (int indexBestSolution = 1; indexBestSolution < bestSolution.length; indexBestSolution++) {
				deliveriesOrdered.add(bestSolution[indexBestSolution]);
				HashMap<Delivery, AtomicPath> pathsFromLastDelivery = allPaths.get(bestSolution[indexBestSolution - 1]);
				System.out.println("v1 : " + initialAllPathsSVG.get(bestSolution[indexBestSolution - 1]).keySet().toString());
				System.out.println("v2 : " + pathsFromLastDelivery.keySet().toString());
				System.out.println("index : " + indexBestSolution + " / size : " + bestSolution.length);
				for(int j=0;j<bestSolution.length; j++) {
					if(bestSolution[j] != null) {
						System.out.println(j + " : " + bestSolution[j]);
					} else {
						System.out.println(j + " : null");
					}
				}
				System.out.println("A :" + bestSolution[indexBestSolution].toString());
				///System.out.println("B : "+ pathsFromLastDelivery.get(bestSolution[indexBestSolution]).toString());
				finalPath.add(pathsFromLastDelivery.get(bestSolution[indexBestSolution]));
			}
			// add the last AtomicPath to the finalPath
			HashMap<Delivery, AtomicPath> pathsFromLastDeliveryOfTheList = allPaths.get(bestSolution[bestSolution.length-1]);
			System.out.println(bestSolution[0]);
			//System.out.println(finalPath.toString());
			System.out.println(pathsFromLastDeliveryOfTheList.get(bestSolution[0]));
			finalPath.add(pathsFromLastDeliveryOfTheList.get(bestSolution[0]));
			this.deliveryList = deliveriesOrdered;
			this.path = finalPath;
		}
		if (timeException!= null) {
			System.out.println("temps limite atteint");
			throw timeException;
			
		}
		this.tsp.setBestSolution(null);
		
		
	}
	/*private void saveCurrentStateForCalculation(Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths) {
		this.allPathsSVG = allPaths;
		this.repositorySVG = repository;
	}*/
	
	public void continueCalculation() throws TSPLimitTimeReachedException {
		//load the save and continue the calculation.
		System.out.println("ContinueCalc_repositorySVG " /*+ repositorySVG*/);
		System.out.println("ContinueCalc_allPathsSVG " /*+ allPathsSVG*/);
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

	public double getCircuitLength() {
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

	protected Repository getRepositorySVG() {
		return repositorySVG;
	}

	protected void setRepositorySVG(Repository repositorySVG) {
		this.repositorySVG = repositorySVG;
	}

	protected HashMap<Delivery, HashMap<Delivery, AtomicPath>> getAllPathsSVG() {
		return allPathsSVG;
	}

	protected void setAllPathsSVG(HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathsSVG) {
		this.allPathsSVG = allPathsSVG;
	}
	
	

}