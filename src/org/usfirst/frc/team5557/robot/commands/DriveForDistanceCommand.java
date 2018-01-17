package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;

//
//Basic Linear Movement in Autonomous
//
public class DriveForDistanceCommand extends Command {
	private double distance;

	public DriveForDistanceCommand(double distance) {
		requires(Robot.drive);
		this.distance = distance;
	}

	@Override
	protected void initialize() {
		Robot.sensors.resetEncoders();
	}

	@Override
	protected void execute() {
		Robot.drive.computerDrive(.5, 0);
	}

	@Override
	protected boolean isFinished() {
		if (Robot.sensors.getDis(MotorType.kFrontLeft) <= distance) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void end() {
		Robot.drive.stop();
	}
}