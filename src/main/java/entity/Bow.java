package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Bow {

    /**
     * Default constructor
     */
    protected Bow() {
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
    protected Bow(Node sNode, Node eNode, String name, double l){
    	this.startNode = sNode;
    	this.endNode = eNode;
    	this.streetName = name;
    	this.length = l;
    }
    
    protected Node getStartNode() {
    	return this.startNode;
    }
    
    protected Node getEndNode(){
    	return this.endNode;
    }
    
    protected String getStreetName(){
    	return this.streetName;
    }
    
    protected double getLength(){
    	return this.length;
    }
    
    protected void setStartNode(Node sNode){
    	this.startNode = sNode;
    }
    
    protected void setEndNode(Node eNode){
    	this.endNode = eNode;
    }
    
    protected void setStreetName(String sName){
    	this.streetName = sName;
    }

    protected void setLength(double l){
    	this.length = l;
    }


}