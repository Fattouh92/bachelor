package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	ArrayList<Lane> lanes = new ArrayList<Lane>();
	ArrayList<Join> joins = new ArrayList<Join>();

	public void read() {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader("lanes2.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\s");
				lanes.add(new Lane(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
				if (Integer.parseInt(parts[4]) == 0) {
					lanes.get(lanes.size()-1).horizontal = true;
				} else {
					lanes.get(lanes.size()-1).horizontal = false;
				}
				if (Integer.parseInt(parts[5]) == 1) {
					lanes.get(lanes.size()-1).right_start = true;
				} else {
					lanes.get(lanes.size()-1).right_start = false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			reader = new BufferedReader(new FileReader("joins.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		line = null;
		try {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\s");
				ArrayList<Integer> Temp = new ArrayList<Integer>();
				for (int c = 3; c < parts.length; c++) {
					Temp.add(new Integer(Integer.parseInt(parts[c])));
				}
				joins.add(new Join(Temp, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2])));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			reader = new BufferedReader(new FileReader("cars2.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		line = null;
		try {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\s");
				Car temp_car = new Car(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
				if (lanes.get(Integer.parseInt(parts[0])).horizontal && lanes.get(Integer.parseInt(parts[0])).right_start) {
					temp_car.ballX = temp_car.lastBallX = lanes.get(Integer.parseInt(parts[0])).x;
					temp_car.ballY = temp_car.lastBallY = lanes.get(Integer.parseInt(parts[0])).y;
					temp_car.ballXVel = 1;
				}
				if (lanes.get(Integer.parseInt(parts[0])).horizontal && !lanes.get(Integer.parseInt(parts[0])).right_start) {
					temp_car.ballX = temp_car.lastBallX = lanes.get(Integer.parseInt(parts[0])).x + lanes.get(Integer.parseInt(parts[0])).distance - temp_car.ballWidth;
					temp_car.ballY = temp_car.lastBallY = lanes.get(Integer.parseInt(parts[0])).y;
					temp_car.ballXVel = -1;
				}
				if (!lanes.get(Integer.parseInt(parts[0])).horizontal && lanes.get(Integer.parseInt(parts[0])).right_start) {
					temp_car.ballX = temp_car.lastBallX = lanes.get(Integer.parseInt(parts[0])).x;
					temp_car.ballY = temp_car.lastBallY = lanes.get(Integer.parseInt(parts[0])).y;
					temp_car.ballYVel = 1;
				}
				if (!lanes.get(Integer.parseInt(parts[0])).horizontal && !lanes.get(Integer.parseInt(parts[0])).right_start) {
					temp_car.ballX = temp_car.lastBallX = lanes.get(Integer.parseInt(parts[0])).x;
					temp_car.ballY = temp_car.lastBallY = lanes.get(Integer.parseInt(parts[0])).y - temp_car.ballHeight + lanes.get(Integer.parseInt(parts[0])).distance;
					temp_car.ballYVel = -1;
				}
				lanes.get(Integer.parseInt(parts[0])).addCar(temp_car);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Lane> getLanes() {
		return lanes;
	}

	public void setLanes(ArrayList<Lane> lanes) {
		this.lanes = lanes;
	}

	public ArrayList<Join> getJoins() {
		return joins;
	}

	public void setJoins(ArrayList<Join> joins) {
		this.joins = joins;
	}
}
