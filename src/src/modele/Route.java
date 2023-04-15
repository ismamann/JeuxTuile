package src.modele;
public class Route extends ElementCarcassonne {

	private boolean routeQuiSeTermine;
	Route() {
		super('R');
		routeQuiSeTermine = false;
	}
	
	Route(boolean routeAvecAbbaye) {
		super('R');
		this.routeQuiSeTermine = routeAvecAbbaye;
	}

}
