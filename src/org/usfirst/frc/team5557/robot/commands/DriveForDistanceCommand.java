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
		System.out.println("DriveForDistance Initialized");
	}

	@Override
	protected void execute() {
		Robot.drive.computerDrive(.5, 0);
	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(Robot.sensors.getDis(MotorType.kRearLeft)) <= distance) {
			return false;
		} else {
			System.out.println("DriveForDistance isFinished");
			return true;
		}
	}

	@Override
	protected void end() {
		Robot.drive.stop();
	}
}