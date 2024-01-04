package jeu;

public class Tour extends Entite {

	private int cout;

	public Tour(int pV, int aT, int posX, int posY, String nom, int vitesseAT, int cout) {
		super(pV, aT, posX, posY, nom, vitesseAT);
		this.cout = cout;
	}

	@Override
	public void attaque(Plateau p) {
		if ( (p.getCases()[this.getPosX()][this.getPosY()] != null) && (p.getCases()[this.getPosX()][this.getPosY()].getEntite() instanceof Tour) ) { // si Tour pas détruite
			// itérer sur les rangées du plateau
			for (int iRow = this.getPosX()-1; iRow>=0; iRow--){
				Case cible = p.getCases() [iRow][this.getPosY()];
				int pVChat = 0;
		    	if ( (cible != null) && (cible.getEntite() instanceof Chat) ) {
		    		Entite entiteCible = cible.getEntite();
		    		pVChat = entiteCible.getpV();
					int nPV = pVChat - this.getAT();
					if (nPV > 0 ){
						entiteCible.setpV(nPV);
		    			System.out.println(this.getNom() + "(x" + (this.getPosX()+1) + "|y" + (this.getPosY()+1) +") attaque -> PV de " + entiteCible.getNom() 
		    					 + "(x" + (entiteCible.getPosX()+1) + "|y" + (entiteCible.getPosY()+1) + "): " + nPV);
					}
					else { // si Chat meurt
						System.out.println(entiteCible.getNom() + "(x" + (entiteCible.getPosX()+1) + "|y" + (entiteCible.getPosY()+1) + ") a été détruit \n" );
						p.viderCase(iRow, this.getPosY()); // actualiser cases
					}
					break; // pour que la tour attaque que le premier chat qu'il trouve
		    	}
			}
		}
	}		
	
	public void attaqueContinu(Plateau p_ref) {
		int vitesseAT = this.getVitesseAT();
		Tour tour = this; // pour que la tour qui attaque soit visible dans la classe anonyme de type Timer
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	if ( (p_ref.getCases()[tour.getPosX()][tour.getPosY()] == null) ||
            				(p_ref.getCases()[tour.getPosX()][tour.getPosY()].getEntite() instanceof Chat) ) {
		            		cancel();
		            	}
		            	tour.attaque(p_ref);
		            }
		        }, 
		        1000, vitesseAT 
		);
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}
	
}
