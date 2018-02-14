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
	 }

}
