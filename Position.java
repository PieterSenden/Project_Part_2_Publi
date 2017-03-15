package asteroids.model;

/**
 * A class representing the position of an entity.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 * 
 * @invar  The xComponent of each position must be a valid xComponent for any position.
 *       | isValidComponent(getxComponent())
 * @invar  The yComponent of each position must be a valid yComponent for any position.
 *       | isValidComponent(getyComponent())
 */
public class Position extends PhysicalVector {
	
	/**
	 * Initialize this new position with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new position.
	 * @param  yComponent
	 *         The yComponent for this new position.
	 * @effect The xComponent of this new position is set to the given xComponent.
	 *       | this.setxComponent(xComponent)
	 * @effect The yComponent of this new position is set to the given yComponent.
	 *       | this.setyComponent(yComponent)
	 * @throws IllegalComponentException
	 * 		   One of the given components is not valid.
	 * 		 | ! isValidComponent(xComponent) || ! isValidComponent(yComponent)
	 */
	public Position(double xComponent, double yComponent) throws IllegalComponentException {
		super(xComponent, yComponent);
	}
	
	
	/** 
	 * Generate a copy of this Position object
	 * @return a copy of this Position object.
	 */
	@Override
	public Position clone() {
		return new Position(getxComponent(), getyComponent());
	}
	
	/**
	 * Calculates the distance between two positions
	 * @param pos1
	 * 			The first position
	 * @param pos2
	 * 			The second position
	 * @return If both positions are effective, the Euclidean distance between them is returned.
	 * 			| result == Math.sqrt( (pos1.getxComponent() - pos2.getxComponent())^2 +
	 *			| 	(pos1.getyComponent() - pos2.getyComponent())^2 )
	 * @throws NullPointerException
	 * 			One of the positions is not effective
	 * 			| (pos1 == null) || (pos2 == null)
	 */
	public static double getDistanceBetween(Position pos1, Position pos2) throws NullPointerException {
		return Math.hypot(pos1.getxComponent() - pos2.getxComponent(),
				pos1.getyComponent() - pos2.getyComponent());
	}
}