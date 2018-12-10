package main.java.tsp;

import java.util.*;
import main.java.entity.*;
import main.java.exception.TSPLimitTimeReachedException;

// TODO: Auto-generated Javadoc
/**
 * The Interface TSP.
 */
public interface TSP {
		
	/**
	 * Gets the limit time reached.
	 *
	 * @return the limit time reached
	 */
	public Boolean getLimitTimeReached();
	
	/**
	 * Search solution.
	 *
	 * @param limitTime the limit time
	 * @param repository the repository
	 * @param allPaths the all paths
	 * @param duration the duration
	 * @param continueInterruptedCalculation TODO
	 * @throws TSPLimitTimeReachedException the TSP limit time reached exception
	 */
	public void searchSolution(int limitTime, Repository repository, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duration, boolean continueInterruptedCalculation) throws TSPLimitTimeReachedException;
	
	/**
	 * Gets the delivery in best solution at index.
	 *
	 * @param index the index
	 * @return the delivery in best solution at index
	 */
	public Delivery getDeliveryInBestSolutionAtIndex(int index);
	
	/**
	 * Gets the cost best solution.
	 *
	 * @return the cost best solution
	 */
	public double getCostBestSolution();
}
