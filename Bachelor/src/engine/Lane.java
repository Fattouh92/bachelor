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
	
	public Lane(int x, int y, int number, int distance, int hor, int right) {
		super();
		this.x = x;
		this.y = y;
		this.number = number;
		this.distance = distance;
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
		cars.add(car);
		if (car.emergency)
			this.emergency = true;
	}
	
	public Car removeCar() {
		boolean flag = false;
		if (cars.get(0).emergency) {
			for (int i = 1; i < cars.size(); i++) {
				if (cars.get(i).emergency) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				this.emergency = false;
			}
		}
		return cars.remove(0);
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
