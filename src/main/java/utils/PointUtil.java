package main.java.utils;

import java.util.HashMap;
import java.util.Map;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.entity.CircuitManagement;

public class PointUtil {
	
	public static double range;
	
	public static Node pointToNode( Point point, CircuitManagement circuitManagement) {
		
		HashMap<Long, Node> nodeMap = circuitManagement.getCurrentMap().getNodeMap();

	    System.out.println(range);
		
		for( Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
			Node currentNode = entry.getValue();
			
			if (currentNode.getLongitude() <= point.getX()+range && point.getX()-range <= currentNode.getLongitude() && 
				currentNode.getLatitude() <= point.getY()+range && point.getY()-range <= currentNode.getLatitude())
				return currentNode;
				
		}
		 return null;
		
	}
	
	

}
