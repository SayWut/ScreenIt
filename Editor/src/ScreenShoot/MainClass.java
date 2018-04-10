package ScreenShoot;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainClass
{

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Frame();
	}
}
