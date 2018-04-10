package ImageUpload;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FinishUploadPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JButton open;
	private JButton close;
	private JButton copy;
	private JTextArea urlLink;
	
	public FinishUploadPanel(String url)
	{
		super(new GridBagLayout());
		
		open = new JButton("Open");
		open.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Desktop.getDesktop().browse(new URI(url));
					System.exit(0);
				} catch (IOException | URISyntaxException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		close = new JButton("Close");
		close.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		
		copy = new JButton("Copy");
		copy.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				StringSelection urlSelection = new StringSelection(url);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(urlSelection, null);
				System.exit(0);
			}
		});
		
		urlLink = new JTextArea(url);
		urlLink.setFont(new Font(urlLink.getFont().getFontName(), Font.CENTER_BASELINE, open.getFont().getSize()));
		urlLink.setPreferredSize(new Dimension(urlLink.getPreferredSize().width, open.getPreferredSize().height));
		urlLink.setEditable(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 5, 0, 0);
		
		add(open, gbc);
		gbc.gridx = 1;
		add(copy, gbc);
		gbc.gridx = 2;
		add(close, gbc);
		gbc.gridx = 3;
		add(urlLink, gbc);
	}

}
