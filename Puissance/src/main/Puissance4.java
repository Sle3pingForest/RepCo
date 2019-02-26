package main;
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
		e.setMax(true);
		System.out.println("test " + e.getJcourant().getNom());
		int nb = 0;
		
		
		
		boolean estRemplie = e.rempli();
		while(!estRemplie){
			estRemplie = e.finJeu();
			if(!e.finJeu()){
				e.poserJetton(j.getJ1(), j);
				e.affichage();
			}
			Joueur i = j.getJ1();
			Joueur i2 = j.getJ2();
			if(!e.finJeu()){
				e.poserJetton(j.getJ2(),j);
				e.affichage();
			}


		}

		System.out.println(e.getGagant().getNom() + "a win");
		System.out.println("end game");
		 

	}



	public static EtatP4 selection (EtatP4 e, Joueur j1, Joueur j2, Joueur jc)  {

			int nb = 0;
			e.createSuccesseur();
			
			while (nb < 1000) {
			double max = Integer.MIN_VALUE;	
			ArrayList<EtatP4> list = new ArrayList<>();
			ArrayList<Integer> indice = new ArrayList<>();
			int index = 0;
			for (EtatP4 et : e.getListSucc()) {
				if (et.bValeur() == max) list.add(et);
				if (et.bValeur() > max ) {
					max = et.bValeur();
					list.clear();
					indice.clear();
					list.add(et);
					indice.add(index);
				}
				index++;
			}
			
			int taille =(list.size()- 1);
			int r = (int) (Math.random() *  (taille -1)) ;
			EtatP4 eCourant = list.get(r);
			eCourant.addParent(e);

			while (eCourant.getListSucc().size() > 0) {
				EtatP4 efils = eCourant.choixNoeudMax();
				efils.addParent(eCourant);
				eCourant = efils;
			}
			if (eCourant.getListSucc().size() == 0 && !eCourant.finJeu() && !eCourant.rempli2()) {
				eCourant.createSuccesseur();
				//System.out.println(eCourant.getListSucc().size() + " BONJOUR");
			} 
			//if (eCourant.getListSucc().size() > 0) {
				//System.out.println("TES DEDANS");
				EtatP4 etatchoix = eCourant.choixRandom(jc);
				etatchoix.addParent(eCourant);
				eCourant.addRecompense(etatchoix.marcheAleatoire(j1, j2, jc));
				/*eCourant.affichage();
				System.out.println(eCourant.bValeur());
				System.out.println(nb);*/
				nb++;
			//}
		}
		/*
		EtatP4 eparcours = e;
		
		affiche(eparcours);
		*/
		
		affiche(e);
		System.out.println(e.choixNoeudMax().bValeur());
		return e.choixNoeudMax(); 

	}
	
	public static void affiche(EtatP4 e) {
		if (e.getListSucc().size() == 0) {
			e.affichage();
			System.out.println(e.getJcourant().getNom() + "    " + e.getMax());
			System.out.println();
			
		} else {
			
			EtatP4 meilleur = e.choixNoeudMax();
			affiche(meilleur);
		}
		
	}

	public static void main(String[] args){
		Puissance4 p4 = new Puissance4(); 
	}

}
