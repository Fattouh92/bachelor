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
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FirstWindow {
	Main main = new Main();
	ArrayList<JPanel> lanes = new ArrayList<JPanel>();
	GamePanel panel = new GamePanel();
	JFrame guiFrame;

	private boolean running = false;
	private boolean paused = false;
	private int fps = 60;
	private int frameCount = 0;
	boolean added = false;

	//Note: Typically the main method will be in a
	//separate class. As this is a simple one class
	//example it's all in the one class.
	public static void main(String[] args) {

		FirstWindow window = new FirstWindow();
		//window.run();
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

	public void run() {
		int i, j;
		//drawLanes();
		ArrayList<Lane> lanes= new ArrayList<Lane>();
		ArrayList<Car> cars= new ArrayList<Car>();
		for(int counter = 0; counter < 10; counter ++) {
			lanes = main.getLanes();
			for( i = 0; i< lanes.size(); i++) {
				this.lanes.get(i).removeAll();
				cars = lanes.get(i).getCars();
				for ( j = 0; j < cars.size(); j++) {
					Insets insets = this.lanes.get(i).getInsets();
					//g.fillRect(insets.left + 5*(lanes.get(i).getDistance() - cars.get(j).getCurrent_pos()) , insets.top,
					//	cars.get(j).getCar_size()*5, this.lanes.get(i).getHeight());
					//g.setColor(Color.red);
					JLabel temp = new JLabel("Car");
					temp.setForeground(Color.red);
					temp.setBounds(insets.left + 5*(lanes.get(i).getDistance() - cars.get(j).getCurrent_pos()) , insets.top,
							cars.get(j).getCar_size()*5, this.lanes.get(i).getHeight());
					this.lanes.get(i).add(temp);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			main.runSimulation();

			lanes = main.getLanes();
			for( i = 0; i< lanes.size(); i++) {
				this.lanes.get(i).removeAll();
				cars = lanes.get(i).getCars();
				for ( j = 0; j < cars.size(); j++) {
					Insets insets = this.lanes.get(i).getInsets();
					//g.fillRect(insets.left + 5*(lanes.get(i).getDistance() - cars.get(j).getCurrent_pos()) , insets.top,
					//	cars.get(j).getCar_size()*5, this.lanes.get(i).getHeight());
					//g.setColor(Color.red);
					JLabel temp = new JLabel("Car");
					temp.setForeground(Color.red);
					temp.setBounds(insets.left + 5*(lanes.get(i).getDistance() - cars.get(j).getCurrent_pos()) , insets.top,
							cars.get(j).getCar_size()*5, this.lanes.get(i).getHeight());
					this.lanes.get(i).add(temp);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			main.cross();
		}
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

	public void gameLoop() {

	}

	private class GamePanel extends JPanel {

		public GamePanel() {

		}

		public void drawLanes() {
			System.out.println("here");
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
			System.out.println(lanes.size());
		}

		public void paintComponent(Graphics g)
		{	
			super.paintComponent(g);
			drawLanes();
		}
	}

}
