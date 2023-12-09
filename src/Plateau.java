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
	
	public void ajouteTour(int posX, int posY) {
		int pV = 70;
		int aT = 20;
		int vitesseAT = 6000;
		int cout = 50;
		Tour tour = new Tour(pV, aT, posX, posY, "T", vitesseAT, cout);
		if ( (this.caseEstVide(posX, posY)) && (tour.getCout() <= this.argent) ) {
			getCases()[posX][posY] = new Case(tour);
			Tour tourCree = (Tour) getCases()[posX][posY].getEntite();
			tourCree.attaqueContinu(this);
			this.argent = this.argent - tour.getCout();
		}
		else if ( !(this.caseEstVide(posX, posY)) ) { // s'il y a une entite dans la case
			System.out.println("Impossible, il y a " + getCases()[posX][posY].getEntite().getNom() + " dans la case");
		}
		else { // si le joueur n'a pas assez d'argent pour acheter la tour
			System.out.println("Vous êtes trop pauvre");
		}
	}
	
	private void genereChat() {
		// mettre chat dans une des 5 sources de chat
		Random rand = new Random();
		int posY = rand.nextInt(5);
		int posX = 0;
		int vitesseAT = 2500; // 1000 = 1 segonde
		int vitesseDL = 7000; // 1000 = 1 segonde
		if (this.caseEstVide(posX, posY)) { 
			Chat chat = new Chat(100, 15, posX, posY, vitesseAT, ("C"), vitesseDL); 
			this.getCases() [0][posY] = new Case(chat);	
			Chat chatDansPlateau = (Chat) this.getCases() [0][posY].getEntite();
			chatDansPlateau.attaqueContinu(this); // le nouveau chat crée attaque et se déplace continuellement, s'il peut, à partir du moment où il a été crée
			chatDansPlateau.deplaceContinu(this); 
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
				if ( !(this.caseEstVide(iRow, iCol)) ) {
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
		            	ref_p.afficheTout();
		            }
		        }, 
		        100, 3000  // affiche le plateau toutes les 3 segondes
		);
	
	}
	
	public void viderCase(int posX, int posY) {
		this.cases[posX][posY] = null;
	}
	
	public boolean caseEstVide(int posX, int posY) {
		return (this.cases[posX][posY] == null);
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
