package engine;

import java.util.ArrayList;

public class Square {
	ArrayList<Integer> lanes = new ArrayList<Integer>();
	int number_cars = 0;
	int rotate_lane = -1;
	ArrayList<Integer> lanes_inside = new ArrayList<Integer>();

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

	public boolean can_rotate (int lane) {
		System.out.println("called");
		for (int i = 0; i < lanes.size(); i++) {
			if (lanes_inside.contains(lanes.get(i)) && lanes.get(i).intValue() != lane) {
				System.out.println(lanes.get(i));
				return false;
			}
		}
		System.out.println("a7a");
		return true;
	}
}
