package main.java.entity;


import java.util.*;

import javafx.util.Pair;

import main.java.exception.DijkstraException;
import main.java.exception.LoadMapException;
import main.java.utils.Deserializer;

/**
 * The Class Map represent all streets and addresses.
 */
public class Map extends Observable{

    /**
     * Default constructor.
     */
    public Map() {
    }

    /** The node map which contains all addresses.
     * 	The id of an element is the node id and its value is the node.
     */
    protected HashMap<Long, Node> nodeMap;

    /** The bow map which contains all streets.
     *  The id of an element is the id of a node and its value is a set of 
     *  all the streets which have this node as origin.
     */
    protected HashMap<Long,Set<Bow>> bowMap;


    /**
     * Constructor of Map with a xml file containing all streets and addresses
     *
     * @param filename the name of the file
     * @throws LoadMapException the load map exception
     */
    public Map(String filename) throws LoadMapException {
        // TODO implement here
    	try {
    		Deserializer.loadMap(filename, this);
		} catch (Exception e) {
			// TODO: handle exception
			throw new LoadMapException(e.getMessage());
		}
    	
    }


    /**
     * Gets the node map.
     *
     * @return the map containing all addresses
     */
    public HashMap<Long, Node> getNodeMap() {
		return nodeMap;
	}

	/**
	 * Sets the node map.
	 *
	 * @param nodeMap the map containing all addresses
	 */
	public void setNodeMap(HashMap<Long, Node> nodeMap) {
		this.nodeMap = new HashMap<Long, Node>(nodeMap);
	}

	/**
	 * Gets the bow map.
	 *
	 * @return the map containing all streets
	 */
	public HashMap<Long, Set<Bow>> getBowMap() {
		return bowMap;
	}

	/**
	 * Sets the bow map.
	 *
	 * @param bowMap the map containing all streets
	 */
	public void setBowMap(HashMap<Long, Set<Bow>> bowMap) {
		this.bowMap = new HashMap<Long, Set<Bow>>(bowMap);
	}

	/**
	 * Dijkstra algorithm to find shortest path from a given start to all others delivery.
	 * The graph exploration stops when all shortest paths to the deliveries have been explored.
	 *
	 * @param startDelivery the start delivery
	 * @param arrivalDeliveries the list of deliveries to reach
	 * @return hash map containing for each delivery, an atomic path beginning from the start delivery to itself
	 * @throws DijkstraException if the start delivery is not in the list of deliveries to reach
	 */
	
    public HashMap<Delivery,AtomicPath> findShortestPath(Delivery startDelivery, List<Delivery> arrivalDeliveries) throws DijkstraException {
        if (!arrivalDeliveries.contains(startDelivery)) {
        	throw new DijkstraException("Deliveries does not contains startDelivery.");
        }
        
        HashMap<Delivery,AtomicPath> AtomicPaths = new HashMap<Delivery,AtomicPath>();
    	
        HashMap<Node, Double> nodeDistances = new HashMap<Node, Double>();
        HashMap<Node, Node> nodePrecedences = new HashMap<Node, Node>();
        
        // Default : nodes are white
        Set<Node> grayNodes = new HashSet<Node>();
        Set<Node> blackNodes = new HashSet<Node>();
        
        grayNodes.add(startDelivery.getPosition());
        nodeDistances.put(startDelivery.getPosition(), (double)0 );
        
    	while(grayNodes.size()!=0) {
    		Pair<Long,Node> minimumDistanceNode = getLowestDistanceNode(grayNodes, nodeDistances);
    		
    		Long idCurrentNode = minimumDistanceNode.getKey();
    		Node currentNode = minimumDistanceNode.getValue();
    		    		
    		Set<Bow> nodeAdjacentEdges = bowMap.get(idCurrentNode);
    		
    		// Check if there is at least a bow commencing with the current node
    		if (nodeAdjacentEdges!=null) {
    			for (Bow adjacentBow : nodeAdjacentEdges) {
        			Node adjacentNode = adjacentBow.getEndNode();
        			if (!blackNodes.contains(adjacentNode)) {
        				releaseBow(adjacentBow, nodeDistances, nodePrecedences);
        				if(!grayNodes.contains(adjacentNode)) {
        					grayNodes.add(adjacentNode);
        				}
        			}
        		}
        		grayNodes.remove(currentNode);
        		blackNodes.add(currentNode);
    		} else {
    			grayNodes.remove(currentNode);
        		blackNodes.add(currentNode);
    		}
    	}
    	
    	for (Delivery currentDelivery : arrivalDeliveries) {
    		if (!currentDelivery.equals(startDelivery)) {
        		List<Bow> bowList = new ArrayList<Bow>();
        		Node currentNode = currentDelivery.getPosition();
        		while (nodePrecedences.containsKey(currentNode)) {
        			Node precedentNode = nodePrecedences.get(currentNode);
        			Set<Bow> currentNodeBows = bowMap.get(precedentNode.getId());
        			Bow bowToAddToAtomicPath = null;
        			for (Bow currentBow : currentNodeBows) {
        				if (currentBow.getEndNode().equals(currentNode)) {
        					bowToAddToAtomicPath = currentBow;
        				}
        			}
        			
        			bowList.add(0, bowToAddToAtomicPath);
        			currentNode = precedentNode;
        		}
        		
        		AtomicPath optimalPath = new AtomicPath(bowList);
        		AtomicPaths.put(currentDelivery, optimalPath);
    		}
    	}  
        return AtomicPaths;
    }
    
