package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;
import org.usfirst.frc.team5557.robot.commands.UltraDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Autonomous path for robot starting on right alliance spot headed for
 * rightmost peg
 */
public class AutoRightGroup extends CommandGroup {
	public AutoRightGroup(boolean shoot) {
		addSequential(new DriveForDistanceCommand(94));// Drive forward 7ft 9 and 1/4 inch
		addSequential(new TurnForAngleCommand(-50));// Turn towards peg
		addSequential(new UltraDriveCommand(400, .6));// Drive towards peg until
												// certain distance
		addSequential(new WaitCommand(1000));// Extra time to make sure gear gets
											// on peg

		if (shoot) {
			// TODO vision processing
		}
	}
}
