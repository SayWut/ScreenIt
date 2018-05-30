package MandelbrotSet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Panel extends JPanel
{
	private static final long serialVersionUID = 1L;

	// final double startX = -1.26, startY = 0.3854;
	// double jump = 0.0000076; // 0.003

	double startX = -2.2, startY = 1.5;
	double jump = 0.003;

	int maxIteration = 100;

	public Panel()
	{
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					startX += e.getX() * jump / 1.7;
					startY -= e.getY() * jump / 1.7;

					jump *= 0.4;
					maxIteration += 100;

					System.out.println(startX + " " + startY + " " + jump);

					repaint();
				}
			}

		});
	}

	@Override
	public void paintComponent(Graphics g)
	{
		double x = startX;
		double y = startY;

		for (int row = 0; row < getWidth(); row++)
		{
			for (int col = 0; col < getHeight(); col++)
			{
				// Mandelbrot set
				ComplexNumber c = new ComplexNumber(x, y);
				ComplexNumber z = new ComplexNumber(0, 0);

				// Julia sets
				//ComplexNumber c = new ComplexNumber(-0.7296593186372746,
				//-0.21989604208416824);
				//ComplexNumber z = new ComplexNumber(x, y);

				y -= jump;

				int iteration = 0;

				while (!Double.isInfinite(Math.sqrt(Math.pow(z.getReal(), 2) + Math.pow(z.getImaginery(), 2)))
						&& iteration < maxIteration)
				{
					z = ComplexNumber.pow2(z).sum(c);
					iteration++;
				}

				if (iteration >= maxIteration)
				{
					g.setColor(Color.black);
					g.drawLine(row, col, row, col);
				} else
				{
					if (iteration <= 0)
						iteration = 1;
					
					Color color = new Color(iteration * 10, false);
					//color = new Color(color.getRGB() ^ 0x00ff00);

					g.setColor(color);
					g.drawLine(row, col, row, col);
				}
			}

			y = startY;
			x += jump;
		}
	}
}
