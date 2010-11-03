package infcup.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

public class CityMap extends Component {
	private static final int ATM_RADIUS = 60;
	private static final Color ATM_BORDER_COLOR = Color.BLACK;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Dimension dim = getSize();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, dim.width, dim.height);

		Polygon p = new Polygon();
		p.addPoint(10, 10);
		p.addPoint(130, 20);
		p.addPoint(120, 70);
		drawDistrict(p, 0.3f, g2);

		p = new Polygon();
		p.addPoint(200, 15);
		p.addPoint(130, 20);
		p.addPoint(120, 70);
		drawDistrict(p, 1f, g2);

		p = new Polygon();
		p.addPoint(65, 40);
		p.addPoint(175, 100);
		p.addPoint(120, 130);
		drawDistrict(p, .5f, g2);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		drawATM(new Point(70, 70), 0.3f, g2);
	}

	private void drawATM(Point point, float quality, Graphics2D g2) {
		g2.setColor(getAtmBackgroundColor(quality));
		g2.fillArc(point.x, point.y, ATM_RADIUS, ATM_RADIUS, 0, 360);

		g2.setColor(ATM_BORDER_COLOR);
		g2.drawArc(point.x, point.y, ATM_RADIUS, ATM_RADIUS, 0, 360);
	}

	private void drawDistrict(Polygon p, float quality, Graphics2D g2) {
		g2.setColor(new Color(1 - quality, 1 - quality, 1 - quality));
		g2.fillPolygon(p);
	}

	private Color getAtmBackgroundColor(float quality) {
		assert (quality >= 0.0f && quality <= 1.0f);
		// first third of HSB is red (0) to green (1/3)
		Color color = new Color(Color.HSBtoRGB(quality / 3, 1.0f, 1.0f) ^ (0x77<< 24), true);
		System.out.println(Color.HSBtoRGB(quality / 3, 1.0f, 1.0f));
		System.out.println(color.getAlpha());
		return color;
	}
}
