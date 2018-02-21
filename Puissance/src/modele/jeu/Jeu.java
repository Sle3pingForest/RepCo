package modele.jeu;

import modele.joueur.Joueur;
import modele.joueur.JoueurP4;

public class Jeu {
	
	protected Joueur j1, j2;
	protected int[][] plateau = new int[6][7]; // ligne =  plateau.leght et colonne = plateau[0].length
	
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



	public Joueur getJ1() {
		return j1;
	}



	public void setJ1(Joueur j1) {
		this.j1 = j1;
	}



	public Joueur getJ2() {
		return j2;
	}


	public void setJ2(Joueur j2) {
		this.j2 = j2;
	}


	public int[][] getPlateau() {
		return plateau;
	}


	public void setPlateau(int[][] plateau) {
		this.plateau = plateau;
	}
	

}
