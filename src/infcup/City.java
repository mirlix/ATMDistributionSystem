package infcup;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

//Class to save the city
public class City {

	private List<District> districts;
	
	public  City() {
		districts = new LinkedList<District>();
	}
	
	public void addDistrict(Point[] edges) {
		districts.add(new District(edges));
	}
	
	public void drawDistricts(Canvas canvas) {
		   GC gc = new GC(canvas);
		   
		   for(District d: districts) {
			drawDistrict(d.getPoints(),gc);   
		   }
		   gc.dispose();
	}
	
	void drawDistrict(Point[] points,GC gc) {
		
	    int[] poly = new int[points.length * 2];
	    for(int i=0;i<points.length;i++) {
	    	poly[i*2] = (int)points[i].x;
	    	poly[i*2+1] = (int)points[i].y;
	    }
	    
	    gc.drawPolygon(poly);
	   
	}
	
	
}
