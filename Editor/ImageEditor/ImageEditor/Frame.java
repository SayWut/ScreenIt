package ImageEditor;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import EditorToolBar.EditorToolBar;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;

	private CustomPane panel;
	
	public Frame(BufferedImage image)
	{
		super("Editor");
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		setExtendedState(MAXIMIZED_BOTH);
		
		panel = new CustomPane(image);
		
		JScrollPane sp = new JScrollPane(panel);
		sp.getVerticalScrollBar().setUnitIncrement(16);
		sp.getHorizontalScrollBar().setUnitIncrement(16);
		
		Supplier<BufferedImage> getEditImage = panel::getEditImage;
		
		add(sp);
		add(new EditorToolBar(panel.getValues(), getEditImage), BorderLayout.NORTH);
				
		setVisible(true);
	}
}
