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
		TourData tour = new TourData(DefaultTourEnum.POINTS_DE_VIE.getValue(), DefaultTourEnum.ATTAQUE.getValue(), posX, posY, DefaultTourEnum.VITESSE_ATTAQUE.getValue(), DefaultTourEnum.COUT.getValue());
		TourRapideData tourRapide = new TourRapideData(DefaultTourEnum.POINTS_DE_VIE.getValue(), DefaultTourEnum.ATTAQUE.getValue(), posX, posY, DefaultTourEnum.VITESSE_ATTAQUE.getValue(), DefaultTourEnum.COUT.getValue());
		TourBouclierData tourBouclier = new TourBouclierData(DefaultTourEnum.POINTS_DE_VIE.getValue(), DefaultTourEnum.ATTAQUE.getValue(), posX, posY, DefaultTourEnum.VITESSE_ATTAQUE.getValue(), DefaultTourEnum.COUT.getValue());
		
		if ( (posX > 1) && (posX < this.getPlateauData().getHauteur()) && (posY < this.getPlateauData().getLargeur()) ) {
			
			if ( (this.getPlateauData().caseEstVide(posX, posY)) && (tour.getCout() <= this.getPlateauData().getArgent()) ) { 
				
				// si le joeur veut creer une Tour qui Tire a l'envers
				if (this.getPlateauData().leJoeurChoisiAjouterTourQuiTireAlEnvers()) {
					tour.setAttaque(TourQuiTireAlEnversEnum.ATTAQUE.getValue());
					tour.setPointsDeVie(TourQuiTireAlEnversEnum.POINTS_DE_VIE.getValue());
					tour.setMaxPointsDeVie(TourQuiTireAlEnversEnum.POINTS_DE_VIE.getValue());
					this.getPlateauData().getCases()[posX][posY] = new Case(tour);
					TourData tourCreeData = (TourData) this.getPlateauData().getCases()[posX][posY].getEntite();
					TourAttaqueDernierChatColoneEnPremierLogic tourCreeLogic = new TourAttaqueDernierChatColoneEnPremierLogic(tourCreeData);
					tourCreeLogic.attaqueContinu(this.getPlateauData());
					this.getPlateauData().setArgent(this.getPlateauData().getArgent() - tour.getCout());
					return; // ne pas faire le reste de la methode
				}
				
				else if (this.getPlateauData().leJoeurChoisiAjouterTourRapide()) {
					this.getPlateauData().getCases()[posX][posY] = new Case(tourRapide);
				}
				
				else if (this.getPlateauData().leJoeurChoisiAjouterTourNormale()) {
					this.getPlateauData().getCases()[posX][posY] = new Case(tour);
				}
				
				else if (this.getPlateauData().leJoeurChoisiAjouterTourBouclier()) {
					this.getPlateauData().getCases()[posX][posY] = new Case(tourBouclier);
				}
				
				TourData tourCreeData = (TourData) this.getPlateauData().getCases()[posX][posY].getEntite();
				TourLogic tourCreeLogic = new TourLogic(tourCreeData);
				tourCreeLogic.attaqueContinu(this.getPlateauData());
				this.getPlateauData().setArgent(this.getPlateauData().getArgent() - tour.getCout());
			}
		}	
	}
	
	public void ajoutePierre(int posX, int posY) {
		Pierre pierre = new Pierre(0, 0, posX, posY, 0);
		this.getPlateauData().getCases()[posX][posY] = new Case(pierre);
	}
	
	public void genereChatContinu() {
		PlateauLogic reference_plateau = this; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		
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
		PlateauLogic reference_plateau = this; // pour que la ref du plateau soit visible dans la classe anonyme Timer
		
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
			int pointsDeVie = ChatEnum.POINTS_DE_VIE.getValue() * this.getPlateauData().getDegreAugmentationStatsChats();
			int attaque = ChatEnum.ATTAQUE.getValue() * this.getPlateauData().getDegreAugmentationStatsChats();
			int vitesseAttaque = ChatEnum.VITESSE_ATTAQUE.getValue();
			
			// mode normal: la vitesse de deplacement reste la même
			int vitesseDeplacement = this.plateauData.getVitesseDeplacementChat();
			
			// mode marathon: la vitesse de deplacement des chats diminue en fonction du temps
			// la vitesse maximale d'un chat est d'un deplacement toutes les 0.5 segondes (deuxième condition)
			if ( !(this.plateauData.isModeNormal()) && (this.plateauData.getVitesseDeplacementChat() > 500)) {
				vitesseDeplacement = this.plateauData.getVitesseDeplacementChat() - 100; // les chats crées se deplacent 0.5 segondes plus rapidement
				this.plateauData.setVitesseDeplacementChat(vitesseDeplacement);
			}
			
			ChatData chat = new ChatData(pointsDeVie, attaque, posX, posY, vitesseAttaque, vitesseDeplacement); 			
			this.getPlateauData().getCases() [0][posY] = new Case(chat);	
			ChatData chatGenereData = (ChatData) this.getPlateauData().getCases() [0][posY].getEntite();
			ChatLogic chatGenereLogic = new ChatLogic(chatGenereData);
			chatGenereLogic.attaqueContinu(this.getPlateauData()); // le nouveau chat crée attaque et se déplace continuellement, s'il peut, à partir du moment où il a été crée
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
