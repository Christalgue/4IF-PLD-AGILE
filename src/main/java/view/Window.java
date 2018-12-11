package main.java.view;

import java.awt.Color;
import java.awt.TextField;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Repository;
import main.java.exception.ManagementException;
import main.java.utils.PopUpType;


public class Window extends JFrame{
	
	private Controller controller;
	private GraphicView graphicView;
	private TextualView textualView;
	
	protected JLabel messageField;
	
	private static JFileChooser chooser;

	protected static ButtonsListener buttonsListener;
	protected static MouseListener mouseListener;	
	protected static KeyListener keyListener;
	
	protected static final String LOAD_MAP = "Charger un plan";
	protected static final String LOAD_DELIVERY_OFFER = "Charger une demande de livraison";
	protected static final String CALCULATE_CIRCUITS = "Calculer les tournees";
	protected static final String ADD_DELIVERY = "Ajouter une livraison";
	protected static final String DELETE_DELIVERY = "Supprimer la livraison";
	protected static final String MOVE_DELIVERY = "Deplacer la livraison";
	protected static final String CONTINUE_CALCULATION = "Continuer le calcul des tournees";
	protected static final String STOP_CALCULATION = "Arreter le calcul des tournees";
	protected static final String UNDO = "Annuler";
	protected static final String REDO = "Retablir";
	protected static final String CANCEL = "Cancel";
	protected static final String RESET_SCALE = "Retablir l'echelle";
	
	protected static final String ZOOM = "+";
	protected static final String UNZOOM = "-";
	
	protected static TextField setNameOfMap;
	protected static TextField setNameOfDeliveryList;
	protected static TextField numberOfDeliveryMenField;
	
	protected static PopUp popUp;
	
	protected static JButton loadDeliveryList;
	protected static JButton calculateCircuitButton;
	protected static JButton addDeliveryButton;
	protected static JButton deleteDeliveryButton;
	protected static JButton moveDeliveryButton;
	protected static JButton undoButton;	
	protected static JButton redoButton;
	protected static JButton upButton;
	protected static JButton downButton;
	protected static JButton rightButton;
	protected static JButton leftButton;
	protected static JButton zoomButton;
	protected static JButton unZoomButton;
	protected static JButton cancelButton;
	protected static JButton resetScaleButton;
	
	protected static final int windowWidth = 1600;
	protected static final int windowHeight = 720;
	protected static final int buttonPanelHeight =50;
	protected static final int graphicWidth = 1030;
	protected static final int messageFieldHeight = 40;
	protected static final int buttonHeight = 40;
	protected static final int buttonSpace = 10;
	
	
	protected static final int pathWidth = 2;
	
	protected JTree textualViewTree;
	protected DefaultMutableTreeNode treeRoot;
	
	protected Delivery selectedNode;
	protected Delivery hoverNode;
	
	protected int selectedCircuit = -1;
	protected int hoverCircuit = -1;
	

	protected HashMap <Integer, Color> colors = new HashMap < Integer,Color>();
	public MyTreeCellRenderer cellRenderer; 
	
	protected static Color selectedColor = new Color(0,150,0);
	protected static Color hoverColor = Color.BLUE;
	protected static Color deliveryColor = Color.RED;
	protected static Color repositoryColor = Color.DARK_GRAY;
	
	/**
	 * Default constructor
	 */
	public Window () {
		
	}
	
