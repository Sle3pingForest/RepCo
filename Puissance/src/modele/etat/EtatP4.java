package modele.etat;

import java.util.ArrayList;
import java.util.Scanner;

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
	
	public EtatP4(Jeu j,int [][] tab){
		super(j);
		this.plateau = tab;
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
				System.out.print(plateau[i][j] + "\t");
			}
			System.out.println("");
		}
	}


	public ArrayList<EtatP4> successeur() {
		ArrayList<EtatP4> listeSuc = new ArrayList<>();
		int colonne = this.plateau[0].length;
		int ligne = this.plateau.length;
		for(int i = 0 ; i < colonne;++i){
			int j = 1;
			boolean jouer = false;
			int [][] tabSuc = new int[ligne][colonne];
			copyValeurTableau(tabSuc);
			while( j <= ligne && !jouer){
				if(this.plateau[ligne - j][i] == 1){
					tabSuc[ligne - j - 1][i] = 1;
					tabSuc[ligne - j][i] = this.plateau[ligne - j][i];
					jouer = true;
				}
				if(this.plateau[ligne - j][i] == 0){
					tabSuc[ligne - j][i] = 1;
					jouer = true;
				}
		
				EtatP4 etatSuc = new EtatP4(this.jeu, tabSuc);
				listeSuc.add(etatSuc);
				j++;
			}
		}
		
		return listeSuc;
	}

	public int[][] getPlateau(){
		return this.plateau;
	}
	
	
	public void copyValeurTableau(int[][] tab){
		for (int i = 0; i < plateau[0].length; ++i){
			for(int j = 0 ;j < plateau.length;++j){
				tab[j][i] = this.plateau[j][i];
			}
		}
	}
	
	
	@Override
	public Jeu getJeu() {
		return this.jeu;
	}
	
	public void setValue(int i , int j){
		this.plateau[i][j] = 1;
	}
	

	@Override
	public void poserJetton(Joueur j){

		boolean estJouer = false;
		while(!estJouer){
			Scanner sc = new Scanner(System.in);
			int indiceColone = sc.nextInt();
			int k = 0;
			while( k < this.plateau.length && !estJouer){
				if(plateau[k][indiceColone] == 1){
					setValue(k + 1,indiceColone);
					j.setNbCoupJoue(1);
					estJouer = true;
					System.out.println(j.getNom() + " a joue!!!");
					setJcourant(j);
				}
				if(plateau[k][indiceColone] == 0 && j.getNbCoupJoue() == 0){
					setValue(k,indiceColone);
					j.setNbCoupJoue(1);
					estJouer = true;
					System.out.println(j.getNom() + " a joue!!!");
					setJcourant(j);
				}
				//EtatP4 ep4= new EtatP4(jeu, plateau);
				k++;
			}
		}
	}
	

	
	public boolean verifierEtat(EtatP4 e){
		boolean idem = true;
		int k = 0;
		while( k < successeur().size() && idem){
			int i = 0;
			while(i < successeur().get(k).getPlateau()[0].length && idem ){
				int j = 0;
				while( j < successeur().get(k).getPlateau().length && idem){
					if(successeur().get(i).getPlateau()[j][i] != e.getPlateau()[j][i]){
						idem = false;
					}
					j++;
				}
				i++;
			}
			k++;
		}
		return idem;
	}

}
