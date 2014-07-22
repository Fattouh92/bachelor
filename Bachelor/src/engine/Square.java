package engine;

import java.util.ArrayList;

public class Square {
	ArrayList<Integer> lanes = new ArrayList<Integer>();
	int selected_lane = -1;
	//int number_cars = 0;
	//int rotate_lane = -1;
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

	/*public boolean can_rotate (int lane) {
		for (int i = 0; i < lanes.size(); i++) {
			if (lanes_inside.contains(lanes.get(i)) && lanes.get(i).intValue() != lane) {
				return false;
			}
		}
		return true;
	}*/

	public int max_delay_count(ArrayList<Lane> lanes, int value) {
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
		if ( max >= value) {
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
				for (int j = 0; j < lanes.get(i).cars.size(); j++) {
					int weight = 0;
					if (lanes.get(i).cars.get(j).type == 0) {
						weight = 1000;
					} else {
						weight = 4000;
					}
					int speed = 0;
					if (lanes.get(i).horizontal) {
						if (lanes.get(i).right_start) {
							speed = (int) (lanes.get(i).cars.get(j).ballXVel * weight);
						} else {
							speed = (int) (-lanes.get(i).cars.get(j).ballXVel * weight);
						}
					} else {
						if (lanes.get(i).right_start) {
							speed = (int) (lanes.get(i).cars.get(j).ballYVel * weight);
						} else {
							speed = (int) (-lanes.get(i).cars.get(j).ballYVel * weight);
						}

					}
					if (speed > max) {
						max = speed;
						index = i;
					}
				}
			}
		}
		//System.out.println("sdsdsds "+index);
		return index;
	}

	public int occupied_lane(ArrayList<Lane> lanes, int value) {
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
		if (max >= value) {
			return index;
		} else {
			return -1;
		}
	}

	public int selected_lane (ArrayList<Lane> lanes, ArrayList<Rule> rules) {
		int emergency = 0, occupied_lane = 0, delay_lane = 0, energy_lane = 0;
		int rule_index = -1;
		for (int p = 0; p < rules.size(); p++) {
			Rule temp_rule = rules.get(p);
			if(temp_rule.emergency == 1) {
				switch (temp_rule.method_called) {
				case 1: emergency = this.emergency_lane(lanes);
				return emergency;
				case -1: emergency = -1;
				case 3: emergency = this.max_energy(lanes);
				return emergency;
				}
			} else {
				if(temp_rule.emergency == 2) {
					switch (temp_rule.method_called) {
					case -1: occupied_lane = -1;
					case 4: occupied_lane = this.occupied_lane(lanes, temp_rule.compare_value);
					return occupied_lane;
					}
				} else {
					if (temp_rule.car_type == -1) {
						if (temp_rule.join_type != -1) {
							switch (temp_rule.method_called) {
							case 1: energy_lane = this.max_energy(lanes);
							return energy_lane;
							case 3:	energy_lane = this.max_energy(lanes);
							return energy_lane;
							}
						} else {

						}
					} else {
						if (temp_rule.join_type != -1) {

						} else {

						}
					}
				}
			}
		}
		return -1;
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
