package HardwareTesting;


import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;

public class RingColorTesting extends Thread{

	private SampleProvider cs;
	//private ColorController cont;
	private float[] csData;
	public float[] rgbValues;
	public enum RingColors{BLUE, GREEN, YELLOW, ORANGE};
	public static RingColors detectedRing;
	
	public static double[] blueRValues = {0.191671, 0.026218};
	public static double[] blueGValues = {0.593177, 0.043177};
	public static double[] blueBValues = {0.7793101, 0.0390857};

	public static double[] greenRValues = {0.504023, 0.007849};
	public static double[] greenGValues = {0.845422, 0.003544};
	public static double[] greenBValues = {0.1757721, 0.0158844};

	public static double[] orangeRValues = {0.97871, 0.007473};
	public static double[] orangeGValues = {0.181839, 0.036419};
	public static double[] orangeBValues = {0.0865479, 0.0137534};

	public static double[] yellowRValues = {0.883695, 0.016992};
	public static double[] yellowGValues = {0.446074, 0.033467};
	public static double[] yellowBValues = {0.1366481, 0.0045489};

	//public volatile boolean running;
	
	public RingColorTesting(SampleProvider cs) {
		this.cs = cs;
		this.csData = new float[cs.sampleSize()];
		this.rgbValues = new float[3];
	}
	
	public void run() {
		int buttonChoice = Button.waitForAnyPress();
		while(buttonChoice != Button.ID_ENTER) {
		LCD.clear();
		detectedRing = null;

		
		cs.fetchSample(csData, 0); // acquire data
		rgbValues[0] = csData[0]; // extract from buffer, cast to int
		rgbValues[1] = csData[1]; // extract from buffer, cast to int
		rgbValues[2] = csData[2]; // extract from buffer, cast to int
		
		float unitRGB = (float)Math.sqrt(
				Math.pow(rgbValues[0], 2) +
				Math.pow(rgbValues[1], 2) + 
				Math.pow(rgbValues[2], 2));
		
		rgbValues[0] /= unitRGB; // extract from buffer, cast to int
		rgbValues[1] /= unitRGB; // extract from buffer, cast to int
		rgbValues[2] /= unitRGB; // extract from buffer, cast to int
		
		RingColors ring = detectColor(rgbValues);
		
		if (ring != null) {
			RingColorTesting.detectedRing = ring;
			//Only use this if u want to print out the rgb values to the console!!!
			//System.out.println(detectedRing.toString() + " " + rgbValues[0] + "   " + rgbValues[1] + "    " + rgbValues[2]);
			LCD.drawString("Color: " + detectedRing.toString(), 0, 0);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(25);
		} catch (Exception e) 
		{
		} // Poor man's timed sampling
		}
	}

	public RingColors detectColor(float[] values) {
		float R, G, B;
		R = values[0];
		G = values[1];
		B = values[2];

		if (withinGaussDist(R, blueRValues, 2) &&
				withinGaussDist(G, blueGValues, 2) &&
				withinGaussDist(B, blueBValues, 2)) {
			return RingColors.BLUE;
		} else if (withinGaussDist(R, greenRValues, 4) &&
				withinGaussDist(G, greenGValues, 4) &&
				withinGaussDist(B, greenBValues, 4)) {
			return RingColors.GREEN;
		} else if (withinGaussDist(R, orangeRValues, 2) &&
				withinGaussDist(G, orangeGValues, 2) &&
				withinGaussDist(B, orangeBValues, 2)) {
			return RingColors.ORANGE;
		} else if (withinGaussDist(R, yellowRValues, 2) &&
				withinGaussDist(G, yellowGValues, 2) &&
				withinGaussDist(B, yellowBValues, 2)) {
			return RingColors.YELLOW;
		}
		return null;
	}


	public boolean withinGaussDist(double value, double[] target, int sigma) {
		return (Math.abs(value - target[0]) <= sigma * target[1]);
	}
}


