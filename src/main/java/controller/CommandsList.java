package main.java.controller;

import java.util.LinkedList;


/**
 * The Class CommandsList.
 * Used to implement the Design Pattern Command, it represents the list of command the user will be
 * able to undo or redo.
 */
public class CommandsList {
	
	/** The commands. */
	private LinkedList<Command> commands;
	
	/** The index. */
	private int index;
	
	/**
	 * Instantiates a new CommandsList.
	 */
	public CommandsList() {
		commands = new LinkedList<Command>();
		index = -1;
	}
	
	/**
	 * Adds a command.
	 *
	 * @param command the command to add
	 */
	public void addCommand(Command command) {
		int i = index+1;
		while(i<commands.size()) {
			commands.remove(i);
		}
		index++;
		commands.add(command);
		command.doCde();
	}
	
	/**
	 * Undo the command.
	 */
	public void undo() {
		if(index >= 0)
		{
			commands.get(index).undoCde();
			index--;
		}
	}
	
	/**
	 * Redo the command.
	 */
	public void redo() {
		if(index<commands.size()-1)
		{
			index++;
			commands.get(index).doCde();
		}
	}
	
	/**
	 * Reset the list.
	 */
	public void reset() {
		index = -1;
		commands.clear();
	}
	
}
