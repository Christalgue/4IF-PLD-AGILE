package main.java.view;

import java.awt.Color;
import java.awt.TextField;
import java.util.HashMap;

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

/**
 * The Class Window.
 */
public class Window extends JFrame{
	
	/** The controller. */
	private Controller controller;
	
	//////////////////////////////LISTENERS/////////////////////////////

	/** The buttons listener. */
	protected ButtonsListener buttonsListener;
	
	/** The mouse listener. */
	protected MouseListener mouseListener;	
	
	/** The key listener. */
	protected KeyListener keyListener;
	
	//////////////////////////////GRAPHIC COMPOSANTS/////////////////////////////

	/** The graphic view, in which map, deliveries and circuits are painted. */
	private GraphicView graphicView;
	
	/** The textual view in which deliveries and circuits list are displayed. */
	private TextualView textualView;
	
	/** The textual view tree which display deliveries and circuits list. */
	protected JTree textualViewTree;
	
	/** The textual view tree's root node. */
	protected DefaultMutableTreeNode treeRoot;
	
	/** The textual view tree's custom cell renderer. */
	public MyTreeCellRenderer cellRenderer; 
	
	/** The message field to display errors, warnings and indications messages. */
	protected JLabel messageField;
	
	/** The validation and error pop up. */
	protected PopUp popUp;
	
	/** The file chooser for deliveries list and map to load. */
	private JFileChooser chooser;
	
	/** The filed to enter number of delivery men. */
	protected TextField numberOfDeliveryMenField;

	//////////////////////////////BUTTONS/////////////////////////////
	
	/** The Constant LOAD_MAP. */
	protected static final String LOAD_MAP = "Charger un plan";
	
	/** The Constant LOAD_DELIVERY_OFFER. */
	protected static final String LOAD_DELIVERY_OFFER = "Charger une demande de livraison";
	
	/** The Constant CALCULATE_CIRCUITS. */
	protected static final String CALCULATE_CIRCUITS = "Calculer les tournees";
	
	/** The Constant ADD_DELIVERY. */
	protected static final String ADD_DELIVERY = "Ajouter une livraison";
	
	/** The Constant DELETE_DELIVERY. */
	protected static final String DELETE_DELIVERY = "Supprimer la livraison";
	
	/** The Constant MOVE_DELIVERY. */
	protected static final String MOVE_DELIVERY = "Deplacer la livraison";
	
	/** The Constant CONTINUE_CALCULATION. */
	protected static final String CONTINUE_CALCULATION = "Continuer le calcul des tournees";
	
	/** The Constant UNDO. */
	protected static final String UNDO = "Retour";
	
	/** The Constant REDO. */
	protected static final String REDO = "Suivant";
	
	/** The Constant CANCEL. */
	protected static final String CANCEL = "Annuler";
	
	/** The Constant RESET_SCALE. */
	protected static final String RESET_SCALE = "Retablir l'echelle";
	
	/** The Constant GENERATE_ROADMAP. */
	protected static final String GENERATE_ROADMAP = "Generer la feuille de route";
	
	/** The Constant UP. */
	protected static final String UP = "U";
	
	/** The Constant DOWN. */
	protected static final String DOWN = "D";
	
	/** The Constant RIGHT. */
	protected static final String RIGHT = "R";
	
	/** The Constant LEFT. */
	protected static final String LEFT = "L";
	
	/** The Constant ZOOM. */
	protected static final String ZOOM = "Zoomer";
	
	/** The Constant UNZOOM. */
	protected static final String UNZOOM = "Dezoomer";
	
	
	/** The load delivery list button. */
	protected JButton loadDeliveryList;
	
	/** The calculate circuit button. */
	protected JButton calculateCircuitButton;
	
	/** The continue circuit's calculation button. */
	protected JButton continueCalculateCircuitButton;
	
	/** The add delivery button. */
	protected JButton addDeliveryButton;
	
	/** The delete delivery button. */
	protected JButton deleteDeliveryButton;
	
	/** The move delivery button. */
	protected JButton moveDeliveryButton;
	
	/** The undo button. */
	protected JButton undoButton;	
	
	/** The redo button. */
	protected JButton redoButton;
	
	/** The up button. */
	protected JButton upButton;
	
	/** The down button. */
	protected JButton downButton;
	
	/** The right button. */
	protected JButton rightButton;
	
	/** The left button. */
	protected JButton leftButton;
	
	/** The zoom button. */
	protected JButton zoomButton;
	
	/** The unzoom button. */
	protected JButton unZoomButton;
	
	/** The cancel add or move button. */
	protected JButton cancelAddButton;
	
	/** The reset scale button. */
	protected JButton resetScaleButton;
	
	/** The generate roadmap button. */
	protected JButton generateRoadmapButton;

	//////////////////////////////COMPOSANTS SIZE/////////////////////////////

	/** The window width. */
	protected final int windowWidth = 1500;
	
	/** The window height. */
	protected final int windowHeight = 720;
	
	/** The button panel height. */
	protected final int buttonPanelHeight =50;
	
	/** The graphic view width. */
	protected final int graphicWidth = 1030;
	
	/** The message field height. */
	protected final int messageFieldHeight = 40;
	
	/** The buttons height in textual view. */
	protected final int buttonHeight = 40;
	
