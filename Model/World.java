package asteroids.model.representation;

import be.kuleuven.cs.som.annotate.*;

/**
 * @invar  The given height must be a valid height for any world.
 *       | isValidHeight(this.getHeight())
 * @invar  The given width must be a valid width for any world.
 *       | isValidWidth(this.getWidth())
 */
public class World {
	/**
	 * Initialize this new world with given height and width.
	 * 
	 * @param  height
	 *         The height for this new world.
	 * @param  width
	 *         The width for this new world.
	 * @post   If the given height is a valid height for any world, the height of this new world is equal to the given
	 *         height. Otherwise, the height of this new world is equal to getMaxHeight().
	 *       | if (isValidHeight(height))
	 *       |   then new.getHeight() == height
	 *       |   else new.getHeight() == getMaxHeight()
	 * @post   If the given width is a valid width for any world, the width of this new world is equal to the given
	 *         width. Otherwise, the width of this new world is equal to getMaxWidth().
	 *       | if (isValidWidth(width))
	 *       |   then new.getWidth() == width
	 *       |   else new.getWidth() == getMaxWidth()
	 */
	public World(double height, double width) {
		if (! isValidHeight(height))
			height = getMaxHeight();
		this.height = height;
		if (! isValidWidth(width))
			width = getMaxWidth();
		this.width = width;
	}
	
	

//	public World(double height) {
//		if (! isValidHeight(height))
//			height = getMaxHeight();
//		this.height = height;
//	}
	
	/**
	 * Return the height of this world.
	 */
	@Basic @Raw @Immutable
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Check whether this world can have the given height as its height.
	 *  
	 * @param  height
	 *         The height to check.
	 * @return 
	 *       | result == (0 < height) && (height <= getMaxHeight())
	*/
	@Raw
	public static boolean isValidHeight(double height) {
		return (0 < height) && (height <= getMaxHeight());
	}
	
	/**
	 * Variable registering the height of this world.
	 */
	private final double height;
	
	
	/**
	 * Return the maximal height for any world.
	 */
	@Basic @Raw
	public static double getMaxHeight() {
		return maxHeight;
	}
	
	/**
	 * A variable registering the maximal height of any world.
	 */
	private static final double maxHeight = Double.MAX_VALUE;
	
	
	/**
	 * Return the width of this world.
	 */
	@Basic @Raw @Immutable
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Check whether this world can have the given width as its width.
	 *  
	 * @param  width
	 *         The width to check.
	 * @return 
	 *       | result == (0 < width) && (width <= getMaxWidth())
	*/
	@Raw
	public static boolean isValidWidth(double width) {
		return (0 < width) && (width <= getMaxWidth());
	}
	
	/**
	 * Variable registering the width of this world.
	 */
	private final double width;
	
	
	/**
	 * Return the maximal width for any world.
	 */
	@Basic @Raw
	public static double getMaxWidth() {
		return maxWidth;
	}
	
	/**
	 * A variable registering the maximal height of any world.
	 */
	private static final double maxWidth = Double.MAX_VALUE;
}
