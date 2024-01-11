package model;

public class TourData extends Entite {

	private int cout;

	public TourData(int pV, int aT, int posX, int posY, int vitesseAT, int cout) {
		super(pV, aT, posX, posY, vitesseAT);
		this.cout = cout;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}
	
}
