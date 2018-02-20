import java.util.ArrayList;

import modele.etat.Etat;
import modele.etat.EtatP4;
import modele.jeu.Jeu;


public class Puissance4 {

	public Puissance4(){
		start();
	}
	public void start(){

		Jeu j = new Jeu("nam", "andre");
		j.setInitial();
		Etat einit = new EtatP4(j);
		j.setValue(5,1);
		einit.affichage();

		EtatP4 etatSuc = new EtatP4(j);
		ArrayList<Etat> listetatSuc = etatSuc.successeur(einit);
		for( Etat e : listetatSuc){
			e.affichage();
		}
		
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
