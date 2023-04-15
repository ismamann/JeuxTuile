package src.vue;

import src.modele.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class VueLanceur extends JFrame {

	VueLanceur() {
		setSize(600, 600);
		setTitle("Choix paramètres de jeu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JSlider nbJoueur = new JSlider(1, 4);
		JButton ok = new JButton("Continuer...");
		nbJoueur.setPaintTicks(true); 
        nbJoueur.setPaintLabels(true); 
        nbJoueur.setMajorTickSpacing(1); 
        
        String[] choix = {"Carcassonne", "Dominos", "Dominos dans le Terminal"};
        JComboBox<String> jeu = new JComboBox<>(choix);
        jeu.setBounds(80, 50, 140, 20);
		
		JPanel conteneur = new JPanel();
		JPanel conteneur2 = new JPanel();
		conteneur2.setLayout(new BorderLayout());
		conteneur.setLayout(new GridLayout(4, 1));
		conteneur2.add("South", ok);
		conteneur.add(new JLabel("Veuillez choisir un nombre de joueurs."));
	    conteneur.add(nbJoueur);
	    conteneur.add(jeu);
	    conteneur.add(conteneur2);
	    getContentPane().add(conteneur);
	    
	    ok.addActionListener((e) -> {
        	int nb = nbJoueur.getValue();
        	String choixJeu = jeu.getItemAt(jeu.getSelectedIndex());
        	String[] choixIA;
        	if (choixJeu.equals("Dominos dans le Terminal")) {
        		dispose();
        		Lanceur.main(null);
        	}

        	if (choixJeu.equals("Dominos")) {
        		String[] choixIA1 = {"Aucune", "IA Basique", "HAL9000"};
        		choixIA = choixIA1;
        	} else {
        		String[] choixIA1 = {"Aucune", "IA Basique"};
        		choixIA = choixIA1;
        	}
        	String[] choixAucuneIA = {"Aucune"};
        	
            jeu.setBounds(80, 50, 140, 20);
        	getContentPane().removeAll();
        	conteneur.removeAll();
        	conteneur.setLayout(new GridLayout(nb+1, 2));
        	for (int i = 0; i<nb; i++) {
        		conteneur.add(new JTextField("Joueur"+i));
        		if (i != 0) conteneur.add(new JComboBox<>(choixIA));
        		else conteneur.add(new JComboBox<>(choixAucuneIA));
        	}
        	JButton lancer = new JButton("Lancer !");
        	JPanel lancerP = new JPanel();
        	lancerP.setLayout(new BorderLayout());
        	lancerP.add("South", lancer);
        	conteneur.add(lancerP);
        	
        	lancer.addActionListener((event) -> {
        		Plateau p = new Plateau();
        		if (choixJeu.equals("Dominos")) p.attributionPiocheDomino();
        		else p.attributionPiocheCarcassonne();
        		
        		Joueur[] participants = new Joueur[nb];
        		Color[] colorJoueur = new Color[4];
        		colorJoueur[0] = Color.blue; colorJoueur[1] = Color.yellow; colorJoueur[2] = Color.red; colorJoueur[3] = Color.green;
        		int k = 0;
        		for (int i = 0; i<nb*2; i++) {
        			if (i == 0 || i == 2 || i == 4 || i == 6) {
        				participants[k] = new Joueur();
            			participants[k].setColor(colorJoueur[k]);
            			participants[k].setPseudo((String) ((JTextField) conteneur.getComponent(i)).getText());
            			
        			}
        			
        			else {
        				JComboBox c = (JComboBox) conteneur.getComponent(i);
            			if (c.getItemAt(c.getSelectedIndex()).equals("IA Basique")) {
            				participants[k].setIABasique(true);
           
            			} else if (c.getItemAt(c.getSelectedIndex()).equals("HAL9000")) {
            				participants[k].setHAL9000(true);
            				
            			}
            			k++;
        			}
        		}
        			
        		dispose();
        		ModeleParticipant mp = new ModeleParticipant(p, participants);
        		if (choixJeu.equals("Dominos")) {
        			p.setTaillePlateau(7, 56);
        			new VueDomino(p, mp);
        		}
				else {
					try {
						new VueCarcassonne(p, mp);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
        	});
        	
        	getContentPane().add(conteneur);
        	revalidate();

        });
	    
	    setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new VueLanceur();
	}
}
	
