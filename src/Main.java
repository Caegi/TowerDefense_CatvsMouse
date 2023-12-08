import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
    	int vitesseGenChats = 30000;
    	int argent = 50; // argent au début de la partie
    	int hauteur = 5;
    	int largeur = 5;
    	int vitesseGenArgent = 20000;
    	
        Plateau Niveau1 = new Plateau(hauteur, largeur, vitesseGenChats, argent, vitesseGenArgent);
        Niveau1.genereChatContinu();
        Niveau1.genereArgentContinu();

        try (Scanner myObj = new Scanner(System.in)) {
            boolean createTower = true;

            while (createTower) {  // boucle infinie pour creer tour pour l'instant 
               	System.out.println("\nMettre une tour (coût 50) \nposition x ? (de 1 à " + Niveau1.getLargeur() + "):");
                int posX = myObj.nextInt() - 1;

                System.out.println("\nposition y ? (de 1 à " + Niveau1.getLargeur() + "):");
                int posY = myObj.nextInt() - 1;
                myObj.nextLine();

                Niveau1.ajouteTour(posX, posY);
                Niveau1.afficheTout(); // essaye de afficher que toutes 2 segondes au lieu d'afficher tout le temps, peut être effacer avant d'afficher 
            }
        }
    }
}
