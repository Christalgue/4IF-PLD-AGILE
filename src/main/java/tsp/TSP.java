package main.java.tsp;

import java.util.HashMap;

import main.java.entity.AtomicPath;
import main.java.entity.Delivery;
import main.java.entity.Repository;
import main.java.exception.TSPLimitTimeReachedException;

/**
 * The Interface for a TSP class that will be used to calculate the solution to the tsp we need to solve.
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
	 * @param allPaths all the paths between each delivery
	 * @param duration duration for each delivery @unused
	 * @param continueInterruptedCalculation true if the method is called after having interrupted the calculation at least once
	 * @throws TSPLimitTimeReachedException when the limit time we set for the calculation is reached before the end of the calculation
	 */
	public void searchSolution(int limitTime, Repository repository, HashMap<Delivery,HashMap<Delivery,AtomicPath>> allPaths, int[] duration, boolean continueInterruptedCalculation) throws TSPLimitTimeReachedException;
	
	/**
	 * Gets the cost of the best solution.
	 *
	 * @return the cost of the best solution
	 */
	public double getCostBestSolution();
}
