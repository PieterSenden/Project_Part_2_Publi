package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestEntity {
	
	
	private static Entity staticEntity1, staticEntity2, entityOverlap1, entityOverlap2, movingEntityInWorld1, movingEntityInWorld2, movingEntityInWorld3,
					staticEntityInWorld1;
	private static Bullet bullet1;
	private static Position position1; 
	private static World world1;
	
	private static final double EPSILON = 0.0001;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		staticEntity1 = new Ship(0, 0, 10);
		staticEntity2 = new Ship(30, 40, 10);
		entityOverlap1 = new Ship(0, 0, 30);
		entityOverlap2 = new Ship(5, 5, 30);
		bullet1 = new Bullet(0, 0, 0, 0, 4);
		movingEntityInWorld1 = new Ship(100, 100, 3, 4, 10, 0);
		movingEntityInWorld2 = new Ship(200, 200, 0, -50, 10, 0);	
		movingEntityInWorld3 = new Ship(200, 250, 0, -100, 10, 0);
		staticEntityInWorld1 = new Ship(800, 800, 0, 0, 10, 0);
		world1 = new World(1000, 1000);
	}
	
	@Test
	public void canHaveAsPosition_NullWorldCase() {
		position1 = new Position(10e34, -12345);
		assertTrue(staticEntity1.canHaveAsPosition(position1));
	}
	
	@Test
	public void terminate() {
		world1.addEntity(movingEntityInWorld1);
		movingEntityInWorld1.terminate();
		assertTrue(movingEntityInWorld1.isTerminated());
		assertNull(movingEntityInWorld1.getWorld());
		assertFalse(world1.hasAsEntity(movingEntityInWorld1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void move_IllegalArgumentCase() {
		staticEntity1.move(-1);
	}
	
	@Test(expected=TerminatedException.class)
	public void move_TerminatedCase() {
		staticEntity1.terminate();
		staticEntity1.move(1);
	}
	
	@Test
	public void move_LegalAndEffectiveWorldCase() {
		world1.addEntity(movingEntityInWorld1);
		movingEntityInWorld1.move(1);
		Position newPosition = new Position(103, 104);
		assertEquals(movingEntityInWorld1.getPosition(), newPosition);
		assertEquals(world1.getEntityAt(newPosition), movingEntityInWorld1);
	}
	
	@Test
	public void getDistanceBetweenCentres_LegalCase() {
		assertEquals(Entity.getDistanceBetweenCentres(staticEntity1, staticEntity2), 50, EPSILON);
	}
	
	@Test(expected = NullPointerException.class)
	public void getDistanceBetweenCentres_NonEffectiveCase() {
		Entity.getDistanceBetweenCentres(staticEntity1, null);
	}
	
	@Test
	public void getDistanceBetween_NonIdenticalCase() {
		assertEquals(Entity.getDistanceBetween(staticEntity1, staticEntity2), 30, EPSILON);
	}
	
	@Test
	public void getDistanceBetween_IdenticalCase() {
		assertEquals(Entity.getDistanceBetween(staticEntity1, staticEntity1), 0, EPSILON);
	}
	
	@Test(expected=NullPointerException.class)
	public void getDistanceBetween_NonEffectiveCase() {
		Entity.getDistanceBetween(staticEntity1, null);
	}
	
	@Test
	public void overlap_NonIdenticalTrueCase() {
		assertTrue(Entity.overlap(entityOverlap1, entityOverlap2));
	}
	
	@Test
	public void overlap_NonIdenticalFalseCase() {
		entityOverlap1 = new Ship(100, 100, 10);
		assertFalse(Entity.overlap(entityOverlap1, entityOverlap2));
	}
	
	@Test
	public void overlap_IdenticalCase() {
		assertTrue(Entity.overlap(entityOverlap1, entityOverlap1));
	}
	
	@Test(expected=NullPointerException.class)
	public void overlap_NonEffectiveCase() {
		Entity.overlap(entityOverlap1, null);
	}
	
	
	@Test(expected = TerminatedException.class)
	public void overlap_TerminatedCase() {
		entityOverlap1.terminate();
		Entity.overlap(entityOverlap1, entityOverlap2);
	}
	
	@Test
	public void canSurround_TrueCase() {
		assertTrue(staticEntity1.canSurround(bullet1));
	}
	
	@Test
	public void canSurround_FalseCase() {
		bullet1 = new Bullet(0, 0, 0, 0, 100);
		assertFalse(staticEntity1.canSurround(bullet1));
	}
	
	@Test(expected = NullPointerException.class)
	public void canSurround_NonEffectiveCase(){
		staticEntity1.canSurround(null);
	}
	
	@Test(expected = TerminatedException.class)
	public void canSurround_TerminatedCase(){
		bullet1.terminate();
		staticEntity1.canSurround(bullet1);
	}
	
	@Test
	public void apparentlyCollide_TrueCase() {
		movingEntityInWorld1 = new Ship(100, 100, 3, 4, 10, 0);
		movingEntityInWorld2 = new Ship(130, 140, -1, -1, 40, 0);
		world1.addEntity(movingEntityInWorld1);
		world1.addEntity(movingEntityInWorld2);
		assertTrue(Entity.apparentlyCollide(movingEntityInWorld1, movingEntityInWorld2));
	}
	
	@Test
	public void apparentlyCollide_FalseCase() {
		movingEntityInWorld1 = new Ship(100, 100, -3, -4, 10, 0);
		movingEntityInWorld2 = new Ship(130, 140, 2, 3, 40, 0);
		world1.addEntity(movingEntityInWorld1);
		world1.addEntity(movingEntityInWorld2);
		assertFalse(Entity.apparentlyCollide(movingEntityInWorld1, movingEntityInWorld2));
	}
	
	@Test(expected = TerminatedException.class)
	public void apparentlyCollide_TerminatedCase() {
		movingEntityInWorld1 = new Ship(100, 100, -3, -4, 10, 0);
		movingEntityInWorld2 = new Ship(130, 140, 2, 3, 40, 0);
		world1.addEntity(movingEntityInWorld1);
		world1.addEntity(movingEntityInWorld2);
		movingEntityInWorld1.terminate();
		Entity.apparentlyCollide(movingEntityInWorld1, movingEntityInWorld2);
	}
	
	@Test
	public void collideAfterMove_TrueCase() {
		world1.addEntity(movingEntityInWorld2);
		world1.addEntity(movingEntityInWorld3);
		assertTrue(Entity.collideAfterMove(movingEntityInWorld2, movingEntityInWorld3, 1.4));
	}
	
	@Test
	public void collideAfterMove_FalseCase() {
		world1.addEntity(movingEntityInWorld1);
		world1.addEntity(movingEntityInWorld2);
		assertFalse(Entity.collideAfterMove(movingEntityInWorld1, movingEntityInWorld2, 1));
	}
	
	@Test(expected = TerminatedException.class)
	public void collideAfterMove_TerminatedCase() {
		world1.addEntity(movingEntityInWorld1);
		movingEntityInWorld1.terminate();
		Entity.collideAfterMove(movingEntityInWorld1, movingEntityInWorld2, 10);
	}
	
	@Test
	public void collidesWithBoundaryAfterMove_TrueCase() {
		world1.addEntity(movingEntityInWorld2);
		assertTrue(movingEntityInWorld2.collidesWithBoundaryAfterMove(4));
	}
	
	@Test
	public void collidesWithBoundaryAfterMove_FalseCase() {
		world1.addEntity(movingEntityInWorld2);
		assertFalse(movingEntityInWorld2.collidesWithBoundaryAfterMove(1));		
	}
	
	@Test(expected = TerminatedException.class)
	public void collidesWithBoundaryAfterMove_TerminatedCase() {
		world1.addEntity(movingEntityInWorld1);
		movingEntityInWorld1.terminate();
		movingEntityInWorld1.collidesWithBoundaryAfterMove(1);
	}
	
	@Test
	public void getTimeToCollision_CollisionCase() {
		movingEntityInWorld2 = new Ship(90, 95, 2, 1, 20, 0);
		movingEntityInWorld3 = new Ship(150, 180, -4, -8, 30, 0);
		world1.addEntity(movingEntityInWorld2);
		world1.addEntity(movingEntityInWorld3);
		double duration = Entity.getTimeToCollision(movingEntityInWorld2, movingEntityInWorld3);
		assertEquals(duration, 5, EPSILON * 1e-5);
		assertTrue(Entity.collideAfterMove(movingEntityInWorld2, movingEntityInWorld3, 5));
		for (int i = 0; i < 50000; i++)
			assertFalse(Entity.collideAfterMove(movingEntityInWorld2, movingEntityInWorld3, i * 0.0001));
	}
	
	@Test(expected=NullPointerException.class)
	public void getTimeToCollision_NonEffectiveCase() {
		world1.addEntity(movingEntityInWorld1);
		Entity.getTimeToCollision(movingEntityInWorld1, null);
	}
	
	@Test
	public void getTimeToCollision_InfiniteCase() {
		world1.addEntity(movingEntityInWorld1);
		world1.addEntity(movingEntityInWorld3);
		assertEquals(Entity.getTimeToCollision(movingEntityInWorld1, movingEntityInWorld3), Double.POSITIVE_INFINITY, EPSILON);
	}
	
	@Test(expected=IllegalMethodCallException.class)
	public void getTimeToCollision_IllegalMethodCallExceptionCase() {
		world1.addEntity(movingEntityInWorld1);
		Entity.getTimeToCollision(movingEntityInWorld1, movingEntityInWorld2);
	}
	
	@Test
	public void getCollisionPosition_CollisionCase() {
		movingEntityInWorld2 = new Ship(90, 95, 2, 1, 20, 0);
		movingEntityInWorld3 = new Ship(150, 180, -4, -8, 30, 0);
		world1.addEntity(movingEntityInWorld2);
		world1.addEntity(movingEntityInWorld3);
		Position collisionPosition = Entity.getCollisionPosition(movingEntityInWorld2, movingEntityInWorld3);
		double timeToCollision = Entity.getTimeToCollision(movingEntityInWorld2, movingEntityInWorld3);
		double radius2 = movingEntityInWorld2.getRadius();
		double radius3 = movingEntityInWorld3.getRadius();
		assertEquals(Position.getDistanceBetween(collisionPosition, movingEntityInWorld2.getPosition().move(movingEntityInWorld2.getVelocity(), timeToCollision)), radius2, EPSILON);
		assertEquals(Position.getDistanceBetween(collisionPosition, movingEntityInWorld3.getPosition().move(movingEntityInWorld3.getVelocity(), timeToCollision)), radius3, EPSILON);
	}
	
	@Test
	public void getCollisionPosition_NonCollisionCase() {
		movingEntityInWorld2 = new Ship(90, 95, -2, 1, 20, 0);
		movingEntityInWorld3 = new Ship(150, 180, 4, 8, 30, 0);
		world1.addEntity(movingEntityInWorld2);
		world1.addEntity(movingEntityInWorld3);
		Position collisionPosition = Entity.getCollisionPosition(movingEntityInWorld2, movingEntityInWorld3);
		assertNull(collisionPosition);
	}
	
	@Test(expected=NullPointerException.class)
	public void getCollisionPosition_NonEffectiveCase() {
		Entity.getCollisionPosition(movingEntityInWorld2, null);
	}
	
	@Test(expected=IllegalMethodCallException.class)
	public void getCollisionPosition_IllegalMethodCallExceptionCase() {
		world1.addEntity(movingEntityInWorld1);
		Entity.getCollisionPosition(movingEntityInWorld1, movingEntityInWorld2);
	}
	
	@Test
	public void apparentlyCollidesWithHorizontalBoundary_TrueCase() {
		movingEntityInWorld1 = new Ship(980, 990, 5, 10, 10, 0);
		world1.addEntity(movingEntityInWorld1);
		assertTrue(movingEntityInWorld1.apparentlyCollidesWithHorizontalBoundary());
	}
	
	@Test
	public void apparentlyCollidesWithHorizontalBoundary_FalseCase() {
		movingEntityInWorld1 = new Ship(980, 990, 5, -10, 10, 0);
		world1.addEntity(movingEntityInWorld1);
		assertFalse(movingEntityInWorld1.apparentlyCollidesWithHorizontalBoundary());
	}
	
	@Test
	public void apparentlyCollidesWithHorizontalBoundary_NonEffectiveWorldCase() {
		assertFalse(movingEntityInWorld1.apparentlyCollidesWithHorizontalBoundary());
	}
	
	
	@Test(expected = TerminatedException.class)
	public void apparentlyCollidesWithHorizontalBoundary_TerminatedCase() {
		movingEntityInWorld1.terminate();
		movingEntityInWorld1.apparentlyCollidesWithHorizontalBoundary();
	}
	
	@Test
	public void apparentlyCollidesWithVerticalBoundary_TrueCase() {
		movingEntityInWorld1 = new Ship(990, 980, 10, 5, 10, 0);
		world1.addEntity(movingEntityInWorld1);
		assertTrue(movingEntityInWorld1.apparentlyCollidesWithVerticalBoundary());
	}
	
	@Test
	public void apparentlyCollidesWithVerticalBoundary_FalseCase() {
		movingEntityInWorld1 = new Ship(990, 980, -10, 5, 10, 0);
		world1.addEntity(movingEntityInWorld1);
		assertFalse(movingEntityInWorld1.apparentlyCollidesWithVerticalBoundary());
	}
	
	@Test
	public void apparentlyCollidesWithVerticalBoundary_NonEffectiveWorldCase() {
		assertFalse(movingEntityInWorld1.apparentlyCollidesWithVerticalBoundary());
	}
	
	
	@Test(expected = TerminatedException.class)
	public void apparentlyCollidesWithVerticalBoundary_TerminatedCase() {
		movingEntityInWorld1.terminate();
		movingEntityInWorld1.apparentlyCollidesWithVerticalBoundary();
	}
	
	@Test
	public void getTimeToCollisionWithBoundary_CollisionCase() {
		world1.addEntity(movingEntityInWorld1);
		double duration = movingEntityInWorld1.getTimeToCollisionWithBoundary();
		assertEquals(duration, 445.0 / 2, EPSILON);
		assertTrue(movingEntityInWorld1.collidesWithBoundaryAfterMove(duration));
	}
	
	@Test
	public void getTimeToCollisionWithBoundary_InfiniteCase() {
		world1.addEntity(staticEntityInWorld1);
		assertEquals(staticEntityInWorld1.getTimeToCollisionWithBoundary(), Double.POSITIVE_INFINITY, EPSILON);
	}
	
	@Test
	public void getTimeToCollisionWithBoundary_NonEffectiveWorldCase() {
		assertEquals(staticEntity1.getTimeToCollisionWithBoundary(), Double.POSITIVE_INFINITY, EPSILON);
	}
	
	@Test
	public void getCollisionWithBoundaryPosition_CollisionCase() {
		world1.addEntity(movingEntityInWorld1);
		Position collisionPosition = movingEntityInWorld1.getCollisionWithBoundaryPosition();
		assertEquals(collisionPosition, new Position(767.5, 990));
	}
	
	@Test
	public void getCollisionWithBoundaryPosition_NonCollisionCase() {
		world1.addEntity(staticEntityInWorld1);
		assertNull(staticEntityInWorld1.getCollisionWithBoundaryPosition());
	}
	
	@Test
	public void getCollisionWithBoundaryPosition_NonEffectiveWorldCase() {
		assertNull(staticEntity1.getCollisionWithBoundaryPosition());
	}
}
