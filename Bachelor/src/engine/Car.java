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
	boolean arrived;
	Car blocked_car;

	int lastDrawX, lastDrawY;

	public Car(int lane, int width, int height, int priority, int emergency, int direction) {
		this.lane = lane;
		ballWidth = width;
		ballHeight = height;
		ballSpeed = 2;
		this.priority = priority;
		this.direction = direction;
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
		if(arrived) {

		}
		if(this.ballX == lanes.get(direction).x && this.ballY == lanes.get(direction).y) {
			this.ballXVel = this.ballYVel = 0;
			this.join = false;
			this.lane = direction;
			this.arrived = true;
		}
		lastBallX = ballX;
		lastBallY = ballY;
		if(horizontal) {
			this.ballX += this.ballXVel;
			if (this.ballX + this.ballWidth >= max && right_start) {
				join = true;
			}
			if (this.ballX <= min && !right_start) {
				join = true;
			}
		} else {
			this.ballY += this.ballYVel;
			if (this.ballY + this.ballHeight >= max && right_start) {
				join = true;
			}
			if (this.ballY <= min && !right_start) {
				join = true;
			}
		}
		if (join) {
			int index = 0;
			for(int j = 0; j< joins.size(); j++) {
				if (joins.get(j).start == this.lane && joins.get(j).end == this.direction) {
					index = j;
					break;
				}
			}
			ArrayList<Integer> blocked_lanes = joins.get(index).blocked_lanes;
			for(int k = 0; k < blocked_lanes.size(); k++) {
				if (!lanes.get(blocked_lanes.get(k)).cars.isEmpty()) {
					Car temp_car = lanes.get(blocked_lanes.get(k)).cars.get(0);
					if (horizontal) {
						if (right_start) {
							if(this.blocked_car != null) {
								if(this.ballX > this.blocked_car.ballX + this.blocked_car.ballWidth ) {
									Lane temp_blocked_lane = lanes.get(this.blocked_car.lane);
									System.out.println(temp_blocked_lane.number);
									if (temp_blocked_lane.horizontal) {
										if (temp_blocked_lane.right_start) {
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = 1;
										}else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = -1;
										}
									} else {
										if (temp_blocked_lane.right_start) {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = 1;
										} else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = -1;
										}
									}
								}
								this.blocked_car = null;
							}
							//gayalha mn ta7t aw fo2
							//bsa sa7 l fo2
							if (this.ballWidth + this.ballX >= temp_car.ballX && this.ballY+this.ballHeight >= temp_car.ballY && this.ballY <= temp_car.ballY+temp_car.ballHeight && this.priority <= temp_car.priority) {
								if (this.ballX < temp_car.ballX + temp_car.ballWidth && this.ballWidth + this.ballX >= temp_car.ballX) {
									temp_car.ballYVel = 0;
									this.blocked_car = temp_car;
									break;
								} else {
									if (this.ballX < temp_car.ballX + temp_car.ballWidth) {
										this.ballXVel = 0;
										break;
									}
								}
							} else {
								this.ballXVel = 1;
							}
						} else {
							if(this.blocked_car != null) {
								if(this.ballX + this.ballWidth > this.blocked_car.ballX) {
									Lane temp_blocked_lane = lanes.get(this.blocked_car.lane);
									System.out.println(temp_blocked_lane.number);
									if (temp_blocked_lane.horizontal) {
										if (temp_blocked_lane.right_start) {
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = 1;
										}else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = -1;
										}
									} else {
										if (temp_blocked_lane.right_start) {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = 1;
										} else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = -1;
										}
									}
								}
								this.blocked_car = null;
							}
							//sa7 llely mn ta7t
							if (this.ballX <= temp_car.ballX+temp_car.ballWidth && this.ballY+this.ballHeight >= temp_car.ballY && this.ballY <= temp_car.ballY+temp_car.ballHeight && this.priority <= temp_car.priority) {
								if (this.ballX < temp_car.ballX+temp_car.ballWidth && this.ballX + this.ballWidth >= temp_car.ballX ) {
									temp_car.ballYVel = 0;
									this.blocked_car = temp_car;
									break;
								} else {
									if (this.ballX + this.ballHeight > temp_car.ballX) {
										this.ballXVel = 0;
										break;
									}
								}
							} else {
								this.ballXVel = -1;
							}
						}
					} else {
						if (right_start) {
							if(this.blocked_car != null) {
								if(this.ballY < this.blocked_car.ballY + this.blocked_car.ballHeight ) {
									Lane temp_blocked_lane = lanes.get(this.blocked_car.lane);
									System.out.println(temp_blocked_lane.number);
									if (temp_blocked_lane.horizontal) {
										if (temp_blocked_lane.right_start) {
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = 1;
										}else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = -1;
										}
									} else {
										if (temp_blocked_lane.right_start) {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = 1;
										} else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = -1;
										}
									}
								}
								this.blocked_car = null;
							}
							if (this.ballHeight + this.ballY >= temp_car.ballY && this.ballX+this.ballWidth >= temp_car.ballX && this.ballX <= temp_car.ballX+temp_car.ballWidth && this.priority < temp_car.priority) {
								if (this.ballHeight + this.ballY > temp_car.ballY && this.ballY <= temp_car.ballY + temp_car.ballHeight) {
									temp_car.ballXVel = 0;
									this.blocked_car = temp_car;
									break;
								} else {
									if (this.ballY < temp_car.ballY+temp_car.ballHeight) {
										this.ballYVel = 0;
										break;
									}
								}
							} else {
								this.ballYVel = 1;
							}
						} else {
							if(this.blocked_car != null) {
								if(this.ballY + this.ballHeight > this.blocked_car.ballY ) {
									Lane temp_blocked_lane = lanes.get(this.blocked_car.lane);
									System.out.println(temp_blocked_lane.number);
									if (temp_blocked_lane.horizontal) {
										if (temp_blocked_lane.right_start) {
											System.out.println("move");
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = 1;
										}else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballXVel = -1;
										}
									} else {
										if (temp_blocked_lane.right_start) {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = 1;
										} else {
											lanes.get(this.blocked_car.lane).cars.get(0).ballYVel = -1;
										}
									}
								}
								this.blocked_car = null;
							}
							if (this.ballY <= temp_car.ballY + temp_car.ballHeight && this.ballX+this.ballWidth >= temp_car.ballX && this.ballX <= temp_car.ballX+temp_car.ballWidth && this.priority < temp_car.priority) {
								if (this.ballY < temp_car.ballY + temp_car.ballHeight && this.ballY + this.ballHeight >= temp_car.ballY) {
									temp_car.ballXVel = 0;
									this.blocked_car = temp_car;
									break;
								} else {
									if(this.ballY+this.ballHeight > temp_car.ballY) {
										this.ballYVel = 0;
										break;
									}
								}
							} else {
								this.ballYVel = -1;
							}
							//this.ballYVel = -1;
						}
					}
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