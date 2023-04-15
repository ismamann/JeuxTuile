package src.modele;
import javax.swing.JFrame;

public class ModeleParticipant {
	
	private Plateau plateau;
	private Joueur[] participants;
	private int index;
	
	public ModeleParticipant(Plateau plateau, Joueur[] participants) {
		this.plateau = plateau;
		this.participants = participants;
		index = 0;
	}
	
	public Joueur[] getParticipants() {
		return participants;
	}
	
	public void setIndex(int i) {
		index = i;
	}
	
	public void abandon() { //retire le joueur qui vient d'abandonner
		Joueur[] nouveauParticipants = new Joueur[participants.length -1];
		int k = 0;
		for (int i = 0; i<participants.length; i++) {
			if (i != index) {
				nouveauParticipants[k] = participants[i];
				k++;
			}
		}
		if (index + 1 == participants.length) index = 0;
		participants = nouveauParticipants;
	}
	
	public boolean aucunJoueurHumain() { //Lorsqu'un joueur abandonne on vérifie qu'il reste au moins encore un joueur humains.Sinon la partie s'arrête.
		for (int i = 0; i<participants.length; i++) {
			if (!participants[i].getIABasique() && !participants[i].getHAL9000()) return false;
		}
		return true;
	}
	
	public String gagnant() { //désigne le gagnant
		Joueur a = participants[0];
		for (int i = 1; i<participants.length; i++) {
			if (a.getScore() < participants[i].getScore()) a = participants[i];
		}
		return "Le gagnant est "+a.getPseudo()+" avec "+a.getScore()+" points.";
	}
	
	public Joueur prochainJoueur() {
		if (index + 1 == participants.length) index = 0;
		else index++;
		Joueur j = participants[index];
		return j;
	}
	
	public Joueur getProchainJoueur() {
		if (index + 1 == participants.length) return participants[0];
		return participants[index+1];
	}
	
	public Joueur joueurCourant() {
		return participants[index];
	}
	
	public Plateau getPlateau() {
		return plateau;
	}
	
	
	
	

}
