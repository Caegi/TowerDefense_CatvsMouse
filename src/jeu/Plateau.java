package jeu;

import java.util.Random;

public class Plateau {

	private Case [][] cases;
	private int largeur;
	private int hauteur;
	private int vitesseGenChat;
	private int argent;
	private int vitesseGenArgent;
	private int pvMaison = 100; // la maison indique les points de pv du joueur, si ca tombe a 0, le joeur perd (initialise a 100%)
	
	public Plateau(int largeur, int hauteur, int vitesseGenChat, int argent, int vitesseGenArgent) {
		this.cases = new Case [hauteur][largeur];
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
		if ( (this.caseEstVide(posX, posY)) && (tour.getCout() <= this.getArgent()) ) {
			getCases()[posX][posY] = new Case(tour);
			Tour tourCree = (Tour) getCases()[posX][posY].getEntite();
			tourCree.attaqueContinu(this);
			this.setArgent(this.getArgent() - tour.getCout());
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
		int vitesseDL = 2000; // 1000 = 1 segonde
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
	
	private void genereArgent() {
		this.setArgent(this.getArgent() + 50);
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

	public int getArgent() {
		return argent;
	}

	public void setArgent(int argent) {
		this.argent = argent;
	}

	public int getPvMaison() {
		return pvMaison;
	}

	public void setPvMaison(int pvMaison) {
		this.pvMaison = pvMaison;
	}

	public boolean isGameOver() {
		return (this.getPvMaison() <= 0);
	}

	
}
