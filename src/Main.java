import ig.*;
import jeu.*;

public class Main {
    
    public static void main(String[] args) {
    	
//    	// Version console
//    	int vitesseGenChats = 24000;
//    	int argent = 50; // argent au début de la partie
//    	int hauteur = 5;
//    	int largeur = 5;
//    	int vitesseGenArgent = 20000;
//    	int vitesseAffichage = 1500;
//    	
//        Plateau Niveau1 = new Plateau(hauteur, largeur, vitesseGenChats, argent, vitesseGenArgent);
//        AffichageConsole afC = new AffichageConsole(vitesseAffichage);
//        Niveau1.genereChatContinu();
//        Niveau1.genereArgentContinu();	
//        afC.afficheContinu(Niveau1);
//        afC.lancerJeuCreationTours(Niveau1);
        
    	// version Implementation Graphique
        MenuPrincipal menu = new MenuPrincipal();
        menu.faireMenu();
    	
    }	


}
