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

		System.out.println(e.getGagant().getNom() + " a win");
		System.out.println("end game");


	}


	public static EtatP4 selection (EtatP4 e, Joueur j1, Joueur j2, Joueur jc)  {


		e.setMax(false);
		e.createSuccesseur();
		long debut = System.currentTimeMillis();
		
		// temps pendant lequel lalgo cherche dans larbre en millieme de seconde
		long tempsRecherche = 3000;
		
		while (System.currentTimeMillis() <  (long)(debut + tempsRecherche) ) {

			EtatP4 eCourant = null;

			if (!e.checkTousVisite() && e.getListSucc().size() > 0) {
				// cas ou il reste des fils non developpe on choisit parmi ceux la
				eCourant = e.choixEtatNonVisite(jc);
			} else if ( e.getListSucc().size() == 0) {
				// cas ou aucun fils n a ete developpe on en choisit 1 au hasard
				eCourant = e.choixRandom(jc);
			} else {
				// cas ou tous les noeuds ont ete visite au moins 1 fois 
				// on choisit le noeud avec la meilleure bvaleur
				eCourant = e;
				while (eCourant.checkTousVisite() &&  !eCourant.finJeu() && !eCourant.rempli2()) {
					EtatP4 efils = eCourant.choixNoeudMax();
					eCourant = efils;
				}
			}
			EtatP4 etatchoix = eCourant;
			if (!eCourant.finJeu() && !eCourant.rempli2()) {
				if (!eCourant.checkTousVisite() && eCourant.getListSucc().size() > 0) {
					// cas ou il reste des fils non developpe on choisit parmi ceux la
					etatchoix = eCourant.choixEtatNonVisite(jc);
				} else if ( eCourant.getListSucc().size() == 0) {
					// cas ou aucun fils n a ete developpe on en choisit 1 au hasard
					etatchoix = eCourant.choixRandom(jc);
				} 
			}
			eCourant.addRecompense(etatchoix.marcheAleatoire(j1, j2, jc));
		}
		EtatP4 efinal = e.choixNoeudMax();
		java.text.DecimalFormat df = new java.text.DecimalFormat("###.##");
		System.out.println("TAUX : " + df.format(efinal.tauxVictoire()) + "%  NOMBRE SIMULATION : " + efinal.getNbSimu() + "   bValeur : " + efinal.bValeur()  + "   " + efinal.getJcourant().getNom());
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
