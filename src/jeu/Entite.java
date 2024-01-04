package jeu;

public abstract class Entite {
	
	private int pV;
	private int aT;
	private int posX;
	private int posY;
	private String nom;
	private int vitesseAT;
	private int maxPV;
	
	public Entite(int pV, int aT, int posX, int posY, String nom, int vitesseAT) {
		this.setpV(pV);
		this.setMaxPV(pV);
		this.setAT(aT);
		this.setPosX(posX);
		this.setPosY(posY);
		this.setVitesseAT(vitesseAT);
		this.setNom(nom);
	}
	
	public abstract void attaque(Plateau p);

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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getpV() {
		return pV;
	}

	public void setpV(int pV) {
		this.pV = pV;
	}

	public int getAT() {
		return aT;
	}

	public void setAT(int aT) {
		this.aT = aT;
	}

	public int getVitesseAT() {
		return vitesseAT;
	}

	public void setVitesseAT(int vitesseAT) {
		this.vitesseAT = vitesseAT;
	}

	public int getMaxPV() {
		return maxPV;
	}

	public void setMaxPV(int maxPV) {
		this.maxPV = maxPV;
	}


}
