package main.java.view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;

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
		
		//this.addTreeListener();
		
		this.deliveryTree.expandRow(0);
		this.deliveryTree.setRootVisible(false);
		this.deliveryTree.setShowsRootHandles(true);
		
		this.treeModel = (DefaultTreeModel) this.deliveryTree.getModel();
		
		this.scrollPane = new JScrollPane(this.deliveryTree);
		scrollPane.setSize(viewWidth-20, viewHeight-220);
		scrollPane.setLocation(10, 20);
		this.add(scrollPane);
		this.setVisible(true);
		
	}
	
/*	 public void addTreeListener () {
		 
		 deliveryTree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode deliveryPoint = (DefaultMutableTreeNode) deliveryTree.getLastSelectedPathComponent();
		        
		        String deliveryInfo = (String) deliveryPoint.getUserObject();
		        
		        if (!deliveryInfo.startsWith("Entrepot")) {
		        	
		        	if (!deliveryInfo.startsWith("Tournee")) {
		        		String secondPart = deliveryInfo.substring(10);
		        		String[] split = secondPart.split(":");
		        		String deliveryNumber = split[0];
		        		int deliveryIndex = Integer.parseInt(deliveryNumber);
		        	} else {
		        		String secondPart = deliveryInfo.substring(8);
		        		String[] split = secondPart.split(":");
		        		String circuitNumber = split[0];
		        		int circuitIndex = Integer.parseInt(circuitNumber);	        		
		        	}
		        }
		    }     
});
		}*/

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
		treeModel.nodeStructureChanged( treeRoot );
			
	}
	
	public void fillCircuitTree() {
		
		treeRoot.removeAllChildren();
		treeModel.reload();
		
		int deliveryIndex =0;
		int circuitIndex= 0;
		
		DefaultMutableTreeNode circuit;
		
		setBorder(BorderFactory.createTitledBorder("Tournees"));
		if(circuitManagement.getCircuitsList()!=null)
		{
			for( Circuit entry : circuitManagement.getCircuitsList() ) {
			
				circuit = new DefaultMutableTreeNode("Tournee "+ 
						(circuitIndex+1) +": Duree "+entry.getCircuitLength()+" s");
				treeModel.insertNodeInto(circuit, treeRoot, circuitIndex++);
				
				
				for ( Delivery delivery : entry.getDeliveryList()) {
					
					if (deliveryIndex == 0) {
						
						treeModel.insertNodeInto(new DefaultMutableTreeNode ("Entrepot: "
							+ "Duree "+delivery.getDuration()+" s"),
								circuit, deliveryIndex++);
					
					}else {
					
						treeModel.insertNodeInto(new DefaultMutableTreeNode ("Livraison "+ 
								(deliveryIndex) +": Duree "+delivery.getDuration()+" s"),
									circuit, deliveryIndex++);		
					}
				}
				
				deliveryIndex =0;
			}
		}
		
		treeModel.nodeStructureChanged( treeRoot );
		
	}
	
}
