package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class representing the velocity of an entity.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 * 
 * @invar  The xComponent of each velocity must be a valid xComponent for any velocity.
 *       | isValidComponent(getxComponent())
 * @invar  The yComponent of each velocity must be a valid yComponent for any velocity.
 *       | isValidComponent(getyComponent())
 */
@Value
public class Velocity extends PhysicalVector {

	/**
	 * Initialize this new velocity with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new velocity.
	 * @param  yComponent
	 *         The yComponent for this new velocity.
	 * @effect This new velocity is initialized as a new physical vector with the given xComponent and yComponent.
	 * 			| @see implementation
	 */
	public Velocity(double xComponent, double yComponent) throws IllegalComponentException {
		super(xComponent, yComponent);
	}
	
	/**
	 * Calculate the speed associated with this velocity 
	 * @return The speed associated with this velocity
	 * 			|  result == Maht.sqrt(getxComponent()^2 + getyComponent()^2)
	 */
	public double getSpeed() {
		return Math.hypot(getxComponent(), getyComponent());
	}
	
	/**
	 * Check whether this velocity is equal to the given object.
	 * @return True iff other is an instance of the class Velocity, 
	 * 			and both xComponents are equal and both yComponents are equal. 
	 * 			| @see implementation
	 */
	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Velocity))
			return false;
		Velocity otherAsVelocity= (Velocity)other;
		return getxComponent() == otherAsVelocity.getxComponent() && getyComponent() == otherAsVelocity.getyComponent();
	}
	
	/**
	 * Return the hash code for this velocity.
	 */
	@Override
	public int hashCode() {
		final int prime = 101;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(getxComponent());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getyComponent());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
}
