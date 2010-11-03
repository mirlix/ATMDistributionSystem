package infcup;

import java.util.LinkedList;
import java.util.List;

public class District {

	private List<Point> edges;
	
	public District(Point[] edges) {
		this.edges = new LinkedList<Point>();
		for(Point p : edges) {
			this.edges.add(p);
		}
		
	}
	
	public Point[] getPoints() {
		Object[] objs = edges.toArray();
		Point[] points= new Point[objs.length];
		for(int i=0;i<objs.length;i++) {
			points[i] = (Point)objs[i];
		}
		return points;
	}
	
}
