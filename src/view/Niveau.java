package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.PlateauLogic;
import enums.Niveau1Enum;
import enums.TourAttaqueTousLesChatsDansColonneEnum;
import enums.TourBouclierEnum;
import enums.TourQuiTireAlEnversEnum;
import enums.TourRapideEnum;
import model.ChatData;
import model.PlateauData;
import model.TourData;

public class Niveau {

    private PlateauLogic plateauLogic;
	private JPanel panelJeu;
	private JPanel panelGeneral;
	private JPanel panelLabels;
    private int vitesseGenerationChat = Niveau1Enum.VITESSE_GENERATION_CHAT.getValue(); 
    private int argent = Niveau1Enum.ARGENT.getValue(); // argent au début de la partie
    private int hauteur = Niveau1Enum.HAUTEUR.getValue();
	private int largeur = Niveau1Enum.LARGEUR.getValue();
	private int vitesseGenerationArgent = Niveau1Enum.VITESSE_GENERATION_ARGENT.getValue();
	private int nombreDeChatsAEliminerAvanttVictoireModeNormal = Niveau1Enum.NOMBRE_CHAT_A_ELIMINER_AVANT_VICTOIRE_MODE_NORMAL.getValue();
    private ImageIcon imageTour = new ImageIcon("Tour.png");
    private ImageIcon imageChat = new ImageIcon("Cat.png");
    private ImageIcon imageMaison = new ImageIcon("MaisonSouris.png");
    private ImageIcon imageTourRapide = new ImageIcon("TourRapide.png");
    private ImageIcon imageTourBouclier = new ImageIcon("Bouclier.png");
    private ImageIcon imageTourQuiTireAlEnvers = new ImageIcon("TourQuiTireAlEnvers.png");
    private ImageIcon imageTourAttaqueTousLesChatsDansColonne = new ImageIcon("TourAttaqueTousLesChatsDansColonne.png");
	private final int tailleCase = 55; // indique les pixels de la largeur et la hauteur d'une case du plateau
	// degreAugmentationStatsChats: les points de vie et l'attaque des chats est definie en fonction de la difficulte choisie par l'utilisateur (1 facile, 2 moyen, 3 difficile, voir enumDifficulte)
	private int degreAugmentationStatsChats;
	private boolean isModeNormal;
	
	public Niveau() {
		this.setPanelGeneral(new JPanel(new BorderLayout()));
		this.setPanelLabels(new JPanel());
	}
	
	public void afficheJeu(JFrame frame) {
		
		PlateauData plateauData = new PlateauData(this.getHauteur(), this.getLargeur(), this.getVitesseGenerationChat(), this.getArgent(), this.getVitesseGenerationArgent(), this.getDegreAugmentationStatsChats(), this.isModeNormal, this.getNombreDeChatsGenererAvantVictoireModeNormal());
		this.setPlateauLogic(new PlateauLogic(plateauData));
		this.getPlateauLogic().genereChatContinu();
		this.getPlateauLogic().genereArgentContinu();
		
		fairePanelJeu();
        this.getPanelGeneral().add(getPanelJeu(), BorderLayout.CENTER);
        
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
        
        // redimentioner la fenetre
        // 345: taille du reste des elements a afficher (l'image de la maison, de la souris, les deux textes et les boutons)
        this.getPanelGeneral().setPreferredSize(new Dimension(this.getTailleCase() * this.getHauteur(), this.getTailleCase() * this.getLargeur() + 345));
        frame.add(this.getPanelGeneral());
        
        frame.getContentPane().setBackground(new Color(139, 69, 19));
        frame.pack(); // option pour l'affichage des elements dans panel soit faite correctement
        this.actualiserIgPlateauContinu(frame);
	}

