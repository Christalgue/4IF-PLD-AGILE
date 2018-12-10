package main.java.tsp;

import java.util.*;

import main.java.entity.AtomicPath;
import main.java.entity.Delivery;

// TODO: Auto-generated Javadoc
/**
 * The Class TSP1.
 */
public class TSP1 extends TemplateTSP {

	/* (non-Javadoc)
	 * @see main.java.tsp.TemplateTSP#bound(main.java.entity.Delivery, java.util.ArrayList, java.util.HashMap, int[])
	 */
	@Override
	protected int bound(Delivery delivery, ArrayList<Delivery> nonViewed,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths,int[] duration) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see main.java.tsp.TemplateTSP#iterator(main.java.entity.Delivery, java.util.ArrayList, java.util.HashMap, int[])
	 */
	@Override
	protected Iterator<Delivery> iterator(Delivery currentDelivery, ArrayList<Delivery> nonViewed,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, int[] duration) {
		
		return new IteratorSeq(nonViewed, currentDelivery, allPaths, duration);
	}
}
