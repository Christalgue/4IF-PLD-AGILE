package main.java.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;

/**
 * The Class MyTreeCellRenderer is a custom TreeCellRenderer to customize displaying in the textual view's tree.
 */
public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	
	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The window. */
	private Window window;
	
	/** The selected delivery. */
	private String selectedDelivery ="";
	
	/** The selected circuit. */
	private String selectedCircuit ="";
	
	/** The hovered delivery. */
	private String hoverDelivery ="";
	
	/** The hovered circuit. */
	private String hoverCircuit="";
	
	/** Indicate the if the repository is selected. */
	private boolean repositorySelected = false;
	
	/**
	 * Instantiates a new custom tree cell renderer.
	 *
	 * @param window the window
	 * @param circuitManagement the circuit management
	 */
	public MyTreeCellRenderer (Window window, CircuitManagement circuitManagement) {
		this.window = window;
		this.circuitManagement = circuitManagement;
	}
	
    /* (non-Javadoc)
     * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     * Function applied to each node of the tree each time a node change
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

        String node = (String) ((DefaultMutableTreeNode) value).getUserObject();

        String parentCircuitNode;
        
        setBackgroundSelectionColor(Color.WHITE);
        setTextSelectionColor(window.selectedColor);

        // If the repository is selected via the textual view as
        // the circuit are displayed
        if ( repositorySelected) {
    	   //System.out.println(selectedDelivery);
    	   TreeNode parentValue = ((TreeNode) value).getParent();
    	   
    	   // If the current node has a parent, its displayed string is stored
    	   if ( parentValue != null) {
    	   
    		   parentCircuitNode = (String) ((DefaultMutableTreeNode) parentValue).getUserObject();
    		   
    	   } else {
    		   parentCircuitNode = "Panda";
    	   }
    	   
    	   // If the current node is the repository and the parent is the corresponding circuit's node
    	   // Or if the current node is the selected circuit, the current node is painted on selectedColor
    	   if ( (selectedCircuit.equals(parentCircuitNode) && selectedDelivery.equals(node)) || selectedCircuit.equals(node)) {
	    	   setForeground ( window.selectedColor);
	       // Else if the current node is the hovered delivery or circuit, it is painted hoverColor
    	   }else if (hoverDelivery.equals(node) || hoverCircuit.equals(node)){
	    	   setForeground ( window.hoverColor);
		   
	        // Else if the current node is the circuit or
	   	   // one of its deliveries, it is painted in the circuit's color  
    	   } else {
		        
	        	if (!node.startsWith("Tournee")) {
		        		
	        		// Except the repository, which is painted in repositoryColor
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
        }
      
       } else {     
 
    	   // If the current node is the selected delivery or the selected circuit
    	   // it is painted on selectedColor
	       if (( selectedDelivery.equals(node) || selectedCircuit.equals(node))) {
	    	   setForeground ( window.selectedColor);
	       
		   // Else if the current node is the hovered delivery or circuit, it is painted hoverColor
	       } else if (hoverDelivery.equals(node) || hoverCircuit.equals(node)){
	    	   setForeground ( window.hoverColor);
	       
	       } else {
	    	   
	    	   // Else if the circuits are displayed, if the current node is the circuit or
		       // one of its deliveries, it is painted in the circuit's color 
	    	   if ( window.colors.get(0) != null) {
		        
		        	if (!node.startsWith("Tournee")) {
		        		
		        		// Except the repository, which is painted in repositoryColor
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
		        	
		        // Else if the circuits aren't displayed, every node is painted 
			    // in deliveryColor, except the repository, which is painted in repositoryColor
		        }else if ( circuitManagement.getDeliveryList() !=null) {
		        	
		        	if ( node.startsWith("Entrepot")) {
		        		setForeground(window.repositoryColor);
		        	} else {
		        		setForeground(window.deliveryColor);
		        	}
		        	
		        }	
	        }
       }

        return this;
    }

    /**
     * Sets the selected delivery.
     *
     * @param delivery : the name of the delivery
     * @param selected indicate if the delivery is selected (true) or hovered (false)
     */
    public void setSelectedDelivery ( String delivery, boolean selected) {    	
    	if (selected) {
    		this.selectedDelivery = delivery;
    	} else {
    		this.hoverDelivery = delivery; 
    	}
    }
    
    /**
     * Sets the selected circuit.
     *
     * @param circuit : the name of the circuit
     * @param selected indicate if the delivery is selected (true) or hovered (false)
     */
    public void setSelectedCircuit ( String circuit, boolean selected) {    	
    	if (selected) {
    		this.selectedCircuit = circuit;
    	} else {
    		this.hoverCircuit = circuit; 
    	}
    }
    
    /**
     * Gets the selected delivery.
     * 
     * @return the selected delivery
     */
    public String getSelectedDelivery() {
    	return selectedDelivery; 
    }
    
    /**
     * Indicate if the repository is selected.
     *
     * @param selected Indicate if the repository is selected
     */
    public void setRepositorySelected ( boolean selected) {
    	this.repositorySelected = selected;
    }
    
    
    
}