package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.representation.Bullet;
import asteroids.model.representation.Velocity;

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
	public void canHaveAsVelocity_TrueCase() {
		Velocity vel = new Velocity(bullet_originZeroVelocity.getSpeedLimit()/2, 0);
		assertTrue(bullet_originZeroVelocity.canHaveAsVelocity(vel));
	}
}
