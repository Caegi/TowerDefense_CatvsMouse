package enums;


public enum TourRapideEnum {
	
	VITESSE_ATTAQUE(100), // attaque plsu rapide (100 contre 600)
	ATTAQUE(1); //pour equilibrer le fait que la tour qui penetre puisse attaquer plus rapidement, elle a une attaque plus faible que la tour normale (1 contre 6) 

	TourRapideEnum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}

