package main.java.controller;

import java.util.LinkedList;

public class CommandsList {
	
	private LinkedList<Command> commands;
	private int index;
	
	public CommandsList() {
		commands = new LinkedList<Command>();
		index = -1;
	}
	
	public void addCommand(Command command) {
		int i = index+1;
		while(i<commands.size()) {
			commands.remove(i);
		}
		index++;
		commands.add(command);
		command.doCde();
	}
	
	public void undo() {
		if(index >= 0)
		{
			commands.get(index).undoCde();
			index--;
		}
	}
	
	public void redo() {
		if(index<commands.size()-1)
		{
			commands.get(index).doCde();
			index++;
		}
	}
	
}
