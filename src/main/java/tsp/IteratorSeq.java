package main.java.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import main.java.entity.AtomicPath;
import main.java.entity.Delivery;

/**
 * The Class IteratorSeq that is an iterator on the non viewed deliveries iterating from the nearest to the most distant delivery from the current delivery.
 */
public class IteratorSeq implements Iterator<Delivery> {

	/** The candidates. */
	private Delivery[] candidates;
	
	/** The number of candidates. */
	private int nbCandidates;

	/**
	 * create an iterator to iterate on every non viewed delivery sorted by proximity from the current delivery.
	 *
	 * @param nonViewed the non viewed deliveries
	 * @param currentDelivery the current delivery
	 * @param allPaths all the AtomicPath between each Delivery in the deliveryList
	 * @param speed the average speed of a delivery man
	 */
	public IteratorSeq(Collection<Delivery> nonViewed, Delivery currentDelivery, HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPaths, double speed){
		this.candidates = new Delivery[nonViewed.size()];
		nbCandidates = 0;
		ArrayList<Delivery> stillToAdd = new ArrayList<Delivery>(nonViewed);
		for(int nbDeliveriesAddedToIteratorFromNonViewed = 0; nbDeliveriesAddedToIteratorFromNonViewed < nonViewed.size(); nbDeliveriesAddedToIteratorFromNonViewed++) {
			double maxTime = Double.MIN_VALUE;
			Delivery toAdd = null;
			Delivery dontAdd = null;
			//add every delivery in the candidates array sorted by proximity from the current delivery, the most distant is the first one to be added 
			for(Delivery deliveryTested : stillToAdd) {
				if(allPaths.get(currentDelivery).containsKey(deliveryTested)) {
					double time = allPaths.get(currentDelivery).get(deliveryTested).getLength()/speed + deliveryTested.getDuration();
					if(time > maxTime) {
						toAdd = deliveryTested;
						maxTime = time;
					} 
				} else {						//if we can't go from the current delivery to the delivery d then don't add it to the list that will be iterated
					dontAdd = deliveryTested;
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
