package main.java.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.java.utils.PopUpType;

/**
 * The Class PopUp.
 */
public class PopUp extends JFrame {

	/** The Constant VALIDATE_ADD. */
	protected static final String VALIDATE_ADD = "Voulez-vous vraiment ajouter ce point de livraison ?";
	
	/** The Constant VALIDATE_ADD_DURATION. */
	protected static final String VALIDATE_ADD_DURATION = "Veuillez entrer la duree de la livraison.";
	
	/** The Constant VALIDATE_MOVE. */
	protected static final String VALIDATE_MOVE = "Voulez-vous vraiment deplacer cette livraison ?";
	
	/** The Constant VALIDATE_DELETE. */
	protected static final String VALIDATE_DELETE = "Voulez-vous vraiment supprimer ce point de livraison ?";
	
	/** The Constant VALIDATE_CONTINUE. */
	protected static final String VALIDATE_CONTINUE = "Voulez-vous continuer la recherche de chemins ?";
	
	/** The pop up. */
	protected static JOptionPane popUp;
	
	/**
	 * Instantiates a new pop up.
	 */
	public PopUp() {
		popUp = new JOptionPane();
	}
	
	/**
	 * Display pop up.
	 *
	 * @param message the message displayed in the pop up
	 * @param window the window which call the pop up
	 * @return the value got by clicking on the pop up's buttons.
	 */
	public static int displayPopUp (PopUpType message, Window window) {
		int userChoice = 0;
		switch (message){
			// Confirmation pop up for when the user want to add of a delivery
			case ADD: 
				Object[] validateOptions = {"Valider", "Annuler"};
				userChoice = JOptionPane.showOptionDialog(window,
													VALIDATE_ADD,
													"Valider ajout de la livraison",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,
													validateOptions,
													validateOptions[0]
													); 
				window.manageAddPopUpValue(userChoice);
			break;
			
			// Confirmation pop up for when the user want to delete a delivery
			case DELETE: 
				Object[] deleteOptions = {"Valider", "Annuler"};
				userChoice = JOptionPane.showOptionDialog(window,
												  	VALIDATE_DELETE,
												  	"Valider la suppression de la livraison",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,
													deleteOptions,
													deleteOptions[0]
													);
				window.manageDeletePopUpValue(userChoice);
				break;
			
			// Pop up to take the duration in second of a new delivery a user wants to add
			case DURATION: 
				JTextField durationTextField = new JTextField(10);
		        String durationMessage = "Duree de la livraison en secondes";
		        
		        Object[] popUpContent = {durationMessage, durationTextField};
		        Object[] durationOptions = {"Valider", "Annuler"};

		        popUp = new JOptionPane(popUpContent,
						                JOptionPane.QUESTION_MESSAGE,
						                JOptionPane.YES_NO_OPTION,
						                null,
						                durationOptions
						                );
		        
		        JDialog durationDialog = new JDialog(window, "Duree de la livraison", true);
		        
		        durationDialog.setSize(400, 150);
		        durationDialog.setLocationRelativeTo(null);
		        durationDialog.setContentPane(popUp);
		        durationDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		        
		        // Detect a change in the value of the event
		        // Allow to not close the pop up directly after a click on its buttons to allow us to verify the entry of the user
		        popUp.addPropertyChangeListener(
	        	    new PropertyChangeListener() {
	        	        public void propertyChange(PropertyChangeEvent e) {
	        	            String prop = e.getPropertyName();
	        	            if (durationDialog.isVisible() && (e.getSource() == popUp)&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {
	        	            	Object value = popUp.getValue();
	        	            	if (durationOptions[0].equals(value)) {
	        	                    try{
	        	                    	String duration = durationTextField.getText();
	        	                        int durationValue = Integer.parseInt(duration);
	        	                        if (durationValue < 0) {
	        	                        	errorPopUp(window, true, 0);
	        	                        	popUp.setValue(42);
	        	            
	        	                        } else {
	        	                        	window.manageDurationPopUpValue(durationValue);
	        	                        	durationDialog.dispose();
	        	                        }
	        	                    } catch(Exception parseException) {
	        	                    	errorPopUp(window, true, 0);
	        	                    	popUp.setValue(42);
	        	                    	popUp.requestFocusInWindow();
	        	                    }
        	                    } else if (durationOptions[1].equals(value)) {
	        	                	popUp.setValue(42);
	        	                	durationDialog.dispose();
	        	            		window.manageDurationPopUpValue(-1);
	        	            	}
	        	            }
	        	        }
	        	    });
		        
		        durationDialog.pack();
		        durationDialog.setVisible(true);
				break;
				
			// Confirmation pop up for when the user want to move a delivery
			case MOVE: 
				Object[] moveOptions = {"Valider", "Anuler"};
				userChoice = JOptionPane.showOptionDialog(window,
													VALIDATE_MOVE,
													"Valider le déplacement de la livraison",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,
													moveOptions,
													moveOptions[0]
													); 
				window.manageMovePopUpValue(userChoice);
				break;
		}
		return userChoice;
	}
	
	/**
	 * Error pop up.
	 *
	 * @param window the window
	 * @param zero to precise if the value can be 0 or not
	 * @param numberOfDeliveries the number of deliveries
	 */
	public static void errorPopUp(Window window, boolean zero, int numberOfDeliveries) {
		String errorPopUpText;
		if (zero)
			errorPopUpText = "Veuillez entrer un nombre entier superieur ou egal a 0.";
		else
			errorPopUpText = "Veuillez entrer un nombre entier superieur a 0 et inferieur a " + numberOfDeliveries + ".";
		JOptionPane.showMessageDialog(window,
				errorPopUpText,
                "Entree invalide.",
                JOptionPane.ERROR_MESSAGE);
	}
}
