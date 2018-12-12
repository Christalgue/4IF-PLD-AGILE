package main.java.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.entity.CircuitManagement;

/**
 * The Class PointUtil gather a few useful functions about points
 */
public class PointUtil {
	
	/** The tolerance on the selection via a click on the graphicView. */
	public static double range;
	
	/**
	 * Return the node which coordinates are the same as Point's
	 * If several nodes matches within range, the nearest is selected
	 *
	 * @param point the point to convert into node
	 * @param circuitManagement the circuit management
	 * @return the corresponding node
	 */
	public static Node pointToNode( Point point, CircuitManagement circuitManagement) {
		
		HashMap<Long, Node> nodeMap = circuitManagement.getCurrentMap().getNodeMap();

	    ArrayList<Node> nodeInReach = new ArrayList<Node>();
		
	    // For every node in the map, if its coordinates are within the point's range
	    // it is added to the nodeInReach list
		for( Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
			Node currentNode = entry.getValue();
			
			if (currentNode.getLongitude() <= point.getX()+range && point.getX()-range <= currentNode.getLongitude() && 
				currentNode.getLatitude() <= point.getY()+range && point.getY()-range <= currentNode.getLatitude())
				nodeInReach.add(currentNode);

		}
		
		double shortestDistance = Double.MAX_VALUE;
		Node nearestNode = null;
		
		// For each node in reach, its distance to the point is calculated
		for(Node node : nodeInReach) {			
			double distance = Math.sqrt(Math.pow(node.getLongitude()-point.getX(), 2)+Math.pow(node.getLatitude()-point.getY(), 2));
			if (distance < shortestDistance) {
				shortestDistance = distance;
				nearestNode = node;
			}
		}
		
		// The node with the shortest distance is returned
		return nearestNode;
		
	}
	
	

}
