package asteroids.model;

/**
 * A class for signaling the overlapping state of 2 ships.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 1.0
 */
public class OverlapException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public OverlapException() {
		super();
	}

}
