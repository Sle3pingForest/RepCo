package modele.jeu;

import modele.joueur.Joueur;
import modele.joueur.JoueurP4;

public class Jeu {
	
	protected Joueur j1, j2;
	protected int[][] plateau = new int[7][6]; // colonne / ligne
	
	public Jeu (String j1, String j2) {
		this.j1 = new JoueurP4(j1);
		this.j2 = new JoueurP4(j2);
		this.setInitial();
	}
	
	
	
	public void setInitial() {
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau[0].length; j++) {
				plateau[i][j] = 0;
			}
		}
	}
	
	

}
