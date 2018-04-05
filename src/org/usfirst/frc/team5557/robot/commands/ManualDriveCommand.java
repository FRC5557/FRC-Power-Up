package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.subsystems.DriveSubSystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for simple, manual driving
 */
public class ManualDriveCommand extends Command {

	private DriveSubSystem drive = DriveSubSystem.getInstance();
	
	// Initializes the command with its name, as well as set DriveSubsystem to
	// be required
	public ManualDriveCommand() {
		super("ManualDrive");
		requires(drive);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		drive.drive();
		//System.out.println(Robot.sensors.getDis());
		//System.out.println("Joystick Y axis: " + OI.driveStickZero.getY());
	} 

	@Override
	protected boolean isFinished() {
		return false;
	}

}
