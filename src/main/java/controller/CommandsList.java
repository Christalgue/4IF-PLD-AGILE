package main.java.controller;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandsList.
 */
public class CommandsList {
	
	/** The commands. */
	private LinkedList<Command> commands;
	
	/** The index. */
	private int index;
	
	/**
	 * Instantiates a new commands list.
	 */
	public CommandsList() {
		commands = new LinkedList<Command>();
		index = -1;
	}
	
	/**
	 * Adds the command.
	 *
	 * @param command the command
	 */
	public void addCommand(Command command) {
		System.out.println("add "+index);
		int i = index+1;
		while(i<commands.size()) {
			commands.remove(i);
		}
		index++;
		commands.add(command);
		command.doCde();
	}
	
	/**
	 * Undo.
	 */
	public void undo() {
		System.out.println("undo "+index);
		if(index >= 0)
		{
			commands.get(index).undoCde();
			index--;
		}
	}
	
	/**
	 * Redo.
	 */
	public void redo() {
		System.out.println("redo "+index);
		if(index<commands.size()-1)
		{
			index++;
			commands.get(index).doCde();
		}
	}
	
	/**
	 * Reset.
	 */
	public void reset() {
		index = -1;
		commands.clear();
	}
	
}
