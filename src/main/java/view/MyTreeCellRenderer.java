package main.java.view;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	
	private CircuitManagement circuitManagement;
	private Window window;
	private String selectedDelivery ="";
	private String selectedCircuit ="";
	private String hoverDelivery ="";
	private String hoverCircuit="";
	
	public MyTreeCellRenderer (Window window, CircuitManagement circuitManagement) {
		this.window = window;
		this.circuitManagement = circuitManagement;
	}
	
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

        // Assuming you have a tree of Strings
        String node = (String) ((DefaultMutableTreeNode) value).getUserObject();

        setBackgroundSelectionColor(Color.WHITE);
        setTextSelectionColor(window.selectedColor);
        
       if (selectedDelivery.equals(node) || selectedCircuit.equals(node)) {
    	   setForeground ( window.selectedColor);
       } else if (hoverDelivery.equals(node) || hoverCircuit.equals(node)){
    	   setForeground ( window.hoverColor);
       } else {
	        if ( window.colors.get(0) != null) {
	        
	        	if (!node.startsWith("Tournee")) {
	        		
	        		if ( node.startsWith("Entrepot")){
	        			setForeground(window.repositoryColor);
	        		} else if ( node.length() >= 10){
	        		
		        		String secondPart = node.substring(10);
		        		String[] split = secondPart.split(":");
		        		String deliveryNumber = split[0];
		        		int deliveryIndex = Integer.parseInt(deliveryNumber);
		        		Delivery delivery = circuitManagement.getDeliveryByIndex(deliveryIndex); 
		        		int circuitIndex = circuitManagement.getCircuitIndexByDelivery(delivery);
		        		Color color = window.colors.get(circuitIndex);
		        		setForeground(color);
		        		
	        		}
	        	
	        	} else if (node.length() >= 8){
	        		String secondPart = node.substring(8);
	        		String[] split = secondPart.split(":");
	        		String circuitNumber = split[0];
	        		int circuitIndex = Integer.parseInt(circuitNumber);	
	        		Color color = window.colors.get(circuitIndex-1);
	        		setForeground(color);
	        	}
	        	
	        	
	        }else if ( circuitManagement.getDeliveryList() !=null) {
	        	
	        	if ( node.startsWith("Entrepot")) {
	        		setForeground(window.repositoryColor);
	        	} else {
	        		setForeground(window.deliveryColor);
	        	}
	        	
	        }	
        }

        return this;
    }

    public void setSelectedDelivery ( String string, boolean selected) {    	
    	if (selected) {
    		this.selectedDelivery = string;
    	} else {
    		this.hoverDelivery = string; 
    	}
    }
    
    public void setSelectedCircuit ( String string, boolean selected) {    	
    	if (selected) {
    		this.selectedCircuit = string;
    	} else {
    		this.hoverCircuit = string; 
    	}
    }
    
    
    
}