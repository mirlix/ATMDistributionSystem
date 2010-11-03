package infcup;

/* from: http://www.cap-lore.com/MathPhys/IP/PolygonIntersect.java
 * Explanation: http://www.cap-lore.com/MathPhys/IP/
 */

/**
 * Area of Intersection of Polygons
 * 
 * Algorithm based on http://cap-lore.com/MathPhys/IP/
 * 
 * Adapted 9-May-2006 by Lagado
 */
public class PolygonIntersect {
	/**
	 * return the area of intersection of two polygons
	 * 
	 * Note: the area result has little more accuracy than a float This is true
	 * even if the polygon is specified with doubles.
	 */
	public static double intersectionArea(Point[] a, Point[] b) {
		PolygonIntersect polygonIntersect = new PolygonIntersect();
		return polygonIntersect.inter(a, b);
	}

	// --------------------------------------------------------------------------

	private static class DoublePoint {
		double x;
		double y;

		DoublePoint(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	private static class Box {
		DoublePoint min = new DoublePoint(Double.MAX_VALUE, Double.MAX_VALUE);
		DoublePoint max = new DoublePoint(-Double.MAX_VALUE, -Double.MAX_VALUE);
	}

	private static class Range {
		int min;
		int max;

		Range(int min, int max) {
			this.min = min;
			this.max = max;
		}

		boolean overlaps(Range that) {
			return this.min < that.max && that.min < this.max;
		}
	}

	private static class IntPoint {
		int x;
		int y;
	}

	private static class Vertex {
		IntPoint ip;
		Range rx;
		Range ry;
		int in;
	}

	private static final double gamut = 500000000.;
	private static final double mid = gamut / 2.;

	// --------------------------------------------------------------------------

	private static void range(Point[] points, Box bbox) {
		for (Point point : points) {
			bbox.min.x = Math.min(bbox.min.x, point.x);
			bbox.min.y = Math.min(bbox.min.y, point.y);
			bbox.max.x = Math.max(bbox.max.x, point.x);
			bbox.max.y = Math.max(bbox.max.y, point.y);
		}
	}

	private static long area(IntPoint a, IntPoint p, IntPoint q) {
		return (long) p.x * q.y - (long) p.y * q.x + (long) a.x * (p.y - q.y) + (long) a.y * (q.x - p.x);
	}

	// --------------------------------------------------------------------------

	private long ssss;
	private double sclx;
	private double scly;

	private void cntrib(int f_x, int f_y, int t_x, int t_y, int w) {
		ssss += (long) w * (t_x - f_x) * (t_y + f_y) / 2;
	}

	private void fit(Point[] x, Vertex[] ix, int fudge, Box bbox) {
		for (int i = 0; i < x.length; i++) {
			ix[i] = new Vertex();
			ix[i].ip = new IntPoint();
			ix[i].ip.x = ((int) ((x[i].x - bbox.min.x) * sclx - mid) & ~7) | fudge | (i & 1);
			ix[i].ip.y = ((int) ((x[i].y - bbox.min.y) * scly - mid) & ~7) | fudge;
		}

		ix[0].ip.y += x.length & 1;
		ix[x.length] = ix[0];

		for (int i = 0; i < x.length; i++) {
			ix[i].rx = ix[i].ip.x < ix[i + 1].ip.x ? new Range(ix[i].ip.x, ix[i + 1].ip.x) : new Range(ix[i + 1].ip.x, ix[i].ip.x);
			ix[i].ry = ix[i].ip.y < ix[i + 1].ip.y ? new Range(ix[i].ip.y, ix[i + 1].ip.y) : new Range(ix[i + 1].ip.y, ix[i].ip.y);
			ix[i].in = 0;
		}
	}

	private void cross(Vertex a, Vertex b, Vertex c, Vertex d, double a1, double a2, double a3, double a4) {
		double r1 = a1 / (a1 + a2);
		double r2 = a3 / (a3 + a4);

		cntrib((int) (a.ip.x + r1 * (b.ip.x - a.ip.x)), (int) (a.ip.y + r1 * (b.ip.y - a.ip.y)), b.ip.x, b.ip.y, 1);
		cntrib(d.ip.x, d.ip.y, (int) (c.ip.x + r2 * (d.ip.x - c.ip.x)), (int) (c.ip.y + r2 * (d.ip.y - c.ip.y)), 1);
		a.in++;
		c.in--;
	}

	private void inness(Vertex[] P, Vertex[] Q) {
		int s = 0;
		IntPoint p = P[0].ip;

		for (int i = 0; i < Q.length - 1; i++) {
			if (Q[i].rx.min < p.x && p.x < Q[i].rx.max) {
				boolean sgn = 0 < area(p, Q[i].ip, Q[i + 1].ip);
				s += (sgn != Q[i].ip.x < Q[i + 1].ip.x) ? 0 : (sgn ? -1 : 1);
			}
		}

		for (int i = 0; i < P.length - 1; ++i) {
			if (s != 0) cntrib(P[i].ip.x, P[i].ip.y, P[i + 1].ip.x, P[i + 1].ip.y, s);
			s += P[i].in;
		}
	}

	// -------------------------------------------------------------------------

	private double inter(Point[] a, Point[] b) {
		if (a.length < 3 || b.length < 3) return 0;

		Vertex[] ipa = new Vertex[a.length + 1];
		Vertex[] ipb = new Vertex[b.length + 1];
		Box bbox = new Box();

		range(a, bbox);
		range(b, bbox);

		sclx = gamut / (bbox.max.x - bbox.min.x);
		scly = gamut / (bbox.max.y - bbox.min.y);

		fit(a, ipa, 0, bbox);
		fit(b, ipb, 2, bbox);

		for (int j = 0; j < a.length; ++j) {
			for (int k = 0; k < b.length; ++k) {
				if (ipa[j].rx.overlaps(ipb[k].rx) && ipa[j].ry.overlaps(ipb[k].ry)) {
					long a1 = -area(ipa[j].ip, ipb[k].ip, ipb[k + 1].ip);
					long a2 = area(ipa[j + 1].ip, ipb[k].ip, ipb[k + 1].ip);
					boolean o = a1 < 0;
					if (o == a2 < 0) {
						long a3 = area(ipb[k].ip, ipa[j].ip, ipa[j + 1].ip);
						long a4 = -area(ipb[k + 1].ip, ipa[j].ip, ipa[j + 1].ip);
						if (a3 < 0 == a4 < 0) {
							if (o)
								cross(ipa[j], ipa[j + 1], ipb[k], ipb[k + 1], a1, a2, a3, a4);
							else
								cross(ipb[k], ipb[k + 1], ipa[j], ipa[j + 1], a3, a4, a1, a2);
						}
					}
				}
			}
		}

		inness(ipa, ipb);
		inness(ipb, ipa);

		return ssss / (sclx * scly);
	}
}
