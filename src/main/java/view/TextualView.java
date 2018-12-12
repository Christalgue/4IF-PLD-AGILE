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
import main.java.entity.Repository;

/**
 * The Class TextualView is a panel that display a tree filled with every deliveries and/or circuits
 */
public class TextualView extends JPanel{

	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The view height. */
	private int viewHeight;
	
	/** The view width. */
	private int viewWidth;	
	
	/** The deliveries and circuits tree. */
	private JTree tree;
	
	/** The tree model. */
	private DefaultTreeModel treeModel;
	
	/** The tree root node. */
	private DefaultMutableTreeNode treeRoot;
	
	/** The scroll pane in which the tree is displayed. */
	private JScrollPane scrollPane;
	
	/** The window to which the view belongs. */
	private Window window;
	
	/**
	 * Default constructor
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
		
		this.tree = window.textualViewTree;
		this.treeRoot = (DefaultMutableTreeNode) tree.getModel().getRoot();
		
		this.window = window;
		
		// The tree's root is hidden and our custom renderer is applied
		this.tree.expandRow(0);
		this.tree.setRootVisible(false);
		this.tree.setShowsRootHandles(true);
		this.tree.setCellRenderer(window.cellRenderer);
		
		this.treeModel = (DefaultTreeModel) this.tree.getModel();
		
		// The tree is added to a scroll pane, which is added to the textualView 
		this.scrollPane = new JScrollPane(this.tree);
		scrollPane.setSize(viewWidth-20, viewHeight-220);
		scrollPane.setLocation(10, 20);
		this.add(scrollPane);
		this.setVisible(true);
		
	}
	


	/**
	 * Empty the tree.
	 */
	public void emptyTree() {
		treeRoot.removeAllChildren();
		treeModel.reload();
	}
	
	/**
	 * Fill the tree, based on the circuitManagement's deliveryList
	 */
	public void fillDeliveryTree() {

		// Display a textualView's border
		setBorder(BorderFactory.createTitledBorder("Livraisons"));
		
		// If circuitManagement's deliveryList is not empty
		if (circuitManagement.getDeliveryList() != null) {
			
			int deliveryListIndex = 0;
			
			// For each delivery in it, it is inserted in the tree, displaying
			// its index and duration
			for( Delivery entry : circuitManagement.getDeliveryList() ) {
				
				if ( entry instanceof Repository) {

					treeModel.insertNodeInto(new DefaultMutableTreeNode ("Entrepot: Duree 0s"),
							treeRoot, deliveryListIndex++);
				
				} else {
				
					treeModel.insertNodeInto(new DefaultMutableTreeNode ("Livraison "+ 
							deliveryListIndex +": Duree "+(int)(entry.getDuration()/60)+"min"+(int)(entry.getDuration()%60)+"s"),
								treeRoot, deliveryListIndex++);				
				}
			}
			
			// Update the tree
			treeModel.nodeStructureChanged( treeRoot );

		}
				
	}
	
	/**
	 * Fill the tree, based on circuitManagement's circuitsList
	 */
	public void fillCircuitTree() {

		// Display a textualView's border
		setBorder(BorderFactory.createTitledBorder("Tournees"));
		
		// If circuitManagement's circuitsList is not empty
		if(circuitManagement.getCircuitsList()!=null)
		{
			
			int deliveryIndex =0;
			int circuitIndex= 0;
			
			DefaultMutableTreeNode circuit;

			// For each circuit in it, it is inserted in the tree, displaying
			// its index and duration
			for( Circuit entry : circuitManagement.getCircuitsList() ) {
				
				double duration = entry.getCircuitDuration();
				int durationHour = (int) duration / (int) 3600;
				int durationMinutes = ((int)duration % 3600) / (int) 60;
				circuit = new DefaultMutableTreeNode("Tournee "+ 
						(circuitIndex+1) + ": Duree "+ durationHour + "h" + durationMinutes + "min" + (int)(duration%60) +"s");

				treeModel.insertNodeInto(circuit, treeRoot, circuitIndex++);
				
				// For each delivery in the circuit, it is inserted in the tree, displaying
				// its index and duration
				for ( Delivery delivery : entry.getDeliveryList()) {
					
					if (delivery instanceof Repository) {
						
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
		
			treeModel.nodeStructureChanged( treeRoot );
			
		}
		
	}
	
}
