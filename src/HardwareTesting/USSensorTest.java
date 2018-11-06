package HardwareTesting;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;


public class USSensorTest extends Thread{
	
  private EV3LargeRegulatedMotor leftMotor; 
  private EV3LargeRegulatedMotor rightMotor;
  private static final int ROTATE_SPEED = 130;
  private static final double WHEEL_RAD = 10.3;
  private static final double WHEEL_BASE = 2.4 ;
  private static final double TILE_SIZE = 30.48;
  public Odometer odo;
  
  static float range;
  public boolean running;
  

  public USSensorTest(Odometer odo) {
	  
	  this.odo = odo;

	  this.leftMotor = odo.leftMotor;
	  this.rightMotor = odo.rightMotor;

	  // initialize motor speed
	  leftMotor.setSpeed(ROTATE_SPEED);
	  rightMotor.setSpeed(ROTATE_SPEED);

	  this.running = true;
	  
  }
  
  
  public void process(int distance){
    int buttonChoice = Button.waitForAnyPress();
    while(buttonChoice != Button.ID_ESCAPE) {
    	
    		Button.LEDPattern(4);
    		
    		leftMotor.forward();
    		rightMotor.backward();
    		System.out.println(odo.getXYT()[2] + "    " + distance);
    	
    }
    
	this.running = false;
    
  }

 
  void turnTo(double currTheta, double destTheta) {
    // get theta difference
    double deltaTheta = destTheta - currTheta;
    // normalize theta (get minimum value)
    deltaTheta = normalizeAngle(deltaTheta);

    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    
    leftMotor.rotate(convertAngle(WHEEL_RAD, WHEEL_BASE, deltaTheta), true);
    rightMotor.rotate(-convertAngle(WHEEL_RAD, WHEEL_BASE, deltaTheta), false);
   
}

  public static double normalizeAngle(double theta) {
    if (theta <= -180) {
        theta += 360;
    }
    else if (theta > 180) {
        theta -= 360;
    }
    return theta;
}
  public static int convertDistance(double radius, double distance) {
    return (int) ((180.0 * distance) / (Math.PI * radius));
}
  public static int convertAngle(double radius, double width, double angle) {
    return convertDistance(radius, Math.PI * width * angle / 360.0);
}


	public boolean isRunning() {
		return this.running;
	}
	public Object getLock() {
		return null;
	}
  
}