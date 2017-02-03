package org.usfirst.frc.team354.robot.subsystems;

import org.usfirst.frc.team354.robot.Constants;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberSystem extends Subsystem {

    private CANTalon d_climberMaster;
    
    public ClimberSystem() {
    	d_climberMaster = new CANTalon(Constants.ClimberSystemConstants.CAN_ID_CLIMBER_MASTER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void start() {
    	d_climberMaster.set(Constants.ClimberSystemConstants.CLIMB_SPEED);
    }
    
    public void stop() {
    	d_climberMaster.set(0);
    }
}

