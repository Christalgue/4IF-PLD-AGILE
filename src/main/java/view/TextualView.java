package main.java.view;

import javax.swing.JPanel;

import main.java.entity.CircuitManagement;

public class TextualView extends JPanel{

	private CircuitManagement circuitManagement;
	private int viewHeight;
	private int viewWidth;	
	
	public TextualView () {
		
	}
	
	public TextualView(CircuitManagement circuitManagement, int viewHeight, int viewWidth) {
		
		super();
		
		this.circuitManagement = circuitManagement;
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
	
	}

	
	
	
	
/*		//To change icons for the tree
		//https://stackoverflow.com/questions/20691946/set-icon-to-each-node-in-jtree
		JTree tree = new JTree();
		tree.setBorder(new LineBorder(new Color(0, 0, 0)));
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Liste des tournées") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					node_1 = new DefaultMutableTreeNode("Tournée 1");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Durée totale : 1h45min"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 4"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tournée 2");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Durée totale : 2h"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 4"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tournée 3");
						node_2 = new DefaultMutableTreeNode("Informations ");
							node_2.add(new DefaultMutableTreeNode("Durée totale : 1h53min"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Livraisons");
							node_2.add(new DefaultMutableTreeNode("Point de livraison 1"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 2"));
							node_2.add(new DefaultMutableTreeNode("Point de livraison 3"));
						node_1.add(node_2);
					add(node_1);
				}
			}
		));
		frame.getContentPane().add(tree, BorderLayout.EAST);*/
	
}
