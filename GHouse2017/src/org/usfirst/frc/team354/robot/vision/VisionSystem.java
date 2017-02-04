package org.usfirst.frc.team354.robot.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionSystem {
	
	// Camera Mounting Position
	//      FRONT OF ROBOT
	//			^ +ve
	//			|
	//	 -ve <--+--> +ve
	//			|
	//			v -ve
	public static final double CAMERA_LATERAL_OFFSET = -6.0; // inches from center of shooter
	public static final double CAMERA_FOREAFT_OFFSET = 3.0; // inches from shooter exit
	public static final double CAMERA_HEIGHT = 13.0; // inches off the ground
	public static final double CAMERA_ANGLE = 30.0; // degrees
	
	public static final double CAMERA_X_RES = 320;
	public static final double CAMERA_Y_RES = 240;
	
	// Target Size Information
	public static final double TARGET_WIDTH = 20.0; // inches
	public static final double TARGET_HEIGHT = 12.0; // inches
	public static final double OPTIMUM_ASPECT_RATIO = TARGET_WIDTH / TARGET_HEIGHT; // ~1.67
	public static final double ASPECT_RATIO_RANGE = 4.0;
	
	public static final double TARGET_HEIGHT_ABOVE_GROUND = 83.0; // inches
	
	// Bounding Box vs Target Area
	public static final double BOUNDING_BOX_AREA = 240.0; // square inches
	public static final double TARGET_AREA = 80.0; // square inches
	public static final double OPTIMUM_COVERAGE_AREA = TARGET_AREA / BOUNDING_BOX_AREA;
	public static final double INVERSE_OPTIMUM_COVERAGE_AREA = 1.0 / OPTIMUM_COVERAGE_AREA;
	
	// NOTE: We want to compare solidity to OPTIMUM_COVERAGE_AREA
	public static class Coords {
		private double d_x;
		private double d_y;
		
		public Coords(double x, double y) {
			d_x = x;
			d_y = y;
		}
		
		public double getX() {
			return d_x;
		}
		
		public double getY() {
			return d_y;
		}
		
		public void setX(double x) {
			d_x = x;
		}
		
		public void setY(double y) {
			d_y = y;
		}
		
		public String toString() {
			return "(" + d_x + ", " + d_y + ")";
		}
	}
	
	public static class VisionTarget {
		private double d_centerX;
		private double d_centerY;
		private double d_width;
		private double d_height;
		private double d_area;
		private double d_solidity;
		
		// Calculated Values
		private double d_aspectRatio;
		private double d_coverageArea;
		
		// Score
		private double d_coverageScore;
		private double d_aspectRatioScore;
		private double d_convexHullAngleScore;
		
		public VisionTarget(double cX, double cY, double w, double h, double a, double s) {
			d_centerX = cX;
			d_centerY = cY;
			d_width = w;
			d_height = h;
			d_area = a;
			d_solidity = s; // Area / ConvexHullArea
			
			d_aspectRatio = d_width / d_height;
			d_coverageArea = d_area / (d_width * d_height); // Area / BoundingBoxArea
			
			// === SCORE CALCULATION ===
			// Coverage Area - We want around 0.3
			// Take the difference in coverage areas and calculate how many points to subtract from 100
			d_coverageScore = 100.0 - 
					((Math.min(Math.abs(OPTIMUM_COVERAGE_AREA - d_coverageArea), INVERSE_OPTIMUM_COVERAGE_AREA) / INVERSE_OPTIMUM_COVERAGE_AREA) * 100.0);
			
			// Aspect Ratio - We want around 1.67
			// Scale to 0.0 - 4.0
			d_aspectRatioScore = 100.0 - 
					((Math.min(Math.abs(OPTIMUM_ASPECT_RATIO - d_aspectRatio), ASPECT_RATIO_RANGE) / ASPECT_RATIO_RANGE) * 100.0);
			
			// Calculate the ratio of convex hull area to bounding box area
			// As the ratio approaches 1, the target is better aligned
			double convexHullArea = d_area / d_solidity;
			d_convexHullAngleScore = (convexHullArea / (d_width * d_height)) * 100;
			
			// Calculate the height of the bounding box and use that to "guess" the distance. Then use the distance
			// to see how wide the target is. 
			// Note: Since GRIP doesn't broadcast oriented bounding boxes, this could have some inaccuracy if we are
			// heading towards a target at an angle. We can use convexHullAngleScore as a measure of confidence
			
		}
		
		public double getCenterX() {
			return d_centerX;
		}
		
		public double getCenterY() {
			return d_centerY;
		}
		
		public double getWidth() {
			return d_width;
		}
		
		public double getHeight() {
			return d_height;
		}
		
		public double getArea() {
			return d_area;
		}
		
		public double getSolidity() {
			return d_solidity;
		}
		
		public double getAspectRatio () {
			return d_aspectRatio;
		}
		
		public double getCoverageArea() {
			return d_coverageArea;
		}
		
		public double getCoverageScore() {
			return d_coverageScore;
		}
		
		public double getAspectRatioScore() {
			return d_aspectRatioScore;
		}
		
		public double getConvexHullAngleScore() {
			return d_convexHullAngleScore;
		}
		
		public double getScore() {
			return d_coverageScore + d_aspectRatioScore + d_convexHullAngleScore;
		}
		
		public String toString() {
			return "(" + d_centerX + "," + d_centerY + ") " + d_width + "x" + d_height + " -> AR: " + 
					d_aspectRatioScore + ", CVR: " + d_coverageScore + ", CHAngle: " + d_convexHullAngleScore + 
					", Total = " + getScore();
		}
	}
	
	private NetworkTable d_gripTable;
	
	private boolean d_initialized = false;
	
	public VisionSystem() {
	}
	
	public void initialize() {
		d_gripTable = NetworkTable.getTable("GRIP/myContoursReport");
		d_initialized = true;
	}
	
	/**
	 * Get available vision targets from GRIP
	 * @return An array of vision targets
	 */
	public VisionTarget[] getTargets() {
		if (!d_initialized) {
			return new VisionTarget[0];
		}
		
		// Attempt to get data from network tables
		double[] defaultValues = new double[0];
		double[] areas = d_gripTable.getNumberArray("area", defaultValues);
		int numTargets = areas.length;
		
		if (numTargets == 0) {
			return new VisionTarget[0];
		}
		
		double[] centerXs = d_gripTable.getNumberArray("centerX", defaultValues);
		double[] centerYs = d_gripTable.getNumberArray("centerY", defaultValues);
		double[] widths = d_gripTable.getNumberArray("width", defaultValues);
		double[] heights = d_gripTable.getNumberArray("height", defaultValues);
		double[] solidities = d_gripTable.getNumberArray("solidity", defaultValues);
		
		VisionTarget[] targets = new VisionTarget[numTargets];
		for (int i = 0; i < numTargets; i++) {
			targets[i] = new VisionTarget(centerXs[i], centerYs[i], widths[i], heights[i], areas[i], solidities[i]);
		}
		
		return targets;
	}
	
	/**
	 * Get the best target available
	 * 
	 * This should take into account area, distance and angle to target
	 * @return Best VisionTarget for firing
	 */
	public VisionTarget getBestTarget() {
		VisionTarget[] targets = getTargets();
		
		if (targets.length == 0) {
			return null;
		}
		
		int highestScoreIdx = -1;
		double highestScore = -1.0;
		for (int i = 0; i < targets.length; i++) {
			if (targets[i].getScore() > highestScore) {
				highestScore = targets[i].getScore();
				highestScoreIdx = i;
			}
		}
		
		// TBD: Generate a fake GRIP/bestTargetReport table and post
		// the bounding box to it
		
		return targets[highestScoreIdx];
	}
}
