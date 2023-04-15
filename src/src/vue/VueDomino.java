package src.vue;

import src.modele.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.MouseInputListener;


public class VueDomino extends JFrame implements MouseInputListener {
	
	private Plateau plateau;
	private boolean jeuFini;
	private JPanel conteneur;
	private JPanel conteneurCentre;
	private JPanel conteneurTuile;
	private JPanel conteneurScore;
	private JPanel etiquetteGagnant;
	private ModeleParticipant modeleParticipant;
	
	VueDomino(Plateau plateau, ModeleParticipant mp) {
		this.plateau = plateau;
		modeleParticipant = mp;
		setSize(800, 800);
		setTitle("Jeu Dominos");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jeuFini = false;
		conteneur = new JPanel(); 
		JPanel conteneurButton = new JPanel(); 
	    conteneurTuile = new JPanel(); 
		conteneurCentre = new JPanel();

		conteneurScore = new JPanel();
	
		
		getContentPane().add(conteneur);
		conteneur.setLayout(new BorderLayout());
		conteneurButton.setLayout(new GridLayout(1, 4));
		conteneurTuile.setLayout(new GridLayout(6, 1));
		conteneurScore.setLayout(new GridLayout(1, 1+modeleParticipant.getParticipants().length));
		majScore();
		conteneurCentre.setLayout(null);
		
		etiquetteGagnant = new JPanel();
		etiquetteGagnant.setBounds(450, 175, 250, 250);
		conteneurCentre.add(etiquetteGagnant);
		etiquetteGagnant.setVisible(false);
		
		
		conteneurCentre.addMouseListener(this);

		
		JButton tourner = new JButton("Tourner");
		JButton defausser = new JButton("Défausser");
		JButton abandonner = new JButton("Abandonner");
		
		tourner.addActionListener((e) -> {
			tourner();
		});
		
		abandonner.addActionListener((e) -> {
			if (modeleParticipant.getParticipants().length == 1) dispose();
			else {
				modeleParticipant.abandon();
				if (modeleParticipant.aucunJoueurHumain()) {
					dispose();
				} else {
					conteneurScore.setLayout(new GridLayout(1, 1+modeleParticipant.getParticipants().length));
					majScore();
					revalidate();
					ia();
				}
			}
		});
		
		defausser.addActionListener((e) -> {
			defausser();
		});
		
		
		
		conteneurButton.add(tourner); conteneurButton.add(defausser); conteneurButton.add(abandonner);
		conteneur.add("South", conteneurButton);
		conteneurTuile.setPreferredSize(new Dimension(50, 90));
		conteneurScore.setPreferredSize(new Dimension(80, 80));
		conteneur.add("East", conteneurTuile);
		conteneurCentre.add(new Carre(650, 275, 3, 28));
		plateau.getPlateau()[3][28] = plateau.getDerniereTuilePioche();
		conteneurTuile.add(new Carre(0, 0, -1, -1));
		conteneur.add("Center", conteneurCentre);
		conteneur.add("North", conteneurScore);
		
		setVisible(true);
	}
	
	public void tourner() {
		plateau.getDerniereTuilePioche().tourner();
		Carre c = (VueDomino.Carre) conteneurTuile.getComponent(0);
		conteneurTuile.removeAll();
		c.tourner();
		conteneurTuile.add(c);
		revalidate();

	}
	
