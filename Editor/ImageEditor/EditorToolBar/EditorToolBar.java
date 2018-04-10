package EditorToolBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import ImageEditor.Keys;
import ImageUpload.UploadDialog;

public class EditorToolBar extends JToolBar
{
	private static final long serialVersionUID = 1L;

	private ArrayList<ImageIcon> buttonsImages;
	
	private ArrayList<ButtonOption> buttonsShapes;

	private JButton colorButton;
	private JComboBox<String> thickness;
	
	public EditorToolBar(HashMap<Keys, Double> values, Supplier<BufferedImage> editImage)
	{
		setFloatable(false);
		
		// getting all the images for the buttons
		buttonsImages = new ArrayList<ImageIcon>();
		buttonsImages.add(new ImageIcon(getImagePath("images/new_shot.png")));
		buttonsImages.add(new ImageIcon(getImagePath("images/save_image.png")));
		buttonsImages.add(new ImageIcon(getImagePath("images/upload_image.png")));
		buttonsImages.add(new ImageIcon(getImagePath("images/line.png")));
		buttonsImages.add(new ImageIcon(getImagePath("images/rectangle.png")));
		buttonsImages.add(new ImageIcon(getImagePath("images/pen.png")));
		buttonsImages.add(new ImageIcon(getImagePath("images/circle.png")));
		buttonsImages.add(new ImageIcon(getImagePath("images/color_picker.png")));
		
		buttonsShapes = new ArrayList<>();
		buttonsShapes.add(new ButtonOption("New", buttonsImages.get(0)));
		buttonsShapes.add(new ButtonOption("Save", buttonsImages.get(1)));
		buttonsShapes.add(new ButtonOption("Upload", buttonsImages.get(2)));
		buttonsShapes.add(new ButtonShape("Line", buttonsImages.get(3), values));
		buttonsShapes.add(new ButtonShape("Rectangle", buttonsImages.get(4), values));
		buttonsShapes.add(new ButtonShape("Pen", buttonsImages.get(5), values));
		buttonsShapes.add(new ButtonShape("Circle", buttonsImages.get(6), values));
		
		buttonsShapes.get(0).addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					// Closing the editor and opening new screen shoot window
					disposeFrame();
					Thread.sleep(170); // waiting for the frame to close
					new ScreenShoot.Frame();
				} catch (Exception ex)
				{

				}
			}
		});
		
		buttonsShapes.get(1).addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				BufferedImage bEditImage = editImage.get();
				JFileChooser path = new JFileChooser();
				path.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				path.setApproveButtonText("Save");
				
				int isApproved = path.showOpenDialog(null);

				if (isApproved == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						// getting the current date by the string format
						SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-hhmmssSS");

						// setting the finished path
						File finishedPath = new File(
								String.format("%s\\%s.png", path.getSelectedFile().getPath(), sdf.format(new Date())));

						// outputting the image to the selected path
						ImageIO.write(bEditImage, "png", finishedPath);
						disposeFrame();
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		buttonsShapes.get(2).addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Window w = SwingUtilities.getWindowAncestor(EditorToolBar.this);
				new UploadDialog(editImage.get(), w);
			}
		});

		thickness = new JComboBox<>();
		thickness.setMaximumSize(new Dimension(65, 25));
		thickness.setFocusable(false);
		thickness.setFont(new Font(getFont().getFontName(), 0, 15));
		thickness.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				String item = (String) e.getItem();
				double precent = Integer.parseInt(item.substring(0, item.length() - 1)) / 100.0;

				// changing the thickness value of the shapes
				values.put(Keys.THICKNESS, 3 * precent);
			}
		});
		for (int i = 0; i < 15; i++)
			thickness.addItem((100 + 50 * i) + "%");

		colorButton = new JButton(buttonsImages.get(7));
		colorButton.setBackground(Color.RED);
		colorButton.setFocusable(false);
		colorButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Color c = JColorChooser.showDialog(null, "Choose a color", colorButton.getBackground());

				if (c != null)
				{
					// picking colors of the shapes
					colorButton.setBackground(c);
					values.put(Keys.COLOR, (double) c.getRGB());
				}
			}
		});

		Dimension space = new Dimension(3, 0);
		
		for(int i = 0; i < 3; i++)
			add(buttonsShapes.get(i));

		addSeparator();
		for(int i = 3; i < 7; i++)
			add(buttonsShapes.get(i));
		
		addSeparator();
		addSeparator(space);
		add(thickness);
		addSeparator(new Dimension(5, 0));
		add(colorButton);

	}
	
	private void disposeFrame()
	{
		SwingUtilities.getWindowAncestor(EditorToolBar.this).dispose();
	}
	
	private URL getImagePath(String path)
	{
		return getClass().getClassLoader().getResource(path);
	}
}
