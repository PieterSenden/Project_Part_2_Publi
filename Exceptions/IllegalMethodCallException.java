package asteroids.model.exceptions;

/**
 * A class for signaling an illegal method call.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */
public class IllegalMethodCallException extends RuntimeException {
	/**
	 * Initialize this new IllegalMethodCallException.
	 */
	public IllegalMethodCallException() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
}