	/** The vertical gap between two buttons in textualView. */
	protected final int buttonSpace = 10; 	
	
	/** The width of a map or circuit bow in graphic view. */
	protected final int pathWidth = 2;

	//////////////////////////////SELECTION AND HOVERING/////////////////////////////
	
	/** The last selected node. */
	protected Delivery selectedNode;
	
	/** The last hovered node. */
	protected Delivery hoverNode;
	
	/** The last selected circuit, "-1" means none. */
	protected int selectedCircuit = -1;
	
	/** The last hovered circuit, "-1" means none. */
	protected int hoverCircuit = -1;
	
	//////////////////////////////COLORS/////////////////////////////

	/** The selected items color. */
	protected Color selectedColor = new Color(0,150,0);
	
	/** The hovered items color. */
	protected Color hoverColor = Color.BLUE;
	
	/** The deliveries color. */
	protected Color deliveryColor = Color.RED;
	
	/** The repository color. */
	protected Color repositoryColor = Color.DARK_GRAY;
	
	/** The HashMap containing each circuit's color,
	 *  with circuit's index in CircuitManagement's circuitsList as key. */
	protected HashMap <Integer, Color> colors = new HashMap < Integer,Color>();

	
	/**
	 * Default constructor.
	 */
	public Window () {
		
	}
	
	/**
	 * Create the window.
	 *
	 * @param controller the controller
	 */
	public Window (Controller controller){
		
		this.controller = controller;
		
		buttonsListener = new ButtonsListener(controller);
		keyListener = new KeyListener(controller);
		setFocusable(true);
		addKeyListener(keyListener);
		CircuitManagement circuitManagement = controller.getCircuitManagement();
		
		//////////////////////////////SET UP THE MAIN WINDOW//////////////////////////////
		
		setControllerWindow();
		
		//////////////////////////////CREATE THE HEADER PANEL/////////////////////////////
		
		JPanel buttonPanel = new JPanel();
		fillButtonPanel(buttonPanel);
		
		//////////////////////////////CREATE THE GRAPHIC VIEW//////////////////////////////
		
		graphicView = new GraphicView (circuitManagement, windowHeight-buttonPanelHeight-messageFieldHeight, graphicWidth, pathWidth,this);
		setGraphicView();
		mouseListener = new MouseListener(controller, graphicView);
		
		//////////////////////////////CREATE THE TEXTUAL VIEW/////////////////////////////
		
		treeRoot = createTreeRoot();
		textualViewTree = new JTree (treeRoot);
		cellRenderer = new MyTreeCellRenderer(this, circuitManagement);
		textualView = new TextualView (circuitManagement, windowHeight-buttonPanelHeight, windowWidth-graphicWidth, this);
		setTextualView();
		addTreeListener();
		
		//////////////////////////////CREATE THE MESSAGE FIELD/////////////////////////////
		
		messageField = new JLabel();
		setMessageField();
		
		//////////////////////////////ADD PANELS TO THE WINDOW//////////////////////////////
		
		getContentPane().add(buttonPanel);
		getContentPane().add(textualView);
		getContentPane().add(messageField);
		getContentPane().add(graphicView);
		
		////////////////////////////// MAKE THE FRAME VISBLE AND PAINTABLE ////////////////////////////////
		
		setVisible(true);
		graphicView.setGraphics();
		
	}

	//////////////////////////////PUT COMPOSANTS IN PLACE/////////////////////////////

