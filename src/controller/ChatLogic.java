package controller;

import model.Case;
import model.ChatData;
import model.Entite;
import model.Pierre;
import model.PlateauData;
import model.TourData;

public class ChatLogic {
	
	private ChatData chatData;
	
	public ChatLogic(ChatData data) {
		this.chatData = data;
	}
	
	// le chat attaque continuellent, s'il y a une tour devant lui
	public void attaque(PlateauData plateau) { 
		
		// si Entite dans la case est un Chat et il n'a pas été détruit
		if ( (plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()] != null) && 
				(plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()].getEntite() instanceof ChatData) ) { 
			
			if ((this.chatData.getPosX() + 1) < plateau.getHauteur()) {
				
				Case cible = plateau.getCases() [this.chatData.getPosX() + 1] [this.chatData.getPosY()];
		    	if ( (cible != null) && (cible.getEntite() instanceof TourData) ) { // s'il y a une tour devant le chat
					
		    		Entite entiteCible = cible.getEntite();
					int nouveauPointsDeVie = entiteCible.getPointsDeVie() - this.chatData.getAttaque();
					
					if (nouveauPointsDeVie > 0 ){
						entiteCible.setPointsDeVie(nouveauPointsDeVie);
					}
					
					else { // si Tour meurt
						plateau.viderCase(this.chatData.getPosX() + 1, this.chatData.getPosY());
					}
		    	}
			}
		}
	}
	
	public void attaqueContinu(PlateauData plateau) {
		int vitesseAT = this.chatData.getVitesseAttaque();
		ChatData referenceChatData = this.chatData; // pour que le chat qui attaque soit visible dans la classe anonyme de type Timer
		ChatLogic referenceChatLogic = this;
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	// s'il est dans la dernière rangée du plateau
		            	// ou s'il a ete detruit
		            	// ou s'il y a une tour où le chat devrait être
		            	// -> arreter d'attaquer
		            	if ( (referenceChatData.getPosX() == plateau.getHauteur()-1) ||
		            			(plateau.getCases()[referenceChatData.getPosX()][referenceChatData.getPosY()] == null) ||
		            			(plateau.getCases()[referenceChatData.getPosX()][referenceChatData.getPosY()].getEntite() instanceof TourData)) { 
							cancel();
						}
						if (plateau.isGameOver()) {
							cancel();
						}
		            	referenceChatLogic.attaque(plateau);
		            }
		        }, 
		        1000, vitesseAT 
		);
	}
		
	private void deplace(PlateauData plateau) {
		
		// si Chat pas détruit
		if ( !(plateau.caseEstVide( this.chatData.getPosX(), this.chatData.getPosY()) ) && 
				(plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()]).getEntite() instanceof ChatData) {
			
			// l'objet courant n'est pas forcément l'object contenu dans la case (il a pu prende des degats)
			ChatData chat = (ChatData) plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()].getEntite(); 
			ChatData chatClone = chat.clone();
			
			if (this.chatData.getPosX() + 1 < plateau.getHauteur()) { 
				
				Case cible = plateau.getCases()[chatClone.getPosX()+1][chatClone.getPosY()];
				if ((cible == null))  {
					
					chatClone.setPosX(this.chatData.getPosX() + 1);
					plateau.getCases() [this.chatData.getPosX() + 1][this.chatData.getPosY()] = new Case(chatClone); // ajouter clone du chat dans la cible
				    plateau.viderCase(this.chatData.getPosX(), this.chatData.getPosY());
				    this.chatData.setPosX(this.chatData.getPosX() + 1); // actualiser la posX de this pour que le prochain deplacement marche
				}
				
				// s'il y a une pierre dans son chemin,
				if ( (cible != null) && (cible.getEntite() instanceof Pierre) ) {
					// le chat va a droite
					Case nouvelleCible = plateau.getCases()[chatClone.getPosX()][chatClone.getPosY() + 1];
					
					if (nouvelleCible == null) {
						chatClone.setPosY(this.chatData.getPosY() + 1);
						plateau.getCases() [this.chatData.getPosX()][this.chatData.getPosY() + 1] = new Case(chatClone); // ajouter clone du chat dans la cible
					    plateau.viderCase(this.chatData.getPosX(), this.chatData.getPosY());
					    this.chatData.setPosY(this.chatData.getPosY() + 1); // actualiser la posX de this pour que le prochain deplacement marche
					}
				}
			}
			
			else if (this.chatData.getPosX() + 1 == plateau.getHauteur()) {
				
				// si le jeu n'est pas termine
				if ( !(plateau.isGameOver()) ) {
					
					// enlever chat
					plateau.viderCase(this.chatData.getPosX(), this.chatData.getPosY());
					// enlever 34 % de PV de la maison
					plateau.setPvMaison(plateau.getPvMaison() - 34);
				}
			}
		}
		
	}	
	
	public void deplaceContinu(PlateauData plateau) {
		int vitesseDeplacement = this.chatData.getVitesseDeplacement();
		ChatData referenceChatData = this.chatData; // pour que ChatData de this soit visible dans la classe anonyme de type timer
		ChatLogic referenceChatLogic = this;
		this.chatData.setPosX(referenceChatData.getPosX());
		this.chatData.setPosY(referenceChatData.getPosY());
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	// s'il est dans la dernière rangée du plateau (elle n'est pas visible par le joueur)
		              	// ou s'il a ete detruit
		            	// -> arreter de se deplacer
						if ( (referenceChatData.getPosX() == plateau.getHauteur()) || 
								(plateau.getCases()[referenceChatData.getPosX()][referenceChatData.getPosY()] == null) ) { 
							cancel();
						}
						if (plateau.isGameOver()) {
							cancel();
						}
						referenceChatLogic.deplace(plateau);
		            }
		        }, 
		        vitesseDeplacement, vitesseDeplacement 
		);
	}
	

}
