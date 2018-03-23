package modele.etat;

import java.util.ArrayList;
import java.util.Scanner;

import modele.jeu.Jeu;
import modele.jeu.Pion;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;

public class EtatP4 extends Etat {

	protected Joueur jcourant;
	protected int[][] plateau;
	protected Pion pIA;

	public EtatP4(Jeu j) {
		super(j);
		this.pIA = new Pion(0, 0);
		this.plateau = j.getPlateau();
	}

	public EtatP4(Jeu j, Joueur jc) {
		super(j);
		this.jcourant = jc;
		this.pIA = new Pion(0, 0);
		this.plateau = j.getPlateau();
	}
	
	public EtatP4(Jeu j,int [][] tab){
		super(j);
		this.pIA = new Pion(0, 0);
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
		for (int i = plateau.length -1; i >=0 ; i--) {
			for (int j = 0; j < plateau[0].length; j++) {
				System.out.print(plateau[i][j] + "\t");
			}
			System.out.println("");
		}
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
				if(etat.getPlateau()[j][i] == 0){
					tabSuc[j][i] = 1;
					EtatP4 etatSuc = new EtatP4(this.jeu, tabSuc);
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
			this.plateau[i][j] = 2;
		}

		if(joueur.getNom().equals(this.jeu.getJ2().getNom().toString())){
			this.plateau[i][j] = 1;
		}
		
	}
	

	@Override
	public void poserJetton(Joueur j, Jeu jeu){

		boolean estJouer = false;
		boolean valide = false;
		int indiceColone = 1;
		while(!valide){	
			if(!j.getNom().equals("IA")){
				Scanner sc = new Scanner(System.in);
				indiceColone = sc.nextInt();
			}
			int k = 0;
			if(0 < indiceColone && indiceColone < 8 ){
				indiceColone = indiceColone - 1;
				valide = true;
			}
			while( k < this.plateau.length-1 && !estJouer && valide){
				
				if(plateau[k][indiceColone] == 1 & plateau[k + 1][indiceColone] == 0){
					System.out.println(j.getNom() + " a joue!!!");
					setJcourant(j);
					EtatP4 etatfavorable = minimax(new EtatP4(jeu, j),0);
					if(j.getNom().equals("IA") &&  j.getNbCoupJoue() > 0){
						etatfavorable.setJcourant(j);
						int x =  etatfavorable.getPion().getPosX();
						int y = etatfavorable.getPion().getPosY();	
						System.out.println(x +" " + y);
						setValue(x + 1,y,j);
					}
					else{
						setValue(k + 1,indiceColone,j);
					}
					Pion p = new Pion(k+1, indiceColone);
					j.setNbCoupJoue(1);
					estJouer = true;
					((JoueurP4)j).ajouterPion(p);
					
					
				}
				if(plateau[k][indiceColone] == 0){
					System.out.println(j.getNom() + " a joue!!!");
					setJcourant(j);
					EtatP4 etatfavorable = minimax(new EtatP4(jeu, j),0);
					if(j.getNom().equals("IA") &&  j.getNbCoupJoue() > 0){	
						etatfavorable.setJcourant(j);
						System.out.println(etatfavorable.getPion());
						int x =  etatfavorable.getPion().getPosX();
						int y = etatfavorable.getPion().getPosY();	
						System.out.println(x +" " + y);
						setValue(x,y,j);
					}
					else{
						setValue(k,indiceColone,j);
					}
					Pion p = new Pion(k, indiceColone);
					j.setNbCoupJoue(1);
					estJouer = true;
					((JoueurP4)j).ajouterPion(p);
				}
				k++;
			}
		}
		
	}

	
	/*
	 * Parcours la liste de pion joué/placé
	 * et compte le nombre de coup gagnant qu'il peut faire pour chaque
	 * (non-Javadoc)
	 * @see modele.etat.Etat#eval0()
	 */
	@Override
	public int eval0(EtatP4 e) {
		// TODO Auto-generated method stub
		
		int coupGagnant = 0;
		System.out.println(e.getJcourant());
		if(e.getJcourant()!= null && ((JoueurP4)e.getJcourant()).getLp() !=null ){
			for (Pion p : ((JoueurP4)e.getJcourant()).getLp()) {
				coupGagnant += coupLigne(p);
				coupGagnant += coupColonne(p);
				coupGagnant += coupDiagonaleBGHD(p);
				coupGagnant += coupDiagonaleHGBD(p);
				System.out.println("Pour ce pion " + p.toString() + "  nombre de coup gagnant " + coupGagnant);
				setPion(p);
			}
		}
		
		return coupGagnant;
	}
	
