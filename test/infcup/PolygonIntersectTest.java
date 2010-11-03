package infcup;

import static infcup.PolygonIntersect.intersectionArea;
import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class PolygonIntersectTest {
	@Test
	public void testPolygonIntersect() {
		double a2[][] = { { 1, 7 }, { 4, 7 }, { 4, 6 }, { 2, 6 }, { 2, 3 }, { 4, 3 }, { 4, 2 }, { 1, 2 } };
		double b2[][] = { { 3, 1 }, { 5, 1 }, { 5, 4 }, { 3, 4 }, { 3, 5 }, { 6, 5 }, { 6, 0 }, { 3, 0 } };
		trial(a2, b2, 0, 9);

		double a3[][] = { { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
		double b3[][] = { { 0, 0 }, { 0, 4 }, { 4, 4 }, { 4, 0 } };
		trial(a3, b3, 0, 0.5);

		double a4[][] = { { 0, 0 }, { 3, 0 }, { 3, 2 }, { 1, 2 }, { 1, 1 }, { 2, 1 }, { 2, 3 }, { 0, 3 } };
		double b4[][] = { { 0, 0 }, { 0, 4 }, { 4, 4 }, { 4, 0 } };
		trial(a4, b4, -9, 11);

		double a5[][] = { { 0, 0 }, { 1, 0 }, { 0, 1 } };
		double b5[][] = { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 0 } };
		trial(a5, b5, -0.5, .5);

		double a6[][] = { { 1, 3 }, { 2, 3 }, { 2, 0 }, { 1, 0 } };
		double b6[][] = { { 0, 1 }, { 3, 1 }, { 3, 2 }, { 0, 2 } };
		trial(a6, b6, -1, 3);

		double a7[][] = { { 0, 0 }, { 0, 2 }, { 2, 2 }, { 2, 0 } };
		double b7[][] = { { 1, 1 }, { 3, 1 }, { 3, 3 }, { 1, 3 } };
		trial(a7, b7, -1, 4);

		double a8[][] = { { 0, 0 }, { 0, 4 }, { 4, 4 }, { 4, 0 } };
		double b8[][] = { { 1, 1 }, { 1, 2 }, { 2, 2 }, { 2, 1 } };
		trial(a8, b8, 1, 16);
	}

	@Test
	public void testPolygonIntersectRedundantStartEndPoint() {
		// The redundant vertices above are to provoke errors as good test cases
		// should. It is not necessary to duplicate the first vertex at the end.
		double a1[][] = { { 2, 3 }, { 2, 3 }, { 2, 3 }, { 2, 4 }, { 3, 3 }, { 2, 3 }, { 2, 3 } };
		double b1[][] = { { 1, 1 }, { 1, 4 }, { 4, 4 }, { 4, 1 }, { 1, 1 } };
		trial(a1, b1, 0.5, 0.5);
	}

	private static Point[] toPointsArray(double[][] a) {
		Point[] A = new Point[a.length];
		for (int i = 0; i < a.length; i++)
			A[i] = new Point(a[i][0], a[i][1]);
		return A;
	}

	private static void trial(double[][] a, double[][] b, double e1, double e2) {
		Point[] A = toPointsArray(a);
		Point[] B = toPointsArray(b);
		assertEquals(e1, intersectionArea(A, B), 1e-6);
		assertEquals(e2, intersectionArea(A, A), 1e-6);
	}
}
