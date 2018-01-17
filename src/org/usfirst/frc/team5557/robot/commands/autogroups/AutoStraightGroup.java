package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.UltraDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Autonomous path in which robot starts aligned with peg
 */
public class AutoStraightGroup extends CommandGroup {
	public AutoStraightGroup(boolean shoot) {
		addSequential(new UltraDriveCommand(400, .6));// Drive towards peg up until
													// certain distance
		addSequential(new WaitCommand(1000));// Extra time for gear to get on
		// peg
		// addSequential(new AutoLinearDriveCommand(-1));// Reverse out of
		// airship
		// spot
		// addSequential(new AutoTurnCommand(1));// Turn towards boiler

		if (shoot) {
			// TODO vision processing
		}
	}
}
