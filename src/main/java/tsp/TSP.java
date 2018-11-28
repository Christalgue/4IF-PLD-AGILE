package main.java.tsp;

import java.util.*;
import main.java.entity.*;
import main.java.exception.TSPLimitTimeReachedException;

public interface TSP {
		
	/**
	 */
	public Boolean getLimitTimeReached();
	
	/**
	 * @throws TSPLimitTimeReachedException 
	 */
	public void searchSolution(int limitTime, Repository repository, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duration) throws TSPLimitTimeReachedException;
	
	/**
	 */
	public Delivery getDeliveryInBestSolutionAtIndex(int index);
	
	/** 
	 */
	public double getCostBestSolution();
}