	/**
	 * fonction evaluation
	 * @param c = entier qui indique le nombre de coup dans le futur
	 * 		  e = etat
	 * @return  entier : coup gagant
	 */
	public int evaluation(int c, EtatP4 e){
		
		ArrayList<EtatP4> emsembleEtat = new ArrayList<>();
		int score, score_max, score_min;
		/*if(e.estFinal()){
			return
		}*/
		
		if(c == 0){
			return eval0(e);
		}
		/*
		emsembleEtat = successeur(e);
		if(e.jcourant.equals("IA")){
			score_max = -1000;
			for(EtatP4)
		}*/
		return 0;
	}
	
	/**
	 * 
	 * @param e = Etat du jeu
	 * @param c = nb de coup dans le futur
	 * @return etat favorable
	 */
	public EtatP4 minimax(EtatP4 e, int c){
		
		ArrayList<EtatP4> ensembleEtat = successeur(e);
		EtatP4 e_sortie = null;
		int score_max = -1000;
		
		if(e.getJcourant().getNom().equals("IA")){
			for(EtatP4 p4 : ensembleEtat){
				int score = evaluation(c, p4);
				if(score > score_max){
					e_sortie = p4;
					score_max = score;
					//e_sortie.setPion(p)
				}
			}
			
			
		}
		return e_sortie;
	}

	public int coupLigne(Pion p) {
		
		// verif quel joueur est en train de jouer et on atribue le joueur opposant
		int joueur;
		if(jcourant.getNom().equals(this.jeu.getJ1().getNom().toString())) joueur = 1;
		else joueur = 2;
		
		// nb de coup gagnant en ligne par rapport à un pion
		int nb = 0;
		
		// borne correspondant soit aux limite de tableau
		int borneGauche = 0, borneDroite = 7;
		
		if (p.getPosX() - 3 >= 0) borneGauche = p.getPosX()-3; 
		if (p.getPosX() + 3 < plateau.length) borneDroite = p.getPosX()+3; 
		
		for (int i = borneGauche; i <= borneDroite - 3; i++) {
			boolean ligneGagnante = true;
			
			for (int j = i; j < i+4; j++) {
				// on regarde si c'est un pion adverse 
				if ( plateau[i][p.getPosY()] == joueur) ligneGagnante = false;
			}
			if (ligneGagnante) nb++;
		}
		
		return nb;
	}
	
