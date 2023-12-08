
public class Chat extends Entite implements Cloneable {
	 
	private int vitesseDL;

	// les chats sont les ennemis
	public Chat(int pV, int aT, int posX, int posY, int vitesseAT, String nom, int vitesseDL) {
		super(pV, aT, posX, posY, nom, vitesseAT);
		this.vitesseDL = vitesseDL; // toutes les entités se déplacent pas
	}

	@Override
	// le chat attaque continuellent, s'il y a une tour devant lui
	public void attaque(Plateau p) { 
		
		if (p.getCases()[this.getPosX()][this.getPosY()] != null) { // si Chat pas détruit
			if ((this.getPosX() + 1) < p.getHauteur()) {
				Case cible = p.getCases() [this.getPosX() + 1] [this.getPosY()];
		    	if ( (cible != null) && (cible.getEntite() instanceof Tour) ) { // s'il y a une tour devant le chat
					Entite entiteCible = cible.getEntite();
					int nPV = entiteCible.getpV() - this.getAT();
					
					if (nPV > 0 ){
						entiteCible.setpV(nPV);
						System.out.println(this.getNom() + "(x" + (this.getPosX()+1) + "|y" + (this.getPosY()+1) +") attaque -> PV de " + entiteCible.getNom() 
    					+ "(x" + (entiteCible.getPosX()+1) + "|y" + (entiteCible.getPosY()+1) + "): " + nPV);
					}
					else { // si Tour meurt
						System.out.println(entiteCible.getNom() + " a été détruit \n" );
						cible.enleverEntite();
						p.getCases() [this.getPosX() + 1][this.getPosY()]= null; // pour que le deplacement se fasse sans probleme apres la tour meure, etre sûr que la case soit nulle  
						p.afficheTout();
					}
		    	}
			}
		}
	}
	
	public void attaqueContinu(Plateau p_ref) {
		int vitesseAT = this.getVitesseAT();
		Chat chatQuiAT = this; // pour que le chat qui attaque soit visible dans la classe anonyme de type Timer
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	if (chatQuiAT.getPosX() == p_ref.getHauteur()-1) { // s'il est dans la dernière rangée visible du plateau
							cancel();
						}
		            	chatQuiAT.attaque(p_ref);
		            }
		        }, 
		        1000, vitesseAT 
		);
	}
	
	public Chat clone() {
		try {
          	Chat clone = (Chat) super.clone(); // Copie supercielle sufisante car il y a que des objets primitifs dans Chat
            return clone;
            
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
	}
		
	private void deplace(Plateau p) {
		
		if (p.getCases()[this.getPosX()][this.getPosY()] != null) { // si Chat pas détruit
			Chat chat = (Chat) p.getCases()[this.getPosX()][this.getPosY()].getEntite(); // l'objet courant n'est pas forcément l'object contenu dans la case (il a pu prende des degats)
			Chat chatClone = chat.clone();
		
			if (this.getPosX() + 1 <= p.getHauteur()) { 
				Case cible = p.getCases()[chatClone.getPosX()+1][chatClone.getPosY()];
				
				if ((cible == null))  {
					
					chatClone.setPosX(getPosX() + 1);
					p.getCases() [this.getPosX() + 1][getPosY()] = new Case(chatClone);
				    p.getCases() [this.getPosX()][getPosY()].enleverEntite(); // enlever chat de la case où le chat se trouvait
				    this.setPosX(this.getPosX() + 1); // actualiser la posX de this pour que le prochain deplacement marche
				    p.afficheTout();
				}
			}
		}	
	}	
	
	public void deplaceContinu(Plateau p) {
		int vitesseDL = this.vitesseDL;
		Chat chatDansPlateau = this; // pour que la ref de this soit visible dans la classe anonyme de type timer
		setPosX(chatDansPlateau.getPosX());
		setPosY(chatDansPlateau.getPosY());
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
						if ( (chatDansPlateau.getPosX() == 5) ) { // s'il est dans la dernière rangée du plateau
							cancel();
						}
		            	chatDansPlateau.deplace(p);
		            }
		        }, 
		        vitesseDL, vitesseDL 
		);
	}
}
	
