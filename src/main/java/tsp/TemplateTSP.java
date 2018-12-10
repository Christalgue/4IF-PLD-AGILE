package main.java.tsp;

import java.util.*;

import main.java.entity.*;
import main.java.exception.TSPLimitTimeReachedException;

// TODO: Auto-generated Javadoc
/**
 * The Class TemplateTSP.
 */
public abstract class TemplateTSP implements TSP {
	
	/** The best solution. Deliveries ordered to have he most little circuit possible */
	private Delivery[] bestSolution;
	
	/** The cost best solution. Initialized with the higher value possible*/
	private double costBestSolution = Double.MAX_VALUE;
	
	/** The limit time has been reached or not. */
	private Boolean limitTimeReached;
	
	
	/** The current delivery saved in memory to continue the calculation if wanted. */
	private Delivery currentDeliverySVG;
	
	/** The list of the non viewed deliveries saved in memory to continue the calculation if wanted. */
	private ArrayList<Delivery> nonViewedSVG = null;
	
	/** The list of the viewed deliveries saved in memory to continue the calculation if wanted. */
	private ArrayList<Delivery> viewedSVG = null;
	
	/** The cost associated to the list of viewed deliveries saved in memory to continue the calculation if wanted. */
	private double viewedCostSVG;
	
	/** The duration for each delivery saved in memory to continue the calculation if wanted. @unused */
	private int[] durationSVG = null;
	
	/** The execution stack to keep a trace of where we were when the calculation stopped because of the limit time exception so that we can continue it if wanted. */
	protected ArrayList<Delivery> executionStack = null;
	
	/* (non-Javadoc)
	 * @see main.java.tsp.TSP#getLimitTimeReached()
	 */
	public Boolean getLimitTimeReached(){
		return limitTimeReached;
	}
	
	/* (non-Javadoc)
	 * @see main.java.tsp.TSP#searchSolution(int, main.java.entity.Repository, java.util.HashMap, int[], boolean)
	 */
	public void searchSolution(int limitTime, Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, int[] duration, boolean continueInterruptedCalculation) throws TSPLimitTimeReachedException{
		limitTimeReached = false;
		if (continueInterruptedCalculation == false) {
			costBestSolution = Double.MAX_VALUE;
			ArrayList<Delivery> nonViewed = new ArrayList<Delivery>();
			nonViewed.addAll(allPaths.keySet());
			nonViewed.remove(repository);
			ArrayList<Delivery> viewed = new ArrayList<Delivery>();
			viewed.add(repository); // the first "node" visited in the repository 
			this.executionStack = new ArrayList<Delivery>();
			this.executionStack.add(repository);
			branchAndBound(repository, nonViewed, viewed, 0, allPaths, duration, System.currentTimeMillis(), limitTime);

			
		} else {
			continueResearchSolution(limitTime, allPaths, System.currentTimeMillis());
		}
		
		// TODO need to consider the time spent delivering the order at each delivery point.
	}
	
