package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Basic Linear Movement in Autonomous
 */
public class DanceCommand extends Command {
	private double speed;
	private long time;
	private long initial;
	public DanceCommand(double speed, long time) {
		requires(Robot.drive);
		this.speed = speed;
		this.time = time;
		initial = System.currentTimeMillis();
	}

	@Override
	protected void initialize() {
		Robot.sensors.resetEncoders();
	}

	@Override
	protected void execute() {

		System.out.println("dance" + speed + " " + initial + " " + time);
		Robot.drive.computerDrive(0,speed);
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
		Robot.drive.stop();
	}
}
