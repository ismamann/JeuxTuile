package src.modele;
public class Champ extends ElementCarcassonne{
	
	private boolean champQuiEntoureAbbaye;
	Champ() {
		super('C');
		champQuiEntoureAbbaye = false;
	}
	
	Champ(boolean b) {
		super('C');
		champQuiEntoureAbbaye = b;
	}

}
