package main.java.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import main.java.entity.AtomicPath;
import main.java.entity.Delivery;

// TODO: Auto-generated Javadoc
/**
 * The Class IteratorSeq.
 */
public class IteratorSeq implements Iterator<Delivery> {

	/** The candidates. */
	private Delivery[] candidates;
	
	/** The nb candidates. */
	private int nbCandidates;

	/**
	 * create an iterator to iterate on every non viewed delivery.
	 *
	 * @param nonViewed the non viewed
	 * @param currentDelivery TODO refactor to guaranty to see the non viewed deliveries in the good order 
	 * @param allPaths TODO
	 * @param duration TODO
	 */
	public IteratorSeq(Collection<Delivery> nonViewed, Delivery currentDelivery, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, int[] duration){
		this.candidates = new Delivery[nonViewed.size()];
		nbCandidates = 0;
		ArrayList<Delivery> stillToAdd = new ArrayList<Delivery>(nonViewed);
		for(int nbDeliveriesAddedToIteratorFromNonViewed = 0; nbDeliveriesAddedToIteratorFromNonViewed < nonViewed.size(); nbDeliveriesAddedToIteratorFromNonViewed++) {
			double maxDistance = Double.MIN_VALUE;
			Delivery toAdd = null;
			Delivery dontAdd = null;
			for(Delivery d : stillToAdd) {
				if(allPaths.get(currentDelivery).containsKey(d)) {
					double distance = allPaths.get(currentDelivery).get(d).getLength();
					if(distance > maxDistance) {
						toAdd = d;
						maxDistance = distance;
					} 
				} else {
					dontAdd = d;
					break;
				}
			}
			if (toAdd != null) {
				candidates[nbCandidates++] = toAdd;
				stillToAdd.remove(toAdd);
			}
			if(dontAdd != null) {
				stillToAdd.remove(dontAdd);
			}
		}
		
		/*for (Delivery s : nonViewed){
			candidates[nbCandidates++] = s;
		}*/
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Delivery next() {
		return candidates[--nbCandidates];
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {}

}
