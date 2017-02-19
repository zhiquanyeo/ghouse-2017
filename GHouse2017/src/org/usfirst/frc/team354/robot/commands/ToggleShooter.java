package org.usfirst.frc.team354.robot.commands;

import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleShooter extends InstantCommand {
	public ToggleShooter() {
		requires(Robot.shooter);
	}
	
	 // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.toggle();
    }
}
