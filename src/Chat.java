
public class Chat extends Entite {
	
	// les chats sont les ennemis
	public Chat(int pV, int aT, int posX, int posY, int vitesseAT, String nom ) {
		super(pV, aT, posX, posY, nom, vitesseAT);
	}

	@Override
	public void attaque(Plateau p) {
		
		Case cible = p.cases [this.posX + 1] [this.posY];
		String nom = this.nom;
		int aT = this.aT;
		int vitesseAT = this.vitesseAT;
		
		new java.util.Timer().scheduleAtFixedRate( // toutes les vitesseAT millisegondes, le Chat attaque
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	if ( (cible != null) && (cible.getEntite() instanceof Tour) ) { // s'il y a une tour devant le chat
		        			Entite entiteCible = cible.getEntite();
		        			
		        			System.out.println(nom + " attaque " + entiteCible.nom);
		        			int nPV = entiteCible.pV - aT;
		        			System.out.println("PV de " + entiteCible.nom + " avant attaque: " + entiteCible.pV);
		        			
		        			if (nPV > 0 ){
		        				entiteCible.pV = nPV;
			        			System.out.println("PV de " + entiteCible.nom + " après attaque: " + nPV + "\n");
		        			}
		        			else {
		        				System.out.println("PV de " + entiteCible.nom + " après attaque: " + nPV);
		        				System.out.println(entiteCible.nom + " a été détruit \n" );
		        				cible.enleverEntite();
		        				p.afficheTout();
		        			}
		            	}
		            }
		        }, 
		        1000, vitesseAT // après 1 segondem, toutes les vitesseAT/1000 segondes, le Chat attaque
		);
		
		
	}
	
}
