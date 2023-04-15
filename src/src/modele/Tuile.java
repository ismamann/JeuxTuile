package src.modele;
import java.util.ArrayList;

public abstract  class Tuile<E> {
	
	private E[] nord;
	private E[] est;
	private E[] sud;
	private E[] ouest;
	private ArrayList<E[]> direction;
	
	
	Tuile(E[] nord, E[] est, E[] sud, E[] ouest) {
		this.nord = nord;
		this.est = est;
		this.sud = sud;
		this.ouest = ouest;
		direction = new ArrayList<>();
		direction.add(ouest);
		direction.add(sud);
		direction.add(est);
		direction.add(nord);
	}
	
	public void tourner() {
		E[] sauvegarde = direction.get(0);
		direction.remove(0);
		direction.add(sauvegarde);
	}
	
	public ArrayList<E[]> getDirection() {
		return direction;
	}
	
	public E[] getEst() {
		return est;
	}

	public E[] getNord() {
		return nord;
	}

	public E[] getSud() {
		return sud;
	}

	public E[] getOuest() {
		return ouest;
	}

	protected abstract boolean posable(Tuile tuile, OrientationTuile sud2); 
	
	
}
