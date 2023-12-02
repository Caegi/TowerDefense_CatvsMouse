
public abstract class Entite {
	
	protected int pV;
	protected int aT;
	protected int posX;
	protected int posY;
	protected String nom;
	protected int vitesseAT; //devrait être public ?
	
	public Entite(int pV, int aT, int posX, int posY, String nom, int vitesseAT) {
		this.pV = pV;
		this.aT = aT;
		this.posX = posX;
		this.posY = posY;
		this.vitesseAT = vitesseAT;
		this.nom = nom;
	}
	
	public abstract void attaque(Plateau p);



}
