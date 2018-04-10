package ImageEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public class Shapes
{
	private Shape sh;
	private Color c;
	private float st;

	public Shapes(Shape shape, Color color, float stroke)
	{
		sh = shape;
		c = color;
		st = stroke;
	}

	public void drawShape(Graphics2D g2)
	{
		g2.setStroke(new BasicStroke(st, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.setColor(c);
		g2.draw(sh);
	}

	public Shape getShape()
	{
		return sh;
	}
}
