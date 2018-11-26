package main.java.controller;

public interface Command {
	
	
	/**
	 * Execut at this command
	 */
	void doCde();
	
	/**
	 * Execut the opposite method at this
	 */
	void undoCde();

}