	/**
	 * Sets the parameters of Window.
	 */
	public void setControllerWindow() {
		
		setLayout(null);
		setSize(windowWidth, windowHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Deliver'IF");
		setResizable(false);
		setLocationRelativeTo(null);
	
	}
	
	/**
	 * Fill button panel.
	 *
	 * @param buttonPanel the button panel to fill
	 */
	public void fillButtonPanel(JPanel buttonPanel) {
		
		buttonPanel.setSize(windowWidth, buttonPanelHeight);
		buttonPanel.setBackground(Color.WHITE);
		
		// Create and add button to undo actions to panel
		undoButton = new JButton();
		undoButton.setActionCommand(UNDO);
		addIconToButton(undoButton, "resources/img/backward.png");
		buttonPanel.add(undoButton);
		undoButton.addActionListener(buttonsListener);
		undoButton.setEnabled(false);
		
		// Create and add button to redo actions to panel
		redoButton = new JButton();
		redoButton.setActionCommand(REDO);
		addIconToButton(redoButton, "resources/img/forward.png");
		buttonPanel.add(redoButton);
		redoButton.addActionListener(buttonsListener);	
		redoButton.setEnabled(false);
		
		// Create and add button to load map to panel
		JButton loadMapButton = new JButton(LOAD_MAP);
		loadMapButton.addActionListener(buttonsListener);
		buttonPanel.add(loadMapButton);
		
		// Create and add button to load delivery list to panel
		loadDeliveryList = new JButton(LOAD_DELIVERY_OFFER);
		loadDeliveryList.addActionListener(buttonsListener);
		loadDeliveryList.setEnabled(false);
		buttonPanel.add(loadDeliveryList);
		
		// Create and add label about delivery men to panel
		JTextArea labelNumberOfDeliveryMen = new JTextArea();
		labelNumberOfDeliveryMen.setText("Nombre de livreurs :");
		labelNumberOfDeliveryMen.setEditable(false);
		buttonPanel.add(labelNumberOfDeliveryMen);
		
		// Create and add field to enter number of deliverymen to panel
		// Default value is 1
		numberOfDeliveryMenField = new TextField();
		numberOfDeliveryMenField.setText("1");
		numberOfDeliveryMenField.setEditable(true);
		buttonPanel.add(numberOfDeliveryMenField);
		
		// Create and add button to calculate circuits to panel
		calculateCircuitButton = new JButton(CALCULATE_CIRCUITS);
		calculateCircuitButton.addActionListener(buttonsListener);
		calculateCircuitButton.setEnabled(false);
		buttonPanel.add(calculateCircuitButton);
		
		// Create and add button to continue calculation to panel
		continueCalculateCircuitButton = new JButton(CONTINUE_CALCULATION);
		continueCalculateCircuitButton.addActionListener(buttonsListener);
		continueCalculateCircuitButton.setEnabled(false);
		buttonPanel.add(continueCalculateCircuitButton);
		
		// Create and add button to cancel current action to panel
		cancelAddButton = new JButton(CANCEL);
		cancelAddButton.addActionListener(buttonsListener);
		cancelAddButton.setEnabled(false);
		buttonPanel.add(cancelAddButton);
		
		// Create and add button to generate road map to panel
		generateRoadmapButton = new JButton(GENERATE_ROADMAP);
		generateRoadmapButton.addActionListener(buttonsListener);
		generateRoadmapButton.setEnabled(false);
		buttonPanel.add(generateRoadmapButton);
	}
	
	/**
	 * Adds an icon to a button.
	 *
	 * @param button the button which will bear the icon
	 * @param path the path of the icon
	 */
	private void addIconToButton (JButton button, String path) {
		// Catch error if the path for the icon is not correct or does not lead to an icon
		try {
		    ImageIcon img = new ImageIcon(path);
		    button.setIcon(img);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the graphic view.
	 *
	 */
	private void setGraphicView() {
		
		graphicView.setLocation(0, buttonPanelHeight+messageFieldHeight);
		graphicView.setLayout(null);
		graphicView.setBackground(Color.LIGHT_GRAY);
		graphicView.setSize(graphicWidth, windowHeight-buttonPanelHeight-messageFieldHeight);
		
	}
	
	/**
	 * Creates the tree root.
	 *
	 * @return the created root
	 */
	private DefaultMutableTreeNode createTreeRoot() {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		return root;
	}
	
	/**
	 * Sets the textual view.
	 *
	 */
	private void setTextualView() {
		
		textualView.setLocation(graphicWidth, buttonPanelHeight);
		textualView.setBackground(Color.WHITE);
		textualView.setSize(windowWidth-graphicWidth, windowHeight-buttonPanelHeight);
		textualView.setLayout(null);
		
		// Create and add button to zoom on graphicView to textualView
		zoomButton = new JButton();
		zoomButton.setActionCommand(ZOOM);
		addIconToButton(zoomButton, "resources/img/zoom-in.png");
		zoomButton.addActionListener(buttonsListener);
		zoomButton.setLocation(10, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		zoomButton.setSize(40, buttonHeight);
		zoomButton.setEnabled(false);
		textualView.add(zoomButton);
		
		// Create and add button to reset graphicView's offsets and scale to textualView
		resetScaleButton = new JButton();
		resetScaleButton.setActionCommand(RESET_SCALE);
		addIconToButton(resetScaleButton, "resources/img/zoom-1:1.png");
		resetScaleButton.addActionListener(buttonsListener);
		resetScaleButton.setLocation(55, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		resetScaleButton.setSize(40, buttonHeight);
		resetScaleButton.setEnabled(false);
		textualView.add(resetScaleButton);
		
		// Create and add button to unzoom on graphicView to textualView
		unZoomButton = new JButton();
		unZoomButton.setActionCommand(UNZOOM);
		addIconToButton(unZoomButton, "resources/img/zoom-out.png");
		unZoomButton.addActionListener(buttonsListener);
		unZoomButton.setLocation(100, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		unZoomButton.setSize(40, buttonHeight);
		unZoomButton.setEnabled(false);
		textualView.add(unZoomButton);
		
		// Create and add button to "move up" in graphic view to textualView
		upButton = new JButton();
		upButton.setActionCommand(UP);
		addIconToButton(upButton, "resources/img/chevron-up.png");
		upButton.addActionListener(buttonsListener);
		upButton.setLocation(60, windowHeight-buttonPanelHeight-(buttonHeight*2+buttonSpace+50));
		upButton.setSize(30, 30);
		upButton.setEnabled(false);
		textualView.add(upButton);
		
		// Create and add button to "move down" in graphic view to textualView
		downButton = new JButton();
		downButton.setActionCommand(DOWN);
		addIconToButton(downButton, "resources/img/chevron-down.png");
		downButton.addActionListener(buttonsListener);
		downButton.setLocation(60, windowHeight-buttonPanelHeight-(buttonHeight+40));
		downButton.setSize(30, 30);
		downButton.setEnabled(false);
		textualView.add(downButton);
		
		// Create and add button to "move right" in graphic view to textualView
		rightButton = new JButton();
		rightButton.setActionCommand(RIGHT);
		addIconToButton(rightButton, "resources/img/chevron-right.png");
		rightButton.addActionListener(buttonsListener);
		rightButton.setLocation(90, windowHeight-buttonPanelHeight-(buttonHeight+70));
		rightButton.setSize(30, 30);
		rightButton.setEnabled(false);
		textualView.add(rightButton);
		
		// Create and add button to "move left" in graphic view to textualView
		leftButton = new JButton();
		leftButton.setActionCommand(LEFT);
		addIconToButton(leftButton, "resources/img/chevron-left.png");
		leftButton.addActionListener(buttonsListener);
		leftButton.setLocation(30, windowHeight-buttonPanelHeight-(buttonHeight+70));
		leftButton.setSize(30, 30);
		leftButton.setEnabled(false);
		textualView.add(leftButton);
		
		// Create and add button to add a delivery to deliveries' list to textualView
		addDeliveryButton = new JButton(ADD_DELIVERY);
		addDeliveryButton.addActionListener(buttonsListener);
		addDeliveryButton.setEnabled(false);
		addDeliveryButton.setLocation(150, windowHeight-buttonPanelHeight-(buttonHeight*3+2*buttonSpace+50));
		addDeliveryButton.setSize(windowWidth-graphicWidth-160, buttonHeight);
		textualView.add(addDeliveryButton);
	
		// Create and add button to delete a delivery from deliveries' list to textualView
		deleteDeliveryButton = new JButton(DELETE_DELIVERY);
		deleteDeliveryButton.addActionListener(buttonsListener);
		deleteDeliveryButton.setEnabled(false);
		deleteDeliveryButton.setLocation(150, windowHeight-buttonPanelHeight-(buttonHeight*2+buttonSpace+50));
		deleteDeliveryButton.setSize(windowWidth-graphicWidth-160, buttonHeight);
		textualView.add(deleteDeliveryButton);	

		// Create and add button to move a delivery from a circuit to another to textualView
		moveDeliveryButton = new JButton(MOVE_DELIVERY);
		moveDeliveryButton.addActionListener(buttonsListener);
		moveDeliveryButton.setEnabled(false);
		moveDeliveryButton.setLocation(150, windowHeight-buttonPanelHeight-(buttonHeight+50));
		moveDeliveryButton.setSize(windowWidth-graphicWidth-160, buttonHeight);
		textualView.add(moveDeliveryButton);	
			
	}
	
	
	/**
 	 * Adds the tree listener to the textual view tree.
 	 */
 	public void addTreeListener () {
		 
		 textualViewTree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        
		    	// If the tree isn't empty
		    	if ( treeRoot.getChildCount() != 0) {
		    		
			    	DefaultMutableTreeNode deliveryPoint = (DefaultMutableTreeNode) textualViewTree.getLastSelectedPathComponent();
			    	
		        	// If a tree node is selected
			    	if ( deliveryPoint != null ) {
				        String deliveryInfo = (String) deliveryPoint.getUserObject();
				        
				        // If it isn't the repository
				        if (!deliveryInfo.startsWith("Entrepot")) {
				        	
				        	// There is no repository's parent circuit to indicate
				        	setRepositorySelected(-1);
				        	
				        	// If it is a delivery
				        	if (!deliveryInfo.startsWith("Tournee")) {
				        		
				        		// Its index is got
				        		String secondPart = deliveryInfo.substring(10);
				        		String[] split = secondPart.split(":");
				        		String deliveryNumber = split[0];
				        		int deliveryIndex = Integer.parseInt(deliveryNumber);
				        		
				        		// Then the delivery
				        		Delivery delivery = controller.getCircuitManagement().getDeliveryByIndex(deliveryIndex); 
				        		
				        		// The node is selected in graphicView and the controller is worn 
				        		nodeSelected(delivery);
				        		controller.treeDeliverySelected(delivery, -1);
				        	
				        	// If it is a circuit
				        	} else {
				        		
				        		// Its index is got
				        		String secondPart = deliveryInfo.substring(8);
				        		String[] split = secondPart.split(":");
				        		String circuitNumber = split[0];
				        		int circuitIndex = Integer.parseInt(circuitNumber);	
				        		
				        		// It is selected in both views
				        		textualCircuitSelected(circuitIndex-1);
				        	}
				        	
				        // If it is the repository
				        } else {
				        	TreeNode parentCircuit = deliveryPoint.getParent();
				        	
				        	int circuitIndex = -1;
				        	
				        	// It is selected in graphicView and the controller is worn
				        	Delivery delivery = controller.getCircuitManagement().getDeliveryList().get(0);
			        		nodeSelected(delivery);
				        	//controller.treeDeliverySelected(delivery);
				        	
				        	// If its parent is a circuit
				        	if ( parentCircuit != treeRoot) {
				        		
				        		// The circuit's index is got
				        		String circuitInfo = (String) ((DefaultMutableTreeNode) parentCircuit).getUserObject();
				        		String secondPart = circuitInfo.substring(8);
				        		String[] split = secondPart.split(":");
				        		String circuitNumber = split[0];
				        		circuitIndex = Integer.parseInt(circuitNumber);
				        		
				        		// It is selected in both views
				        		textualCircuitSelected(circuitIndex-1);
				        		// It is set as repository's parent circuit
				        		setRepositorySelected (circuitIndex);
				        	
				        	// If there is no parent circuit
				        	} else {
				        		setRepositorySelected(-1);
				        	}
				        	
				        	controller.treeDeliverySelected(delivery, circuitIndex-1);

				        }
				        
			    	}
		    	}
		    }
   
		 });
	}
	
	/**
	 * Sets the message field.
	 *
	 * @param messageField the new message field
	 */
	private void setMessageField() {
		messageField.setSize( graphicWidth, messageFieldHeight);
		messageField.setLocation(0, buttonPanelHeight);
		messageField.setOpaque(true);
		messageField.setForeground(Color.WHITE);
		messageField.setBackground(Color.DARK_GRAY);
		messageField.setHorizontalTextPosition(JLabel.CENTER);
		messageField.setText("Veuillez selectionner un plan a charger");
	}
	
	/**
	 * Sets the mouse listener.
	 */
	public void setMouseListener() {
		this.graphicView.addMouseListener(mouseListener);
		this.graphicView.addMouseMotionListener(mouseListener);
	}
	

	/**
	 * Gets the file path.
	 *
	 * @return the file path of the file to open selected by the user in the file chooser
	 */
	//////////////////////////////GET DATAS FROM WINDOW/////////////////////////////
	protected String getFilePath () {
		
		// Create and set parameters of the file chooser.
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		int returnValue = chooser.showOpenDialog(controller.getWindow());
		String filePath = "";
		
		// Test if the user clicked on the button "Open" of the file chooser
		if(returnValue == JFileChooser.APPROVE_OPTION){
			filePath = chooser.getSelectedFile().getAbsolutePath();
		}
		return filePath;
		
	}
	
	/**
	 * Gets the folder path.
	 *
	 * @return the folder path selected by the user in the file chooser
	 */
	protected String getFolderPath() {
		
		// Create and set parameters of the file chooser.
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = chooser.showOpenDialog(controller.getWindow());
		String folderPath = "";
		
		// Test if the user clicked on the button "Open" of the file chooser.
		if(returnValue == JFileChooser.APPROVE_OPTION){
			folderPath = chooser.getSelectedFile().getAbsolutePath();
		}
		return folderPath;
		
	}

	/**
	 * Gets the delivery men number entered by the user in the text field.
	 *
	 * @return the delivery men number
	 */
	public boolean getDeliveryMenNumber() {
		
		// Variable to know if the value entered by the user is correct or not.
		boolean correctValue = false;
		int numberOfDeliveries = controller.getCircuitManagement().getDeliveryList().size();
		
		try{
        	String numberOfDeliveryMen = numberOfDeliveryMenField.getText();
            int numberOfDeliveryMenValue = Integer.parseInt(numberOfDeliveryMen);
            // Test if the number entered is lesser or equal than 0 and greater than the number of delivery.
            // The -1 is here because the repository is stocked in the delivery list because it is the starting point of all circuits 
            // but it doesn't count as a delivery point.
            if (numberOfDeliveryMenValue <= 0 || numberOfDeliveryMenValue > numberOfDeliveries -1) {
            	// Display the error pop up
            	PopUp.errorPopUp(this, false, numberOfDeliveries);
        	// If the entry is correct
            } else {
            	buttonsListener.setDeliveryMenNumber(numberOfDeliveryMenValue);
            	correctValue = true;
            }
        // Catch errors when converting the string of the text field into an integer.
        } catch(Exception parseException) {
        	// Display the error pop up
        	PopUp.errorPopUp(this, false, numberOfDeliveries);
        }
		return correctValue;
		
	}
	
	//////////////////////////////CHANGE DISPLAYED TEXT/////////////////////////////
	
	/**
	 * Display the message in the message field on a gray background
	 *
	 * @param string a message to display in the message field
	 */
	public void setMessage(String string) {
		messageField.setBackground(Color.DARK_GRAY);
		messageField.setText(string);
	}
	
	/**
	 * Display the error message in the message field on a red background
	 *
	 * @param string an error message to display in the message field
	 */
	public void setErrorMessage(String string) {
		messageField.setBackground(Color.RED);
		messageField.setText(string);
	}
	
	/**
	 * Display the warning message in the message field on an orange background
	 *
	 * @param string a warning message to display in the message field
	 */
	public void setWarningMessage(String string) {
		messageField.setBackground(Color.ORANGE);
		messageField.setText(string);
	}
	
	//////////////////////////////DRAW COMPOSANTS/////////////////////////////
	
	/**
	 * Calculate scale.
	 */
	public void calculateScale() {
		graphicView.calculateScale(controller.getCircuitManagement());
	}
	
	/**
	 * Draw the map in the graphic view.
	 */
	public void drawMap() {
		textualView.emptyTree();
		graphicView.paintMap();
	}
	
	/**
	 * Draw the deliveries in the graphic view and fill the delivery tree.
	 */
	public void drawDeliveries() {
		drawMap();
		graphicView.paintDeliveries();
		textualView.fillDeliveryTree();
	}
	
	/**
	 * Draw the circuits in the graphic view and fill the circuit tree.
	 */
	public void drawCircuits() {
		drawDeliveries();
		graphicView.paintCircuits();
		textualView.emptyTree();
		textualView.fillCircuitTree();
	}
	
	//////////////////////////////BUTTON ACTIVATION/////////////////////////////
	
	/**
	 * Enable button load deliveries list.
	 */
	public void enableButtonLoadDeliveriesList() {
		loadDeliveryList.setEnabled(true);
	}
	
	/**
	 * Enable button calculate circuit.
	 */
	public void enableButtonCalculateCircuit() {
		calculateCircuitButton.setEnabled(true);
	}	
	
	/**
	 * Enable button continue calculation.
	 */
	public void enableButtonContinueCalculation() {
		continueCalculateCircuitButton.setEnabled(true);
	}
	
	/**
	 * Enable button undo.
	 */
	public void enableButtonUndo() {
		undoButton.setEnabled(true);
	}
	
	/**
	 * Enable button redo.
	 */
	public void enableButtonRedo() {
		redoButton.setEnabled(true);
	}
	
	/**
	 * Enable button add delivery.
	 */
	public void enableButtonAddDelivery() {
		addDeliveryButton.setEnabled(true);
	}
	
	/**
	 * Enable button delete delivery.
	 */
	public void enableButtonDeleteDelivery() {
		deleteDeliveryButton.setEnabled(true);
	}
	
	/**
	 * Enable button move delivery.
	 */
	public void enableButtonMoveDelivery() {
		moveDeliveryButton.setEnabled(true);
	}
	
	/**
	 * Enable zoom button.
	 */
	public void enableZoomButton() {
		zoomButton.setEnabled(true);
		keyListener.zoomEnable(true);
	}

	/**
	 * Enable unzoom button.
	 */
	public void enableDeZoomButton() {
		unZoomButton.setEnabled(true);
		keyListener.unZoomEnable(true);
	}

	/**
	 * Enable cancel button.
	 */
	public void enableCancelButton() {
		cancelAddButton.setEnabled(true);
	}
	
	/**
	 * Enable reset scale button.
	 */
	public void enableResetScaleButton() {
		resetScaleButton.setEnabled(true);
	}
	
	/**
	 * Enable generate roadmap button.
	 */
	public void enableGenerateRoadmapButton () {
		generateRoadmapButton.setEnabled(true);
	}
	
	/**
	 * Enable arrows buttons.
	 */
	public void enableArrows() {
		upButton.setEnabled(true);
		downButton.setEnabled(true);
		rightButton.setEnabled(true);
		leftButton.setEnabled(true);
		keyListener.shiftEnable(true);
	}
	
	//////////////////////////////BUTTON DESACTIVATION/////////////////////////////

	/**
	 * Disable calculate circuit button .
	 */
	public void disableButtonCalculateCircuit() {
		calculateCircuitButton.setEnabled(false);
	}
	
	/**
	 * Disable continue calculation  button.
	 */
	public void disableButtonContinueCalculation() {
		continueCalculateCircuitButton.setEnabled(false);
	}
	
	/**
	 * Disable add delivery  button.
	 */
	public void disableButtonAddDelivery() {
		addDeliveryButton.setEnabled(false);
	}
	
	/**
	 * Disable delete delivery  button.
	 */
	public void disableButtonDeleteDelivery() {
		deleteDeliveryButton.setEnabled(false);
	}
	
	/**
	 * Disable move delivery button.
	 */
	public void disableButtonMoveDelivery() {
		moveDeliveryButton.setEnabled(false);
	}
	
	/**
	 * Disable undo button.
	 */
	public void disableButtonUndo() {
		undoButton.setEnabled(false);
	}
	
	/**
	 * Disable redo button.
	 */
	public void disableButtonRedo() {
		redoButton.setEnabled(false);
	}

	/**
	 * Disable cancel button.
	 */
	public void disableCancelButton() {
		cancelAddButton.setEnabled(false);
	}
	
	/**
	 * Disable reset scale button.
	 */
	public void disableResetScaleButton() {
		resetScaleButton.setEnabled(false);
	}
	
	/**
	 * Disable generate roadmap button.
	 */
	public void disableGenerateRoadmapButton () {
		generateRoadmapButton.setEnabled(false);
	}
	
	//////////////////////////////POP UP MANAGEMENT/////////////////////////////
	
	/**
	 * Gets the pop up value.
	 *
	 * @param message the message to display in the pop up
	 * @param window the window
	 * @return the pop up value corresponding to the button clicked by the user.
	 */
	public int getPopUpValue(PopUpType message, Window window) {
		return popUp.displayPopUp(message, window);
	}
	
	/**
	 * Manage add pop up value.
	 *
	 * @param userChoice the user choice corresponding to the button clicked by the user
	 */
	public void manageAddPopUpValue(int userChoice) {
		// Test if the user clicked on validate in the pop up.
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
	
	/**
	 * Manage delete pop up value.
	 *
	 * @param userChoice the user choice
	 */
	public void manageDeletePopUpValue(int userChoice) {
		// Test if the user clicked on validate in the pop up.
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
	
	 /**
 	 * Manage duration pop up value.
 	 *
 	 * @param inputValue the input value
 	 */
 	public void manageDurationPopUpValue(int inputValue) {
 	// Test if the user clicked on validate in the pop up.
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
	
	/**
	 * Manage move pop up value.
	 *
	 * @param userChoice the user choice
	 */
	public void manageMovePopUpValue(int userChoice) {
		// Test if the user clicked on validate in the pop up.
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
	 
	 //////////////////////////////GRAPHIC DISPLAY/////////////////////////////
	 
	 /**
 	 * Zoom on the graphic view.
 	 */
 	public void zoom() {
		graphicView.zoom();
	 }
	 
	 /**
 	 * Unzoom on the graphic view.
 	 */
 	public void unZoom() {
		graphicView.unZoom();
	 }
	 
	 /**
 	 * Horizontal shift on the graphic view.
 	 *
 	 * @param right right the value of the horizontal shift to apply, right if positive, left if negative
 	 */
 	public void horizontalShift( int right) {
		 graphicView.horizontalShift(right);
	 }
	 
	 /**
 	 * Vertical shift on the view.
	 *
	 * @param down the value of the vertical shift to apply, down if positive, up if negative
 	 */
 	public void verticalShift(int down) {
		 graphicView.verticalShift(down);
	 }
	 
	 /**
 	 * Reset default values, displaying the whole map at the center of the view.
 	 */
 	public void resetScale() {
		 graphicView.resetDefaultValues();
	 }
	 
	 /**
 	 * Empty the colors HashMap so that new colors will be generated at the next circuits' displaying.
 	 */
 	public void emptyColors () {
		 colors = new HashMap < Integer,Color>();
	 }
	 
	 //////////////////////////////SELECTION/////////////////////////////
	 
	/**
 	 * Manage selected node's displaying
 	 *
 	 * @param delivery the selected node
 	 */
 	public void nodeSelected(Delivery delivery) {
		
		// If there is a node already selected
		if(selectedNode!=null){
			//It is paint back to its former color
			graphicView.unPaintNode( selectedNode);
		}
		
		// The current selected node is paint to selection color
		// and set as selected node
		graphicView.paintSelectedNode( delivery, true);
		selectedNode = delivery;
		
		// The hover node is emptied, since it is currently the selected node
		// and the selection is prior to hovering
		hoverNode = null;
		
		// The selected node wasn't selected via textualView
		setRepositorySelected(-1);
		// Set node in selectedColor in textualView, if it exists
		setSelectedTreeNode( delivery,true);
		
	}
		
	/**
	 * Manage hover node's displaying
	 *
	 * @param delivery the hovered node
	 */
	public void nodeHover(Delivery delivery) {
		
		// If the mouse exit a node
		if(delivery == null && hoverNode!=null) {
			// The last hoverNode is unpaint and set to null
			graphicView.unPaintNode( hoverNode);
			hoverNode = null;
		}
		// If the mouse hover a node that isn't selected 
		else if ( delivery != null && (selectedNode == null || delivery.getPosition().getId()!=selectedNode.getPosition().getId())){
			
			//If the mouse enter a node, it is painted
			if( hoverNode == null) {
				graphicView.paintSelectedNode( delivery, false);
			}
			//If the mouse pass from one node to another, the former hoverNode is unpainted and the new is painted
			else if(delivery.getPosition().getId()!=hoverNode.getPosition().getId()) {
				graphicView.unPaintNode( hoverNode);
				graphicView.paintSelectedNode( delivery, false);
			}
			
			// The hovered node is set as hoverNode
			hoverNode = delivery;
			
			//If circuits are displayed
			if ( controller.getCircuitManagement().getCircuitsList()!= null) {
				circuitHover(delivery);
			}		
			
		}
		
		// Set node in hoverColor in textualView, if it exists
		setSelectedTreeNode (delivery, false);
			
	}
		
	/**
	 * Manage selected circuit's displaying
	 *
	 * @param selectedDelivery the selected node
	 */
	public void circuitSelected(Delivery selectedDelivery) {

		// If the newer selected node isn't null and circuits are displayed
		if (selectedDelivery != null && controller.getCircuitManagement().getCircuitsList()!= null) {
			
			int circuitIndex;
			
			// If the newer selected node is a delivery, the circuit index is get from its delivery list
			if (selectedDelivery.getDuration()!= -1) {
				circuitIndex = controller.getCircuitManagement().getCircuitIndexByDelivery( selectedDelivery);
			// Else, the circuit index is get from its path
			} else {
				circuitIndex = controller.getCircuitManagement().getCircuitIndexByNode( selectedDelivery);
			}
			
			// If another circuit is already selected, it is unpainted
			if(selectedCircuit!= -1 && selectedCircuit != circuitIndex){
				graphicView.unPaintCircuit( selectedCircuit);
			}
			
			// If newer selected node belong to a circuit
			// it is painted an set as selectedCircuit
			// while hoverCircuit is set to -1 ( because selection is prior to hovering)
			if (circuitIndex != -1) {
				graphicView.paintSelectedCircuit(circuitIndex, true); 
				hoverCircuit = -1 ;
				selectedCircuit = circuitIndex;
			}
			
			// Set the circuit selectedColor in textual view
			setSelectedTreeNode(circuitIndex, true);
		}
		
	}

	/**
	 * Manage hovered circuit's displaying.
	 *
	 * @param delivery the hovered node
	 */
	public void circuitHover(Delivery delivery) {
					
		int toDrawCircuit = controller.getCircuitManagement().getCircuitIndexByNode(delivery);
		
		// If the hovered node belong to a circuit
		if( toDrawCircuit != -1 ) {
			
			// If the hovered circuit is not the selected one
			if( toDrawCircuit != selectedCircuit) {
					
				//If the mouse enter a circuit, the circuit is paint in hoverColor
				if( hoverCircuit == -1) {
					graphicView.paintSelectedCircuit( toDrawCircuit, false);
				}
				
				// If the mouse pass from one circuit to another, the first one is unpainted
				// while the second is painted
				else if( toDrawCircuit != hoverCircuit) {
					graphicView.unPaintCircuit( hoverCircuit);
					graphicView.paintSelectedCircuit( toDrawCircuit, false);
				}
				
				// The hovered circuit is set as hoverCircuit
				hoverCircuit = toDrawCircuit;
					
			} else if ( toDrawCircuit == selectedCircuit) {
			
			graphicView.paintSelectedCircuit( toDrawCircuit, true);
			hoverCircuit = -1;
			
			}
			
		// Else the former hoverCircuit is unpainted 
		} else {
			graphicView.unPaintCircuit( hoverCircuit);
			hoverCircuit =-1;
		}
		
		// Set circuit in hoverColor in textualView, if it exists
		setSelectedTreeNode (toDrawCircuit, false);
	}		
		
	/**
	 * Manage the displaying of a circuit selected from the textual view.
	 *
	 * @param circuitIndex the index of the selected circuit
	 */
	public void textualCircuitSelected(int circuitIndex) {
		
		// The former selected circuit is unpainted
		graphicView.unPaintCircuit(selectedCircuit);
		
		// Then the new one is painted and set as selectedCircuit 
		graphicView.paintSelectedCircuit(circuitIndex, true);
		selectedCircuit = circuitIndex;
		
		// It is selected in textualView as well
		setSelectedTreeNode (circuitIndex, true);
		if ( !cellRenderer.getSelectedDelivery().equals("Entrepot: Duree 0s")) {
			cellRenderer.setSelectedDelivery("", true);
			System.out.println("DUH");
		}
	}	
	
	 /**
 	 * Sets the selected node (delivery) of the textual view tree.
 	 *
 	 * @param delivery the selected node
 	 * @param selected Indicate if delivery is selected (true) or hovered (false)
 	 */
 	public void setSelectedTreeNode ( Delivery delivery, boolean selected ){
		
		 // If a delivery is selected or hovered, it is put in the right color in the textualViewTree  
		 if ( delivery != null && delivery.getDuration() != -1) {
			 
			 if ( delivery instanceof Repository ) {
				 cellRenderer.setSelectedDelivery("Entrepot: Duree 0s", selected);
			 } else {
				 int index = controller.getCircuitManagement().getDeliveryIndex(delivery);
				 String string = "Livraison "+ 
							index +": Duree "+(int)(delivery.getDuration()/60)+"min"+(int)(delivery.getDuration()%60)+"s";
				 cellRenderer.setSelectedDelivery(string, selected);
			 }
		 // Else the selected node in textualView is emptied
		 }else {
			 cellRenderer.setSelectedDelivery("", selected);
		 }
		 
		 // Update the textualViewTree
		 ((DefaultTreeModel) textualViewTree.getModel()).nodeChanged(treeRoot);

	 }
		
	 /**
	 * Sets the selected node(circuit) of the textual view tree.
	 *
	 * @param circuitIndex the selected circuit's index
	 * @param selected Indicate if delivery is selected (true) or hovered (false)
	 */
	private void setSelectedTreeNode(int circuitIndex, boolean selected) {
		
		// If a delivery is selected or hovered, it is put in the right color in the textualViewTree  
		if ( circuitIndex != -1 ) {
			 
			double duration = controller.getCircuitManagement().getCircuitByIndex(circuitIndex).getCircuitDuration();
			 int durationHour = (int) duration / (int) 3600;
			 int durationMinutes = ((int)duration % 3600) / (int) 60;
			 String string = "Tournee "+ 
						(circuitIndex+1) + ": Duree "+ durationHour + "h" + durationMinutes + "min" + (int)(duration%60) +"s";
			 cellRenderer.setSelectedCircuit(string, selected);
			 
		// Else the selected circuit in textualView is emptied
		} else {
			 cellRenderer.setSelectedCircuit("", selected);

		}
		
		// Update the textualViewTree
		((DefaultTreeModel) textualViewTree.getModel()).nodeChanged(treeRoot);

	}

	/**
	 * Sets the repository selected boolean of the cell renderer.
	 *
	 * @param circuitIndex the selected repository circuit
	 */
	private void setRepositorySelected(int circuitIndex) {
		
		// If the repository is selected via textualView and it belongs to a circuit,
		// this circuit's node is colored in selectedColor in textualView
		if (circuitIndex !=-1) {
			 cellRenderer.setRepositorySelected(true);
			 
		// Else the repositoryCircuit is emptied
		} else {
			 cellRenderer.setRepositorySelected(false);
		}
	}  
		
	/**
	 * Empty the window's selected node.
	 */
	public void emptySelectedNode() {
		
		// If a node is selected, it is unpainted an selectedNode is
		// set to  null
		if(selectedNode!=null){
			graphicView.unPaintNode( selectedNode);
			selectedNode = null;
		}
	}
	
	/**
	 * Empty the window's selected circuit.
	 */
	public void emptySelectedCircuit() {
		
		// If a circuit is selected, it is unpainted an selectedCircuit is
		// set to  null
		if(selectedCircuit!= -1){
			graphicView.unPaintCircuit( selectedCircuit);
			selectedCircuit = -1;
		}
	}
}
