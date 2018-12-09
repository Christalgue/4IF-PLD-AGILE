package main.java.entity;


import java.util.*;

/**
 * 
 */
public class AtomicPath extends Observable{

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

	public AtomicPath(List<Bow> path) {
		super();
		this.path = new ArrayList<>(path);
		this.length = calculateLength();
	}
	
	public List<Bow> getPath() {
		return path;
	}


	protected void setPath(List<Bow> path) {
		this.path = new ArrayList(path);
	}



	public double getLength() {
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


    @Override
	public String toString() {
		String s = "Route :\n";
		for (Bow currentBow : this.path) {
			s+=(currentBow.getStartNode().getId()+" => "+currentBow.getEndNode().getId()+" ("+currentBow.getLength()+")\n");
		}
		/*s+= path.get(0).getStartNode().toString();
		s+= " --> " + path.get(path.size()-1).getEndNode().toString();*/
		return s;
	}    


}