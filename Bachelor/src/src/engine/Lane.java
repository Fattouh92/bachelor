package src.engine;

import java.util.ArrayList;

public class Lane {
	ArrayList<Car> cars;
	int number;
	int distance;
	int per_left;
	int per_right;
	boolean has_emergency;
	int car_counter = 0;
	int highest_speed;
	
	public Lane(int n, int d, int p_l, int p_r, int h_s) {
		cars = new ArrayList<Car>();
		number = n;
		distance = d;
		has_emergency = false;
		this.per_left = p_l;
		this.per_right = p_r;
		this.highest_speed = h_s;
	}
	
	public void addCar(Car car) {
		cars.add(car);
		car.current_direction = number;
		car.current_pos = distance;
		car.ready_to_join = false;
		car.stopped = false;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean isHas_emergency() {
		return has_emergency;
	}

	public void setHas_emergency(boolean has_emergency) {
		this.has_emergency = has_emergency;
	}
}
