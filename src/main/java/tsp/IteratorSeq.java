package main.java.tsp;

import java.util.Collection;
import java.util.Iterator;

import main.java.entity.Delivery;

public class IteratorSeq implements Iterator<Delivery> {

	private Delivery[] candidates;
	private int nbCandidates;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorSeq(Collection<Delivery> nonVus, Delivery sommetCrt){
		this.candidates = new Delivery[nonVus.size()];
		nbCandidates = 0;
		for (Delivery s : nonVus){
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
