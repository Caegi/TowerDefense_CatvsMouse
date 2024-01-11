package controller;

import model.Case;
import model.ChatData;
import model.Entite;
import model.PlateauData;
import model.TourData;

public class TourAttaqueDernierChatColoneEnPremierLogic extends TourLogic {

	public TourAttaqueDernierChatColoneEnPremierLogic(TourData data) {
		super(data);
	}
	
	@Override
	public void attaque(PlateauData plateauData) {
		
		// si Tour pas détruite
		if ( (plateauData.getCases()[this.getTourData().getPosX()][this.getTourData().getPosY()] != null) && 
				(plateauData.getCases()[this.getTourData().getPosX()][this.getTourData().getPosY()].getEntite() instanceof TourData) ) { 
			
			// itérer sur les rangées du plateau
			for (int iRow = 0; iRow < 5; iRow++){ // changement ici, parcoure la colonne a l'envers (attaque dernier chat devant lui au lieu du premier)
				
				Case cible = plateauData.getCases() [iRow][this.getTourData().getPosY()];
				int pVChat = 0;
				
		    	if ( (cible != null) && (cible.getEntite() instanceof ChatData) ) {
		    		Entite entiteCible = cible.getEntite();
		    		pVChat = entiteCible.getPointsDeVie();
					int nouveauPointsDeVie = pVChat - this.getTourData().getAttaque();
					
					if (nouveauPointsDeVie > 0 ){
						entiteCible.setPointsDeVie(nouveauPointsDeVie);
					}
					
					else { // si Chat meurt
						plateauData.viderCase(iRow, this.getTourData().getPosY()); // actualiser cases
						plateauData.setCompteurChatElimines( plateauData.getCompteurChatElimines() + 1 );
					}
					break; // sortie du for loop pour que la tour attaque seulement le premier chat qu'il trouve
		    	}
			}
		}
	}
	
	@Override
	public void attaqueContinu(PlateauData plateauData) {
		int vitesseAttaque = this.getTourData().getVitesseAttaque();
		TourData referenceTourData = this.getTourData();
		TourAttaqueDernierChatColoneEnPremierLogic referenceTourLogic = this; // pour que la tour qui attaque soit visible dans la classe anonyme de type Timer
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	// si la tour a ete detruite
		            	// ou s'il y a un chat où la tour devrait être
		            	// -> arreter d'attaquer
		            	if ((plateauData.getCases()[referenceTourData.getPosX()][referenceTourData.getPosY()] == null) ||
            				(plateauData.getCases()[referenceTourData.getPosX()][referenceTourData.getPosY()].getEntite() instanceof ChatData) ) {
		            		cancel();
		            	}
		            	if (plateauData.isGameOver()){
		            		cancel();
		            	}
		            	referenceTourLogic.attaque(plateauData);
		            }
		        }, 
		        1000, vitesseAttaque 
		);
	}

}
