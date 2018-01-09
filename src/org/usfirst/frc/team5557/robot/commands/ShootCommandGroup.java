package org.usfirst.frc.team5557.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ShootCommandGroup extends CommandGroup{
	public ShootCommandGroup()
	{

		addParallel(new ShootCommand());
		addSequential(new WaitCommand(0.5));
		addSequential(new AgitateCommand());
	}
}
