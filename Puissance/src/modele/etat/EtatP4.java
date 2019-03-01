package modele.etat;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import main.Puissance4;
import modele.jeu.Jeu;
import modele.jeu.Pion;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;

public class EtatP4 extends Etat {

	protected JoueurP4 gagant;
	protected int[][] plateau;
	protected Pion pIA;
	public EtatP4 ref;
	protected ArrayList<EtatP4> listSucc = new ArrayList<>();

	public EtatP4(Jeu j) {
		super(j);
		this.plateau = j.getPlateau();
	}

	public EtatP4(Jeu j, Joueur jc) {
		super(j);
		this.jcourant = jc;
		this.plateau = j.getPlateau();
	}

	public void createSuccesseur() {
		Joueur jc = jeu.getJ1();
		if (getJcourant().getNom() == jeu.getJ1().getNom()) jc = jeu.getJ2();
		listSucc = successeur(this, jc);
		for (EtatP4 e : listSucc) {
			e.addParent(this);
		}
	}


	public void createSuccesseur(Joueur jc) {
		listSucc = successeur(this, jc);
		for (EtatP4 e : listSucc) {
			e.addParent(this);
		}
	}

	public ArrayList<EtatP4> getListSucc() {
		return listSucc;
	}

	public void setListSucc(ArrayList<EtatP4> listSucc) {
		this.listSucc = listSucc;
	}

	public boolean checkTousVisite() {
		boolean check = true;
		if (listSucc.size() == 0) check = false;
		for (EtatP4 e : listSucc) {
			if (e.getVisite() == false) check = false;
		}
		return check;
	}

	public EtatP4 choixRandom(Joueur jc) {

		if (this.getListSucc().size() == 0) this.createSuccesseur();
		//System.out.println("test list " + this.getListSucc());
		int taille = (this.getListSucc().size()- 1);
		int r = (int) (Math.random() *  (taille -1)) ;
		//this.getListSucc().get(r).setJcourant(jc);
		if (this.getListSucc().get(r).getVisite() == false) {
			this.getListSucc().get(r).setVisite(true);
			this.augmenterNbVisite();
		}
		jc.augmenterCoup();
		//this.getListSucc().get(r).addParent(this);
		return this.getListSucc().get(r);
	}

	public EtatP4 choixEtatNonVisite(Joueur jc) {
		ArrayList<EtatP4> ensembleEtat = new ArrayList<>();

		for (EtatP4 e : listSucc) {
			if ( e.getVisite() == false) {
				ensembleEtat.add(e);
			}
		}

		int taille = (ensembleEtat.size()- 1);
		int r = (int) (Math.random() *  (taille -1)) ;
		ensembleEtat.get(r).setVisite(true);
		//ensembleEtat.get(r).setJcourant(jc);
		this.augmenterNbVisite();
		this.getJcourant().augmenterCoup();
		//ensembleEtat.get(r).addParent(this);
		return ensembleEtat.get(r);
	}

	/*
	public EtatP4 choixMaxBValeur() {

		ArrayList<EtatP4> ensembleEtat = successeur(this);		
		int taille = (ensembleEtat.size()- 1);
		int r = (int) (Math.random() *  (taille -1)) ;

		return ensembleEtat.get(r);
	}
	 */

	public EtatP4 choixNoeudMax() {

		EtatP4 eCourant = null;
		if (!this.finJeu() && !this.rempli2())  {
			double max = Integer.MIN_VALUE;
			ArrayList<EtatP4> list = new ArrayList<>();
			for (EtatP4 et : this.getListSucc()) {
				if (et.bValeur() == max) list.add(et); 
				if (et.bValeur() > max ) {
					max = et.bValeur();
					list.clear();
					list.add(et);
				}
			}
			int taille = (list.size()- 1);
			int r = (int) (Math.random() *  (taille -1)) ;
			if ( taille < 0 ) {
				Joueur jc =  (this.getJcourant().getNom() == jeu.getJ1().getNom()) ? jeu.getJ2() : jeu.getJ1(); 
				eCourant = this.choixRandom(jc);
			} else { 
				eCourant = list.get( r );
				//eCourant.addParent(this);
			}
		} else {
			eCourant = this;
		}
		return eCourant;
	}


