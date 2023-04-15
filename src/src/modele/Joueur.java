package src.modele;
import java.awt.Color;
import java.util.Scanner;

public class Joueur {
	
	private String pseudo;
	private int score;
	private Tuile tuileCourante;
	private Color color;
	private int nbPartisan;
	private boolean iaBasique;
	private boolean hal9000;
	public Joueur() {
		this.score = 0;
		this.tuileCourante = null;
		nbPartisan = 10;
		hal9000 = false;
		iaBasique = false;
	}
	
	public void setIABasique(boolean b) {
		this.iaBasique = b;
	}
	
	public void setHAL9000(boolean b) {
		this.hal9000 = b;
	}
	
	public boolean getIABasique() {
		return this.iaBasique;
	}
	
	public boolean getHAL9000() {
		return this.hal9000;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getPartisan() {
		return nbPartisan;
	}
	
	public void setPartisan(int nbPartisan) {
		this.nbPartisan = nbPartisan;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public Tuile getTuileCourante() {
		return tuileCourante;
	}
	
	public void setTuileCourante(Tuile tuile) {
		tuileCourante = tuile;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	
	
	

}
