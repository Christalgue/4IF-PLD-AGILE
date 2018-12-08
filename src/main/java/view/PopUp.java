package main.java.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.utils.PopUpType;

public class PopUp extends JFrame{
	
	protected static final String VALIDATE_ADD = "Voulez-vous vraiment ajouter ce point de livraison ?";
	protected static final String VALIDATE_ADD_DURATION = "Veuillez entrer la durée de la livraison.";
	protected static final String VALIDATE_MOVE = "Voulez-vous vraiment déplacer cette livraison ?";
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
				Object[] validateOptionsMove = {"Valider", "Anuler"};
				String inputValue = JOptionPane.showInputDialog("Duree de la livraison");
				//window.manageDurationPopUpValue(inputValue);
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
											"Meilleure tournée actuelle",
											JOptionPane.YES_NO_CANCEL_OPTION,
											JOptionPane.QUESTION_MESSAGE,
											null,
											continueOptions,
											continueOptions[0]
											);
				//window.manageContinuePopUpValue(userChoice);
				break;
		}
		return userChoice;
	}
}
