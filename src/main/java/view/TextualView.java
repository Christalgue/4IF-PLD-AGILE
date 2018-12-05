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
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;

public class TextualView extends JPanel{

	private CircuitManagement circuitManagement;
	private int viewHeight;
	private int viewWidth;	
	
	private JTree deliveryTree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode treeRoot;
	
	private JScrollPane scrollPane;
	
	public TextualView () {
		
	}
	
	public TextualView(CircuitManagement circuitManagement, int viewHeight, int viewWidth, JTree deliveryTree) {
		
		super();
		
		this.circuitManagement = circuitManagement;
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		
		this.deliveryTree = deliveryTree;
		this.treeRoot = (DefaultMutableTreeNode) deliveryTree.getModel().getRoot();
		
		this.deliveryTree.setRootVisible(true);
		this.deliveryTree.setShowsRootHandles(true);
		
		this.treeModel = (DefaultTreeModel) this.deliveryTree.getModel();
		
		this.scrollPane = new JScrollPane(this.deliveryTree);
		scrollPane.setSize(viewWidth-20, viewHeight-220);
		scrollPane.setLocation(10, 20);
		this.add(scrollPane);
		this.setVisible(true);
		
	}

	public void fillDeliveryTree() {
		
		setBorder(BorderFactory.createTitledBorder("Livraisons"));
		
		int deliveryListIndex = 1;
			
		for( Delivery entry : circuitManagement.getDeliveryList() ) {
		
			treeModel.insertNodeInto(new DefaultMutableTreeNode("Livraison "+ deliveryListIndex +": Durée "+entry.getDuration()+" s"),
	                  treeRoot,
	                  deliveryListIndex-1);
			System.out.println(deliveryListIndex);
			deliveryListIndex++;
			
		}
	
		
		
	}
	
}
