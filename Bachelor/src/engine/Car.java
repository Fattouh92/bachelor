package engine;

public class Car {
	int lane;
	float ballX, ballY, lastBallX, lastBallY;
	int ballWidth, ballHeight;
	float ballXVel, ballYVel;
	float ballSpeed;

	int lastDrawX, lastDrawY;
	
	public Car(int lane, int x, int y, int width, int height) {
		this.lane = lane;
		ballX = lastBallX = x;
		ballY = lastBallY = y;
		ballWidth = width;
		ballHeight = height;
		ballSpeed = 25;
		ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
		ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
	}
	
	public void update(int height, int width) {
		lastBallX = ballX;
		lastBallY = ballY;

		ballX += ballXVel;
		ballY += ballYVel;

		if (ballX + ballWidth/2 >= width)
		{
			ballXVel *= -1;
			ballX = width - ballWidth/2;
			ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
		}
		else if (ballX - ballWidth/2 <= 0)
		{
			ballXVel *= -1;
			ballX = ballWidth/2;
		}

		if (ballY + ballHeight/2 >= height)
		{
			ballYVel *= -1;
			ballY = height - ballHeight/2;
			ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
		}
		else if (ballY - ballHeight/2 <= 0)
		{
			ballYVel *= -1;
			ballY = ballHeight/2;
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