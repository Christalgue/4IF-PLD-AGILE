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

// TODO: Auto-generated Javadoc
/**
 * The Class MyTreeCellRenderer.
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
	
	/** The hover delivery. */
	private String hoverDelivery ="";
	
	/** The hover circuit. */
	private String hoverCircuit="";
	
	/** The repository circuit. */
	private String repositoryCircuit="";
	
	/**
	 * Instantiates a new my tree cell renderer.
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
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

        // Assuming you have a tree of Strings
        String node = (String) ((DefaultMutableTreeNode) value).getUserObject();
        String parentCircuitNode;
        
        setBackgroundSelectionColor(Color.WHITE);
        setTextSelectionColor(window.selectedColor);
        
       if ( !repositoryCircuit.equals("")) {
    	   
    	   TreeNode parentValue = ((TreeNode) value).getParent();
    	   
    	   if ( parentValue != null) {
    	   
    		   parentCircuitNode = (String) ((DefaultMutableTreeNode) parentValue).getUserObject();
    		   
    	   } else {
		   parentCircuitNode = "Panda";
    	   }
    	   
    	   if ( (repositoryCircuit.equals(parentCircuitNode) && selectedDelivery.equals(node)) || selectedCircuit.equals(node)) {
	    	   setForeground ( window.selectedColor);
    	   }else if (hoverDelivery.equals(node) || hoverCircuit.equals(node)){
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
    	   
       } else {     
 
	       if (( selectedDelivery.equals(node) || selectedCircuit.equals(node))) {
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
       }

        return this;
    }

    /**
     * Sets the selected delivery.
     *
     * @param string the string
     * @param selected the selected
     */
    public void setSelectedDelivery ( String string, boolean selected) {    	
    	if (selected) {
    		this.selectedDelivery = string;
    	} else {
    		this.hoverDelivery = string; 
    	}
    }
    
    /**
     * Sets the selected circuit.
     *
     * @param string the string
     * @param selected the selected
     */
    public void setSelectedCircuit ( String string, boolean selected) {    	
    	if (selected) {
    		this.selectedCircuit = string;
    	} else {
    		this.hoverCircuit = string; 
    	}
    }
    
    /**
     * Sets the repository circuit.
     *
     * @param circuit the new repository circuit
     */
    public void setRepositoryCircuit ( String circuit) {
    	this.repositoryCircuit = circuit;
    }
    
    
    
}