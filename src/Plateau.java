import java.util.Random;

public class Plateau {

	private Case [][] cases;
	private int largeur;
	private int hauteur;
	private int vitesseGenChat;
	private int argent;
	private int vitesseGenArgent;
	
	public Plateau(int largeur, int hauteur, int vitesseGenChat, int argent, int vitesseGenArgent) {
		this.setCases(new Case [hauteur+1][largeur]);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.vitesseGenChat = vitesseGenChat;
		this.argent = argent;
		this.vitesseGenArgent = vitesseGenArgent;
	}
	
	public void remplirCasesVides() {
		for (int iRow = 0; iRow<this.getLargeur(); iRow++){
			System.out.println("");
			
			// itérer sur les colonnes du plateau
			for (int iCol = 0; iCol<this.getHauteur(); iCol++){
				getCases()[iRow][iCol] = new Case(null);
			}
			
		}
	}
	
	public void ajouteTour(int posX, int posY) {
		
		int pV = 70;
		int aT = 20;
		int vitesseAT = 6000;
		int cout = 50;
		Tour tour = new Tour(pV, aT, posX, posY, "T", vitesseAT, cout);
		
		if ((getCases()[posX][posY] == null) && (tour.getCout() <= this.argent) ) {
			getCases()[posX][posY] = new Case(tour);
			Tour tourCree = (Tour) getCases()[posX][posY].getEntite();
			tourCree.attaqueContinu(this);
			this.argent = this.argent - tour.getCout();
		}
		else if ((getCases()[posX][posY] != null)) { // s'il y a une entite dans la case
			System.out.println("Impossible, il y a " + getCases()[posX][posY].getEntite().getNom() + " dans la case");
		}
		else { // si le joueur n'a pas assez d'argent pour acheter la tour
			System.out.println("Vous êtes trop pauvre");
		}
	}
	
	private void genereChat() {
		boolean rangeePasRemplie = ( (this.getCases()[0][0] == null) || (this.getCases()[0][1] == null) || (this.getCases()[0][2] == null) || (this.getCases()[0][3] == null) || (this.getCases()[0][4] == null));
		if (rangeePasRemplie) { // s'il y a une case vide dans la première rangée
			
			// le mettre dans une des 5 sources de chat
			Random rand = new Random();
			int posY = rand.nextInt(5);
			int posX = 0;
			int vitesseAT = 2500; // 1000 = 1 segonde
			int vitesseDL = 6000; // 1000 = 1 segonde
			
			if (this.getCases()[posX][posY] == null) { 
				Chat chat = new Chat(100, 15, posX, posY, vitesseAT, ("C"), vitesseDL); 
				this.getCases() [0][posY] = new Case(chat);	
				
				Chat chatDansPlateau = (Chat) this.getCases() [0][posY].getEntite();
				Plateau p_ref = this; // pour que la plateau soit visible dans deplaceContinu
				this.afficheTout();
				
				chatDansPlateau.attaqueContinu(p_ref); // le nouveau chat crée attaque et se déplace continuellement, s'il peut, à partir du moment où il a été crée
				chatDansPlateau.deplaceContinu(p_ref); 
			}
			else { // s'il y avait déja une entité sur la case, essayer une autre
				this.genereChat();
			}
		}
	}
	
	public void genereChatContinu() {
		
		Plateau ref_p = this; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	ref_p.genereChat();
		            }
		        }, 
		        1000, vitesseGenChat 
		);
	}
	
	public void afficheTout() {
		// itérer sur les rangées du plateau
		System.out.println("\n Argent: " + this.argent);
		for (int iRow = 0; iRow<this.getLargeur(); iRow++){
			System.out.println();
			// itérer sur les colonnes du plateau
			for (int iCol = 0; iCol<this.getHauteur(); iCol++){
				if (getCases()[iRow][iCol] != null) {
					System.out.print( getCases()[iRow][iCol].toString() );
				}
				else {
					System.out.print(" . ");
				}
			}
			
		}
		System.out.print(" \n      /\\ \n     /  \\\n    /----\\\n   / |  | \\\n   | |  | |"); // affiche la maison de souris
		System.out.println("\n");
	}
	
	private void genereArgent() {
		this.argent = this.argent + 50;
	}
	
	public void genereArgentContinu() {
		
		Plateau ref_p = this; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	ref_p.genereArgent();
		            }
		        }, 
		        vitesseGenArgent, vitesseGenArgent  
		);
	
	}
	
	public void afficheContinu() {
		
		Plateau ref_p = this; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	ref_p.genereArgent();
		            }
		        }, 
		        100, 1000  
		);
	
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public Case [][] getCases() {
		return cases;
	}

	public void setCases(Case [][] cases) {
		this.cases = cases;
	}
	
}
