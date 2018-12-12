package main.java.view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;

// TODO: Auto-generated Javadoc
/**
 * The Class TextualView.
 */
public class TextualView extends JPanel{

	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The view height. */
	private int viewHeight;
	
	/** The view width. */
	private int viewWidth;	
	
	/** The delivery tree. */
	private JTree deliveryTree;
	
	/** The tree model. */
	private DefaultTreeModel treeModel;
	
	/** The tree root. */
	private DefaultMutableTreeNode treeRoot;
	
	/** The scroll pane. */
	private JScrollPane scrollPane;
	
	/** The window. */
	private Window window;
	
	/**
	 * Instantiates a new textual view.
	 */
	public TextualView () {
		
	}
	
	/**
	 * Instantiates a new textual view.
	 *
	 * @param circuitManagement the circuit management
	 * @param viewHeight the view height
	 * @param viewWidth the view width
	 * @param window the window
	 */
	public TextualView(CircuitManagement circuitManagement, int viewHeight, int viewWidth, 
			Window window ) {
		
		super();
		
		this.circuitManagement = circuitManagement;
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		
		this.deliveryTree = window.textualViewTree;
		this.treeRoot = (DefaultMutableTreeNode) deliveryTree.getModel().getRoot();
		
		//this.addTreeListener();
		this.window = window;
		
		this.deliveryTree.expandRow(0);
		this.deliveryTree.setRootVisible(false);
		this.deliveryTree.setShowsRootHandles(true);
		this.deliveryTree.setCellRenderer(window.cellRenderer);
		
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

	/**
 * Fill delivery tree.
 */

public void emptyTree() {
	treeRoot.removeAllChildren();
	treeModel.reload();
}
	
public void fillDeliveryTree() {

		setBorder(BorderFactory.createTitledBorder("Livraisons"));
		
		int deliveryListIndex = 0;
		
		if (circuitManagement.getDeliveryList() != null) {
			for( Delivery entry : circuitManagement.getDeliveryList() ) {
	
				if (deliveryListIndex == 0) {
					
					treeModel.insertNodeInto(new DefaultMutableTreeNode ("Entrepot: Duree 0s"),
							treeRoot, deliveryListIndex++);
				
				} else {
				
					treeModel.insertNodeInto(new DefaultMutableTreeNode ("Livraison "+ 
							deliveryListIndex +": Duree "+(int)(entry.getDuration()/60)+"min"+(int)(entry.getDuration()%60)+"s"),
								treeRoot, deliveryListIndex++);				
				}
			}
		}
		
		treeModel.nodeStructureChanged( treeRoot );
		
	}
	
	/**
	 * Fill circuit tree.
	 */
	public void fillCircuitTree() {
		
		int deliveryIndex =0;
		int circuitIndex= 0;
		
		DefaultMutableTreeNode circuit;
		
		setBorder(BorderFactory.createTitledBorder("Tournees"));
		if(circuitManagement.getCircuitsList()!=null)
		{
			for( Circuit entry : circuitManagement.getCircuitsList() ) {
				double duration = entry.getCircuitDuration();
				int durationHour = (int) duration / (int) 3600;
				int durationMinutes = ((int)duration % 3600) / (int) 60;
				circuit = new DefaultMutableTreeNode("Tournee "+ 
						(circuitIndex+1) + ": Duree "+ durationHour + "h" + durationMinutes + "min" + (int)(duration%60) +"s");

				treeModel.insertNodeInto(circuit, treeRoot, circuitIndex++);
				
				
				for ( Delivery delivery : entry.getDeliveryList()) {
					
					if (deliveryIndex == 0) {
						
						treeModel.insertNodeInto(new DefaultMutableTreeNode ("Entrepot: Duree 0s"),
								circuit, deliveryIndex++);
					
					}else {
					
						treeModel.insertNodeInto(new DefaultMutableTreeNode ("Livraison "+ 
								circuitManagement.getDeliveryIndex(delivery) +": Duree "+(int)(delivery.getDuration()/60)+"min"+(int)(delivery.getDuration()%60)+"s"),
									circuit, deliveryIndex++);		
					}
				}
				
				deliveryIndex =0;
			}
		}
		
		treeModel.nodeStructureChanged( treeRoot );
		
	}
	
}
