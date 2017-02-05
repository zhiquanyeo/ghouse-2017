package org.usfirst.frc.team354.robot.commands.autonomous.base;

import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForTime extends Command {
	private double d_moveVal;
	private double d_rotateVal;
	private long d_startTime;
	private long d_duration;
	
    public DriveForTime(double move, double rotate, long durationMs) {
        d_moveVal = move;
        d_rotateVal = rotate;
        d_duration = durationMs;
        requires(Robot.driveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	d_startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSystem.arcadeDrive(d_moveVal, d_rotateVal);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (System.currentTimeMillis() - d_startTime) > d_duration;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSystem.arcadeDrive(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
