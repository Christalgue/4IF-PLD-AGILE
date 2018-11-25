package main.java.view;

import main.java.entity.Map;

public class MapView {
	
	/**
	 * Default constructor
	 */
	protected MapView () {
		
	}
	
	/**
     * The color of the road
     */
	private String colorRoad;
	
	/**
     * The width of the road
     */
	private int width;
	
	/**
     * The color of the background
     */
	private String colorBackground;
	
	/**
	 * 
	 * @return The color of the road
	 */
	protected String getColorRoad () {
		return colorRoad;
	}
	
	/**
	 * 
	 * @return The width of the road
	 */
	protected int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return The color of the background of the map
	 */
	protected String getColorBackground() {
		return colorBackground;
	}
	
	/**
	 * 
	 * @param map The map to write in the Json
	 */
	protected void toJson (Map map) {
		
	}
}
