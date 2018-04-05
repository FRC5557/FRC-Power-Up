package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.subsystems.DriveSubSystem;
import org.usfirst.frc.team5557.robot.subsystems.SensorSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;

//
//Basic Linear Movement in Autonomous
//
public class DriveForDistanceCommand extends Command {
	
	private SensorSubsystem sensors = SensorSubsystem.getInstance();
	private DriveSubSystem drive = DriveSubSystem.getInstance();
	
	private double distance;
	double dif;
	double angle;

	public DriveForDistanceCommand(double distance) {
		requires(drive);
		requires(sensors);
		this.distance = distance;
	}

	@Override
	protected void initialize() {
		sensors.resetEncoders();
		System.out.println("DriveForDistance Initialized");
	}

	@Override
	protected void execute() {
		//TODO Balance motors using PID instead of manual motor speeds
		drive.computerDrive(.8, 0); 
	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(sensors.getDis(MotorType.kFrontRight)) <= distance+250) {
			return false;
		} else {
			System.out.println("DriveForDistance isFinished");
			return true;
		}
	}

	@Override
	protected void end() {
		drive.stop();
	}
}