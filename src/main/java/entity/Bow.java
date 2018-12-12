package main.java.entity;


import java.util.Observable;

/**
 * The Class Bow represents the path between two nodes.
 * @see Node
 */
public class Bow extends Observable{

    /**
     * Default constructor.
     */
	public Bow() {
    }

    /** 
     * The start node.
     * */
    private Node startNode;

    /** 
     * The end node.
     * */
    private Node endNode;

    /** The street name. */
    private String streetName;

    /** The length. */
    private double length;

    /**
     * Constructor.
     *
     * @param sNode the s node
     * @param eNode the e node
     * @param name the name
     * @param l the l
     */
    public Bow(Node sNode, Node eNode, String name, double l){
    	this.startNode = sNode;
    	this.endNode = eNode;
    	this.streetName = name;
    	this.length = l;
    }
    
    /**
     * Gets the starting node.
     *
     * @return the starting node
     */
    public Node getStartNode() {
    	return this.startNode;
    }
    
    /**
     * Gets the ending node.
     *
     * @return the ending node
     */
    public Node getEndNode(){
    	return this.endNode;
    }
    
    /**
     * Gets the street name.
     *
     * @return the street name
     */
    public String getStreetName(){
    	return this.streetName;
    }
    
    /**
     * Gets the length.
     *
     * @return the length
     */
    public double getLength(){
    	return this.length;
    }
    
    /**
     * Sets the starting node.
     *
     * @param sNode the new starting node
     */
    public void setStartNode(Node sNode){
    	this.startNode = sNode;
    }
    
    /**
     * Sets the ending node.
     *
     * @param eNode the new ending node
     */
    public void setEndNode(Node eNode){
    	this.endNode = eNode;
    }
    
    /**
     * Sets the street name.
     *
     * @param sName the new street name
     */
    public void setStreetName(String sName){
    	this.streetName = sName;
    }

    /**
     * Sets the length.
     *
     * @param l the new length
     */
    public void setLength(double l){
    	this.length = l;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bow [startNode=" + startNode.getId() + ", endNode=" + endNode.getId() + ", streetName=" + streetName + ", length="
				+ length + "]\n";
	}

    

}