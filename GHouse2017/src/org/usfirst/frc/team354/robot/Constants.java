package org.usfirst.frc.team354.robot;

public class Constants {
	//=== Joystick Ports ===
	public static final class JoystickPorts {
		public static final int DRIVER_GAMEPAD_ID = 0;
		public static final int AUX_GAMEPAD_ID = 1;
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
		public static final double INTAKE_SPEED = -0.85;
	}
	
	//=== ShooterSystem ===
	public static final class ShooterSystemConstants {
		public static final int CAN_ID_SHOOTER_MASTER = 21;
		public static final int CAN_ID_SHOOTER_SLAVE = 22;
		public static final int CAN_ID_INTAKE_MOTOR = 27;
		
		// Old -0.758
		public static final double SHOOTER_SPEED = -0.65; // good for 80-88. For 88 - 99, use 0.7
		public static final double INTAKE_SPEED = 0.8;
	}
	
	//--- ClimberSystem ===
	public static final class ClimberSystemConstants {
		public static final int CAN_ID_CLIMBER_MASTER = 24;
		public static final int CAN_ID_CLIMBER_SLAVE = 25;
		public static final double CLIMB_SPEED = -0.5;
	}
	
	public static final class DrivetrainPIDConstants {
		public static final double P = 0.05;
		public static final double I = 0.0;
		public static final double D = 0.1;
		public static final double F = 0.0;
		
		public static final double TOLERANCE_DEGREES = 2.0;
	}
}
