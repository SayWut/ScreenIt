package ImageUpload;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class UploadPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JProgressBar pb;
	private JButton cancel;

	public UploadPanel()
	{
		super(new GridBagLayout());

		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});

		pb = new JProgressBar();
		pb.setIndeterminate(true);
		pb.setPreferredSize(new Dimension(220, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.gridx = 0;
		add(cancel, gbc);
		gbc.gridx = 1;
		add(pb, gbc);
	}

}
