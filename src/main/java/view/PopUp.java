package main.java.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.utils.PopUpType;

public class PopUp extends JFrame{

	protected static final String VALIDATE_ADD = "Voulez-vous vraiment ajouter ce point de livraison ?";
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
													null,     //do not use a custom Icon
													validateOptions,  //the titles of buttons
													validateOptions[0]//default button title
													); 
			break;
			case DELETE: 
				Object[] deleteOptions = {"Valider", "Annuler"};
				userChoice = JOptionPane.showOptionDialog(window,
													VALIDATE_DELETE,
													"Valider la suppression de la livraison",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,     //do not use a custom Icon
													deleteOptions,  //the titles of buttons
													deleteOptions[0]//default button title
													); 
			break;
			case CONTINUE: 
				Object[] continueOptions = {"Garder", "Continuer"};
				userChoice = JOptionPane.showOptionDialog(window,
											VALIDATE_CONTINUE,
											"Meilleure tourneee actuelle",
											JOptionPane.YES_NO_CANCEL_OPTION,
											JOptionPane.QUESTION_MESSAGE,
											null,     //do not use a custom Icon
											continueOptions,  //the titles of buttons
											continueOptions[0]//default button title
											); 
				break;
		}
		return userChoice;
	}
}
