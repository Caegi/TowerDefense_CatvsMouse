import javax.swing.JFrame;

import controller.PlateauLogic;
import enums.Niveau1Enum;
import model.PlateauData;
import view.AffichageConsole;
import view.MenuPrincipal;

public class Main {
    
    public static void main(String[] args) {
    	
    	// Version console
    	if (args[0].equals("texte")) {
	    	// la console lance par defaut le niveau 1 dans le mode normal et la difficulté facile
	        int vitesseGenerationChat = Niveau1Enum.VITESSE_GENERATION_CHAT.getValue(); 
	        int argent = Niveau1Enum.ARGENT.getValue(); // argent au début de la partie
	        int hauteur = Niveau1Enum.HAUTEUR.getValue();
	    	int largeur = Niveau1Enum.LARGEUR.getValue();
	    	int vitesseGenerationArgent = Niveau1Enum.VITESSE_GENERATION_ARGENT.getValue();
	    	int degreAugmentationStatsChats = 1; // 1 correspond au mode de difficulté facile, les points de vie des chats et leur attaque vont être multipliés par 1
	    	boolean isModeNormal = true;
	    	int nombreDeChatsAGenererAvantVictoireModeNormal = 5;
	    	
	        PlateauData Niveau1Data = new PlateauData(hauteur, largeur, vitesseGenerationChat, argent, vitesseGenerationArgent, 
	        		degreAugmentationStatsChats, isModeNormal, nombreDeChatsAGenererAvantVictoireModeNormal);
	        PlateauLogic Niveau1Logic = new PlateauLogic(Niveau1Data);
	        Niveau1Logic.genereChatContinu();
	        Niveau1Logic.genereArgentContinu();
	        
	        AffichageConsole affichageConsole = new AffichageConsole(Niveau1Enum.VITESSE_AFFICHAGE_CONSOLE.getValue());
	        affichageConsole.afficheContinu(Niveau1Data);
	        affichageConsole.lancerJeuCreationTours(Niveau1Logic);
    	}
    	
    	// version Implementation Graphique
    	else if (args[0].equals("graphique")) {
            MenuPrincipal menu = new MenuPrincipal();
            JFrame frame = new JFrame("Tower Defense: Chat vs Souris");
            menu.faireMenu(frame);
    	}
    	
    }
}