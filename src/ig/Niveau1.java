package ig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import jeu.Case;
import jeu.Chat;
import jeu.Plateau;
import jeu.Tour;

public class Niveau1 {

	private JFrame frame;
    private Plateau p;
	private JPanel panelJeu;
	private JPanel panelGeneral;
	private JPanel panelLabels;
    private int vitesseGenChats = 12000; // 12 seg
    private int argent = 50; // argent au début de la partie
    private int hauteur = 5;
	private int largeur = 5;
	private int vitesseGenArgent = 12500; // 10 seg
	private JFrame frameMenuPrincipal;
    private ImageIcon imageTour = new ImageIcon("Tower.png");
    private ImageIcon imageChat = new ImageIcon("Cat.png");
    private ImageIcon imageMaison = new ImageIcon("MiceHouse.png");
	private int tailleCellule = 55;
	
	public Niveau1(JFrame frameMenuPrincipal) {
		this.frame = new JFrame("Tower Defense: Chat vs Souris"); 
		this.p = new Plateau(this.hauteur, this.largeur, this.vitesseGenChats, this.argent, this.vitesseGenArgent);
		this.p.genereChatContinu();
		this.p.genereArgentContinu();
		this.frameMenuPrincipal = frameMenuPrincipal;
		this.panelGeneral = new JPanel(new BorderLayout());
		this.panelLabels = new JPanel();
	}
	
	public void afficheJeu() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		this.panelJeu = new JPanel() {
            private static final long serialVersionUID = 1L; // necessary for some reason
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int iRow = 0; iRow < hauteur; iRow++) {
                    for (int iCol = 0; iCol < largeur; iCol++) {
                        
                        int x = iRow * tailleCellule;
                        int y = iCol * tailleCellule;

                        g.drawRect(x, y, tailleCellule, tailleCellule);
                        
                        if (p.getCases()[iCol][iRow] != null) {
                        	
                        	// mettre image de la Tour dans la cellule
                        	if (p.getCases()[iCol][iRow].getEntite() instanceof Tour) {
                        		int dimensionTour =  ((p.getCases()[iCol][iRow].getEntite().getpV() * tailleCellule) / p.getCases()[iCol][iRow].getEntite().getMaxPV()); // taille de l'image depend des points de pv
                        		Image imageTourRedimensione = imageTour.getImage().getScaledInstance(dimensionTour, dimensionTour, Image.SCALE_SMOOTH);
                                ImageIcon iconeTourRedimensione = new ImageIcon(imageTourRedimensione);
                                
                        		// Calculer le centre pour un affichage centré
                                int centreX = x + (tailleCellule - iconeTourRedimensione.getIconWidth()) / 2;
                                int centreY = y + (tailleCellule - iconeTourRedimensione.getIconHeight()) / 2;
                        		
                        		iconeTourRedimensione.paintIcon(this, g, centreX, centreY);
                        	}
                        	
                        	// mettre image du Chat dans la cellule
                        	if (p.getCases()[iCol][iRow].getEntite() instanceof Chat) {
                        		int dimensionChat = ((p.getCases()[iCol][iRow].getEntite().getpV() * tailleCellule) / p.getCases()[iCol][iRow].getEntite().getMaxPV()); // taille de l'image depend des points de pv
                        		Image imageChatRedimensione = imageChat.getImage().getScaledInstance(dimensionChat, dimensionChat, Image.SCALE_SMOOTH);
                                ImageIcon iconeChatRedimensione = new ImageIcon(imageChatRedimensione);
                                
                        		// Calculer le centre pour un affichage centré
                                int centreX = x + (tailleCellule - iconeChatRedimensione.getIconWidth()) / 2;
                                int centreY = y + (tailleCellule - iconeChatRedimensione.getIconHeight()) / 2;
                        		
                        		iconeChatRedimensione.paintIcon(this, g, centreX, centreY);
                        	}
                        }
                    }
                }
            }
        };
        this.panelGeneral.add(panelJeu, BorderLayout.CENTER);
        
        this.panelLabels.setLayout(new BoxLayout(this.panelLabels, BoxLayout.Y_AXIS));
        
        JLabel houseLabel = new JLabel(this.imageMaison);
        this.panelLabels.add(houseLabel);
        
        // texte pv maison 
        CustomJPanel labelPvMaison = new CustomJPanel("pvMaison");
        labelPvMaison.setText("PV Maison: " + p.getPvMaison() + "%");
        labelPvMaison.setFont(labelPvMaison.getFont().deriveFont(16.0F));
        this.panelLabels.add(labelPvMaison);
        
        // texte argent 
        CustomJPanel labelArgent = new CustomJPanel("argent");
        labelArgent.setText("Argent: " + p.getArgent());
        labelArgent.setFont(labelArgent.getFont().deriveFont(16.0F));
        this.panelLabels.add(labelArgent);
        
        // ajouter une tour
        this.panelJeu.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Calculer la cellule qui a été cliquée
                int posX = e.getX() / tailleCellule;
                int posY = e.getY() / tailleCellule;
                
                p.ajouteTour(posY, posX);
            }
        });
        
        this.panelGeneral.add(this.panelLabels, BorderLayout.SOUTH);
        this.panelGeneral.setPreferredSize(new Dimension(this.tailleCellule * this.largeur, 510));
        
        this.frame.add(this.panelGeneral);
        this.frame.pack(); // option pour l'affichage des elements dans panel soit faite correctement
        this.frame.setVisible(true);
        this.actualiserIgPlateauContinu();
	}
	
	public void actualiserIgPlateauContinu() {
		JPanel ref_panelLabels = this.panelLabels;
		JPanel ref_panelJeu = this.panelJeu;
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	if (p.isGameOver()) {
		            		 GameOver gameOver = new GameOver(frameMenuPrincipal);
		            		 gameOver.faireMenu();
		                     frame.setVisible(false);
		                     cancel();
						}
		            	
		            	SwingUtilities.invokeLater(() -> {
			            	ref_panelJeu.repaint(); // actualiser la representation graphique du plateau
			            	
			            	for (Component jc : ref_panelLabels.getComponents()) { // actualier pv maison
		                         if ((jc instanceof CustomJPanel)) {
		                        	 CustomJPanel label = (CustomJPanel) jc;
		                             if (label.getId().equals("pvMaison")) {
		                            	 label.setText("PV Maison " + p.getPvMaison() + "%");
		                             }
		                             else if (label.getId().equals("argent")){
		                            	 label.setText("Argent: " + p.getArgent());
		                             }
		                         }
		                     }
		            	});
		            }
		        }, 
		        0, 100  
		);
    }
	
}
