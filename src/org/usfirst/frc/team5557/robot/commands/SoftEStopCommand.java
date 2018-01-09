package org.usfirst.frc.team5557.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Driver Controlled Emergency Stop for all Mechanisms
 */
public class SoftEStopCommand extends Command {

	@Override
	protected void execute() {
		Scheduler.getInstance().removeAll();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}
}
