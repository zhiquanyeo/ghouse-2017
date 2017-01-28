package org.usfirst.frc.team354.robot.subsystems;

import org.usfirst.frc.team354.robot.Constants;
import org.usfirst.frc.team354.robot.commands.OperatorArcadeDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * DriveSystem subsystem for 2017 G-House Robot
 * 
 * Consists of a left/right gearbox, each having 2 motors
 * One motor per side will be designated as the master, and
 * the other as the slave (set to Follower mode)
 */
public class DriveSystem extends Subsystem {
	
	private CANTalon d_leftMaster;
	private CANTalon d_leftSlave;
	
	private CANTalon d_rightMaster;
	private CANTalon d_rightSlave;
	
	private RobotDrive d_driveSystem;
	
	public DriveSystem() {
		d_leftMaster = new CANTalon(Constants.DriveSystemConstants.CAN_ID_DRIVE_LEFT_MASTER);
		d_leftSlave = new CANTalon(Constants.DriveSystemConstants.CAN_ID_DRIVE_LEFT_SLAVE);
		d_rightMaster = new CANTalon(Constants.DriveSystemConstants.CAN_ID_DRIVE_RIGHT_MASTER);
		d_rightSlave = new CANTalon(Constants.DriveSystemConstants.CAN_ID_DRIVE_RIGHT_SLAVE);
		
		// Set up the speed controllers
		d_leftSlave.changeControlMode(TalonControlMode.Follower);
		d_leftSlave.set(Constants.DriveSystemConstants.CAN_ID_DRIVE_LEFT_MASTER);
		
		d_rightSlave.changeControlMode(TalonControlMode.Follower);
		d_rightSlave.set(Constants.DriveSystemConstants.CAN_ID_DRIVE_RIGHT_MASTER);
		
		d_driveSystem = new RobotDrive(d_leftMaster, d_rightMaster);
		
		d_driveSystem.setInvertedMotor(MotorType.kRearLeft, true);
		d_driveSystem.setInvertedMotor(MotorType.kRearRight, true);
	}


    public void initDefaultCommand() {
    	setDefaultCommand(new OperatorArcadeDrive());
    }
    
    public void arcadeDrive(GenericHID stick) {
    	d_driveSystem.arcadeDrive(stick);
    }
    
    public void arcadeDrive(GenericHID stick, boolean squaredInputs) {
    	d_driveSystem.arcadeDrive(stick, squaredInputs);
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
    	d_driveSystem.arcadeDrive(moveValue, rotateValue);
    }
    
    public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
    	d_driveSystem.arcadeDrive(moveValue, rotateValue, squaredInputs);
    }
}

