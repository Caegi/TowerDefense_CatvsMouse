package controller;

import model.Case;
import model.ChatData;
import model.Entite;
import model.Pierre;
import model.PlateauData;
import model.TourData;

public class ChatLogic {
	
	private ChatData chatData;
	
	// initialise chatData dans le constructeur qui stocke les données des Chats
	public ChatLogic(ChatData data) {
		this.chatData = data;
	}
	
	public void attaque(PlateauData plateau) { 
		
		// vérifier si dans la position X et Y courante de l'objet courant Chat this il y a un chat dans le plateau (pour résoudre bug)  
		if ( (plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()] != null) && 
				(plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()].getEntite() instanceof ChatData) ) { 
			
			// si le chat n'est pas dans la dernière rangée du plateau (s'il est dans la dernière rangée, il ne peut rien attaquer)
			if ((this.chatData.getPosX() + 1) < plateau.getHauteur()) {
				
				// cible: la case que le chat veut attaquer
				Case cible = plateau.getCases() [this.chatData.getPosX() + 1] [this.chatData.getPosY()];
				// s'il y a une tour devant le chat
		    	if ( (cible != null) && (cible.getEntite() instanceof TourData) ) { 
					
		    		// entiteCible: tour dans la case que le chat veut attaquer
		    		Entite entiteCible = cible.getEntite();
		    		// nouveauPointsDeVie: les points de vie de la tour après avoir été attaqué
					int nouveauPointsDeVie = entiteCible.getPointsDeVie() - this.chatData.getAttaque();
					
					// si le chat n'est pas mort, actualiser ses points de vie
					if (nouveauPointsDeVie > 0 ){
						entiteCible.setPointsDeVie(nouveauPointsDeVie);
					}
					// si la tour est eliminée, vider la case
					else { 
						plateau.viderCase(this.chatData.getPosX() + 1, this.chatData.getPosY());
					}
		    	}
			}
		}
	}
	
	public void attaqueContinu(PlateauData plateau) {
		int vitesseAttaque = this.chatData.getVitesseAttaque();
		ChatData referenceChatData = this.chatData; // pour que les données du chat soient visibles dans la classe anonyme de type Timer
		ChatLogic referenceChatLogic = this; // pour que la logique du chat soit visible dans la classe anonyme de type Timer
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	// si le chat est dans la dernière rangée du plateau
		            	// ou s'il a ete detruit
		            	// ou s'il y a une tour où le chat devrait être
		            	// -> arreter d'attaquer
		            	if ( (referenceChatData.getPosX() == plateau.getHauteur()-1) ||
		            			(plateau.getCases()[referenceChatData.getPosX()][referenceChatData.getPosY()] == null) ||
		            			(plateau.getCases()[referenceChatData.getPosX()][referenceChatData.getPosY()].getEntite() instanceof TourData)) { 
							cancel();
						}
		            	// si le jeu est terminé: arrêter timer
						if (plateau.isGameOver()) {
							cancel();
						}
		            	referenceChatLogic.attaque(plateau);
		            }
		        }, 
		        1000, vitesseAttaque // après une segonde, attaque toutes les vitesseAttaque millisegondes 
		);
	}
		
	private void deplace(PlateauData plateau) {
		
		// si le chat n'est pas détruit
		if ( !(plateau.caseEstVide( this.chatData.getPosX(), this.chatData.getPosY()) ) && 
				(plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()]).getEntite() instanceof ChatData) {
			
			// obtenir chat dans la position x et y du chat courant car this n'est pas forcément l'object contenu dans la case (il a pu prende des degats)
			ChatData chat = (ChatData) plateau.getCases()[this.chatData.getPosX()][this.chatData.getPosY()].getEntite(); 
			ChatData chatClone = chat.clone();
			
			// si le chat n'est pas dans la dernière rangée du plateau (il ne peut pas se déplacer en dehors du plateau)
			if (this.chatData.getPosX() + 1 < plateau.getHauteur()) { 
				
				// cible: la case où le chat veut se déplacer (la case dans la rangée sous lui)
				Case cible = plateau.getCases()[chatClone.getPosX()+1][chatClone.getPosY()];
				
				// s'il n'y a aucune entite dans la case
				if ((cible == null))  {
					// actualiser la position du chat
					chatClone.setPosX(this.chatData.getPosX() + 1);
					// mettre clone du chat dans le plateau
					plateau.getCases() [this.chatData.getPosX() + 1][this.chatData.getPosY()] = new Case(chatClone); // ajouter clone du chat dans la cible
					// vider la case où le chat était avant son déplacement
				    plateau.viderCase(this.chatData.getPosX(), this.chatData.getPosY()); 
				    this.chatData.setPosX(this.chatData.getPosX() + 1); // actualiser la posX de this pour que le prochain deplacement marche
				}
				
				// s'il y a une pierre dans son chemin,
				if ( (cible != null) && (cible.getEntite() instanceof Pierre) ) {
					// le chat va a droite (au lieu d'aller sur la prochaine rangee, le chat va sur la prochaine colonne à sa droite)
					Case nouvelleCible = plateau.getCases()[chatClone.getPosX()][chatClone.getPosY() + 1];
					
					// s'il n'y a aucune entite dans la casa où le chat veut se déplacer
					if (nouvelleCible == null) {
						// refaire le même processus qu'en haut mais le chat se déplace sur sa droite
						chatClone.setPosY(this.chatData.getPosY() + 1);
						plateau.getCases() [this.chatData.getPosX()][this.chatData.getPosY() + 1] = new Case(chatClone); // ajouter clone du chat dans la cible
					    plateau.viderCase(this.chatData.getPosX(), this.chatData.getPosY());
					    this.chatData.setPosY(this.chatData.getPosY() + 1); // actualiser la posX de this pour que le prochain deplacement marche
					}
				}
			}
			
			// si le chat est dans la dernière rangée du plateau et il veut se déplacer
			else if (this.chatData.getPosX() + 1 == plateau.getHauteur()) {
				
				// si le jeu n'est pas terminé
				if ( !(plateau.isGameOver()) ) {
					// enlever chat
					plateau.viderCase(this.chatData.getPosX(), this.chatData.getPosY());
					// enlever 34 % de points de vie de la maison
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
		        vitesseDeplacement, vitesseDeplacement // après vitesseDeplacement millisegondes, le chat se déplace toutes les vitesseDeplacement millisegondes 
		);
	}
	

}
