package model;

import enums.ChatEnum;

public class PlateauData {

	private Case [][] cases;
	private int largeur;
	private int hauteur;
	private int vitesseGenerationChat;
	private int argent;
	private int vitesseGenerationArgent;
	private int pointsDeVieMaison = 100; // la maison indique les points de pv du joueur, si ca tombe a 0, le joeur perd (initialise a 100%)
	private int degreAugmentationStatsChats;
	private boolean isModeNormal;
	private int compteurChatElimines = 0;
	private int nombreDeChatsAEliminerAvantVictoireModeNormal;
	// vitesseDeplacementChat sert a garder en memoire la vitesse de deplacement des chats dans le mode marathon 
	// pour pouvoir diminuer sa valeur chaque fois qu'un chat est généré
	private int vitesseDeplacementChat;
	private boolean joeurChoisiAjouterTourRapide = false; // initialise a false pour que l'utilisateur ajoute par defaut une tour normale
	private boolean joeurChoisiAjouterTourNormale = true; // initialise a true pour que l'utilisateur ajoute par defaut une tour normale
	private boolean joeurChoisiAjouterTourBouclier = false; // initialise a false pour que l'utilisateur ajoute par defaut une tour normale
	private boolean joeurChoisiAjouterTourQuiTireAlEnvers = false; // initialise a false pour que l'utilisateur ajoute par defaut une tour normale
	private boolean joeurChoisiAjouterTourAttaqueTousLesChatsDansColonne = false;
	
	public PlateauData(int largeur, int hauteur, int vitesseGenerationChat, int argent, int vitesseGenerationArgent, int degreAugmentationStatsChats, boolean isModeNormal, int nombreDeChatsAEliminerAvantVictoireModeNormal) {
		this.cases = new Case [hauteur][largeur];
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.vitesseGenerationChat = vitesseGenerationChat;
		this.argent = argent;
		this.vitesseGenerationArgent = vitesseGenerationArgent;
		this.degreAugmentationStatsChats = degreAugmentationStatsChats;
		this.isModeNormal = isModeNormal;
		this.vitesseDeplacementChat = ChatEnum.VITESSE_DEPLACEMENT.getValue();
		this.nombreDeChatsAEliminerAvantVictoireModeNormal = nombreDeChatsAEliminerAvantVictoireModeNormal;
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
		return pointsDeVieMaison;
	}

	public void setPvMaison(int pointsDeVieMaison) {
		this.pointsDeVieMaison = pointsDeVieMaison;
	}

	public boolean isGameOver() {
		return (this.getPvMaison() <= 0);
	}
	
	public boolean isVictory() {
		return ( ( this.getCompteurChatElimines() >= (this.nombreDeChatsAEliminerAvantVictoireModeNormal) ) && (this.isModeNormal) ); 
	}


	public int getVitesseGenerationChat() {
		return vitesseGenerationChat;
	}

	public void setVitesseGenerationChat(int vitesseGenChat) {
		this.vitesseGenerationChat = vitesseGenChat;
	}

	public int getVitesseGenerationArgent() {
		return vitesseGenerationArgent;
	}

	public void setVitesseGenerationArgent(int vitesseGenerationArgent) {
		this.vitesseGenerationArgent = vitesseGenerationArgent;
	}

	public int getDegreAugmentationStatsChats() {
		return degreAugmentationStatsChats;
	}

	public void setDegreAugmentationStatsChats(int degreAugmentationStatsChats) {
		this.degreAugmentationStatsChats = degreAugmentationStatsChats;
	}

	public boolean isModeNormal() {
		return isModeNormal;
	}

	public void setModeNormal(boolean isModeNormal) {
		this.isModeNormal = isModeNormal;
	}

	public int getCompteurChatElimines() {
		return compteurChatElimines;
	}

	public void setCompteurChatElimines(int compteurChatElimines) {
		this.compteurChatElimines = compteurChatElimines;
	}

	public int getVitesseDeplacementChat() {
		return vitesseDeplacementChat;
	}

	public void setVitesseDeplacementChat(int vitesseDeplacementChat) {
		this.vitesseDeplacementChat = vitesseDeplacementChat;
	}

	

	public boolean leJoeurChoisitAjouterTourNormale() {
		return joeurChoisiAjouterTourNormale;
	}

	public void setJoeurChoisiAjouterTourNormale(boolean joeurChoisiAjouterTourNormale) {
		this.joeurChoisiAjouterTourNormale = joeurChoisiAjouterTourNormale;
	}

	public boolean leJoeurChoisitAjouterTourBouclier() {
		return joeurChoisiAjouterTourBouclier;
	}

	public void setJoeurChoisiAjouterTourBouclier(boolean joeurChoisiAjouterTourBouclier) {
		this.joeurChoisiAjouterTourBouclier = joeurChoisiAjouterTourBouclier;
	}

	public boolean leJoeurChoisitAjouterTourRapide() {
		return joeurChoisiAjouterTourRapide;
	}

	public void setJoeurChoisiAjouterTourRapide(boolean joeurChoisiAjouterTourRapide) {
		this.joeurChoisiAjouterTourRapide = joeurChoisiAjouterTourRapide;
	}

	public boolean leJoeurChoisiAjouterTourQuiTireAlEnvers() {
		return joeurChoisiAjouterTourQuiTireAlEnvers;
	}

	public void setJoeurChoisiAjouterTourQuiTireAlEnvers(
			boolean joeurChoisiAjouterTourQuiTireAlEnvers) {
		this.joeurChoisiAjouterTourQuiTireAlEnvers = joeurChoisiAjouterTourQuiTireAlEnvers;
	}

	public boolean leJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne() {
		return joeurChoisiAjouterTourAttaqueTousLesChatsDansColonne;
	}

	public void setJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne(
			boolean joeurChoisiAjouterTourAttaqueTousLesChatsDansColonne) {
		this.joeurChoisiAjouterTourAttaqueTousLesChatsDansColonne = joeurChoisiAjouterTourAttaqueTousLesChatsDansColonne;
	}



}
