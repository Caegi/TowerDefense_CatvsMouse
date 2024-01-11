package model;

public class Case {
	
	private Entite entite;
	
	public Case(Entite entite) {
		this.entite = entite;
	}
	
	public Entite getEntite() {
		return this.entite;
	}
	
	public void setEntite(Entite e) {
		this.entite = e;
	}
	
	@Override
	public String toString() {
		
		if (entite instanceof ChatData) {
			return " C ";
		}
		
		else if (entite instanceof TourData) {
			return " T ";
		}
		
		else if (entite instanceof Pierre) {
			return " P ";
		}
		
		else {
			return " . ";
		}
	}
}
