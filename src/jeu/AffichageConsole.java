package jeu;

import java.util.Scanner;

public class AffichageConsole {
	
	private int vitesseAffichage;
	
	public AffichageConsole(int vAf) {
		this.vitesseAffichage = vAf;
	}
	
	public void afficheTout(Plateau p) {
		System.out.println("\n Argent: " + p.getArgent());
		// itérer sur les rangées du plateau
		for (int iRow = 0; iRow < p.getLargeur(); iRow++){
			System.out.println();
			// itérer sur les colonnes du plateau
			for (int iCol = 0; iCol < p.getHauteur(); iCol++){
				if ( !(p.caseEstVide(iRow, iCol)) ) {
					System.out.print( p.getCases()[iRow][iCol].toString() );
				}
				else {
					System.out.print(" . ");
				}
			}
		}
		System.out.print(" \n      /\\ \n     /  \\\n    /----\\\n   / |  | \\\n   | |  | |"); // affiche la maison de souris
		System.out.println("\n\n" + "Points de Vie Maison: " + p.getPvMaison() + "%\n");
	}
	
	public void afficheContinu(Plateau p) {
		Plateau ref_p = p; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		AffichageConsole ref_afC = this; 
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	ref_afC.afficheTout(ref_p);
		            	if (p.isGameOver()) {
		            		System.out.println("\nGAME OVER");
							System.exit(0); // terminer le programme
						}
		            }
		        }, 
		        100, this.vitesseAffichage  // affiche le plateau toutes les (vitesseAffichage / 1000) segondes
		);
	
	}
	
	public void lancerJeuCreationTours(Plateau p) {
		try (Scanner myObj = new Scanner(System.in)) {
            while ( !(p.isGameOver()) ) {  // pendant que le jeu n'est pas termine
               	System.out.println("\nMettre une tour (coût 50) \nposition x ? (de 2 à " + p.getLargeur() + "):"); // creer tour position x=1 pas encore traite
                int posX = myObj.nextInt() - 1;
                System.out.println("\nposition y ? (de 1 à " + p.getLargeur() + "):");
                int posY = myObj.nextInt() - 1;
                myObj.nextLine();
                p.ajouteTour(posX, posY);
            }
            
            
        }
	}
}
