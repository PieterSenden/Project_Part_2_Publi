package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestBullet {
	
	private static final double EPSILON = 0.0001;
	
	private static Bullet bullet_originZeroVelocity;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bullet_originZeroVelocity = new Bullet(0, 0, 0, 0, Bullet.getMinimalRadius(), 10e12, 10e20);
	}
	
	private static Bullet bullet_originWithVelocity;
	
	@Before
	public void setUp() throws Exception {
		bullet_originWithVelocity = new Bullet(0, 0, 1, -1, Bullet.getMinimalRadius(), 10e12, 10e20);
	}
	
	private static final double EPSILON = 0.0001;
	private static Bullet bullet;
	
	
	@Before
	public void setUp() {
		bullet = new Bullet(0, 0, 0, 0, Bullet.getMinimalRadius(), 7.8e12, 10e20);
	}
	
	@Test
	public void mostExtendedConstructor_LegalCase() {
		bullet = new Bullet(1, 2, 3, 4, Bullet.getMinimalRadius(), 7.8e12, 10e20);
		assertEquals(bullet.getPosition().getxComponent(), 1, EPSILON);
		assertEquals(bullet.getPosition().getyComponent(), 2, EPSILON);
		assertEquals(bullet.getVelocity().getxComponent(), 3, EPSILON);
		assertEquals(bullet.getVelocity().getyComponent(), 4, EPSILON);
		assertEquals(bullet.getRadius(), Bullet.getMinimalRadius(), EPSILON);
		assertEquals(bullet.getDensity(), 7.8e12, EPSILON);
		assertEquals(bullet.getMass(), 4.0 / 3 * Math.PI * Math.pow(Bullet.getMinimalRadius(), 3) * 7.8e12, EPSILON);
	}
	
	@Test(expected = IllegalComponentException.class)
	public void mostExtendedConstructor_IllegalComponent() throws Exception {
		bullet = new Bullet(Double.NaN, 2, 3, 4, 15, 7.8e12, 10e20);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public void mostExtendedConstructor_IllegalRadius() throws Exception {
		bullet = new Bullet(1, 2, 3, 4, 0.5, 7.8e12, 10e20);
	}
	
	@Test
	public void copy_RegularCase() {
		Bullet newBullet = bullet.copy();
		assertFalse(newBullet == bullet);
		assertEquals(bullet.getPosition().getxComponent(), newBullet.getPosition().getxComponent(), EPSILON);
		assertEquals(bullet.getPosition().getyComponent(), newBullet.getPosition().getyComponent(), EPSILON);
		assertEquals(bullet.getVelocity().getxComponent(), newBullet.getVelocity().getxComponent(), EPSILON);
		assertEquals(bullet.getVelocity().getyComponent(), newBullet.getVelocity().getyComponent(), EPSILON);
		assertEquals(bullet.getRadius(), newBullet.getRadius(), EPSILON);
		assertEquals(bullet.getDensity(), newBullet.getDensity(), EPSILON);
		assertEquals(bullet.getMass(), newBullet.getMass(), EPSILON);
	}
	
	@Test
	public void terminate_NotYetTerminatedCase() {
		
	}
	
	@Test
	public void terminate_AllreadyTerminatedCase() {
		
	}
	
	@Test
	public void move_ValidCase() {
		bullet_originWithVelocity.move(2);
		assertEquals(bullet_originWithVelocity.getPosition().getxComponent(), 2, EPSILON);
		assertEquals(bullet_originWithVelocity.getPosition().getyComponent(), -2, EPSILON);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void move_IllegalArgument() {
		bullet_originWithVelocity.move(-1);
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
	
	@Test
	public void bounceOfBoundary_MaximalNumberOfBouncesReached() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void resolveCollision_TerminatedBulletCase() {
		
	}
	
	@Test
	public void resolveCollision_WithBullet() {
		
	}
	
	@Test
	public void resolveCollision_WithOwnShip() {
		
	}
	
	@Test
	public void resolveCollision_WithEnemyShip() {
		
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
	public void canBeRemovedFromWorld_DoesNotCollideWithOwnShip() {
		
	}
	
	@Test
	public void canBeRemovedFromWorld_TrueCase() {
		
	}
	
}
