package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for simple, manual driving
 */
public class ManualDriveCommand extends Command {

	// Initializes the command with its name, as well as set DriveSubsystem to
	// be required
	public ManualDriveCommand() {
		super("ManualDrive");
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.drive.drive();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
