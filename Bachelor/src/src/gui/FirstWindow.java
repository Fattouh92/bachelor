package src.gui;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import src.engine.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FirstWindow {
	Main main = new Main();
	ArrayList<JPanel> lanes = new ArrayList<JPanel>();
	GamePanel panel = new GamePanel();
	JFrame guiFrame;

	private int fps = 60;
	private int frameCount = 0;
	boolean added = false;

	//Note: Typically the main method will be in a
	//separate class. As this is a simple one class
	//example it's all in the one class.
	public static void main(String[] args) {

		FirstWindow window = new FirstWindow();
		window.runGameLoop();
	}

	public FirstWindow()
	{
		guiFrame = new JFrame();
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Example GUI");
		guiFrame.setSize(600,600);

		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		//panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setLayout(null);
		guiFrame.add(panel);
		guiFrame.setVisible(true);
	}

	public void runGameLoop()
	{
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		loop.start();
	}
	public void gameLoop2() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				updateGame();
				updates++;
				delta--;
			}
			drawGame(20);
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	public void gameLoop() {
		//This value would probably be stored elsewhere.
		final double GAME_HERTZ = 30.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		//At the very most we will update the game this many times before a new render.
		//If you're worried about visual hitches more than perfect timing, set this to 1.
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		//We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		//Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		//Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		boolean running = false;
		while (running)
		{
			double now = System.nanoTime();
			int updateCount = 0;

			//Do as many game updates as we need to, potentially playing catchup.
			while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
			{	
				updateGame();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			//If for some reason an update takes forever, we don't want to do an insane number of catchups.
			//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
			if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
			{
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			//Render. To do so, we need to calculate interpolation for a smooth render.
			float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
			drawGame(interpolation);
			lastRenderTime = now;

			//Update the frames we got.
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime)
			{
				System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
				fps = frameCount;
				frameCount = 0;
				lastSecondTime = thisSecond;
			}

			//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
			while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
			{
				Thread.yield();

				//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
				try {Thread.sleep(1);} catch(Exception e) {} 

				now = System.nanoTime();
			}
		}
	}

	private void updateGame()
	{
		System.out.println("update");
		panel.update();
	}

	private void drawGame(float interpolation)
	{
		System.out.println("draw");
		panel.setInterpolation(interpolation);
		panel.repaint();
	}

	private class GamePanel extends JPanel {
		float interpolation;

		public GamePanel() {
		}

		public void setInterpolation(float interp)
		{
			interpolation = interp;
		}

		public void update()
		{
			main.runSimulation();
		}

		public void drawLanes() {
			main.read(8);
			JPanel tempPanel;
			Insets insets = panel.getInsets();
			//0
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(25 + insets.left, 130 + insets.top,
					main.getLanes().get(0).getDistance()*5, 20);
			if (!added)
				lanes.add(tempPanel);
			//1
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(25 + insets.left, 155 + insets.top,
					main.getLanes().get(1).getDistance()*5, 20);
			if (!added)
				lanes.add(tempPanel);
			//2
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(150 + insets.left, 5 + insets.top, 20,
					main.getLanes().get(2).getDistance()*5);
			if (!added)
				lanes.add(tempPanel);
			//3
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(175 + insets.left, 5 + insets.top, 20,
					main.getLanes().get(3).getDistance()*5);
			if (!added)
				lanes.add(tempPanel);
			//4
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(195 + insets.left, 130 + insets.top,
					main.getLanes().get(4).getDistance()*5, 20);
			if (!added)
				lanes.add(tempPanel);
			//5
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(195 + insets.left, 155 + insets.top,
					main.getLanes().get(5).getDistance()*5, 20);
			if (!added)
				lanes.add(tempPanel);
			//6
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(175 + insets.left, 175 + insets.top, 20,
					main.getLanes().get(6).getDistance()*5);
			if (!added)
				lanes.add(tempPanel);
			//7
			tempPanel = new JPanel();
			tempPanel.setBackground(Color.black);
			tempPanel.setOpaque(true);
			tempPanel.setLayout(null);
			panel.add(tempPanel);
			tempPanel.setBounds(150 + insets.left, 175 + insets.top, 20,
					main.getLanes().get(7).getDistance()*5);
			if (!added)
				lanes.add(tempPanel);
			added = true;
		}

		public void drawCars(Graphics2D g) {
			int i, j;
			ArrayList<Lane> lanes2= new ArrayList<Lane>();
			ArrayList<Car> cars= new ArrayList<Car>();
			for(int counter = 0; counter < 10; counter ++) {
				lanes2 = main.getLanes();
				for( i = 0; i< lanes.size(); i++) {
					lanes.get(i).removeAll();
					cars = lanes2.get(i).getCars();
					for ( j = 0; j < cars.size(); j++) {
						Insets insets = lanes.get(i).getInsets();
						g.setColor(Color.red);
						g.draw(new Rectangle(lanes.get(0).getLocation().x + 5*(lanes2.get(i).getDistance() - cars.get(j).getCurrent_pos()), lanes.get(0).getLocation().y,
								cars.get(j).getCar_size()*5, lanes.get(i).getHeight()));
						JLabel temp = new JLabel("Car");
						temp.setForeground(Color.red);
						temp.setBounds(insets.left + 5*(lanes2.get(i).getDistance() - cars.get(j).getCurrent_pos()) , insets.top,
								cars.get(j).getCar_size()*5, lanes.get(i).getHeight());
						lanes.get(i).add(temp);
					}
				}
			}
		}
		
		@Override
		public void paintComponent(Graphics g)
		{	
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			drawLanes();
			//drawCars(g2d);
			//g2d.fillRect(lanes.get(2).getInsets().left, lanes.get(2).getInsets().top, 30, 30);
			//g2d.draw(new Rectangle(lanes.get(0).getLocation().x,lanes.get(0).getLocation().y,50,50));
			int i, j;
			ArrayList<Lane> lanes2= new ArrayList<Lane>();
			ArrayList<Car> cars= new ArrayList<Car>();
			for(int counter = 0; counter < 10; counter ++) {
				lanes2 = main.getLanes();
				for( i = 0; i< lanes.size(); i++) {
					lanes.get(i).removeAll();
					cars = lanes2.get(i).getCars();
					for ( j = 0; j < cars.size(); j++) {
						Insets insets = lanes.get(i).getInsets();
						g2d.setColor(Color.red);
						g2d.draw(new Rectangle(lanes.get(i).getLocation().x + 5*(lanes2.get(i).getDistance() - cars.get(j).getCurrent_pos()), lanes.get(i).getLocation().y,
								cars.get(j).getCar_size()*5, lanes.get(i).getHeight()));
						JLabel temp = new JLabel("Car");
						temp.setForeground(Color.red);
						temp.setBounds(insets.left + 5*(lanes2.get(i).getDistance() - cars.get(j).getCurrent_pos()) , insets.top,
								cars.get(j).getCar_size()*5, lanes.get(i).getHeight());
						lanes.get(i).add(temp);
					}
				}
			}
		}
	}
}