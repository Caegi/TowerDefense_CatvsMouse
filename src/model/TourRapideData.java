package model;

import enums.TourRapideEnum;

public class TourRapideData extends TourData {

	public TourRapideData(int pV, int aT, int posX, int posY, int vitesseAT, int cout) {
		super(pV, aT, posX, posY, vitesseAT, cout);
		this.setAttaque(TourRapideEnum.ATTAQUE.getValue());
		this.setVitesseAttaque(TourRapideEnum.VITESSE_ATTAQUE.getValue());
	}
	
	
}
