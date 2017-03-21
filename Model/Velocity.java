package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import be.kuleuven.cs.som.annotate.Raw;

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

public class Velocity extends PhysicalVector {

	/**
	 * Initialize this new velocity with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new velocity.
	 * @param  yComponent
	 *         The yComponent for this new velocity.
	 * @effect The xComponent of this new velocity is set to the given xComponent.
	 *       | this.setxComponent(xComponent)
	 * @effect The yComponent of this new velocity is set to the given yComponent.
	 *       | this.setyComponent(yComponent)
	 */
	public Velocity(double xComponent, double yComponent) {
		super(xComponent, yComponent);
	}
	
	
	/**
	 * Set the xComponent of this velocity to the given xComponent.
	 * 
	 * @param  xComponent
	 *         The new xComponent for this velocity.
	 * @post   If the given xComponent is valid, the xComponent of this new velocity is equal to the given xComponent.
	 *       | if (isValidComponent(xComponent))
	 *       |		then new.getxComponent() == xComponent
	 */
	@Raw
	public void setxComponent(double xComponent) {
		try {
			super.setxComponent(xComponent);
		}
		catch (IllegalComponentException exc) {
			;
		}
	}
	
	/**
	 * Set the yComponent of this velocity to the given yComponent.
	 * 
	 * @param  yComponent
	 *         The new yComponent for this velocity.
	 * @post   If the given yComponent is valid, the yComponent of this new velocity is equal to the given yComponent.
	 *       | if (isValidComponent(yComponent))
	 *       |		then new.getyComponent() == yComponent
	 */
	@Raw
	public void setyComponent(double yComponent) {
		try {
			super.setyComponent(yComponent);
		}
		catch (IllegalComponentException exc) {
			;
		}
	}
	
	/**
	 * Set the xComponent and yComponent of this velocity to the given xComponent and yComponent
	 * @param xComponent
	 * 			The new xComponent for this velocity.
	 * @param yComponent
	 * 			The new yComponent for this velocity.
	 * @effect The new xComponent of this velocity is set to the given xComponent.
	 * 			| setxComponent(xComponent)
	 * @effect The new yComponent of this velocity is set to the given yComponent.
	 * 			| setyComponent(yComponent)
	 */
	public void setVelocity(double xComponent, double yComponent) {
		setxComponent(xComponent);
		setyComponent(yComponent);
	}
	
	/** 
	 * Generate a copy of this Velocity object
	 * @return a copy of this Velocity object.
	 */
	public Velocity copy() {
		return new Velocity(getxComponent(), getyComponent());
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
