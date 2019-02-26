import java.util.ArrayList;

import modele.etat.Etat;
import modele.etat.EtatP4;
import modele.jeu.Jeu;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;


public class Puissance4 {
	
	protected ArrayList<EtatP4> etatParcouru;

	public Puissance4(){
		etatParcouru = new ArrayList<>();
		start();
	}
	public void start(){

		Jeu j = new Jeu("nam", "IA");
		System.out.println("Joueur 1: " + j.getJ1().getNom() +" ******* Joueur 2: " + j.getJ2().getNom() );
		j.setInitial();
		EtatP4 e = new EtatP4(j, j.getJ1());
		selection(e, j.getJ1(), j.getJ2(), e.getJcourant());
		/*
		boolean estRemplie = e.rempli();
		while(!estRemplie){
			estRemplie = e.finJeu();
			if(!e.finJeu()){
				e.poserJetton(j.getJ1(), j);

				selection(e);
				//e.affichage();
			}
			Joueur i = j.getJ1();
			Joueur i2 = j.getJ2();
			if(!e.finJeu()){
				e.poserJetton(j.getJ2(),j);

				selection(e);
				//e.affichage();
			}
			

		}
		
		System.out.println(e.getGagant().getNom() + "a win");
		System.out.println("end game");
		*/
		
		
/*
		
		ArrayList<EtatP4> listetatSuc = e.successeur();
		for( EtatP4 et : listetatSuc){
			System.out.println("Joueur 1: " + j.getJ1().getNom() +" ******* Joueur 2: " + j.getJ2().getNom() );
			et.affichage();
		}*/
		
		/*
		EtatP4 e = new EtatP4(j, j.getJ1());
		EtatP4 e2 = new EtatP4(j, j.getJ1());
		System.out.println("test1: egalite joueur" + e.egalite(e2));
		Jeu j2 = new Jeu("nam", "andre");
		e2 = new EtatP4(j2, j2.getJ1());
		System.out.println("test2: egalite joueur " +  e.egalite(e2));*/

	}
	
	
	
	public void selection (EtatP4 e, Joueur j1, Joueur j2, Joueur jc)  {
		
		ArrayList<EtatP4> list = new ArrayList<>();
		double max = -1;
		EtatP4 choix;
		if (etatParcouru.size() == 0) {
			choix = e.choixRandom();
			etatParcouru.add(choix);
			

			int size = etatParcouru.size() -1;
			etatParcouru.get(size).marcheAleatoire(j1, j2, jc);
		}
		else {
			for (EtatP4 et : etatParcouru) {
				if (et.bValeur() == max) list.add(et);
				if (et.bValeur() > max) {
					max = et.bValeur();
					list.clear();
					list.add(et);
				}
			}
				
			int taille = (list.size()- 1);
			int r = (int) (Math.random() *  (taille -1)) ;
			etatParcouru.add(list.get(r));
			int size = etatParcouru.size() -1;
			selection(etatParcouru.get(size),j1, j2, jc);
		}
		
		
		
	}

	public static void main(String[] args){
		Puissance4 p4 = new Puissance4(); 
	}

}
