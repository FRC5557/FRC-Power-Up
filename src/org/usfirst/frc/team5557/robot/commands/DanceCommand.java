package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.subsystems.DriveSubSystem;
import org.usfirst.frc.team5557.robot.subsystems.SensorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Basic Linear Movement in Autonomous
 */
public class DanceCommand extends Command {
	
	private SensorSubsystem sensors = SensorSubsystem.getInstance();
	private DriveSubSystem drive = DriveSubSystem.getInstance();
	
	private double speed;
	private long time;
	private long initial;
	public DanceCommand(double speed, long time) {
		requires(drive);
		this.speed = speed;
		this.time = time;
		initial = System.currentTimeMillis();
	}

	@Override
	protected void initialize() {
		sensors.resetEncoders();
	}

	@Override
	protected void execute() {

		System.out.println("dance" + speed + " " + initial + " " + time);
		drive.computerDrive(0,speed);
	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(System.currentTimeMillis() - initial) < time) {
		
			return false;
			
		} else {
			initial = System.currentTimeMillis() + 5;
			return true;
		}
	}

	@Override
	protected void end() {
		drive.stop();
	}
}
