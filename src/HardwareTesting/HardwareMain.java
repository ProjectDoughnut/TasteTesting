package HardwareTesting;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.UARTSensor;
import lejos.robotics.SampleProvider;

public class HardwareMain {
	
	private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));

	//private static final Port usPort = LocalEV3.get().getPort("S4");
	//private static final Port gyroPort = LocalEV3.get().getPort("S2");



	//Setting up ultrasonic sensor
	//public static UARTSensor usSensor = new EV3UltrasonicSensor(usPort);
	//public static SampleProvider usValue = usSensor.getMode("Distance");

	//Setting up gyro sensor 
	//public static EV3GyroSensor gyroSensor = new EV3GyroSensor(gyroPort);
	//public static SampleProvider gyroValue = gyroSensor.getMode("Angle");
	
	//Setting up light sensor

	
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
<<<<<<< HEAD

=======
		
		MotorTesting motorTest = new MotorTesting(WHEEL_RAD, WHEEL_BASE);
		RingColorTesting colorTest = new RingColorTesting(csValue);
		USsensorTesting usTest = new USsensorTesting();
>>>>>>> db2b3a1f9b6f92a37a08dfebe6b53952a10b836a
		
		do {
			// clear the display
			lcd.clear();

			// ask the user whether the motors should drive in a square or float
			lcd.drawString("< Left | Right >", 0, 0);
			lcd.drawString("Motors |  US    ", 0, 1);
			lcd.drawString(" Center: Line   ", 0, 2);
			lcd.drawString(" ^ Up  |  Down  ", 0, 3);
			lcd.drawString(" Color |  Gyro  ", 0, 4);

			buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)

		} while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT && buttonChoice != Button.ID_UP && buttonChoice != Button.ID_DOWN && buttonChoice != Button.ID_ENTER);

		if (buttonChoice == Button.ID_LEFT) {
			
			
			MotorTesting motorTest = new MotorTesting(WHEEL_RAD, WHEEL_BASE);
			
			Thread motorThread = new Thread(motorTest);
			motorThread.start();
			
		}
		else if (buttonChoice == Button.ID_UP) { 
			
			final Port csPort = LocalEV3.get().getPort("S3");
			EV3ColorSensor csSensor = new EV3ColorSensor(csPort);
			SampleProvider csValue = csSensor.getRGBMode();
			RingColorTesting colorTest = new RingColorTesting(csValue);
			
			Thread colorThread = new Thread(colorTest);
			colorThread.start();
			
		}
		if(buttonChoice == Button.ID_ENTER){
		  
		  Thread usThread = new Thread(usTest);
		  usThread.start();
		}
		
		else if (buttonChoice == Button.ID_ENTER) { 
			
			final Port lsPort = LocalEV3.get().getPort("S1");
			UARTSensor lsSensor = new EV3ColorSensor(lsPort);
			SampleProvider lsValue = lsSensor.getMode("Red");
			
			LineDetectionTesting lineTest = new LineDetectionTesting(lsValue, leftMotor, rightMotor, WHEEL_RAD, WHEEL_BASE);
			
			
			Thread lineThread = new Thread(lineTest);
			lineThread.start();
			
		}
		

	}

}
