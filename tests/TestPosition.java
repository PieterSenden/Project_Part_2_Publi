package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.representation.*;

public class TestPosition {
	
	private static Position position_00, otherPosition_00, position_34;
	private static Velocity velocity_00, velocity_12;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		position_00 = new Position(0, 0);
		otherPosition_00 = new Position(0, 0);
		position_34 = new Position(3, 4);
		velocity_00 = new Velocity(0, 0);
		velocity_12 = new Velocity(1, 2);
	}
	
	@Test
	public void Constructor_LegalCase() {
		Position myPosition = new Position(1,2);
		assertEquals(myPosition.getxComponent(), 1, 0.01);
		assertEquals(myPosition.getyComponent(), 2, 0.01);
	}
	
	@Test
	public void getDistanceBetween_RegularCase() {
		assertEquals(Position.getDistanceBetween(position_00, position_34), 5, 0.01);
	}
	
	@Test(expected=NullPointerException.class)
	public void getDistanceBetween_NonEffectiveCase() {
		Position.getDistanceBetween(position_00, null);
	}
	
	@Test
	public void move_LegalCase() {
		Position movedPosition = position_00.move(velocity_12, 5);
		assertEquals(movedPosition, new Position(5, 10));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void move_NegativeDuration() {
		position_00.move(velocity_12, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void move_NullVelocity() {
		position_00.move(null, 1);
	}
	
	@Test
	public void equals_EqualCase() {
		assertTrue(position_00.equals(otherPosition_00));
	}
	
	@Test
	public void equals_NotSameType() {
		assertFalse(position_00.equals(velocity_00));
	}
	
	@Test
	public void equals_NotEqualPosition() {
		assertFalse(position_00.equals(position_34));
	}
}
