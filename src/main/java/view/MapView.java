package main.java.view;

import main.java.entity.Map;

public class MapView {
	
	/**
	 * Default constructor
	 */
	public MapView () {
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
	 * @param colorRoad 		The color of the road
	 * @param width 			The width of the road
	 * @param colorBackground	The color of the background
	 */
	public MapView(String colorRoad, int width, String colorBackground) {
		this.colorRoad = colorRoad;
		this.width = width;
		this.colorBackground = colorBackground;
	}
	
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
