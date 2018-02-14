import modele.etat.Etat;
import modele.etat.EtatP4;
import modele.jeu.Jeu;


public class Puissance4 {
	
	public Puissance4(){
		Jeu j = new Jeu("nam", "andre");
		j.setInitial();
		Etat e = new EtatP4(j);
		e.affichage();
	}
	
	 public static void main(String[] args){
		 Puissance4 p4 = new Puissance4(); 
		 
		 Jeu j = new Jeu("nam", "andre");
		 
		 EtatP4 e = new EtatP4(j, j.getJ1());
		 
		 EtatP4 e2 = new EtatP4(j, j.getJ1());
		 
		 System.out.println("test 1 " + e.egalite(e2));
		 
		 Jeu j2 = new Jeu("nam", "andre");
		 
		 e2 = new EtatP4(j2, j2.getJ1());
		 
		 System.out.println("test 2 " +  e.egalite(e2));
	 }

}
