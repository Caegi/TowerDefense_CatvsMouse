package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPrincipal {
	
	private JPanel panel;
	
	public MenuPrincipal() {
		this.panel = new JPanel();
	}
	
	// frame est un object JFrame avec aucun object Component
	public void faireMenu(JFrame frame) {        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        JButton boutonCommencer = new JButton("Commencer");
        boutonCommencer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Commencer" est cliqué
                MenuNiveau menuNiveau = new MenuNiveau();
                frame.remove(panel);
                menuNiveau.faireMenu(frame);
            }
        });
        
        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Quitter" est cliqué
                System.exit(0);
            }
        });
    
        this.panel.add(boutonCommencer);
        this.panel.add(boutonQuitter);
        
        this.panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200)); // setBorder a besoin en entree d'un objet Border
        this.panel.setLayout(new GridLayout(0, 1)); // setLayout prend en entree un objet LayoutManager
        
        frame.add(this.panel, BorderLayout.CENTER);
        frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        frame.setSize(800, 900);
        frame.setVisible(true);
	}
	
	
}
