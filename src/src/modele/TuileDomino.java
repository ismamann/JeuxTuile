package src.modele;
import java.util.ArrayList;
import java.util.Random;

public class TuileDomino extends Tuile {

	@SuppressWarnings("unchecked")
	TuileDomino() {
		super(new Integer[3], new Integer[3], new Integer[3], new Integer[3]);
	}

	
	@Override
	public String toString() {
		String affichage = "";
		ArrayList<Integer[]> t = getDirection();
	
		affichage = "* "+t.get(3)[0]+" "+t.get(3)[1]+" "+t.get(3)[2]+" *\n"
		  + t.get(0)[0]+" * * * "+t.get(2)[0]+"\n"
		  + t.get(0)[1]+" * * * "+t.get(2)[1]+"\n"
		  + t.get(0)[2]+" * * * "+t.get(2)[2]+"\n"
		         +  "* "+t.get(1)[0]+" "+t.get(1)[1]+" "+t.get(1)[2]+" *";
		return affichage;
	}
	
	public String premiereLigne() {
		ArrayList<Integer[]> t = getDirection();
		return "* "+t.get(3)[0]+" "+t.get(3)[1]+" "+t.get(3)[2]+" *";
	}
	
	public String deuxiemeLigne() {
		ArrayList<Integer[]> t = getDirection();
		return t.get(0)[0]+" * * * "+t.get(2)[0];
	}
	
	public String troisiemeLigne() {
		ArrayList<Integer[]> t = getDirection();
		return  t.get(0)[1]+" * * * "+t.get(2)[1];
	}
	
	public String quatriemeLigne() {
		ArrayList<Integer[]> t = getDirection();
		return  t.get(0)[2]+" * * * "+t.get(2)[2];
	}
	
	public String derniereLigne() {
		ArrayList<Integer[]> t = getDirection();
		return "* "+t.get(1)[0]+" "+t.get(1)[1]+" "+t.get(1)[2]+" *";
	}
	
	public boolean posable(Tuile tuile, OrientationTuile orientation) {
		int k = 0;
		ArrayList<Integer[]> t = getDirection();
		ArrayList<Integer[]> tuile1 = tuile.getDirection();
		switch (orientation) {
			case NORD : k = 1; break;
			case SUD : k = 3; break;
			case OUEST : k = 2; break;
			default : k = 0; break;
		}

		for (int i = 0; i<3; i++) {
			if (t.get(k)[i] != tuile1.get(orientation.ordinal())[i]) return false;
		}
		
		return true;
	}
	
	
	public static TuileDomino[] genereDomino() {
		TuileDomino[] pioche = new TuileDomino[28];
		Random rd = new Random();
		int chance = 0;
		int index = 0;
		while(index != 28) {
			chance = rd.nextInt(0,11);
			TuileDomino domino1;
			if (index != 0) domino1 = pioche[index-1];
			else domino1 = new TuileDomino();
			TuileDomino domino2 = new TuileDomino();

			for (int i = 0; i<domino2.getNord().length; i++) {
				int chiffre = rd.nextInt(0,10);
				int chiffre1 = rd.nextInt(0,10);
				int chiffre2 = rd.nextInt(0,10);
				if (index == 0) {
					domino1.getNord()[i] = chiffre; domino1.getSud()[i] = chiffre1; domino1.getOuest()[i] = chiffre2; domino1.getEst()[i] = rd.nextInt(0, 10);
				} else if (chance <= 3 && index >= 2) {
					domino2.getNord()[i] = pioche[index-2].getOuest()[i]; domino2.getSud()[i] = domino1.getEst()[i]; domino2.getOuest()[i] = rd.nextInt(0, 10); domino2.getEst()[i] = rd.nextInt(0, 10);
			    } else if (chance <= 6) {
					domino2.getNord()[i] = domino1.getOuest()[i]; domino2.getSud()[i] = rd.nextInt(0, 10); domino2.getOuest()[i] = rd.nextInt(0, 10); domino2.getEst()[i] = rd.nextInt(0, 10);
				} else if (chance <= 9) {
					domino2.getNord()[i] = domino1.getNord()[i]; domino2.getSud()[i] = domino1.getSud()[i]; domino2.getOuest()[i] = rd.nextInt(0, 10); domino2.getEst()[i] = rd.nextInt(0, 10);
				} else if (chance > 9){
					domino2.getNord()[i] = domino1.getNord()[i]; domino2.getSud()[i] = domino1.getSud()[i]; domino2.getOuest()[i] = domino1.getOuest()[i]; domino2.getEst()[i] = rd.nextInt(0, 10);
				}
			}
			for (int i = 0; i<rd.nextInt(0,5); i++) {
				domino2.tourner();
			}

				if (index == 0) {
					pioche[index] = domino1;
					index++;
			}   else {
					pioche[index] = domino2;
					index++;
				}
				
			}
	//	}
		return pioche;
		
	}

}
