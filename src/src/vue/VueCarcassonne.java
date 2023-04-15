package src.vue;

import src.modele.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class VueCarcassonne extends JFrame implements MouseInputListener {
	
	private Plateau plateau;
	private boolean jeuFini;
	private JPanel conteneur;
	private JPanel conteneurCentre;
	private JPanel conteneurTuile;
	private JPanel conteneurScore;
	private JPanel etiquetteFin;
	private BufferedImage imageCourante;
	private Carre derniereImagePose;
	private ModeleParticipant modeleParticipant;
	private JButton poserPartisans;
	private JButton nePasPoserPartisans;
	private boolean partisan;
	
	
	VueCarcassonne(Plateau plateau, ModeleParticipant mp) throws IOException  {
		this.plateau = plateau;
		modeleParticipant = mp;
		setSize(1500, 700);
		setName("Carcassonne");
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
		conteneurTuile.setLayout(new GridLayout(8, 1));
		conteneurScore.setLayout(new GridLayout(1, 1+modeleParticipant.getParticipants().length));
		majScore();
		conteneurCentre.setLayout(null);
		
		etiquetteFin = new JPanel();
		etiquetteFin.setBounds(450, 175, 250, 250);
		conteneurCentre.add(etiquetteFin);
		etiquetteFin.setVisible(false);
		
		
		conteneurCentre.addMouseListener(this);
		
		JButton tourner = new JButton("Tourner");
		JButton defausser = new JButton("Défausser");
		JButton abandonner = new JButton("Abandonner");
		poserPartisans = new JButton("Poser un Partisan");
		nePasPoserPartisans = new JButton("Ne Pas Poser de Partisan");
		nePasPoserPartisans.setEnabled(false);
		poserPartisans.setEnabled(false);
		
		nePasPoserPartisans.addActionListener((e) -> {
			modeleParticipant.prochainJoueur();
			poserPartisans.setEnabled(false);
			nePasPoserPartisans.setEnabled(false);
			majScore();
			partisan = false;
			revalidate();
			ia();
		});
		
		poserPartisans.addActionListener((e) -> {
			
			derniereImagePose.addMouseListener(new MouseInputListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					poserPartisan(e.getX(), e.getY());
					modeleParticipant.prochainJoueur();

					poserPartisans.setEnabled(false);
					nePasPoserPartisans.setEnabled(false);
					majScore();
					partisan = false;
					ia();
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
				
			});
			conteneurCentre.addMouseListener(this);
		});
	
		
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
		
		conteneurButton.add(tourner); conteneurButton.add(defausser); conteneurButton.add(abandonner); conteneurButton.add(poserPartisans);
		conteneurButton.add(nePasPoserPartisans);
		conteneur.add("South", conteneurButton);
		conteneurTuile.setPreferredSize(new Dimension(70, 30));
		conteneurScore.setPreferredSize(new Dimension(80, 80));
		conteneur.add("West", conteneurTuile);
		conteneurCentre.add(new Carre(650, 275, 5, 72));
		plateau.getPlateau()[5][72] = plateau.getDerniereTuilePioche();
		Carre tuile = new Carre(0, 0, -1, -1);
		derniereImagePose = tuile;
		conteneurTuile.add(tuile);
		conteneur.add("Center", conteneurCentre);
		conteneur.add("North", conteneurScore);
		
		setVisible(true);
	}
	
	public void poserPartisan(int a, int b) {
		if (!derniereImagePose.tuile.getPartisan() && modeleParticipant.joueurCourant().getPartisan() > 0) {
			Graphics2D graphics = derniereImagePose.image.createGraphics();
			graphics.setColor(modeleParticipant.joueurCourant().getColor());
			graphics.fillRect(a, b, 10, 10);
			derniereImagePose.tuile.setPartisan(true);
			conteneurCentre.updateUI();
			modeleParticipant.joueurCourant().setPartisan(modeleParticipant.joueurCourant().getPartisan()-1);
			
			for (int i = 0; i<derniereImagePose.getMouseListeners().length; i++) {
				derniereImagePose.removeMouseListener(derniereImagePose.getMouseListeners()[i]);
			}
		}
	}
	
	public void tourner() {
		plateau.getDerniereTuilePioche().tourner();
		BufferedImage newImageFromBuffer = new BufferedImage(imageCourante.getWidth(), imageCourante.getHeight(), imageCourante.getType());
        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        graphics2D.rotate(Math.toRadians(90), imageCourante.getWidth() / 2, imageCourante.getHeight() / 2);
        graphics2D.drawImage(imageCourante, null, 0, 0);
		
		imageCourante = newImageFromBuffer;
		JLabel l = new JLabel(new ImageIcon(imageCourante));
		l.setBounds(400, 200, 70, 70);
		conteneurTuile.removeAll();
		Carre c = new Carre(l);
		c.tuile = derniereImagePose.tuile;
		c.image = imageCourante;
		conteneurTuile.add(c);
		revalidate();
	}
	
	public void defausser() {
		if (jeuFini) {
			dispose();
			return;
		}
		if (plateau.jeuFini()) {
			jeuFini = true;
			etiquetteFin.removeAll();
			etiquetteFin.add(new JLabel("Fin de la partie."));
			etiquetteFin.setVisible(true);
		} else {
			conteneurTuile.removeAll();
			try {
				conteneurTuile.add(new Carre(0, 0, -1, -1));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			modeleParticipant.prochainJoueur();
			majScore();
			revalidate();
			if (modeleParticipant.joueurCourant().getIABasique()) ia();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (jeuFini) {
			dispose();
			return;
		}
		if (!partisan) {
		Component[] t = conteneurCentre.getComponents();
		for (int i = 1; i<t.length; i++) { //on cherche la tuile à proximite du clique pour pouvoir faire les vérification necessaire à la pose de la tuile pioché.
			if (e.getY()+70 <= t[i].getY()+70 && e.getY()+70 >= t[i].getY() && e.getX() >= t[i].getX() && e.getX() <= t[i].getX()+70) {
				Carre c = (VueCarcassonne.Carre) t[i];
				if (c.i > 1 && plateau.getPlateau()[c.i-1][c.j] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i-1, c.j)) {
					plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i-1,c.j);
					Carre pioche = (VueCarcassonne.Carre) conteneurTuile.getComponent(0);
					pioche.i = c.i-1; pioche.j = c.j; pioche.setLocation(c.getX(), c.getY() -64);
					conteneurCentre.add(pioche);
					conteneurTuile.removeAll();
					if (plateau.jeuFini()) {
						jeuFini = true;
						etiquetteFin.removeAll();
						etiquetteFin.add(new JLabel("Fin de la partie."));
						etiquetteFin.setVisible(true);
					} else {
						try {
							conteneurTuile.add(new Carre(0, 0, -1, -1));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						derniereImagePose = pioche;
						derniereImagePose.tuile.setPartisan(false);
						if (modeleParticipant.joueurCourant().getPartisan() == 0) {
							modeleParticipant.prochainJoueur();
							ia();
						} else {
							poserPartisans.setEnabled(true);
							nePasPoserPartisans.setEnabled(true);
							partisan = true;
						}
						majScore();
						revalidate();
					}
					break;
				}
			}
			else if (e.getY()-70 <= t[i].getY()+70 && e.getY()-70 >= t[i].getY() && e.getX() >= t[i].getX() && e.getX() <= t[i].getX()+70) {
					Carre c = (VueCarcassonne.Carre) t[i];
				if (c.i < 8 && plateau.getPlateau()[c.i+1][c.j] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i+1, c.j)) {
					plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i+1,c.j);
					Carre pioche = (VueCarcassonne.Carre) conteneurTuile.getComponent(0);
					pioche.i = c.i+1; pioche.j = c.j; pioche.setLocation(c.getX(), c.getY() +64);
					conteneurCentre.add(pioche);
					conteneurTuile.removeAll();
					if (plateau.jeuFini()) {
						jeuFini = true;
						etiquetteFin.removeAll();
						etiquetteFin.add(new JLabel("Fin de la partie."));
						etiquetteFin.setVisible(true);
					} else {
						try {
							conteneurTuile.add(new Carre(0, 0, -1, -1));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						derniereImagePose = pioche;
						derniereImagePose.tuile.setPartisan(false);
						if (modeleParticipant.joueurCourant().getPartisan() == 0) {
							modeleParticipant.prochainJoueur();
							ia();
						} else {
							poserPartisans.setEnabled(true);
							nePasPoserPartisans.setEnabled(true);
							partisan = true;
						}
						majScore();
						revalidate();
					}				
					break;
				}
			}
			
			else if (e.getX()+70 <= t[i].getX()+70 && e.getX()+70 >= t[i].getX() && e.getY() <= t[i].getY()+70 && e.getY() >= t[i].getY()) {
				Carre c = (VueCarcassonne.Carre) t[i];
			if (plateau.getPlateau()[c.i][c.j-1] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i, c.j-1)) {
				plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i,c.j-1);
				Carre pioche = (VueCarcassonne.Carre) conteneurTuile.getComponent(0);
				pioche.i = c.i; pioche.j = c.j-1; pioche.setLocation(c.getX()-64, c.getY());
				conteneurCentre.add(pioche);
				conteneurTuile.removeAll();
				if (plateau.jeuFini()) {
					jeuFini = true;
					etiquetteFin.removeAll();
					etiquetteFin.add(new JLabel("Fin de la partie."));
					etiquetteFin.setVisible(true);
				} else {
					try {
						conteneurTuile.add(new Carre(0, 0, -1, -1));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					derniereImagePose = pioche;
					derniereImagePose.tuile.setPartisan(false);
					if (modeleParticipant.joueurCourant().getPartisan() == 0) {
						modeleParticipant.prochainJoueur();
						ia();
					} else {
						poserPartisans.setEnabled(true);
						nePasPoserPartisans.setEnabled(true);
						partisan = true;
					}
					majScore();
					revalidate();
				}
				break;
			}
		}
			else if (e.getX()-70 <= t[i].getX()+70 && e.getX()-70 >= t[i].getX() && e.getY() <= t[i].getY()+70 && e.getY() >= t[i].getY()) {
				Carre c = (VueCarcassonne.Carre) t[i];
			if (plateau.getPlateau()[c.i][c.j+1] == null && plateau.posable(plateau.getDerniereTuilePioche(), modeleParticipant.joueurCourant(), c.i, c.j+1)) {
				plateau.poserTuile(plateau.getDerniereTuilePioche(), c.i,c.j+1);
				Carre pioche = (VueCarcassonne.Carre) conteneurTuile.getComponent(0);
				pioche.i = c.i; pioche.j = c.j+1; pioche.setLocation(c.getX()+64, c.getY());
				conteneurCentre.add(pioche);
				conteneurTuile.removeAll();
				if (plateau.jeuFini()) {
					jeuFini = true;
					etiquetteFin.removeAll();
					etiquetteFin.add(new JLabel("Fin de la partie."));
					etiquetteFin.setVisible(true);
				} else {
					try {
						conteneurTuile.add(new Carre(0, 0, -1, -1));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					derniereImagePose = pioche;
					derniereImagePose.tuile.setPartisan(false);
					if (modeleParticipant.joueurCourant().getPartisan() == 0) {
						modeleParticipant.prochainJoueur();
						ia();
					} else {
						poserPartisans.setEnabled(true);
						nePasPoserPartisans.setEnabled(true);
						partisan = true;
					}
					majScore();
					revalidate();
				}
				break;
			}
		}
			}
		}
	}
	
	public void majScore()  {
		conteneurScore.removeAll();
		for (int i = 0; i<modeleParticipant.getParticipants().length; i++) {
			Joueur j = modeleParticipant.getParticipants()[i];
			JLabel label = new JLabel(j.getPseudo()+" : Partisans restants : "+j.getPartisan());
			JPanel panel = new JPanel();
			panel.add(label); panel.setBackground(j.getColor());
			conteneurScore.add(panel);
		}
		conteneurScore.add(new JLabel("Au tour de "+modeleParticipant.joueurCourant().getPseudo()));
		
	}
	
	public void ia() {
		boolean defausser = false;
		if (plateau.jeuFini()) return;
		 if (modeleParticipant.joueurCourant().getIABasique()) {
			if (plateau.iaBasique(modeleParticipant.joueurCourant())) {
				int[] coordonnees = plateau.getPoseIA();
				
				for (int i = 0; i<coordonnees[2]; i++)  tourner();
				
				Component[] t = conteneurCentre.getComponents();
				Carre pioche = (VueCarcassonne.Carre) conteneurTuile.getComponent(0);
				plateau.poserTuile(plateau.getDerniereTuilePioche(), coordonnees[0], coordonnees[1]);
				for (int k = 1; k<t.length; k++) {
					Carre c = (VueCarcassonne.Carre) t[k];
					if (c.i == coordonnees[0] -1 && c.j == coordonnees[1]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX(), c.getY() +64);
						conteneurCentre.add(pioche);
						break;
					} else if (c.i == coordonnees[0] +1 && c.j == coordonnees[1]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX(), c.getY() -64);
						conteneurCentre.add(pioche);
						break;
					} else if (c.j == coordonnees[1] +1 && c.i == coordonnees[0]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX() -64, c.getY());
						conteneurCentre.add(pioche);
						break;
					} else if (c.j == coordonnees[1] -1 && c.i == coordonnees[0]) {
						pioche.i = coordonnees[0]; pioche.j = coordonnees[1]; pioche.setLocation(c.getX() +64, c.getY());
						conteneurCentre.add(pioche);
						break;
					}
				}
				conteneurTuile.removeAll();
				try {
					conteneurTuile.add(new Carre(0, 0, -1, -1));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				derniereImagePose = pioche;
				derniereImagePose.tuile.setPartisan(false);
				Random rd = new Random();
				if (rd.nextInt(0,6) <= 2 && modeleParticipant.joueurCourant().getPartisan() > 0 ) poserPartisan(25, 25);

			} else {
				defausser();
				defausser = true;
			}
			 conteneurCentre.revalidate();
		     if (!defausser) {
		    	 modeleParticipant.prochainJoueur();
		    	 majScore();
				 if (modeleParticipant.joueurCourant().getIABasique()) ia();
		     }
		}
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
		private BufferedImage image;
		private TuileCarcassonne tuile;
		
		Carre(int pixelI, int pixelJ, int i, int j) throws IOException {
			this.i = i; this.j = j;
			setBounds(pixelI, pixelJ, 70, 70);
			this.i = i; this.j = j;
			tuile = (TuileCarcassonne) plateau.piocher();
			int numero = tuile.getNumero();
		    image = ImageIO.read(new File("/home/ismael/Téléchargements/Akyar-Driche/images/"+numero+".JPG"));
			imageCourante = image;
			JLabel l = new JLabel(new ImageIcon(image));
			l.setBounds(400, 200, 70, 70);
			this.add(l);
			l.setVisible(true);
			setVisible(true);
		}
		
		Carre(JLabel l) {
			this.add(l);
			setBounds(0, 0, 70, 70);
			setVisible(true);
		}
		

	}
	

}
