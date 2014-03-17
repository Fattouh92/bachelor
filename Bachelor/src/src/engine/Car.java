package src.engine;

public class Car {
	int direction;
	int speed;
	int type;
	int current_direction;
	boolean stopped;
	int delay_count;
	int priority;
	
	public Car(int direction, int speed, int type, int current_direction,
			boolean stopped, int delay_count, int priority) {
		super();
		this.direction = direction;
		this.speed = speed;
		this.type = type;
		this.current_direction = current_direction;
		this.stopped = stopped;
		this.delay_count = delay_count;
		this.priority = priority;
	}
}
