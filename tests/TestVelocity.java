package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Velocity;

public class TestVelocity {
	Velocity myVelocity;
		
	@Before
	public void setUp() throws Exception {
		myVelocity = new Velocity(10, 10);
	}

	@Test
	public void constructor_RegularCase() {
		myVelocity = new Velocity(20, 20);
		assertEquals(myVelocity.getxComponent(), 20, 0.01);
		assertEquals(myVelocity.getyComponent(), 20, 0.01);
	}

	@Test
	public void constructor_NonRegularCase() {
		myVelocity = new Velocity(Double.POSITIVE_INFINITY, 10);
		assertEquals(myVelocity.getxComponent(), 0, 0.01);
		assertEquals(myVelocity.getyComponent(), 10, 0.01);
	}
	
	@Test
	public void isValidComponent_TrueCase() {
		assertTrue(Velocity.isValidComponent(10));
	}

	@Test
	public void isValidComponent_NaNCase() {
		assertFalse(Velocity.isValidComponent(Double.NaN));
	}
	
	@Test
	public void isValidComponent_InfiniteCase() {
		assertFalse(Velocity.isValidComponent(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void setxComponent_RegularCase() {
		myVelocity.setxComponent(5);
		assertEquals(myVelocity.getxComponent(), 5, 0.01);
		assertEquals(myVelocity.getyComponent(), 10, 0.01);
	}
	
	@Test
	public void setxComponent_NonRegularCase() {
		myVelocity.setxComponent(Double.NaN);
		assertEquals(myVelocity.getxComponent(), 10, 0.01);
		assertEquals(myVelocity.getyComponent(), 10, 0.01);
	}

	@Test
	public void setyComponent_RegularCase() {
		myVelocity.setyComponent(5);
		assertEquals(myVelocity.getyComponent(), 5, 0.01);
		assertEquals(myVelocity.getxComponent(), 10, 0.01);
	}

	@Test
	public void setyComponent_NonRegularCase() {
		myVelocity.setyComponent(Double.NaN);
		assertEquals(myVelocity.getxComponent(), 10, 0.01);
		assertEquals(myVelocity.getyComponent(), 10, 0.01);
	}
	
	@Test
	public void setVelocity_RegularCase() {
		myVelocity.setVelocity(5, 5);
		assertEquals(myVelocity.getxComponent(), 5, 0.01);
		assertEquals(myVelocity.getyComponent(), 5, 0.01);
	}

	@Test
	public void clone_RegularCase() {
		Velocity yourVelocity = myVelocity.clone();
		assertNotEquals(yourVelocity, myVelocity);
		assertEquals(myVelocity.getxComponent(), yourVelocity.getxComponent(), 0.01);
		assertEquals(myVelocity.getyComponent(), yourVelocity.getyComponent(), 0.01);
	}
	
	@Test
	public void getAsArrayTest(){
		assertEquals(myVelocity.getAsArray()[0], 10, 0.01);
		assertEquals(myVelocity.getAsArray()[1], 10, 0.01);
	}
}
