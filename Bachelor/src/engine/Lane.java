package engine;

import java.util.ArrayList;

public class Lane {
	ArrayList<Car> cars = new ArrayList<Car>();
	boolean horizontal;
	int x, y;
	int number;
	int distance;
	boolean right_start;
	boolean emergency;
	
	public Lane(int x, int y, int number, int distance) {
		super();
		this.x = x;
		this.y = y;
		this.number = number;
		this.distance = distance;
	}
	
	public void addCar(Car car) {
		cars.add(car);
		if (car.emergency)
			this.emergency = true;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isRight_start() {
		return right_start;
	}

	public void setRight_start(boolean right_start) {
		this.right_start = right_start;
	}
}
