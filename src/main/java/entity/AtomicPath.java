package main.java.entity;


import java.util.*;

/**
 * 
 */
public class AtomicPath {

    /**
     * Default constructor
     */
    public AtomicPath() {
    }

    /**
     * 
     */
    private List<Bow> path;

    /**
     * 
     */
    private double length;

	public AtomicPath(List<Bow> path, double duration) {
		super();
		this.path = new ArrayList<>(path);
		this.length = calculateLength();
	}
	
	protected List<Bow> getPath() {
		return path;
	}


	protected void setPath(List<Bow> path) {
		this.path = new ArrayList(path);
	}



	protected double getLength() {
		return length;
	}



	protected void setLength(double length) {
		this.length = length;
	}



	private double calculateLength(){
		double length = 0;
		for(Bow bow : this.path ){
			length += bow.getLength();
		}
		return length;
	}

    


}