	public void marcheAleatoire(Joueur j1, Joueur j2, Joueur jc) {
		if (this.finJeu() || this.rempli2()) {
			double score = this.evaluation(0, this);
			this.incremente();
			this.addRecompense(score);
		}
		else {
			if (jc.getNom() == j1.getNom() )  jc = j2;
			else jc = j1;
			// si un etat est gagnant on le choisit
			EtatP4 etat = null;
			if (listSucc.size() == 0) this.createSuccesseur();
			for (EtatP4 e : listSucc) {
				if (e.finJeu() && e.getMax()) etat = e;
			}
			if (etat == null) etat = choixRandom(jc); 
			etat.marcheAleatoire(j1, j2, jc);
		}
	}

	public EtatP4(Jeu j,Joueur jc,Pion p, int [][] tab){
		super(j);
		jcourant = jc;
		this.plateau = tab;
		this.pIA = p;
		int i = p.getPosX();
		int k = p.getPosY();
		if(this.jcourant.getNom().equals(jeu.getJ2().getNom())){
			this.plateau[i][k] = jeu.getJ2().getNum();
		}
		else{
			this.plateau[i][k] = jeu.getJ1().getNum();
		}
	}

	@Override
	public boolean finJeu() {
		boolean fin = false;
		if(!this.rempli2()){
			int j1 = this.getJeu().getJ1().getNum();
			int j2 = this.getJeu().getJ2().getNum();
			for(int i = 0; i < this.getPlateau().length; ++i){
				for(int j = 0; j < this.getPlateau()[0].length-3; ++j){
					if(this.getPlateau()[i][j] == j1
							&& this.getPlateau()[i][j+1] == j1
							&& this.getPlateau()[i][j+2] == j1
							&& this.getPlateau()[i][j+3] == j1){
						fin = true;

					}
					if(this.getPlateau()[i][j] == j2
							&& this.getPlateau()[i][j+1] == j2
							&& this.getPlateau()[i][j+2] == j2
							&& this.getPlateau()[i][j+3] == j2){
						fin = true;
					}
				}
			}
			for(int i = 0; i < this.getPlateau()[0].length ; ++i){
				for(int j = 0; j < this.getPlateau().length - 3; ++j){
					if(this.getPlateau()[j][i] == j1
							&& this.getPlateau()[j+1][i] == j1
							&& this.getPlateau()[j+2][i] == j1
							&& this.getPlateau()[j+3][i] == j1){
						fin = true;
					}
					if(this.getPlateau()[j][i] == j2
							&& this.getPlateau()[j+1][i] == j2
							&& this.getPlateau()[j+2][i] == j2
							&& this.getPlateau()[j+3][i] == j2){

						fin = true;
					}
				}

			}

			// diagonale haute droite
			for(int i = 0; i < this.getPlateau().length - 3; ++i){
				for(int j = 0; j < this.getPlateau()[0].length - 3; ++j){
					if(this.getPlateau()[i][j] == j1 &&
							this.getPlateau()[i+1][j+1] == j1 &&
							this.getPlateau()[i+2][j+2] == j1 &&
							this.getPlateau()[i+3][j+3] == j1 ){
						fin = true;
					}

					if(this.getPlateau()[i][j] == j2 &&
							this.getPlateau()[i+1][j+1] == j2 &&
							this.getPlateau()[i+2][j+2] == j2 &&
							this.getPlateau()[i+3][j+3] == j2 ){
						fin = true;
					}
				}
			}

			// diagonale haute gauche
			for(int i = this.getPlateau().length - 1; i > 2 ; --i){
				for(int j = 0; j < this.getPlateau()[0].length -3; ++j){

					if(this.getPlateau()[i][j] == j1 &&
							this.getPlateau()[i-1][j+1] == j1 &&
							this.getPlateau()[i-2][j+2] == j1 &&
							this.getPlateau()[i-3][j+3] == j1 ){
						fin = true;
					}

					if(this.getPlateau()[i][j] == j2 &&
							this.getPlateau()[i-1][j+1] == j2 &&
							this.getPlateau()[i-2][j+2] == j2 &&
							this.getPlateau()[i-3][j+3] == j2 ){
						fin = true;

					}
				}
			}
		}
		else{
			fin = true;
		}
		if(fin){
			this.gagant = (JoueurP4)this.jcourant;
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

	public boolean rempli2() {
		boolean rempli = true;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (plateau[i][j] == 0)rempli = false;
			}	
		}
		return rempli;
	}




