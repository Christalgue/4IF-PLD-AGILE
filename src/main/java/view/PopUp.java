package main.java.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.java.utils.PopUpType;

public class PopUp extends JFrame {

	protected static final String VALIDATE_ADD = "Voulez-vous vraiment ajouter ce point de livraison ?";
	protected static final String VALIDATE_ADD_DURATION = "Veuillez entrer la duree de la livraison.";
	protected static final String VALIDATE_MOVE = "Voulez-vous vraiment deplacer cette livraison ?";
	protected static final String VALIDATE_DELETE = "Voulez-vous vraiment supprimer ce point de livraison ?";
	protected static final String VALIDATE_CONTINUE = "Voulez-vous continuer la recherche de chemins ?";
	
	protected static JOptionPane popUp;
	
	public PopUp() {
		popUp = new JOptionPane();
	}
	
	public static int displayPopUp (PopUpType message, Window window) {
		int userChoice = 0;
		switch (message){
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
	        	                        	errorPopUp(window, true);
	        	                        	popUp.setValue(42);
	        	            
	        	                        } else {
	        	                        	window.manageDurationPopUpValue(durationValue);
	        	                        	durationDialog.dispose();
	        	                        }
	        	                    } catch(Exception parseException) {
	        	                    	errorPopUp(window, true);
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
				
			case MOVE: 
				Object[] moveOptions = {"Valider", "Anuler"};
				userChoice = JOptionPane.showOptionDialog(window,
													VALIDATE_MOVE,
													"Valider la suppression de la livraison",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,
													moveOptions,
													moveOptions[0]
													); 
				window.manageMovePopUpValue(userChoice);
				break;
				
			case CONTINUE: 
				Object[] continueOptions = {"Garder", "Continuer"};
				userChoice = JOptionPane.showOptionDialog(window,
											VALIDATE_CONTINUE,
											"Meilleure tournee actuelle",
											JOptionPane.YES_NO_CANCEL_OPTION,
											JOptionPane.QUESTION_MESSAGE,
											null,
											continueOptions,
											continueOptions[0]
											);
				window.manageContinuePopUpValue(userChoice);
				break;
				
			case ERROR:
				Object[] errorOptions = {"OK"};
				userChoice = 0;
				JOptionPane.showMessageDialog(window,
											"Une erreur dans le fichier XML a été détectée",
											"Erreur fichier XML",
											JOptionPane.WARNING_MESSAGE);
				window.manageContinuePopUpValue(userChoice);
				break;
		}
		return userChoice;
	}
	
	public static void errorPopUp(Window window, boolean zero) {
		String errorPopUpText;
		if (zero)
			errorPopUpText = "Veuillez entrer un nombre entier superieur ou egal a 0.";
		else
			errorPopUpText = "Veuillez entrer un nombre entier superieur a 0.";
		JOptionPane.showMessageDialog(window,
				errorPopUpText,
                "Entree invalide.",
                JOptionPane.ERROR_MESSAGE);
	}
}
