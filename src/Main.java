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
	        int vitesseGenerationChat = Niveau1Enum.VITESSE_GENERATION_CHAT.getValue(); // la vitesse en millisegondes de la generation des chats
	        int argent = Niveau1Enum.ARGENT.getValue(); // argent au début de la partie
	        int hauteur = Niveau1Enum.HAUTEUR.getValue(); 
	    	int largeur = Niveau1Enum.LARGEUR.getValue();
	    	int vitesseGenerationArgent = Niveau1Enum.VITESSE_GENERATION_ARGENT.getValue(); // la vitesse en millisegondes de la generation de l'argent
	    	int degreAugmentationStatsChats = 1; // 1 correspond au mode de difficulté facile (les points de vie des chats et leur attaque vont être multipliés par 1)
	    	boolean isModeNormal = true; // pour la console le mode par defaut est le mode normal
	    	int nombreDeChatsAEliminerAvantVictoireModeNormal = Niveau1Enum.NOMBRE_CHAT_A_ELIMINER_AVANT_VICTOIRE_MODE_NORMAL.getValue(); // il suffit de eliminer 8 chats pour gagner 
	    	
	    	// initialiser les données du niveau1 (package model)
	        PlateauData Niveau1Data = new PlateauData(hauteur, largeur, vitesseGenerationChat, argent, vitesseGenerationArgent, 
	        		degreAugmentationStatsChats, isModeNormal, nombreDeChatsAEliminerAvantVictoireModeNormal);
	        // initialiser la logique du niveau1 (package controller)
	        PlateauLogic Niveau1Logic = new PlateauLogic(Niveau1Data);
	        // commencer le timer de la generation de chat (il genere les chats de manière continue)
	        Niveau1Logic.genereChatContinu();
	        // commencer le timer de la generation de l'argent (il genere de l'argent de manière continue)
	        Niveau1Logic.genereArgentContinu();
	        
	        // package view, unique classe d'affichage de la console 
	        AffichageConsole affichageConsole = new AffichageConsole(Niveau1Enum.VITESSE_AFFICHAGE_CONSOLE.getValue());
	        // commencer le timer qui print continuellement le jeu dans la console 
	        affichageConsole.afficheContinu(Niveau1Data);
	        // lancer le jeu et rendre possible la creation de tours en fonction de l'input de l'utilisateur
	        affichageConsole.lancerJeuCreationTours(Niveau1Logic);
    	}
    	
    	// version Implementation Graphique
    	else if (args[0].equals("graphique")) {
    		// initialiser le menu principal
            MenuPrincipal menu = new MenuPrincipal();
            // creer la fenêtre du jeu avec le titre du jeu
            JFrame frame = new JFrame("Tower Defense: Chat vs Souris");
            // creer le menu principal
            menu.faireMenu(frame);
    	}
    	
    }
}