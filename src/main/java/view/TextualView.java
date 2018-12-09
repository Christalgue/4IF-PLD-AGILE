package main.java.view;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import main.java.entity.Circuit;
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
		

		treeRoot.removeAllChildren();
		treeModel.reload();
		
		setBorder(BorderFactory.createTitledBorder("Livraisons"));
		
		int deliveryListIndex = 0;
			
		for( Delivery entry : circuitManagement.getDeliveryList() ) {

			if (deliveryListIndex == 0) {
				
				treeModel.insertNodeInto(new DefaultMutableTreeNode ("Entrepot: "
					+ "Duree "+entry.getDuration()+" s"),
						treeRoot, deliveryListIndex++);
			
			}else {
			
				treeModel.insertNodeInto(new DefaultMutableTreeNode ("Livraison "+ 
						deliveryListIndex +": Duree "+entry.getDuration()+" s"),
							treeRoot, deliveryListIndex++);				
		
			}
			
		}
			
	}
	
	public void fillCircuitTree() {
		
		treeRoot.removeAllChildren();
		treeModel.reload();
		
		int deliveryIndex =0;
		int circuitIndex= 0;
		
		DefaultMutableTreeNode circuit;
		
		setBorder(BorderFactory.createTitledBorder("Tournees"));
		
		for( Circuit entry : circuitManagement.getCircuitsList() ) {
		
			circuit = new DefaultMutableTreeNode("Tournee "+ 
					(circuitIndex+1) +": Duree "+(int)entry.getCircuitLength()+" s");
			treeModel.insertNodeInto(circuit, treeRoot, circuitIndex++);
			
			
			for ( Delivery delivery : entry.getDeliveryList()) {
				
				if (deliveryIndex == 0) {
					
					treeModel.insertNodeInto(new DefaultMutableTreeNode ("Entrepot: "
						+ "Duree "+delivery.getDuration()+" s"),
							circuit, deliveryIndex++);
				
				}else {
				
					treeModel.insertNodeInto(new DefaultMutableTreeNode ("Livraison "+ 
							(circuitManagement.getDeliveryIndex(delivery)) +": Duree "+delivery.getDuration()+" s"),
								circuit, deliveryIndex++);				
			
				}
				
			}
			
			deliveryIndex =0;
			
		}
		
	}
	
}
