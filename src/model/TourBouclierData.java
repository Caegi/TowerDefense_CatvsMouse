package model;

import enums.TourBouclierEnum;

public class TourBouclierData extends TourData {

	public TourBouclierData(int pV, int aT, int posX, int posY, int vitesseAT, int cout) {
		super(pV, aT, posX, posY, vitesseAT, cout);
		this.setAttaque(TourBouclierEnum.ATTAQUE.getValue());
		this.setPointsDeVie(TourBouclierEnum.POINTS_DE_VIE.getValue());
	}

}
