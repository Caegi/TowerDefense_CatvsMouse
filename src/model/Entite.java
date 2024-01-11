package model;

public abstract class Entite {
	
	private int pV;
	private int aT;
	private int posX;
	private int posY;
	private int vitesseAT;
	private int maxPointsDeVie;
	
	public Entite(int pointsDeVie, int attaque, int posX, int posY, int vitesseAttaque) {
		this.pV = pointsDeVie;
		this.maxPointsDeVie = pointsDeVie;
		this.aT = attaque;
		this.posX = posX;
		this.posY = posY;
		this.vitesseAT = vitesseAttaque;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPointsDeVie() {
		return pV;
	}

	public void setPointsDeVie(int pV) {
		this.pV = pV;
	}

	public int getAttaque() {
		return aT;
	}

	public void setAttaque(int aT) {
		this.aT = aT;
	}

	public int getVitesseAttaque() {
		return vitesseAT;
	}

	public void setVitesseAttaque(int vitesseAT) {
		this.vitesseAT = vitesseAT;
	}

	public int getMaxPointsDeVie() {
		return maxPointsDeVie;
	}

	public void setMaxPointsDeVie(int maxPV) {
		this.maxPointsDeVie = maxPV;
	}


}
