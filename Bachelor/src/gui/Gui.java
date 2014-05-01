package gui;

import javax.swing.*;

import engine.Lane;
import engine.Main;
import engine.Car;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
		setSize(600, 600);
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
					lanes.get(i).getCars().get(k).update(getHeight(), getWidth());

				}
			}
			//car.update();
			/*lastBallX = ballX;
			lastBallY = ballY;

			ballX += ballXVel;
			ballY += ballYVel;

			if (ballX + ballWidth/2 >= getWidth())
			{
				ballXVel *= -1;
				ballX = getWidth() - ballWidth/2;
				ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
			}
			else if (ballX - ballWidth/2 <= 0)
			{
				ballXVel *= -1;
				ballX = ballWidth/2;
			}

			if (ballY + ballHeight/2 >= getHeight())
			{
				ballYVel *= -1;
				ballY = getHeight() - ballHeight/2;
				ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
			}
			else if (ballY - ballHeight/2 <= 0)
			{
				ballYVel *= -1;
				ballY = ballHeight/2;
			}*/
		}

		public void paintComponent(Graphics g)
		{	
			ArrayList<Lane> lanes = main.getLanes();
			for (int j=0; j < lanes.size(); j++) {
				if (lanes.get(j).isHorizontal()) {
					g.drawRect(lanes.get(j).getX(), lanes.get(j).getY(), lanes.get(j).getDistance(), 30);
				} else {
					g.drawRect(lanes.get(j).getX(), lanes.get(j).getY(), 30, lanes.get(j).getDistance());
				}
			}

			for (int c = 0; c < lanes.size(); c++) {
				for (int i = 0; i < lanes.get(c).getCars().size(); i++) {


					//BS way of clearing out the old rectangle to save CPU.
					g.setColor(getBackground());
					g.fillRect(lanes.get(c).getCars().get(i).getLastDrawX()-1, lanes.get(c).getCars().get(i).getLastDrawY()-1, lanes.get(c).getCars().get(i).getBallWidth()+2, lanes.get(c).getCars().get(i).getBallHeight()+2);
					g.fillRect(5, 0, 75, 30);

					g.setColor(Color.RED);
					int drawX = (int) ((lanes.get(c).getCars().get(i).getBallX() - lanes.get(c).getCars().get(i).getLastBallX()) * interpolation + lanes.get(c).getCars().get(i).getLastBallX() - lanes.get(c).getCars().get(i).getBallWidth()/2);
					int drawY = (int) ((lanes.get(c).getCars().get(i).getBallY() - lanes.get(c).getCars().get(i).getLastBallY()) * interpolation + lanes.get(c).getCars().get(i).getLastBallY() - lanes.get(c).getCars().get(i).getBallHeight()/2);
					g.fillRect(drawX, drawY, lanes.get(c).getCars().get(i).getBallWidth(), lanes.get(c).getCars().get(i).getBallHeight());

					lanes.get(c).getCars().get(i).setLastDrawX(drawX);
					lanes.get(c).getCars().get(i).setLastDrawY(drawY);

					g.setColor(Color.BLACK);
					g.drawString("FPS: " + fps, 5, 10);
				}}
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