	public void fairePanelLabelsEtBoutonsTours() {
		// faire que les elements Component s'empilent les uns sur les autres dans l'axe y
		this.getPanelLabels().setLayout(new BoxLayout(this.getPanelLabels(), BoxLayout.Y_AXIS));
        
        JLabel houseLabel = new JLabel(this.getImageMaison());
        this.getPanelLabels().add(houseLabel);
        
        JButton boutonTourNormale = new JButton("Ajouter Tour Normale");
        this.getPanelLabels().add(boutonTourNormale);
        boutonTourNormale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourNormale(true);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourRapide(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourBouclier(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourQuiTireAlEnvers(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne(false);
            }
        });
        
        JButton boutonTourRapide = new JButton("Ajouter Tour Rapide");
        this.getPanelLabels().add(boutonTourRapide);
        boutonTourRapide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	plateauLogic.getPlateauData().setJoeurChoisiAjouterTourNormale(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourRapide(true);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourBouclier(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourQuiTireAlEnvers(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne(false);
            }
        });
        
        JButton boutonTourBouclier = new JButton("Ajouter Tour Bouclier");
        this.getPanelLabels().add(boutonTourBouclier);
        boutonTourBouclier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	plateauLogic.getPlateauData().setJoeurChoisiAjouterTourNormale(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourRapide(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourBouclier(true);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourQuiTireAlEnvers(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne(false);
            }
        });
        
        JButton boutonTourQuiTireAlEnvers = new JButton("Ajouter Tour qui tire à l'envers");
        this.getPanelLabels().add(boutonTourQuiTireAlEnvers);
        boutonTourQuiTireAlEnvers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	plateauLogic.getPlateauData().setJoeurChoisiAjouterTourNormale(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourRapide(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourBouclier(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourQuiTireAlEnvers(true);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne(false);
            }
        });
        
        JButton boutonTourAttaqueTousLesChatsDansColonne = new JButton("Ajouter Tour qui penètre");
        this.getPanelLabels().add(boutonTourAttaqueTousLesChatsDansColonne);
        boutonTourAttaqueTousLesChatsDansColonne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	plateauLogic.getPlateauData().setJoeurChoisiAjouterTourNormale(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourRapide(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourBouclier(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourQuiTireAlEnvers(false);
                plateauLogic.getPlateauData().setJoeurChoisiAjouterTourAttaqueTousLesChatsDansColonne(true);
            }
        });
        
        // texte pv maison 
        CustomJPanel labelPointsDeVieMaison = new CustomJPanel("pvMaison");
        labelPointsDeVieMaison.setText("PV Maison: " + getPlateauLogic().getPlateauData().getPvMaison() + "%");
        labelPointsDeVieMaison.setFont(labelPointsDeVieMaison.getFont().deriveFont(16.0F));
        this.getPanelLabels().add(labelPointsDeVieMaison);
        
        // texte argent 
        CustomJPanel labelArgent = new CustomJPanel("argent");
        labelArgent.setText("Argent: " + getPlateauLogic().getPlateauData().getArgent());
        labelArgent.setFont(labelArgent.getFont().deriveFont(16.0F));
        this.getPanelLabels().add(labelArgent);
        
	}

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
                        	
                        	// mettre image de la Tour qui tire sur tous les chats dans la colonne dans la case
                        	if ( (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof TourData) && 
                        			(getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getAttaque() == TourAttaqueTousLesChatsDansColonneEnum.ATTAQUE.getValue()) ) { // au contraire de la tour normale, cette tour a une attaque de 4
                        		int dimensionTour = ((getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getPointsDeVie() * getTailleCase()) 
                        				/ getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getMaxPointsDeVie()); // taille de l'image depend des points de pv
                        		if (dimensionTour > 0) {
                        			Image imageTourRedimensione = imageTourAttaqueTousLesChatsDansColonne.getImage().getScaledInstance(dimensionTour, dimensionTour, Image.SCALE_SMOOTH);
                        		    ImageIcon iconeTourRedimensione = new ImageIcon(imageTourRedimensione);
                                      
                              		// Calculer le centre pour un affichage centré
                                      int centreX = x + (getTailleCase() - iconeTourRedimensione.getIconWidth()) / 2;
                                      int centreY = y + (getTailleCase() - iconeTourRedimensione.getIconHeight()) / 2;
                              		
                              		iconeTourRedimensione.paintIcon(this, g, centreX, centreY);
                        		}
                        	}
                        	
