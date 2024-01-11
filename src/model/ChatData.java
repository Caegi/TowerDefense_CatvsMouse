package model;

public class ChatData extends Entite implements Cloneable {
	 
	private int vitesseDeplacement;

	// les chats sont les ennemis
	public ChatData(int pV, int aT, int posX, int posY, int vitesseAT, int vitesseDeplacement) {
		super(pV, aT, posX, posY, vitesseAT);
		this.vitesseDeplacement = vitesseDeplacement; // seuls les chats se déplacent
	}
	
	public ChatData clone() {
		try {
          	ChatData clone = (ChatData) super.clone(); // Copie supercielle sufisante car il y a que des objets primitifs dans Chat
            return clone;
            
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
	}

	public int getVitesseDeplacement() {
		return vitesseDeplacement;
	}

	public void setVitesseDeplacement(int vitesseDeplacement) {
		this.vitesseDeplacement = vitesseDeplacement;
	}
	
}	