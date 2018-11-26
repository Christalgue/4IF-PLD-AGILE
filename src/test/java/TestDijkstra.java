package test.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.java.entity.AtomicPath;
import main.java.entity.Bow;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.entity.Node;
import main.java.exception.DijkstraException;

public class TestDijkstra {

	public static void main (String[] args) {
		System.out.println("Test Dijkstra");
		
		
		HashMap<Long, Node> tempNodeMap = new HashMap<Long, Node>();
		HashMap<Long,Set<Bow>> tempBowMap = new HashMap<Long,Set<Bow>>();
		
		Node a = new Node(1,1,0);
		Node b = new Node(2,0,1);
		Node c = new Node(3,0,2);
		Node d = new Node(4,2,2);
		Node e = new Node(5,2,1);
		
		tempNodeMap.put(a.getId(), a);
		tempNodeMap.put(b.getId(), b);
		tempNodeMap.put(c.getId(), c);
		tempNodeMap.put(d.getId(), d);
		tempNodeMap.put(e.getId(), e);
		
		Bow arc1 = new Bow(a,b,"ab",3);
		Bow arc2 = new Bow(a,e,"ae",5);
		Set<Bow> setA = new HashSet<Bow>();
		setA.add(arc1);
		setA.add(arc2);
		
		Bow arc3 = new Bow(b,e,"be",2);
		Bow arc4 = new Bow(b,c,"bc",6);
		Set<Bow> setB = new HashSet<Bow>();
		setB.add(arc3);
		setB.add(arc4);
		
		Bow arc5 = new Bow(c,d,"cd",2);
		Set<Bow> setC = new HashSet<Bow>();
		setC.add(arc5);
		
		Bow arc6 = new Bow(d,a,"da",3);
		Bow arc7 = new Bow(d,c,"dc",7);
		Set<Bow> setD = new HashSet<Bow>();
		setD.add(arc6);
		setD.add(arc7);
		
		Bow arc8 = new Bow(e,b,"eb",1);
		Bow arc9 = new Bow(e,c,"ec",4);
		Bow arc10 = new Bow(e,d,"ed",6);
		Set<Bow> setE = new HashSet<Bow>();
		setE.add(arc8);
		setE.add(arc9);
		setE.add(arc10);
		
		tempBowMap.put(a.getId(), setA);
		tempBowMap.put(b.getId(), setB);
		tempBowMap.put(c.getId(), setC);
		tempBowMap.put(d.getId(), setD);
		tempBowMap.put(e.getId(), setE);
		
		Map map = new Map();
		map.setBowMap(tempBowMap);
		map.setNodeMap(tempNodeMap);
		
		Delivery delA = new Delivery(a,0);
		Delivery delB = new Delivery(b,0);
		Delivery delC = new Delivery(c,0);
		Delivery delD = new Delivery(d,0);
		Delivery delE = new Delivery(e,0);
		
		List<Delivery> arrivalDeliveries = new ArrayList<Delivery>();
		
		arrivalDeliveries.add(delA);
		arrivalDeliveries.add(delB);
		arrivalDeliveries.add(delC);
		arrivalDeliveries.add(delD);
		arrivalDeliveries.add(delE);
		
		try {
			AtomicPath[] myPaths = map.findShortestPath(delA, arrivalDeliveries);
			for (AtomicPath path : myPaths) {
				System.out.println("Atomic Path : ");
				/*for (Bow currentBow : path.) {
					
				}*/
			}
			
			System.out.println("FIN TEST");
		} catch (DijkstraException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
