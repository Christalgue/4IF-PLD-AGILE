package main.java.controller;


/**
 * The Interface Command.
 * Used to implement the Design Pattern Command, it represents a command the user will be able 
 * to undo or redo.
 */
public interface Command {
	
	
	/**
	 * Execute the command.
	 */
	void doCde();
	
	/**
	 * Execute the opposite command.
	 */
	void undoCde();

}
