package gui;

import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

public class FirstWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FirstWindow window = new FirstWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(null);
		/*Image image = null;
		try {
			image = ImageIO.read(new File("C:\\Users\\Mohamed\\Pictures\\T.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JPanel j = new IPanel(image);
		j.setVisible(true);*/  
		Image bg_Image = new Image(display, "T.png");
		shell.setBackgroundImage(bg_Image);
		/*JFrame f = new JFrame();
		try {
			f.getContentPane().add(new JPanelWithBackground("T.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.setVisible(true);*/
	}

	/*public class IPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Image             imageOrg         = null;
		private Image             image            = null;
		{
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(final ComponentEvent e) {
					final int w = IPanel.this.getWidth();
					final int h = IPanel.this.getHeight();
					image = w > 0 && h > 0 ? imageOrg.getScaledInstance(w, h, Image.SCALE_SMOOTH) : imageOrg;
					IPanel.this.repaint();
				}
			});
		}

		public IPanel(final Image i) {
			imageOrg = i;
			image = i;
		}

		@Override
		public void paintComponent(final Graphics g) {
			super.paintComponent(g);
			if (image != null)
				g.drawImage(image, 0, 0, null);
		}
	}
	public class JPanelWithBackground extends JPanel {

		  private Image backgroundImage;

		  // Some code to initialize the background image.
		  // Here, we use the constructor to load the image. This
		  // can vary depending on the use case of the panel.
		  public JPanelWithBackground(String fileName) throws IOException {
		    backgroundImage = ImageIO.read(new File(fileName));
		  }

		  public void paintComponent(Graphics g) {
		    super.paintComponent(g);

		    // Draw the background image.
		    g.drawImage(backgroundImage, 0, 0, this);
		  }
		}*/
}

