package ig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jeu.Chat;
import jeu.Plateau;
import jeu.Tour;

public class Niveau1 {
	
	private JFrame frame;
    private Plateau p;
	private JPanel panelJeu;
    private int vitesseGenChats = 5000; // 24 seg
    private int argent = 50; // argent au début de la partie
    private int hauteur = 5;
	private int largeur = 5;
	private int vitesseGenArgent = 20000; // 20 seg
	private JFrame frameMenuPrincipal;
	
	
	public Niveau1(JFrame frameMenuPrincipal) {
		this.frame = new JFrame("Tower Defense: Chat vs Souris"); 
		this.p = new Plateau(this.hauteur, this.largeur, this.vitesseGenChats, this.argent, this.vitesseGenArgent);
		this.p.genereChatContinu();
		this.p.genereArgentContinu();
		this.frameMenuPrincipal = frameMenuPrincipal;
	}
	
	public void afficheJeu() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int tailleCellule = 50;
        this.frame.setSize(500, 500);
        this.frame.setLocationRelativeTo(null);

		this.panelJeu = new JPanel() {
            private static final long serialVersionUID = 1L; // necessary for some reason
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int iRow = 0; iRow < hauteur; iRow++) {
                    for (int iCol = 0; iCol < largeur; iCol++) {
                        
                        int x = iRow * tailleCellule;
                        int y = iCol * tailleCellule;

                        // Draw each cell based on your custom object properties
                        g.drawRect(x, y, tailleCellule, tailleCellule);
                        if (p.getCases()[iCol][iRow] != null) {
                            // Customize drawing based on your object
                        	if (p.getCases()[iCol][iRow].getEntite() instanceof Tour) {
                                g.setColor(Color.BLUE);
                        	}
                        	if (p.getCases()[iCol][iRow].getEntite() instanceof Chat) {
                                g.setColor(Color.RED);
                        	}
                            g.fillOval(x + 5, y + 5, tailleCellule - 10, tailleCellule - 10);
                        }
                    }
                }
            }
        };
        
        JLabel gameOverLabel = new JLabel();
        gameOverLabel.setText("PV Maison " + p.getPvMaison() + "%");
        gameOverLabel.setFont(gameOverLabel.getFont().deriveFont(10.0F));
        gameOverLabel.setHorizontalAlignment(JLabel.LEFT);
        panelJeu.add(gameOverLabel);
        
        panelJeu.setPreferredSize(new Dimension(500, 500));
        this.frame.add(panelJeu);

        this.frame.pack(); // option pour l'affichage des elements dans panel soit faite correctement
        this.frame.setVisible(true);
        this.actualiserIgPlateauContinu();
	}
	
	public void actualiserIgPlateauContinu() {
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
			            	
			            	for (Component jc : ref_panelJeu.getComponents()) {
		                         if (jc instanceof JLabel) {
		                             JLabel label = (JLabel) jc;
		                             label.setText("PV Maison " + p.getPvMaison() + "%");
		                         }
		                     }
		            	});
		            }
		        }, 
		        0, 30  // affiche le plateau toutes les (vitesseAffichage / 1000) segondes
		);
    }
	
	
}
