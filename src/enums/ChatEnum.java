package enums;

public enum ChatEnum {
	POINTS_DE_VIE(45),
	ATTAQUE(14),
	VITESSE_ATTAQUE(2500), // 2.5 segondes
	VITESSE_DEPLACEMENT(3500);

	ChatEnum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}
