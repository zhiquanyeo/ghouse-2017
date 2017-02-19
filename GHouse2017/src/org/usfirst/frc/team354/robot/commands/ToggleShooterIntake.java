package org.usfirst.frc.team354.robot.commands;

import org.usfirst.frc.team354.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ToggleShooterIntake extends InstantCommand {

    public ToggleShooterIntake() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.shooterIntake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.shooterIntake.toggle();
    }

}
