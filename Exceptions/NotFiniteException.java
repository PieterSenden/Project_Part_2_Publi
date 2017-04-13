package asteroids.model.exceptions;

/**
 * A class for signaling exceptions regarding not finite values.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */
public class NotFiniteException extends RuntimeException {
	/**
	 * Initialize this new NotFiniteException.
	 */
	public NotFiniteException() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
}
