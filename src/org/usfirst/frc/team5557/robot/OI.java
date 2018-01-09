package org.usfirst.frc.team5557.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5557.robot.commands.AgitateCommand;
import org.usfirst.frc.team5557.robot.commands.ClimbCommand;
import org.usfirst.frc.team5557.robot.commands.ExampleCommand;
import org.usfirst.frc.team5557.robot.commands.ShootCommandGroup;
import org.usfirst.frc.team5557.robot.commands.SoftEStopCommand;
import org.usfirst.frc.team5557.robot.commands.UltraDriveCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// Joystick defined here
	public static final Joystick driveStick = new Joystick(RobotMap.JOYSTICK_DRIVE);

	// Buttons defined here
	public final Button autoGearButton = new JoystickButton(driveStick, RobotMap.AUTOMATIC_GEAR_PLACEMENT_BUTTON);
	public final Button shooterButton = new JoystickButton(driveStick, RobotMap.SHOOTER_BUTTON);
	public final Button climberButton = new JoystickButton(driveStick, RobotMap.CLIMBER_BUTTON);
	public final Button eStopButton = new JoystickButton(driveStick, RobotMap.EMERGENCY_STOP_BUTTON);
	public final Button reverseClimbButton = new JoystickButton(driveStick, RobotMap.REVERSE_CLIMBER_BUTTON);
	public final Button agitateButton = new JoystickButton(driveStick, RobotMap.COLLECTOR_BUTTON);
	
	public OI() {
		autoGearButton.whenPressed(new UltraDriveCommand(400,.6));
		shooterButton.whileHeld(new ShootCommandGroup());
		agitateButton.whileHeld(new AgitateCommand());
		climberButton.whileHeld(new ClimbCommand());
		eStopButton.whenPressed(new SoftEStopCommand());
		reverseClimbButton.whileHeld(new ClimbCommand(-1));
	}

	
	
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
}
