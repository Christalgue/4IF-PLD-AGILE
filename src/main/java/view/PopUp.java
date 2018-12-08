package main.java.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopUp extends JFrame{
	
	public static final String VALIDATE_ADD = "Voulez-vous vraiment ajouter ce point de livraison ?";
	public static final String VALIDATE_DELETE = "Voulez-vous vraiment supprimer ce point de livraison ?";
	public static final String CONTINUE = "Voulez-vous continuer la recherche de chemins ?";
	
	protected static JOptionPane popUp;
	
	public PopUp() {
		popUp = new JOptionPane();
	}
	
	public static int displayPopUp (String message, Window window) {
		int userChoice = 0;
		switch (message){
			case VALIDATE_ADD: 
				Object[] validateOptions = {"Valider", "Anuler"};
				userChoice = popUp.showOptionDialog(window,
													CONTINUE,
													"Valider ajout de la livraison",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,     //do not use a custom Icon
													validateOptions,  //the titles of buttons
													validateOptions[0]//default button title
													); 
			break;
			case VALIDATE_DELETE: 
				Object[] deleteOptions = {"Valider", "Anuler"};
				userChoice = popUp.showOptionDialog(window,
													CONTINUE,
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
				userChoice = popUp.showOptionDialog(window,
											CONTINUE,
											"Meilleure tournee actuelle",
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
