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
	int endX, endY;
	int limit;

	public Lane(int x, int y, int number, int distance, int hor, int right, int limit) {
		super();
		this.x = x;
		this.y = y;
		this.number = number;
		this.distance = distance;
		this.limit = limit;
		if (hor == 0) {
			this.horizontal = true;
		} else {
			this.horizontal = false;
		}
		if (right == 1) {
			this.right_start = true;
		} else {
			this.right_start = false;
		}
		if(right_start) {
			if (this.horizontal) {
				this.endX = this.x + distance;
				this.endY = this.y;
			} else {
				this.endX = this.x;
				this.endY = this.y + distance;
			}
		} else {
			if (this.horizontal) {
				this.endX = this.x;
				this.endY = this.y;
			} else {
				this.endX = this.x;
				this.endY = this.y;
			}
		}
	}

	public int emergency_car() {
		for(int i = 0; i<cars.size(); i++) {
			if (cars.get(i).emergency) {
				return cars.get(i).directions.get(0).intValue();
			}
		}
		return -1;
	}

	public void addCar(Car car) {
		if (car.emergency)
			this.emergency = true;
		if(horizontal) {
			if (right_start) {
				car.ballXVel = 1;
			} else {
				car.ballXVel = -1;
			}
		} else {
			if (right_start) {
				car.ballYVel = 1;
			} else {
				car.ballYVel = -1;
			}
		}
		cars.add(car);
	}

	public Car removeCar(Car c) {
		boolean flag = false;
		if (cars.get(cars.indexOf(c)).emergency) {
			for (int i = 0; i < cars.size(); i++) {
				if (cars.get(i).emergency && !cars.get(i).equals(c)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				this.emergency = false;
			}
		}
		return cars.remove(cars.indexOf(c));
	}

	public int occupied() {
		int car_size = 0;
		for(int i = 0; i < cars.size(); i++) {
			if (this.horizontal) {
				car_size += cars.get(i).ballWidth;
			} else {
				car_size += cars.get(i).ballHeight;
			}
		}
		return car_size*100/this.distance;
	}

	public int car_waiting() {
		for (int i = 0; i < cars.size(); i++) {
			if (!cars.get(i).join) {
				return i;
			}
		}
		return -1;
	}

	public void updateEnds(int x, int y) {
		this.endX -= x;
		this.endY -= y;
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
