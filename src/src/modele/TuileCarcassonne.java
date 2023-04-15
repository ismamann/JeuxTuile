package src.modele;
import java.util.ArrayList;

public class TuileCarcassonne extends Tuile {

	private int numero;
	private boolean partisan;
	TuileCarcassonne() {
		super(new ElementCarcassonne[3], new ElementCarcassonne[3], new ElementCarcassonne[3], new ElementCarcassonne[3]);
		partisan = false;
	}
	
	public boolean getPartisan() {
		return partisan;
	}
	
	public void setPartisan(boolean partisan) {
		this.partisan = partisan;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public static TuileCarcassonne[] genereTuileCarcassonne() {
		TuileCarcassonne[] pioche = new TuileCarcassonne[68];
		Ville villeCarte1 = new Ville(true); Ville villeCarte2 = new Ville(true); Champ champCarte2 = new Champ();
		Ville villeCarte3 = new Ville(false); Champ champCarte3 = new Champ(); Ville villeCarte4 = new Ville(false); Champ fChampCarte4 = new Champ();
		Champ sChampCarte4 = new Champ(); Champ ChampCarte5 = new Champ();
		
		for (int i = 0; i<pioche.length; i++) pioche[i] = new TuileCarcassonne();
		pioche[0].numero = 1; pioche[1].numero = 3; pioche[2].numero = 4; pioche[3].numero = 6; pioche[4].numero = 21; 
		for (int i = 0; i<3; i++) {
			 pioche[0].getNord()[i] = villeCarte1; pioche[0].getEst()[i] = villeCarte1; pioche[0].getSud()[i] = villeCarte1; pioche[0].getOuest()[i] = villeCarte1;
			 pioche[1].getNord()[i] = villeCarte2; pioche[1].getEst()[i] = villeCarte2; pioche[1].getSud()[i] = champCarte2; pioche[1].getOuest()[i] = villeCarte2;
			pioche[2].getNord()[i] = villeCarte3; pioche[2].getEst()[i] = villeCarte3; pioche[2].getOuest()[i] = villeCarte3;
			pioche[3].getNord()[i] = fChampCarte4; pioche[3].getEst()[i] = villeCarte4; pioche[3].getSud()[i] = sChampCarte4; pioche[3].getOuest()[i] = villeCarte4;
			if (i == 1) {
				pioche[2].getSud()[i] = new Route();
				pioche[4].getNord()[i] = new Route(true);
				pioche[4].getSud()[i] = new Route(true);
				pioche[4].getEst()[i] = new Route(true);
				pioche[4].getOuest()[i] = new Route(true);
			}
			else {
				pioche[2].getSud()[i] = champCarte3;
				pioche[4].getNord()[i] = ChampCarte5;
				pioche[4].getSud()[i] = ChampCarte5;
				pioche[4].getEst()[i] = ChampCarte5;
				pioche[4].getOuest()[i] = ChampCarte5;
			}
			
		}
		
		int index = 5;
		for (int i = 0; i<3; i++) {
			Champ champ = new Champ(); Ville ville = new Ville(false);
			Champ champ1 = new Champ(); Ville ville1 = new Ville(false);
			Champ champ2 = new Champ(); Ville ville2 = new Ville(false); Route route2 = new Route();
			Champ champ3 = new Champ(); Ville ville3 = new Ville(false); Ville ville4 = new Ville(false);
			Champ champ5 = new Champ(); Ville ville5 = new Ville(false); Route route5 = new Route();
			Champ champ6 = new Champ(); Ville ville6 = new Ville(false); Route route6 = new Route();
			Champ champ7 = new Champ(); Ville ville7 = new Ville(false); Route route7 = new Route();
			Champ champ8 = new Champ(); Ville ville8 = new Ville(false); Route route8 = new Route();
			Champ champ9 = new Champ(true); Champ champ10 = new Champ();
			pioche[index].numero = 2;  pioche[index+3].numero = 8; pioche[index+6].numero = 10; pioche[index+9].numero = 13;
			 pioche[index+12].numero = 15; pioche[index+15].numero = 16; pioche[index+18].numero = 17; pioche[index+21].numero = 18;
			 pioche[index+24].numero = 19; pioche[index+27].numero = 22; 
			for (int j = 0; j<3; j++) {
				 pioche[index].getNord()[j] = ville; pioche[index].getEst()[j] = ville; pioche[index].getSud()[j] = champ; pioche[index].getOuest()[j] = ville;
				 pioche[index+3].getNord()[j] = ville1; pioche[index+3].getEst()[j] = champ1; pioche[index+3].getSud()[j] = champ1; pioche[index+3].getOuest()[j] = ville1;
				 pioche[index+6].getNord()[j] = ville2; pioche[index+6].getOuest()[j] = ville2;
				 pioche[index+9].getNord()[j] = champ3; pioche[index+9].getEst()[j] = ville3; pioche[index+9].getSud()[j] = champ3; pioche[index+9].getOuest()[j] = ville4;
				 pioche[index+12].getNord()[j] = ville5; pioche[index+12].getOuest()[j] = champ5;
				 pioche[index+15].getNord()[j] = ville6; pioche[index+15].getEst()[j] = champ6;
				 pioche[index+18].getNord()[j] = ville7;
				 pioche[index+21].getNord()[j] = ville8; pioche[index+21].getSud()[j] = champ8;
				 pioche[index+24].getNord()[j] = champ9; pioche[index+24].getEst()[j] = champ9; pioche[index+24].getSud()[j] = champ9; pioche[index+24].getOuest()[j] = champ9;
				 pioche[index+27].getNord()[j] = champ10;
				if (j == 1) {
					pioche[index+6].getEst()[j] = route2;
					pioche[index+6].getSud()[j] = route2;
					pioche[index+12].getEst()[j] = route5;
					pioche[index+12].getSud()[j] = route5; 
					pioche[index+15].getOuest()[j] = route6;
					pioche[index+15].getSud()[j] = route6; 
					pioche[index+18].getEst()[j] = route7;
					pioche[index+18].getOuest()[j] = route7;
					pioche[index+18].getSud()[j] = route7; 
					pioche[index+21].getEst()[j] = route8;
					pioche[index+21].getOuest()[j] = route8;
					pioche[index+27].getSud()[j] = new Route(true); 
					pioche[index+27].getEst()[j] = new Route(true);
					pioche[index+27].getOuest()[j] = new Route(true);
				} else {
					pioche[index+6].getEst()[j] = champ2;
					pioche[index+6].getSud()[j] = champ2;
					pioche[index+12].getEst()[j] = champ5;
					pioche[index+12].getSud()[j] = champ5; 
					pioche[index+15].getOuest()[j] = champ6;
					pioche[index+15].getSud()[j] = champ6; 
					pioche[index+18].getEst()[j] = champ7;
					pioche[index+18].getOuest()[j] = champ7;
					pioche[index+18].getSud()[j] = champ7; 
					pioche[index+21].getEst()[j] = champ8;
					pioche[index+21].getOuest()[j] = champ8;
					pioche[index+27].getSud()[j] = champ10;
					pioche[index+27].getEst()[j] = champ10;
					pioche[index+27].getOuest()[j] = champ10;
				}
			}
			index++;
		}
		
		index += 27;
		for (int i = 0; i<2; i++) {
			Ville ville = new Ville(true); 
			Ville ville1 = new Ville(true); Champ champ1 = new Champ();
			Ville ville2 = new Ville(true); Champ champ2 = new Champ();
			Ville ville3 = new Ville(true); Champ champ3 = new Champ(); Route route3 = new Route();
			Ville ville4 = new Ville(false); Ville ville5 = new Ville(false); Champ champ5 = new Champ();
			Champ champ6 = new Champ(); Route route6 = new Route(true);
			pioche[index].numero = 5; pioche[index+2].numero = 7; pioche[index+4].numero = 9; 
			pioche[index+6].numero = 11; pioche[index+8].numero = 12; pioche[index+10].numero = 20;
			for (int j = 0; j<3; j++) {
				pioche[index].getNord()[j] = ville; pioche[index].getEst()[j] = ville; pioche[index].getOuest()[j] = ville;
				pioche[index+2].getNord()[j] = champ1; pioche[index+2].getEst()[j] = ville1; pioche[index+2].getOuest()[j] = ville1; pioche[index+2].getSud()[j] = champ1;  	
				pioche[index+4].getNord()[j] = ville2; pioche[index+4].getEst()[j] = champ2; pioche[index+4].getOuest()[j] = ville2; pioche[index+4].getSud()[j] = champ2;  				
				pioche[index+6].getNord()[j] = ville3; pioche[index+6].getOuest()[j] = ville3;		
				pioche[index+8].getNord()[j] = ville4; pioche[index+8].getEst()[j] = ville5; pioche[index+8].getOuest()[j] = champ5; pioche[index+8].getSud()[j] = champ5; 
				pioche[index+10].getNord()[j] = champ6; pioche[index+10].getEst()[j] = champ6; pioche[index+10].getOuest()[j] = champ6;  	

				if (j == 1) {
					pioche[index].getSud()[j] = new Route();
					pioche[index+6].getSud()[j] = route3;  
					pioche[index+6].getEst()[j] = route3;
					pioche[index+10].getSud()[j] = route6;
				} else {
					pioche[index].getSud()[j] = new Champ();
					pioche[index+6].getEst()[j] = champ3;
					pioche[index+6].getSud()[j] = champ3;  
					pioche[index+10].getSud()[j] = champ6;
				}

			}
			index++;
		}
		index += 10;
		for (int j = 0; j<5; j++) {
			Champ champ = new Champ(); Ville ville = new Ville(false);
			pioche[index].numero = 14;
			for (int i = 0; i<3; i++) {
				pioche[index].getNord()[i] = ville; pioche[index].getEst()[i] = champ; pioche[index].getOuest()[i] = champ; pioche[index].getSud()[i] = champ; 
			}
			index++;
		}
		for (int j = 0; j<8; j++) {
			Champ champ = new Champ();Route route = new Route();
			Champ champ2 = new Champ();Route route2 = new Route();

			pioche[index].numero = 23; pioche[index+8].numero = 24; 
			for (int i = 0; i<3; i++) {
			     pioche[index].getEst()[i] = champ; pioche[index].getOuest()[i] = champ;
			     pioche[index+8].getNord()[i] = champ2; pioche[index+8].getEst()[i] = champ2;

				if (i == 1) {
					pioche[index].getNord()[i] = route;
					pioche[index].getSud()[i] = route;
					pioche[index+8].getOuest()[i] = route2;
					pioche[index+8].getSud()[i] = route2;
				} else {
					pioche[index].getSud()[i] = champ;
					pioche[index].getNord()[i] = champ;
					pioche[index+8].getOuest()[i] = champ2;
					pioche[index+8].getSud()[i] = champ2;
				}
			}
			index++;
		}
		return pioche;
 	}
	
	public  boolean posable(Tuile tuile, OrientationTuile orientationTuile) {
		int k = 0;
		ArrayList<ElementCarcassonne[]> t = getDirection();
		ArrayList<ElementCarcassonne[]> tuile1 = tuile.getDirection();
		switch (orientationTuile) {
			case NORD : k = 1; break;
			case SUD : k = 3; break;
			case OUEST : k = 2; break;
			default : k = 0; break;
		}
		
		for (int i = 0; i<3; i++) {
			//System.out.println(t.get(k)[i].toString()+" "+tuile1.get(orientationTuile.ordinal())[i].toString());
			if (!t.get(k)[i].toString().equals(tuile1.get(orientationTuile.ordinal())[i].toString())) return false;
		}
		
		return true;
	}

	

}
