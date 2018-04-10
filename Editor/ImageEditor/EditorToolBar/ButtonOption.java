package EditorToolBar;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class ButtonOption extends JToggleButton
{
	private static final long serialVersionUID = 1L;
	
	private Font buttonsFont = new Font(getFont().getFontName(), 0, 15);
	
	public ButtonOption(String text, ImageIcon image)
	{
		super(text, image);
		setFocusable(false);
		setFont(buttonsFont);
	}

}
