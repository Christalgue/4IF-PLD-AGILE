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
	
	public void searchSolution(int limitTime, List<Delivery> deliveries, AtomicPath[][] allPathes, int[] duration){
		limitTimeReached = false;
		costBestSolution = Integer.MAX_VALUE;
		bestSolution = new Delivery[deliveries.size()];
		ArrayList<Delivery> nonViewed = new ArrayList<Delivery>(deliveries);
		//for (int indexListDeliveries=1; indexListDeliveries<deliveries.size(); indexListDeliveries++) nonViewed.add(indexListDeliveries, deliveries.get(indexListDeliveries));
		ArrayList<Delivery> viewed = new ArrayList<Delivery>(deliveries.size());
		viewed.add(0, deliveries.get(0)); // le premier sommet visite est 0
		branchAndBound(0, nonViewed, viewed, 0, allPathes, duration, System.currentTimeMillis(), limitTime);
	}
	
	public Delivery getBestSolution(int i){
		if ((bestSolution == null) || (i<0) || (i>=bestSolution.length))
			return null;
		return bestSolution[i];
	}
	
	public double getCostBestSolution(){
		return costBestSolution;
	}
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param sommetCourant
	 * @param nonVus : tableau des sommets restant a visiter
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return une borne inferieure du cout des permutations commencant par sommetCourant, 
	 * contenant chaque sommet de nonVus exactement une fois et terminant par le sommet 0
	 */
	protected abstract int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree);
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param sommetCrt
	 * @param nonVus : tableau des sommets restant a visiter
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree);
	
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
	 void branchAndBound(int indexCurrentDelivery, ArrayList<Delivery> nonViewed, ArrayList<Delivery> viewed, double viewedCost, AtomicPath[][] allPathes, int[] duration, long startingTime, int limitTime){
		 if (System.currentTimeMillis() - startingTime > limitTime){
			 limitTimeReached = true;
			 return;
		 }
	    if (nonViewed.size() == 0){ // tous les sommets ont ete visites
	    	AtomicPath returnToRepository = allPathes[indexCurrentDelivery][0];
	    	viewedCost += returnToRepository.getLength();
	    	if (viewedCost < costBestSolution){ // on a trouve une solution meilleure que meilleureSolution
	    		viewed.toArray(bestSolution);
	    		costBestSolution = viewedCost;
	    	}
	    } /*else if (viewedCost + bound(indexCurrentDelivery, nonViewed, allPathes, duration) < costBestSolution){
	        Iterator<Integer> it = iterator(indexCurrentDelivery, nonViewed, allPathes, duration);
	        while (it.hasNext()){
	        	Integer prochainSommet = it.next();
	        	viewed.add(prochainSommet);
	        	nonViewed.remove(prochainSommet);
	        	branchAndBound(prochainSommet, nonViewed, viewed, viewedCost + allPathes[indexCurrentDelivery][prochainSommet] + duration[prochainSommet], allPathes, duration, startingTime, limitTime);
	        	viewed.remove(prochainSommet);
	        	nonViewed.add(prochainSommet);
	        }  
	    }*/
	}
}

