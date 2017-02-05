package org.usfirst.frc.team354.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class MaxbotixRangefinder {
	private static final double V_PER_IN = 5.0 / 512.0;
	
	private AnalogInput d_aIn;
	private double d_offset;
	
	/**
	 * Construct a new Analog MaxbotixRangefinder
	 * @param channel Analog channel the sensor is connected to
	 * @param offset Distance (in inches) to offset readings by
	 */
	public MaxbotixRangefinder(int channel, double offset) {
		d_aIn = new AnalogInput(channel);
		d_offset = offset;
	}
	
	public double getRangeInch() {
		double v = d_aIn.getAverageVoltage();
		double inches = v / V_PER_IN;
		return inches - d_offset;
	}
}
