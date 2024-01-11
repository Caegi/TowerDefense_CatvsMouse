package enums;

public enum DifficulteEnum {
	
	FACILE(1),
	MOYEN(2),
	DIFFICILE(3);

	DifficulteEnum(int value) {
		this.value = value;
	}

	private int value;
	
	public int getValue() {
		return value;
	}
	
}

