package engine;

public class Rule {
	boolean emergency;
	int join_type, car_type;
	int compare_value;
	int method_called;
	public Rule(int emergency, int join_type, int car_type,
			int compare_value, int method_called) {
		if (emergency == 0) {
			this.emergency = false;
		} else {
			this.emergency = true;
		}
		this.join_type = join_type;
		this.car_type = car_type;
		this.compare_value = compare_value;
		this.method_called = method_called;
	}	
}


