package org.usfirst.frc.team354.robot.commands.autonomous.routines;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.commands.ToggleShooter;
import org.usfirst.frc.team354.robot.commands.ToggleShooterIntake;
import org.usfirst.frc.team354.robot.commands.autonomous.base.GyroStraightDriveForTime;
import org.usfirst.frc.team354.robot.commands.autonomous.base.GyroTurn;
import org.usfirst.frc.team354.robot.commands.autonomous.base.ReverseToDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutonomousShoot extends CommandGroup {

    public AutonomousShoot(double turnAngle) {
    	addSequential(new GyroStraightDriveForTime(-0.65, 1500));
    	addSequential(new GyroTurn(turnAngle));
    	addSequential(new GyroStraightDriveForTime(-0.65, 1000)); // to align with wall
    	addSequential(new ReverseToDistance(Constants.AutonomousConstants.AUTO_SHOOT_DISTANCE_FROM_WALL));
    	
    	// Spin up the shooter
    	addSequential(new ToggleShooter());
    	addSequential(new TimedCommand("Wait for shooter", 1.0));
    	addSequential(new ToggleShooterIntake());
    	addSequential(new TimedCommand("Fire!", 6.0));
    	
    	// Turn everything off
    	addSequential(new ToggleShooter());
    	addSequential(new ToggleShooterIntake());
    }
}
