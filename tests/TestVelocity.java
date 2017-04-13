package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestVelocity {
	private static final double EPSILON = 0.0001;
	
	private static Velocity myVelocity, velocity_00, otherVelocity_00;
	private static Position position_00;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		velocity_00 = new Velocity(0, 0);
		otherVelocity_00 = new Velocity(0, 0);
		position_00 = new Position(0, 0);
	}
	
	@Before
	public void setUp() throws Exception {
		myVelocity = new Velocity(6, 8);
	}

	@Test
	public void constructor_RegularCase() {
		myVelocity = new Velocity(20, 20);
		assertEquals(myVelocity.getxComponent(), 20, 0.01);
		assertEquals(myVelocity.getyComponent(), 20, 0.01);
	}

	@Test(expected=IllegalComponentException.class)
	public void constructor_NonRegularCase() {
		myVelocity = new Velocity(Double.POSITIVE_INFINITY, 10);
	}
	
	@Test
	public void getSpeed() {
		assertEquals(myVelocity.getSpeed(), 10, EPSILON);
	}
	
	@Test
	public void equals_EqualCase() {
		assertTrue(velocity_00.equals(otherVelocity_00));
	}
	
	@Test
	public void equals_NotSameType() {
		assertFalse(velocity_00.equals(position_00));
	}
	
	@Test
	public void equals_NotEqualPosition() {
		assertFalse(velocity_00.equals(myVelocity));
	}
}
