
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
	
	public boolean estVide() {
		return !(this.e == null); 
	}
	
	public void enleverEntite() {
		this.e = null;
	}
	
	public String toString() {
		
		if (e instanceof Chat) {
			return e.nom + " ";
		}
		
		else if (e instanceof Tour) {
			return e.nom + " ";
		}
		
		else {
			return " . ";
		}
	}
}
