package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.PlateauLogic;
import model.ChatData;
import model.Pierre;
import model.PlateauData;
import model.TourData;

public class NiveauAvecPierre extends Niveau {
	private ArrayList<ArrayList<Integer>> listePierres;
	
	public NiveauAvecPierre(ArrayList<ArrayList<Integer>> listePierres) {
		this.setPanelGeneral(new JPanel(new BorderLayout()));
		this.setPanelLabels(new JPanel());
		this.listePierres = listePierres;
	}
	
	@Override
    public void afficheJeu(JFrame frame) {
		PlateauData plateauData = new PlateauData(this.getHauteur(), this.getLargeur(), this.getVitesseGenerationChat(), this.getArgent(), this.getVitesseGenerationArgent(), this.getDegreAugmentationStatsChats(), this.isModeNormal(), this.getNombreDeChatsGenererAvantVictoireModeNormal());
		this.setPlateauLogic(new PlateauLogic(plateauData));
		this.getPlateauLogic().genereChatContinu();
		this.getPlateauLogic().genereArgentContinu();
		ajoutePierresDansPlateau();
		
		fairePanelJeu();
        this.getPanelGeneral().add(getPanelJeu(), BorderLayout.CENTER);
        
        this.getPanelLabels().setLayout(new BoxLayout(this.getPanelLabels(), BoxLayout.Y_AXIS));
        
        fairePanelLabelsEtBoutonsTours();
        this.getPanelGeneral().add(this.getPanelLabels(), BorderLayout.SOUTH);
        
        // ajouter une tour
        this.getPanelJeu().addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Calculer la cellule qui a été cliquée
                int posX = e.getX() / getTailleCase();
                int posY = e.getY() / getTailleCase();
                
                getPlateauLogic().ajouteTour(posY, posX);
            }
        });
        
        // 330: taille du reste des elements a afficher (l'image de la maison, de la souris, les deux textes et les boutons)
        this.getPanelGeneral().setPreferredSize(new Dimension(this.getTailleCase() * this.getHauteur(), this.getTailleCase() * this.getLargeur() + 330));
        
        frame.add(this.getPanelGeneral());
        frame.pack(); // option pour l'affichage des elements dans panel soit faite correctement
        this.actualiserIgPlateauContinu(frame);    }
	
	@ Override
	public void fairePanelJeu() {
		this.setPanelJeu(new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int iRow = 0; iRow < getHauteur(); iRow++) {
                    for (int iCol = 0; iCol < getLargeur(); iCol++) {
                        
                        int x = iRow * getTailleCase();
                        int y = iCol * getTailleCase();

                        g.drawRect(x, y, getTailleCase(), getTailleCase());
                        
                        if (getPlateauLogic().getPlateauData().getCases()[iCol][iRow] != null) {
                        	
                        	
                        	// mettre image de la TourBouclier dans la case
                        	if ( (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof TourData) && 
                        			(getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getAttaque() == 10) ) { // au contraire de la tour normale, la tourBouclier a une attaque de 0
                        		int dimensionTour = ((getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getPointsDeVie() * getTailleCase()) 
                        				/ getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getMaxPointsDeVie()); // taille de l'image depend des points de pv
                        		if (dimensionTour > 0) {
                        			Image imageTourRedimensione = getImageTourQuiTireAlEnvers().getImage().getScaledInstance(dimensionTour, dimensionTour, Image.SCALE_SMOOTH);
                        		    ImageIcon iconeTourRedimensione = new ImageIcon(imageTourRedimensione);
                                      
                              		// Calculer le centre pour un affichage centré
                                      int centreX = x + (getTailleCase() - iconeTourRedimensione.getIconWidth()) / 2;
                                      int centreY = y + (getTailleCase() - iconeTourRedimensione.getIconHeight()) / 2;
                              		
                              		iconeTourRedimensione.paintIcon(this, g, centreX, centreY);
                        		}
                        	}
                        	
                        	// mettre image de la TourBouclier dans la case
                        	else if ( (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof TourData) && 
                        			(getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getAttaque() == 0) ) { // au contraire de la tour normale, la tourBouclier a une attaque de 0
                        		int dimensionTour = ((getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getPointsDeVie() * getTailleCase()) 
                        				/ getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getMaxPointsDeVie()); // taille de l'image depend des points de pv
                        		if (dimensionTour > 0) {
                        			Image imageTourRedimensione = getImageTourBouclier().getImage().getScaledInstance(dimensionTour, dimensionTour, Image.SCALE_SMOOTH);
                        		    ImageIcon iconeTourRedimensione = new ImageIcon(imageTourRedimensione);
                                      
                              		// Calculer le centre pour un affichage centré
                                      int centreX = x + (getTailleCase() - iconeTourRedimensione.getIconWidth()) / 2;
                                      int centreY = y + (getTailleCase() - iconeTourRedimensione.getIconHeight()) / 2;
                              		
                              		iconeTourRedimensione.paintIcon(this, g, centreX, centreY);
                        		}
                        	}
                        	
                        	// mettre image de la TourRapide dans la case
                        	else if ( (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof TourData) && 
                        			(getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getAttaque() == 1) ) { // au contraire de la tour normale, la tour qui attaque a une attaque de 1 au lieu de 6
                        		int dimensionTour = ((getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getPointsDeVie() * getTailleCase()) 
                        				/ getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getMaxPointsDeVie()); // taille de l'image depend des points de pv
                        		if (dimensionTour > 0) {
                        			Image imageTourRedimensione = getImageTourRapide().getImage().getScaledInstance(dimensionTour, dimensionTour, Image.SCALE_SMOOTH);
                        		    ImageIcon iconeTourRedimensione = new ImageIcon(imageTourRedimensione);
                                      
                              		// Calculer le centre pour un affichage centré
                                      int centreX = x + (getTailleCase() - iconeTourRedimensione.getIconWidth()) / 2;
                                      int centreY = y + (getTailleCase() - iconeTourRedimensione.getIconHeight()) / 2;
                              		
                              		iconeTourRedimensione.paintIcon(this, g, centreX, centreY);
                        		}
                        	}
                        	
                        	
                        	// mettre image de la Tour dans la case
                        	else if (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof TourData) {
                        		int dimensionTour = ((getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getPointsDeVie() * getTailleCase()) 
                        				/ getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getMaxPointsDeVie()); // taille de l'image depend des points de pv
                        		if (dimensionTour > 0) {
                        			Image imageTourRedimensione = getImageTour().getImage().getScaledInstance(dimensionTour, dimensionTour, Image.SCALE_SMOOTH);
                        			ImageIcon iconeTourRedimensione = new ImageIcon(imageTourRedimensione);
                                    
                            		// Calculer le centre pour un affichage centré
                                    int centreX = x + (getTailleCase() - iconeTourRedimensione.getIconWidth()) / 2;
                                    int centreY = y + (getTailleCase() - iconeTourRedimensione.getIconHeight()) / 2;
                            		
                            		iconeTourRedimensione.paintIcon(this, g, centreX, centreY);
                        		}
                        	}
                        	
                        	// mettre image du Chat dans la case
                        	if (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof ChatData) {
                        		int dimensionChat = ((getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getPointsDeVie() * getTailleCase()) / getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getMaxPointsDeVie()); // taille de l'image depend des points de vie
                        		if (dimensionChat > 0) {
                        			Image imageChatRedimensione = getImageChat().getImage().getScaledInstance(dimensionChat, dimensionChat, Image.SCALE_SMOOTH);
                        			ImageIcon iconeChatRedimensione = new ImageIcon(imageChatRedimensione);
                                    
                            		// Calculer le centre pour un affichage centré
                                    int centreX = x + (getTailleCase() - iconeChatRedimensione.getIconWidth()) / 2;
                                    int centreY = y + (getTailleCase() - iconeChatRedimensione.getIconHeight()) / 2;
                            		
                            		iconeChatRedimensione.paintIcon(this, g, centreX, centreY);
                        		}
                        	}

                        	
                        	// mettre image de la Pierre dans la case
                        	else if (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof Pierre) {
                                ImageIcon imagePierre = new ImageIcon("Rock.png");
                                
                        		// Calculer le centre pour un affichage centré
                                int centreX = x + (getTailleCase() - imagePierre.getIconWidth()) / 2;
                                int centreY = y + (getTailleCase() - imagePierre.getIconHeight()) / 2;
                        		
                                imagePierre.paintIcon(this, g, centreX, centreY);
                        	}
                        }
                    }
                }
            }
        });
	}

	public void ajoutePierresDansPlateau() {
		// listePosXPosY a 2 elements: un int posX et autre int posY
		for (ArrayList<Integer> listePosXPosY : this.listePierres) {
			int posX = listePosXPosY.get(0);
			int posY = listePosXPosY.get(1);
			this.getPlateauLogic().ajoutePierre(posX, posY); // ajoute une pierre dans la troisieme rangee et deuxieme colonne du plateau
		}
	}
	
}
