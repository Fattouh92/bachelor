package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	ArrayList<Lane> lanes = new ArrayList<Lane>();

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
				lanes.get(Integer.parseInt(parts[0])).cars.add(
						new Car(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
								Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
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

}
