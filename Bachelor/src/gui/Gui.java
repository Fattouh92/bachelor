package gui;

import javax.swing.*;

import engine.Lane;
import engine.Main;
import engine.Car;
import engine.Join;
import engine.Square;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class Gui extends JFrame implements ActionListener
{
	Main main = new Main();
	private GamePanel gamePanel = new GamePanel();
	private JButton startButton = new JButton("Start");
	private boolean running = false;
	private boolean paused = false;
	private int fps = 60;
	private int frameCount = 0;

	public Gui()
	{
		super("Bachelor");
		setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,2));
		p.add(startButton);
		add(gamePanel, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		main.read();
		startButton.addActionListener(this);
	}

	public static void main(String[] args)
	{
		Gui game = new Gui();
		game.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object s = e.getSource();
		if (s == startButton)
		{
			running = !running;
			if (running)
			{
				startButton.setText("Stop");
				runGameLoop();
			}
			else
			{
				startButton.setText("Start");
			}
		}
	}

	//Starts a new thread and runs the game loop in it.
	public void runGameLoop() {
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		loop.start();
	}

	//Only run this in another Thread!
	private void gameLoop()
	{
		//This value would probably be stored elsewhere.
		final double GAME_HERTZ = 30.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		//At the very most we will update the game this many times before a new render.
		//If you're worried about visual hitches more than perfect timing, set this to 1.
		final int MAX_UPDATES_BEFORE_RENDER = 1;
		//We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		//Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		//Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running)
		{
			double now = System.nanoTime();
			int updateCount = 0;

			if (!paused)
			{
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
					//System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
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
			if (main.over()) {
				paused = true;
				startButton.setText("Finished!!");
			}
		}
	}

	private void updateGame()
	{
		gamePanel.update();
	}

	private void drawGame(float interpolation)
	{
		gamePanel.setInterpolation(interpolation);
		gamePanel.repaint();
	}

	private class GamePanel extends JPanel
	{	
		ArrayList<Lane> lanes = main.getLanes();
		//Car car = new Car();
		float interpolation;
		/*float ballX, ballY, lastBallX, lastBallY;
		int ballWidth, ballHeight;
		float ballXVel, ballYVel;
		float ballSpeed;

		int lastDrawX, lastDrawY;*/

		public GamePanel()
		{
			//cars.add(new Car(100, 100, 25, 25));
			//cars.add(new Car(100, 100, 25, 25));
			/*ballX = lastBallX = 100;
			ballY = lastBallY = 100;
			ballWidth = 25;
			ballHeight = 25;
			ballSpeed = 25;
			ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
			ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;*/
		}

		public void setInterpolation(float interp)
		{
			interpolation = interp;
		}

		public void update()
		{	
			for (int i = 0; i < lanes.size(); i++) {
				for (int k=0; k < lanes.get(i).getCars().size(); k++) {
					if (lanes.get(i).isHorizontal()) {
						if (lanes.get(i).isRight_start()) {
							lanes.get(i).getCars().get(k).update2(lanes.get(i).getX(), lanes.get(i).getX()+lanes.get(i).getDistance(), main.getLanes(), main.getJoins(), main.getSquares(), main.getRules());
						} else {
							lanes.get(i).getCars().get(k).update2(lanes.get(i).getX(), lanes.get(i).getX()+lanes.get(i).getDistance(), main.getLanes(), main.getJoins(), main.getSquares(), main.getRules());
						}
					} else {
						if (lanes.get(i).isRight_start()) {
							lanes.get(i).getCars().get(k).update2(lanes.get(i).getY(), lanes.get(i).getY()+lanes.get(i).getDistance(), main.getLanes(), main.getJoins(), main.getSquares(), main.getRules());
						} else {
							lanes.get(i).getCars().get(k).update2(lanes.get(i).getY(), lanes.get(i).getY()+lanes.get(i).getDistance(), main.getLanes(), main.getJoins(), main.getSquares(), main.getRules());
						}
					}
				}
			}
		}

		public void paintComponent(Graphics g)
		{	
			Graphics2D g2d = (Graphics2D)g;
			super.paintComponent(g2d);
			ArrayList<Lane> lanes = main.getLanes();
			for (int j=0; j < lanes.size(); j++) {
				if (lanes.get(j).isHorizontal()) {
					g2d.drawRect(lanes.get(j).getX(), lanes.get(j).getY(), lanes.get(j).getDistance(), 40);
				} else {
					g2d.drawRect(lanes.get(j).getX(), lanes.get(j).getY(), 40, lanes.get(j).getDistance());
				}
			}

			for (int c = 0; c < lanes.size(); c++) {
				for (int i = 0; i < lanes.get(c).getCars().size(); i++) {
					//BS way of clearing out the old rectangle to save CPU.
					g2d.setColor(getBackground());
					g2d.fillRect(lanes.get(c).getCars().get(i).getLastDrawX()-1, lanes.get(c).getCars().get(i).getLastDrawY()-1, lanes.get(c).getCars().get(i).getBallWidth()+2, lanes.get(c).getCars().get(i).getBallHeight()+2);
					g2d.fillRect(5, 0, 75, 30);

					ArrayList<Integer> temp_directions = lanes.get(c).getCars().get(i).getDirections(); 
					g2d.setColor(lanes.get(c).getCars().get(i).getColor());
					if(!lanes.get(c).getCars().get(i).isArrived()) {
						if (!lanes.get(c).getCars().get(i).isJoin()) {
							Lane temp_lane = this.lanes.get(lanes.get(c).getCars().get(i).getLane());
							if (temp_lane.isHorizontal()) {
								g2d.fillRect(temp_lane.getX(), temp_lane.getY() + 15, temp_lane.getDistance(), 10);
							} else {
								g2d.fillRect(temp_lane.getX() + 15, temp_lane.getY(), 10,temp_lane.getDistance());
							}
						}
						for(int d = 0; d< temp_directions.size(); d++) {
							Lane temp_lane = lanes.get(temp_directions.get(d));
							if (temp_lane.isHorizontal()) {
								g2d.fillRect(temp_lane.getX(), temp_lane.getY() + 15, temp_lane.getDistance(), 10);
							} else {
								g2d.fillRect(temp_lane.getX() + 15, temp_lane.getY(), 10,temp_lane.getDistance());
							}
						}
					}

					int drawX = (int) ((lanes.get(c).getCars().get(i).getBallX() - lanes.get(c).getCars().get(i).getLastBallX()) * interpolation + lanes.get(c).getCars().get(i).getLastBallX());
					int drawY = (int) ((lanes.get(c).getCars().get(i).getBallY() - lanes.get(c).getCars().get(i).getLastBallY()) * interpolation + lanes.get(c).getCars().get(i).getLastBallY());
					AffineTransform old = g2d.getTransform();
					g2d.rotate(Math.toRadians(lanes.get(c).getCars().get(i).getAngle()),lanes.get(c).getCars().get(i).getBallX()+lanes.get(c).getCars().get(i).getBallWidth()/2,lanes.get(c).getCars().get(i).getBallY()+lanes.get(c).getCars().get(i).getBallHeight()/2);
					if(lanes.get(c).getCars().get(i).isEmergency()) {
						g2d.setColor(Color.RED);
					} else {
						g2d.setColor(Color.BLUE);
					}
					g.fillRect(drawX, drawY, lanes.get(c).getCars().get(i).getBallWidth(), lanes.get(c).getCars().get(i).getBallHeight());
					g2d.setTransform(old);

					lanes.get(c).getCars().get(i).setLastDrawX(drawX);
					lanes.get(c).getCars().get(i).setLastDrawY(drawY);

					g2d.setColor(Color.BLACK);
					g2d.drawString("FPS: " + fps, 5, 10);
				}
			}
			frameCount++;
		}
	}

	/*private class Ball
	{
		float x, y, lastX, lastY;
		int width, height;
		float xVelocity, yVelocity;
		float speed;

		int lastDrawX, lastDrawY;

		public Ball()
		{
			width = (int) (Math.random() * 50 + 10);
			height = (int) (Math.random() * 50 + 10);
			x = (float) (Math.random() * (gamePanel.getWidth() - width) + width/2);
			y = (float) (Math.random() * (gamePanel.getHeight() - height) + height/2);
			lastX = x;
			lastY = y;
			xVelocity = (float) Math.random() * speed*2 - speed;
			yVelocity = (float) Math.random() * speed*2 - speed;
		}

		public void update()
		{
			lastX = x;
			lastY = y;

			x += xVelocity;
			y += yVelocity;

			if (x + width/2 >= gamePanel.getWidth())
			{
				xVelocity *= -1;
				x = gamePanel.getWidth() - width/2;
				yVelocity = (float) Math.random() * speed*2 - speed;
			}
			else if (x - width/2 <= 0)
			{
				xVelocity *= -1;
				x = width/2;
			}

			if (y + height/2 >= gamePanel.getHeight())
			{
				yVelocity *= -1;
				y = gamePanel.getHeight() - height/2;
				xVelocity = (float) Math.random() * speed*2 - speed;
			}
			else if (y - height/2 <= 0)
			{
				yVelocity *= -1;
				y = height/2;
			}
		}

		public void draw(Graphics g) {

		}
	}*/


}