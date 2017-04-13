package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestEntity {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void copy_EffectiveCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void copy_TerminatedCase() {
		
	}
	
	@Test
	public void canHaveAsPosition_NullWorldCase() {
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void move_IllegalArgumentCase() {
		
	}
	
	@Test(expected=TerminatedException.class)
	public void move_TerminatedCase() {
		
	}
	
	@Test
	public void move_LegalCase() {
		
	}
	
	@Test
	public void getDistanceBetweenCentres_LegalCase() {
		
	}
	
	@Test(expected = NullPointerException.class)
	public void getDistanceBetweenCentres_NonEffectiveCase() {
		
	}
	
	@Test
	public void getDistanceBetween_NonIdenticalCase() {
		assertEquals(Ship.getDistanceBetween(ship_Overlap1, ship_Overlap2), 30, EPSILON);
	}
	
	@Test
	public void getDistanceBetween_IdenticalCase() {
		assertEquals(Ship.getDistanceBetween(ship_Overlap1, ship_Overlap1), 0, EPSILON);
	}
	
	@Test(expected=NullPointerException.class)
	public void getDistanceBetween_NonEffectiveCase() {
		Ship.getDistanceBetween(ship_Overlap1, null);
	}
	
	
	@Test
	public void overlap_NonIdenticalTrueCase() {
		assertFalse(Ship.overlap(ship_Overlap1, ship_Overlap2));
	}
	
	@Test
	public void overlap_NonIdenticalFalseCase() {
		assertFalse(Ship.overlap(ship_Overlap1, ship_Overlap2));
	}
	
	@Test
	public void overlap_IdenticalCase() {
		assertTrue(Ship.overlap(ship_Overlap1, ship_Overlap1));
	}
	
	@Test(expected=NullPointerException.class)
	public void overlap_NonEffectiveCase() {
		Ship.overlap(ship_Overlap1, null);
	}
	
	
	@Test(expected = TerminatedException.class)
	public void overlap_TerminatedCase() {
		
	}
	
	@Test
	public void canSurround_TrueCase() {
		
	}
	
	@Test
	public void canSurround_FalseCase() {
		
	}
	
	@Test(expected = NullPointerException.class)
	public void canSurround_NonEffectiveCase(){
		
	}
	
	@Test(expected = TerminatedException.class)
	public void canSurround_TerminatedCase(){
		
	}
	
	@Test
	public void apparentlyCollide_TrueCase() {
		
	}
	
	@Test
	public void apparentlyCollide_FalseCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void apparentlyCollide_TerminatedCase() {
		
	}
	
	@Test
	public void collideAfterMove_TrueCase() {
		
	}
	
	@Test
	public void collideAfterMove_FalseCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void collideAfterMove_TerminatedCase() {
		
	}
	
	@Test
	public void collidesWithBoundaryAfterMove_TrueCase() {
		
	}
	
	@Test
	public void collidesWithBoundaryAfterMove_FalseCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void collidesWithBoundaryAfterMove_TerminatedCase() {
		
	}
	
	@Test
	public void getTimeToCollision_CollisionCase() {
		double duration = Ship.getTimeToCollision(ship_Collision1, ship_Collision2);
		assertEquals(duration, 8.619288124, EPSILON);
		Ship ship1Clone = ship_Collision1.copy();
		Ship ship2Clone = ship_Collision2.copy();
		ship_Collision1.move(8.619);
		ship_Collision2.move(8.619);
		assertFalse(Ship.overlap(ship_Collision1, ship_Collision2));
		ship1Clone.move(duration);
		ship2Clone.move(duration);
		assertTrue(Ship.overlap(ship1Clone, ship2Clone));
	}
	
	@Test(expected=NullPointerException.class)
	public void getTimeToCollision_NonEffectiveCase() {
		Ship.getTimeToCollision(ship_Collision1, null);
	}
	
	@Test
	public void getTimeToCollision_InfiniteCase() {
		assertEquals(Ship.getTimeToCollision(ship_Collision1, ship_Collision3), Double.POSITIVE_INFINITY, EPSILON);
	}
	
	@Test(expected=OverlapException.class)
	public void getTimeToCollision_OverlapCase() {
		Ship.getTimeToCollision(ship_Collision1, ship_Collision1);
	}
	
	@Test
	public void getCollisionPosition_CollisionCase() {
		double duration = Ship.getTimeToCollision(ship_Collision1, ship_Collision2);
//		assertEquals(Ship.getCollisionPosition(ship_Collision1, ship_Collision2), )
		Position collisionPosition = Ship.getCollisionPosition(ship_Collision1, ship_Collision2);
		ship_Collision1.move(duration);
		ship_Collision2.move(duration);
		assertEquals(Position.getDistanceBetween(collisionPosition, ship_Collision1.getPosition()), ship_Collision1.getRadius(), EPSILON);
		assertEquals(Position.getDistanceBetween(collisionPosition, ship_Collision2.getPosition()), ship_Collision2.getRadius(), EPSILON);
	}
	
	@Test
	public void getCollisionPosition_NonCollisionCase() {
		assertNull(Ship.getCollisionPosition(ship_Collision1, ship_Collision3));
	}
	
	@Test(expected=NullPointerException.class)
	public void getCollisionPosition_NonEffectiveCase() {
		Ship.getCollisionPosition(ship_Collision1, null);
	}
	
	@Test(expected=OverlapException.class)
	public void getCollisionPosition_OverlapCase() {
		Ship.getCollisionPosition(ship_Collision1, ship_Collision1);
	}
	
	@Test
	public void apparentlyCollidesWithHorizontalBoundary_TrueCase() {
		
	}
	
	@Test
	public void apparentlyCollidesWithHorizontalBoundary_FalseCase() {
		
	}
	
	@Test
	public void apparentlyCollidesWithHorizontalBoundary_NonEffectiveWorldCase() {
		
	}
	
	@Test(expected = NullPointerException.class)
	public void apparentlyCollidesWithHorizontalBoundary_NonEffectiveCase() {
		
	}
		
	@Test(expected = TerminatedException.class)
	public void apparentlyCollidesWithHorizontalBoundary_TerminatedCase() {
		
	}
	
	@Test
	public void apparentlyCollidesWithVerticalBoundary_TrueCase() {
		
	}
	
	@Test
	public void apparentlyCollidesWithVerticalBoundary_FalseCase() {
		
	}
	
	@Test
	public void apparentlyCollidesWithVerticalBoundary_NonEffectiveWorldCase() {
		
	}
	
	@Test(expected = NullPointerException.class)
	public void apparentlyCollidesWithVerticalBoundary_NonEffectiveCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void apparentlyCollidesWithVerticalBoundary_TerminatedCase() {
		
	}
	
	@Test
	public void getTimeToCollisionWithBoundary_CollisionCase() {
		double duration = Ship.getTimeToCollision(ship_Collision1, ship_Collision2);
		assertEquals(duration, 8.619288124, EPSILON);
		Ship ship1Clone = ship_Collision1.copy();
		Ship ship2Clone = ship_Collision2.copy();
		ship_Collision1.move(8.619);
		ship_Collision2.move(8.619);
		assertFalse(Ship.overlap(ship_Collision1, ship_Collision2));
		ship1Clone.move(duration);
		ship2Clone.move(duration);
		assertTrue(Ship.overlap(ship1Clone, ship2Clone));
	}
	
	@Test(expected=NullPointerException.class)
	public void getTimeToCollisionWithBoundary_NonEffectiveCase() {
		Ship.getTimeToCollision(ship_Collision1, null);
	}
	
	@Test
	public void getTimeToCollisionWithBoundary_InfiniteCase() {
		assertEquals(Ship.getTimeToCollision(ship_Collision1, ship_Collision3), Double.POSITIVE_INFINITY, EPSILON);
	}
	
	@Test(expected=OverlapException.class)
	public void getTimeToCollisionWithBoundary_NonEffecitveWorldCase() {
		Ship.getTimeToCollision(ship_Collision1, ship_Collision1);
	}
	
	@Test
	public void getCollisionWithBoundaryPosition_CollisionCase() {
		double duration = Ship.getTimeToCollision(ship_Collision1, ship_Collision2);
//		assertEquals(Ship.getCollisionPosition(ship_Collision1, ship_Collision2), )
		Position collisionPosition = Ship.getCollisionPosition(ship_Collision1, ship_Collision2);
		ship_Collision1.move(duration);
		ship_Collision2.move(duration);
		assertEquals(Position.getDistanceBetween(collisionPosition, ship_Collision1.getPosition()), ship_Collision1.getRadius(), EPSILON);
		assertEquals(Position.getDistanceBetween(collisionPosition, ship_Collision2.getPosition()), ship_Collision2.getRadius(), EPSILON);
	}
	
	@Test
	public void getCollisionWithBoundaryPosition_NonCollisionCase() {
		assertNull(Ship.getCollisionPosition(ship_Collision1, ship_Collision3));
	}
	
	@Test(expected=NullPointerException.class)
	public void getCollisionWithBoundaryPosition_NonEffectiveCase() {
		Ship.getCollisionPosition(ship_Collision1, null);
	}
	
	@Test(expected=OverlapException.class)
	public void getCollisionWithBoundaryPosition_NonEffecitveWorldCase() {
		Ship.getCollisionPosition(ship_Collision1, ship_Collision1);
	}
		
	@Test
	public void setWorld_LegalCase() {
		
	}
	
	@Test(expected = TerminatedException.class)
	public void setWorld_TerminatedEntityCase() {
		
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void setWorld_TerminatedWorldCase() {
		
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void setWorld_NonEffectiveWorldButStillContainedInWorldCase() {
		
	}
	
	
	
}
