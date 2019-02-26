package modele.etat;

import java.util.ArrayList;

import modele.jeu.Jeu;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;

public abstract class Etat {
	
	protected Jeu jeu;
	protected Joueur jcourant;
	protected ArrayList<Integer> recompense;
	protected int nbSimu;
	public final static double c = Math.sqrt(2);
	protected Etat parent;
	protected boolean max;
	
	// ETAT racine
	public Etat(Jeu j){
		this.jeu = j;
		recompense = new ArrayList<>();
		nbSimu = 0;
		parent = null;
	}
	
	// ETAT FILS
	public Etat(Jeu j, Etat parent){
		this.jeu = j;
		recompense = new ArrayList<>();
		nbSimu = 0;
		this.parent = parent;
		max = !parent.getMax();
	}
	
	

	public abstract boolean finJeu();
	public abstract Etat lecture();
	public abstract void ecrire(Etat e);
	public abstract boolean egalite(Etat e);
	public abstract void affichage();
	public abstract Jeu getJeu();
	public abstract void poserJetton(Joueur j, Jeu jeu);
	public abstract void setJcourant(Joueur jcourant);
	public abstract Joueur getJcourant();
	public abstract int eval0( EtatP4 e , Joueur p);
	public void setMax(boolean b) {
		max = b;
	}
	public boolean getMax() {
		return max;
	}
	public void addRecompense(int r) {
		recompense.add(r);
	}
	public void incremente() {
		nbSimu++;
	}
	public int getNbSimu() {
		return nbSimu;
	}
	public void addParent(Etat e) {
		parent = e;
	}
	public double moyenne() {
		int somme = 0;
		for (int i : recompense) somme += i;
		return somme/nbSimu;
	}
	
	public double bValeur() {
		if (getNbSimu() == 0) return 0;
		double bValeur = moyenne() + c * Math.sqrt( Math.log(parent.getNbSimu()) / getNbSimu());
		if (!getMax()) return -bValeur;
		return bValeur;
	}

}

