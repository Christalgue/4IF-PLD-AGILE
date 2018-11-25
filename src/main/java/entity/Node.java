package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Node {

    
	/**
     * Default constructor
     */
    protected Node() {
    }

    //add a non-default constructor
    
    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private double latitude;

    /**
     * 
     */
    private double longitude;
    
    

    protected Node(int id, double latitude, double longitude) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	protected int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected double getLatitude() {
		return latitude;
	}

	protected void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	protected double getLongitude() {
		return longitude;
	}

	protected void setLongitude(double longitude) {
		this.longitude = longitude;
	}



}