package ImageEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class CustomPane extends JPanel
{
	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	private HashMap<Keys, Double> values;

	private ArrayList<Shapes> shapes;
	private Point p1, p2;

	public CustomPane(BufferedImage i)
	{
		setBackground(Color.WHITE);

		if (i != null)
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setPreferredSize(screenSize);
		}

		image = i;

		values = new HashMap<>();
		values.put(Keys.THICKNESS, 3.0);
		values.put(Keys.COLOR, (double) Color.RED.getRGB());
		values.put(Keys.SHAPE, (double) -1);

		shapes = new ArrayList<>();

		p1 = new Point();
		p2 = new Point();

		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "remove");
		getActionMap().put("remove", new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (shapes.size() > 0)
				{
					shapes.remove(shapes.size() - 1);
					repaint();
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				p2.setLocation(e.getX(), e.getY());

				if (shapes.size() > 0)
				{
					Shape tmp = shapes.get(shapes.size() - 1).getShape();

					if (tmp instanceof Line2D.Double)
						((Line2D.Double) tmp).setLine(p1, p2);
					else if (tmp instanceof Rectangle2D.Double)
					{
						int x = (int) Math.min(p1.getX(), p2.getX());
						int y = (int) Math.min(p1.getY(), p2.getY());
						int width = (int) Math.abs(p1.getX() - p2.getX());
						int height = (int) Math.abs(p1.getY() - p2.getY());

						((Rectangle2D.Double) tmp).setRect(x, y, width, height);
					} else if (tmp instanceof Path2D.Double)
						((Path2D.Double) tmp).lineTo(p2.getX(), p2.getY());
					else if (values.get(Keys.SHAPE) == 3)
					{
						int x = (int) Math.min(p1.getX(), p2.getX());
						int y = (int) Math.min(p1.getY(), p2.getY());
						int width = (int) Math.abs(p1.getX() - p2.getX());
						int height = (int) Math.abs(p1.getY() - p2.getY());

						((Ellipse2D.Double) tmp).setFrame(x, y, width, height);
					}
						
					repaint();
				}
			}
		});

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (shapes.size() > 0)
				{
					if (p1.distance(p2) == 0 && !(shapes.get(shapes.size() - 1).getShape() instanceof Path2D.Double))
						shapes.remove(shapes.size() - 1);

					repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				p1.setLocation(e.getX(), e.getY());
				p2.setLocation(e.getX(), e.getY());

				Color color = new Color(values.get(Keys.COLOR).intValue());
				float thickness = values.get(Keys.THICKNESS).floatValue();

				if (values.get(Keys.SHAPE) == 0)
					shapes.add(new Shapes(new Line2D.Double(p1, p1), color, thickness));
				else if (values.get(Keys.SHAPE) == 1)
					shapes.add(new Shapes(new Rectangle2D.Double(p1.getX(), p1.getY(), 0, 0), color, thickness));
				else if (values.get(Keys.SHAPE) == 2)
				{
					Path2D.Double tmp = new Path2D.Double();
					tmp.setWindingRule(Path2D.WIND_EVEN_ODD);
					tmp.moveTo((int) p1.getX(), (int) p1.getY());
					tmp.lineTo((int) p1.getX(), (int) p1.getY());

					shapes.add(new Shapes(tmp, color, thickness));
				} else if (values.get(Keys.SHAPE) == 3)
					shapes.add(new Shapes(new Ellipse2D.Double(p1.getX(), p1.getY(), 0, 0), color, thickness));
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		if (image != null)
			g2.drawImage(image, getWidth() / 2 - image.getWidth() / 2, getHeight() / 2 - image.getHeight() / 2, null);

		for (Shapes s : shapes)
			s.drawShape(g2);
	}

	public HashMap<Keys, Double> getValues()
	{
		return values;
	}

	public BufferedImage getEditImage()
	{
		BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		paint(bi.getGraphics()); // copying the panel to the bufferdimage
		bi = bi.getSubimage(getWidth() / 2 - image.getWidth() / 2, getHeight() / 2 - image.getHeight() / 2,
				image.getWidth(), image.getHeight());

		return bi;
	}

}
