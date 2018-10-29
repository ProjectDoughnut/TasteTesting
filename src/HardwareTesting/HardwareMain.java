package HardwareTesting;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.UARTSensor;
import lejos.robotics.SampleProvider;

public class HardwareMain {
	
	private static final Port usPort = LocalEV3.get().getPort("S4");
	private static final Port gyroPort = LocalEV3.get().getPort("S2");
	private static final Port lsPort = LocalEV3.get().getPort("S1");
	private static final Port csPort = LocalEV3.get().getPort("S3");

	//Setting up ultrasonic sensor
	public static UARTSensor usSensor = new EV3UltrasonicSensor(usPort);
	public static SampleProvider usValue = usSensor.getMode("Distance");

	//Setting up gyro sensor 
	public static EV3GyroSensor gyroSensor = new EV3GyroSensor(gyroPort);
	public static SampleProvider gyroValue = gyroSensor.getMode("Angle");
	
	//Setting up light sensor

	public static UARTSensor lsSensor = new EV3ColorSensor(lsPort);
	public static SampleProvider lsValue = lsSensor.getMode("Red");
	
	public static EV3ColorSensor csSensor = new EV3ColorSensor(csPort);
	public static SampleProvider csValue = csSensor.getRGBMode();
	
	private static final TextLCD lcd = LocalEV3.get().getTextLCD();

	public static final double WHEEL_RAD = 2.2;
	public static final double WHEEL_BASE = 10.45;
	public static final double TILE_SIZE = 30.48;
	
	
	public static void main(String[] args) {
		// init thread to exit application
		Thread exitThread = new Thread() {
			public void run() {
				while (Button.waitForAnyPress() != Button.ID_ESCAPE);
				System.exit(0);
			}
		};
		exitThread.start();

		int buttonChoice;
		
		MotorTesting motorTest = new MotorTesting(WHEEL_RAD, WHEEL_BASE);
		RingColorTesting colorTest = new RingColorTesting(csValue);
		
		do {
			// clear the display
			lcd.clear();

			// ask the user whether the motors should drive in a square or float
			lcd.drawString("< Left | Right >", 0, 0);
			lcd.drawString("Motors |  US    ", 0, 1);
			lcd.drawString(" Center: Light  ", 0, 2);
			lcd.drawString(" ^ Up  |  Down  ", 0, 3);
			lcd.drawString(" Color |  Gyro  ", 0, 4);

			buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)

		} while (buttonChoice != Button.ID_ALL);

		if (buttonChoice == Button.ID_LEFT) { 
			
			Thread motorThread = new Thread(motorTest);
			motorThread.start();
			
		}
		if (buttonChoice == Button.ID_UP) { 
			
			Thread colorThread = new Thread(colorTest);
			colorThread.start();
			
		}
		

	}

}
