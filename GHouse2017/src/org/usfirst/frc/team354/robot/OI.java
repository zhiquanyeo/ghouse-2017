package org.usfirst.frc.team354.robot;

import org.usfirst.frc.team354.controls.XBox360Controller;
import org.usfirst.frc.team354.robot.commands.StartClimb;
import org.usfirst.frc.team354.robot.commands.StartIntake;
import org.usfirst.frc.team354.robot.commands.ToggleShooter;

import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	private final XBox360Controller mainGamepad;
	private final XBox360Controller auxGamepad;
	private final ToggleShooter d_shooter;
	
	public OI() {
		mainGamepad = new XBox360Controller(Constants.JoystickPorts.DRIVER_GAMEPAD_ID);
		auxGamepad = new XBox360Controller((Constants.JoystickPorts.AUX_GAMEPAD_ID));
		d_shooter = new ToggleShooter();
	
		auxGamepad.getRightTriggerButton().whileHeld(new StartIntake());
		auxGamepad.getLeftTriggerButton().whileHeld(new StartClimb());
		
		auxGamepad.getAButton().whenPressed(d_shooter);
		
	}
	
	public double getDriverGamepadLeftX() {
		return mainGamepad.getLeftX();
	}
	public double getDriverGamepadLeftY() {
		return mainGamepad.getLeftY();
	}
	public double getDriverGamepadRightX() {
		return mainGamepad.getRightX();
	}
	public double getDriverGamepadRightY() {
		return mainGamepad.getRightY();
	}
	
	public JoystickButton getButton(int stick, int button) {
		switch (stick) {
			case Constants.JoystickPorts.DRIVER_GAMEPAD_ID:
				return new JoystickButton(mainGamepad, button);
				
			default: 
				return null;
		}
	}
}
