package org.usfirst.frc.team354.robot.commands.operator;

import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OperatorArcadeDrive extends Command {
	private double d_scale = 1.0;
	
    public OperatorArcadeDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this(1.0);
    }
    
    public OperatorArcadeDrive(double scaleFactor) {
    	requires(Robot.driveSystem);
    	
    	d_scale = scaleFactor;
    	if (d_scale < 0) d_scale = 0.0;
    	if (d_scale > 1) d_scale = 1.0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double move = Robot.oi.getDriverGamepadLeftY() * d_scale;
    	double rotate = Robot.oi.getDriverGamepadRightX() * d_scale;
    	
    	Robot.driveSystem.arcadeDrive(move, rotate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
