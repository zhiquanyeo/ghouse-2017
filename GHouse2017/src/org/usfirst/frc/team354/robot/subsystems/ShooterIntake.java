package org.usfirst.frc.team354.robot.subsystems;

import org.usfirst.frc.team354.robot.Constants;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterIntake extends Subsystem {
	private CANTalon d_intake;
	private boolean d_shooterIntakeActive;

    public ShooterIntake() {
    	d_intake = new CANTalon(Constants.ShooterSystemConstants.CAN_ID_INTAKE_MOTOR);
    	d_shooterIntakeActive = false;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void start() {
    	d_intake.set(Constants.ShooterSystemConstants.INTAKE_SPEED);
    	d_shooterIntakeActive = true;
    }
    
    public void stop() {
    	d_intake.set(0);
    	d_shooterIntakeActive = false;
    }
    
    public void toggle() {
    	if (d_shooterIntakeActive) {
    		stop();
    	}
    	else {
    		start();
    	}
    }
    
    public boolean isActive() {
    	return d_shooterIntakeActive;
    }
}

