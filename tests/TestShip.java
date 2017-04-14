package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestShip {
	
	private Ship constructorShip, ship1, ship2, enemyShip, shipAtBoundary, collidingShip1, collidingShip2;
	private static Ship shipOrientation45deg;
	private static Bullet ownBullet1, ownBullet2, ownBullet3, oversizedBullet, enemyBullet;
	private static World world1;
	
	private static final double EPSILON = 0.00001;
	

	@Before
	public void setUp() throws Exception {
		shipOrientation45deg = new Ship(0, 0, 0, 0, Ship.getMinimalRadius(), Math.PI/4);
		ship1 = new Ship(500, 500, 0, 0, 10, 0);
		ship2 = new Ship(750, 750, 0, 0, 10, 0);
		shipAtBoundary = new Ship(990, 100, 0, 0, 10, 0);
		
		world1 = new World(1000, 1000);
		
		ownBullet1 = new Bullet(10, 10, 0, 0, 3);
		ownBullet2 = new Bullet(10, 10, 0, 0, 3);
		ownBullet3 = new Bullet(10, 10, 0, 0, 3);
		oversizedBullet = new Bullet(10, 10, 0, 0, 200);
	}

	@Test
	public void mostExtendedConstructor_LegalCase() throws Exception {
		constructorShip = new Ship(1, 2, 3, 4, 15, Math.PI / 2, 10e30, true);
		assertEquals(constructorShip.getPosition().getxComponent(), 1, EPSILON);
		assertEquals(constructorShip.getPosition().getyComponent(), 2, EPSILON);
		assertEquals(constructorShip.getVelocity().getxComponent(), 3, EPSILON);
		assertEquals(constructorShip.getVelocity().getyComponent(), 4, EPSILON);
		assertEquals(constructorShip.getRadius(), 15, EPSILON);
		assertEquals(constructorShip.getOrientation(), Math.PI / 2, EPSILON);
		assertEquals(constructorShip.getMass(), 10e30, EPSILON);
		assertTrue(constructorShip.hasThrusterActivated());
	}
	
	@Test(expected = IllegalComponentException.class)
	public void mostExtendedConstructor_IllegalComponent() throws Exception {
		constructorShip = new Ship(Double.NaN, 2, 3, 4, 15, Math.PI / 2, 10e30, true);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public void mostExtendedConstructor_IllegalRadius() throws Exception {
		constructorShip = new Ship(1, 2, 3, 4, 5, Math.PI / 2, 10e30, true);
	}
	
	@Test
	public void leastExtendedConstructor_LegalCase() throws Exception {
		constructorShip = new Ship(1, 2, 15);
		assertEquals(constructorShip.getPosition().getxComponent(), 1, EPSILON);
		assertEquals(constructorShip.getPosition().getyComponent(), 2, EPSILON);
		assertEquals(constructorShip.getVelocity().getxComponent(), 0, EPSILON);
		assertEquals(constructorShip.getVelocity().getyComponent(), 0, EPSILON);
		assertEquals(constructorShip.getRadius(), 15, EPSILON);
		assertEquals(constructorShip.getOrientation(), 0, EPSILON);
	}
	
	@Test(expected = IllegalComponentException.class)
	public void leastExtendedConstructor_IllegalComponent() throws Exception {
		constructorShip = new Ship(Double.NaN, 2, 15);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public void leastExtendedConstructor_IllegalRadius() throws Exception {
		constructorShip = new Ship(1, 2, 3);
	}
	
	@Test
	public void terminate_NotYetTerminatedCase() {
		world1.addEntity(ship1);
		ship1.loadBullets(new Bullet[] {ownBullet1, ownBullet2, ownBullet3});
		ship1.fireBullet();
		ship1.terminate();
		assertTrue(ship1.getFiredBullets().isEmpty());
		assertTrue(ship1.getMagazine().isEmpty());
	}
	
	@Test
	public void move_LegalCase() {
		ship1 = new Ship(400, 400, 10, 10, 20, 0);
		ship1.loadBullet(ownBullet1);
		ship1.move(1);
		assertEquals(ship1.getPosition(), new Position(410, 410));
		assertEquals(ownBullet1.getPosition(), new Position(410, 410));
	}
	
	@Test
	public void thrust_ThrusterOff() {
		shipOrientation45deg.thrustOff();
		shipOrientation45deg.thrust(1);
		assertEquals(shipOrientation45deg.getVelocity().getxComponent(), 0, EPSILON);
		assertEquals(shipOrientation45deg.getVelocity().getyComponent(), 0, EPSILON);
	}
	
	@Test
	public void thrust_ThrusterOnLegalCase() {
		shipOrientation45deg.thrustOn();
		shipOrientation45deg.thrust(1);
		assertEquals(shipOrientation45deg.getVelocity().getxComponent(), Math.sqrt(2) / 2 * shipOrientation45deg.getAcceleration() * 1, EPSILON);
		assertEquals(shipOrientation45deg.getVelocity().getyComponent(), Math.sqrt(2) / 2 * shipOrientation45deg.getAcceleration() * 1, EPSILON);
	}
	
	@Test
	public void thrust_ThrusterOnNegativeAmount() {
		shipOrientation45deg.thrustOn();
		shipOrientation45deg.thrust(-5);
		assertEquals(shipOrientation45deg.getVelocity().getxComponent(), 0, EPSILON);
		assertEquals(shipOrientation45deg.getVelocity().getyComponent(), 0, EPSILON);
	}
	
	@Test
	public void thrust_ThursterOnTooLargeAmount() {
		shipOrientation45deg.thrustOn();
		shipOrientation45deg.thrust(10e20);
		assertEquals(shipOrientation45deg.getVelocity().getxComponent(), shipOrientation45deg.getSpeedLimit() * Math.sqrt(2)/2, EPSILON);
		assertEquals(shipOrientation45deg.getVelocity().getyComponent(), shipOrientation45deg.getSpeedLimit() * Math.sqrt(2)/2, EPSILON);
	}
	
	@Test
	public void loadBullet_EffectiveCase() {
		ship1.loadBullet(ownBullet1);
		assertTrue(ship1.hasLoadedInMagazine(ownBullet1));
		assertEquals(ownBullet1.getShip(), ship1);
		assertNull(ownBullet1.getWorld());
	}
	
	@Test(expected = TerminatedException.class)
	public void loadBullet_TerminatedCase() {
		ship1.terminate();
		ship1.loadBullet(ownBullet1);
	}
	
	@Test(expected = IllegalBulletException.class)
	public void loadBullet_BulletTooBig() {
		ship1.loadBullet(oversizedBullet);
	}
	
	@Test(expected = IllegalBulletException.class)
	public void loadBullet_BulletAlreadyLoadedOnOtherShip() {
		ship2.loadBullet(ownBullet1);
		ship1.loadBullet(ownBullet1);
	}
	
	@Test
	public void loadBullets_EffectiveCase() {
		ship1.loadBullets(new Bullet[] {ownBullet1, ownBullet2, ownBullet3});
		assertTrue(ship1.hasLoadedInMagazine(ownBullet1));
		assertTrue(ship1.hasLoadedInMagazine(ownBullet2));
		assertTrue(ship1.hasLoadedInMagazine(ownBullet3));
	}
	
	@Test(expected = TerminatedException.class)
	public void loadBullets_TerminatedCase() {
		ship1.terminate();
		ship1.loadBullets(new Bullet[] {ownBullet1, ownBullet2, ownBullet3});
	}
	
	@Test
	public void fireBullet_EffectiveCase() {
		world1.addEntity(ship1);
		ship1.loadBullet(ownBullet1);
		ship1.fireBullet();
		assertTrue(ship1.hasFired(ownBullet1));
		assertFalse(ship1.hasLoadedInMagazine(ownBullet1));
		assertEquals(ship1.getWorld(), ownBullet1.getWorld());
	}
	
	@Test
	public void fireBullet_NonEffectiveWorld() {
		ship1.loadBullet(ownBullet1);
		ship1.fireBullet();
		assertTrue(ship1.hasLoadedInMagazine(ownBullet1));
		assertFalse(ship1.hasFired(ownBullet1));
	}
	
	@Test
	public void fireBullet_OutOfBoundaries() {
		world1.addEntity(shipAtBoundary);
		shipAtBoundary.loadBullet(ownBullet1);
		shipAtBoundary.fireBullet();
		ownBullet1.getPosition();
		assertTrue(ownBullet1.isTerminated());
	}
	
	@Test
	public void fireBullet_ImmediateCollision() {
		enemyShip = new Ship(520, 500, 0, 0, 10, 0);
		world1.addEntity(enemyShip);
		world1.addEntity(ship1);
		ship1.loadBullet(ownBullet1);
		ship1.fireBullet();
		assertTrue(enemyShip.isTerminated());
		assertTrue(ownBullet1.isTerminated());
	}
	
	@Test
	public void bounceOfBoundary_HorizontalBoundary() {
		shipAtBoundary = new Ship(100, 990, 20, 10, 10, 0);
		world1.addEntity(shipAtBoundary);
		shipAtBoundary.bounceOfBoundary();
		assertEquals(shipAtBoundary.getVelocity(), new Velocity(20, -10));
	}
	
	@Test
	public void bounceOfBoundary_VerticalBoundary() {
		shipAtBoundary = new Ship(990, 100, 20, 10, 10, 0);
		world1.addEntity(shipAtBoundary);
		shipAtBoundary.bounceOfBoundary();
		assertEquals(shipAtBoundary.getVelocity(), new Velocity(-20, 10));
	}
	
	@Test(expected = TerminatedException.class)
	public void bounceOfBoundary_TerminatedShipCase() {
		shipAtBoundary = new Ship(990, 100, 20, 10, 10, 0);
		world1.addEntity(shipAtBoundary);
		shipAtBoundary.terminate();
		shipAtBoundary.bounceOfBoundary();
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void bounceOfBoundary_NonEffectiveWorldCase() {
		shipAtBoundary = new Ship(990, 100, 20, 10, 10, 0);
		shipAtBoundary.bounceOfBoundary();
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void bounceOfBoundary_NoCollisionCase() {
		shipAtBoundary = new Ship(200, 100, 20, 10, 10, 0);
		world1.addEntity(shipAtBoundary);
		shipAtBoundary.bounceOfBoundary();
	}
	
	@Test(expected = TerminatedException.class)
	public void resolveCollision_TerminatedShipCase() {
		collidingShip1 = new Ship(100, 200, 10);
		collidingShip2 = new Ship(100, 300, 10);
		world1.addEntity(collidingShip1);
		world1.addEntity(collidingShip2);
		collidingShip1.terminate();
		collidingShip1.resolveCollision(collidingShip2);
	}
	
	@Test
	public void resolveCollision_WithShip() {
		collidingShip1 = new Ship(100, 200, 10, 5, 10,  0, 10e20);
		collidingShip2 = new Ship(130, 240, -20, -30, 40, 0, 10e20);
		world1.addEntity(collidingShip1);
		world1.addEntity(collidingShip2);
		collidingShip1.resolveCollision(collidingShip2);
		assertEquals(collidingShip1.getVelocity().getxComponent(), -17.6, EPSILON);
		assertEquals(collidingShip1.getVelocity().getyComponent(), -31.8, EPSILON);
		assertEquals(collidingShip2.getVelocity().getxComponent(), 7.6, EPSILON);
		assertEquals(collidingShip2.getVelocity().getyComponent(), 6.8, EPSILON);		
	}
	
	@Test
	public void resolveCollision_WithOwnBullet() {
		collidingShip1 = new Ship(100, 200, 500, 0, 40, 0);
		ownBullet1 = new Bullet(30 ,30, 0, 0, 10);
		World bigWorld = new World(10e10, 10e10);
		bigWorld.addEntity(collidingShip1);
		collidingShip1.loadBullet(ownBullet1);
		collidingShip1.fireBullet();
		double time = Entity.getTimeToCollision(collidingShip1, ownBullet1);
		ownBullet1.move(time);
		collidingShip1.move(time);
		assertTrue(Entity.apparentlyCollide(ownBullet1, collidingShip1));
		collidingShip1.resolveCollision(ownBullet1);
		assertTrue(collidingShip1.hasLoadedInMagazine(ownBullet1));
		assertFalse(collidingShip1.hasFired(ownBullet1));
		assertEquals(ownBullet1.getShip(), collidingShip1);
	}
	
	@Test
	public void resolveCollision_WithEnemyBullet() {
		collidingShip1 = new Ship(100, 200, 30, 30, 10, 0);
		enemyBullet = new Bullet(115, 200, -200, -200, 5);
		world1.addEntity(collidingShip1);
		world1.addEntity(enemyBullet);
		collidingShip1.resolveCollision(enemyBullet);
		assertTrue(collidingShip1.isTerminated());
		assertTrue(enemyBullet.isTerminated());
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_NonEffectiveWorld() {
		collidingShip1 = new Ship(100, 200, 10);
		collidingShip2 = new Ship(100, 300, 10);
		world1.addEntity(collidingShip1);
		collidingShip1.resolveCollision(collidingShip2);
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_NoCollisionCase() {
		collidingShip1 = new Ship(100, 200, 10);
		collidingShip2 = new Ship(100, 300, 10);
		world1.addEntity(collidingShip1);
		world1.addEntity(collidingShip2);
		collidingShip1.resolveCollision(collidingShip2);		
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_DifferentWorldsCase() {
		collidingShip1 = new Ship(100, 200, 10);
		collidingShip2 = new Ship(100, 300, 10);
		world1.addEntity(collidingShip1);
		World world2 = new World(2000, 2000);
		world2.addEntity(collidingShip2);
		collidingShip1.resolveCollision(collidingShip2);
	}
	
	@Test
	public void canBeRemovedFromWorld_NonEffectiveWorld() {
		assertFalse(ship1.canBeRemovedFromWorld());
	}
	
	@Test
	public void canBeRemovedFromWorld_FiredBulletsNonEmpty() {
		world1.addEntity(ship1);
		ship1.loadBullet(ownBullet1);
		ship1.fireBullet();
		assertFalse(ship1.canBeRemovedFromWorld());
	}
	
	@Test
	public void canBeRemovedFromWorld_TrueCase() {
		world1.addEntity(ship1);
		ship1.canBeRemovedFromWorld();
	}
	
}
