package ImageUpload;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	public UploadDialog(BufferedImage image, Window w)
	{
		super((JDialog) null);
		w.setVisible(false);

		int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 4.3);
		int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 14.5);

		UploadPanel up = new UploadPanel();

		setSize(width, height);
		setTitle("Uploading Image");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(up);

		setVisible(true);

		// Uploading the image
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				String path = null;
				try
				{
					path = uploadImage(image);
					path = getURL(path);
				} catch (IOException | JSONException e)
				{
					JOptionPane.showMessageDialog(UploadDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

				// After uploading
				// Setting the finish panel
				FinishUploadPanel fup = new FinishUploadPanel(path);

				setContentPane(fup);
				// Refreshing the dialog to show new panel
				invalidate();
				validate();
				repaint();

			}
		}).start();

		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent arg0)
			{
				System.exit(0);
			}
		});
	}

	private String getURL(String s) throws JSONException
	{
		// Extracting the URL from the upload data
		JSONObject jObject = new JSONObject(s);
		JSONObject data = (JSONObject) jObject.get("data");

		return data.getString("link");
	}

	private String uploadImage(BufferedImage image) throws IOException
	{
		
			String clientID = "";

			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ImageIO.write(image, "png", byteArray);
			byte[] byteImage = byteArray.toByteArray();
			String dataImage = Base64.getEncoder().encodeToString(byteImage);
			String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");

			URL url = new URL("https://api.imgur.com/3/image");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Client-ID " + clientID);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			conn.connect();
			StringBuilder stb = new StringBuilder();
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
				stb.append(line).append("\n");

			wr.close();
			rd.close();

			return stb.toString();
		
	}
}
