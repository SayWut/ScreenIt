package ScreenShoot;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Rectangle bounds;

	public Frame()
	{

		bounds = getFrameBounds();

		setLocation(bounds.x, bounds.y);
		setSize(bounds.width, bounds.height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		setAlwaysOnTop(true);
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		setUndecorated(true);

		setContentPane(new CustomPane(takeScreenShot()));

		setVisible(true);

		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					System.exit(0);
			}
		});
		
		requestFocus();
		
	}

	private BufferedImage takeScreenShot()
	{
		try
		{
			Robot r = new Robot();
			BufferedImage screenShot = r.createScreenCapture(bounds);

			return screenShot;
		} catch (AWTException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private Rectangle getFrameBounds()
	{
		Rectangle monitorsBounds = new Rectangle();

		// get all monitors
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (GraphicsDevice gd : ge.getScreenDevices())
		{
			// get monitor bounds x,y,width,height
			Rectangle tmp = gd.getDefaultConfiguration().getBounds();
			
			if(tmp.x < monitorsBounds.x)
				monitorsBounds.x = tmp.x; // setting jframe x
			if(tmp.y < monitorsBounds.y)
				monitorsBounds.y = tmp.y; // setting jframe y
			
			monitorsBounds.width += tmp.width; // setting jframe width
			monitorsBounds.height += tmp.height; // setting jframe hieght
		}

		return monitorsBounds;
	}
}
