package main.java.controller;

import main.java.entity.Node;

public class PreviousDeliverySelectedState extends DefaultState {
	
	Node node;
	int duration;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	protected void setDuration (int duration) {
		this.duration  = duration;
	}
	
	

}
