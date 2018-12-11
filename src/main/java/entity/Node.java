package main.java.entity;


import java.util.Observable;

/**
 * The Class Node.
 */
public class Node extends Observable{

    /** The id. */
    private Long id;

    /** The latitude. */
    private double latitude;

    /** The longitude. */
    private double longitude;
    
    

    /**
     * Instantiates a new node.
     *
     * @param id the id
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public Node(long id, double latitude, double longitude) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
		return id;
	}

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
		this.id = id;
	}

    /**
     * Gets the latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
		return latitude;
	}

    /**
     * Sets the latitude.
     *
     * @param latitude the new latitude
     */
    public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

    /**
     * Gets the longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
		return longitude;
	}

    /**
     * Sets the longitude.
     *
     * @param longitude the new longitude
     */
    public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

    


}