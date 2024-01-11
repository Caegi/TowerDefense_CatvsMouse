package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import enums.DifficulteEnum;

public class MenuDifficulte {
	
	private JPanel panel;
	private Niveau niveau1;
	
	public MenuDifficulte(Niveau niveau1) {
		this.panel = new JPanel();
		this.niveau1 = niveau1;
	}
	
	public void faireMenu(JFrame frame) {
        JButton boutonFacile = new JButton("Facile");
        JButton boutonMoyen = new JButton("Moyen");
        JButton boutonDifficile = new JButton("Difficile");
    
        this.panel.add(boutonFacile);
        boutonFacile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Niveau 1" est cliqué
            	niveau1.setDegreAugmentationStatsChats(DifficulteEnum.FACILE.getValue());
                frame.remove(panel);
                MenuModeJeu menuModeJeu = new MenuModeJeu(niveau1);
                menuModeJeu.faireMenu(frame);
            }
        });
        
        this.panel.add(boutonMoyen);
        boutonMoyen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Menu Principal" est cliqué
            	niveau1.setDegreAugmentationStatsChats(DifficulteEnum.MOYEN.getValue());
            	frame.remove(panel);
                MenuModeJeu menuModeJeu = new MenuModeJeu(niveau1);
                menuModeJeu.faireMenu(frame);
            }
        });
        
        this.panel.add(boutonDifficile);
        boutonDifficile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Menu Principal" est cliqué
            	niveau1.setDegreAugmentationStatsChats(DifficulteEnum.DIFFICILE.getValue());
            	frame.remove(panel);
                MenuModeJeu menuModeJeu = new MenuModeJeu(niveau1);
                menuModeJeu.faireMenu(frame);
            }
        });
        
        this.panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200)); // setBorder a besoin en entree d'un objet Border
        this.panel.setLayout(new GridLayout(0, 1)); // setLayout prend en entree un objet LayoutManager
        
        frame.add(this.panel, BorderLayout.CENTER);
        frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        frame.setSize(800, 900); 
	}	
}

	

