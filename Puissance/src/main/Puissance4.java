package main;
import java.util.ArrayList;
import java.util.Scanner;

import modele.etat.Etat;
import modele.etat.EtatP4;
import modele.jeu.Jeu;
import modele.joueur.Joueur;
import modele.joueur.JoueurP4;


public class Puissance4 {

	protected ArrayList<EtatP4> etatParcouru;
	public static long tempsRecherche;

	public Puissance4(){
		etatParcouru = new ArrayList<>();
		start();
	}
	public void start(){

		Jeu j = new Jeu("nam", "IA");
		System.out.println("Joueur 1: " + j.getJ1().getNom() +" ******* Joueur 2: " + j.getJ2().getNom() );
		j.setInitial();
		EtatP4 e = new EtatP4(j, j.getJ1());
		//e.setMax(false);
		//int nb = 0;

		boolean correct = false;
		System.out.print("Choisissez le temps de calcul de l'IA (echelle: 1000 = 1s)");
		Scanner scc = new Scanner(System.in);
		String ss = scc.next();
		while(!correct){
			if(e.isInteger(ss)){
				correct = true;
				Puissance4.tempsRecherche = Integer.parseInt(ss);
			}
			else{
				System.out.print("Choisissez le temps de calcul de l'IA (echelle: 1000 = 1s)");
				scc = new Scanner(System.in);
				ss = scc.next();
			}
		}
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
		System.out.println(e.getGagant().getNom() + " a win");
		System.out.println("end game");
	}


	public static EtatP4 selection (EtatP4 e, Joueur j1, Joueur j2, Joueur jc)  {

		Joueur jcc = (jc.getNom() == j1.getNom() ) ? j2 : j1;
		e.setJcourant(jcc);

		e.setMax(false);
		e.reset();
		if (!e.finJeu() && !e.rempli2()) e.createSuccesseur();
		long debut = System.currentTimeMillis();


		while (System.currentTimeMillis() <  (long)(debut + Puissance4.tempsRecherche) ) {

			EtatP4 eCourant = null;

			if (!e.finJeu() && !e.rempli2()) {
				// Q3 : si on a un noeud fils gagnant on le choisit
				for (EtatP4 ee : e.getListSucc()) {
					if (ee.finJeu() && ee.getMax()) {
						eCourant = ee; 
					}
				}
				if (eCourant == null) {
					// FIN Q3 sinon on fait selon mcts

					// cas ou tous les noeuds ont ete visite au moins 1 fois 
					// on choisit le noeud avec la meilleure bvaleur
					eCourant = e;
					while (eCourant.checkTousVisite() &&  !eCourant.finJeu() && !eCourant.rempli2()) {
						EtatP4 efils = eCourant.choixNoeudMax();
						eCourant = efils;
					}
					if (!eCourant.finJeu() && !eCourant.rempli2()) {
						if (!e.checkTousVisite() && e.getListSucc().size() > 0) {
							// cas ou il reste des fils non developpe on choisit parmi ceux la
							eCourant = e.choixEtatNonVisite(jc);
						} else if ( e.getListSucc().size() == 0) {
							// cas ou aucun fils n a ete developpe on en choisit 1 au hasard
							eCourant = e.choixRandom(jc);
						} 
					}
				}
			} else {
				eCourant = e;
			}
			eCourant.marcheAleatoire(j1, j2, jc);
		}
		EtatP4 efinal = e.choixBestMoy();
		java.text.DecimalFormat df = new java.text.DecimalFormat("###.##");
		System.out.println("NOMBRE SIMULATION : " + e.getNSimu() +"   TAUX : " + df.format(efinal.calculMoy()*100) + "%");
		return efinal; 
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
