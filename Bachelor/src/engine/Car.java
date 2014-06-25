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
		return new Color(randomNumber.nextFloat(), randomNumber.nextFloat(), randomNumber.nextFloat());
	}

	public void update2(int min, int max,
			ArrayList<Lane> lanes, ArrayList<Join> joins, ArrayList<Square> squares) {
		boolean horizontal = lanes.get(this.lane).horizontal;
		boolean right_start = lanes.get(this.lane).right_start;

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
						for (int a = 0; a < lanes.get(this.lane).cars.size(); a++) {
							if (a+1 == lanes.get(this.lane).cars.size())
								break;
							Car car1 = lanes.get(this.lane).cars.get(a);
							Car car2 = lanes.get(this.lane).cars.get(a+1);
							if (car1.ballX == car2.ballX+car2.ballWidth) {
								car2.ballXVel = 0;
							} else {
								car2.ballXVel = 1;
							}
						}
					} else {
						for (int a = 0; a < lanes.get(this.lane).cars.size(); a++) {
							if (a+1 == lanes.get(this.lane).cars.size())
								break;
							Car car1 = lanes.get(this.lane).cars.get(a);
							Car car2 = lanes.get(this.lane).cars.get(a+1);
							if (car2.ballX == car1.ballX+car1.ballWidth) {
								car2.ballXVel = 0;
							} else {
								car2.ballXVel = -1;
							}
						}
					}
				} else {
					if (right_start) {
						for (int a = 0; a < lanes.get(this.lane).cars.size(); a++) {
							if (a+1 == lanes.get(this.lane).cars.size())
								break;
							Car car1 = lanes.get(this.lane).cars.get(a);
							Car car2 = lanes.get(this.lane).cars.get(a+1);
							if (car1.ballY == car2.ballY+car2.ballHeight) {
								car2.ballYVel = 0;
							} else {
								car2.ballYVel = 1;
							}
						}
					} else {
						for (int a = 0; a < lanes.get(this.lane).cars.size(); a++) {
							if (a+1 == lanes.get(this.lane).cars.size())
								break;
							Car car1 = lanes.get(this.lane).cars.get(a);
							Car car2 = lanes.get(this.lane).cars.get(a+1);
							if (car2.ballY == car1.ballY+car1.ballHeight) {
								car2.ballYVel = 0;
							} else {
								car2.ballYVel = -1;
							}
						}
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
									my_square.rotate_lane = -1;
									my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
								this.lane = this.directions.remove(0);
							}
						} else {
							if(this.ballY == lanes.get(directions.get(0)).y) {
								rotated = false;
								this.ballYVel = 0;
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									my_square.rotate_lane = -1;
									my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
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
									my_square.rotate_lane = -1;
									my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
								this.lane = this.directions.remove(0);
							}
						} else {
							if (this.ballX == lanes.get(directions.get(0)).x) {
								rotated = false;
								this.ballXVel = 0;
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									my_square.rotate_lane = -1;
									my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
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
									my_square.rotate_lane = -1;
									my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
								this.lane = this.directions.remove(0);
							}
						} else {
							if(this.ballX+this.ballWidth == lanes.get(directions.get(0)).x + lanes.get(directions.get(0)).distance && this.ballY == lanes.get(directions.get(0)).y) {
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									my_square.rotate_lane = -1;
									my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
								this.lane = this.directions.remove(0);
							}
						}
					} else {
						if (lanes.get(directions.get(0)).right_start) {
							if(this.ballX == lanes.get(directions.get(0)).x && this.ballY == lanes.get(directions.get(0)).y) {
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									my_square.rotate_lane = -1;
									my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
								this.lane = this.directions.remove(0);
							}
						} else {
							if(this.ballX == lanes.get(directions.get(0)).x && this.ballY+this.ballHeight == lanes.get(directions.get(0)).y+lanes.get(directions.get(0)).distance) {
								this.join = false;
								if(my_square != null) {
									my_square.number_cars--;
									my_square.rotate_lane = -1;
									//try {
									System.out.println(my_square.lanes_inside.toString());
										my_square.lanes_inside.remove(my_square.lanes_inside.indexOf(this.lane));
									//} catch (Exception e) {
										//e.printStackTrace();
									//}
								}
								lanes.get(directions.get(0)).addCar(lanes.get(this.lane).removeCar(this));
								this.lane = this.directions.remove(0);
							}
						}
					}
				}
				//System.out.println(my_square.rotate_lane+ " " +this.lane);
				//for arriving and stopping
			} else {
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
						if (this.ballX == lanes.get(this.lane).endX && this.ballY + this.ballHeight  == lanes.get(this.lane).endY) {
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
				if (rotate && joins.get(index).duration == 3 && my_square.lanes.contains(new Integer(this.lane)) && my_square.rotate_lane == -1)
					my_square.rotate_lane = this.lane;
				int emergency = my_square.emergency_lane(lanes);
				int rotate_lane = my_square.rotate_lane;
				Join emergency_join = null;
				boolean emergency_flag = false;
				boolean rotate_flag = false;
				if (emergency != -1) {
					for(int q = 0; q< joins.size(); q++) {
						if (joins.get(q).start == emergency && joins.get(q).end == lanes.get(emergency).emergency_car()) {
							emergency_join = joins.get(q);
							break;
						}
					}
					if (emergency_join != null) {
						for (int g = 0; g < emergency_join.blocked_lanes.size(); g++) {
							if (this.lane == emergency_join.blocked_lanes.get(g)[0].intValue() && this.directions.get(0) == emergency_join.blocked_lanes.get(g)[1].intValue())
								emergency_flag = true;
						}
						for (int g = 0; g < lanes.get(emergency).cars.size(); g++) {
							//if (lanes.get(emergency).cars.get(g).emergency)
								//break;
							Join temp_join = null;
							for(int q = 0; q< joins.size(); q++) {
								if (joins.get(q).start == lanes.get(emergency).cars.get(g).lane && joins.get(q).end == lanes.get(emergency).cars.get(g).directions.get(0)) {
									temp_join = joins.get(q);
									break;
								}
							}
							for (int f = 0; f < temp_join.blocked_lanes.size(); f++) {
								if (this.lane == temp_join.blocked_lanes.get(f)[0].intValue() && this.directions.get(0) == temp_join.blocked_lanes.get(f)[1].intValue())
									emergency_flag = true;
							}
						}
					}
				}
				if (rotate_lane != -1) {
					for (int g = 0; g < joins.get(index).blocked_lanes.size(); g++) {
						if (this.lane == joins.get(index).blocked_lanes.get(g)[0].intValue() && this.directions.get(0) == joins.get(index).blocked_lanes.get(g)[1].intValue())
							rotate_flag = true;
					}
				}
				if(horizontal) {
					if (this.lane == 4 || this.lane == 2)
					System.out.println(emergency_flag+" "+ emergency);
					if (this.ballX + this.ballWidth == max && right_start) {
						if ((!emergency_flag && emergency != -1) || (emergency == this.lane)||(emergency == -1 && my_square.rotate_lane == this.lane && my_square.can_rotate(this.lane))||(emergency == -1 && my_square.rotate_lane == -1 && my_square.number_cars < 3)) {							join = true;
						my_square.number_cars++;
						my_square.lanes_inside.add(this.lane);
						}
						else
							this.ballXVel = 0;
					}
					if (this.ballX == min && !right_start) {
						if ((!emergency_flag && emergency != -1) || (emergency == this.lane)||(emergency == -1 && my_square.rotate_lane == this.lane && my_square.can_rotate(this.lane))||(emergency == -1 && my_square.rotate_lane == -1 && my_square.number_cars < 3)){							join = true;
						my_square.number_cars++;
						my_square.lanes_inside.add(this.lane);
						}
						else
							this.ballXVel = 0;
					}
				} else {
					if (this.ballY + this.ballHeight == max && right_start ) {
						if ((!emergency_flag && emergency != -1) || (emergency == this.lane)||(emergency == -1 && my_square.rotate_lane == this.lane && my_square.can_rotate(this.lane))||(emergency == -1 && my_square.rotate_lane == -1 && my_square.number_cars < 3)){							join = true;
						my_square.number_cars++;
						my_square.lanes_inside.add(this.lane);
						}
						else
							this.ballYVel = 0;
					}
					if (this.ballY == min && !right_start) {
						if ((!emergency_flag && emergency != -1) || (emergency == this.lane)||(emergency == -1 && my_square.rotate_lane == this.lane && my_square.can_rotate(this.lane))||(emergency == -1 && my_square.rotate_lane == -1 && my_square.number_cars < 3)){							join = true;
						my_square.number_cars++;
						my_square.lanes_inside.add(this.lane);
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
				ArrayList<Integer[]> blocked_lanes = joins.get(index).blocked_lanes;
				for(int k = 0; k < blocked_lanes.size(); k++) {
					if (!lanes.get(blocked_lanes.get(k)[0]).cars.isEmpty()) {
						Car temp_car = lanes.get(blocked_lanes.get(k)[0]).cars.get(0);
						if (temp_car.directions.get(0) == blocked_lanes.get(k)[1].intValue()) {
							//if (temp_car.rotate && !this.emergency && this.join) {
							//this.ballXVel = this.ballYVel = 0;
							//}

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