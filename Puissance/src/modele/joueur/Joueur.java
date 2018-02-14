package modele.joueur;

public abstract class Joueur {
	
	protected String nom;
	protected int nbCoupJoue;
	
	public Joueur(String j) {
		this.nom = j;
		this.nbCoupJoue = 0;
	}
	
	
	public int getNbCoupJoue() {
		return nbCoupJoue;
	}

	public void setNbCoupJoue(int nbCoupJoue) {
		this.nbCoupJoue = nbCoupJoue;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}

}