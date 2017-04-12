package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class representing the position of an entity.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 * 
 */
@Value
public class Position extends PhysicalVector {
	
	/**
	 * Initialize this new position with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new position.
	 * @param  yComponent
	 *         The yComponent for this new position.
	 * @effect This new position is initialized as a physical vector with the given xComponent and yComponent.
	 * 		 | @see implementation
	 */
	public Position(double xComponent, double yComponent) throws IllegalComponentException {
		super(xComponent, yComponent);
	}
	
	/**
	 * Calculates the distance between two positions.
	 * 
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
	
	/**
	 * Move this position during a given time duration and with a given velocity.
	 * 
	 * @param velocity
	 * 			The velocity with which the object is moved.
	 * @param duration
	 * 			The length of the time interval during which the entity is moved.
	 * @return A new position object which is the result of moving this position object
	 * 			with the given velocity during the given duration  
	 * 			| @see implementation 
	 * @throws IllegalArgumentException
	 * 			The given duration is strictly less than 0.
	 * 			| velocity == null || duration < 0
	 */
	public Position move(Velocity velocity, double duration) throws IllegalComponentException, IllegalArgumentException {
		if (velocity == null || duration < 0)
			throw new IllegalArgumentException();
		return new Position(getxComponent() + velocity.getxComponent() * duration,
				getyComponent() + velocity.getyComponent() * duration);
	}
	
	/**
	 * Check whether this position is equal to the given object.
	 * 
	 * @return True iff other is an instance of the class Position, 
	 * 			and both xComponents are equal and both yComponents are equal.
	 * 			| @see implementation
	 */
	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Position))
			return false;
		Position otherAsPosition = (Position)other;
		return getxComponent() == otherAsPosition.getxComponent() && getyComponent() == otherAsPosition.getyComponent();
	}
	
	/**
	 * Return the hash code for this position.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(getxComponent());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getyComponent());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
}