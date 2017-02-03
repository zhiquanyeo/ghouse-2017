package org.usfirst.frc.team354.robot;

public class Constants {
	//=== Joystick Ports ===
	public static final class JoystickPorts {
		public static final int DRIVER_GAMEPAD_ID = 0;
	}
	
	//=== General ===
	public static final class XBoxButtonMap {
		public static final int LEFT_STICK_X_AXIS = 0;
		public static final int LEFT_STICK_Y_AXIS = 1;
		public static final int RIGHT_STICK_X_AXIS = 4;
		public static final int RIGHT_STICK_Y_AXIS = 5;
		public static final int LEFT_TRIGGER = 2;
		public static final int RIGHT_TRIGGER = 3;
		
		public static final int A_BUTTON = 0;
		public static final int B_BUTTON = 1;
		public static final int X_BUTTON = 2;
		public static final int Y_BUTTON = 3;
		public static final int LEFT_SHOULDER = 4;
		public static final int RIGHT_SHOULDER = 5;
		public static final int BACK_BUTTON = 6;
		public static final int START_BUTTON = 7;
		
		public static final int LEFT_STICK_BUTTON = 8;
		public static final int RIGHT_STICK_BUTTON = 9;
	}
	
	//=== DriveSystem ===
	public static final class DriveSystemConstants {
		public static final int CAN_ID_DRIVE_LEFT_MASTER = 26;
		public static final int CAN_ID_DRIVE_LEFT_SLAVE = 32;
		public static final int CAN_ID_DRIVE_RIGHT_MASTER = 30;
		public static final int CAN_ID_DRIVE_RIGHT_SLAVE = 20;
	}
	
	//=== IntakeSystem ===
	public static final class IntakeSystemConstants {
		public static final int CAN_ID_INTAKE_MOTOR = 23;
		public static final double INTAKE_SPEED = 0.75;
	}
	
	//=== ShooterSystem ===
	public static final class ShooterSystemConstants {
		public static final int CAN_ID_SHOOTER_MASTER = 21;
		public static final int CAN_ID_SHOOTER_SLAVE = 22;
		public static final int CAN_ID_INTAKE_MOTOR = 28; // TBD change
		
		public static final double SHOOTER_SPEED = -0.758; 
		public static final double INTAKE_SPEED = 0.75;
	}
	
	//--- ClimberSystem ===
	public static final class ClimberSystemConstants {
		public static final int CAN_ID_CLIMBER_MASTER = 27;
		public static final double CLIMB_SPEED = 0.5;
	}
}
