package src.modele;
public class Ville extends ElementCarcassonne {
	
	private boolean bouclier;

	Ville(boolean bouclier) {
		super('V');
		this.bouclier = bouclier;
	}

}
