import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		
		
		Plateau Niveau1 = new Plateau(5,5);
		Niveau1.genereChat();

		
		Niveau1.afficheTout();
		
		try (Scanner myObj = new Scanner(System.in)) {
			System.out.println("Mettre Tour ? (oui/non)");

			String reponse = myObj.nextLine();  // lecture de l'input du user
			
			if (reponse.equals("oui")) {
				
				System.out.println("Position x ? (de 1 à " + Niveau1.getLargeur() + ")");
				int posX = myObj.nextInt() - 1;
				System.out.println("Position y ? (de 1 à " + Niveau1.getLargeur() + ")");
				int posY = myObj.nextInt() - 1;
				Niveau1.ajouteTour(posX, posY);
			}
		}
		
		Niveau1.afficheTout();
		
		for (ArrayList<Integer> lCoordChat : Niveau1.lCoordChats) {
			int posX = lCoordChat.get(0);
			int posY = lCoordChat.get(1);
			Entite chat = Niveau1.cases[posX][posY].getEntite();
			chat.attaque(Niveau1) ;
		}
		
	
	}
	
}
