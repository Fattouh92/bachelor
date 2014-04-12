package src.engine;

public class Car {
	int direction;
	int speed;
	int type;
	int max_acc;
	int max_dec;
	int current_direction;
	boolean stopped;
	int delay_count;
	int priority;
	boolean ready_to_join;
	int current_pos;
	int car_size;
	
	public Car(int car_size, int direction, int speed, int type, int max_acc, int max_dec, 
			int current_direction, boolean stopped, int delay_count, int priority,
			int current_po) {
		super();
		this.direction = direction;
		this.speed = speed;
		this.type = type;
		this.max_acc = max_acc;
		this.max_dec = max_dec;
		this.current_direction = current_direction;
		this.stopped = stopped;
		this.delay_count = delay_count;
		this.priority = priority;
		this.current_pos = current_po;
		this.car_size = car_size;
	}
	
	public void move(int movement_distance, int high_speed) {
		int distance = this.current_pos - movement_distance - this.car_size ;
		System.out.println("dsfd"+ this.current_pos);
		if (distance > this.speed) {
			int acc = this.max_dec - this.speed;
			if (acc > distance) 
				acc = distance;
			if ( acc > max_acc)
				acc = max_acc;
			this.speed += acc;
			if (speed > high_speed)
				speed = high_speed;
			this.current_pos = this.current_pos + (- this.speed);
			System.out.println("dl"+ current_pos);
		} else {
			this.speed -= max_dec;
			if (speed<0) {
				this.speed = 0;
				stopped = true;
			}
			this.current_pos = this.current_pos + (- this.speed);
			if ((current_pos - car_size) == 0)
				ready_to_join = true;
		}
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMax_acc() {
		return max_acc;
	}

	public void setMax_acc(int max_acc) {
		this.max_acc = max_acc;
	}

	public int getMax_dec() {
		return max_dec;
	}

	public void setMax_dec(int max_dec) {
		this.max_dec = max_dec;
	}

	public int getCurrent_direction() {
		return current_direction;
	}

	public void setCurrent_direction(int current_direction) {
		this.current_direction = current_direction;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public int getDelay_count() {
		return delay_count;
	}

	public void setDelay_count(int delay_count) {
		this.delay_count = delay_count;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isReady_to_join() {
		return ready_to_join;
	}

	public void setReady_to_join(boolean ready_to_join) {
		this.ready_to_join = ready_to_join;
	}

	public int getCurrent_pos() {
		return current_pos;
	}

	public void setCurrent_pos(int current_pos) {
		this.current_pos = current_pos;
	}

	public int getCar_size() {
		return car_size;
	}

	public void setCar_size(int car_size) {
		this.car_size = car_size;
	}
}