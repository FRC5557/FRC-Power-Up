package org.usfirst.frc.team5557.robot.commands.autogroups;


import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * Autonomous path for robot starting on left alliance spot headed for leftmost
 * peg
 */
public class AutoLeftGroup extends CommandGroup {
	public AutoLeftGroup(boolean shoot) {
		System.out.println("Stating left auton");
		addParallel(new DriveForDistanceCommand(1500));// Drive forward 7 ft 9 and 1/4 inch
		addParallel(new RaiseArmCommand());
	}
}
