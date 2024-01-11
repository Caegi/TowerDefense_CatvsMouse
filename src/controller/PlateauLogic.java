package controller;

import java.util.Random;

import enums.ChatEnum;
import enums.DefaultTourEnum;
import enums.TourQuiTireAlEnversEnum;
import model.Case;
import model.ChatData;
import model.Pierre;
import model.PlateauData;
import model.TourBouclierData;
import model.TourData;
import model.TourRapideData;

public class PlateauLogic {
	private PlateauData plateauData;
	
	public PlateauLogic(PlateauData data) {
		this.setPlateauData(data);
	}
	
	public void ajouteTour(int posX, int posY) {
		// tour: données de la tour normale / tour par défaut
		TourData tour = new TourData(DefaultTourEnum.POINTS_DE_VIE.getValue(), DefaultTourEnum.ATTAQUE.getValue(), 
				posX, posY, DefaultTourEnum.VITESSE_ATTAQUE.getValue(), DefaultTourEnum.COUT.getValue());
		// tourRapide: données de la tour rapide
		TourRapideData tourRapide = new TourRapideData(DefaultTourEnum.POINTS_DE_VIE.getValue(), DefaultTourEnum.ATTAQUE.getValue(), 
				posX, posY, DefaultTourEnum.VITESSE_ATTAQUE.getValue(), DefaultTourEnum.COUT.getValue());
		// tourBoublier: données de la tour bouclier 
		TourBouclierData tourBouclier = new TourBouclierData(DefaultTourEnum.POINTS_DE_VIE.getValue(), 
				DefaultTourEnum.ATTAQUE.getValue(), posX, posY, DefaultTourEnum.VITESSE_ATTAQUE.getValue(), DefaultTourEnum.COUT.getValue());
		
		// vérifier si la position x et la position y de la tour sont valables (l'utilisateur ne peut pas placer de tour dans la première rangée)
		if ( (posX > 0) && (posX < this.getPlateauData().getHauteur()) && (posY < this.getPlateauData().getLargeur()) ) {
			
			// s'il est possible de mettre une tour dans la case où le joueur veut metttre la tour
			if ( (this.getPlateauData().caseEstVide(posX, posY)) && (tour.getCout() <= this.getPlateauData().getArgent()) ) { 
				
				// ajouter la tour qui tire à l'envers dans le plateau si le joueur a cliqué sur le bouton Tour qui tire à l'envers (package view classe Niveau)
				if (this.getPlateauData().leJoeurChoisiAjouterTourQuiTireAlEnvers()) {
					
					// données de la tour qui tire à l'envers (elle attaque du haut vers la bas au lieu du bas vers le haut)
					tour.setAttaque(TourQuiTireAlEnversEnum.ATTAQUE.getValue());
					tour.setPointsDeVie(TourQuiTireAlEnversEnum.POINTS_DE_VIE.getValue());
					tour.setMaxPointsDeVie(TourQuiTireAlEnversEnum.POINTS_DE_VIE.getValue());
					
					// ajoute la tour dans le plateau
					this.getPlateauData().getCases()[posX][posY] = new Case(tour);
					
					TourData tourCreeData = (TourData) this.getPlateauData().getCases()[posX][posY].getEntite();
					TourQuiTireAlEnversLogic tourCreeLogic = new TourQuiTireAlEnversLogic(tourCreeData);
					// lancer le timer de l'attaque continue de la tour
					tourCreeLogic.attaqueContinu(this.getPlateauData());
					// actualiser l'argent
					this.getPlateauData().setArgent(this.getPlateauData().getArgent() - tour.getCout());
					return; // ne pas faire le reste de la methode
				}
				
				// ajouter la tour qui tire sur tous les chats dans la colonne si le joueur a cliqué sur le bouton Tour qui tire sur tous les chats dans la colonne (package view classe Niveau)
				if (this.getPlateauData().leJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne()) {
					
					// ajoute la tour dans le plateau
					this.getPlateauData().getCases()[posX][posY] = new Case(tour);
					
					TourData tourCreeData = (TourData) this.getPlateauData().getCases()[posX][posY].getEntite();
					TourAttaqueTousLesChatsDansColonneLogic tourCreeLogic = new TourAttaqueTousLesChatsDansColonneLogic(tourCreeData);
					// lancer le timer de l'attaque continue de la tour
					tourCreeLogic.attaqueContinu(this.getPlateauData());
					// actualiser l'argent
					this.getPlateauData().setArgent(this.getPlateauData().getArgent() - tour.getCout());
					return; // ne pas faire le reste de la methode
				}
				
				// ajouter la tour rapide dans le plateau si le joueur a cliqué sur le bouton Tour Rapide (package view classe Niveau)
				else if (this.getPlateauData().leJoeurChoisitAjouterTourRapide()) {
					this.getPlateauData().getCases()[posX][posY] = new Case(tourRapide);
				}
				// ajouter la tour normale dans le plateau si le joueur n'a cliqué sur aucun bouton ou a cliqué sur le bouton Tour Normale (package view classe Niveau)
				else if (this.getPlateauData().leJoeurChoisitAjouterTourNormale()) {
					this.getPlateauData().getCases()[posX][posY] = new Case(tour);
				} 
				// ajouter la tour normale dans le plateau si le joueur a cliqué sur le bouton Tour Bouclier (package view classe Niveau) 
				else if (this.getPlateauData().leJoeurChoisitAjouterTourBouclier()) {
					this.getPlateauData().getCases()[posX][posY] = new Case(tourBouclier);
				}
				TourData tourCreeData = (TourData) this.getPlateauData().getCases()[posX][posY].getEntite();
				TourLogic tourCreeLogic = new TourLogic(tourCreeData);
				// lancer le timer de l'attaque continue de la tour
				tourCreeLogic.attaqueContinu(this.getPlateauData());
				this.getPlateauData().setArgent(this.getPlateauData().getArgent() - tour.getCout());
			}
		}	
	}
	
