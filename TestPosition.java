package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.IllegalComponentException;
import asteroids.model.Position;

public class TestPosition {
	
	private static Position position_00, position_34;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		position_00 = new Position(0,0);
		position_34 = new Position(3,4);
	}
	
	@Test
	public void testConstructor_LegalCase() {
		Position myPosition = new Position(1,2);
		assertEquals(myPosition.getxComponent(), 1, 0.01);
		assertEquals(myPosition.getyComponent(), 2, 0.01);
	}
	
	@Test(expected=IllegalComponentException.class)
	public void testConstructor_IllegalCase() {
		new Position(Double.POSITIVE_INFINITY,2);
	}
	
	@Test
	public void testIsValidComponent_ValidCase() {
		assertTrue(Position.isValidComponent(0));
	}
	
	@Test
	public void testIsValidComponent_InfiniteCase() {
		assertFalse(Position.isValidComponent(Double.NEGATIVE_INFINITY));
	}
	
	@Test
	public void testIsValidComponent_NaNCase() {
		assertFalse(Position.isValidComponent(Double.NaN));
	}
	
	@Test
	public void testSetxComponent_LegalCase() {
		position_00.setxComponent(3);
		assertEquals(position_00.getxComponent(), 3, 0.01);
	}
	
	@Test(expected=IllegalComponentException.class)
	public void testSetxComponent_IllegalCase() {
		position_00.setxComponent(Double.POSITIVE_INFINITY);
	}
	
	@Test
	public void testSetyComponent_LegalCase() {
		position_00.setyComponent(5);
		assertEquals(position_00.getyComponent(), 5, 0.01);
	}
	
	@Test(expected=IllegalComponentException.class)
	public void testSetyComponent_IllegalCase() {
		position_00.setyComponent(Double.NEGATIVE_INFINITY);
	}
	
	@Test
	public void clone_RegularCase() {
		Position newPosition = position_00.clone();
		assertNotEquals(position_00, newPosition);
		assertEquals(position_00.getxComponent(), newPosition.getxComponent(), 0.01);
		assertEquals(position_00.getyComponent(), newPosition.getyComponent(), 0.01);
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
	public void getAsArrayTest(){
		assertEquals(position_34.getAsArray()[0], 3, 0.01);
		assertEquals(position_34.getAsArray()[1], 4, 0.01);
	}
}