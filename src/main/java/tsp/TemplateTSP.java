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
		//for (int indexListDeliveries=1; indexListDeliveries<deliveries.size(); indexListDeliveries++) nonViewed.add(indexListDeliveries, deliveries.get(indexListDeliveries));
		ArrayList<Delivery> viewed = new ArrayList<Delivery>(deliveries.size());
		viewed.add(0, repository); // le premier sommet visite est l'entrepot
		///duration must be sent with maxValue for each node to visit --> find an adaptation for our model
		    ///HashMap<Delivery,ShortestAtomicPathToGoToThisDelivery> ?     
		branchAndBound(0, nonViewed, viewed, 0, allPaths, duration, System.currentTimeMillis(), limitTime);
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
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param delivery
	 * @param nonViewed : tableau des sommets restant a visiter
	 * @param allPaths : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return une borne inferieure du cout des permutations commencant par sommetCourant, 
	 * contenant chaque sommet de nonVus exactement une fois et terminant par le sommet 0
	 */
	protected abstract int bound(Delivery delivery, ArrayList<Delivery> nonViewed, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duree);
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param currentDelivery
	 * @param nonViewed : tableau des sommets restant a visiter
	 * @param allPaths : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<Delivery> iterator(Delivery currentDelivery, ArrayList<Delivery> nonViewed,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, int[] duration);
	
	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP
	 * @param indexCurrentDelivery le dernier sommet visite
	 * @param nonViewed la liste des sommets qui n'ont pas encore ete visites
	 * @param viewed la liste des sommets visites (y compris sommetCrt)
	 * @param viewedCost la somme des couts des arcs du chemin passant par tous les sommets de vus + la somme des duree des sommets de vus
	 * @param allPathes : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @param startingTime : moment ou la resolution a commence
	 * @param limitTime : limite de temps pour la resolution
	 */	
	 void branchAndBound(int indexCurrentDelivery, ArrayList<Delivery> nonViewed, ArrayList<Delivery> viewed, double viewedCost, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPathes, int[] duration, long startingTime, int limitTime){
		 if (System.currentTimeMillis() - startingTime > limitTime){
			 limitTimeReached = true;
			 return;
		 }
	    if (nonViewed.size() == 0){ // tous les sommets ont ete visites
	    	AtomicPath returnToRepository = allPathes.get(viewed.get(indexCurrentDelivery)).get(viewed.get(0)); //may be split for more readability
	    	viewedCost += returnToRepository.getLength();
	    	if (viewedCost < costBestSolution){ // on a trouve une solution meilleure que meilleureSolution
	    		viewed.toArray(bestSolution);
	    		costBestSolution = viewedCost;
	    	}
	    } else if (viewedCost + bound(viewed.get(indexCurrentDelivery), nonViewed, allPathes, duration) < costBestSolution){
	    	
	        Iterator<Delivery> it = iterator(viewed.get(indexCurrentDelivery), nonViewed, allPathes, duration);
	        while (it.hasNext()){
	        	Delivery nextDelivery = it.next();
	        	viewed.add(nextDelivery);
	        	nonViewed.remove(nextDelivery);
	        	
	        	// Y A DU NULL ICI
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

