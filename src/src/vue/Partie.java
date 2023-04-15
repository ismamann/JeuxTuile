package src.vue;
import src.modele.*;
import java.util.Scanner;

public class Partie {
	private ModeleParticipant modeleParticipant;
	private static Scanner scanReponse = new Scanner(System.in);

	Partie(ModeleParticipant modeleParticipant) {
		this.modeleParticipant =  modeleParticipant;
	}
	
	public void afficheCourantTerminal() {
		Tuile[][] plateau = modeleParticipant.getPlateau().getPlateau();
		for (int i = 1; i<= plateau[0].length -2; i++) {
			System.out.print("       "+i+"   ");
		}
		System.out.println();
		for (int i = 1; i<= plateau[0].length -2; i++) {
			System.out.print("------------");
		}
		
		System.out.println();
		int A = 65;
		int index = 0;
		int premiereTuileNonNull = 0;
		for (int i = 1; i<plateau.length -1; i++) {
			index = 0;
			System.out.print((char)A+" | ");
			TuileDomino[] tuilePrint = new TuileDomino[plateau.length];
			for (int j = 1; j<plateau.length -1; j++) {
				if (plateau[i][j] != null) {
					TuileDomino t = (TuileDomino) plateau[i][j];
					tuilePrint[index] = t;
					index++;
					if (premiereTuileNonNull == 0) premiereTuileNonNull = j;
				}
			}
			if (premiereTuileNonNull != 0) {
				
			
					for (int k = 0; k<5; k++) {
						System.out.println();
						for (int z = 0; z<premiereTuileNonNull; z++) System.out.print("           ");
							if (k == 0) {
								for (int p = 0; tuilePrint[p] != null; p++) System.out.print(tuilePrint[p].premiereLigne()+"  ");
							}
							else if (k == 1) {
								for (int p = 0; tuilePrint[p] != null; p++) System.out.print(tuilePrint[p].deuxiemeLigne()+"  ");
							}
							else if (k == 2) {
								for (int p = 0; tuilePrint[p] != null; p++) System.out.print(tuilePrint[p].troisiemeLigne()+"  ");
							}
							else if (k == 3) {
								for (int p = 0; tuilePrint[p] != null; p++) System.out.print(tuilePrint[p].quatriemeLigne()+"  ");
							}
							else {
								for (int p = 0; tuilePrint[p] != null; p++) System.out.print(tuilePrint[p].derniereLigne()+"  ");
							}
						}
					}
			A++;
			premiereTuileNonNull = 0;
			index = 0;
			System.out.println();
		}
	}
	
	public static String saisirPseudo(boolean premierJoueur) {
		if (!premierJoueur) System.out.println("Veuillez saisir un pseudo. Si vous voulez que ce joueur soit une IA Basique veuillez saisir '1', pour HAL9000 veuillez saisir '2'");
		else System.out.println("Veuillez saisir un pseudo.");
		return scanReponse.nextLine();
	}
	
	public static boolean veutJouer() {
		System.out.println("Voulez-vous jouez (oui/non) ?");
		String a = scanReponse.next();
		if (a.equals("oui")) return true;
		return false;
	}
	
	public static int demanderNbJoueur() {
		System.out.println("Veuillez saisir un nombre de joueur (1 à 4 max)");
		String s = scanReponse.nextLine();
		while (s.equals(""))  s = scanReponse.nextLine();
		while (!s.equals("1") && !s.equals("2") && !s.equals("3") && !s.equals("4")) {
			System.out.println("Veuillez saisir un nombre de joueur (1 à 4 max)");
		    s = scanReponse.nextLine();
		}
		return Integer.valueOf(s);
	}
	
	public int[] demanderCoordonnes() {
		System.out.println("Veuillez saisir les coordonnés de la case (par exemple B3)");
		int[] t = new int[2];
		String s = scanReponse.next();
		String res = "0";
		t[0] = s.charAt(0) -64;
		if (s.length() >= 2) res = String.valueOf(Character.getNumericValue(s.charAt(1)));
		if (s.length() >= 3) res += String.valueOf(Character.getNumericValue(s.charAt(2)));
		t[1] = Integer.valueOf(res);
		return t;
	}
	
	public static void finir() {
		scanReponse.close();
	}
	
	public char demanderAction() {
		System.out.println("Voulez-vous tourner la tuile (T), poser votre Tuile (P), la defausser (D) ou bien abandonner (A) ?");
		return scanReponse.next().charAt(0);
	}
	

	
	
	public void jouer() {
		Plateau plateau = modeleParticipant.getPlateau();
		Joueur[] participants = modeleParticipant.getParticipants();
		
		int limite = participants.length;
		
		while(!plateau.jeuFini() && !modeleParticipant.aucunJoueurHumain()) {
			for (int i = 0; i<limite; i++) {
				participants[i].setTuileCourante(plateau.piocher());
				modeleParticipant.setIndex(i);
				
				while (participants[i].getTuileCourante() != null) {
					System.out.println("Au tour de : "+participants[i].getPseudo()+". Score : "+participants[i].getScore());
					System.out.println(participants[i].getTuileCourante().toString()+"       ");
					afficheCourantTerminal();
					if (participants[i].getIABasique()) {
						if (plateau.iaBasique(participants[i])) {
							for (int k = 0; k<plateau.getPoseIA()[2]; k++) plateau.getDerniereTuilePioche().tourner();
							plateau.poserTuile(plateau.getDerniereTuilePioche(), plateau.getPoseIA()[0], plateau.getPoseIA()[1]);
						}
						participants[i].setTuileCourante(null);
					} else if (participants[i].getHAL9000()) {
						if (plateau.hal9000(participants[i])) {
							for (int k = 0; k<plateau.getPoseIA()[2]; k++) plateau.getDerniereTuilePioche().tourner();
							plateau.poserTuile(plateau.getDerniereTuilePioche(), plateau.getPoseIA()[0], plateau.getPoseIA()[1]);
						}
						participants[i].setTuileCourante(null);
					} else {
						char action = demanderAction();
					    if (action == 'T') participants[i].getTuileCourante().tourner();
						else if (action == 'P'){
							int[] coordonnes = demanderCoordonnes();
							while(coordonnes[0] >= plateau.getPlateau().length || coordonnes[0] < 0 || coordonnes[1] >= plateau.getPlateau()[0].length || coordonnes[1] < 0) {
								System.out.println("Cette case n'existe pas.");
								coordonnes = demanderCoordonnes();
							}
							if (plateau.posable((TuileDomino)participants[i].getTuileCourante(), participants[i], coordonnes[0], coordonnes[1])) {
								plateau.getPlateau()[coordonnes[0]][coordonnes[1]] = participants[i].getTuileCourante();
								participants[i].setTuileCourante(null);
							} else {
								System.out.println("Vous ne pouvez pas poser cette tuile à cet endroit.");
							}
						} else if (action == 'D') participants[i].setTuileCourante(null);
						else if (action == 'A') {
							modeleParticipant.abandon();
							limite--;
							break;
						}
					}
					
				}
			}
			
		}
		if (participants.length != 0) System.out.println(modeleParticipant.gagnant());
	}

}
