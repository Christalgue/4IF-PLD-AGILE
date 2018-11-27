package main.java.tsp;

import java.util.*;
import main.java.entity.*;

public interface TSP {
		
	/**
	 * @return true si chercheSolution() s'est terminee parce que la limite de temps avait ete atteinte, avant d'avoir pu explorer tout l'espace de recherche,
	 */
	public Boolean getLimitTimeReached();
	
	/**
	 * Cherche un circuit de duree minimale passant par chaque sommet (compris entre 0 et nbSommets-1)
	 * @param limitTime : limite (en millisecondes) sur le temps d'execution de chercheSolution
	 * @param deliveries TODO
	 * @param allPaths : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 */
	public void searchSolution(int limitTime, Repository repository, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duree);
	
	/**
	 * @param index
	 * @return le sommet visite en i-eme position dans la solution calculee par chercheSolution
	 */
	public Delivery getDeliveryInBestSolutionAtIndex(int index);
	
	/** 
	 * @return la duree de la solution calculee par chercheSolution
	 */
	public double getCostBestSolution();
}