	/**
	 * Create the application.
	 */
	public Window (Controller controller){
		
		this.controller = controller;
		
		buttonsListener = new ButtonsListener(controller);
		keyListener = new KeyListener(controller);
		this.setFocusable(true);
		this.addKeyListener(keyListener);
		CircuitManagement circuitManagement = controller.getCircuitManagement();
		
		//////////////////////////////CREATE THE MAIN WINDOW//////////////////////////////
		setControllerWindow(this);
		
		//////////////////////////////CREATE THE HEADER PANEL/////////////////////////////
		JPanel buttonPanel = new JPanel();
		fillButtonPanel(buttonPanel);
		
		//////////////////////////////CREATE THE GRAPHIC VIEW//////////////////////////////
		
		this.graphicView = new GraphicView (circuitManagement, windowHeight-buttonPanelHeight-messageFieldHeight, graphicWidth, pathWidth,this);
		setGraphicView(this.graphicView);
		mouseListener = new MouseListener(controller, this.graphicView, this);
		
		//////////////////////////////CREATE THE TEXTUAL VIEW/////////////////////////////
		
		this.treeRoot = createTree();
		this.textualViewTree = new JTree (this.treeRoot);
		this.cellRenderer = new MyTreeCellRenderer(this, circuitManagement);
		this.textualView = new TextualView (circuitManagement, windowHeight-buttonPanelHeight, windowWidth-graphicWidth, this);
		setTextualView(this.textualView);
		addTreeListener();
		
		//////////////////////////////CREATE THE MESSAGE FIELD/////////////////////////////
		
		this.messageField = new JLabel();
		setMessageField(this.messageField);
		
		//////////////////////////////ADD PANELS TO THE WINDOW//////////////////////////////
		this.getContentPane().add(buttonPanel);
		this.getContentPane().add(this.textualView);
		this.getContentPane().add(this.messageField);
		this.getContentPane().add(this.graphicView);
		
		////////////////////////////// MAKE THE FRAME VISBLE AND PAINTABLE ////////////////////////////////
		this.setVisible(true);
		
		this.graphicView.setGraphics();
		
	}
	
	private static DefaultMutableTreeNode createTree() {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		return root;
	}

	//////////////////////////////PUT COMPOSANTS IN PLACE/////////////////////////////
	private static void setMessageField(JLabel messageField) {
		messageField.setSize( graphicWidth, messageFieldHeight);
		messageField.setLocation(0, buttonPanelHeight);
		messageField.setOpaque(true);
		messageField.setForeground(Color.WHITE);
		messageField.setBackground(Color.DARK_GRAY);
		messageField.setHorizontalTextPosition(JLabel.CENTER);
		messageField.setText("Veuillez selectionner un plan a charger");
	
	}

	public static void setControllerWindow(Window window) {
		
		window.setLayout(null);
		window.setSize(windowWidth, windowHeight);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setTitle("Deliver'IF");
		window.setResizable(false);
		window.setLocationRelativeTo(null);
	
	}
	
	private static void setGraphicView(GraphicView graphicView) {
		
		graphicView.setLocation(0, buttonPanelHeight+messageFieldHeight);
		graphicView.setLayout(null);
		graphicView.setBackground(Color.LIGHT_GRAY);
		graphicView.setSize(graphicWidth, windowHeight-buttonPanelHeight-messageFieldHeight);
		
	}
	
	public static void setMouseListener(Window window) {
		window.graphicView.addMouseListener(mouseListener);
		window.graphicView.addMouseMotionListener(mouseListener);
	}
	
