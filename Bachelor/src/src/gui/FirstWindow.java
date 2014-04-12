package src.gui;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import src.engine.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FirstWindow {
	Main main = new Main();
	ArrayList<JPanel> lanes = new ArrayList<JPanel>();
	JPanel panel;
	JFrame guiFrame;
	//Note: Typically the main method will be in a
	//separate class. As this is a simple one class
	//example it's all in the one class.
	public static void main(String[] args) {

		FirstWindow window = new FirstWindow();
		window.run();
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
		this.panel = new JPanel();
		//panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setLayout(null);
		guiFrame.add(panel);

		//guiFrame.setLayout(new BorderLayout());
		//guiFrame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\Mohamed\\Pictures\\T.png")));
		//guiFrame.setLayout(new FlowLayout());
		/* 
      //Options for the JComboBox 
      String[] fruitOptions = {"Apple", "Apricot", "Banana"
              ,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"};

      //Options for the JList
      String[] vegOptions = {"Asparagus", "Beans", "Broccoli", "Cabbage"
              , "Carrot", "Celery", "Cucumber", "Leek", "Mushroom"
              , "Pepper", "Radish", "Shallot", "Spinach", "Swede"
              , "Turnip"};

      //The first JPanel contains a JLabel and JCombobox
      final JPanel comboPanel = new JPanel();
      JLabel comboLbl = new JLabel("Fruits:");
      JComboBox fruits = new JComboBox(fruitOptions);

      comboPanel.add(comboLbl);
      comboPanel.add(fruits);

      //Create the second JPanel. Add a JLabel and JList and
      //make use the JPanel is not visible.
      final JPanel listPanel = new JPanel();
      listPanel.setVisible(false);
      JLabel listLbl = new JLabel("Vegetables:");
      JList vegs = new JList(vegOptions);
      vegs.setLayoutOrientation(JList.HORIZONTAL_WRAP);

      listPanel.add(listLbl);
      listPanel.add(vegs);

      JButton vegFruitBut = new JButton( "Fruit or Veg");

      //The ActionListener class is used to handle the
      //event that happens when the user clicks the button.
      //As there is not a lot that needs to happen we can 
      //define an anonymous inner class to make the code simpler.
      vegFruitBut.addActionListener(new ActionListener()
      {
          @Override
          public void actionPerformed(ActionEvent event)
          {
             //When the fruit of veg button is pressed
             //the setVisible value of the listPanel and
             //comboPanel is switched from true to 
             //value or vice versa.
             listPanel.setVisible(!listPanel.isVisible());
             comboPanel.setVisible(!comboPanel.isVisible());

          }
      });

      //The JFrame uses the BorderLayout layout manager.
      //Put the two JPanels and JButton in different areas.
      guiFrame.add(comboPanel, BorderLayout.NORTH);
      guiFrame.add(listPanel, BorderLayout.CENTER);
      guiFrame.add(vegFruitBut,BorderLayout.SOUTH);*/

		//make sure the JFrame is visible
		guiFrame.setVisible(true);
	}

	public void drawLanes() {
		int c;
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
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
		//1
		tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		tempPanel.setOpaque(true);
		tempPanel.setLayout(null);
		panel.add(tempPanel);
		tempPanel.setBounds(25 + insets.left, 155 + insets.top,
				main.getLanes().get(1).getDistance()*5, 20);
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
		//2
		tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		tempPanel.setOpaque(true);
		tempPanel.setLayout(null);
		panel.add(tempPanel);
		tempPanel.setBounds(150 + insets.left, 5 + insets.top, 20,
				main.getLanes().get(2).getDistance()*5);
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
		//3
		tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		tempPanel.setOpaque(true);
		tempPanel.setLayout(null);
		panel.add(tempPanel);
		tempPanel.setBounds(175 + insets.left, 5 + insets.top, 20,
				main.getLanes().get(3).getDistance()*5);
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
		//4
		tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		tempPanel.setOpaque(true);
		tempPanel.setLayout(null);
		panel.add(tempPanel);
		tempPanel.setBounds(195 + insets.left, 130 + insets.top,
				main.getLanes().get(4).getDistance()*5, 20);
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
		//5
		tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		tempPanel.setOpaque(true);
		tempPanel.setLayout(null);
		panel.add(tempPanel);
		tempPanel.setBounds(195 + insets.left, 155 + insets.top,
				main.getLanes().get(5).getDistance()*5, 20);
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
		//6
		tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		tempPanel.setOpaque(true);
		tempPanel.setLayout(null);
		panel.add(tempPanel);
		tempPanel.setBounds(175 + insets.left, 175 + insets.top, 20,
				main.getLanes().get(6).getDistance()*5);
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
		
		tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		tempPanel.setOpaque(true);
		tempPanel.setLayout(null);
		panel.add(tempPanel);
		tempPanel.setBounds(150 + insets.left, 175 + insets.top, 20,
				main.getLanes().get(7).getDistance()*5);
		lanes.add(tempPanel);
		panel.revalidate();
		panel.repaint();
		guiFrame.revalidate();
		guiFrame.repaint();
	}

	public void run() {
		int i, j;
		drawLanes();
		ArrayList<Lane> lanes= new ArrayList<Lane>();
		ArrayList<Car> cars= new ArrayList<Car>();
		for(int counter = 0; counter < 10; counter ++) {
			lanes = main.getLanes();
			for( i = 0; i< lanes.size(); i++) {
				this.lanes.get(i).removeAll();
				cars = lanes.get(i).getCars();
				for ( j = 0; j < cars.size(); j++) {
					Insets insets = this.lanes.get(i).getInsets();
					JLabel temp = new JLabel("Car");
					temp.setForeground(Color.red);
					temp.setBounds(insets.left + 5*(lanes.get(i).getDistance() - cars.get(j).getCurrent_pos()) , insets.top,
							cars.get(j).getCar_size()*5, this.lanes.get(i).getHeight());
					this.lanes.get(i).add(temp);
				}
			}
			/*try {
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
					Point point = this.lanes.get(i).getLocation();
					System.out.println(point.x);
					System.out.println(point.y);
					point.setLocation(point.x+cars.get(j).getCurrent_pos(), point.y);
					JLabel temp = new JLabel("Car");
					temp.setForeground(Color.red);
					this.lanes.get(i).add(temp, point);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			main.cross();*/
		}
	}

}
