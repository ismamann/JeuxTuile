package src.modele;
public abstract class ElementCarcassonne {

	private char typeElement;
	
	ElementCarcassonne(char typeElement) {
		this.typeElement = typeElement;
	}
	
	public String toString() {
		return this.typeElement+"";
	}
}
