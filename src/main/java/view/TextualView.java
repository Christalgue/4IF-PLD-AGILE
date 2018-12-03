package main.java.view;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;

public class TextualView extends JPanel{

	private CircuitManagement circuitManagement;
	private int viewHeight;
	private int viewWidth;	
	
	private JTree deliveryTree;
	private DefaultMutableTreeNode treeRoot;
	
	JScrollPane scrollPane;
	
	public TextualView () {
		
	}
	
	public TextualView(CircuitManagement circuitManagement, int viewHeight, int viewWidth, JTree deliveryTree) {
		
		super();
		
		this.circuitManagement = circuitManagement;
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		this.deliveryTree = deliveryTree;
		this.treeRoot = (DefaultMutableTreeNode) deliveryTree.getModel().getRoot();
		
		this.scrollPane = new JScrollPane(deliveryTree); 
		this.add(scrollPane);
		this.setVisible(true);
		
	}

	public void fillDeliveryTree() {
		
		setBorder(BorderFactory.createTitledBorder("Livraisons"));
		
		int deliveryListIndex = 1;
			
		for( Delivery entry : circuitManagement.getDeliveryList() ) {
			    
			treeRoot.add(new DefaultMutableTreeNode("Livraison "+ deliveryListIndex +": Durée "+entry.getDuration()+" s"));
			System.out.println(deliveryListIndex);
			deliveryListIndex++;
			
		}
	
		
		
	}
	
}
