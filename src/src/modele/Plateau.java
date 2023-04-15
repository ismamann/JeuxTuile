package src.modele;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Plateau {
	
	private Tuile[][] plateau;
	 Tuile[] pioche;
	private int prochainePioche;
	private Tuile derniereTuilePioche;
	private int[] poseIA;
	
	public Plateau() {
		plateau = new Tuile[28][28];
		prochainePioche = 0;
		poseIA = new int[3];
	}
	
	public int[] getPoseIA() {
		return this.poseIA;
	}
	
	public void setTaillePlateau(int i, int j) {
		plateau =  new Tuile[i][j];
	}
	
	public Tuile[][] getPlateau() {
		return plateau;
	}
	
	public Tuile getDerniereTuilePioche() {
		return derniereTuilePioche;
	}
	
	public void melangePioche() {
		Random rd = new Random();
		for (int i = 0; i<pioche.length; i++) {
			int k = rd.nextInt(0, pioche.length);
			Tuile a = pioche[i];
			pioche[i] = pioche[k];
			pioche[k] = a;
		}
	}
	
	public boolean posable(Tuile aPoser, Joueur joueur, int i, int j) {
		int score = 0;
		ArrayList<Integer[]> direction = null;
		if (aPoser instanceof TuileDomino)  direction = ((TuileDomino)aPoser).getDirection();
		if (plateau[i][j] != null) return false;
		if (plateau[i-1][j] == null && plateau[i+1][j] == null && plateau[i][j-1] == null && plateau[i][j+1] == null) return false;
		if (plateau[i-1][j] != null) {
			if (!aPoser.posable(plateau[i-1][j], OrientationTuile.SUD)) return false;
			if (aPoser instanceof TuileDomino) {
				Integer[] t = direction.get(3);
				score += (int)t[0] + (int)t[1] + (int)t[2];
			}
		}
		
		if (plateau[i+1][j] != null) {
			if (!aPoser.posable(plateau[i+1][j], OrientationTuile.NORD)) return false;
			if (aPoser instanceof TuileDomino) {
				Integer[] t = direction.get(1);
				score += (int)t[0] + (int)t[1] + (int)t[2];
			}
		}
		if (plateau[i][j-1] != null) {
			if (!aPoser.posable(plateau[i][j-1], OrientationTuile.EST)) return false;
			if (aPoser instanceof TuileDomino) {
				Integer[] t = direction.get(0);
				score += (int)t[0] + (int)t[1] + (int)t[2];
			}
		}
		if (plateau[i][j+1] != null) {
			if (!aPoser.posable(plateau[i][j+1], OrientationTuile.OUEST)) return false;
			if (aPoser instanceof TuileDomino) {
				Integer[] t = direction.get(2);
				score += (int)t[0] + (int)t[1] + (int)t[2];
			}
		}
		if (aPoser instanceof TuileDomino) joueur.setScore(joueur.getScore()+score);
		return true;
	}
	
	public void poserTuile(Tuile aPoser, int i, int j) {
		plateau[i][j] = aPoser;
	}
	
	public boolean jeuFini() {
		return pioche[pioche.length-1] == null;
	}
	
	public Tuile piocher() {
		Tuile tuile = pioche[prochainePioche];
		derniereTuilePioche = tuile;
		pioche[prochainePioche] = null;
		prochainePioche++;
		return tuile;
	}
	
	public void attributionPiocheDomino() {
		pioche = TuileDomino.genereDomino();
	}
	
	public void attributionPiocheCarcassonne() {
		pioche = TuileCarcassonne.genereTuileCarcassonne();
		plateau = new Tuile[10][144];
		melangePioche();
	}
	
	public boolean iaBasique(Joueur joueur) {
		Random rd = new Random();
		int iRandom = 0;
		int jRandom = 0;
		boolean placeTrouve = false;
		for (int i = 1; i<plateau.length-1; i++) {
			iRandom = rd.nextInt(1, plateau.length-1);
			for (int j = 1; j<plateau[i].length-1; j++) {
				jRandom = rd.nextInt(1, plateau[i].length-1);
				if(plateau[iRandom-1][jRandom] != null || plateau[iRandom+1][jRandom] != null || plateau[iRandom][jRandom+1] != null || plateau[iRandom][jRandom-1] != null) {
					for (int k = 0; k<4; k++) {
						if (posable(derniereTuilePioche, joueur, iRandom, jRandom) && !placeTrouve) {
							placeTrouve = true;
							poseIA[0] = iRandom; poseIA[1] = jRandom; poseIA[2] = k;
						}
						derniereTuilePioche.tourner();
					}
					if (placeTrouve) return true;
				}
			}
		}
		return false;
	}
	
	public boolean hal9000(Joueur joueur) {
		poseIA[0] = -1; poseIA[1] = -1;
		int scoreDepart = joueur.getScore();
		int scoreFin = 0;
		for (int i = 1; i<plateau.length-1; i++) {
			for (int j = 1; j<plateau[i].length-1; j++) {
				if(plateau[i-1][j] != null || plateau[i+1][j] != null || plateau[i][j+1] != null || plateau[i][j-1] != null) {
					for (int k = 0; k<4; k++) {
						if (posable(derniereTuilePioche, joueur, i, j)) {
							if (joueur.getScore() - scoreDepart > scoreFin) {
								scoreFin = joueur.getScore();
								joueur.setScore(scoreDepart);
							    poseIA[0] = i; poseIA[1] = j; poseIA[2] = k;
							}
						}
						derniereTuilePioche.tourner();
					}
				}
			}
		}
		if (poseIA[0] != -1) {
			joueur.setScore(scoreFin);
		}
		return poseIA[0] != -1;
	}


}
