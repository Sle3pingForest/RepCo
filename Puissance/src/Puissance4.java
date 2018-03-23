import java.util.ArrayList;

import modele.etat.Etat;
import modele.etat.EtatP4;
import modele.jeu.Jeu;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;


public class Puissance4 {

	public Puissance4(){
		start();
	}
	public void start(){

		Jeu j = new Jeu("nam", "IA");
		System.out.println("Joueur 1: " + j.getJ1().getNom() +" ******* Joueur 2: " + j.getJ2().getNom() );
		j.setInitial();
		EtatP4 e = new EtatP4(j, j.getJ1());
		e.affichage();
		
		boolean estRemplie = e.rempli();
		while(!estRemplie){
			e.poserJetton(j.getJ1(), j);
			e.affichage();
			Joueur i = j.getJ1();
			Joueur i2 = j.getJ2();
			for(int k = 0; k < ((JoueurP4)i).getLp().size();++k){
				//System.out.println("Pion "+k +"("+ ((JoueurP4)i).getLp().get(k) +")") ;
			}
			e.poserJetton(j.getJ2(),j);
			e.affichage();
			for(int k = 0; k < ((JoueurP4)i).getLp().size();++k){
				//System.out.println("Pion "+k +"("+ ((JoueurP4)i2).getLp().get(k) +")") ;
			}
			if(j.getJ1().getNbCoupJoue() + j.getJ2().getNbCoupJoue() == 42){
				estRemplie = true;
				System.out.println("end");
			}
			
		}
		System.out.println("end game");
		
		
		
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

	public static void main(String[] args){
		Puissance4 p4 = new Puissance4(); 
	}

}
