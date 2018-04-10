package SystemTray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.function.Consumer;

public class Tray extends TrayIcon
{

	private boolean isProcessFinished = true;

	public Tray(Image icon)
	{
		super(icon);
		setToolTip("Screen Shot");

		Consumer<Integer> execute = this::excuteScreenShot;

		setPopupMenu(new TrayMenu(execute));
		addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				excuteScreenShot(1);
			}
		});

		try
		{
			SystemTray.getSystemTray().add(this);
		} catch (AWTException e)
		{
			e.printStackTrace();
		}
	}

	public void excuteScreenShot(Integer l)
	{
		if (isProcessFinished)
			new Thread(() -> {

				try
				{
					String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
					path = path.substring(1, path.lastIndexOf("/")) + "/screenshot.exe";
					// decoding if there are special characters
					path = URLDecoder.decode(path, "UTF-8");
					if (new File(path).exists())
					{
						ProcessBuilder process = new ProcessBuilder(path);
						isProcessFinished = false;
						process.start().waitFor();
						isProcessFinished = true;
					} else
						TrayMainClass.errorMassage("Missing File", "File Missing screenshot.exe");
				} catch (InterruptedException | IOException e)
				{
					e.printStackTrace();
				}
			}).start();

	}
}
