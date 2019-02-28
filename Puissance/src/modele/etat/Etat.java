package modele.etat;

import java.util.ArrayList;

import modele.jeu.Jeu;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;

public abstract class Etat {
	
	protected Jeu jeu;
	protected Joueur jcourant;
	protected ArrayList<Double> recompense;
	protected int nbSimu;
	public final static double c = Math.sqrt(2);
	protected Etat parent;
	protected boolean max;
	protected boolean estVisite = false;
	protected int nbVisite;
	
	// ETAT racine
	public Etat(Jeu j){
		this.jeu = j;
		recompense = new ArrayList<>();
		nbSimu = 0;
		parent = null;
		nbVisite = 0;
	}
	
	// ETAT FILS
	public Etat(Jeu j, Etat parent){
		this.jeu = j;
		recompense = new ArrayList<>();
		nbSimu = 0;
		this.parent = parent;
		max = !parent.getMax();
		nbVisite = 0;
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
	public void addRecompense(double r) {
		recompense.add(r);
		nbSimu++;
		if (parent != null) {
			parent.addRecompense(r);
		}
	}
	public void incremente() {
		nbSimu++;
	}
	public int getNbSimu() {
		return nbSimu;
	}
	public void addParent(Etat e) {
		parent = e;
		max = !parent.getMax();
	}
	public Etat getParent() {
		return parent;
	}
	public double moyenne() {
		int somme = 0;
		for (double i : recompense) somme += i;
		return somme/nbSimu;
	}
	
	public double bValeur() {
		if (getNbSimu() == 0 || parent == null) return 0;
		double bValeur = moyenne() + c * Math.sqrt( Math.log(parent.getNbSimu()) / getNbSimu());
		if (!getMax()) return -bValeur;
		return bValeur;
	}
	
	public void setVisite(boolean b){
		this.estVisite = b;
	}
	
	public boolean getVisite(){
		return this.estVisite;
	}

	public int getNbVisite() {
		return nbVisite;
	}

	public void setNbVisite(int nbVisite) {
		this.nbVisite = nbVisite;
	}
	
	public void augmenterNbVisite() {
		this.nbVisite += 1;
	}
	
	public double tauxVictoire() {
		
		int nbVictoire =0;
		for (double i : recompense) {
			//System.out.println(i);
			if (i == 1) nbVictoire++;
		}
		//System.out.println( nbVictoire + "   " + getNbSimu() + " dddddddddddddddddddddddd");
		return ((double)nbVictoire/nbSimu) * 100;
	}
	
	

}

