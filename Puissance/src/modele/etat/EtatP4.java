package modele.etat;

import java.util.ArrayList;

import modele.jeu.Jeu;
import modele.joueur.Joueur;

public class EtatP4 extends Etat {

	protected Joueur jcourant;
	protected int[][] plateau;

	public EtatP4(Jeu j) {
		super(j);
		this.plateau = j.getPlateau();
	}

	public EtatP4(Jeu j, Joueur jc) {
		super(j);
		this.jcourant = jc;
		this.plateau = j.getPlateau();
	}

	@Override
	public boolean finJeu() {

		boolean fin = false;
		if (!this.rempli()) {


		} else {
			fin = true;
		}
		return fin;
	}

	public boolean rempli() {
		boolean rempli = false;
		int colonne = this.jeu.getPlateau().length;
		int ligne = this.jeu.getPlateau()[0].length;
		boolean j1 = this.jeu.getJ1().getNbCoupJoue() == (ligne*colonne)/2;
		boolean j2 = this.jeu.getJ2().getNbCoupJoue() == (ligne*colonne)/2;
		if ( j1 && j2) {
			rempli = true;
		}
		return rempli;
	}

	@Override
	public Etat lecture() {
		return null;
	}

	@Override
	public void ecrire(Etat e) {

	}

	@Override
	public boolean egalite(Etat e) {
		boolean egal = true;
		
		egal = this.jcourant == ((EtatP4) e).getJcourant();
		if ( egal) {
			boolean verif = true;
			int [][] p = this.jeu.getPlateau(), p2 = e.jeu.getPlateau();
			int compt = 0,i =1, j = 0, colonne = p.length;
			int ligne = p[0].length;
			verif = p == p2;
			while ( compt < ligne*colonne && verif) {
				
				verif = p[i-1][j] == p2[i-1][j];
				if (j == p[0].length -1 ) i++;
				
				// regarde si on est à la fin de la ligne
				if (j == p[0].length - 1 ) j = 0;
				else j++;
				compt++;
			}
			egal = verif;
		}

		return egal;
	}

	public Joueur getJcourant() {
		return jcourant;
	}

	public void setJcourant(Joueur jcourant) {
		this.jcourant = jcourant;
	}

	@Override
	public void affichage() {
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau[0].length; j++) {
				System.out.print(plateau[i][j]);
			}
			System.out.println("");
		}
	}

	@Override
	public ArrayList<Etat> successeur() {
		return null;
	}

}