	/**
	 * Continue research solution.
	 *
	 * @param limitTime the limit time after which the calculation have to stop, finished or not
	 * @param allPaths all the AtomicPath between each Delivery in the deliveryList
	 * @param startingTime the starting time
	 * @throws TSPLimitTimeReachedException the TSP limit time reached exception
	 */
	protected void continueResearchSolution(int limitTime, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, long startingTime) throws TSPLimitTimeReachedException {
		//end the branch and bound that was ongoing when saving
		ArrayList<Delivery> viewed = new ArrayList<Delivery>(viewedSVG);
		ArrayList<Delivery> nonViewed = new ArrayList<Delivery>(nonViewedSVG);
		double viewedCost = viewedCostSVG;
		branchAndBound(currentDeliverySVG, nonViewed, viewed, viewedCost, allPaths, durationSVG, startingTime, limitTime);
		Delivery lastDeliveryTreated = currentDeliverySVG;
		viewed.remove(currentDeliverySVG);
		this.executionStack.remove(currentDeliverySVG);
		viewedCost -= allPaths.get(this.executionStack.get(this.executionStack.size()-1)).get(currentDeliverySVG).getLength();
		nonViewed.add(currentDeliverySVG);
		Delivery currentDelivery = null;
		Delivery nextDelivery = null;
		boolean nextDeliveryFound;
		boolean continueUnstack = this.executionStack.size()>0;
		int compteurTest = 0;
		while(continueUnstack) {			
			//continue the branch and bound that was ongoing for the last delivery of the execution stack
				//create the iterator and fix it to the good value
			currentDelivery = this.executionStack.get(this.executionStack.size()-1);
			Iterator<Delivery> it = iterator(currentDelivery, nonViewed, allPaths, durationSVG);
			
			nextDeliveryFound = false;
			while(it.hasNext() && nextDeliveryFound == false) {
				nextDelivery = it.next();
				nextDeliveryFound = (nextDelivery == lastDeliveryTreated);
				
			}
			//if still deliveries to branch and bound then go and execute it for every one left to explore.
			if(nextDeliveryFound) {
				viewed.add(nextDelivery);
	        	this.executionStack.add(nextDelivery);
	        	nonViewed.remove(nextDelivery);
	        	branchAndBound(nextDelivery, nonViewed, viewed, viewedCost + allPaths.get(currentDelivery).get(nextDelivery).getLength()/*+ duration[prochainSommet]*/,
	        			allPaths, durationSVG, startingTime, limitTime);
	        	viewed.remove(nextDelivery);
	        	this.executionStack.remove(nextDelivery);
	        	nonViewed.add(nextDelivery);
	        	while (it.hasNext()){
	        		nextDelivery = it.next();
		        	viewed.add(nextDelivery);
		        	this.executionStack.add(nextDelivery);
		        	
		        	nonViewed.remove(nextDelivery);
		        	
		        	branchAndBound(nextDelivery, nonViewed, viewed, viewedCost + allPaths.get(currentDelivery).get(nextDelivery).getLength()/*+ duration[prochainSommet]*/,
		        			allPaths, durationSVG, startingTime, limitTime);
		        	viewed.remove(nextDelivery);
		        	this.executionStack.remove(nextDelivery);
		        	nonViewed.add(nextDelivery);
		        }
			}
			
			//roll back the execution stack properly to have the good initial settings to apply the code just applied
			lastDeliveryTreated = currentDelivery;
			viewed.remove(currentDelivery);
			this.executionStack.remove(currentDelivery);
			if(this.executionStack.size()>0) {
				viewedCost -= allPaths.get(this.executionStack.get(this.executionStack.size()-1)).get(currentDelivery).getLength();
				nonViewed.add(currentDelivery);
			} else {
				continueUnstack = false;
			}
		}
	}
	
	/**
	 * Calculate viewed cost.
	 *
	 * @param viewed the viewed
	 * @param allPaths the all paths
	 * @return the double
	 */
	protected double calculateViewedCost(ArrayList<Delivery> viewed, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths) {
		double viewedCost = 0;
		if(viewed.size()>1) {
			for(int indexViewed = 0; indexViewed<viewed.size()-1; indexViewed++) {
				viewedCost += allPaths.get(viewed.get(indexViewed)).get(viewed.get(indexViewed+1)).getLength();
			}
		} else {
			//viewedCost += 
		}
		return viewedCost;
	}
	
	/**
	 * Gets the best solution.
	 *
	 * @return the best solution
	 */
	public Delivery[] getBestSolution() {
		return bestSolution;
	}

	/**
	 * Sets the best solution.
	 *
	 * @param bestSolution the new best solution
	 */
	public void setBestSolution(Delivery[] bestSolution) {
		this.bestSolution = bestSolution;
	}

	/* (non-Javadoc)
	 * @see main.java.tsp.TSP#getCostBestSolution()
	 */
	public double getCostBestSolution(){
		return costBestSolution;
	}
	
	/**
	 * Save current state of calculation.
	 *
	 * @param currentDelivery the current delivery
	 * @param nonViewed the non viewed
	 * @param viewed the viewed
	 * @param viewedCost the viewed cost
	 * @param duration the duration
	 * @param limitTime the limit time
	 */
	private void saveCurrentStateOfCalculation(Delivery currentDelivery, ArrayList<Delivery> nonViewed, ArrayList<Delivery> viewed, double viewedCost,
			int[] duration, int limitTime) {
		this.currentDeliverySVG = currentDelivery;
		this.nonViewedSVG = nonViewed;
		this.viewedSVG = viewed;
		this.viewedCostSVG = viewedCost;
		this.durationSVG = duration;
	}
	
