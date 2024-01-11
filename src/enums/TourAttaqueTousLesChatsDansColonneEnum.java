package enums;


public enum TourAttaqueTousLesChatsDansColonneEnum {
	
	ATTAQUE(4);

	TourAttaqueTousLesChatsDansColonneEnum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}

