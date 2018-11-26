package main.java.tsp;

import java.util.*;

import main.java.entity.AtomicPath;
import main.java.entity.Delivery;

public class TSP1 extends TemplateTSP {

	@Override
	protected int bound(Delivery delivery, ArrayList<Delivery> nonViewed,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathes, int[] duree) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Iterator<Delivery> iterator(Delivery currentDelivery, ArrayList<Delivery> nonViewed,
			HashMap<Delivery, HashMap<Delivery, AtomicPath>> allPathes, int[] duration) {
		// TODO Auto-generated method stub
		return new IteratorSeq(nonViewed, currentDelivery);
	}
}
