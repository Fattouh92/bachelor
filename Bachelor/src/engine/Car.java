package engine;

import java.util.ArrayList;

public class Car {
	int lane;
	float ballX, ballY, lastBallX, lastBallY;
	int ballWidth, ballHeight;
	float ballXVel, ballYVel;
	float ballSpeed;
	boolean join = false;
	int delay_count;
	int priority;
	boolean emergency;
	int direction;

	int lastDrawX, lastDrawY;

	public Car(int lane, int width, int height, int priority, int emergency) {
		this.lane = lane;
		ballWidth = width;
		ballHeight = height;
		ballSpeed = 2;
		this.priority = priority;
		if (emergency == 1) {
			this.emergency = true;
		} else {
			this.emergency = false;
		}
		//ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
		//ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
	}

	public void update2(int min, int max, boolean horizontal, boolean right_start,
			ArrayList<Lane> lanes, ArrayList<Join> joins) {
		lastBallX = ballX;
		lastBallY = ballY;
		if(horizontal) {
			this.ballX += this.ballXVel;
			if (this.ballX + this.ballWidth >= max && right_start) {
				this.ballXVel = 0;
				join = true;
			}
			if (this.ballX <= min && !right_start) {
				this.ballXVel = 0;
				join = true;
			}
		} else {
			this.ballY += this.ballYVel;
			if (this.ballY + this.ballHeight >= max && right_start) {
				this.ballYVel = 0;
				join = true;
			}
			if (this.ballY <= min && !right_start) {
				this.ballYVel = 0;
				join = true;
			}
		}
		if (join) {
			int index;
			for(int j = 0; j< joins.size(); j++) {
				if (joins.get(j).start == this.lane && joins.get(j).end == this.direction) {
					index = j;
					break;
				}
			}
		}
	}

	public float getBallX() {
		return ballX;
	}

	public void setBallX(float ballX) {
		this.ballX = ballX;
	}

	public float getBallY() {
		return ballY;
	}

	public void setBallY(float ballY) {
		this.ballY = ballY;
	}

	public float getLastBallX() {
		return lastBallX;
	}

	public void setLastBallX(float lastBallX) {
		this.lastBallX = lastBallX;
	}

	public float getLastBallY() {
		return lastBallY;
	}

	public void setLastBallY(float lastBallY) {
		this.lastBallY = lastBallY;
	}

	public int getBallWidth() {
		return ballWidth;
	}

	public void setBallWidth(int ballWidth) {
		this.ballWidth = ballWidth;
	}

	public int getBallHeight() {
		return ballHeight;
	}

	public void setBallHeight(int ballHeight) {
		this.ballHeight = ballHeight;
	}

	public float getBallXVel() {
		return ballXVel;
	}

	public void setBallXVel(float ballXVel) {
		this.ballXVel = ballXVel;
	}

	public float getBallYVel() {
		return ballYVel;
	}

	public void setBallYVel(float ballYVel) {
		this.ballYVel = ballYVel;
	}

	public float getBallSpeed() {
		return ballSpeed;
	}

	public void setBallSpeed(float ballSpeed) {
		this.ballSpeed = ballSpeed;
	}

	public int getLastDrawX() {
		return lastDrawX;
	}

	public void setLastDrawX(int lastDrawX) {
		this.lastDrawX = lastDrawX;
	}

	public int getLastDrawY() {
		return lastDrawY;
	}

	public void setLastDrawY(int lastDrawY) {
		this.lastDrawY = lastDrawY;
	}

}