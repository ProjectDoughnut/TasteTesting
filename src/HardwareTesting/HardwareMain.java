package HardwareTesting;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.UARTSensor;
import lejos.robotics.SampleProvider;

public class HardwareMain {
	
	private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	
	//private static final Port gyroPort = LocalEV3.get().getPort("S2");



	//Setting up gyro sensor 
	//public static EV3GyroSensor gyroSensor = new EV3GyroSensor(gyroPort);
	//public static SampleProvider gyroValue = gyroSensor.getMode("Angle");
	
	//Setting up light sensor

	
	private static final TextLCD lcd = LocalEV3.get().getTextLCD();

	public static final double WHEEL_RAD = 2.2;
	public static final double WHEEL_BASE = 10.8;
	public static final double TILE_SIZE = 30.48;
	
	public static final String TestsList[] = {"Motors", "US", "Line", "Color", "Gyro"};
	
	
	public static void main(String[] args) throws OdometerExceptions {
		// init thread to exit application
		
		Thread exitThread = new Thread() {
			public void run() {
				while (Button.waitForAnyPress() != Button.ID_ESCAPE);
				System.exit(0);
			}
		};
		exitThread.start();


		int buttonChoice;
		int testChoice = 0;
		do {
			// clear the display
			lcd.clear();

			// ask the user whether the motors should drive in a square or float
			lcd.drawString("Select test:", 0, 0);
			lcd.drawString(TestsList[(TestsList.length+testChoice-1) % TestsList.length], 0, 2);
			lcd.drawString(TestsList[testChoice], 0, 3, true);
			lcd.drawString(TestsList[(testChoice+1) % TestsList.length], 0, 4);

			buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)
			if (buttonChoice == Button.ID_UP) {
				testChoice = (testChoice + TestsList.length - 1) % TestsList.length;
			} else if (buttonChoice == Button.ID_DOWN) {
				testChoice = (testChoice +1) % TestsList.length;
			}
		} while (buttonChoice != Button.ID_ENTER);
		lcd.clear();

		if (testChoice == 0) {
			
			
			MotorTesting motorTest = new MotorTesting(WHEEL_RAD, WHEEL_BASE);
			
			Thread motorThread = new Thread(motorTest);
			motorThread.start();
			
		}
		else if(testChoice == 1){
			  
			Odometer odo = Odometer.getOdometer(leftMotor, rightMotor, WHEEL_BASE, WHEEL_RAD);
			final Port usPort = LocalEV3.get().getPort("S4");
			UARTSensor usSensor = new EV3ColorSensor(usPort);
			SampleProvider usValue = usSensor.getMode("Distance");
			USSensorTest usTest = new USSensorTest(odo);
			UltrasonicPoller usPoller = new UltrasonicPoller(usValue, usTest);
			Thread usThread = new Thread(usPoller);
			usThread.start();
		}
		else if (testChoice == 2) { 
			
			final Port lsPort = LocalEV3.get().getPort("S1");
			UARTSensor lsSensor = new EV3ColorSensor(lsPort);
			SampleProvider lsValue = lsSensor.getMode("Red");
			LineDetectionTesting lineTest = new LineDetectionTesting(lsValue, leftMotor, rightMotor, WHEEL_RAD, WHEEL_BASE);
			Thread lineThread = new Thread(lineTest);
			lineThread.start();
		} 
		else if (testChoice == 3) { 
			
			final Port csPort = LocalEV3.get().getPort("S3");
			EV3ColorSensor csSensor = new EV3ColorSensor(csPort);
			SampleProvider csValue = csSensor.getRGBMode();
			RingColorTesting colorTest = new RingColorTesting(csValue);
			
			Thread colorThread = new Thread(colorTest);
			colorThread.start();
			
		}
		else if (testChoice == 4) {
			
			Port gyroPort = LocalEV3.get().getPort("S2");
			EV3GyroSensor gyroSensor = new EV3GyroSensor(gyroPort);
			SampleProvider gyroValue = gyroSensor.getMode("Angle");
			Odometer odo;

			try {
				odo = Odometer.getOdometer(leftMotor, rightMotor, WHEEL_BASE, WHEEL_RAD);
				
			} catch (OdometerExceptions e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				odo = null;
			}
			Navigation nav = new Navigation(odo, leftMotor, rightMotor, WHEEL_RAD, WHEEL_BASE, TILE_SIZE);
			nav.setRunning(true);
			GyroTester tester = new GyroTester(gyroValue, odo, nav);
			Thread t = new Thread(tester);
			t.start();
			
		}
		

	}

}
