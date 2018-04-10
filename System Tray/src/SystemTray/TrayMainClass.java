package SystemTray;

import java.awt.SystemTray;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class TrayMainClass
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		if (!SystemTray.isSupported())
			errorMassage("Not Supported", "Tray isn't supported on your operation system");
		else
		{
			ClassLoader classloader = new TrayMainClass().getClass().getClassLoader();
			ImageIcon icon = new ImageIcon(classloader.getResource("images/java.png"));

			Tray t = new Tray(icon.getImage());
			
			try
			{
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e)
			{
				e.printStackTrace();
			}

			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
			logger.setUseParentHandlers(false);

			GlobalScreen.addNativeKeyListener(new GlobalKeyEvent(t::excuteScreenShot));
		}
	}

	public static void errorMassage(String title, String massage)
	{
		JOptionPane.showMessageDialog(null, massage, title, JOptionPane.ERROR_MESSAGE);
	}
}
