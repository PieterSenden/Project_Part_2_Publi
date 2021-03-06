package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

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
@Value
abstract class PhysicalVector {


	/**
	 * Initialize this new physical vector with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new physical vector.
	 * @param  yComponent
	 *         The yComponent for this new physical vector.
	 * @post   If the given xComponent is valid, the xComponent of this new physical vector is equal to the given xComponent.
	 *       | if (isValidComponent(xComponent))
	 *       |		then new.getxComponent() == xComponent
	 * @post   If the given yComponent is valid, the yComponent of this new physical vector is equal to the given yComponent.
	 *       | if (isValidComponent(yComponent))
	 *       |		then new.getyComponent() == yComponent
	 * @throws IllegalComponentException
	 * 		   One of the given components is not valid.
	 * 		 | ! isValidComponent(xComponent) || ! isValidComponent(yComponent)
	 */
	@Raw
	protected PhysicalVector(double xComponent, double yComponent) throws IllegalComponentException {
		if (! isValidComponent(xComponent))
			throw new IllegalComponentException();
		this.xComponent = xComponent;
		if (! isValidComponent(yComponent))
			throw new IllegalComponentException();
		this.yComponent = yComponent;
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
	 * Return the xComponent of this physical vector.
	 */
	@Basic @Raw
	public double getxComponent() {
		return this.xComponent;
	}
	
	/**
	 * Variable registering the xComponent of this physical vector.
	 */
	private final double xComponent;
	
	
	/**
	 * Return the yComponent of this physical vector.
	 */
	@Basic @Raw
	public double getyComponent() {
		return this.yComponent;
	}
	
	/**
	 * Variable registering the yComponent of this physical vector.
	 */
	private final double yComponent;
	
	/**
	 * Return this physical an array of length 2, with the xComponent at index 0
	 *  and the yComponent at index 1.
	 *  
	 * @return  an array of length 2, with the xComponent at index 0
	 *  		and the yComponent at index 1.
	 * 			| result == new double[] {getxComponent(), getyComponent()}
	 */
	public double[] getAsArray() {
		return new double[] {getxComponent(), getyComponent()};
	}
	
	/**
	 * Calculate the Euclidean scalar product of two physical vectors.
	 * 
	 * @param other
	 * 			The other physical vector involved in the scalar product.
	 * @return	The scalar product of this physical vector and the given other physical vector.
	 * 			| result == getxComponent() * other.getxComponent() + getyComponent() * other.getyComponent()
	 * @throws NullPointerException
	 * 			The given other physical vector is not effective.
	 * 			| other == null
	 * @throws NotFiniteException
	 * 			The computation of the scalar product results in an infinite number or Nan.
	 * 			| !Double.isFinite(getxComponent() * other.getxComponent() + getyComponent() * other.getyComponent())
	 */
	public double scalarProductWith(PhysicalVector other) throws NullPointerException, NotFiniteException {
		double result = (getxComponent() * other.getxComponent() + getyComponent() * other.getyComponent());
		if (!Double.isFinite(result))
			throw new NotFiniteException();
		return result;
	}

	/**
	 * Return the difference of this physical vector with the given other physical vector.
	 * 
	 * @param other
	 * 			The second physical vector (after minus sign).
	 * @throws NullPointerException
	 * 			The given other physical vector is not effective.
	 * 			| other == null
	 * @throws IllegalArgumentException
	 * 			The given other physical vector does not have the same dynamic type as this physical vector.
	 * 			| this.getClass() != other.getClass()
	 */
	public abstract PhysicalVector vectorMinus(PhysicalVector other) throws NullPointerException, IllegalComponentException,
																						IllegalArgumentException;
}