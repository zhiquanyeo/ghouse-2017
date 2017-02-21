package org.usfirst.frc.team354.robot.commands.autonomous.base;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroTurn extends Command implements PIDOutput {
	
	private double d_turnToAngleRate;
	private PIDController d_controller;
	
	private double d_angle;
	
    public GyroTurn(double angle) {
    	d_angle = angle;
        requires(Robot.driveSystem);
        
        d_controller = new PIDController(Constants.DrivetrainPIDConstants.P,
        								 Constants.DrivetrainPIDConstants.I,
        								 Constants.DrivetrainPIDConstants.D,
        								 Constants.DrivetrainPIDConstants.F,
        								 Robot.ahrs, this);
        d_controller.setInputRange(-180.0, 180.0);
        d_controller.setOutputRange(-0.6, 0.6);
        d_controller.setAbsoluteTolerance(Constants.DrivetrainPIDConstants.TOLERANCE_DEGREES);
        d_controller.setContinuous(true);
        
        d_controller.setSetpoint(d_angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ahrs.reset();
    	d_controller.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSystem.arcadeDrive(0.0, d_turnToAngleRate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return d_controller.onTarget();
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
