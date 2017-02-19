package org.usfirst.frc.team354.robot.subsystems;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.commands.ManualControlShooter;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Encapsulation of a wiffle ball shooter consisting of a 2 motor shooting
 * mechanism, and TBD intake motor(s) that supply the balls into the 
 * shooting chamber
 * 
 * The shooting mechanism is toggelable. 
 * 
 * TBD: Might move to closed loop shooting control once we have the encoders 
 * hooked up
 */
public class ShooterSystem extends Subsystem {
	
	private CANTalon d_shooterMaster;
	private CANTalon d_shooterSlave;
	
	private boolean d_shooterOn;
	
	public ShooterSystem() {
		d_shooterMaster = new CANTalon(Constants.ShooterSystemConstants.CAN_ID_SHOOTER_MASTER);
		d_shooterSlave = new CANTalon(Constants.ShooterSystemConstants.CAN_ID_SHOOTER_SLAVE);
		
		d_shooterSlave.changeControlMode(TalonControlMode.Follower);
		d_shooterSlave.set(Constants.ShooterSystemConstants.CAN_ID_SHOOTER_MASTER);
		d_shooterOn = false;
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ManualControlShooter());
    }
    
    public void start() {
    	start(Constants.ShooterSystemConstants.SHOOTER_SPEED);
    	d_shooterOn = true;
    }
    
    public void start(double speed) {
    	d_shooterMaster.set(speed);
    	d_shooterOn = true;
    }
    
    public void stop() {
    	d_shooterMaster.set(0);
    	d_shooterOn = false;
    }
    
    public void toggle() {
    	if (d_shooterOn) {
    		stop();
    	} else {
    		start();
    	}
    }
    
    public boolean isActive() {
    	return d_shooterOn;
    }
}

