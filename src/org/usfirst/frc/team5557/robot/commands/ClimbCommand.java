package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for climbing mechanism
 */
public class ClimbCommand extends Command {
	private double speed;

	public ClimbCommand() {
		requires(Robot.climber);
		speed = 1;
	}

	public ClimbCommand(double speed) {
		requires(Robot.climber);
		this.speed = speed;
	}

	@Override
	protected void execute() {
		Robot.climber.climb(speed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.climber.stop();
	}
}
