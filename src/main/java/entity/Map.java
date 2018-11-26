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
    protected HashMap<Long, Node> nodeMap;

    /**
     * 
     */
    protected HashMap<Long,Set<Bow>> bowMap;

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


    public HashMap<Long, Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(HashMap<Long, Node> nodeMap) {
		this.nodeMap = new HashMap(nodeMap);
	}

	public HashMap<Long, Set<Bow>> getBowMap() {
		return bowMap;
	}

	public void setBowMap(HashMap<Long, Set<Bow>> bowMap) {
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
    protected AtomicPath[] findShortestPath(Node startNode, List<Delivery> arrivalDeliveries) {
        // TODO implement here
    	AtomicPath result[] = new AtomicPath[arrivalDeliveries.size()];
        return result;
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