package controller;

import model.Case;
import model.ChatData;
import model.Entite;
import model.PlateauData;
import model.TourData;

public class TourLogic {
	
	private TourData tourData;
	
	public TourLogic(TourData data) {
		this.setTourData(data);
	}
	
	public void attaque(PlateauData plateauData) {
		// si Tour pas détruite
		if ( (plateauData.getCases()[this.getTourData().getPosX()][this.getTourData().getPosY()] != null) && 
				(plateauData.getCases()[this.getTourData().getPosX()][this.getTourData().getPosY()].getEntite() instanceof TourData) ) { 
			
			// itérer sur les rangées du plateau
			for (int iRow = this.getTourData().getPosX()-1; iRow>=0; iRow--){
				
				// cible: case que la tour essaye d'attaquer
				Case cible = plateauData.getCases() [iRow][this.getTourData().getPosY()];
				int pointsDeVie = 0;
				
				// si il y a un chat dans la cible
		    	if ( (cible != null) && (cible.getEntite() instanceof ChatData) ) {
		    		Entite entiteCible = cible.getEntite();
		    		pointsDeVie = entiteCible.getPointsDeVie();
		    		// points de vie du chat après avoir été attaqué
					int nouveauPointsDeVie = pointsDeVie - this.getTourData().getAttaque();
					
					// si le chat n'a pas été éliminé
					if (nouveauPointsDeVie > 0 ){
						entiteCible.setPointsDeVie(nouveauPointsDeVie);
					}
					// si chat a été éliminé
					else { 
						plateauData.viderCase(iRow, this.getTourData().getPosY()); // actualiser cases
						// actualiser compteur eliminés (pour le mode de jeu normal)
						plateauData.setCompteurChatElimines( plateauData.getCompteurChatElimines() + 1 );
					}
					break; // sortie du for loop pour que la tour attaque seulement le premier chat qu'il trouve
		    	}
			}
		}
	}		
	
	public void attaqueContinu(PlateauData plateauData) {
		int vitesseAttaque = this.getTourData().getVitesseAttaque();
		TourData referenceTourData = this.getTourData();
		TourLogic referenceTourLogic = this; // pour que la tour qui attaque soit visible dans la classe anonyme de type Timer
		
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
		        1000, vitesseAttaque // // après 1 segonde, la tour attaque toutes les vitesseAttaque millisegondes
		);
	}

	public TourData getTourData() {
		return tourData;
	}

	public void setTourData(TourData tourData) {
		this.tourData = tourData;
	}

}