	public void ajoutePierre(int posX, int posY) {
		Pierre pierre = new Pierre(0, 0, posX, posY, 0);
		// mettre Pierre dans plateau
		this.getPlateauData().getCases()[posX][posY] = new Case(pierre);
	}
	
	public void genereChatContinu() {
		PlateauLogic reference_plateau = this; // pour que la reference du plateau soit visible dans la classe anonyme Timer
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	reference_plateau.genereChat();
		            	
		            	// si le jeu est au mode normal et que le nombre de chats a generer avant la victoire a ete atteint
		            	// -> arreter le jeu: le joeur a gagne
		            	if (reference_plateau.getPlateauData().isVictory()) {
		            		cancel();
		            	}
		            	if (reference_plateau.getPlateauData().isGameOver()) {
		            		cancel();
		            	}
		            	
		            }
		        }, 
		        1000, reference_plateau.getPlateauData().getVitesseGenerationChat() 
		);
		
	}
	
	public void genereArgentContinu() {
		PlateauLogic reference_plateau = this; // pour que la reference du plateau soit visible dans la classe anonyme Timer
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	reference_plateau.genereArgent();
						if (reference_plateau.getPlateauData().isGameOver()) {
							cancel();
						}
		            }	
		        }, 
		        this.plateauData.getVitesseGenerationArgent(), this.getPlateauData().getVitesseGenerationArgent()  
		);
	
	}
	
	private void genereArgent() {
		this.getPlateauData().setArgent(this.getPlateauData().getArgent() + 50);
	}
	
	private void genereChat() {
		
		// mettre chat dans une des sources de chat
		Random rand = new Random();
		int posY = rand.nextInt(this.plateauData.getLargeur()); // genere un nombre au hazard entre 0 et la largeur du plateau (non inclu)
		int posX = 0; // toujours fixé à zero car les chats sont toujours générés dans la premiere rangée
		
		if (this.getPlateauData().caseEstVide(posX, posY)) {
			// données du chat 
			int pointsDeVie = ChatEnum.POINTS_DE_VIE.getValue() * this.getPlateauData().getDegreAugmentationStatsChats();
			int attaque = ChatEnum.ATTAQUE.getValue() * this.getPlateauData().getDegreAugmentationStatsChats();
			int vitesseAttaque = ChatEnum.VITESSE_ATTAQUE.getValue();
			
			// mode normal: la vitesse de deplacement reste la même
			int vitesseDeplacement = this.plateauData.getVitesseDeplacementChat();
			
			// mode marathon: la vitesse de deplacement des chats diminue en fonction du temps
			// la vitesse maximale d'un chat est d'un deplacement toutes les 0.5 segondes (deuxième condition)
			if ( !(this.plateauData.isModeNormal()) && (this.plateauData.getVitesseDeplacementChat() > 500)) {
				vitesseDeplacement = this.plateauData.getVitesseDeplacementChat() - 100; // les chats crées se deplacent 0.1 segondes plus rapidement
				this.plateauData.setVitesseDeplacementChat(vitesseDeplacement);
			}
			// initialiser chat et le mettre dans le plateau
			ChatData chat = new ChatData(pointsDeVie, attaque, posX, posY, vitesseAttaque, vitesseDeplacement); 			
			this.getPlateauData().getCases() [0][posY] = new Case(chat);	
			ChatData chatGenereData = (ChatData) this.getPlateauData().getCases() [0][posY].getEntite();
			ChatLogic chatGenereLogic = new ChatLogic(chatGenereData);
			// le nouveau chat crée attaque et se déplace continuellement, s'il peut, à partir du moment où il a été crée
			chatGenereLogic.attaqueContinu(this.getPlateauData()); 
			chatGenereLogic.deplaceContinu(this.getPlateauData());
		}
	}

	public PlateauData getPlateauData() {
		return plateauData;
	}

	public void setPlateauData(PlateauData plateauData) {
		this.plateauData = plateauData;
	}
}
