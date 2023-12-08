
public class Missile extends Entite implements Cloneable {

	public Missile(int pV, int aT, int posX, int posY, String nom, int vitesseAT) {
		super(pV, aT, posX, posY, nom, vitesseAT);
	}

	@Override
	public void attaque(Plateau p) {
		
		Case cible = p.getCases()[this.getPosX()-1][this.getPosY()];
			
		if ( (cible != null) && (cible.getEntite() instanceof Chat)) {
			Entite entiteCible = cible.getEntite();
			int nPV = entiteCible.getpV() - this.getAT();
			
			if (nPV > 0 ){
				entiteCible.setpV(nPV);
    			System.out.println("PV de " + entiteCible.getNom() + " après attaque: " + nPV);
			}
			else { // si Chat meurt
				System.out.println(entiteCible.getNom() + " a été détruit \n" );
				cible.enleverEntite(); // enlever l'entité de la case
				p.getCases() [this.getPosX() - 1][this.getPosY()] = null; // pour que les cases du plateau soit actualisées 
			}
			p.getCases()[this.getPosX()][this.getPosY()].enleverEntite();
			p.getCases()[this.getPosX()][this.getPosY()] = null;
			p.afficheTout();
		}
	}
	
	public void deplace(Plateau p) throws CloneNotSupportedException {
		Missile missile = (Missile) this.clone();
		
		if (this.getPosX() + 1 <= p.getHauteur()) { 
			Case cible = p.getCases()[missile.getPosX()-1][missile.getPosY()];
			
			if ((cible == null))  {
				
				missile.setPosX(getPosX() - 1);
				p.getCases() [this.getPosX() - 1][getPosY()] = new Case(missile);
			    this.setPosX(this.getPosX() - 1); // actualiser la posX de this pour que le prochain deplacement marche
			    p.afficheTout();
			    if (p.getCases()[getPosX()][getPosY()] != null) {
			    	p.getCases()[getPosX()][getPosY()].enleverEntite();
			    }
			}
		}

	}	
	
	public void deplaceContinu(Plateau p_ref) {
		int vitesseDL = 4000;
		Missile missile = this; // pour que la ref de this soit visible dans la classe anonyme de type timer
		
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {

					@Override
		            public void run() {
						Case cible = p_ref.getCases()[missile.getPosX()-1][missile.getPosY()];
						
						if ( (cible != null) && (cible.getEntite() instanceof Chat)) {
							missile.attaque(p_ref);
							cancel();
						}
		            	try {
							missile.deplace(p_ref);
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
		            }
		        }, 
		        vitesseDL, vitesseDL 
		);
	}
	
	

}
