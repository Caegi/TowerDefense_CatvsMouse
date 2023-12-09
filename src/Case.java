
public class Case {
	
	private Entite e;
	
	public Case(Entite e) {
		this.e = e;
	}
	
	public Entite getEntite() {
		return this.e;
	}
	
	public void setEntite(Entite e) {
		this.e = e;
	}
	
	public String toString() {
		
		if (e instanceof Chat) {
			return " " + e.getNom() + " ";
		}
		
		else if (e instanceof Tour) {
			return " " + e.getNom() + " ";
		}
		
		else {
			return " . ";
		}
	}
}
