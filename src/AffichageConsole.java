
public class AffichageConsole {
	
	private int vitesseAffichage;
	
	public AffichageConsole(int vAf) {
		this.vitesseAffichage = vAf;
	}
	
	public void afficheTout(Plateau p) {
		// itérer sur les rangées du plateau
		System.out.println("\n Argent: " + p.getArgent());
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
		System.out.println("\n");
	}
	
	public void afficheContinu(Plateau p) {
		Plateau ref_p = p; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	ref_p.afficheTout();
		            }
		        }, 
		        100, this.vitesseAffichage  // affiche le plateau toutes les (vitesseAffichage / 1000) segondes
		);
	
	}
}
