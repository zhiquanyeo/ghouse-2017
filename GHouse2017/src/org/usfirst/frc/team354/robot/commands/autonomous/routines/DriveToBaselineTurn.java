package org.usfirst.frc.team354.robot.commands.autonomous.routines;

import org.usfirst.frc.team354.robot.commands.autonomous.base.GyroStraightDriveForTime;
import org.usfirst.frc.team354.robot.commands.autonomous.base.GyroTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToBaselineTurn extends CommandGroup {

    public DriveToBaselineTurn() {
    	addSequential(new GyroStraightDriveForTime(-0.65, 2000));
    	addSequential(new GyroTurn(180.0));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
