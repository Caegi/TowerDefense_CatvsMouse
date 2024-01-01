package ig;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPrincipal {
	
	private JFrame frame;
	private JPanel panel;
	
	public MenuPrincipal() {
		this.frame = new JFrame("Tower Defense: Chat vs Souris"); // window
		this.panel = new JPanel();
	}
	
	public void faireMenu() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        JButton boutonCommencer = new JButton("Commencer");
        boutonCommencer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Commencer" est cliqué
                MenuNiveau menuNiveau = new MenuNiveau();
                menuNiveau.faireMenu();
                frame.setVisible(false);
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
        
        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        this.frame.setSize(800, 900); 
        this.frame.setVisible(true);
	}
	
	
}
