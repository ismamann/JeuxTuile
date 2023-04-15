package src.vue;

import src.modele.*;
public class Lanceur {

	public static void main(String[] args) {
	
		
		while(Partie.veutJouer()) {
			int nbJ = Partie.demanderNbJoueur();
			Joueur[] participants = new Joueur[nbJ];
			for (int i = 0; i<nbJ; i++) {
				int numero = i+1;
				participants[i] = new Joueur();
				System.out.print("Pour le joueur "+numero+", ");
				String pseudo = Partie.saisirPseudo(i == 0);
				while (i == 0 && (pseudo.equals("1") || pseudo.equals("2"))) {
					System.out.print("Pour le joueur "+numero+", ");
					pseudo = Partie.saisirPseudo(i == 0);
				}
				if (pseudo.equals("1")) {
					participants[i].setPseudo("IA Basique"+i);
					participants[i].setIABasique(true);
				} else if (pseudo.equals("2")) {
					participants[i].setPseudo("HAL9000"+i);
					participants[i].setHAL9000(true);
				} else participants[i].setPseudo(pseudo);
			}
			Plateau p = new Plateau();
			Partie partie = new Partie(new ModeleParticipant(p, participants));
			p.attributionPiocheDomino();
			p.getPlateau()[14][14] = p.piocher();
			partie.jouer();
		}
		
		Partie.finir();
	}
	
}
