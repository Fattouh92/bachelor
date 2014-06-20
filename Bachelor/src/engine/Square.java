package engine;

import java.util.ArrayList;

public class Square {
	ArrayList<Integer> lanes = new ArrayList<Integer>();
	int number_cars = 0;
	boolean rotate = false;

	public Square(ArrayList<Integer> lanes) {
		this.lanes = lanes;
	}

	public int emergency_lane(ArrayList<Lane> lanes) {
		for (int i = 0; i < lanes.size(); i++) {
			if (lanes.get(i).emergency && this.lanes.contains(new Integer(lanes.get(i).number))) {
				return i;
			}
		}
		return -1;
	}
}
