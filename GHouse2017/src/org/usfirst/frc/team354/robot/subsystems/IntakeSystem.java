package org.usfirst.frc.team354.robot.subsystems;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.commands.StopIntake;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSystem extends Subsystem {
	
	private CANTalon d_intakeMotor;
	
	public IntakeSystem() {
		d_intakeMotor = new CANTalon(Constants.IntakeSystemConstants.CAN_ID_INTAKE_MOTOR);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new StopIntake());
    }
    
    public void start() {
    	start(Constants.IntakeSystemConstants.INTAKE_SPEED);
    }
    
    public void start(double speed) {
    	d_intakeMotor.set(speed);
    }
    
    public void stop() {
    	d_intakeMotor.set(0);
    }
}

