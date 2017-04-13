
package org.usfirst.frc.team354.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Mat;
import org.usfirst.frc.team354.robot.commands.autonomous.base.DriveForTime;
import org.usfirst.frc.team354.robot.commands.autonomous.base.GyroStraightDriveForTime;
import org.usfirst.frc.team354.robot.commands.autonomous.base.GyroTurn;
import org.usfirst.frc.team354.robot.commands.autonomous.routines.DriveToBaselineTurn;
import org.usfirst.frc.team354.robot.subsystems.ClimberSystem;
import org.usfirst.frc.team354.robot.subsystems.DriveSystem;
import org.usfirst.frc.team354.robot.subsystems.IntakeSystem;
import org.usfirst.frc.team354.robot.subsystems.ShooterIntake;
import org.usfirst.frc.team354.robot.subsystems.ShooterSystem;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final DriveSystem driveSystem = new DriveSystem();
	public static final IntakeSystem intake = new IntakeSystem();
	public static final ShooterSystem shooter = new ShooterSystem();
	public static final ClimberSystem climber = new ClimberSystem();
	public static final ShooterIntake shooterIntake = new ShooterIntake();
	public static final MaxbotixRangefinder rangefinder = new MaxbotixRangefinder(0, -8.0);
	
	public static final AHRS ahrs = new AHRS(SPI.Port.kMXP);
	
	public static OI oi;
	
	public static boolean useCamera1;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	private static final boolean USE_MULTI_CAMERA = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addObject("GyroTurn 90", new GyroTurn(90.0));
		chooser.addObject("StraightDrive", new GyroStraightDriveForTime(-0.5, 2000));
		chooser.addObject("DumbStraightDrive", new DriveForTime(-0.5, 0.0, 2000));
		chooser.addObject("DriveToBaselineTurn", new DriveToBaselineTurn());
		chooser.addObject("CenterGear", new GyroStraightDriveForTime(-0.65, 1300));
		
		SmartDashboard.putData("Auto mode", chooser);
		
		// Set up CameraServer
		//CameraServer.getInstance().startAutomaticCapture();
		
		// Allow view switching via button
		if (USE_MULTI_CAMERA) {
			Thread t = new Thread(() -> {
				
				UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
				camera1.setResolution(320, 240);
				camera1.setFPS(30);
				
				UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
				camera2.setResolution(320,  240);
				camera2.setFPS(30);
				
				CvSink cvSink1 = CameraServer.getInstance().getVideo(camera1);
				CvSink cvSink2 = CameraServer.getInstance().getVideo(camera2);
				CvSource outputStream = CameraServer.getInstance().putVideo("Camera Switcher", 320, 240);
				
				Mat image = new Mat();
				
				while (!Thread.interrupted()) {
					
					if (useCamera1) {
						cvSink2.setEnabled(false);
						cvSink1.setEnabled(true);
						cvSink1.grabFrame(image);
					}
					else {
						cvSink1.setEnabled(false);
						cvSink2.setEnabled(true);
						cvSink2.grabFrame(image);
					}
					
					//System.out.println("Putting image on output");
					if (image.empty()) continue;
					outputStream.putFrame(image);
				}
			});
			
			t.start();
		}
		else {
			CameraServer.getInstance().startAutomaticCapture(0);
			CameraServer.getInstance().startAutomaticCapture(1);
		}
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		dumpDashboardStats();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		dumpDashboardStats();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void dumpDashboardStats() {
		// System Status
		SmartDashboard.putBoolean("shooterActive", Robot.shooter.isActive());
		SmartDashboard.putBoolean("shooterIntakeActive", Robot.shooterIntake.isActive());
		SmartDashboard.putBoolean("intakeActive", Robot.intake.isActive());
		
		// Distance
		SmartDashboard.putNumber("rangefinderDistance", Robot.rangefinder.getRangeInch());
	}
}
