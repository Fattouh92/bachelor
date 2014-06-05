package engine;

import java.util.ArrayList;

public class Square {
	ArrayList<Integer> lanes = new ArrayList<Integer>();
	int number_cars = 0;
	boolean rotate = false;
	
	public Square(ArrayList<Integer> lanes) {
		this.lanes = lanes;
	}	
}
