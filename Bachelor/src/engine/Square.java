package engine;

import java.util.ArrayList;

public class Square {
	ArrayList<Integer> lanes = new ArrayList<Integer>();
	//int lane_selected = -1;
	//int number_cars = 0;
	//int rotate_lane = -1;
	//ArrayList<Integer> lanes_inside = new ArrayList<Integer>();

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

	/*public boolean can_rotate (int lane) {
		for (int i = 0; i < lanes.size(); i++) {
			if (lanes_inside.contains(lanes.get(i)) && lanes.get(i).intValue() != lane) {
				return false;
			}
		}
		return true;
	}*/

	public int max_delay_count(ArrayList<Lane> lanes) {
		int max = -1;
		int index = -1;
		for (int i = 0; i < lanes.size(); i++) {
			//int temp = lanes.get(i).car_waiting();
			if(!lanes.get(i).cars.isEmpty() && this.lanes.contains(new Integer(lanes.get(i).number))) {
				if (lanes.get(i).cars.get(0).delay_count > max) {
					max = lanes.get(i).cars.get(0).delay_count;
					index = i;
				}
			}
		}
		if ( max >= 3) {
			return index;
		} else {
			return -1;
		}
	}

	public int max_energy (ArrayList<Lane> lanes) {
		int max = -1;
		int index = -1;
		for (int i = 0; i < lanes.size(); i++) {
			if(!lanes.get(i).cars.isEmpty() && this.lanes.contains(new Integer(lanes.get(i).number))) {
				int weight = 0;
				if (lanes.get(i).cars.get(0).type == 0) {
					weight = 1000;
				} else {
					weight = 4000;
				}
				int speed = 0;
				if (lanes.get(i).horizontal) {
					if (lanes.get(i).right_start) {
						speed = (int) (lanes.get(i).cars.get(0).ballXVel * weight);
					} else {
						speed = (int) (-lanes.get(i).cars.get(0).ballXVel * weight);
					}
				} else {
					if (lanes.get(i).right_start) {
						speed = (int) (lanes.get(i).cars.get(0).ballYVel * weight);
					} else {
						speed = (int) (-lanes.get(i).cars.get(0).ballYVel * weight);
					}

				}
				if (speed > max) {
					max = speed;
					index = i;
				}
			}
		}
		//System.out.println("sdsdsds "+index);
		return index;
	}

	public int occupied_lane(ArrayList<Lane> lanes) {
		double max = 0;
		int index = -1;
		for (int i = 0; i < lanes.size(); i++) {
			if (this.lanes.contains(new Integer(lanes.get(i).number))) {
				if (lanes.get(i).occupied() > max) {
					max = lanes.get(i).occupied();
					index = i;
				}
			}
		}
		if (max >= 50) {
			return index;
		} else {
			return -1;
		}
	}

	public int selected_lane (ArrayList<Lane> lanes) {
		int emergency = this.emergency_lane(lanes);
		int occupied_lane = this.occupied_lane(lanes);
		int delay_lane = this.max_delay_count(lanes);
		int energy_lane = this.max_energy(lanes);
		if (emergency != -1) {
			return emergency;
		} else if (delay_lane != -1) {
			System.out.println("delay "+ delay_lane);
			return delay_lane;
		} else if (occupied_lane != -1) {
			System.out.println("occupied "+ occupied_lane);
			return occupied_lane;
		} else {
			System.out.println("energy "+ energy_lane);
			return energy_lane;
		}
	}

	public void increase_delay_count(ArrayList<Lane> lanes) {
		for (int i = 0; i < lanes.size(); i++) {
			if (this.lanes.contains(new Integer(lanes.get(i).number))) {
				for (int j = 0; j < lanes.get(i).cars.size(); j++) {
					if (lanes.get(i).cars.get(j).waiting) {
						lanes.get(i).cars.get(j).delay_count++;
					}
				}
			}
		}
	}
}
