package enums;

public enum TourQuiTireAlEnversEnum {
	
	POINTS_DE_VIE(75),
	ATTAQUE(10); 

	TourQuiTireAlEnversEnum(int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}

