package main.java.tsp;

import java.util.*;

import main.java.entity.*;

public abstract class TemplateTSP implements TSP {
	
	private Delivery[] bestSolution;
	private double costBestSolution = 0;
	private Boolean limitTimeReached;
	
	public Boolean getLimitTimeReached(){
		return limitTimeReached;
	}
	
	public void searchSolution(int limitTime, Repository repository, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, int[] duration){
		limitTimeReached = false;
		costBestSolution = Integer.MAX_VALUE;
		Set<Delivery> deliveries = allPaths.keySet();
		bestSolution = new Delivery[deliveries.size()];
		ArrayList<Delivery> nonViewed = new ArrayList<Delivery>();
		nonViewed.addAll(allPaths.keySet());
		nonViewed.remove(repository);
		ArrayList<Delivery> viewed = new ArrayList<Delivery>(deliveries.size());
		viewed.add(0, repository); // the first "node" visited in the repository 
		branchAndBound(0, nonViewed, viewed, 0, allPaths, duration, System.currentTimeMillis(), limitTime);
		// TODO need to consider the time spent delivering the order at each delivery point.
	}
	
	public Delivery getDeliveryInBestSolutionAtIndex(int index){
		if ((bestSolution == null) || (index<0) || (index>=bestSolution.length))
			return null;
		return bestSolution[index];
	}
	
	
	public Delivery[] getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(Delivery[] bestSolution) {
		this.bestSolution = bestSolution;
	}

	public double getCostBestSolution(){
		return costBestSolution;
	}
	
	/**
	 * Method to redefine in the classes that extend TemplateTSP
	 * @param delivery
	 * @param nonViewed : list of the deliveries we still need to visit
	 * @param allPaths : used as cost to go from every delivery to every other
	 * @param duration : time spent to visit a delivery
	 * @return the minimum path starting and finishing by the repository, passing by every other delivery
	 */
	protected abstract int bound(Delivery delivery, ArrayList<Delivery> nonViewed, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duration);
	
	/**
	 * Method to redefine in the classes that extend TemplateTSP
	 * @param currentDelivery
	 * @param nonViewed : list of the deliveries we still need to visit
	 * @param allPaths : used as cost to go from every delivery to every other
	 * @param duration : time spent to visit a delivery
	 * @return iterator for the non viewed deliveries
	 */
	protected abstract Iterator<Delivery> iterator(Delivery currentDelivery, ArrayList<Delivery> nonViewed,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, int[] duration);
	
	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP
	 * @param indexCurrentDelivery le dernier sommet visite
	 * @param nonViewed : list of the deliveries we still need to visit
	 * @param viewed : list of the deliveries we have already visited
	 * @param viewedCost : cost of the path we are building
	 * @param allPathes : used as cost to go from every delivery to every other
	 * @param duration : time spent to visit a delivery
	 * @param startingTime : time when the resolution started
	 * @param limitTime : maximum time that the resolution is allowed to take
	 */	
	 void branchAndBound(int indexCurrentDelivery, ArrayList<Delivery> nonViewed, ArrayList<Delivery> viewed, double viewedCost, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPathes, int[] duration, long startingTime, int limitTime){
		 if (System.currentTimeMillis() - startingTime > limitTime){
			 limitTimeReached = true;
			 return;
		 }
	    if (nonViewed.size() == 0){ // all the deliveries have been visited 
	    	AtomicPath returnToRepository = allPathes.get(viewed.get(indexCurrentDelivery)).get(viewed.get(0)); //may be split for more readability
	    	viewedCost += returnToRepository.getLength();
	    	if (viewedCost < costBestSolution){ // we found a better solution than bestSolution
	    		viewed.toArray(bestSolution);
	    		costBestSolution = viewedCost;
	    	}
	    } else if (viewedCost + bound(viewed.get(indexCurrentDelivery), nonViewed, allPathes, duration) < costBestSolution){
	    	
	        Iterator<Delivery> it = iterator(viewed.get(indexCurrentDelivery), nonViewed, allPathes, duration);
	        while (it.hasNext()){
	        	Delivery nextDelivery = it.next();
	        	viewed.add(nextDelivery);
	        	nonViewed.remove(nextDelivery);
	        	
	        	// Some bullshit there
	        	/*
	        	System.out.println("");
	        	System.out.println("Deliveries currentDelivery : "+viewed.get(indexCurrentDelivery).getPosition().getId());
        		System.out.println("Deliveries nextDelivery : "+nextDelivery.getPosition().getId());
        		System.out.println("Taille viewed : "+viewed.size());
        		System.out.println("Taille nonviewed : "+nonViewed.size());
        		System.out.println("");
        		
	        	if(allPathes.get(viewed.get(indexCurrentDelivery)).get(nextDelivery).getLength() == null) { // commenter le .getLength()
	        		System.out.println("C'EST CET ARGUMENT DE MERDE QUI FAIT CHIER!!!");
	        		
	        		System.out.println("Deliveries currentDelivery : "+viewed.get(indexCurrentDelivery).getPosition().getId());
	        		System.out.println("Deliveries nextDelivery : "+nextDelivery.getPosition().getId());
	        		
	        		System.out.println("Taille viewed : "+viewed.size());	        		
	        	}*/
	        	
	        	branchAndBound(viewed.indexOf(nextDelivery), nonViewed, viewed, viewedCost + allPathes.get(viewed.get(indexCurrentDelivery)).get(nextDelivery).getLength()/*allPathes[indexCurrentDelivery][prochainSommet]*/ /*+ duration[prochainSommet]*/, allPathes, duration, startingTime, limitTime);
	        	viewed.remove(nextDelivery);
	        	nonViewed.add(nextDelivery);
	        }  
	    }
	}


}

