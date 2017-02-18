package org.usfirst.frc.team354.robot.commands;

import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class ToggleShooter extends Command {
	public ToggleShooter() {
		requires(Robot.shooter);
	}
	
	 // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.toggleShooting();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// do nothing
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.stop();
    }


}
