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
		System.out.println("test " + e.getJcourant().getNom());
		int nb = 0;
		selection(e, j.getJ1(), j.getJ2(), e.getJcourant());
		selection(e, j.getJ1(), j.getJ2(), e.getJcourant());
		selection(e, j.getJ1(), j.getJ2(), e.getJcourant());
		selection(e, j.getJ1(), j.getJ2(), e.getJcourant());
		for (EtatP4 ee : etatParcouru) {
			ee.affichage();
			System.out.println(ee.bValeur());
		}
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
		double max = Integer.MIN_VALUE;
		if (etatParcouru.size() == 0) {
			etatParcouru.add(e);
			EtatP4 et = e.choixRandom(jc);
			et.addParent(e);
			et.createSuccesseur();
			etatParcouru.add(et);
			
			int size = etatParcouru.size() -1;
			etatParcouru.get(size).addRecompense(etatParcouru.get(size).marcheAleatoire(j1, j2, jc));
		
			
		}
		else {
			ArrayList<Integer> indice = new ArrayList<>();
			int index = 0;
			for (EtatP4 et : etatParcouru) {
				if (et.bValeur() == max) list.add(et);
				if (et.bValeur() > max) {
					max = et.bValeur();
					list.clear();
					indice.clear();
					list.add(et);
					indice.add(index);
				}
				index++;
			}
				
			int taille = (list.size()- 1);
			int r = (int) (Math.random() *  (taille -1)) ;
			EtatP4 eCourant = etatParcouru.get(indice.get(r));
			// cas ou le noeud avec la plus grande bValeur n a developpe aucun fils
			if (eCourant.getListSucc().size() == 0) {
				eCourant.createSuccesseur();
				EtatP4 et = eCourant.choixRandom(jc);
				et.addParent(eCourant);
				et.createSuccesseur();
				etatParcouru.add(et);
				
				eCourant.affichage();
				System.out.println();
				et.affichage();
				
				int size = etatParcouru.size() -1;
				etatParcouru.get(size).addRecompense(etatParcouru.get(size).marcheAleatoire(j1, j2, jc));
			} else {
				
				//check si tous les fils nont pas tous ete developpe
				boolean allCheck = true;
				for (EtatP4 eDeveloppe : eCourant.getListSucc()) {
					if (!eDeveloppe.getVisite()) allCheck = false;
				}
				
				if (!allCheck) {
					EtatP4 et = eCourant.choixEtatNonVisite();
					et.addParent(eCourant);
					eCourant.addRecompense(et.marcheAleatoire(j1, j2, jc));
				}
				
				
				
			}
			
			/*
			 if (eCourant.getNbVisite() > 6) {
			 
				EtatP4 et = eCourant.choixRandom(jc);
				et.addParent(eCourant);
				et.createSuccesseur();
				etatParcouru.add(et);
				int size = etatParcouru.size() -1;
				etatParcouru.get(size).addRecompense(etatParcouru.get(size).marcheAleatoire(j1, j2, jc));
			}
			
			/*
			boolean check = false;
			//System.out.println(listSucc);
			ArrayList<EtatP4> ll = successeur(this, jc);
			for (int i = 0; i < 7; i++) {
				System.out.println(i);
				System.out.println(listSucc.get(i).getPion().getPosX() + " " + 
						listSucc.get(i).getPion().getPosY() + " " + 
						ll.get(i).getPion().getPosX() + " "+ 
						ll.get(i).getPion().getPosY() );
			}
			
				while (!check) {
				if (nbVisite < 7) {
					
					for (EtatP4 ee : listSucc) {
						//System.out.println(ee.getPion().getPosX() + "   "+ etat.getPion().getPosX() + "  " + ee.getPion().getPosY()  + "  " + etat.getPion().getPosY());
						if (ee.getPion().getPosX() == etat.getPion().getPosX() && ee.getPion().getPosY() == etat.getPion().getPosY() && !ee.getVisite()) {
							ee.setVisite(true);
							check = true;
							nbVisite++;
						} else {
							etat = choixRandom(jc);
						}
					}
				} else {
					check = true;
				}
			}
			*/
		}
		
		
	}

	public static void main(String[] args){
		Puissance4 p4 = new Puissance4(); 
	}

}
