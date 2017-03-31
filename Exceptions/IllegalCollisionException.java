package asteroids.model.exceptions;

/**
 * A class for signaling exceptions regarding collisions.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */
public class IllegalCollisionException extends RuntimeException {
	/**
	 * Initialize this new IllegalCollisionException.
	 */
	public IllegalCollisionException() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
}
