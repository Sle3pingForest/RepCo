package modele.joueur;

import java.util.ArrayList;

import modele.jeu.Pion;

public abstract class Joueur {
	
	protected String nom;
	protected int nbCoupJoue;	
	protected int num ;
	
	public Joueur(String j) {
		this.nom = j;
		this.nbCoupJoue = 0;
	}
	public Joueur(String j, int numP) {
		this.nom = j;
		this.nbCoupJoue = 0;
		num = numP;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNbCoupJoue() {
		return nbCoupJoue;
	}

	public void setNbCoupJoue(int nbCoupJoue) {
		this.nbCoupJoue = nbCoupJoue;
	}
	
	public void augmenterCoup() {
		this.nbCoupJoue ++;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


}