                        	// mettre image de la Tour qui tire a l'envers dans la case
                        	else if ( (getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite() instanceof TourData) && 
                        			(getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getAttaque() == TourQuiTireAlEnversEnum.ATTAQUE.getValue()) ) { // au contraire de la tour normale, cette a une attaque de 0
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
                        			(getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getAttaque() == TourBouclierEnum.ATTAQUE.getValue()) ) { // au contraire de la tour normale, la tourBouclier a une attaque de 0
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
                        			(getPlateauLogic().getPlateauData().getCases()[iCol][iRow].getEntite().getAttaque() == TourRapideEnum.ATTAQUE.getValue()) ) { // au contraire de la tour normale, cette tour a une attaque de 1 au lieu de 6
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
                        	
                        	mettreImageChat(g, iRow, iCol, x, y);
                        }
                    }
                }
            }

			public void mettreImageChat(Graphics g, int iRow, int iCol, int x, int y) {
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
			}
        });
	}
	
	
	
	public void actualiserIgPlateauContinu(JFrame frame) {
		JPanel ref_panelLabels = this.getPanelLabels();
		JPanel ref_panelJeu = this.getPanelJeu();
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		            	if (getPlateauLogic().getPlateauData().isGameOver()) {
		            		 GameOver gameOver = new GameOver();
		            		 frame.remove(getPanelGeneral());
		            		 gameOver.faireMenu(frame);
		                     cancel();
						}
		            	
		            	if (getPlateauLogic().getPlateauData().isVictory()) {
		            		 Victoire victoire = new Victoire();
		            		 frame.remove(getPanelGeneral());
		            		 victoire.faireMenu(frame);
		                     cancel();
						}
		            	
		            	SwingUtilities.invokeLater(() -> {
			            	ref_panelJeu.repaint(); // actualiser la representation graphique du plateau
			            	
			            	for (Component jc : ref_panelLabels.getComponents()) { // actualier pv maison
		                         if ((jc instanceof CustomJPanel)) {
		                        	 
		                        	 CustomJPanel label = (CustomJPanel) jc;
		                             
		                        	 if (label.getId().equals("pvMaison")) {
		                            	 label.setText("PV Maison " + getPlateauLogic().getPlateauData().getPvMaison() + "%");
		                             }
		                        	 
		                             else if (label.getId().equals("argent")){
		                            	 label.setText("Argent: " + getPlateauLogic().getPlateauData().getArgent());
		                             }
		                         }
		                     }
		            	});
		            }
		        }, 
		        0, Niveau1Enum.VITESSE_AFFICHAGE_IMPLEMENTATION_GRAPHIQUE.getValue()
		);
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

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getVitesseGenerationChat() {
		return vitesseGenerationChat;
	}

	public void setVitesseGenerationChat(int vitesseGenerationChat) {
		this.vitesseGenerationChat = vitesseGenerationChat;
	}

	public int getVitesseGenerationArgent() {
		return vitesseGenerationArgent;
	}

	public void setVitesseGenerationArgent(int vitesseGenerationArgent) {
		this.vitesseGenerationArgent = vitesseGenerationArgent;
	}

	public int getNombreDeChatsGenererAvantVictoireModeNormal() {
		return nombreDeChatsAEliminerAvanttVictoireModeNormal;
	}

	public void setNombreDeChatsGenererAvantVictoireModeNormal(int nombreDeChatsAEliminerAvanttVictoireModeNormal) {
		this.nombreDeChatsAEliminerAvanttVictoireModeNormal = nombreDeChatsAEliminerAvanttVictoireModeNormal;
	}

	public PlateauLogic getPlateauLogic() {
		return plateauLogic;
	}

	public void setPlateauLogic(PlateauLogic plateauLogic) {
		this.plateauLogic = plateauLogic;
	}

	public int getArgent() {
		return argent;
	}

	public void setArgent(int argent) {
		this.argent = argent;
	}

	public int getDegreAugmentationStatsChats() {
		return degreAugmentationStatsChats;
	}

	public JPanel getPanelJeu() {
		return panelJeu;
	}

	public void setPanelJeu(JPanel panelJeu) {
		this.panelJeu = panelJeu;
	}

	public int getTailleCase() {
		return tailleCase;
	}

	public void setImageTour(ImageIcon imageTour) {
		this.imageTour = imageTour;
	}

	public ImageIcon getImageChat() {
		return imageChat;
	}

	public JPanel getPanelGeneral() {
		return panelGeneral;
	}

	public void setPanelGeneral(JPanel panelGeneral) {
		this.panelGeneral = panelGeneral;
	}

	public JPanel getPanelLabels() {
		return panelLabels;
	}

	public void setPanelLabels(JPanel panelLabels) {
		this.panelLabels = panelLabels;
	}

	public ImageIcon getImageMaison() {
		return imageMaison;
	}

	public ImageIcon getImageTourRapide() {
		return imageTourRapide;
	}

	public ImageIcon getImageTour() {
		return imageTour;
	}

	public ImageIcon getImageTourBouclier() {
		return imageTourBouclier;
	}

	public ImageIcon getImageTourQuiTireAlEnvers() {
		return imageTourQuiTireAlEnvers;
	}

	public ImageIcon getImageTourAttaqueTousLesChatsDansColonne() {
		return imageTourAttaqueTousLesChatsDansColonne;
	}


	
}
