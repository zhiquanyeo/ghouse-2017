package org.usfirst.frc.team354.robot.vision;

import org.usfirst.frc.team354.robot.vision.VisionSystem.Coords;
import org.usfirst.frc.team354.robot.vision.VisionSystem.VisionTarget;

public class VisionProcessing {
	
	// Angle Constants - Precalculate
	private static final double TAN_CAMERA_THETA_HORZ = Math.tan(Math.toRadians(VisionConstants.CameraConstants.LIFECAM_HORZ_VIEW_ANGLE / 2));
	private static final double TAN_CAMERA_THETA_VERT = Math.tan(Math.toRadians(VisionConstants.CameraConstants.LIFECAM_VERT_VIEW_ANGLE / 2));
	private static final double TAN_CAMERA_MOUNT_ANGLE_THETA = Math.tan(Math.toRadians(VisionSystem.CAMERA_ANGLE));
	
	// From pure H/V values
	private static final double ERR_HORZ_DIST = 27.604; // TODO: Find the error for this
	private static final double ERR_VERT_DIST = 27.604;
	
	// From corrected Vertical
	private static final double ERR_CORR_VERT_DIST = 184;
	
	// Pixel offset values for aiming
	private static final int AIM_PIXEL_X_OFFSET = 25;
	
	public static double viewAngle = 25;
	public static int cameraRes = 640;
	
	// distanceToTargetRaw - Measures using uncorrected vertical/horizontal widths
	// distanceToTargetCorrected - Measures using a camera-angle corrected height of target
	/**
	 * Calculate the distance to target using purely vision measurements.
	 * 
	 * This method does not take into account skew from the camera mounting angle
	 * 
	 * Most accurate when looking at a target head on
	 * @param target A VisionTarget
	 * @param useHorizontal Whether or not to use the width for measurement. Defaults to using height
	 * @return Distance in inches to target
	 */
	public static double distanceToTargetRaw(VisionTarget target, boolean useHorizontal) {
		if (useHorizontal) {
			return ((VisionSystem.TARGET_WIDTH * VisionSystem.CAMERA_X_RES) / (2 * target.getWidth() * TAN_CAMERA_THETA_HORZ)) - ERR_HORZ_DIST;
		}
		else {
			return ((VisionSystem.TARGET_HEIGHT * VisionSystem.CAMERA_Y_RES) / (2 * target.getHeight() * TAN_CAMERA_THETA_VERT)) - ERR_VERT_DIST;
		}
	}
	
	/**
	 * Calculate the distance to target using camera-angle corrected height.
	 * 
	 * Most accurate when looking at a target head-on
	 * @param target A VisionTarget
	 * @return Distance in inches to target
	 */
	public static double distanceToTargetCorrected(VisionTarget target) {
		double actualHeight = target.getHeight() * TAN_CAMERA_MOUNT_ANGLE_THETA;
		return ((VisionSystem.TARGET_HEIGHT * VisionSystem.CAMERA_Y_RES) / (2 * actualHeight * TAN_CAMERA_THETA_VERT)) - ERR_CORR_VERT_DIST;
	}
	
	/**
	 * Return a set of aiming coordinates for the target
	 * 
	 * The coordinate system is set up such that (0,0) is center of aim and axes grow up and right
	 * @param target A VisionTarget
	 * @return Coordinates of the target relative to the shooter exit
	 */
	public static Coords aimingCoordinates(VisionTarget target) {
		double aimX = ((target.getCenterX() - AIM_PIXEL_X_OFFSET) - (VisionSystem.CAMERA_X_RES / 2)) / (VisionSystem.CAMERA_X_RES / 2);
		if (aimX < -1.0) aimX = -1.0;
		if (aimX > 1.0) aimX = 1.0;
		
		double aimY = (target.getCenterY() - (VisionSystem.CAMERA_Y_RES / 2)) / (VisionSystem.CAMERA_Y_RES / 2);
		if (aimY < -1.0) aimY = -1.0;
		if (aimY > 1.0) aimY = 1.0;
		
		return new Coords(aimX, -aimY);
	}
	
	/**
	 * Calculate the number of degrees to turn the robot in order to center with the target
	 * 
	 * Positive numbers indicate clockwise, negative indicate counter clockwise
	 * @param target A VisionTarget
	 * @return Number of degrees to pivot
	 */
	public static double degreesForAlignment(VisionTarget target) {
		Coords aimCoords = aimingCoordinates(target);
		return Math.toDegrees(Math.asin(aimCoords.getX()));
	}
	
	
	//d = Tft*FOVpixel/(2*Tpixel*tanΘ)
	// 
	
	public static double widthInFeet(double knownPix, double knownFt, double widthPix) {
		return (knownFt * widthPix)/knownPix;
	}
	
	public double targetDistanceFromCenter(double centerX) {
		double centerOfGravity = cameraRes/2;
		return centerOfGravity - centerX;
	}
	
	
	public double distanceToCenter(double targetPix, double targetFt) {
		double w = widthInFeet(targetPix, targetFt, cameraRes)/2;
		double angleTangent = Math.tan(Math.toRadians(viewAngle/2));
		return w/angleTangent;
	}
	
	//widthPix is distance in pixels from center of FOV to center of target.
	public double horizontalAngle(double targetPix, double targetFt, double widthPix) {
		double w = widthInFeet(targetPix, targetFt, widthPix);
		double d = distanceToCenter(targetPix, targetFt);
		return Math.toDegrees(Math.atan(w/d));
	}
	
	public static void main(String[] args) {
		
		VisionProcessing vp = new VisionProcessing();
		
		double targetPix = 66;
		double centerX = 361;
		double targetFt = 20/12.0;
		
		double distance = vp.distanceToCenter(targetPix, targetFt);
	    System.out.println(distance);
	    double width = vp.widthInFeet(targetPix, targetFt, cameraRes);
	   // System.out.println(width);
	    double angle = vp.horizontalAngle(targetPix, targetFt, vp.targetDistanceFromCenter(centerX));
	 

	} 

}
