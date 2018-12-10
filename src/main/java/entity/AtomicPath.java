package main.java.entity;


import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AtomicPath.
 */
public class AtomicPath extends Observable{

    /**
     * Default constructor.
     */
    public AtomicPath() {
    }

    /** The path. */
    private List<Bow> path;


	/** The length. */
    private double length;
    
    /** The duration. */
    private double duration;

	/**
	 * Instantiates a new atomic path.
	 *
	 * @param path the path
	 */
	public AtomicPath(List<Bow> path) {
		super();
		this.path = new ArrayList<>(path);
		this.length = calculateLength();
		this.duration = calculateDuration();
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public List<Bow> getPath() {
		return path;
	}


	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	protected void setPath(List<Bow> path) {
		this.path = new ArrayList(path);
	}



	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Gets the start node.
	 *
	 * @return the start node
	 */
	public Node getStartNode() {
		return path.get(0).getStartNode();
	}
	
	/**
	 * Gets the end node.
	 *
	 * @return the end node
	 */
	public Node getEndNode() {
		return path.get(path.size()-1).getEndNode();
	}
	
	/**
	 * Gets the atomic path duration.
	 *
	 * @return the atomic path duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	protected void setLength(double length) {
		this.length = length;
	}



	/**
	 * Calculate length.
	 *
	 * @return the double
	 */
	private double calculateLength(){
		double length = 0;
		for(Bow bow : this.path ){
			length += bow.getLength();
		}
		return length;
	}
	
	/**
	 * Calculate duration.
	 *
	 * @return the double
	 */
	protected double calculateDuration() {
		double result = (length*3600.0)/(15.0*1000.0);
		return result;
	}


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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