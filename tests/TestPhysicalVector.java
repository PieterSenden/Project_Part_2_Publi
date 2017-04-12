package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestPhysicalVector {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	private static Position position_34;
	
	@Before
	public void setUp() throws Exception {
		position_34 = new Position(3,4);
	}
	
	@Test
	public void Constructor_LegalCase() {
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
	public void getAsArrayTest(){
		assertEquals(position_34.getAsArray()[0], 3, 0.01);
		assertEquals(position_34.getAsArray()[1], 4, 0.01);
	}
}
