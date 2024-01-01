package ig;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import jeu.Plateau;

public class Niveau1 {
	
	private JFrame frame;
	private JTable table;
    private JScrollPane scrollPane;
    private Plateau p;
    private int vitesseGenChats = 24000; // 1000 = 1seg
    private int argent = 50; // argent au début de la partie
    private int hauteur = 5;
	private int largeur = 5;
	private int vitesseGenArgent = 20000; // 1000 = 1seg
	
	public Niveau1() {
		this.frame = new JFrame("Tower Defense: Chat vs Souris"); 
		this.p = new Plateau(this.hauteur, this.largeur, this.vitesseGenChats, this.argent, this.vitesseGenArgent);
	}
	
	public void afficheTout() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
        this.frame.pack(); // option pour l'affichage des elements dans panel soit faite correctement
        this.frame.setSize(800, 900); 
        this.frame.setVisible(true);
	}

}
