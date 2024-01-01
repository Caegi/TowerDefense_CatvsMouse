package ig;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuNiveau {
	
	private JFrame frame;
	private JPanel panel;
	
	public MenuNiveau() {
		this.frame = new JFrame("Tower Defense: Chat vs Souris"); // window
		this.panel = new JPanel();
	}
	
	public void faireMenu() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        JButton boutonNiveau1 = new JButton("Niveau 1");
        JButton boutonMenuPrincipal = new JButton("Menu Principal");
    
        this.panel.add(boutonNiveau1);
        boutonNiveau1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Commencer" est cliqué
                Niveau1 affJeu = new Niveau1();
                frame.setVisible(false);
            }
        });
        
        this.panel.add(boutonMenuPrincipal);
        boutonMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Commencer" est cliqué
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.faireMenu();
                frame.setVisible(false);
            }
        });
        
        this.panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200)); // setBorder a besoin en entree d'un objet Border
        this.panel.setLayout(new GridLayout(0, 1)); // setLayout prend en entree un objet LayoutManager
        
        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        this.frame.setSize(800, 900); 
        this.frame.setVisible(true);
	}	
}
