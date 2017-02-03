package org.usfirst.frc.team354.controls;

import org.usfirst.frc.team354.robot.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Interface for an XBox 360 controller
 * 
 * This provides a semantically better experience for dealing with an
 * XBox 360 controller. It exposes all buttons and axes, and has provisions
 * for using the triggers as digital triggers
 *
 */
public class XBox360Controller extends Joystick {
	private double d_deadband = 0.0;
	
	// Hardware buttons
	private final JoystickButton d_buttonA;
	private final JoystickButton d_buttonB;
	private final JoystickButton d_buttonX;
	private final JoystickButton d_buttonY;
	private final JoystickButton d_leftShoulder;
	private final JoystickButton d_rightShoulder;
	private final JoystickButton d_buttonBack;
	private final JoystickButton d_buttonStart;
	private final JoystickButton d_leftStickButton;
	private final JoystickButton d_rightStickButton;
	
	// Trigger as Digital Button
	private final XBox360DigitalTrigger d_leftTriggerAsButton;
	private final XBox360DigitalTrigger d_rightTriggerAsButton;
	
	public XBox360Controller(int port) {
		this(port, 0.0);
	}
	
	public XBox360Controller(int port, double deadband) {
		super(port);
		d_deadband = deadband;
		d_buttonA = new JoystickButton(this, Constants.XBoxButtonMap.A_BUTTON);
		d_buttonB = new JoystickButton(this, Constants.XBoxButtonMap.B_BUTTON);
		d_buttonX = new JoystickButton(this, Constants.XBoxButtonMap.X_BUTTON);
		d_buttonY = new JoystickButton(this, Constants.XBoxButtonMap.Y_BUTTON);
		d_leftShoulder = new JoystickButton(this, Constants.XBoxButtonMap.LEFT_SHOULDER);
		d_rightShoulder = new JoystickButton(this, Constants.XBoxButtonMap.RIGHT_SHOULDER);
		d_buttonBack = new JoystickButton(this, Constants.XBoxButtonMap.BACK_BUTTON);
		d_buttonStart = new JoystickButton(this, Constants.XBoxButtonMap.START_BUTTON);
		d_leftStickButton = new JoystickButton(this, Constants.XBoxButtonMap.LEFT_STICK_BUTTON);
		d_rightStickButton = new JoystickButton(this, Constants.XBoxButtonMap.RIGHT_STICK_BUTTON);
		
		d_leftTriggerAsButton = new XBox360DigitalTrigger(this, Constants.XBoxButtonMap.LEFT_TRIGGER, 0.65);
		d_rightTriggerAsButton = new XBox360DigitalTrigger(this, Constants.XBoxButtonMap.RIGHT_TRIGGER, 0.65);
	}
	
	private double fitWithinDeadband(double rawVal) {
		if (Math.abs(rawVal) < d_deadband) {
			return 0.0;
		}
		return rawVal;
	}
	
	public double getLeftX() {
		return fitWithinDeadband(this.getRawAxis(Constants.XBoxButtonMap.LEFT_STICK_X_AXIS));
	}
	
	public double getLeftY() {
		return fitWithinDeadband(this.getRawAxis(Constants.XBoxButtonMap.LEFT_STICK_Y_AXIS));
	}
	
	public double getRightX() {
		return fitWithinDeadband(this.getRawAxis(Constants.XBoxButtonMap.RIGHT_STICK_X_AXIS));
	}
	
	public double getRightY() {
		return fitWithinDeadband(this.getRawAxis(Constants.XBoxButtonMap.RIGHT_STICK_Y_AXIS));
	}
	
	public double getLeftTriggerAnalog() {
		return this.getRawAxis(Constants.XBoxButtonMap.LEFT_TRIGGER);
	}
	
	public double getRightTriggerAnalog() {
		return this.getRawAxis(Constants.XBoxButtonMap.RIGHT_TRIGGER);
	}
	
	// Return buttons
	public Button getAButton() {
		return d_buttonA;
	}
	
	public Button getBButton() {
		return d_buttonB;
	}
	
	public Button getXButton() {
		return d_buttonX;
	}
	
	public Button getYButton() {
		return d_buttonY;
	}
	
	public Button getLeftShoulderButton() {
		return d_leftShoulder;
	}
	public Button getRightShoulderButton() {
		return d_rightShoulder;
	}
	
	public Button getBackButton() {
		return d_buttonBack;
	}
	
	public Button getStartButton() {
		return d_buttonStart;
	}
	
	public Button getLeftStickButton() {
		return d_leftStickButton;
	}
	
	public Button getRightStickButton() {
		return d_rightStickButton;
	}
	
	public Button getLeftTriggerButton() {
		return d_leftTriggerAsButton;
	}
	
	public Button getRightTriggerButton() {
		return d_rightTriggerAsButton;
	}
	
	public static class XBox360DigitalTrigger extends Button {
		private double d_thresh;
		private int d_axis;
		private XBox360Controller d_controller;
		
		public XBox360DigitalTrigger(XBox360Controller controller, int triggerAxis, double threshold) {
			super();
			d_controller = controller;
			d_axis = triggerAxis;
			d_thresh = threshold;
		}

		@Override
		public boolean get() {
			return d_controller.getRawAxis(d_axis) > d_thresh;
		}
	}
}
