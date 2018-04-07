package modele.etat;

import java.util.ArrayList;

import modele.jeu.Jeu;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;

public abstract class Etat {
	protected Jeu jeu;
	
	public Etat(Jeu j){
		this.jeu = j;
	}

	public abstract boolean finJeu();
	public abstract Etat lecture();
	public abstract void ecrire(Etat e);
	public abstract boolean egalite(Etat e);
	public abstract void affichage();
	public abstract Jeu getJeu();
	public abstract void poserJetton(Joueur j, Jeu jeu);
	public abstract void setJcourant(Joueur jcourant);
	public abstract int eval0( EtatP4 e , Joueur p);


}

