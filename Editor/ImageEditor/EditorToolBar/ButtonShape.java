package EditorToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import ImageEditor.Keys;

public class ButtonShape extends ButtonOption implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static double shapeNumCounter = 0;
	private static ButtonGroup group = new ButtonGroup();
	
	private HashMap<Keys, Double> values;
	private double shapeNum;

	
	public ButtonShape(String text, ImageIcon image, HashMap<Keys, Double> v)
	{
		super(text, image);
		
		shapeNum = shapeNumCounter;
		shapeNumCounter++;
		
		addActionListener(this);
		
		values = v;
		
		group.add(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		values.put(Keys.SHAPE, shapeNum);	
	}

}