	public int coupColonne(Pion p) {
		
		// verif quel joueur est en train de jouer et on atribue le joueur opposant
		int joueur;
		if(jcourant.getNom().equals(this.jeu.getJ1().getNom().toString())) joueur = 1;
		else joueur = 2;
		
		// nb de coup gagnant en ligne par rapport à un pion
		int nb = 0;
		
		// borne correspondant soit aux limite de tableau
		int borneGauche = 0, borneDroite = 6;
		
		if (p.getPosY() - 3 >= 0) borneGauche = p.getPosY()-3; 
		if (p.getPosY() + 3 < plateau[0].length) borneDroite = p.getPosY()+3; 
		
		for (int i = borneGauche; i <= borneDroite - 3; i++) {
			boolean ligneGagnante = true;
			
			for (int j = i; j < i+4; j++) {
				// on regarde si c'est un pion adverse 
				if ( plateau[p.getPosX()][j] == joueur) ligneGagnante = false;
			}
			if (ligneGagnante) nb++;
		}
		
		return nb;
	}
	
	
	/*
	 * Diagonale qui part du haut a gauche et qui finit en bas a droite
	 */
	public int coupDiagonaleHGBD(Pion p) {

		ArrayList<Pion> ltest = new ArrayList<>();
		// verif quel joueur est en train de jouer et on atribue le joueur opposant
		int joueur;
		if(jcourant.getNom().equals(this.jeu.getJ1().getNom().toString())) joueur = 1;
		else joueur = 2;
		
		// nb de coup gagnant en ligne par rapport à un pion
		int nb = 0;
		
		// borne correspondant soit aux limite de tableau
		int borneGaucheX, borneGaucheY, borneDroiteX, borneDroiteY;
		
		
		// calcul borne diago gauche
		if ( p.getPosX()-3 >= 0  && p.getPosY() + 3 < plateau[0].length) {
			borneGaucheX = p.getPosX() - 3;
			borneGaucheY = p.getPosY() + 3;
		} else {
			int min = Math.min( p.getPosX() , plateau[0].length - 1 - p.getPosY());
			
			borneGaucheX = p.getPosX() - min;
			borneGaucheY = p.getPosY() + min;
		}
		
		
		
		// calcul borne diago droite 
		if ( p.getPosY()-3 >= 0  && p.getPosX() + 3 < plateau.length) {
			borneDroiteX = p.getPosX() + 3;
			borneDroiteY = p.getPosY() - 3;
		} else {
			
			// pas fini
			int min = Math.min( plateau.length - 1 - p.getPosX()  , p.getPosY());
			
			borneDroiteX = p.getPosX() + min;
			borneDroiteY = p.getPosY() - min;
		}
		
		// longueur de la diagonale
		int distance = borneDroiteX - borneGaucheX; 
		
		
		for (int i = 0; i <= distance - 3; i++) {
			boolean ligneGagnante = true;
			ltest.clear();
			for (int j = i; j < i+4; j++) {
				
			if ( plateau[borneGaucheX + j][ borneGaucheY - j] == joueur) ligneGagnante = false;
			ltest.add(new Pion(borneGaucheX+j, borneGaucheY+j));
			}
			System.out.println();
			for(Pion m : ltest) {
				System.out.print(m.toString());
			}
			System.out.println();
			if (ligneGagnante) nb++;
		}
		
		return nb;
	}
	
	/*
	 * Diagonale qui part du bas a gauche et qui finit en haut a droite
	 */
	public int coupDiagonaleBGHD(Pion p) {
		
		// verif quel joueur est en train de jouer et on atribue le joueur opposant
		int joueur;
		if(jcourant.getNom().equals(this.jeu.getJ1().getNom().toString())) joueur = 1;
		else joueur = 2;
		
		// nb de coup gagnant en ligne par rapport à un pion
		int nb = 0;
		
		// borne correspondant soit aux limite de tableau
		int borneGaucheX, borneGaucheY, borneDroiteX, borneDroiteY;
		
		
		// calcul borne diago gauche
		if ( p.getPosX()-3 >= 0  && p.getPosY() - 3 >= 0) {
			borneGaucheX = p.getPosX() - 3;
			borneGaucheY = p.getPosY() - 3;
		} else {
			int min = Math.min( p.getPosX() , p.getPosY());
			
			borneGaucheX = p.getPosX() - min;
			borneGaucheY = p.getPosY() - min;
		}
		
		
		// calcul borne diago droite 
		if ( p.getPosY()+3 < plateau[0].length  && p.getPosX() + 3 < plateau.length) {
			borneDroiteX = p.getPosX() + 3;
			borneDroiteY = p.getPosY() + 3;
		} else {
			
			int min = Math.min( plateau.length - 1 - p.getPosX() , plateau[0].length - 1 - p.getPosY() );
			
			borneDroiteX = p.getPosX() + min;
			borneDroiteY = p.getPosY() + min;
		}
		
		// longueur de la diagonale
		int distance = borneDroiteX - borneGaucheX; 
		
		
		
		
		for (int i = 0; i <= distance - 3; i++) {
			boolean ligneGagnante = true;
			
			for (int j = i; j < i+4; j++) {
			if ( plateau[borneGaucheX + i][ borneGaucheY + i] == joueur) ligneGagnante = false;
			}
			if (ligneGagnante) nb++;
		}
		
		return nb;
	}

	public void setPion(Pion p){
		this.pIA = p;
	}
	
	public Pion getPion(){
		return this.pIA;
	}
	/*
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
*/
}
