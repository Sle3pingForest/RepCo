package modele.jeu;

public class Pion {
	
	protected int posx,posy;
	
	public Pion(int x, int y) {
		posx = x;
		posy = y;
	}
	
	public int getPosX() {
		return posx;
	}
	
	public int getPosY() {
		return posy;
	}
	
	public String toString(){
		return this.posx + "," + this.posy;
	}

}
