package enums;

public enum Niveau2Enum {
	
	VITESSE_GENERATION_CHAT(3000), // 3 segondes
	ARGENT(50), // argent au début de la partie
	HAUTEUR(7), 
	LARGEUR(5), 
	VITESSE_GENERATION_ARGENT(3500),
	NOMBRE_CHAT_A_ELIMINER_AVANT_VICTOIRE_MODE_NORMAL(15); 

	Niveau2Enum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}