	public void defausser() {
		if (jeuFini) dispose();
		if (plateau.jeuFini()) {
			jeuFini = true;
			etiquetteGagnant.removeAll();
			etiquetteGagnant.add(new JLabel(modeleParticipant.gagnant()));
			etiquetteGagnant.setVisible(true);
		} else {
			conteneurTuile.removeAll();
			conteneurTuile.add(new Carre(0, 0, -1, -1));
			modeleParticipant.prochainJoueur();
			majScore();
			revalidate();
			ia();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (jeuFini) dispose();
		if (plateau.jeuFini()) {
			jeuFini = true;
			etiquetteGagnant.removeAll();
			etiquetteGagnant.add(new JLabel(modeleParticipant.gagnant()));
			etiquetteGagnant.setVisible(true);
		}
		Component[] t = conteneurCentre.getComponents();
		for (int i = 1; i<t.length; i++) { //on cherche la tuile à proximite du clique pour pouvoir faire les vérification necessaire à la pose.
			if (e.getY()+90 <= t[i].getY()+90 && e.getY()+90 >= t[i].getY() && e.getX() >= t[i].getX() && e.getX() <= t[i].getX()+50) {
				Carre c = (VueDomino.Carre) t[i];
				if (c.i > 1 && plateau.getPlateau()[c.i-1][c.j] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i-1, c.j)) {
					plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i-1,c.j);
					Carre pioche = (VueDomino.Carre) conteneurTuile.getComponent(0);
					pioche.i = c.i-1; pioche.j = c.j; pioche.setLocation(c.getX(), c.getY() -90);
					plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i-1, c.j);
					conteneurCentre.add(pioche);
					conteneurTuile.removeAll();
					conteneurTuile.add(new Carre(0, 0, -1, -1));
					modeleParticipant.prochainJoueur();
					majScore();
					revalidate();
					ia();
					break;
				}
			}
			else if (e.getY()-90 <= t[i].getY()+90 && e.getY()-90 >= t[i].getY() && e.getX() >= t[i].getX() && e.getX() <= t[i].getX()+50) {
					Carre c = (VueDomino.Carre) t[i];
				if (c.i < 5  && plateau.getPlateau()[c.i+1][c.j] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i+1, c.j)) {
					plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i+1,c.j);
					Carre pioche = (VueDomino.Carre) conteneurTuile.getComponent(0);
					pioche.i = c.i+1; pioche.j = c.j; pioche.setLocation(c.getX(), c.getY() +90);
					plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i+1, c.j);
					conteneurCentre.add(pioche);
					conteneurTuile.removeAll();
					conteneurTuile.add(new Carre(0, 0, -1, -1));
					modeleParticipant.prochainJoueur();
					majScore();
					revalidate();
					ia();
					break;
				}
			}
			
			else if (e.getX()+50 <= t[i].getX()+50 && e.getX()+50 >= t[i].getX() && e.getY() <= t[i].getY()+90 && e.getY() >= t[i].getY()) {
				Carre c = (VueDomino.Carre) t[i];
			if (plateau.getPlateau()[c.i][c.j-1] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i, c.j-1)) {
				plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i,c.j-1);
				Carre pioche = (VueDomino.Carre) conteneurTuile.getComponent(0);
				pioche.i = c.i; pioche.j = c.j-1; pioche.setLocation(c.getX()-50, c.getY());
				plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i, c.j-1);
				conteneurCentre.add(pioche);
				conteneurTuile.removeAll();
				conteneurTuile.add(new Carre(0, 0, -1, -1));
				modeleParticipant.prochainJoueur();
				majScore();
				revalidate();
				ia();
				break;
			}
		}
			else if (e.getX()-50 <= t[i].getX()+50 && e.getX()-50 >= t[i].getX() && e.getY() <= t[i].getY()+90 && e.getY() >= t[i].getY()) {
				Carre c = (VueDomino.Carre) t[i];
			if (plateau.getPlateau()[c.i][c.j+1] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i, c.j+1)) {
				plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i,c.j+1);
				Carre pioche = (VueDomino.Carre) conteneurTuile.getComponent(0);
				pioche.i = c.i; pioche.j = c.j+1; pioche.setLocation(c.getX()+50, c.getY());
				plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i, c.j+1);
				conteneurCentre.add(pioche);
				conteneurTuile.removeAll();
				conteneurTuile.add(new Carre(0, 0, -1, -1));
				modeleParticipant.prochainJoueur();
				majScore();
				revalidate();
				ia();
				break;
			}
		}
		}
	}
	
	public void ia() {
		boolean poseReussie = false;
		if (plateau.jeuFini()) return;
		 if (modeleParticipant.joueurCourant().getIABasique())  poseReussie = plateau.iaBasique(modeleParticipant.joueurCourant());
		 else if (modeleParticipant.joueurCourant().getHAL9000()) poseReussie = plateau.hal9000(modeleParticipant.joueurCourant());
		 
		 if (poseReussie) {
			 int[] coordonnees = plateau.getPoseIA();
			 
			 for (int i = 0; i<coordonnees[2]; i++)  tourner();
			 
				Component[] t = conteneurCentre.getComponents();
				Carre pioche = (VueDomino.Carre) conteneurTuile.getComponent(0);
				plateau.poserTuile(plateau.getDerniereTuilePioche(), coordonnees[0], coordonnees[1]);
				for (int k = 1; k<t.length; k++) {
					Carre c = (VueDomino.Carre) t[k];
					if (c.i == coordonnees[0] -1 && c.j == coordonnees[1]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX(), c.getY() +90);
						conteneurCentre.add(pioche);
						break;
					} else if (c.i == coordonnees[0] +1 && c.j == coordonnees[1]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX(), c.getY() -90);
						conteneurCentre.add(pioche);
						break;
					} else if (c.j == coordonnees[1] +1 && c.i == coordonnees[0]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX() -50, c.getY());
						conteneurCentre.add(pioche);
						break;
					} else if (c.j == coordonnees[1] -1 && c.i == coordonnees[0]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX() +50, c.getY());
						conteneurCentre.add(pioche);
						break;
					}
				}
				conteneurTuile.removeAll();
				conteneurTuile.add(new Carre(0, 0, -1, -1));
				conteneurCentre.revalidate();
				modeleParticipant.prochainJoueur();
				majScore();
				if (modeleParticipant.joueurCourant().getIABasique() || modeleParticipant.joueurCourant().getHAL9000()) ia();
			} else if ((modeleParticipant.joueurCourant().getIABasique() || modeleParticipant.joueurCourant().getHAL9000()) && !poseReussie){
				defausser();
			}
	}

	
	
	public void majScore()  {
		conteneurScore.removeAll();
		for (int i = 0; i<modeleParticipant.getParticipants().length; i++) {
			conteneurScore.add(new JLabel("Score de "+modeleParticipant.getParticipants()[i].getPseudo()+" : "+modeleParticipant.getParticipants()[i].getScore()));
		}
		conteneurScore.add(new JLabel("Au tour de "+modeleParticipant.joueurCourant().getPseudo()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public class Carre extends JPanel {
		
		private int i; //i et j sont les coordonnées dans le plateau
		private int j;
		private JLabel info;
		
		Carre(int pixelI, int pixelJ, int i, int j) { 
			this.i = i; this.j = j;
			setBounds(pixelI, pixelJ, 50, 90);
			this.setBackground(Color.gray);
			TuileDomino t = (TuileDomino) VueDomino.this.plateau.piocher();
		    info = new JLabel("<html>"+t.premiereLigne()+"<br>"+t.deuxiemeLigne()+"<br>"+t.troisiemeLigne()+"<br>"+t.quatriemeLigne()+"<br>"+t.derniereLigne()+"</html>");
			this.add(info);
			setVisible(true);
		}
		
		public void tourner() {
			this.removeAll();
			TuileDomino t = (TuileDomino) VueDomino.this.plateau.getDerniereTuilePioche();
		    info = new JLabel("<html>"+t.premiereLigne()+"<br>"+t.deuxiemeLigne()+"<br>"+t.troisiemeLigne()+"<br>"+t.quatriemeLigne()+"<br>"+t.derniereLigne()+"</html>");
			this.add(info);
		}
		
	}

	

}
