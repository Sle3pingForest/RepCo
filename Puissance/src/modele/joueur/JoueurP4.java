package modele.joueur;
import java.util.ArrayList;

import modele.jeu.Pion;


public class JoueurP4 extends Joueur {
	
	protected ArrayList<Pion> lp;

	public JoueurP4(String j1) {
		super(j1);
		this.lp = new ArrayList<Pion>();
	}
	public JoueurP4(String j1,int num) {
		super(j1,num);
		this.lp = new ArrayList<Pion>();
	}
	

	public ArrayList<Pion> getLp() {
		return this.lp;
	}

	public void setLp(ArrayList<Pion> lp) {
		this.lp = lp;
	}
	
	
	public void ajouterPion(Pion p){
		this.lp.add(p);
	}
	
	

}
