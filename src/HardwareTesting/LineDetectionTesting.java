package HardwareTesting;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.SampleProvider;

public class LineDetectionTesting extends Thread{
	
	private static SampleProvider ls;
	private static float[] lsData;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private double WHEEL_RAD;
	private double WHEEL_BASE;
	private static int firstReading = -1;
	private double lightThreshold = 30.0;
	private int distance;
	
	private static final int FORWARD_SPEED = 200;
	
	private static final TextLCD lcd = LocalEV3.get().getTextLCD();

	public LineDetectionTesting(SampleProvider ls, EV3LargeRegulatedMotor leftMotor, 
								EV3LargeRegulatedMotor rightMotor, double rad, double base) {
		LineDetectionTesting.ls = ls;
		LineDetectionTesting.lsData = new float[ls.sampleSize()];
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.WHEEL_RAD = rad;
		this.WHEEL_BASE = base;
	}
	
	public void run() {
		int buttonChoice = Button.waitForAnyPress();
		while(buttonChoice != Button.ID_ESCAPE) { 

		/*
		 * This test will have the robot move forward 8 tiles.
		 * Every time it detects a line, it will beep
		 * It will run this maneuver 35 times, after each trial it will wait for you to press a button
		 * Set the robot in a tile in whatever direction you want it to go in
		 */
		lcd.clear(); 
		int count = 0;
   
		Button.waitForAnyPress();
			
		ls.fetchSample(lsData, 0); // acquire data
		distance = (int)(lsData[0] * 100.0);
		if (LineDetectionTesting.firstReading == -1) { //Set the first reading value
			LineDetectionTesting.firstReading = distance;
		}
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		leftMotor.forward();
		rightMotor.forward();
			
		while (count < 8) {
			ls.fetchSample(lsData, 0); // acquire data
			distance = (int)(lsData[0] * 100.0);

				
			if ((100*Math.abs(distance - firstReading)/firstReading) > lightThreshold) {
				if (distance < firstReading) {
					Sound.beep();
					count++;
						//leftMotor.stop(true);
						//rightMotor.stop(false);
				}
			} 
		}
			
		leftMotor.stop(true);
		rightMotor.stop(false);
		lcd.drawString("Lines detected: " + count, 0, 0);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
	}
	
	public static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
}
