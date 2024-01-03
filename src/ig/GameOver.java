package ig;

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

public class GameOver {
		
	private JFrame frame;
	private JPanel panel;
	private JFrame frameMenuPrincipal;
	
	public GameOver(JFrame frameMenuPrincipal) {
		this.frame = new JFrame("Tower Defense: Chat vs Souris"); // window
		this.panel = new JPanel();
		this.frameMenuPrincipal = frameMenuPrincipal;
	}
	
	public void faireMenu() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        JButton boutonRecommencer = new JButton("Recommencer");
        boutonRecommencer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Recommencer" est cliqué
                Niveau1 nvNiveau1 = new Niveau1(frameMenuPrincipal);
                frame.setVisible(false);
                nvNiveau1.afficheJeu();
            }
        });
        
        JButton boutonMenuPrincipal = new JButton("Menu Principal");
        this.panel.add(boutonMenuPrincipal);
        boutonMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Menu Principal" est cliqué
                frameMenuPrincipal.setVisible(true); // remettre visible le menu principal
                frame.setVisible(false); // mettre invisible le menu de Game Over
            }
        });
        
        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // action à exécuter quand "Quitter" est cliqué
                System.exit(0); // terminer programme
            }
        });
        
        // texte GAME OVER
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(gameOverLabel.getFont().deriveFont(20.0F));
        gameOverLabel.setText("GAME OVER".toUpperCase()); 
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        gameOverLabel.setForeground(Color.RED); 
        
        this.panel.add(gameOverLabel); 
        this.panel.add(boutonRecommencer);
        this.panel.add(boutonMenuPrincipal);
        this.panel.add(boutonQuitter);
        
        this.panel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200)); // setBorder a besoin en entree d'un objet Border
        this.panel.setLayout(new GridLayout(0, 1)); // setLayout prend en entree un objet LayoutManager
        
        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        this.frame.setSize(800, 900); 
        this.frame.setVisible(true);
	}
		
		
	
}
