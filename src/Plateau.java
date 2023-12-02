import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Plateau {

	Case [][] cases; // ok ?
	private int largeur;
	private int hauteur;
	
	// lCoordChats: liste[liste[posX, posY]]
	LinkedList<ArrayList<Integer>> lCoordChats = new LinkedList<ArrayList<Integer>>(); 
	
	public Plateau(int largeur, int hauteur) {
		this.cases = new Case [hauteur][largeur];
		this.setLargeur(largeur);
		this.hauteur = hauteur;
	}
	
	public void ajouteTour(int posX, int posY) {

		// trouver le chiffre du nom de la nouvelle Tour en comptant le nombre de tours dans le plateau
		Integer chiffreNom = 1;
		for (int iRow = 0; iRow<this.getLargeur(); iRow++){
			for (int iCol = 0; iCol<this.hauteur; iCol++){
				if ( (cases[iRow][iCol] != null) && (cases[iRow][iCol].getEntite() instanceof Tour) ) {
					chiffreNom++;
				}
			}
			
		}
	
		Tour catapulte = new Tour(70, 20, 2, 2, 2000, ("T" + chiffreNom) );
		if (cases[posX][posY] == null) {
			cases[posX][posY] = new Case(catapulte);
		}
		else {
			System.out.println("Impossible, il y a " + cases[posX][posY].getEntite().nom + " dans la case");
		}
	}
	
	public void genereChat() {
		boolean rangeePasRemplie = ( (this.cases[0][0] == null) || (this.cases[0][1] == null) || (this.cases[0][2] == null) || (this.cases[0][3] == null) || (this.cases[0][4] == null));
		if (rangeePasRemplie) { // s'il y a une case vide dans la première rangée
			
			// trouver le chiffre du nom du nouveau Chat
			String chiffreNom = "1";
			if ( !(this.lCoordChats.isEmpty()) ) {
				int posX = lCoordChats.getLast().get(0); // coordonée du dernier element de lCoordChats, qui est une arraylist[posX, posY] 
				int posY = lCoordChats.getLast().get(1); 
				chiffreNom = cases[posX][posY].getEntite().nom.substring(1); // le nom du chat est de la forme "Cn" (n:int), et on récupère n on trouvant la substring
				chiffreNom = String.valueOf(Integer.valueOf(chiffreNom) + 1); // ajoute 1 au chiffre du dernier chat ajouté à lCoordChats	
			}
			
			// le mettre dans une des 5 sources de chat
			Random rand = new Random();
			int posY = rand.nextInt(5);
			int posX = 0;
			if (this.cases[posX][posY] == null) { 
				Chat chat = new Chat(100, 15, posX, posY, 5000, ("C" + chiffreNom)); 
				this.cases [0][posY] = new Case(chat);
				ArrayList<Integer> lCoordChat = new ArrayList<Integer>();
				lCoordChat.add(posX); // garder en mémoire les coordonées des chats pour ne pas avoir à parcourir le plateau pour les trouver
				lCoordChat.add(posY);
				this.lCoordChats.add(lCoordChat);
			}
			else { // s'il y avait déja une entité sur la case, essayer une autre
				this.genereChat();
			}
		}
	}
	
	public void afficheTout() {
		// itérer sur les rangées du plateau
		for (int iRow = 0; iRow<this.getLargeur(); iRow++){
			System.out.println("");
			
			// itérer sur les colonnes du plateau
			for (int iCol = 0; iCol<this.hauteur; iCol++){
				if (cases[iRow][iCol] != null) {
					System.out.print( cases[iRow][iCol].toString() );
				}
				else {
					System.out.print(" . ");
				}
			}
			
		}
		System.out.print(" \n      /\\ \n     /  \\\n    /----\\\n   / |  | \\\n   | |  | |"); // affiche la maison de souris
		System.out.println("\n");
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	
}
