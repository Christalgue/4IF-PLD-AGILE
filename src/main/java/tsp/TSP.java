package main.java.tsp;

import java.util.*;
import main.java.entity.*;

public interface TSP {
		
	/**
	 */
	public Boolean getLimitTimeReached();
	
	/**
	 */
	public void searchSolution(int limitTime, Repository repository, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duration);
	
	/**
	 */
	public Delivery getDeliveryInBestSolutionAtIndex(int index);
	
	/** 
	 */
	public double getCostBestSolution();
}
