package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Fuel Shooter Command
 */
public class ShootCommand extends Command {
	private double count;
	private double speed;

	private boolean timed;

	public ShootCommand() {
		requires(Robot.shooter);
		timed = false;
		speed = .9;
		
	}

	public ShootCommand(double count) {
		requires(Robot.shooter);
		timed = true;
		this.count = count;
		speed = 1;
	}

	public ShootCommand(double count, double speed) {
		requires(Robot.shooter);
		timed = true;
		this.count = count;
		this.speed = speed;
	}

	@Override
	protected void initialize() {
		Robot.shooter.shoot(speed);
		if (timed) {
			setTimeout(count);
		}
	}

	@Override
	protected void execute() {
		//Robot.shooter.agitate(.3);
		Robot.shooter.shoot(speed);
		}

	@Override
	protected boolean isFinished() {
		if (timed) {
			return isTimedOut();
		}
		return false;
	}

	@Override
	protected void end() {
		//Robot.shooter.stopCollector();
		Robot.shooter.stop();
	}
}
