package src.engine;

import java.util.ArrayList;

public class Join {
	ArrayList<Integer> blocked_lanes;
	int start;
	int end;
	int duration;
	
	public Join(ArrayList<Integer> blocked_lanes, int start, int end,
			int duration) {
		super();
		this.blocked_lanes = blocked_lanes;
		this.start = start;
		this.end = end;
		this.duration = duration;
	}
	
}