	public Joueur getJcourant() {
		return jcourant;
	}

	public void setJcourant(Joueur jcourant) {
		this.jcourant = jcourant;
	}

	@Override
	public void affichage() {
		for (int i = plateau.length -1; i >=0 ; i--) {
			for (int j = 0; j < plateau[0].length; j++) {
				System.out.print(plateau[i][j] + "\t");
			}
			System.out.println("");
		}
		System.out.println();
	}


	public ArrayList<EtatP4> successeur(EtatP4 etat, Joueur jc) {
		ArrayList<EtatP4> listeSuc = new ArrayList<>();
		int colonne = this.plateau[0].length;
		int ligne = this.plateau.length;
		for(int i = 0 ; i < colonne;++i){
			int j = 0;
			boolean estPossible = false;
			int [][] tabSuc = new int[ligne][colonne];
			copyValeurTableau(tabSuc, etat.getPlateau());
			while( j < ligne && !estPossible){

				if(etat.getPlateau()[j][i] == 0 ){

					Pion p = new Pion(j, i);

					EtatP4 etatSuc = new EtatP4(this.jeu, jc,p, tabSuc);
					listeSuc.add(etatSuc);
					estPossible = true;

				}
				j++;
			}
		}
		return listeSuc;
	}
	public ArrayList<EtatP4> successeur(EtatP4 etat) {
		ArrayList<EtatP4> listeSuc = new ArrayList<>();
		int colonne = this.plateau[0].length;
		int ligne = this.plateau.length;
		for(int i = 0 ; i < colonne;++i){
			int j = 0;
			boolean estPossible = false;
			int [][] tabSuc = new int[ligne][colonne];
			copyValeurTableau(tabSuc, etat.getPlateau());
			while( j < ligne && !estPossible){

				if(etat.getPlateau()[j][i] == 0 ){

					Pion p = new Pion(j, i);

					EtatP4 etatSuc = new EtatP4(this.jeu, etat.getJcourant(),p, tabSuc);
					listeSuc.add(etatSuc);
					estPossible = true;

				}
				j++;
			}
		}
		return listeSuc;
	}

	public int[][] getPlateau(){
		return this.plateau;
	}


	public void copyValeurTableau(int[][] tab, int [][] tabRef){
		for (int i = 0; i < tabRef[0].length; ++i){
			for(int j = 0 ;j < tabRef.length;++j){
				tab[j][i] = tabRef[j][i];
			}
		}
	}


	/*@Override
	public ArrayList<Etat> successeur() {
		return null;
	}*/

	public Jeu getJeu() {
		return this.jeu;
	}

	public void setValue(int i , int j, Joueur joueur){
		if(joueur.getNom().equals(this.jeu.getJ1().getNom().toString())){
			this.plateau[i][j] = joueur.getNum();
		}

		if(joueur.getNom().equals(this.jeu.getJ2().getNom().toString())){
			this.plateau[i][j] = joueur.getNum();
		}

	}



