package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Bow {

    /**
     * Default constructor
     */
	public Bow() {
    }

    /**
     * 
     */
    private Node startNode;

    /**
     * 
     */
    private Node endNode;

    /**
     * 
     */
    private String streetName;

    /**
     * 
     */
    private double length;

    /**
     * Constructor
     * @param Node sNode; Node eNode; String name; double l
     */
    public Bow(Node sNode, Node eNode, String name, double l){
    	this.startNode = sNode;
    	this.endNode = eNode;
    	this.streetName = name;
    	this.length = l;
    }
    
    public Node getStartNode() {
    	return this.startNode;
    }
    
    public Node getEndNode(){
    	return this.endNode;
    }
    
    public String getStreetName(){
    	return this.streetName;
    }
    
    public double getLength(){
    	return this.length;
    }
    
    public void setStartNode(Node sNode){
    	this.startNode = sNode;
    }
    
    public void setEndNode(Node eNode){
    	this.endNode = eNode;
    }
    
    public void setStreetName(String sName){
    	this.streetName = sName;
    }

    public void setLength(double l){
    	this.length = l;
    }

	@Override
	public String toString() {
		return "Bow [startNode=" + startNode.getId() + ", endNode=" + endNode.getId() + ", streetName=" + streetName + ", length="
				+ length + "]\n";
	}

    

}