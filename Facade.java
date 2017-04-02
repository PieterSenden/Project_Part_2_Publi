package asteroids.facade;

import java.util.Collection;
import java.util.Set;

import asteroids.model.representation.*;
import asteroids.part2.CollisionListener;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

public class Facade implements IFacade {

	/**************
	 * SHIP: Basic methods
	 *************/

	/**
	 * Create a new non-null ship with the given position, velocity, radius,
	 * direction and mass.
	 * 
	 * The thruster of the new ship is initially inactive. The ship is not
	 * located in a world.
	 */
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction,
			double mass) throws ModelException {
		try {
			return new Ship(x, y, xVelocity, yVelocity, radius, direction, mass);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Terminate <code>ship</code>.
	 */
	public void terminateShip(Ship ship) throws ModelException {
		try {
			ship.terminate();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Check whether <code>ship</code> is terminated.
	 */
	public boolean isTerminatedShip(Ship ship) throws ModelException {
		try {
			return ship.isTerminated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	@Override
	public double[] getShipPosition(Ship ship) throws ModelException {
		try {
			return ship.getPosition().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double[] getShipVelocity(Ship ship) throws ModelException {
		try {
			return ship.getVelocity().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getShipRadius(Ship ship) throws ModelException {
		try {
			return ship.getRadius();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getShipOrientation(Ship ship) throws ModelException {
		try {
			return ship.getOrientation();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void turn(Ship ship, double angle) throws ModelException {
		try {
			ship.turn(angle);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the total mass of <code>ship</code> (i.e., including bullets
	 * loaded onto the ship).
	 */
	public double getShipMass(Ship ship) throws ModelException {
		try {
			return ship.getTotalMass();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the world of <code>ship</code>.
	 */
	public World getShipWorld(Ship ship) throws ModelException {
		try {
			return ship.getWorld();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return whether <code>ship</code>'s thruster is active.
	 */
	public boolean isShipThrusterActive(Ship ship) throws ModelException {
		try {
			return ship.hasThrusterActivated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Enables or disables <code>ship</code>'s thruster depending on the value
	 * of the parameter <code>active</code>.
	 */
	public void setThrusterActive(Ship ship, boolean active) throws ModelException {
		try {
			ship.setThrust(active);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the acceleration of <code>ship</code>.
	 */
	public double getShipAcceleration(Ship ship) throws ModelException {
		try {
			return ship.getAcceleration();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}


	/**************
	 * BULLET: Basic methods
	 *************/

	/**
	 * Create a new non-null bullet with the given position, velocity and
	 * radius,
	 * 
	 * The bullet is not located in a world nor loaded on a ship.
	 */
	public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		try {
			return new Bullet(x, y, xVelocity, yVelocity, radius);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Terminate <code>bullet</code>.
	 */
	public void terminateBullet(Bullet bullet) throws ModelException {
		try {
			bullet.terminate();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Check whether <code>bullet</code> is terminated.
	 */
	public boolean isTerminatedBullet(Bullet bullet) throws ModelException {
		try {
			return bullet.isTerminated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the position of <code>ship</code> as an array containing the
	 * x-coordinate, followed by the y-coordinate.
	 */
	public double[] getBulletPosition(Bullet bullet) throws ModelException {
		try {
			return bullet.getPosition().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the velocity of <code>ship</code> as an array containing the
	 * velocity along the X-axis, followed by the velocity along the Y-axis.
	 */
	public double[] getBulletVelocity(Bullet bullet) throws ModelException {
		try {
			return bullet.getVelocity().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the radius of <code>bullet</code>.
	 */
	public double getBulletRadius(Bullet bullet) throws ModelException {
		try {
			return bullet.getRadius();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the mass of <code>bullet</code>.
	 */
	public double getBulletMass(Bullet bullet) throws ModelException {
		try {
			return bullet.getMass();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the world in which <code>bullet</code> is positioned.
	 * 
	 * This method must return null if a bullet is not positioned in a world, or
	 * if it is positioned on a ship.
	 */
	public World getBulletWorld(Bullet bullet) throws ModelException {
		try {
			return bullet.getWorld();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the ship in which <code>bullet</code> is positioned.
	 * 
	 * This method must return null if a bullet is not positioned on a ship.
	 */
	public Ship getBulletShip(Bullet bullet) throws ModelException {
		try {
			if (bullet.getShip() != null && bullet.getShip().hasLoadedInMagazine(bullet))
				return bullet.getShip();
			return null;
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the ship that fired <code>bullet</code>.
	 */
	public Ship getBulletSource(Bullet bullet) throws ModelException {
		try {
			if (bullet.getWorld() != null)
				return bullet.getShip();
			return null;
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**************
	 * WORLD: Basic methods
	 *************/

	/**
	 * Create a new world with the given <code>width</code> and
	 * <code>height</code>.
	 */
	public World createWorld(double width, double height) throws ModelException {
		try {
			return new World(height, width);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Terminate <code>world</code>.
	 */
	public void terminateWorld(World world) throws ModelException {
		try {
			world.terminate();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Check whether <code>world</code> is terminated.
	 */
	public boolean isTerminatedWorld(World world) throws ModelException {
		try {
			return world.isTerminated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return the size of <code>world</code> as an array containing the width,
	 * followed by the height.
	 */
	public double[] getWorldSize(World world) throws ModelException {
		try {
			return world.getDimensions();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return all ships located within <code>world</code>.
	 */
	public Set<? extends Ship> getWorldShips(World world) throws ModelException {
		try {
			return world.getShips();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return all bullets located in <code>world</code>.
	 */
	public Set<? extends Bullet> getWorldBullets(World world) throws ModelException {
		try {
			return world.getBullets();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Add <code>ship</code> to <code>world</code>.
	 */
	public void addShipToWorld(World world, Ship ship) throws ModelException {
		try {
			world.addEntity(ship);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Remove <code>ship</code> from <code>world</code>.
	 */
	public void removeShipFromWorld(World world, Ship ship) throws ModelException {
		try {
			world.removeEntity(ship);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Add <code>bullet</code> to <code>world</code>.
	 */
	public void addBulletToWorld(World world, Bullet bullet) throws ModelException {
		try {
			world.addEntity(bullet);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Remove <code>ship</code> from <code>world</code>.
	 */
	public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException {
		try {
			world.removeEntity(bullet);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**************
	 * SHIP: Methods related to loaded bullets
	 *************/

	/**
	 * Return the set of all bullets loaded on <code>ship</code>.
	 * 
	 * For students working alone, this method may return null.
	 */
	public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException {
		try {
			return ship.getMagazine();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return the number of bullets loaded on <code>ship</code>.
	 */
	public int getNbBulletsOnShip(Ship ship) throws ModelException {
		try {
			return ship.getNbOfBulletsInMagazine();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Load <code>bullet</code> on <code>ship</code>.
	 */
	public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException {
		try {
			ship.loadBullet(bullet);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Load <code>bullet</code> on <code>ship</code>.
	 * 
	 * For students working alone, this method must not do anything.
	 */
	public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException {
		try {
			ship.loadBullets((Bullet[])bullets.toArray());
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Remove <code>bullet</code> from <code>ship</code>.
	 */
	public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException {
		try {
			//TODO
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * <code>ship</code> fires a bullet.
	 */
	public void fireBullet(Ship ship) throws ModelException {
		try {
			ship.fireBullet();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/******************
	 * COLLISIONS
	 **************/

	/**
	 * Return the shortest time in which the given entity will collide with the
	 * boundaries of its world.
	 */
	public double getTimeCollisionBoundary(Object object) throws ModelException {
		try {
			return ((Entity)object).getTimeToCollisionWithBoundary();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return the first position at which the given entity will collide with the
	 * boundaries of its world.
	 */
	public double[] getPositionCollisionBoundary(Object object) throws ModelException {
		try {
			return ((Entity)object).getCollisionWithBoundaryPosition().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return the shortest time in which the first entity will collide with the
	 * second entity.
	 */
	public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException {
		try {
			return Entity.getTimeToCollision((Entity)entity1, (Entity)entity2);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return the first position at which the first entity will collide with the
	 * second entity.
	 */
	public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException {
		try {
			
			return Entity.getCollisionPosition((Entity)entity1, (Entity)entity2).getAsArray();
		}
		catch (NullPointerException exc) {
			return null;
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return the time that must pass before a boundary collision or an entity
	 * collision will take place in the given world. Positive Infinity is
	 * returned if no collision will occur.
	 */
	public double getTimeNextCollision(World world) throws ModelException {
		try {
			return world.getTimeToFirstCollision();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Return the position of the first boundary collision or entity collision
	 * that will take place in the given world. Null is returned if no collision
	 * will occur.
	 */
	public double[] getPositionNextCollision(World world) throws ModelException {
		try {
			return world.getPositionFirstCollision().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}

	}

	/**
	 * Advance <code>world</code> by <code>dt<code> seconds. 
	 * 
	 * To enable explosions within the UI, notify <code>collisionListener</code>
	 * whenever an entity collides with a boundary or another entity during this
	 * method. <code>collisionListener</code> may be null. If
	 * <code>collisionListener</code> is <code>null</code>, do not call its
	 * notify methods.
	 */
	public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException {
		try {
			world.evolve(dt, collisionListener);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the entity at the given <code>position</code> in the given
	 * <code>world</code>.
	 */
	public Object getEntityAt(World world, double x, double y) throws ModelException {
		try {
			return world.getEntityAt(new Position(x, y));
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Return a set of all the entities in the given world.
	 */
	public Set<? extends Object> getEntities(World world) throws ModelException {
		try {
			return world.getEntities();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
		try {
			return Entity.overlap(ship1, ship2);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	

	@Override
	public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

}
