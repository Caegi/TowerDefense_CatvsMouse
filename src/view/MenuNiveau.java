package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import enums.Niveau2Enum;

public class MenuNiveau {
	
	private JPanel panel;
	
	public MenuNiveau() {
		this.panel = new JPanel();
	}
	
	public void faireMenu(JFrame frame) {
        JButton boutonNiveau1 = new JButton("Niveau 1");
        JButton boutonNiveau2 = new JButton("Niveau 2");
        JButton boutonNiveau3 = new JButton("Niveau 3");
        JButton boutonMenuPrincipal = new JButton("Menu Principal");
    
        this.panel.add(boutonNiveau1);
        boutonNiveau1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Niveau 1" est cliqué
            	Niveau niveau1 = new Niveau();
            	MenuDifficulte menuDifficulte = new MenuDifficulte(niveau1);
                frame.remove(panel);
                menuDifficulte.faireMenu(frame);
            }
        });
        
        this.panel.add(boutonNiveau2);
        boutonNiveau2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Niveau 1" est cliqué
            	Niveau niveau2 = new Niveau();
            	niveau2.setHauteur(Niveau2Enum.HAUTEUR.getValue());
            	niveau2.setLargeur(Niveau2Enum.LARGEUR.getValue());
            	niveau2.setVitesseGenerationChat(Niveau2Enum.VITESSE_GENERATION_CHAT.getValue());
            	niveau2.setVitesseGenerationArgent(Niveau2Enum.VITESSE_GENERATION_ARGENT.getValue());
            	niveau2.setNombreDeChatsGenererAvantVictoireModeNormal(Niveau2Enum.NOMBRE_CHAT_A_ELIMINER_AVANT_VICTOIRE_MODE_NORMAL.getValue());
            	MenuDifficulte menuDifficulte = new MenuDifficulte(niveau2);
                frame.remove(panel);
                menuDifficulte.faireMenu(frame);
            }
        });
        
        this.panel.add(boutonNiveau3);
        boutonNiveau3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ArrayList<ArrayList<Integer>> listePierres = new ArrayList<>();
            	
            	ArrayList<Integer> listePosXPosYPierreCentre = new ArrayList<>();
            	// ajouter une pierre dans le centre du plateau
            	listePosXPosYPierreCentre.add(2); // rangee pierre
            	listePosXPosYPierreCentre.add(2); // colone pierre
            	listePierres.add(listePosXPosYPierreCentre);
            	
            	ArrayList<Integer> listePosXPosYPierre2 = new ArrayList<>();
            	// ajouter une pierre dans la 4eme ranee et 4eme colonne du plateau
            	listePosXPosYPierre2.add(2); // rangee pierre
            	listePosXPosYPierre2.add(3); // colone pierre
            	listePierres.add(listePosXPosYPierre2);
            	
            	NiveauAvecPierre niveau3 = new NiveauAvecPierre(listePierres);
            	MenuDifficulte menuDifficulte = new MenuDifficulte(niveau3);
                frame.remove(panel);
                menuDifficulte.faireMenu(frame);
            }
        });
        
        this.panel.add(boutonMenuPrincipal);
        boutonMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Menu Principal" est cliqué
            	MenuPrincipal menuPrincipal = new MenuPrincipal();
            	frame.remove(panel);
            	menuPrincipal.faireMenu(frame);
            }
        });
        
        this.panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200)); // setBorder a besoin en entree d'un objet Border
        this.panel.setLayout(new GridLayout(0, 1)); // setLayout prend en entree un objet LayoutManager
        
        frame.add(this.panel, BorderLayout.CENTER);
        frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        frame.setSize(800, 900); 
	}	
}