	/**
	 * Method to redefine in the classes that extend TemplateTSP.
	 *
	 * @param delivery the delivery
	 * @param nonViewed : list of the deliveries we still need to visit
	 * @param allPaths : used as cost to go from every delivery to every other
	 * @param duration : time spent to visit a delivery
	 * @return the minimum path starting and finishing by the repository, passing by every other delivery
	 */
	protected abstract int bound(Delivery delivery, ArrayList<Delivery> nonViewed, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duration);
	
	/**
	 * Method to redefine in the classes that extend TemplateTSP.
	 *
	 * @param currentDelivery the current delivery
	 * @param nonViewed : list of the deliveries we still need to visit
	 * @param allPaths : used as cost to go from every delivery to every other
	 * @param durationSVG2 : time spent to visit a delivery
	 * @return iterator for the non viewed deliveries
	 */
	protected abstract Iterator<Delivery> iterator(Delivery currentDelivery, ArrayList<Delivery> nonViewed,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, int[] durationSVG2);
	
	
	
	
	//////////////////////// Sauvegarder pile d'appel et l'utiliser pour reprendre de la ou on en �tait
	/////////////////////// ---> ArrayList<Delivery> + gestion de l'iterateur qui parcours tjs dans le meme ordre,
	/////////////////////// ce qui permet de ne pas reparcourir des branches deja vues
	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP.
	 *
	 * @param currentDelivery le dernier sommet visite
	 * @param nonViewed : list of the deliveries we still need to visit
	 * @param viewed : list of the deliveries we have already visited
	 * @param viewedCost : cost of the path we are building
	 * @param allPaths : used as cost to go from every delivery to every other
	 * @param duration : time spent to visit a delivery
	 * @param startingTime : time when the resolution started
	 * @param limitTime : maximum time that the resolution is allowed to take
	 * @throws TSPLimitTimeReachedException the TSP limit time reached exception
	 */	
	 private void branchAndBound(Delivery currentDelivery, ArrayList<Delivery> nonViewed, ArrayList<Delivery> viewed, double viewedCost, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duration, long startingTime, int limitTime) throws TSPLimitTimeReachedException{
		 if (System.currentTimeMillis() - startingTime > limitTime){
			 limitTimeReached = true;
			 saveCurrentStateOfCalculation(currentDelivery, nonViewed, viewed, viewedCost, duration, limitTime);
			 throw new TSPLimitTimeReachedException("Branch and Bound of the circuit : " /*+ allPaths.keySet().toString()*/);
		 }
	    if (nonViewed.size() == 0){ // all the deliveries have been visited 
	    	AtomicPath returnToRepository = allPaths.get(currentDelivery).get(viewed.get(0)); //may be split for more readability
	    	if (returnToRepository != null) {
	    		viewedCost += returnToRepository.getLength();
		    	if (viewedCost < costBestSolution){ // we found a better solution than bestSolution
		    		if(bestSolution == null) {
		    			bestSolution = new Delivery[allPaths.keySet().size()];
		    		}
		    		viewed.toArray(bestSolution);
		    		costBestSolution = viewedCost;
		    	}
	    	}
	    } else if (viewedCost + bound(currentDelivery, nonViewed, allPaths, duration) < costBestSolution){
	        Iterator<Delivery> it = iterator(currentDelivery, nonViewed, allPaths, duration);
	        while (it.hasNext()){
	        	Delivery nextDelivery = it.next();
	        	viewed.add(nextDelivery);
	        	this.executionStack.add(nextDelivery);
	        	
	        	nonViewed.remove(nextDelivery);
	        	
	        	branchAndBound(nextDelivery, nonViewed, viewed, viewedCost + allPaths.get(currentDelivery).get(nextDelivery).getLength()/*allPathes[indexCurrentDelivery][prochainSommet]*/ /*+ duration[prochainSommet]*/,
	        			allPaths, duration, startingTime, limitTime);
	        	viewed.remove(nextDelivery);
	        	this.executionStack.remove(nextDelivery);
	        	nonViewed.add(nextDelivery);
	        }
	    }
	}


}

