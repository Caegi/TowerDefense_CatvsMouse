package enums;


public enum TourBouclierEnum {
	
	POINTS_DE_VIE(250),
	ATTAQUE(0); 

	TourBouclierEnum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}

