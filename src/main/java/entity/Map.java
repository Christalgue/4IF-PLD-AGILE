package main.java.entity;


import java.util.*;
import javafx.util.Pair;

import main.java.exception.DijkstraException;
import main.java.exception.LoadMapException;
import main.java.utils.Deserializer;

/**
 * 
 */
public class Map extends Observable{

    /**
     * Default constructor
     */
    public Map() {
    }

    /**
     * 
     */
    protected HashMap<Long, Node> nodeMap;

    /**
     * 
     */
    protected HashMap<Long,Set<Bow>> bowMap;


    /**
     * appel au serializer etc
     * @param filename
     * @throws LoadMapException 
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


    public HashMap<Long, Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(HashMap<Long, Node> nodeMap) {
		this.nodeMap = new HashMap<Long, Node>(nodeMap);
	}

	public HashMap<Long, Set<Bow>> getBowMap() {
		return bowMap;
	}

	public void setBowMap(HashMap<Long, Set<Bow>> bowMap) {
		this.bowMap = new HashMap<Long, Set<Bow>>(bowMap);
	}

	/**
     * 
     * Dijkstra & co ;)
     * @param Node startNode; Node endNode 
     * @return
	 * @throws DijkstraException 
     */
	
	// A METTRE EN NODE
    public HashMap<Delivery,AtomicPath> findShortestPath(Delivery startDelivery, List<Delivery> arrivalDeliveries) throws DijkstraException {
        if (!arrivalDeliveries.contains(startDelivery)) {
        	throw new DijkstraException("Deliveries does not contains startDelivery.");
        }
        
        HashMap<Delivery,AtomicPath> AtomicPaths = new HashMap<Delivery,AtomicPath>();
        		//new AtomicPath[arrivalDeliveries.size()-1];
    	
        HashMap<Node, Double> nodeDistances = new HashMap<Node, Double>();
        HashMap<Node, Node> nodePrecedences = new HashMap<Node, Node>();
        
        // Default : nodes are white
        Set<Node> grayNodes = new HashSet<Node>();
        Set<Node> blackNodes = new HashSet<Node>();
        
        grayNodes.add(startDelivery.getPosition());
        nodeDistances.put(startDelivery.getPosition(), (double)0 );
        //nodePrecedences.put(startDelivery.getPosition(), startDelivery.getPosition());
        
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
    	
    	for (HashMap.Entry<Node,Double> e : nodeDistances.entrySet()) {
    		//System.out.println("Distance du node "+e.getKey().getId()+" : "+e.getValue());
    	}
    	for (HashMap.Entry<Node,Node> e : nodePrecedences.entrySet()) {
    		//System.out.println("Precedent du node "+e.getKey().getId()+" : "+e.getValue().getId());
    	}
    	
    	for (Delivery currentDelivery : arrivalDeliveries) {
    		if (!currentDelivery.equals(startDelivery)) {
    			//System.out.println("Delivery : "+currentDelivery.getPosition());
        		List<Bow> bowList = new ArrayList<Bow>();
        		Node currentNode = currentDelivery.getPosition();
        		while (nodePrecedences.containsKey(currentNode)) {
        			Node precedentNode = nodePrecedences.get(currentNode);
        			Set<Bow> currentNodeBows = bowMap.get(precedentNode.getId());
        			Bow bowToAddToAtomicPath = null;
        			for (Bow currentBow : currentNodeBows) {
        				if (currentBow.getEndNode().equals(currentNode)) {			////////////////////////////////////////////////////////////////////////////
        					bowToAddToAtomicPath = currentBow;
        				}
        			}
        			
        			bowList.add(0, bowToAddToAtomicPath);
        			currentNode = precedentNode;
        		}
        		/*System.out.println("Taille de atomic path : "+bowList.size());
        		System.out.println("Atomic path : "+AtomicPathsIndex);
        		for (Bow b : bowList) {
        			System.out.println(b.getStartNode().getId()+" => "+b.getEndNode().getId());
        		}
        		System.out.println("");*/
        		
        		AtomicPath optimalPath = new AtomicPath(bowList);
        		AtomicPaths.put(currentDelivery, optimalPath);
    		}
    	}    	
        return AtomicPaths;
    }
    
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