	private static void setTextualView(TextualView textualView) {
		
		textualView.setLocation(graphicWidth, buttonPanelHeight);
		textualView.setBackground(Color.WHITE);
		textualView.setSize(windowWidth-graphicWidth, windowHeight-buttonPanelHeight);
		textualView.setLayout(null);
		
		addDeliveryButton = new JButton(ADD_DELIVERY);
		addDeliveryButton.addActionListener(buttonsListener);
		addDeliveryButton.setVisible(true);
		addDeliveryButton.setEnabled(false);
		addDeliveryButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		addDeliveryButton.setSize(windowWidth-graphicWidth-20, buttonHeight);
		textualView.add(addDeliveryButton);
	
		deleteDeliveryButton = new JButton(DELETE_DELIVERY);
		deleteDeliveryButton.addActionListener(buttonsListener);
		deleteDeliveryButton.setVisible(true);
		deleteDeliveryButton.setEnabled(false);
		deleteDeliveryButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight*2+buttonSpace+50));
		deleteDeliveryButton.setSize(windowWidth-graphicWidth-20, buttonHeight);
		textualView.add(deleteDeliveryButton);	
	
		moveDeliveryButton = new JButton(MOVE_DELIVERY);
		moveDeliveryButton.addActionListener(buttonsListener);
		moveDeliveryButton.setVisible(true);
		moveDeliveryButton.setEnabled(false);
		moveDeliveryButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight+50));
		moveDeliveryButton.setSize(windowWidth-graphicWidth-20, buttonHeight);
		textualView.add(moveDeliveryButton);
		
			
	}
	
	
	public static void fillButtonPanel(JPanel buttonPanel) {
		
		buttonPanel.setSize(windowWidth, buttonPanelHeight);
		buttonPanel.setBackground(Color.WHITE);
		
		resetScaleButton = new JButton(RESET_SCALE);
		resetScaleButton.addActionListener(buttonsListener);
		resetScaleButton.setEnabled(false);
		buttonPanel.add(resetScaleButton);
		
		setNameOfMap = new TextField();
		setNameOfMap.setText("resources/xml/moyenPlan.xml");
		buttonPanel.add(setNameOfMap);
		
		JButton loadMapButton = new JButton(LOAD_MAP);
		loadMapButton.addActionListener(buttonsListener);
		buttonPanel.add(loadMapButton);
		
		setNameOfDeliveryList = new TextField();
		setNameOfDeliveryList.setText("resources/xml/dl-moyen-9.xml");
		setNameOfDeliveryList.setEditable(true);
		buttonPanel.add(setNameOfDeliveryList);
		
		loadDeliveryList = new JButton(LOAD_DELIVERY_OFFER);
		loadDeliveryList.addActionListener(buttonsListener);
		loadDeliveryList.setEnabled(false);
		buttonPanel.add(loadDeliveryList);
		
		JTextArea labelNumberOfDeliveryMen = new JTextArea();
		labelNumberOfDeliveryMen.setText("Nombre de livreurs :");
		labelNumberOfDeliveryMen.setEditable(false);
		buttonPanel.add(labelNumberOfDeliveryMen);
		
		numberOfDeliveryMenField = new TextField();
		numberOfDeliveryMenField.setText("1");
		numberOfDeliveryMenField.setEditable(true);
		buttonPanel.add(numberOfDeliveryMenField);
	
		undoButton = new JButton(UNDO);
		buttonPanel.add(undoButton);
		undoButton.addActionListener(buttonsListener);
		undoButton.setEnabled(false);
		

		redoButton = new JButton(REDO);
		buttonPanel.add(redoButton);
		redoButton.addActionListener(buttonsListener);	
		redoButton.setEnabled(false);
		
		calculateCircuitButton = new JButton(CALCULATE_CIRCUITS);
		calculateCircuitButton.addActionListener(buttonsListener);
		calculateCircuitButton.setEnabled(false);
		buttonPanel.add(calculateCircuitButton);
		
		zoomButton = new JButton(ZOOM);
		zoomButton.addActionListener(buttonsListener);
		zoomButton.setEnabled(true);
		buttonPanel.add(zoomButton);
		
		unZoomButton = new JButton(UNZOOM);
		unZoomButton.addActionListener(buttonsListener);
		unZoomButton.setEnabled(true);
		buttonPanel.add(unZoomButton);
		
		cancelButton = new JButton(CANCEL);
		cancelButton.addActionListener(buttonsListener);
		cancelButton.setEnabled(true);
		buttonPanel.add(cancelButton);
	}
	
	//////////////////////////////GET DATA FROM WINDOW/////////////////////////////
	protected String getFile () {
			
		chooser = new JFileChooser();
		int returnValue = chooser.showOpenDialog(controller.getWindow());
		String filePath = "";
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			filePath = chooser.getSelectedFile().getAbsolutePath();
		}
		return filePath;
	}

	public boolean getDeliveryMenNumber() {
		
		boolean correctValue = false;
		try{
        	String numberOfDeliveryMen = numberOfDeliveryMenField.getText();
            int numberOfDeliveryMenValue = Integer.parseInt(numberOfDeliveryMen);
            if (numberOfDeliveryMenValue <= 0) {
            	PopUp.errorPopUp(this, false);
            } else {
            	buttonsListener.setDeliveryMenNumber(numberOfDeliveryMenValue);
            	correctValue = true;
            }
        } catch(Exception parseException) {
        	PopUp.errorPopUp(this, false);
        }
		return correctValue;
		
	}
	
	public static void getMapName() {
		
		buttonsListener.setMap(setNameOfMap.getText());
		
	}
	/////
	
	public static void getDeliveryListName() {
		
		buttonsListener.setDeliveryList(setNameOfDeliveryList.getText());
		
	}
	
	//////////////////////////////CHANGE DISPLAYED TEXT/////////////////////////////
	public void setTextualViewBorderTitle( String string) {	
		textualView.setBorder(BorderFactory.createTitledBorder(string));
	}
	
	public void setMessage( String string) {
		messageField.setBackground(Color.DARK_GRAY);
		messageField.setText(string);
	}
	
	public void setErrorMessage( String string) {
		messageField.setBackground(Color.RED);
		messageField.setText(string);
	}
	
	public void setWarningMessage(String string) {
		messageField.setBackground(Color.ORANGE);
		messageField.setText(string);
	}
	//////////////////////////////DRAW COMPOSANTS/////////////////////////////
	public void calculateScale() {
		graphicView.calculateScale(controller.getCircuitManagement());
	}
	
	public void drawMap() {
		//graphicView.removeAll();
		//graphicView.update(graphicView.getGraphics());
		graphicView.paintMap();
	}
	
	public void drawDeliveries() {
		drawMap();
		graphicView.paintDeliveries();
		textualView.fillDeliveryTree();
	}
	
	public void drawCircuits() {
		drawDeliveries();
		graphicView.paintCircuits();
		if ( controller.getCircuitManagement().getCircuitsList()!= null) {
			textualView.fillCircuitTree();
		}
	}

	public void fillDeliveryTree() {
		textualView.fillDeliveryTree();
	}
	
	//////////////////////////////BUTTON ACTIVATION/////////////////////////////
	public void enableButtonLoadDeliveriesList() {
		loadDeliveryList.setEnabled(true);
	}
	
	public void enableButtonCalculateCircuit() {
		calculateCircuitButton.setEnabled(true);
	}	
	
	public void enableButtonUndo() {
		undoButton.setEnabled(true);
	}
	
	public void enableButtonRedo() {
		redoButton.setEnabled(true);
	}
	
	public void enableButtonAddDelivery() {
		addDeliveryButton.setVisible(true);
		addDeliveryButton.setEnabled(true);
	}
	
	public void enableButtonDeleteDelivery() {
		deleteDeliveryButton.setVisible(true);;
		deleteDeliveryButton.setEnabled(true);
	}
	
	public void enableButtonMoveDelivery() {
		moveDeliveryButton.setVisible(true);;
		moveDeliveryButton.setEnabled(true);
	}

	
	//////////////////////////////BUTTON DESACTIVATION/////////////////////////////
	public void disableButtonLoadDeliveriesList() {
		loadDeliveryList.setEnabled(false);
	}
	
	public void disableButtonCalculateCircuit() {
		calculateCircuitButton.setEnabled(false);
	}	
	
	public void disableButtonAddDelivery() {
		addDeliveryButton.setEnabled(false);
	}
	
	public void disableButtonDeleteDelivery() {
		deleteDeliveryButton.setEnabled(false);
	}
	
	public void disableButtonMoveDelivery() {
		moveDeliveryButton.setEnabled(false);
	}
	
	public void disableButtonUndo() {
		undoButton.setEnabled(false);
	}
	
	public void disableButtonRedo() {
		redoButton.setEnabled(false);
	}
	
	public void enableResetScaleButton() {
		 resetScaleButton.setEnabled(true);
	 }
	 
	 public void disableResetScaleButton() {
		 resetScaleButton.setEnabled(false);
	 }
	
	//////////////////////////////POP UP MANAGEMENT/////////////////////////////
	public int getPopUpValue(PopUpType message, Window window) {
		
		return popUp.displayPopUp(message, window);
	}
	
	public void manageAddPopUpValue(int userChoice) {
		if (userChoice == 0) {
			try {
				controller.validateAdd();
			} catch (ManagementException e) {
				e.printStackTrace();
			}
		} else {
			controller.cancelAdd();
		}
	}
	
	public void manageDeletePopUpValue(int userChoice) {
		if (userChoice == 0) {
			try {
				controller.validateDelete();
			} catch (ManagementException e) {
				e.printStackTrace();
			}
		} else {
			controller.cancelDelete();
		}
	}
	
	 public void manageDurationPopUpValue(int inputValue) {
		 if (inputValue >= 0) {
			try {
				controller.validateDuration(inputValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			controller.cancelDuration();
		}
	}
	
	public void manageMovePopUpValue(int userChoice) {
		if (userChoice == 0) {
			try {
				controller.validateMove();
			} catch (ManagementException e) {
				e.printStackTrace();
			}
		} else {
			controller.cancelMove();
		}
	}
	
	 public void manageContinuePopUpValue(int userChoice) {
		if (userChoice == 0) {
			controller.continueCalculation(false);
		} else if (userChoice == 1) {
			controller.continueCalculation(true);
		}
	 }
	 
	 public void zoom() {
		graphicView.zoom();
	 }
	 
	 public void unZoom() {
		graphicView.unZoom();
	 }
	 
	 public void horizontalShift( int right) {
		 graphicView.horizontalShift(right);
	 }
	 
	 public void verticalShift(int down) {
		 graphicView.verticalShift(down);
	 }
	 
	 public void emptyColors () {
		 colors = new HashMap < Integer,Color>();
	 }
	 
	 public void addTreeListener () {
		 
		 textualViewTree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        
		    	if ( treeRoot.getChildCount() != 0) {
		    		
			    	DefaultMutableTreeNode deliveryPoint = (DefaultMutableTreeNode) textualViewTree.getLastSelectedPathComponent();
			        
			    	if ( deliveryPoint != null ) {
				        String deliveryInfo = (String) deliveryPoint.getUserObject();
				        
				        if (!deliveryInfo.startsWith("Entrepot")) {
				        	
				        	if (!deliveryInfo.startsWith("Tournee")) {
				        		String secondPart = deliveryInfo.substring(10);
				        		String[] split = secondPart.split(":");
				        		String deliveryNumber = split[0];
				        		int deliveryIndex = Integer.parseInt(deliveryNumber);
				        		Delivery delivery = controller.getCircuitManagement().getDeliveryByIndex(deliveryIndex); 
				        		nodeSelected(delivery);
				        		controller.treeDeliverySelected(delivery);
				        	} else {
				        		String secondPart = deliveryInfo.substring(8);
				        		String[] split = secondPart.split(":");
				        		String circuitNumber = split[0];
				        		int circuitIndex = Integer.parseInt(circuitNumber);	
				        		textualCircuitSelected(circuitIndex-1);
				        	}
				        } else {
				        	Delivery delivery = controller.getCircuitManagement().getDeliveryList().get(0);
			        		nodeSelected(delivery);
				        	controller.treeDeliverySelected(delivery);
				        }
				        
			    	}
		    	}
		    }     
});
		}
	 
	public void nodeSelected(Delivery delivery) {
		if(selectedNode!=null){
			graphicView.unPaintNode( selectedNode);
		}
			
		graphicView.paintSelectedNode( delivery, true);
		hoverNode = null;
		selectedNode = delivery;
			
		setSelectedTreeNode( delivery,true);
	}
		
	public void nodeHover(Delivery delivery) {
		//Mouse exit a node
		if(delivery == null && hoverNode!=null) {
			graphicView.unPaintNode( hoverNode);
			hoverNode = delivery;
		}
		//Cannot hover a selected node
		else if ( delivery != null && (selectedNode == null || delivery.getPosition().getId()!=selectedNode.getPosition().getId())){
			//Mouse enter a node
			if(delivery!=null && hoverNode == null) {
				graphicView.paintSelectedNode( delivery, false);
			}
			//Mouse pass from one node to another
			else if(delivery!=null && delivery.getPosition().getId()!=hoverNode.getPosition().getId()) {
				graphicView.unPaintNode( hoverNode);
				graphicView.paintSelectedNode( delivery, false);
			}
			hoverNode = delivery;
		
			if ( controller.getCircuitManagement().getCircuitsList()!= null) {
				circuitHover(delivery);
			}		
			
		}
		
		setSelectedTreeNode (delivery, false);
			
	}
		
	public void circuitSelected(Delivery selectedDelivery) {

		if (selectedDelivery != null && controller.getCircuitManagement().getCircuitsList()!= null) {
			
			int circuitIndex;
								
			if (selectedDelivery.getDuration()!= -1)
				circuitIndex = controller.getCircuitManagement().getCircuitIndexByDelivery( selectedDelivery);
			else
				circuitIndex = controller.getCircuitManagement().getCircuitIndexByNode( selectedDelivery);

			if(selectedCircuit!= -1 && selectedCircuit != circuitIndex){
				graphicView.unPaintCircuit( selectedCircuit);
			}
			
			if (circuitIndex != -1) {
				graphicView.paintSelectedCircuit(circuitIndex, true); 
				hoverCircuit = -1 ;
				selectedCircuit = circuitIndex;
			}
			
			setSelectedTreeNode(circuitIndex, true);
		}
		
	}

	public void circuitHover(Delivery delivery) {
					
		int toDrawCircuit = controller.getCircuitManagement().getCircuitIndexByNode(delivery);
				
		if( toDrawCircuit != -1 ) {
			
			if( toDrawCircuit != selectedCircuit) {
					
				//Mouse enter a node
				if( hoverCircuit == -1) {
					graphicView.paintSelectedCircuit( toDrawCircuit, false);
				}
				//Mouse pass from one node to another
				else if( toDrawCircuit != hoverCircuit) {
					graphicView.unPaintCircuit( hoverCircuit);
					graphicView.paintSelectedCircuit( toDrawCircuit, false);
				}
				hoverCircuit = toDrawCircuit;
					
			} else if ( toDrawCircuit == selectedCircuit) {
			
			graphicView.paintSelectedCircuit( toDrawCircuit, true);
			hoverCircuit = -1;
			
			}
		} else {
			graphicView.unPaintCircuit( hoverCircuit);
			hoverCircuit =-1;
		}
		
		setSelectedTreeNode (toDrawCircuit, false);
	}		
		
	public void textualCircuitSelected(int circuitIndex) {
		graphicView.unPaintCircuit(selectedCircuit);
		graphicView.paintSelectedCircuit(circuitIndex);
		selectedCircuit = circuitIndex;
		setSelectedTreeNode (circuitIndex, true);
	}	
	
	 public void setSelectedTreeNode ( Delivery delivery, boolean selected ){
			 
		 if ( delivery != null && delivery.getDuration() != -1) {
			 
			 if ( delivery instanceof Repository ) {
				 cellRenderer.setSelectedDelivery("Entrepot: Duree 0s", selected);
			 } else {
				 int index = controller.getCircuitManagement().getDeliveryIndex(delivery);
				 String string = "Livraison "+ index +": Duree "+delivery.getDuration()+" s";
				 cellRenderer.setSelectedDelivery(string, selected);
			 }
		 }else {
			 cellRenderer.setSelectedDelivery("", selected);
		 }
		 
		 //((DefaultTreeModel) textualViewTree.getModel()).reload();
		 ((DefaultTreeModel) textualViewTree.getModel()).nodeChanged(treeRoot);

	 }
	 
	 public void resetScale() {
		 graphicView.resetDefaultValues();
	 }
		
	private void setSelectedTreeNode(int circuitIndex, boolean selected) {
			
		if ( circuitIndex != -1 ) {
			 cellRenderer.setSelectedCircuit("Tournee "+(circuitIndex+1) +": Duree "+controller.getCircuitManagement().getCircuitByIndex(circuitIndex).getCircuitLength()+" s", selected);
		 } else {
			 cellRenderer.setSelectedCircuit("", selected);

		 }
		 
		 //((DefaultTreeModel) textualViewTree.getModel()).reload();
		 ((DefaultTreeModel) textualViewTree.getModel()).nodeChanged(treeRoot);

	}			
		
	public void emptySelectedNode() {
		selectedNode = null;
	}
	
	public void emptySelectedCircuit() {
		selectedCircuit = -1;
	}
}
