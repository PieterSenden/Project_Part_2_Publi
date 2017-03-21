package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing a physical vector.
 * 
 * @invar  The xComponent of each physical vector must be a valid xComponent for any physical vector.
 *       | isValidComponent(getxComponent())
 * @invar  The yComponent of each physical vector must be a valid yComponent for any physical vector.
 *       | isValidComponent(getyComponent())
 *       
 *       
 * @version 2.0
 * @author Joris Ceulemans & Pieter Senden
 */

public abstract class PhysicalVector {


	/**
	 * Initialize this new physical vector with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new physical vector.
	 * @param  yComponent
	 *         The yComponent for this new physical vector.
	 * @effect The xComponent of this new physical vector is set to the given xComponent.
	 *       | this.setxComponent(xComponent)
	 * @effect The yComponent of this new physical vector is set to the given yComponent.
	 *       | this.setyComponent(yComponent)
	 * @throws IllegalComponentException
	 * 		   One of the given components is not valid.
	 * 		 | ! isValidComponent(xComponent) || ! isValidComponent(yComponent)
	 */
	@Raw
	protected PhysicalVector(double xComponent, double yComponent) throws IllegalComponentException {
		this.setxComponent(xComponent);
		this.setyComponent(yComponent);
	}
	
	
	/**
	 * Return the xComponent of this physical vector.
	 */
	@Basic @Raw
	public double getxComponent() {
		return this.xComponent;
	}
	
	/**
	 * Check whether the given component is a valid component for any physical vector.
	 *  
	 * @param  component
	 *         The component to check.
	 * @return true iff the given component is a finite number.
	 *       | result == Double.isFinite(component)
	*/
	public static boolean isValidComponent(double component) {
		return Double.isFinite(component);
	}
	
	/**
	 * Set the xComponent of this physical vector to the given xComponent.
	 * 
	 * @param  xComponent
	 *         The new xComponent for this physical vector.
	 * @post   If the given xComponent is valid, the xComponent of this new physical vector is equal to the given xComponent.
	 *       | if (isValidComponent(xComponent))
	 *       |		then new.getxComponent() == xComponent
	 * @throws IllegalComponentException
	 *         The given xComponent is not a valid component for any position.
	 *       | ! isValidComponent(xComponent)
	 */
	@Raw
	public void setxComponent(double xComponent) throws IllegalComponentException {
		if (! isValidComponent(xComponent))
			throw new IllegalComponentException();
		this.xComponent = xComponent;
	}
	
	/**
	 * Variable registering the xComponent of this physical vector.
	 */
	private double xComponent;
	
	
	/**
	 * Return the yComponent of this physical vector.
	 */
	@Basic @Raw
	public double getyComponent() {
		return this.yComponent;
	}
	
	/**
	 * Set the yComponent of this physical vector to the given yComponent.
	 * 
	 * @param  yComponent
	 *         The new yComponent for this physical vector.
	 * @post   If the given yComponent is valid, the yComponent of this new physical vector is equal to the given yComponent.
	 *       | if (isValidComponent(yComponent))
	 *       |		then new.getyComponent() == yComponent
	 * @throws IllegalComponentException
	 *         The given yComponent is not a valid component for any position.
	 *       | ! isValidComponent(yComponent)
	 */
	@Raw
	public void setyComponent(double yComponent) {
		if (! isValidComponent(yComponent))
			throw new IllegalComponentException();
		this.yComponent = yComponent;
	}
	
	/**
	 * Variable registering the yComponent of this physical vector.
	 */
	private double yComponent;
	
	
	/**
	 * Set the xComponent and yComponent of this physical vector to the given xComponent and yComponent
	 * @param xComponent
	 * 			The new xComponent for this physical vector.
	 * @param yComponent
	 * 			The new yComponent for this physical vector.
	 * @effect The new xComponent of this physical vector is set to the given xComponent.
	 * 			| setxComponent(xComponent)
	 * @effect The new yComponent of this physical vector is set to the given yComponent.
	 * 			| setyComponent(yComponent)
	 */
	public void setComponents(double xComponent, double yComponent) throws IllegalComponentException {
		setxComponent(xComponent);
		setyComponent(yComponent);
	}
	
	/**
	 * Return this physical an array of length 2, with the xComponent at index 0
	 *  and the yComponent at index 1. 
	 * @return  an array of length 2, with the xComponent at index 0
	 *  		and the yComponent at index 1.
	 * 			| result == new double[] {getxComponent(), getyComponent()}
	 */
	public double[] getAsArray() {
		return new double[] {getxComponent(), getyComponent()};
	}
}
