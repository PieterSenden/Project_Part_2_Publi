package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing an entity floating in outer space.
 * 
 * @invar  Each entity can have its position as position.
 *       	| canHaveAsPosition(getPosition())
 * @invar  Each entity can have its velocity as velocity.
 *       	| canHaveAsVelocity(getVelocity())
 *       
 * 
 * @author Joris Ceulemans & Pieter Senden
 *
 */

public abstract class Entity {
	
	/**
	 * Return the position of this entity.
	 */
	@Basic @Raw
	public Position getPosition() {
		if (this.position == null)
			return null;
		return this.position.clone();
	}
	
	/**
	 * Check whether the given position is a valid position for any entity.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return true iff the given position is effective.
	 *       | result == (position != null)
	 */
	
	public boolean canHaveAsPosition(Position position) {
		return position != null;
	}
	
	/**
	 * Move this entity during a given time duration.
	 * 
	 * @param duration
	 * 			The length of the time interval during which the entity is moved.
	 * @effect Each new component of the position of this entity is set to the sum of the old component
	 * 			and the given duration times the corresponding component of the velocity of this entity.
	 * 			| setPosition(getPosition().getxComponent() + duration * getVelocity().getxComponent(),
	 * 			|				getPosition().getyComponent() + duration * getVelocity().getyComponent())
	 * @throws IllegalArgumentException
	 * 			The given duration is strictly less than 0.
	 * 			| duration < 0
	 */
	public void move(double duration) throws IllegalArgumentException, IllegalComponentException {
		if (duration < 0)
			throw new IllegalArgumentException();
		setPosition(getPosition().getxComponent() + duration * getVelocity().getxComponent(),
				getPosition().getyComponent() + duration * getVelocity().getyComponent());
	}
	
	
	/**
	 * Set the position of this entity to the given position.
	 * 
	 * @param  xComponent
	 *         The new xComponent for the position for this entity.
	 * @param  yComponent
	 *         The new yComponent for the position for this entity.
	 * @post   The xComponent of the position of this new entity is equal to the given xComponent.
	 *       | new.getPosition().getxComponent() == xComponent
	 * @post   The yComponent of the position of this new entity is equal to the given yComponent.
	 *       | new.getPosition().getyComponent() == yComponent
	 * @throws IllegalComponentException
	 * 		   One of the given components is not valid
	 * 		 | ! Position.isValidComponent(xComponent) || ! Position.isValidComponent(yComponent)
	 */
	@Raw @Model
	private void setPosition(double xComponent, double yComponent) throws IllegalComponentException {
		try {
			this.position.setComponents(xComponent, yComponent);
		}
		catch(NullPointerException exc) {
			this.position = new Position(xComponent, yComponent);
		}
	}
	
	/**
	 * Variable registering the position of this entity.
	 */
	private Position position;
	
}
