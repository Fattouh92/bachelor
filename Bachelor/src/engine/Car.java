package engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

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
	ArrayList<Integer> directions;
	boolean arrived;
	Car blocked_car;
	double angle = 0;
	Color color = null;
	boolean rotated = false;
	boolean rotate;
	boolean stop_rotate = false;

	int lastDrawX, lastDrawY;

	public Car(int lane, int width, int height, int priority, int emergency, ArrayList<Integer> directions) {
		this.lane = lane;
		ballWidth = width;
		ballHeight = height;
		ballSpeed = 2;
		this.priority = priority;
		this.directions = directions;
		if (emergency == 1) {
			this.emergency = true;
		} else {
			this.emergency = false;
		}
		this.color = getRandomColor();
	}

	private Random randomNumber = new Random();

	private Color getRandomColor() {
		return new Color(0, randomNumber.nextFloat(), randomNumber.nextFloat());
	}

	public void update2(int min, int max, boolean horizontal, boolean right_start,
			ArrayList<Lane> lanes, ArrayList<Join> joins, ArrayList<Square> squares) {
		System.out.println(this.lane+" "+this.directions);
		if(arrived) {

		} else {
			lastBallX = ballX;
			lastBallY = ballY;

			Square my_square = null;
			for(int r = 0; r < squares.size(); r++) {
				for (int w = 0; w < squares.get(r).lanes.size(); w++) {
					if (squares.get(r).lanes.get(w).intValue() == this.lane) {
						my_square = squares.get(r);
						break;
					}
				}
			}

			rotate = false;
			boolean increase = false;
			int index = 0;
			for(int j = 0; j< joins.size(); j++) {
				if (joins.get(j).start == this.lane && joins.get(j).end == this.directions.get(0).intValue()) {
					index = j;
					if (joins.get(j).rotate) {
						rotate = true;
						if (joins.get(j).increase) {
							increase = true;
						}
					}
					break;
				}
			}

			if(lanes.get(this.lane).cars.size()>1) {
				if(horizontal) {
					if (right_start) {

					} else {

					}
				} else {
					if (right_start) {

					} else {

					}
				}
			}

			//for leaving join
			if(!this.directions.isEmpty()) {
				if (rotate) {
					if (lanes.get(directions.get(0)).horizontal) {
						if (lanes.get(directions.get(0)).right_start) {
							if(this.ballY == lanes.get(directions.get(0)).y) {
								rotated = false;
								this.ballYVel = 0;
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									//my_square.rotate = false;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						} else {
							if(this.ballY == lanes.get(directions.get(0)).y) {
								rotated = false;
								this.ballYVel = 0;
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									//my_square.rotate = false;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						}
					} else {
						if (lanes.get(directions.get(0)).right_start) {
							if (this.ballX == lanes.get(directions.get(0)).x) {
								rotated = false;
								this.ballXVel = 0;
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									//my_square.rotate = false;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						} else {
							if (this.ballX == lanes.get(directions.get(0)).x) {
								rotated = false;
								this.ballXVel = 0;
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									//my_square.rotate = false;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						}
					}
				} else {
					if (lanes.get(directions.get(0)).horizontal) {
						if (lanes.get(directions.get(0)).right_start) {
							if(this.ballX == lanes.get(directions.get(0)).x && this.ballY == lanes.get(directions.get(0)).y) {
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						} else {
							if(this.ballX+this.ballWidth == lanes.get(directions.get(0)).x + lanes.get(directions.get(0)).distance && this.ballY == lanes.get(directions.get(0)).y) {
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						}
					} else {
						if (lanes.get(directions.get(0)).right_start) {
							if(this.ballX == lanes.get(directions.get(0)).x && this.ballY == lanes.get(directions.get(0)).y) {
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						} else {
							if(this.ballX == lanes.get(directions.get(0)).x && this.ballY+this.ballHeight == lanes.get(directions.get(0)).y+lanes.get(directions.get(0)).distance) {
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar());
								this.lane = this.directions.remove(0);
							}
						}
					}
				}

				//for arriving and stopping
			} else {
				System.out.println("a7a bgad");
				if (horizontal) {
					if (right_start) {
						if (this.ballX + this.ballWidth == lanes.get(this.lane).endX && this.ballY == lanes.get(this.lane).endY) {
							this.ballXVel = this.ballYVel = 0;
							this.arrived = true;
							lanes.get(this.lane).updateEnds(this.ballWidth, 0);
						}
					}else {
						if (this.ballX == lanes.get(this.lane).endX && this.ballY == lanes.get(this.lane).endY) {
							this.ballXVel = this.ballYVel = 0;
							this.arrived = true;
							lanes.get(this.lane).updateEnds(-this.ballWidth, 0);
						}
					}
				} else {
					if (right_start) {
						System.out.println("o7a");
						if (this.ballX == lanes.get(this.lane).endX && this.ballY + this.ballHeight  == lanes.get(this.lane).endY) {
							System.out.println("o7a2");
							this.ballXVel = this.ballYVel = 0;
							this.arrived = true;
							lanes.get(this.lane).updateEnds(0, this.ballHeight);
						}
					}else {
						if (this.ballX == lanes.get(this.lane).endX && this.ballY== lanes.get(this.lane).endY) {
							this.ballXVel = this.ballYVel = 0;
							this.arrived = true;
							lanes.get(this.lane).updateEnds(0, -this.ballHeight);
						}
					}
				}
			}

			this.ballX += this.ballXVel;
			this.ballY += this.ballYVel;

			if(my_square != null) {
				int emergency = my_square.emergency_lane(lanes);
				//if(this.lane == 0 || this.lane == 2 || this.lane == 4 || this.lane == 6)
				//System.out.println(emergency);
				if(horizontal) {
					if (this.ballX + this.ballWidth == max && right_start) {
						if (my_square.number_cars < 3 && (emergency == -1 || emergency == this.lane)) {
							join = true;
							my_square.number_cars++;
						}
						else
							this.ballXVel = 0;
					}
					if (this.ballX == min && !right_start) {
						if (my_square.number_cars < 3  && (emergency == -1 || emergency == this.lane)) {
							join = true;
							my_square.number_cars++;
						}
						else
							this.ballXVel = 0;
					}
				} else {
					if (this.ballY + this.ballHeight == max && right_start ) {
						if (my_square.number_cars < 3  && (emergency == -1 || emergency == this.lane)) {
							join = true;
							my_square.number_cars++;
						}
						else
							this.ballYVel = 0;
					}
					if (this.ballY == min && !right_start) {
						if (my_square.number_cars < 3 && (emergency == -1 || emergency == this.lane)) {
							join = true;
							my_square.number_cars++;
						}
						else
							this.ballYVel = 0;
					}
				}
			}
			if (join) {
				if(rotate) {
					//my_square.rotate = true;
					if(increase) {
						if(horizontal) {
							if(right_start) {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle += 3;
								if (this.angle == 90) {
									rotated = true;
									angle = 0;
								}
							} else {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle -= 1.5;
								if (this.angle == -90) {
									rotated = true;
									angle = 0;
								}
							}
						} else {
							if(right_start) {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle -= 1.5;
								if (this.angle == -90) {
									rotated = true;
									angle = 0;
								}
							} else {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle += 3;
								if (this.angle == 90) {
									rotated = true;
									angle = 0;
								}
							}
						}
					} else {
						if(horizontal) {
							if(right_start) {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle -= 1.5;
								if (this.angle == -90) {
									rotated = true;
									angle = 0;
								}
							} else {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle += 3;
								if (this.angle == 90) {
									rotated = true;
									angle = 0;
								}
							}
						} else {
							if(right_start) {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle += 3;
								if (this.angle == 90) {
									rotated = true;
									angle = 0;
								}
							} else {
								if(!this.rotated)
									if (!this.stop_rotate)
										this.angle -= 1.5;
								if (this.angle == -90) {
									rotated = true;
									angle = 0;
								}
							}
						}
					}
				}
				if(horizontal) {
					if(right_start) {
						this.ballXVel = 1;
						if (rotate) {
							if (increase) {
								System.out.println("omar");
								this.ballYVel = 1;
							}
							else {
								this.ballYVel = -1;
							}
						}
					} else {
						this.ballXVel = -1;
						if (rotate) {
							if (increase) {
								this.ballYVel = 1;
							}
							else {
								this.ballYVel = -1;
							}
						}
					}
				} else {
					if(right_start) {
						this.ballYVel = 1;
						if (rotate) {
							if (increase) {
								this.ballXVel = 1;
							}
							else {
								this.ballXVel = -1;
							}
						}
					} else {
						this.ballYVel = -1;
						if (rotate) {
							if (increase) {
								this.ballXVel = 1;
							}
							else {
								this.ballXVel = -1;
							}
						}
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
										lanes.get(this.blocked_car.lane).cars.get(0).stop_rotate = false;
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
								if (!this.emergency && this.ballWidth + this.ballX >= temp_car.ballX && this.ballY+this.ballHeight >= temp_car.ballY && this.ballY <= temp_car.ballY+temp_car.ballHeight && this.priority <= temp_car.priority) {
									if (this.ballX <= temp_car.ballX + temp_car.ballWidth && this.ballWidth + this.ballX > temp_car.ballX) {
										temp_car.ballYVel = temp_car.ballXVel = 0;
										temp_car.stop_rotate = true;
										this.blocked_car = temp_car;
										break;
									} else {
										if (this.ballX < temp_car.ballX + temp_car.ballWidth) {
											this.ballXVel = this.ballYVel = 0;
											break;
										}
									}
								} else {
									this.ballXVel = 1;
									if (rotate) {
										if (increase) {
											this.ballYVel = 1;
										}
										else {
											this.ballYVel = -1;
										}
									}
								}
							} else {
								if(this.blocked_car != null) {
									if(this.ballX + this.ballWidth < this.blocked_car.ballX) {
										Lane temp_blocked_lane = lanes.get(this.blocked_car.lane);
										lanes.get(this.blocked_car.lane).cars.get(0).stop_rotate = false;
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
								if (!this.emergency && this.ballX <= temp_car.ballX+temp_car.ballWidth && this.ballY+this.ballHeight >= temp_car.ballY && this.ballY <= temp_car.ballY+temp_car.ballHeight && this.priority <= temp_car.priority) {
									if (this.ballX < temp_car.ballX+temp_car.ballWidth && this.ballX + this.ballWidth >= temp_car.ballX ) {
										temp_car.ballYVel = temp_car.ballXVel = 0;
										temp_car.stop_rotate = true;
										this.blocked_car = temp_car;
										System.out.println(this.lane+" "+ temp_car.lane);
										break;
									} else {
										if (this.ballX + this.ballHeight > temp_car.ballX) {
											this.ballXVel= this.ballYVel = 0;
											break;
										}
									}
								} else {
									this.ballXVel = -1;
									if (rotate) {
										if (increase) {
											this.ballYVel = 1;
										}
										else {
											this.ballYVel = -1;
										}
									}
								}
							}
						} else {
							if (right_start) {
								if(this.blocked_car != null) {
									if(this.ballY < this.blocked_car.ballY + this.blocked_car.ballHeight ) {
										Lane temp_blocked_lane = lanes.get(this.blocked_car.lane);
										lanes.get(this.blocked_car.lane).cars.get(0).stop_rotate = false;
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
								if (!this.emergency && this.ballHeight + this.ballY >= temp_car.ballY && this.ballX+this.ballWidth >= temp_car.ballX && this.ballX <= temp_car.ballX+temp_car.ballWidth && this.priority < temp_car.priority) {
									if (this.ballHeight + this.ballY > temp_car.ballY && this.ballY <= temp_car.ballY + temp_car.ballHeight && !temp_car.emergency) {
										temp_car.ballXVel = temp_car.ballYVel = 0;
										temp_car.stop_rotate = true;
										this.blocked_car = temp_car;
										break;
									} else {
										if (this.ballY < temp_car.ballY+temp_car.ballHeight) {
											this.ballYVel = this.ballXVel = 0;
											break;
										}
									}
								} else {
									this.ballYVel = 1;
									if (rotate) {
										if (increase) {
											this.ballXVel = 1;
										}
										else {
											this.ballXVel = -1;
										}
									}
								}
							} else {
								if(this.blocked_car != null) {
									if(this.ballY + this.ballHeight > this.blocked_car.ballY ) {
										Lane temp_blocked_lane = lanes.get(this.blocked_car.lane);
										lanes.get(this.blocked_car.lane).cars.get(0).stop_rotate = false;
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
								if (!this.emergency && this.ballY <= temp_car.ballY + temp_car.ballHeight && this.ballX+this.ballWidth >= temp_car.ballX && this.ballX <= temp_car.ballX+temp_car.ballWidth && this.priority < temp_car.priority) {
									if (this.ballY < temp_car.ballY + temp_car.ballHeight && this.ballY + this.ballHeight >= temp_car.ballY) {
										temp_car.ballXVel = temp_car.ballYVel = 0;
										temp_car.stop_rotate = true;
										this.blocked_car = temp_car;
										break;
									} else {
										if(this.ballY+this.ballHeight > temp_car.ballY) {
											this.ballYVel = this.ballXVel = 0;
											break;
										}
									}
								} else {
									this.ballYVel = -1;
									if (rotate) {
										if (increase) {
											this.ballXVel = 1;
										}
										else {
											this.ballXVel = -1;
										}
									}
								}
							}
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

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public boolean isJoin() {
		return join;
	}

	public void setJoin(boolean join) {
		this.join = join;
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

	public boolean isEmergency() {
		return emergency;
	}

	public void setEmergency(boolean emergency) {
		this.emergency = emergency;
	}

	public ArrayList<Integer> getDirections() {
		return directions;
	}

	public void setDirections(ArrayList<Integer> directions) {
		this.directions = directions;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public Car getBlocked_car() {
		return blocked_car;
	}

	public void setBlocked_car(Car blocked_car) {
		this.blocked_car = blocked_car;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}