package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Victoire {
		
	private JPanel panel;
	
	public Victoire() {
		this.panel = new JPanel();
	}
	
	public void faireMenu(JFrame frame) {
        
        JButton boutonMenuPrincipal = new JButton("Menu Principal");
        this.panel.add(boutonMenuPrincipal);
        boutonMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Menu Principal" est cliqué
            	MenuPrincipal menuPrincipal = new MenuPrincipal();
            	frame.remove(panel);
            	menuPrincipal.faireMenu(frame);
            }
        });
        
        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Quitter" est cliqué
                System.exit(0); // terminer programme
            }
        });
        
        // texte VICTOIRE !
        JLabel Victoire = new JLabel();
        Victoire.setFont(Victoire.getFont().deriveFont(20.0F));
        Victoire.setText("VICTOIRE !".toUpperCase()); 
        Victoire.setHorizontalAlignment(JLabel.CENTER);
        Victoire.setForeground(Color.GREEN); 
        
        this.panel.add(Victoire); 
        this.panel.add(boutonMenuPrincipal);
        this.panel.add(boutonQuitter);
        
        this.panel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200)); // setBorder a besoin en entree d'un objet Border
        this.panel.setLayout(new GridLayout(0, 1)); // setLayout prend en entree un objet LayoutManager
        
        frame.add(this.panel, BorderLayout.CENTER);
        frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        frame.setSize(800, 900); 
	}
		
		
	
}