    /**
     * Release a bow.
     * Updates the distance to the end node of the bow and the precedences.
     *
     * @param currentBow the current bow to release
     * @param nodeDistances contains for each node, the distance from the start node
     * @param nodePrecedences contains for each node, the precedent node in the shortest path
     */
    protected static void releaseBow(Bow currentBow, HashMap<Node, Double> nodeDistances, HashMap<Node, Node> nodePrecedences) {
    	Double newDistance = nodeDistances.get(currentBow.getStartNode()) + currentBow.getLength();
    	if (nodeDistances.containsKey(currentBow.getEndNode())) {
    		Double oldDistance = nodeDistances.get(currentBow.getEndNode());
    		if (newDistance < oldDistance) {
        		nodeDistances.replace(currentBow.getEndNode(), newDistance);
        		nodePrecedences.replace(currentBow.getEndNode(), currentBow.getStartNode());
        	}
    	} else {
    		nodeDistances.put(currentBow.getEndNode(), newDistance);
    		nodePrecedences.put(currentBow.getEndNode(),currentBow.getStartNode());
    	}
    	
    	
    }
    
    /**
     * Gets the node with the lowest distance .
     *
     * @param nodeSet the set containing all nodes
     * @param nodeDistances containing for each node its distance from the start node
     * @return the node with the lowest distance from the start node
     */
    protected static Pair<Long, Node> getLowestDistanceNode(Set<Node> nodeSet, HashMap<Node, Double> nodeDistances) {
    	Pair<Long, Node> minimumDistanceNodeEntry = null;
    	Double minimumDistance = Double.MAX_VALUE;
    	Node minimumDistanceNode = null;
    	for (Node node : nodeSet) {
    		if (nodeDistances.containsKey(node)) {
    			Double nodeDistance = nodeDistances.get(node);
    			if (nodeDistance < minimumDistance) {
    				minimumDistance = nodeDistance;
    				minimumDistanceNode = node;
    				minimumDistanceNodeEntry = new Pair<Long, Node>(minimumDistanceNode.getId(),minimumDistanceNode);
    			}
    		}
    	}
    	return minimumDistanceNodeEntry; 
    }
    
    /**
     * Gets the id from the node which corresponds to the given coordinates.
     *
     * @param longitude the longitude
     * @param latitude the latitude
     * @return the id from the node
     */
    public long getIdFromCorrespondingNode (double longitude, double latitude) {

		for( HashMap.Entry<Long, Node> entry : nodeMap.entrySet()) {
		    
			Node currentNode = entry.getValue();
			if (currentNode.getLongitude() == longitude && currentNode.getLatitude() == latitude)	{
				return currentNode.getId();
			}
		}
		return 0;
    }
    
	public String displayIntersectionNode (Node node) {
		long id = node.getId();
		String temp = "";
		String finalString = "";
		int i=0;
		for( HashMap.Entry<Long,Set<Bow>> bowSet : bowMap.entrySet() ) {
			
			Iterator<Bow> iterator = bowSet.getValue().iterator();
		    while(iterator.hasNext()) {
		        Bow setElement = iterator.next();
		        if (setElement.getStartNode().getId() == id || setElement.getEndNode().getId()==id) {
		        	if (!(setElement.getStreetName() == "")) {
			        	if(i == 0) {
				        
				            temp = temp + setElement.getStreetName();	
				            i++;
				        }
				        else if (i == 1 && (!temp.contains(setElement.getStreetName()))) {
					       	temp = temp +" et " + setElement.getStreetName();
					       	i++;
			     	    }
		        	}
		        }
		        
		        if (i == 2) {
		        	break;
		        }
		    }
		}
		
		if (i == 0) {
			finalString = "-";
		}
		else if (i == 2) {
			finalString = "Intersection entre " + temp;
		} else if (i==1) {
			finalString = "Impasse : " + temp;
		} 
		return finalString;
	}
    
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		
		for (long key : nodeMap.keySet()) {
			s += key + ": ";
			s += nodeMap.get(key).toString();
			s += "\n";
		}
		
		for (long key : bowMap.keySet()) {
			s += key + ": ";
			s += bowMap.get(key).toString();
			s += "\n";
		}
		
		return s;
	}

}