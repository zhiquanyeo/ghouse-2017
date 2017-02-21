package org.usfirst.frc.team354.robot.commands.autonomous.base;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroStraightDriveForTime extends Command implements PIDOutput {
	private double d_speed;
	private long d_duration;
	private long d_startTime;
	
	private double d_turnToAngleRate;
	private PIDController d_controller;
	
    public GyroStraightDriveForTime(double speed, long duration) {
    	requires(Robot.driveSystem);
    	
        d_controller = new PIDController(Constants.DrivetrainPIDConstants.P,
										 Constants.DrivetrainPIDConstants.I,
										 Constants.DrivetrainPIDConstants.D,
										 Constants.DrivetrainPIDConstants.F,
										 Robot.ahrs, this);
    	d_controller.setInputRange(-180.0, 180.0);
    	d_controller.setOutputRange(-1.0, 1.0);
    	d_controller.setAbsoluteTolerance(Constants.DrivetrainPIDConstants.TOLERANCE_DEGREES);
    	d_controller.setContinuous(true);
    	
    	d_controller.setSetpoint(0.0);
    	
    	d_duration = duration;
    	d_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ahrs.reset();
    	d_controller.enable();
    	d_startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSystem.arcadeDrive(d_speed, d_turnToAngleRate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (System.currentTimeMillis() - d_startTime) > d_duration;
    }

    // Called once after isFinished returns true
    protected void end() {
    	d_controller.disable();
    	Robot.driveSystem.arcadeDrive(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	d_controller.disable();
    	Robot.driveSystem.arcadeDrive(0.0, 0.0);
    }

	@Override
	public void pidWrite(double output) {
		d_turnToAngleRate = output;
		
	}
}
