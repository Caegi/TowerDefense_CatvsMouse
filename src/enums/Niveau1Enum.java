package enums;

public enum Niveau1Enum {
	
	VITESSE_GENERATION_CHAT(6000), // 12 segondes
	ARGENT(50), // argent au début de la partie
	HAUTEUR(5), 
	LARGEUR(5), 
	VITESSE_GENERATION_ARGENT(6500), // 12.5 segondes
	VITESSE_AFFICHAGE_IMPLEMENTATION_GRAPHIQUE(100), // 0.1 segondes
	VITESSE_AFFICHAGE_CONSOLE(1500), // 1 segonde
	NOMBRE_CHAT_A_ELIMINER_AVANT_VICTOIRE_MODE_NORMAL(8);

	Niveau1Enum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}


