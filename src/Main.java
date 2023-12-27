import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
    	int vitesseGenChats = 25000;
    	int argent = 50; // argent au début de la partie
    	int hauteur = 5;
    	int largeur = 5;
    	int vitesseGenArgent = 20000;
    	
        Plateau Niveau1 = new Plateau(hauteur, largeur, vitesseGenChats, argent, vitesseGenArgent);
        AffichageConsole A = new AffichageConsole(1500);
        Niveau1.genereChatContinu();
        Niveau1.genereArgentContinu();
        A.afficheContinu(Niveau1);

        try (Scanner myObj = new Scanner(System.in)) {
            boolean createTower = true;

            while (createTower) {  // boucle infinie pour creer tour pour l'instant 
               	System.out.println("\nMettre une tour (coût 50) \nposition x ? (de 2 à " + Niveau1.getLargeur() + "):"); // creer tour position x=1 pas encore traite
                int posX = myObj.nextInt() - 1;

                System.out.println("\nposition y ? (de 1 à " + Niveau1.getLargeur() + "):");
                int posY = myObj.nextInt() - 1;
                myObj.nextLine();

                Niveau1.ajouteTour(posX, posY);
            }
        }
    }
}
