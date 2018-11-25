package main.java.entity;


import java.util.*;

import main.java.exception.LoadMapException;
import main.java.utils.Deserializer;

/**
 * 
 */
public class Map {

    /**
     * Default constructor
     */
    public Map() {
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
     * @throws LoadMapException 
     */
    public Map(String filename) throws LoadMapException {
        // TODO implement here
    	try {
    		chargingUnit.loadMap(filename, this);
		} catch (Exception e) {
			// TODO: handle exception
			throw new LoadMapException(e.getMessage());
		}
    	
    }


    public HashMap<Integer, Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(HashMap<Integer, Node> nodeMap) {
		this.nodeMap = new HashMap(nodeMap);
	}

	public HashMap<Integer, Set<Bow>> getBowMap() {
		return bowMap;
	}

	public void setBowMap(HashMap<Integer, Set<Bow>> bowMap) {
		this.bowMap = new HashMap(bowMap);
	}

	public Deserializer getChargingUnit() {
		return chargingUnit;
	}

	public void setChargingUnit(Deserializer chargingUnit) {
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