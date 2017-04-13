package asteroids.model.exceptions;

/**
 * A class for signaling an exception due to the termination of an object.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */
public class TerminatedException extends RuntimeException {
	
	/**
	 * Initialize this new IllegalRadiusException.
	 */
	public TerminatedException() {
		super();
	}
	
	
	private static final long serialVersionUID = 1L;
}
