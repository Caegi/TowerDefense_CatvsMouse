package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuModeJeu {
		
	private JPanel panel;
	private Niveau niveau1;
	
	public MenuModeJeu(Niveau niveau1) {
		this.panel = new JPanel();
		this.niveau1 = niveau1;
	}
	
	public void faireMenu(JFrame frame) {
		JButton boutonNormal = new JButton("Normal");
        JButton boutonMarathon = new JButton("Marathon");
    
        this.panel.add(boutonNormal);
        boutonNormal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                niveau1.setModeNormal(true);
                frame.remove(panel);
                niveau1.afficheJeu(frame);
            }
        });
        
        this.panel.add(boutonMarathon);
        boutonMarathon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	niveau1.setModeNormal(false);
            	frame.remove(panel);
            	niveau1.afficheJeu(frame);
            }
        });
        
        this.panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200)); // setBorder a besoin en entree d'un objet Border
        this.panel.setLayout(new GridLayout(0, 1)); // setLayout prend en entree un objet LayoutManager
        
        frame.add(this.panel, BorderLayout.CENTER);
        frame.pack(); // option pour les affichage des elements dans panel soit faite correctement
        frame.setSize(800, 900); 
	}	
	

		
}
