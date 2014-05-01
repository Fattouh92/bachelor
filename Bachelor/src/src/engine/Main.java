package src.engine;

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
			reader = new BufferedReader(new FileReader("lanes.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\s");
				lanes.add(new Lane(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
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
			reader = new BufferedReader(new FileReader("cars.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		line = null;
		try {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\s");
				if(lanes.get(Integer.parseInt(parts[0])).cars.isEmpty()) {
					lanes.get(Integer.parseInt(parts[0])).cars.add(
							new Car(Integer.parseInt(parts[2]), Integer.parseInt(parts[5]), 0, Integer.parseInt(parts[1]), 
									Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), 
									Integer.parseInt(parts[0]), true, 0, Integer.parseInt(parts[6]), Integer.parseInt(parts[2])));
				} else {
					lanes.get(Integer.parseInt(parts[0])).cars.add(
							new Car(Integer.parseInt(parts[2]), Integer.parseInt(parts[5]), 0, Integer.parseInt(parts[1]), 
									Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), 
									Integer.parseInt(parts[0]), true, 0, Integer.parseInt(parts[6]), 
									lanes.get(Integer.parseInt(parts[0])).cars.get(lanes.get(Integer.parseInt(parts[0])).car_counter-1).current_pos+Integer.parseInt(parts[2])));

				}
				lanes.get(Integer.parseInt(parts[0])).car_counter++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runSimulation() {
		for (int i = 0; i < lanes.size(); i++) {
			for (int j = 0; j < lanes.get(i).cars.size(); j++) {	
				if (j == 0) {
					lanes.get(i).cars.get(j).move(0, lanes.get(i).highest_speed);
				} else {
					lanes.get(i).cars.get(j).move(lanes.get(i).cars.get(j-1).current_pos, lanes.get(i).highest_speed);
				}
			}
		}
	}

	public void cross() {
		ArrayList<Integer> cars_to_move = new ArrayList<Integer>();
		ArrayList<Integer> joins_move = new ArrayList<Integer>();
		ArrayList<Integer> emergency = new ArrayList<Integer>();
		ArrayList<Integer> blocked_lanes = new ArrayList<Integer>();
		int i;
		for (i = 0; i < lanes.size(); i++) {
			if(!lanes.get(i).cars.isEmpty()) {
				if (lanes.get(i).has_emergency && lanes.get(i).cars.get(0).ready_to_join)
					emergency.add(new Integer(i));
			}
		}
		for (i = 0; i < emergency.size(); i++) {
			Car tempCar =lanes.get(emergency.get(i).intValue()).cars.get(0);
			int index = -1;
			for(int j = 0; j< joins.size(); j++) {
				if (joins.get(j).start == tempCar.current_direction && joins.get(j).end == tempCar.direction) {
					index = j;
					break;
				}
			}
			int highest_i = i;
			int highest = lanes.get(i).cars.get(0).priority;
			for (int k = 0; k < emergency.size(); k++) {
				if (k!=i) {
					if (joins.get(index).blocked_lanes.contains(lanes.get(k).cars.get(0).current_direction)) {
						if(highest < lanes.get(k).cars.get(0).priority) {
							highest = lanes.get(k).cars.get(0).priority;
							highest_i = k;
						}
					}
				}
			}
			if (!cars_to_move.contains(new Integer(highest_i))) {
				cars_to_move.add(new Integer(highest_i));
				joins_move.add(index);
			}
		}

		for(i = 0; i < joins_move.size(); i++) {
			for(int c = 0; c < this.joins.get(joins_move.get(i)).blocked_lanes.size(); c++) {
				if(!blocked_lanes.contains(this.joins.get(joins_move.get(i)).blocked_lanes.get(c)))
					blocked_lanes.add(this.joins.get(joins_move.get(i)).blocked_lanes.get(c));
			}
		}

		ArrayList<Integer> normal_cars = new ArrayList<Integer>();
		for(i = 0; i<lanes.size(); i++) {
			if(!lanes.get(i).cars.isEmpty()) {
				if (lanes.get(i).cars.get(0).ready_to_join && !lanes.get(i).has_emergency &&
						!blocked_lanes.contains(new Integer(i))) {
					normal_cars.add(new Integer(i));
				}	
			}
		}
		for (i = 0; i < normal_cars.size(); i++) {
			Car tempCar = lanes.get(normal_cars.get(i).intValue()).cars.get(0);
			int index = -1;
			for(int j = 0; j< joins.size(); j++) {
				if (joins.get(j).start == tempCar.current_direction && joins.get(j).end == tempCar.direction) {
					index = j;
				}
			}
			boolean wait_flag = false;
			int highest_i = i;
			int highest = lanes.get(i).cars.get(0).priority;
			int wait = -1;
			for (int k = 0; k < emergency.size(); k++) {
				if (k!=i) {
					if (lanes.get(k).cars.get(0).delay_count > 10 && wait < lanes.get(k).cars.get(0).delay_count) {
						highest_i = k;
						highest = lanes.get(k).cars.get(0).priority;
						wait = lanes.get(k).cars.get(0).delay_count;
						wait_flag = true;
					}
					if(highest < lanes.get(k).cars.get(0).priority && !wait_flag) {
						highest = lanes.get(k).cars.get(0).priority;
						highest_i = k;
					}
				}
			}
			if (!cars_to_move.contains(new Integer(highest_i))) {
				cars_to_move.add(new Integer(highest_i));
				joins_move.add(index);	
			}
		}
		
		for(i = 0; i<cars_to_move.size(); i++) {
			Car tempCar = lanes.get(cars_to_move.get(i)).cars.remove(0);
			lanes.get(tempCar.direction).addCar(tempCar);
		}

		/*int index_emergency = -1;
		for (int k = 0; k < lanes.size(); k++) {
			ArrayList<Integer> temp_lanes = joins.get(k).blocked_lanes;
			ArrayList<Car> temp_cars = new ArrayList<Car>();
			for (int l = 0; l < temp_lanes.size(); l++) {
				int counter = temp_lanes.get(l).intValue();
				if (lanes.get(counter).has_emergency)
					index_emergency = counter;
				if (lanes.get(counter).cars.size() != 0) {
					if (lanes.get(counter).cars.get(0).ready_to_join) {
						System.out.println("ready");
						temp_cars.add(lanes.get(counter).cars.get(0));
					}
				}
			}
			Car car_join;
			Car car_join2;
			int max = -1;
			int index = -1;
			if (index_emergency != -1) {
				car_join = temp_cars.get(index_emergency);
				car_join2 = lanes.get(car_join.current_direction).cars.remove(0);
				lanes.get(car_join2.direction).addCar(car_join2);
			} else {
				for (int o = 0; o < temp_cars.size(); o++) {
					if (temp_cars.get(o).priority > max) {
						index = o;
						max = temp_cars.get(o).priority;
					}
				}

				car_join = temp_cars.get(index);
				car_join2 = lanes.get(car_join.current_direction).cars.remove(0);
				lanes.get(car_join2.direction).addCar(car_join2);
			}
		}*/
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

	public static void main(String[]args) {
		Main main = new Main();
		main.read();
		main.runSimulation();
		main.cross();
	}
}