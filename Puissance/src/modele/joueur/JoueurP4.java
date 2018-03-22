package modele.joueur;
import java.util.ArrayList;

import modele.jeu.Pion;


public class JoueurP4 extends Joueur {
	
	ArrayList<Pion> lp;

	public JoueurP4(String j1) {
		super(j1);
		lp = new ArrayList<Pion>();
	}

	public ArrayList<Pion> getLp() {
		return lp;
	}

	public void setLp(ArrayList<Pion> lp) {
		this.lp = lp;
	}
	
	

	
	
	
}
