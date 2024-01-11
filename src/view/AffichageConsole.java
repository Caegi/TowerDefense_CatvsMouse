package view;

import java.util.Scanner;

import controller.PlateauLogic;
import model.PlateauData;

public class AffichageConsole {
	
	private int vitesseAffichage;
	
	public AffichageConsole(int vAf) {
		this.vitesseAffichage = vAf;
	}
	
	public void afficheTout(PlateauData plateauData) {
		System.out.println("\n Argent: " + plateauData.getArgent());
		// itérer sur les rangées du plateau
		for (int iRow = 0; iRow < plateauData.getLargeur(); iRow++){
			System.out.println();
			// itérer sur les colonnes du plateau
			for (int iCol = 0; iCol < plateauData.getHauteur(); iCol++){
				if ( !(plateauData.caseEstVide(iRow, iCol)) ) {
					System.out.print( plateauData.getCases()[iRow][iCol].toString() );
				}
				else {
					System.out.print(" . ");
				}
			}
		}
		System.out.print(" \n      /\\ \n     /  \\\n    /----\\\n   / |  | \\\n   | |  | |"); // affiche la maison de souris
		System.out.println("\n\n" + "Points de Vie Maison: " + plateauData.getPvMaison() + "%\n");
	}
	
	public void afficheContinu(PlateauData plateauData) {
		PlateauData referencePlateau = plateauData; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		AffichageConsole ref_afC = this; 
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	ref_afC.afficheTout(referencePlateau);
		            	if (plateauData.isGameOver()) {
		            		System.out.println("\nGAME OVER");
							System.exit(0); // terminer le programme
						}
		            	if (plateauData.isVictory()) {
		            		System.out.println("\nVICTOIRE !");
							System.exit(0); // terminer le programme
						}
		            }
		        }, 
		        100, this.vitesseAffichage  // affiche le plateau toutes les (vitesseAffichage / 1000) segondes
		);
	
	}
	
	public void lancerJeuCreationTours(PlateauLogic plateauLogic) {
		try (Scanner myObj = new Scanner(System.in)) {
            while ( !(plateauLogic.getPlateauData().isGameOver()) ) {  // pendant que le jeu n'est pas termine
               	System.out.println("\nMettre une tour (coût 50) \nposition x ? (de 2 à " + plateauLogic.getPlateauData().getLargeur() + "):"); // creer tour position x=1 pas encore traite
                int posX = myObj.nextInt() - 1;
                System.out.println("\nposition y ? (de 1 à " + plateauLogic.getPlateauData().getLargeur() + "):");
                int posY = myObj.nextInt() - 1;
                myObj.nextLine();
                
                if ((posX > 1) && (posX < plateauLogic.getPlateauData().getHauteur()) && (posY < plateauLogic.getPlateauData().getLargeur()) ){
                	plateauLogic.ajouteTour(posX, posY);
                }
                else {
                	System.out.println("Position invalide");
                }
            }
            
            
        }
	}
}
