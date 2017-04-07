package org.usfirst.frc.team354.robot.commands;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartIntake extends Command {
	private boolean d_invert = false;
	
    public StartIntake(boolean invert) {
    	requires(Robot.intake);
    	requires(Robot.climber);
    	
    	d_invert = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (d_invert) {
    		Robot.intake.start(-Constants.IntakeSystemConstants.INTAKE_SPEED);
    		// We can't reverse the climber, so we should just stop it
    		Robot.climber.stop();
    	}
    	else {
    		Robot.intake.start();
    		Robot.climber.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stop();
    	Robot.climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stop();
    	Robot.climber.stop();
    }
}
