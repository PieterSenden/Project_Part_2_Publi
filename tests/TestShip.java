package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestShip {
	
	private Ship myShip;
	private static Velocity velocity_Legal, velocity_TooLarge;
	private static Ship ship_MinimalRadius, ship_Orientation45deg;
	private static Ship ship_Overlap1, ship_Overlap2;
	private static Ship ship_Collision1, ship_Collision2, ship_Collision3;
	private static final double EPSILON = 0.00001;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		velocity_Legal = new Velocity(10, 20);
		velocity_TooLarge = new Velocity(300000, 100);
		ship_Overlap1 = new Ship(0, 0, 10);
		ship_Overlap2 = new Ship(30,40,10);		
	}

	@Before
	public void setUp() throws Exception {
		myShip = new Ship(0, 0, 10, 10, 10, 0);
		ship_MinimalRadius = new Ship(0, 0, Ship.getMinimalRadius());
		ship_Orientation45deg = new Ship(0, 0, 0, 0, Ship.getMinimalRadius(), Math.PI/4);
		ship_Collision1 = new Ship(-20, -20, 1, 1, 10, 0);
		ship_Collision2 = new Ship(20, 20, -2, -2, 10, 0);
		ship_Collision3 = new Ship(0, 0, 100, 100, 10, 0);
	}

	@Test
	public void mostExtendedConstructor_LegalCase() throws Exception {
		myShip = new Ship(1, 2, 3, 4, 15, Math.PI / 2);
		assertEquals(myShip.getPosition().getxComponent(), 1, EPSILON);
		assertEquals(myShip.getPosition().getyComponent(), 2, EPSILON);
		assertEquals(myShip.getVelocity().getxComponent(), 3, EPSILON);
		assertEquals(myShip.getVelocity().getyComponent(), 4, EPSILON);
		assertEquals(myShip.getRadius(), 15, EPSILON);
		assertEquals(myShip.getOrientation(), Math.PI / 2, EPSILON);
	}
	
	@Test(expected = IllegalComponentException.class)
	public void mostExtendedConstructor_IllegalComponent() throws Exception {
		myShip = new Ship(Double.NaN, 2, 3, 4, 15, Math.PI / 2);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public void mostExtendedConstructor_IllegalRadius() throws Exception {
		myShip = new Ship(1, 2, 3, 4, 3, Math.PI / 2);
	}
	
	@Test
	public void leastExtendedConstructor_LegalCase() throws Exception {
		myShip = new Ship(1, 2, 15);
		assertEquals(myShip.getPosition().getxComponent(), 1, EPSILON);
		assertEquals(myShip.getPosition().getyComponent(), 2, EPSILON);
		assertEquals(myShip.getVelocity().getxComponent(), 0, EPSILON);
		assertEquals(myShip.getVelocity().getyComponent(), 0, EPSILON);
		assertEquals(myShip.getRadius(), 15, EPSILON);
		assertEquals(myShip.getOrientation(), 0, EPSILON);
	}
	
	@Test(expected = IllegalComponentException.class)
	public void leastExtendedConstructor_IllegalComponent() throws Exception {
		myShip = new Ship(Double.NaN, 2, 15);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public void leastExtendedConstructor_IllegalRadius() throws Exception {
		myShip = new Ship(1, 2, 3);
	}
	
	@Test
	public void terminate_NotYetTerminatedCase() {
		
	}
	
	@Test
	public void terminate_AllreadyTerminatedCase() {
		
	}
	
	@Test
	public void move_LegalCase() {
		myShip.move(1);
		assertEquals(myShip.getPosition().getxComponent(), 0 + 1 * 10, EPSILON);
		assertEquals(myShip.getPosition().getyComponent(), 0 + 1 * 10, EPSILON);
	}
	
//	@Test
//	public void isValidOrientation_TrueCase() {
//		assertTrue(Ship.isValidOrientation(Math.PI / 2));
//	}
//	
//	@Test
//	public void isValidOrientation_FalseCase() {
//		assertFalse(Ship.isValidOrientation(-10));
//	}
//
//	@Test
//	public void turn_LegalCase() {
//		myShip.turn(4);
//		assertEquals(myShip.getOrientation(), 0 + 4, EPSILON);
//	}
//	
//	@Test
//	public void testIsValidRadius_ValidCase() {
//		assertTrue(Ship.isValidRadius(Ship.getMinimalRadius()));
//	}
//	
//	@Test
//	public void testIsValidRadius_InvalidCase() {
//		assertFalse(Ship.isValidRadius(Ship.getMinimalRadius()-1));
//	}
//	
//	@Test
//	public void testIsValidMinimalRadius_ValidCase() {
//		assertTrue(Ship.isValidMinimalRadius(1));
//	}
//	
//	@Test
//	public void testIsValidMinimalRadius_InvalidCase() {
//		assertFalse(Ship.isValidMinimalRadius(0));
//	}
//	
//	@Test
//	public void testSetMinimalRadius_LegalRadius() {
//		Ship.setMinimalRadius(5);
//		assertEquals(Ship.getMinimalRadius(), 5, EPSILON);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void testSetMinimalRadius_IllegalRadius() {
//		Ship.setMinimalRadius(-5);
//	}
	
	
	@Test
	public void thrust_ThrusterOff() {
		ship_Orientation45deg.thrustOff();
		ship_Orientation45deg.thrust(1);
		assertEquals(ship_Orientation45deg.getVelocity().getxComponent(), 0, EPSILON);
		assertEquals(ship_Orientation45deg.getVelocity().getyComponent(), 0, EPSILON);
	}
	
	@Test
	public void thrust_ThrusterOnLegalCase() {
		ship_Orientation45deg.thrustOn();
		ship_Orientation45deg.thrust(1);
		assertEquals(ship_Orientation45deg.getVelocity().getxComponent(), Math.sqrt(2) / 2 * ship_Orientation45deg.getAcceleration() * 1, EPSILON);
		assertEquals(ship_Orientation45deg.getVelocity().getyComponent(), Math.sqrt(2) / 2 * ship_Orientation45deg.getAcceleration() * 1, EPSILON);
	}
	
	@Test
	public void thrust_ThrusterOnNegativeAmount() {
		ship_Orientation45deg.thrustOn();
		ship_Orientation45deg.thrust(-5);
		assertEquals(ship_Orientation45deg.getVelocity().getxComponent(), 0, EPSILON);
		assertEquals(ship_Orientation45deg.getVelocity().getyComponent(), 0, EPSILON);
	}
	
	@Test
	public void thrust_ThursterOnTooLargeAmount() {
		ship_Orientation45deg.thrustOn();
		ship_Orientation45deg.thrust(10e20);
		assertEquals(ship_Orientation45deg.getVelocity().getxComponent(), ship_Orientation45deg.getSpeedLimit()*Math.sqrt(2)/2, EPSILON);
		assertEquals(ship_Orientation45deg.getVelocity().getyComponent(), ship_Orientation45deg.getSpeedLimit()*Math.sqrt(2)/2, EPSILON);
	}
	
	@Test
	public void bounceOfBoundary_HorizontalBoundary() {
		
	}
	
	@Test
	public void bounceOfBoundary_VerticalBoundary() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void bounceOfBoundary_TerminatedShipCase() {
	
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void bounceOfBoundary_NonEffectiveWorldCase() {
		
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void bounceOfBoundary_NoCollisionCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void resolveCollision_TerminatedShipCase() {
		
	}
	
	@Test
	public void resolveCollision_WithShip() {
		
	}
	
	@Test
	public void resolveCollision_WithOwnBullet() {
		
	}
	
	@Test
	public void resolveCollision_WithEnemyBullet() {
		
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_NonEffectiveWorld() {
		
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_NoCollisionCase() {
		
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_DifferentWorldsCase() {
		
	}
	
	@Test
	public void canBeRemovedFromWorld_NonEffectiveWorld() {
		
	}
	
	@Test
	public void canBeRemovedFromWorld_FiredBulletsNonEmpty() {
		
	}
	
	@Test
	public void canBeRemovedFromWorld_TrueCase() {
		
	}
	
	@Test
	public void canHaveAsBullet_TrueCase() {
		
	}
	
	@Test
	public void fireBullet_EffectiveCase() {
		
	}
	
	@Test
	public void fireBullet_EmptyMagazine() {
		
	}
	
	@Test
	public void fireBullet_NonEffectiveWorld() {
		
	}
	
	@Test
	public void fireBullet_TerminatedCase() {
		
	}
	
	@Test
	public void fireBullet_OutOfBoundaries() {
		
	}
	
	@Test
	public void fireBullet_ImmediateCollision() {
		
	}
	
	@Test
	public void loadBullet_EffectiveCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void loadBullet_TerminatedCase() {
		
	}
	
	@Test(expected = IllegalBulletException.class)
	public void loadBullet_InvalidBullet() {
		
	}
	
	@Test(expected = IllegalBulletException.class)
	public void loadBullet_BulletAlreadyLoadedOnOtherShip() {
		
	}
	
	@Test
	public void loadBullets_EffectiveCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void loadBullets_TerminatedCase() {
		
	}
}
