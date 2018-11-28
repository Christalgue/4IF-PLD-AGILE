package main.java.tsp;

import java.util.Collection;
import java.util.Iterator;

import main.java.entity.Delivery;

public class IteratorSeq implements Iterator<Delivery> {

	private Delivery[] candidates;
	private int nbCandidates;

	/**
	 * create an iterator to iterate on every non viewed delivery
	 * @param nonViewed
	 * @param currentDelivery
	 * TODO refactor to guaranty to see the non viewed deliveries in the good order 
	 */
	public IteratorSeq(Collection<Delivery> nonViewed, Delivery currentDelivery){
		this.candidates = new Delivery[nonViewed.size()];
		nbCandidates = 0;
		for (Delivery s : nonViewed){
			candidates[nbCandidates++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Delivery next() {
		return candidates[--nbCandidates];
	}

	@Override
	public void remove() {}

}
