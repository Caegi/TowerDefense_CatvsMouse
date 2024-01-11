package controller;

import enums.TourAttaqueTousLesChatsDansColonneEnum;
import model.Case;
import model.ChatData;
import model.Entite;
import model.PlateauData;
import model.TourData;

public class TourAttaqueTousLesChatsDansColonneLogic extends TourLogic{
	
	public TourAttaqueTousLesChatsDansColonneLogic(TourData data) {
		super(data);
		this.getTourData().setAttaque(TourAttaqueTousLesChatsDansColonneEnum.ATTAQUE.getValue());
	}
	
	@Override
	public void attaque(PlateauData plateauData) {
		// si Tour pas détruite
		if ( (plateauData.getCases()[this.getTourData().getPosX()][this.getTourData().getPosY()] != null) && 
				(plateauData.getCases()[this.getTourData().getPosX()][this.getTourData().getPosY()].getEntite() instanceof TourData) ) { 
			
			// itérer sur les rangées du plateau
			for (int iRow = 0; iRow < 5; iRow++){ 
				
				// cible: case que la tour essaye d'attaquer
				Case cible = plateauData.getCases() [iRow][this.getTourData().getPosY()];
				int pVChat = 0;
				
				// si il y a un chat dans la cible
		    	if ( (cible != null) && (cible.getEntite() instanceof ChatData) ) {
		    		Entite entiteCible = cible.getEntite();
		    		pVChat = entiteCible.getPointsDeVie();
					int nouveauPointsDeVie = pVChat - this.getTourData().getAttaque();
					
		    		// points de vie du chat après avoir été attaqué
					if (nouveauPointsDeVie > 0 ){
						entiteCible.setPointsDeVie(nouveauPointsDeVie);
					}
					
					else { // si Chat meurt
						plateauData.viderCase(iRow, this.getTourData().getPosY()); // actualiser cases
						plateauData.setCompteurChatElimines( plateauData.getCompteurChatElimines() + 1 );
					}
		    	}
			}
		}
	}
	
	@Override
	public void attaqueContinu(PlateauData plateauData) {
		int vitesseAttaque = this.getTourData().getVitesseAttaque();
		TourData referenceTourData = this.getTourData();
		TourAttaqueTousLesChatsDansColonneLogic referenceTourLogic = this; // pour que la tour qui attaque soit visible dans la classe anonyme de type Timer
		
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
		        1000, vitesseAttaque // après une segonde, la tour attaque toutes les vitesseAttaque millisegondes
		);
	}
}
