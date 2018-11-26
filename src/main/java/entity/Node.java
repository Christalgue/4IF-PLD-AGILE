package main.java.entity;


import java.util.*;

/**
 * 
 */
public class Node extends Observable{

    
	/**
     * Default constructor
     */
	public Node() {
    }

    //add a non-default constructor
    
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private double latitude;

    /**
     * 
     */
    private double longitude;
    
    

    public Node(long id, double latitude, double longitude) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

    public long getId() {
		return id;
	}

    public void setId(long id) {
		this.id = id;
	}

    public double getLatitude() {
		return latitude;
	}

    public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

    public double getLongitude() {
		return longitude;
	}

    public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

    


}