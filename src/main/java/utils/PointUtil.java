package main.java.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.entity.CircuitManagement;

// TODO: Auto-generated Javadoc
/**
 * The Class PointUtil.
 */
public class PointUtil {
	
	/** The range. */
	public static double range;
	
	/**
	 * Point to node.
	 *
	 * @param point the point
	 * @param circuitManagement the circuit management
	 * @return the node
	 */
	public static Node pointToNode( Point point, CircuitManagement circuitManagement) {
		
		HashMap<Long, Node> nodeMap = circuitManagement.getCurrentMap().getNodeMap();

	    ArrayList<Node> nodeInReach = new ArrayList<Node>();
		
		for( Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
			Node currentNode = entry.getValue();
			
			if (currentNode.getLongitude() <= point.getX()+range && point.getX()-range <= currentNode.getLongitude() && 
				currentNode.getLatitude() <= point.getY()+range && point.getY()-range <= currentNode.getLatitude())
				nodeInReach.add(currentNode);
				
		}
		
		double shortestDistance = Double.MAX_VALUE;
		Node nearestNode = null; 
		for(Node node : nodeInReach) {
			double distance = Math.sqrt(Math.pow(node.getLongitude()-point.getX(), 2)+Math.pow(node.getLatitude()-point.getY(), 2));
			if (distance < shortestDistance) {
				shortestDistance = distance;
				nearestNode = node;
			}
		}
		
		return nearestNode;
		
	}
	
	

}
