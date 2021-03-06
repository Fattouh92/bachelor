package engine;

import java.util.ArrayList;

public class Join {
	ArrayList<Integer[]> blocked_lanes;
	int start;
	int end;
	int duration;
	int number_of_cars = 0;
	boolean rotate;
	boolean increase;
	
	public Join(ArrayList<Integer[]> blocked_lanes, int start, int end, int rotate, int increase, int duration) {
		super();
		this.blocked_lanes = blocked_lanes;
		this.start = start;
		this.end = end;
		this.duration = duration;
		if (rotate == 1) {
			this.rotate = true;
		} else {
			this.rotate = false;
		}
		if (increase == 1) {
			this.increase = true;
		} else {
			this.increase = false;
		}
	}
	
	/*public boolean canPass(int lane) {
		for(int i = 0; i<this.blocked_lanes.size(); i++) {
			if (lane == this.blocked_lanes.get(i)[0].intValue()) {
				return false;
			}
		}
		return true;
	}*/

	public ArrayList<Integer[]> getBlocked_lanes() {
		return blocked_lanes;
	}

	public void setBlocked_lanes(ArrayList<Integer[]> blocked_lanes) {
		this.blocked_lanes = blocked_lanes;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}