package main.java.view;

import java.awt.Color;
import java.awt.TextField;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.tree.TreeNode;

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
	protected static final String UNDO = "Retour";
	protected static final String REDO = "Suivant";
	protected static final String CANCEL = "Annuler";
	protected static final String RESET_SCALE = "Retablir l'echelle";
	protected static final String GENERATE_ROADMAP = "Generer la feuille de route";
	protected static final String UP = "U";
	protected static final String DOWN = "D";
	protected static final String RIGHT = "R";
	protected static final String LEFT = "L";
	
	protected static final String ZOOM = "Zoomer";
	protected static final String UNZOOM = "Dezoomer";
	
	protected static TextField setNameOfMap;
	protected static TextField setNameOfDeliveryList;
	protected static TextField numberOfDeliveryMenField;
	
	protected static PopUp popUp;
	
	protected static JButton loadDeliveryList;
	protected static JButton calculateCircuitButton;
	protected static JButton continueCalculateCircuitButton;
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
	protected static JButton cancelAddButton;
	protected static JButton resetScaleButton;
	protected static JButton generateRoadmapButton;
	
	protected static final int windowWidth = 1500;
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
		setFocusable(true);
		addKeyListener(keyListener);
		CircuitManagement circuitManagement = controller.getCircuitManagement();
		
		//////////////////////////////CREATE THE MAIN WINDOW//////////////////////////////
		setControllerWindow(this);
		
		//////////////////////////////CREATE THE HEADER PANEL/////////////////////////////
		JPanel buttonPanel = new JPanel();
		fillButtonPanel(buttonPanel);
		
		//////////////////////////////CREATE THE GRAPHIC VIEW//////////////////////////////
		
		graphicView = new GraphicView (circuitManagement, windowHeight-buttonPanelHeight-messageFieldHeight, graphicWidth, pathWidth,this);
		setGraphicView(graphicView);
		mouseListener = new MouseListener(controller, graphicView, this);
		
		//////////////////////////////CREATE THE TEXTUAL VIEW/////////////////////////////
		
		treeRoot = createTree();
		textualViewTree = new JTree (treeRoot);
		cellRenderer = new MyTreeCellRenderer(this, circuitManagement);
		textualView = new TextualView (circuitManagement, windowHeight-buttonPanelHeight, windowWidth-graphicWidth, this);
		setTextualView(textualView);
		addTreeListener();
		
		//////////////////////////////CREATE THE MESSAGE FIELD/////////////////////////////
		
		messageField = new JLabel();
		setMessageField(messageField);
		
		//////////////////////////////ADD PANELS TO THE WINDOW//////////////////////////////
		getContentPane().add(buttonPanel);
		getContentPane().add(textualView);
		getContentPane().add(messageField);
		getContentPane().add(graphicView);
		
		////////////////////////////// MAKE THE FRAME VISBLE AND PAINTABLE ////////////////////////////////
		setVisible(true);
		
		graphicView.setGraphics();
		
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
	
	private static void addIconToButton (JButton button, String path) {
		try {
		    ImageIcon img = new ImageIcon(path);
		    button.setIcon(img);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	private static void setTextualView(TextualView textualView) {
		
		textualView.setLocation(graphicWidth, buttonPanelHeight);
		textualView.setBackground(Color.WHITE);
		textualView.setSize(windowWidth-graphicWidth, windowHeight-buttonPanelHeight);
		textualView.setLayout(null);
		
		zoomButton = new JButton();
		zoomButton.setActionCommand(ZOOM);
		addIconToButton(zoomButton, "resources/img/zoom-in.png");
		zoomButton.addActionListener(buttonsListener);
		zoomButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		zoomButton.setSize(40, buttonHeight);
		zoomButton.setEnabled(false);
		textualView.add(zoomButton);
		
		resetScaleButton = new JButton();
		resetScaleButton.setActionCommand(RESET_SCALE);
		addIconToButton(resetScaleButton, "resources/img/zoom-1:1.png");
		resetScaleButton.addActionListener(buttonsListener);
		resetScaleButton.setLocation(55, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		resetScaleButton.setSize(40, buttonHeight);
		resetScaleButton.setEnabled(false);
		textualView.add(resetScaleButton);
		
		unZoomButton = new JButton();
		unZoomButton.setActionCommand(UNZOOM);
		addIconToButton(unZoomButton, "resources/img/zoom-out.png");
		unZoomButton.addActionListener(buttonsListener);
		unZoomButton.setLocation(100, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		unZoomButton.setSize(40, buttonHeight);
		unZoomButton.setEnabled(false);
		textualView.add(unZoomButton);
		
		upButton = new JButton();
		upButton.setActionCommand(UP);
		addIconToButton(upButton, "resources/img/chevron-up.png");
		upButton.addActionListener(buttonsListener);
		upButton.setLocation(60, windowHeight-buttonPanelHeight-(buttonHeight*2+buttonSpace+50));
		upButton.setSize(30, 30);
		upButton.setEnabled(false);
		textualView.add(upButton);
		
		downButton = new JButton();
		downButton.setActionCommand(DOWN);
		addIconToButton(downButton, "resources/img/chevron-down.png");
		downButton.addActionListener(buttonsListener);
		downButton.setLocation(60, windowHeight-buttonPanelHeight-(buttonHeight+40));
		downButton.setSize(30, 30);
		downButton.setEnabled(false);
		textualView.add(downButton);
		
		rightButton = new JButton();
		rightButton.setActionCommand(RIGHT);
		addIconToButton(rightButton, "resources/img/chevron-right.png");
		rightButton.addActionListener(buttonsListener);
		rightButton.setLocation(90, windowHeight-buttonPanelHeight-(buttonHeight+70));
		rightButton.setSize(30, 30);
		rightButton.setEnabled(false);
		textualView.add(rightButton);
		
		leftButton = new JButton();
		leftButton.setActionCommand(LEFT);
		addIconToButton(leftButton, "resources/img/chevron-left.png");
		leftButton.addActionListener(buttonsListener);
		leftButton.setLocation(30, windowHeight-buttonPanelHeight-(buttonHeight+70));
		leftButton.setSize(30, 30);
		leftButton.setEnabled(false);
		textualView.add(leftButton);
		
		addDeliveryButton = new JButton(ADD_DELIVERY);
		addDeliveryButton.addActionListener(buttonsListener);
		addDeliveryButton.setVisible(true);
		addDeliveryButton.setEnabled(false);
		addDeliveryButton.setLocation(150, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		addDeliveryButton.setSize(windowWidth-graphicWidth-160, buttonHeight);
		textualView.add(addDeliveryButton);
	
		deleteDeliveryButton = new JButton(DELETE_DELIVERY);
		deleteDeliveryButton.addActionListener(buttonsListener);
		deleteDeliveryButton.setVisible(true);
		deleteDeliveryButton.setEnabled(false);
		deleteDeliveryButton.setLocation(150, windowHeight-buttonPanelHeight-(buttonHeight*2+buttonSpace+50));
		deleteDeliveryButton.setSize(windowWidth-graphicWidth-160, buttonHeight);
		textualView.add(deleteDeliveryButton);	
	
		moveDeliveryButton = new JButton(MOVE_DELIVERY);
		moveDeliveryButton.addActionListener(buttonsListener);
		moveDeliveryButton.setVisible(true);
		moveDeliveryButton.setEnabled(false);
		moveDeliveryButton.setLocation(150, windowHeight-buttonPanelHeight-(buttonHeight+50));
		moveDeliveryButton.setSize(windowWidth-graphicWidth-160, buttonHeight);
		textualView.add(moveDeliveryButton);
		
			
	}
	
	
	public static void fillButtonPanel(JPanel buttonPanel) {
		
		undoButton = new JButton();
		undoButton.setActionCommand(UNDO);
		addIconToButton(undoButton, "resources/img/backward.png");
		buttonPanel.add(undoButton);
		undoButton.addActionListener(buttonsListener);
		undoButton.setEnabled(false);

		redoButton = new JButton();
		redoButton.setActionCommand(REDO);
		addIconToButton(redoButton, "resources/img/forward.png");
		buttonPanel.add(redoButton);
		redoButton.addActionListener(buttonsListener);	
		redoButton.setEnabled(false);
		
		buttonPanel.setSize(windowWidth, buttonPanelHeight);
		buttonPanel.setBackground(Color.WHITE);
		
		JButton loadMapButton = new JButton(LOAD_MAP);
		loadMapButton.addActionListener(buttonsListener);
		buttonPanel.add(loadMapButton);
		
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
		
		calculateCircuitButton = new JButton(CALCULATE_CIRCUITS);
		calculateCircuitButton.addActionListener(buttonsListener);
		calculateCircuitButton.setEnabled(false);
		buttonPanel.add(calculateCircuitButton);
		
		continueCalculateCircuitButton = new JButton(CONTINUE_CALCULATION);
		continueCalculateCircuitButton.addActionListener(buttonsListener);
		continueCalculateCircuitButton.setEnabled(false);
		buttonPanel.add(continueCalculateCircuitButton);
		cancelAddButton = new JButton(CANCEL);
		cancelAddButton.addActionListener(buttonsListener);
		cancelAddButton.setEnabled(false);
		buttonPanel.add(cancelAddButton);
		
		generateRoadmapButton = new JButton(GENERATE_ROADMAP);
		generateRoadmapButton.addActionListener(buttonsListener);
		generateRoadmapButton.setEnabled(false);
		buttonPanel.add(generateRoadmapButton);
	}
	
	//////////////////////////////GET DATA FROM WINDOW/////////////////////////////
	protected String getFile () {
			
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		int returnValue = chooser.showOpenDialog(controller.getWindow());
		String filePath = "";
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			filePath = chooser.getSelectedFile().getAbsolutePath();
		}
		return filePath;
		
	}
	
	protected String getFolder() {
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = chooser.showOpenDialog(controller.getWindow());
		String folderPath = "";
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			folderPath = chooser.getSelectedFile().getAbsolutePath();
		}
		return folderPath;
		
	}

	public boolean getDeliveryMenNumber() {
		
		boolean correctValue = false;
		int numberOfDeliveries = controller.getCircuitManagement().getDeliveryList().size();
		try{
        	String numberOfDeliveryMen = numberOfDeliveryMenField.getText();
            int numberOfDeliveryMenValue = Integer.parseInt(numberOfDeliveryMen);
            if (numberOfDeliveryMenValue <= 0 || numberOfDeliveryMenValue > numberOfDeliveries -1) {
            	PopUp.errorPopUp(this, false, numberOfDeliveries);
            } else {
            	buttonsListener.setDeliveryMenNumber(numberOfDeliveryMenValue);
            	correctValue = true;
            }
        } catch(Exception parseException) {
        	PopUp.errorPopUp(this, false, numberOfDeliveries);
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
		textualView.emptyTree();
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
			textualView.emptyTree();
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
	
	public void enableButtonContinueCalculation() {
		continueCalculateCircuitButton.setVisible(true);
		continueCalculateCircuitButton.setEnabled(true);
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
	
	public void enableZoomButton() {
		zoomButton.setEnabled(true);
		keyListener.zoomEnable(true);
	}

	public void enableDeZoomButton() {
		unZoomButton.setEnabled(true);
		keyListener.unZoomEnable(true);
	}

	public void enableCancelButton() {
		cancelAddButton.setEnabled(true);
	}
	
	public void enableResetScaleButton() {
		resetScaleButton.setEnabled(true);
	}
	
	public void enableGenerateRoadmapButton () {
		generateRoadmapButton.setEnabled(true);
	}
	
	public void enableArrows() {
		upButton.setEnabled(true);
		downButton.setEnabled(true);
		rightButton.setEnabled(true);
		leftButton.setEnabled(true);
		keyListener.shiftEnable(true);
	}
	
	//////////////////////////////BUTTON DESACTIVATION/////////////////////////////
	public void disableButtonLoadDeliveriesList() {
		loadDeliveryList.setEnabled(false);
	}
	
	public void disableButtonCalculateCircuit() {
		calculateCircuitButton.setEnabled(false);
	}
	
	public void disableButtonContinueCalculation() {
		continueCalculateCircuitButton.setEnabled(false);
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
	
	public void disableZoomButton() {
		zoomButton.setEnabled(false);
	}
		
	public void disableDeZoomButton() {
		unZoomButton.setEnabled(false);
	}

	public void disableCancelButton() {
		cancelAddButton.setEnabled(false);
	}
	
	public void disableResetScaleButton() {
		resetScaleButton.setEnabled(false);
	}
	
	public void disableGenerateRoadmapButton () {
		generateRoadmapButton.setEnabled(false);
	}
	
	public void disableArrows() {
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		rightButton.setEnabled(false);
		leftButton.setEnabled(false);
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
			        
		        	setRepositoryCircuit(-1);
			    	
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
				        	TreeNode parentCircuit = deliveryPoint.getParent();
				        	
				        	Delivery delivery = controller.getCircuitManagement().getDeliveryList().get(0);
			        		nodeSelected(delivery);
				        	controller.treeDeliverySelected(delivery);
				        
				        	if ( parentCircuit != null) {
				        		String circuitInfo = (String) ((DefaultMutableTreeNode) parentCircuit).getUserObject();
				        		String secondPart = circuitInfo.substring(8);
				        		String[] split = secondPart.split(":");
				        		String circuitNumber = split[0];
				        		int circuitIndex = Integer.parseInt(circuitNumber);	
				        		textualCircuitSelected(circuitIndex-1);
				        		setRepositoryCircuit (circuitIndex);
				        	}
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
		
		setRepositoryCircuit(-1);
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
				 String string = "Livraison "+ 
							index +": Duree "+(int)(delivery.getDuration()/60)+"min"+(int)(delivery.getDuration()%60)+"s";
				 cellRenderer.setSelectedDelivery(string, selected);
			 }
		 }else {
			 cellRenderer.setSelectedDelivery("", selected);
		 }
		 
		 ((DefaultTreeModel) textualViewTree.getModel()).nodeChanged(treeRoot);

	 }
	 
	 public void resetScale() {
		 graphicView.resetDefaultValues();
	 }
		
	private void setSelectedTreeNode(int circuitIndex, boolean selected) {
			
		if ( circuitIndex != -1 ) {
			 double duration = controller.getCircuitManagement().getCircuitByIndex(circuitIndex).getCircuitDuration();
			 int durationHour = (int) duration / (int) 3600;
			 int durationMinutes = ((int)duration % 3600) / (int) 60;
			 String string = "Tournee "+ 
						(circuitIndex+1) + ": Duree "+ durationHour + "h" + durationMinutes + "min" + (int)(duration%60) +"s";
			 cellRenderer.setSelectedCircuit(string, selected);
		
		} else {
			 cellRenderer.setSelectedCircuit("", selected);

		 }
		 
		 ((DefaultTreeModel) textualViewTree.getModel()).nodeChanged(treeRoot);

	}
	

	private void setRepositoryCircuit(int circuitIndex) {
		
		if (circuitIndex !=-1) {
			 double duration = controller.getCircuitManagement().getCircuitByIndex(circuitIndex-1).getCircuitDuration();
			 int durationHour = (int) duration / (int) 3600;
			 int durationMinutes = ((int)duration % 3600) / (int) 60;
			 String string = "Tournee "+ 
						(circuitIndex) + ": Duree "+ durationHour + "h" + durationMinutes + "min" + (int)(duration%60) +"s";
			 cellRenderer.setRepositoryCircuit(string);
		} else {
			 cellRenderer.setRepositoryCircuit("");
	
		 }
	}  
		
	public void emptySelectedNode() {
		if(selectedNode!=null){
			graphicView.unPaintNode( selectedNode);
		}
		
		selectedNode = null;
	}
	
	public void emptySelectedCircuit() {
		if(selectedCircuit!= -1){
			graphicView.unPaintCircuit( selectedCircuit);
		}
		
		selectedCircuit = -1;
	}
}
