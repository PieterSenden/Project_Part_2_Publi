package asteroids.model;


/**
 * A class for signaling exceptions positions of entities.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */

public class IllegalPositionException extends RuntimeException {
	
	/**
	 * Initialize this new IllegalPositionException.
	 */
	public IllegalPositionException() {
		super();
	}

	private static final long serialVersionUID = 1L;

}
