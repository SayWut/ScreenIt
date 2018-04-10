package SystemTray;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class TrayMenu extends PopupMenu implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private MenuItem exit;
	private MenuItem screenShot;
	
	private Consumer<Integer> execute;
	
	public TrayMenu(Consumer<Integer> execute)
	{
		this.execute = execute;
		
		screenShot = new MenuItem("Take a screenshot");
		screenShot.addActionListener(this);
		
		exit = new MenuItem("Exit");
		exit.addActionListener(this);

		add(screenShot);
		addSeparator();
		add(exit);
	}

	@Override
	public void actionPerformed(ActionEvent ac)
	{
		switch (ac.getActionCommand())
		{
			case "Take a screenshot":
				execute.accept(1);
				break;
	
			case "Exit":
				System.exit(0);
				break;
		}
	}
}
