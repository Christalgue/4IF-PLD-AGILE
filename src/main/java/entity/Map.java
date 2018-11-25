package main.java.entity;


import java.util.*;

import main.java.utils.Deserializer;

/**
 * 
 */
public class Map {

    /**
     * Default constructor
     */
    protected Map() {
    }

    /**
     * 
     */
    protected HashMap<Integer, Node> nodeMap;

    /**
     * 
     */
    protected HashMap<Integer,Set<Bow>> bowMap;

    /**
     * 
     */
    protected Deserializer chargingUnit;



    /**
     * appel au serializer etc
     * @param filename
     */
    protected void Map(String filename) {
        // TODO implement here
    	chargingUnit.loadMap(filename, this);
    }


    protected HashMap<Integer, Node> getNodeMap() {
		return nodeMap;
	}

	protected void setNodeMap(HashMap<Integer, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}

	protected HashMap<Integer, Set<Bow>> getBowMap() {
		return bowMap;
	}

	protected void setBowMap(HashMap<Integer, Set<Bow>> bowMap) {
		this.bowMap = bowMap;
	}

	protected Deserializer getChargingUnit() {
		return chargingUnit;
	}

	protected void setChargingUnit(Deserializer chargingUnit) {
		this.chargingUnit = chargingUnit;
	}


	/**
     * 
     * Dijkstra & co ;)
     * @param Node startNode; Node endNode 
     * @return
     */
    protected AtomicPath findShortestPath(Node startNode, Node endNode) {
        // TODO implement here
    	AtomicPath result = new AtomicPath();
        return result;
    }

    

}