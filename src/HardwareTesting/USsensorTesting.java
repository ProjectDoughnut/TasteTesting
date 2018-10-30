package HardwareTesting;


import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;


public class USsensorTesting extends Thread{
  private static EV3LargeRegulatedMotor leftMotor; 
  private static EV3LargeRegulatedMotor rightMotor;
  private static final int FORWARD_SPEED = 200;
  private static final int ROTATE_SPEED = 130;
  private static final double WHEEL_RAD = 10.3;
  private static final double WHEEL_BASE = 2.4 ;
  private static final double TILE_SIZE = 30.48;
  
  
  
  static float  range;
  
  public static final Port port = LocalEV3.get().getPort("S4");
  
  static UltraSonicSensor uss = new UltraSonicSensor(port);
  
  
  public void run(){
    
    Button.LEDPattern(4);    // flash green led and
    Sound.beepSequenceUp();    // make sound when ready.

    Button.waitForAnyPress();
    
   


    double  j = 0;
      for(int i=5; i<180; i = i+5){
       turnTo(j, i);
       j = i;
       
       System.out.println("Distance is: " + uss.getRange() + "Angle is: " + i);
       Delay.msDelay(500);
       
      }
      
  }
  static void turnTo(double currTheta, double destTheta) {
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

  
}
