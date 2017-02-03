package org.usfirst.frc.team354.robot.subsystems;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.commands.ManualControlShooter;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSystem extends Subsystem {
	
	private CANTalon d_shooterMaster;
	private CANTalon d_shooterSlave;
	
	private CANTalon d_intake;
	
	public ShooterSystem() {
		d_shooterMaster = new CANTalon(Constants.ShooterSystemConstants.CAN_ID_SHOOTER_MASTER);
		d_shooterSlave = new CANTalon(Constants.ShooterSystemConstants.CAN_ID_SHOOTER_SLAVE);
		
		d_intake = new CANTalon(Constants.ShooterSystemConstants.CAN_ID_INTAKE_MOTOR);
		
		d_shooterSlave.changeControlMode(TalonControlMode.Follower);
		d_shooterSlave.set(Constants.ShooterSystemConstants.CAN_ID_SHOOTER_MASTER);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ManualControlShooter());
    }
    
    public void start() {
    	start(Constants.ShooterSystemConstants.SHOOTER_SPEED);
    }
    
    public void start(double speed) {
    	d_shooterMaster.set(speed);
    }
    
    public void stop() {
    	d_shooterMaster.set(0);
    }
    
    public void startIntake() {
    	d_intake.set(Constants.ShooterSystemConstants.INTAKE_SPEED);
    }
    
    public void stopIntake() {
    	d_intake.set(0);
    }
}