	public boolean isInteger(String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void poserJetton(Joueur j, Jeu jeu){

		setJcourant(j);
		boolean estJouer = false;
		boolean valide = false;
		int indiceColone = 1;
		EtatP4 etatfavorable = null;

		while(!valide){	
			int k = 0;
			if(!j.getNom().equals("IA")){
				System.out.print("Choisissez la colonne ou poser le pion (1 a 7) : ");
				Scanner sc = new Scanner(System.in);
				String s = sc.next();
				boolean ok = false;
				while(!ok){
					if(this.isInteger(s)){
						ok = true;
						indiceColone = Integer.parseInt(s);
					}
					else{
						System.out.print("Choisissez la colonne ou poser le pion (1 a 7) : ");
						sc = new Scanner(System.in);
						s = sc.next();
					}
				}
				if(0 < indiceColone && indiceColone < 8 ){
					indiceColone = indiceColone - 1;
					valide = true;
				}

				if(valide && this.plateau[0][indiceColone] != 0 &&
						this.plateau[1][indiceColone] != 0 && 
						this.plateau[2][indiceColone] != 0 && 
						this.plateau[3][indiceColone] != 0 && 
						this.plateau[4][indiceColone] != 0 &&
						this.plateau[5][indiceColone] != 0){
					System.out.println("La ligne " +  indiceColone + " est remplie");
					valide = false;
				} 
			} else {
				valide = true;

				etatfavorable = Puissance4.selection(this, jeu.getJ1(), jeu.getJ2(), j);//minimax(new EtatP4(jeu, j),1);
				indiceColone = etatfavorable.getPion().getPosY();
			}
			while( k < this.plateau.length-1 && !estJouer && valide){

				if((plateau[k][indiceColone] == jeu.getJ1().getNum() || plateau[k][indiceColone] == jeu.getJ2().getNum()) && plateau[k + 1][indiceColone] == 0){
					System.out.println(j.getNom() + " a joue!!!");
					setJcourant(j);
					//EtatP4 etatfavorable = minimax(new EtatP4(jeu, j),0);
					//EtatP4 etatfavorable = minimaxAlphaBeta(new EtatP4(jeu, j),0,-100,+100);
					if(j.getNom().equals("IA")){
						setValue(etatfavorable.getpIA().getPosX(),etatfavorable.getpIA().getPosY(),jcourant);
					}
					else{
						setValue(k + 1,indiceColone,j);
					}
					Pion p = new Pion(k+1, indiceColone);
					j.setNbCoupJoue(1);
					estJouer = true;
					//((JoueurP4)j).ajouterPion(p);

				}
				if(plateau[k][indiceColone] == 0){
					System.out.println(j.getNom() + " a joue!!!");
					setJcourant(j);
					//EtatP4 etatfavorable = minimax(new EtatP4(jeu, j),0);
					//EtatP4 etatfavorable = minimaxAlphaBeta(new EtatP4(jeu, j),0,-100,+100);
					if(j.getNom().equals("IA")){	
						setValue(etatfavorable.getpIA().getPosX(),etatfavorable.getpIA().getPosY(),jcourant);
					}
					else{
						setValue(k,indiceColone,j);
					}
					Pion p = new Pion(k, indiceColone);
					j.setNbCoupJoue(1);
					estJouer = true;
					//((JoueurP4)j).ajouterPion(p);
				}
				k++;
			}
		}

	}



	/**
	 * fonction evaluation
	 * @param c = entier qui indique le nombre de coup dans le futur
	 * 		  e = etat
	 * @return  entier : coup gagant
	 */
	public double evaluation(int c, EtatP4 e){

		//System.out.println(" JOUEUR " + e.jcourant.getNom( )+ "  num " + num);
		int score_max , score_min;
		ArrayList<EtatP4> emsembleEtat = new ArrayList<>();
		int score = 0;
		if(e.finJeu()){
			if(e.getMax()/*e.getJcourant().getNom().equals("IA")*/){
				return 1.0;
			} else {
				return 0;
			}
			/*if(!e.getJcourant().getNom().equals("IA")){
				return 0;
			}*/

		}
		if(rempli2()){return 0;}
		return 0;
	}

	public int max(int a, int b ){
		if(a >= b) return a;
		else return b;
	}
	public int min(int a, int b ){
		if(a >= b) return b;
		else return a;
	}

	public JoueurP4 getGagant() {
		return gagant;
	}

	public void setGagant(JoueurP4 gagant) {
		this.gagant = gagant;
	}

	public Pion getpIA() {
		return pIA;
	}

	public void setpIA(Pion pIA) {
		this.pIA = pIA;
	}

	public void setPlateau(int[][] plateau) {
		this.plateau = plateau;
	}

	public void setPion(Pion p){
		this.pIA = p;
	}

	public Pion getPion(){
		return this.pIA;
	}

	@Override
	public Etat lecture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ecrire(Etat e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean egalite(Etat e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int eval0(EtatP4 e, Joueur p) {
		// TODO Auto-generated method stub
		return 0;
	}
}
