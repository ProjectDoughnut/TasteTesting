package SensorTesting;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class MotorTesting {

	private static final EV3LargeRegulatedMotor testingMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	
	private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	
	private static final TextLCD lcd = LocalEV3.get().getTextLCD();
	
	public static final double WHEEL_RAD = 2.2;
	public static final double WHEEL_BASE = 10.45;
	
	public static int testingTheta = 45;
	
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
		
		do {
			// clear the display
			lcd.clear();

			// ask the user whether the motors should drive in a square or float
			lcd.drawString("< Left | Right >", 0, 0);
			lcd.drawString("       |        ", 0, 1);
			lcd.drawString("Single |  Two   ", 0, 2);
			lcd.drawString(" Motor | Motors ", 0, 3);
			lcd.drawString("       |        ", 0, 4);

			buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)

		} while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);

		
		/* Testing a single motor 
		 * Go through 10 runs, stop after each turn until a button is pressed
		 */
		if (buttonChoice == Button.ID_LEFT) { 
			
			
			// clear the display
			lcd.clear();

			// ask the user whether the motors should drive in a square or float
			lcd.drawString(" Left: | Down:  ", 0, 0);
			lcd.drawString("  45   |  270   ", 0, 1);
			lcd.drawString("Up: 90 |        ", 0, 2);
			lcd.drawString("Right: |Center: ", 0, 3);
			lcd.drawString(" 180   |  360   ", 0, 4);

			buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)

			
		
			//Test by turning 45 degrees
			if (buttonChoice == Button.ID_LEFT) { 
					

				for (int i = 0; i < 10; i++) {
					testingMotor.rotate(45, true);
					Button.waitForAnyPress();		
				}	
			}
			
			//Test by turning 90 degrees
			if (buttonChoice == Button.ID_UP) { 
					
				for (int i = 0; i < 10; i++) {
					testingMotor.rotate(90, true);
					Button.waitForAnyPress();		
				}	
			}
			
			
			//Test by turning 180 degrees
			if (buttonChoice == Button.ID_RIGHT) {

				for (int i = 0; i < 10; i++) {
					testingMotor.rotate(180, true);
					Button.waitForAnyPress();		
				}	
			}
			
			//Test by turning 270 (or -90) degrees
			if (buttonChoice == Button.ID_DOWN) { 

				for (int i = 0; i < 10; i++) {
					testingMotor.rotate(270, true);
					Button.waitForAnyPress();		
				}	
			}
			
			//Test by turning 360 (or 0) degrees
			if (buttonChoice == Button.ID_ENTER) {

				for (int i = 0; i < 10; i++) {
					testingMotor.rotate(360, true);
					Button.waitForAnyPress();		
				}	
			}
		}
		
		/* Testing two motors 
		 * Go through 10 runs, stop after each turn until a button is pressed
		 */
		else {
			
			// clear the display
			lcd.clear();

			// ask the user whether the motors should drive in a square or float
			lcd.drawString("       |        ", 0, 0);
			lcd.drawString(" <Left | Right> ", 0, 1);
			lcd.drawString(" Turn  |  Turn  ", 0, 2);
			lcd.drawString(" to    |  to    ", 0, 3);
			lcd.drawString(" Left  |  Right ", 0, 4);

			buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)

			} while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);
			
			
			//Turning the robot to the left!
			if (buttonChoice == Button.ID_LEFT) {
				// clear the display
				lcd.clear();

				// ask the user whether the motors should drive in a square or float
				lcd.drawString(" Left: | Down:  ", 0, 0);
				lcd.drawString("  45   |  270   ", 0, 1);
				lcd.drawString("Up: 90 |        ", 0, 2);
				lcd.drawString("Right: |Center: ", 0, 3);
				lcd.drawString(" 180   |  360   ", 0, 4);

				buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)

				
				//Test by turning 45 degrees
				if (buttonChoice == Button.ID_LEFT) {
						

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 45), true);
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 45), true);
						Button.waitForAnyPress();		
					}	
				}
				
				//Test by turning 90 degrees
				if (buttonChoice == Button.ID_UP) { 
						
					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 90), true);
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 90), true);
						Button.waitForAnyPress();		
					}	
				}
				
				
				//Test by turning 180 degrees
				if (buttonChoice == Button.ID_RIGHT) {

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 180), true);
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 180), true);
						Button.waitForAnyPress();		
					}	
				}
				
				//Test by turning 270 (or -90) degrees
				if (buttonChoice == Button.ID_DOWN) {

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 270), true);
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 270), true);
						Button.waitForAnyPress();		
					}	
				}
				
				//Test by turning 360 (or 0) degrees
				if (buttonChoice == Button.ID_ENTER) {

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 360), true);
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 360), true);
						Button.waitForAnyPress();		
					}	
				}
			}
			//Testing by turning to the right!
			else {
				// clear the display
				lcd.clear();

				// ask the user whether the motors should drive in a square or float
				lcd.drawString(" Left: | Down:  ", 0, 0);
				lcd.drawString("  45   |  270   ", 0, 1);
				lcd.drawString("Up: 90 |        ", 0, 2);
				lcd.drawString("Right: |Center: ", 0, 3);
				lcd.drawString(" 180   |  360   ", 0, 4);

				buttonChoice = Button.waitForAnyPress(); // Record choice (left or right press)

				
				//Test by turning 45 degrees
				if (buttonChoice == Button.ID_LEFT) { 
						

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 45), true);
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 45), true);
						Button.waitForAnyPress();		
					}	
				}
				
				//Test by turning 90 degrees
				if (buttonChoice == Button.ID_UP) { 
						
					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 90), true);
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 90), true);
						Button.waitForAnyPress();		
					}	
				}
				
				
				//Test by turning 180 degrees
				if (buttonChoice == Button.ID_RIGHT) { 

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 180), true);
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 180), true);
						Button.waitForAnyPress();		
					}	
				}
				
				//Test by turning 270 (or -90) degrees
				if (buttonChoice == Button.ID_DOWN) { 

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 270), true);
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 270), true);
						Button.waitForAnyPress();		
					}	
				}
				
				//Test by turning 360 (or 0) degrees
				if (buttonChoice == Button.ID_ENTER) { 

					for (int i = 0; i < 10; i++) {
						leftMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, 360), true);
						leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, 360), true);
						Button.waitForAnyPress();		
					}	
				}
			}
		
	    while (Button.waitForAnyPress() != Button.ID_ESCAPE);
	    System.exit(0);
	}
	
	public static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

	public static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}
}
