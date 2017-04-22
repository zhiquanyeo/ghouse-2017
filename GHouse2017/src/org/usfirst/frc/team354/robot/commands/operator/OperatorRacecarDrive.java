package org.usfirst.frc.team354.robot.commands.operator;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OperatorRacecarDrive extends Command {
	private double d_scale = 1.0;
	
    public OperatorRacecarDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(1.0);
    }
    
    public OperatorRacecarDrive(double scaleFactor) {
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
    	double fwdVal = expo(Robot.oi.getDriverRightTrigger(), 
    							Constants.DriveSystemConstants.EXPO_FWD);
    	double backVal = expo(Robot.oi.getDriverRightTrigger(), 
    							Constants.DriveSystemConstants.EXPO_BACK);
    	double moveVal = (fwdVal - backVal) * d_scale;
    	double turnVal = expo(Robot.oi.getDriverGamepadLeftX(),
    							Constants.DriveSystemConstants.EXPO_TURN) * d_scale;
    	
    	Robot.driveSystem.arcadeDrive(moveVal, turnVal);
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
    
    private double expo(double input, double expoValue) {
    	double multiplier = 1.0;
    	if (input < 0) {
    		input = input * -1.0;
    		multiplier = -1.0;
    	}
    	double yVal = (Math.exp(expoValue * input) - 1) / (Math.exp(expoValue) - 1);
    	return multiplier * yVal;
    }
}
