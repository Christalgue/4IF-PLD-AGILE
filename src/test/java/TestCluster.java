package test.java;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.exception.ClusteringException;

public class TestCluster {

	public static void main (String[] args) {
		
		Node a = new Node(1,0.5,0.5);
		Node b = new Node(2,0.25,2.75);
		Node c = new Node(3,1.25,1.75);
		Node d = new Node(4,1.25,2.75);
		Node e = new Node(5,1.75,1.25);
		Node f = new Node(6,2,2);
		Node g = new Node(7,2.5,0.5);
		Node h = new Node(8,2.5,2.5);
		Node i = new Node(9,2.75,5.5);
		Node j = new Node(10,3,1);
		Node k = new Node(11,3,5);
		Node l = new Node(12,3.25,2.5);
		Node m = new Node(13,3.25,3.75);
		Node n = new Node(14,3.75,3.5);
		Node o = new Node(15,4.25,3.25);
		Node p = new Node(16,4.5,3.75);
		Node q = new Node(17,4.5,4.5);
		Node r = new Node(18,4.75,4.75);
		Node s = new Node(19,4,6);
		
		Delivery delA = new Delivery(a,0);
		Delivery delB = new Delivery(b,0);
		Delivery delC = new Delivery(c,0);
		Delivery delD = new Delivery(d,0);
		Delivery delE = new Delivery(e,0);
		Delivery delF = new Delivery(f,0);
		Delivery delG = new Delivery(g,0);
		Delivery delH = new Delivery(h,0);
		Delivery delI = new Delivery(i,0);
		Delivery delJ = new Delivery(j,0);
		Delivery delK = new Delivery(k,0);
		Delivery delL = new Delivery(l,0);
		Delivery delM = new Delivery(m,0);
		Delivery delN = new Delivery(n,0);
		Delivery delO = new Delivery(o,0);
		Delivery delP = new Delivery(p,0);
		Delivery delQ = new Delivery(q,0);
		Delivery delR = new Delivery(r,0);
		Delivery delS = new Delivery(s,0);
		
		List<Delivery> arrivalDeliveries = new ArrayList<Delivery>();
		
		arrivalDeliveries.add(delA);
		arrivalDeliveries.add(delB);
		arrivalDeliveries.add(delC);
		arrivalDeliveries.add(delD);
		arrivalDeliveries.add(delE);
		arrivalDeliveries.add(delF);
		arrivalDeliveries.add(delG);
		arrivalDeliveries.add(delH);
		arrivalDeliveries.add(delI);
		arrivalDeliveries.add(delJ);
		arrivalDeliveries.add(delK);
		arrivalDeliveries.add(delL);
		arrivalDeliveries.add(delM);
		arrivalDeliveries.add(delN);
		arrivalDeliveries.add(delO);
		arrivalDeliveries.add(delP);
		arrivalDeliveries.add(delQ);
		arrivalDeliveries.add(delR);
		arrivalDeliveries.add(delS);
		
		CircuitManagement CircuitManager = new CircuitManagement();
		CircuitManager.setDeliveryList(arrivalDeliveries);
		
		int nbDeliveryMan = 4;
		
		CircuitManager.setNbDeliveryMan(nbDeliveryMan);
		
		System.out.println("TEST Clustering");
		
		/*List<Pair<Double,Double>> barycenters = new ArrayList<Pair<Double,Double>>(nbDeliveryMan);
    	for (int barycenterIndex = 0 ; barycenterIndex < nbDeliveryMan ; barycenterIndex++) {
    		CircuitManager.addRandomBarycenter(barycenters);
    	}*/
    	
    	/*
    	List<ArrayList<Delivery>> distribution = CircuitManager.KmeansClusteringStep(barycenters);
    	for (ArrayList<Delivery> deliveries : distribution) {
    		Pair<Double,Double> barycenter = barycenters.get(distribution.indexOf(deliveries));
    		System.out.println("");
    		System.out.println("Livreur : ");
    		System.out.println("Barycentre : "+barycenter.getKey()+" (lat) / "+barycenter.getValue()+" (long)");
    		for (Delivery del : deliveries) {
    			System.out.println("Livraison "+del.getPosition().getId());
    		}
    	}
    	*/
		
    	List<ArrayList<Delivery>> distribution;
		try {
			distribution = CircuitManager.cluster();
			for (ArrayList<Delivery> deliveries : distribution) {
	    		System.out.println("");
	    		System.out.println("Livreur : ");
	    		for (Delivery del : deliveries) {
	    			System.out.println("Livraison "+del.getPosition().getId());
	    		}
	    	}
		} catch (ClusteringException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
	}

}
