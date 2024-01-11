package enums;

public enum DefaultTourEnum {
	POINTS_DE_VIE(150),
	ATTAQUE(6),
	VITESSE_ATTAQUE(800),
	COUT(50);

	DefaultTourEnum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}

