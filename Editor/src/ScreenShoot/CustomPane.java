package ScreenShoot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

public class CustomPane extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Point startP, endP;
	private Rectangle cutImageRect;
	private BufferedImage cutImage;

	private BufferedImage fss;

	public CustomPane(BufferedImage screenShot)
	{
		setFocusable(false);
		setToolTipText("Select area");
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
		startP = new Point();
		endP = new Point();
		
		cutImageRect = new Rectangle();

		fss = screenShot;

		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK)
				{
					endP.setLocation(e.getX(), e.getY());

					cutImageRect.setBounds(Math.min(startP.x, endP.x), Math.min(startP.y, endP.y),
							Math.abs(endP.x - startP.x), Math.abs(endP.y - startP.y));

					if (cutImageRect.getHeight() > 0 && cutImageRect.getWidth() > 0)
						cutImage = screenShot.getSubimage(cutImageRect.x, cutImageRect.y, cutImageRect.width,
								cutImageRect.height);

					repaint();
				}
			}
		});

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
					startP.setLocation(e.getX(), e.getY());
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (cutImageRect.width > 0 && cutImageRect.height > 0)
				{
					SwingUtilities.getWindowAncestor(CustomPane.this).dispose();

					new ImageEditor.Frame(cutImage);
				}
			}
		});
	}
	
	@Override
	public Point getToolTipLocation(MouseEvent e)
	{
		Point p = e.getPoint();
		p.y += 15;
		p.x += 15;
		
		return p;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setStroke(new BasicStroke(2));

		g2.setColor(new Color(105, 105, 105, 90));
		g2.drawImage(fss, 0, 0, null);
		g2.fillRect(0, 0, getWidth(), getHeight());

		g2.setColor(Color.RED);
		g2.drawImage(cutImage, cutImageRect.x, cutImageRect.y, null);
		g2.drawRect(cutImageRect.x, cutImageRect.y, cutImageRect.width, cutImageRect.height);
	}
}
