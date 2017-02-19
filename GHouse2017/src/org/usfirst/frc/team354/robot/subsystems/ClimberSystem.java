package org.usfirst.frc.team354.robot.subsystems;

import org.usfirst.frc.team354.robot.Constants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberSystem extends Subsystem {

    private CANTalon d_climberMaster;
    private CANTalon d_climberSlave;
    
    public ClimberSystem() {
    	d_climberMaster = new CANTalon(Constants.ClimberSystemConstants.CAN_ID_CLIMBER_MASTER);
    	d_climberSlave = new CANTalon(Constants.ClimberSystemConstants.CAN_ID_CLIMBER_SLAVE);
    	
    	d_climberSlave.changeControlMode(TalonControlMode.Follower);
    	d_climberSlave.set(Constants.ClimberSystemConstants.CAN_ID_CLIMBER_MASTER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void start() {
    	start(Constants.ClimberSystemConstants.CLIMB_SPEED);
    }
    
    public void start(double speed) {
    	d_climberMaster.set(speed);
    }
    
    public void stop() {
    	d_climberMaster.set(0);
    }
}

