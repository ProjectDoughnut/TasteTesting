package HardwareTesting;

import lejos.robotics.SampleProvider;

public class GyroTester extends Thread {

	private SampleProvider gyro;
    private float [] sample;
    
    private Navigation nav;
    private Odometer odo;
	
	public GyroTester(SampleProvider gyro, Odometer odo, Navigation nav) {
		
		this.gyro = gyro;
		this.sample = new float[gyro.sampleSize()];
		this.nav = nav;
		this.odo = odo;
	}
	

	public float getAngle() {
		gyro.fetchSample(sample, 0);
		return -sample[0];
	}
	
	@Override
	public void run() {
		int lapses = 3;
		System.out.println("Odo X, Odo Y, Odo T, Gyro T");
		do {
			double xyt[] = odo.getXYT();
			System.out.println(xyt[0] + ", " + xyt[1] + ", " + xyt[2] + ", " + this.getAngle());
			nav.syncTravelTo(0, 1);
			System.out.println(xyt[0] + ", " + xyt[1] + ", " + xyt[2] + ", " + this.getAngle());
			nav.syncTravelTo(1, 1);
			System.out.println(xyt[0] + ", " + xyt[1] + ", " + xyt[2] + ", " + this.getAngle());
			nav.syncTravelTo(1, 0);
			System.out.println(xyt[0] + ", " + xyt[1] + ", " + xyt[2] + ", " + this.getAngle());
			nav.syncTravelTo(0, 0);
			lapses--;
		} while(lapses > 0);
	}

	
	
}
