package main.java.tsp;

import java.util.*;

import main.java.entity.*;
import main.java.exception.TSPLimitTimeReachedException;

// TODO: Auto-generated Javadoc
///////////////////////////::
//////////////////////////
///////////////////////////
//////////////////////////
/////// ON DIRAIT QUE DES TRUCS MODIFIENT ALLPATHS EN COURS DE ROUTE DANS CONTINUE CALCULATION !!!!!!!
/////////////////////////////////////
////////////////////////////////////
/**
 * The Class TemplateTSP.
 */
////////////////////////////////////
public abstract class TemplateTSP implements TSP {
	
	/** The best solution. */
	private Delivery[] bestSolution;
	
	/** The cost best solution. */
	private double costBestSolution = 0;
	
	/** The limit time reached. */
	private Boolean limitTimeReached;
	
	
	/** The current delivery SVG. */
	private Delivery currentDeliverySVG;
	
	/** The non viewed SVG. */
	private ArrayList<Delivery> nonViewedSVG = null;
	
	/** The viewed SVG. */
	private ArrayList<Delivery> viewedSVG = null;
	
	/** The viewed cost SVG. */
	private double viewedCostSVG;
	
	/** The duration SVG. */
	private int[] durationSVG = null;
	
	/** The execution stack. */
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
			System.out.println("premier clacul du tsp");
			costBestSolution = Integer.MAX_VALUE;
			ArrayList<Delivery> nonViewed = new ArrayList<Delivery>();
			nonViewed.addAll(allPaths.keySet());
			nonViewed.remove(repository);
			ArrayList<Delivery> viewed = new ArrayList<Delivery>();
			viewed.add(repository); // the first "node" visited in the repository 
			this.executionStack = new ArrayList<Delivery>();
			this.executionStack.add(repository);
			/*try {*/
				branchAndBound(repository, nonViewed, viewed, 0, allPaths, duration, System.currentTimeMillis(), limitTime);
			/*} catch (TSPLimitTimeReachedException e) {
				System.out.println(e.getMessage());
			}*/
			
		} else {
			//System.out.println("passage par searchSolution");
			System.out.println("passage dans le tsp suite a un continue");
			//System.out.println("indexCurrentDeliverySVG " + indexCurrentDeliverySVG);
			//System.out.println("nonViewedSVG " + nonViewedSVG);
			//System.out.println("viewedSVG " + viewedSVG);
			//System.out.println("viewedCostSVG " + viewedCostSVG);
			System.out.println("allPathsSVG " + allPaths);
			continueResearchSolution(limitTime, allPaths, System.currentTimeMillis());
			//branchAndBound(currentDeliverySVG, nonViewedSVG, viewedSVG, viewedCostSVG, allPathsSVG, durationSVG, System.currentTimeMillis(), limitTime);
		}
		
		// TODO need to consider the time spent delivering the order at each delivery point.
	}
	
	/**
	 * Continue research solution.
	 *
	 * @param limitTime the limit time
	 * @param allPaths the all paths
	 * @param startingTime the starting time
	 * @throws TSPLimitTimeReachedException the TSP limit time reached exception
	 */
	protected void continueResearchSolution(int limitTime, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, long startingTime) throws TSPLimitTimeReachedException {
		//end the branch and bound that was ongoing when saving
		ArrayList<Delivery> viewed = new ArrayList<Delivery>(viewedSVG);
		ArrayList<Delivery> nonViewed = new ArrayList<Delivery>(nonViewedSVG);
		double viewedCost = viewedCostSVG;
		branchAndBound(currentDeliverySVG, nonViewed, viewed, viewedCost, allPaths, durationSVG, startingTime, limitTime);
		System.out.println("putain ca plante");
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
			System.out.println("test");
			
			//continue the branch and bound that was ongoing for the last delivery of the execution stack
				//create the iterator and fix it to the good value
			currentDelivery = this.executionStack.get(this.executionStack.size()-1);
			Iterator<Delivery> it = iterator(currentDelivery, nonViewed, allPaths, durationSVG);
			
			nextDeliveryFound = false;
			while(it.hasNext() && nextDeliveryFound == false) {
				System.out.println("il est passé par ici");
				nextDelivery = it.next();
				nextDeliveryFound = (nextDelivery == lastDeliveryTreated);
				
			}
			//if still deliveries to branch and bound then go and execute it for every one left to explore.
			if(nextDeliveryFound) {
				System.out.println("il repassera par la");
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
				System.out.println("ca couille sa race");
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
		System.out.println("calculateVC");
		if(viewed.size()>1) {
			for(int indexViewed = 0; indexViewed<viewed.size()-1; indexViewed++) {
				viewedCost += allPaths.get(viewed.get(indexViewed)).get(viewed.get(indexViewed+1)).getLength();
			}
		} else {
			//viewedCost += 
		}
		return viewedCost;
	}
	
	/* (non-Javadoc)
	 * @see main.java.tsp.TSP#getDeliveryInBestSolutionAtIndex(int)
	 */
	public Delivery getDeliveryInBestSolutionAtIndex(int index){
		if ((bestSolution == null) || (index<0) || (index>=bestSolution.length))
			return null;
		return bestSolution[index];
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
		System.out.println("passage par saveCurrentStateOfCalculation");
		this.currentDeliverySVG = currentDelivery;
		this.nonViewedSVG = nonViewed;
		this.viewedSVG = viewed;
		this.viewedCostSVG = viewedCost;
		this.durationSVG = duration;
		System.out.println("fin sauvegarde etat");
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
	
	
	
	
	//////////////////////// Sauvegarder pile d'appel et l'utiliser pour reprendre de la ou on en était
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
		 System.out.println("passage par B&B");
		 //System.out.println("execution stack : " + this.executionStack.toString());
		 //System.out.println("viewedCost " + viewedCost);
		 if (System.currentTimeMillis() - startingTime > limitTime){
			 System.out.println("coucou");
			 System.out.println("temps Limite atteint");
			 limitTimeReached = true;
			 saveCurrentStateOfCalculation(currentDelivery, nonViewed, viewed, viewedCost, duration, limitTime);
			 throw new TSPLimitTimeReachedException("Branch and Bound of the circuit : " /*+ allPaths.keySet().toString()*/);
		 }
	    if (nonViewed.size() == 0){ // all the deliveries have been visited 
	    	System.out.println("////////////**********************//////////////////////***********************");
	    	System.out.println("tous les chemins visites");
	    	AtomicPath returnToRepository = allPaths.get(currentDelivery).get(viewed.get(0)); //may be split for more readability
	    	if (returnToRepository != null) {
	    		viewedCost += returnToRepository.getLength();
		    	if (viewedCost < costBestSolution){ // we found a better solution than bestSolution
		    		if(bestSolution == null) {
		    			bestSolution = new Delivery[allPaths.keySet().size()];
		    		}
		    		viewed.toArray(bestSolution);
		    		costBestSolution = viewedCost;
		    		System.out.println("solution enregistree");
		    	}
	    	}
	    } else if (viewedCost + bound(currentDelivery, nonViewed, allPaths, duration) < costBestSolution){
	    	//System.out.println("exploration du noeud : "+ currentDelivery);
	    	//System.out.println(nonViewed.toString());
	    	System.out.println("salut");
	        Iterator<Delivery> it = iterator(currentDelivery, nonViewed, allPaths, duration);
	        while (it.hasNext()){
	        	//System.out.println("op 1");
	        	Delivery nextDelivery = it.next();
	        	//System.out.println("op 2");
	        	viewed.add(nextDelivery);
	        	this.executionStack.add(nextDelivery);
	        	
	        	//System.out.println("op 3");
	        	nonViewed.remove(nextDelivery);
	        	
	        	branchAndBound(nextDelivery, nonViewed, viewed, viewedCost + allPaths.get(currentDelivery).get(nextDelivery).getLength()/*allPathes[indexCurrentDelivery][prochainSommet]*/ /*+ duration[prochainSommet]*/,
	        			allPaths, duration, startingTime, limitTime);
	        	//System.out.println("op5");
	        	viewed.remove(nextDelivery);
	        	this.executionStack.remove(nextDelivery);
	        	//System.out.println("op 6");
	        	nonViewed.add(nextDelivery);
	        	//System.out.println("op 7");
	        }
	    }
	}


}

