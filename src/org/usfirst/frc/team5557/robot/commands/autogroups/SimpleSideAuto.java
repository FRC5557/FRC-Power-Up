package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.ExpellCommand;
import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;
import org.usfirst.frc.team5557.robot.commands.ShootBoxCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class SimpleSideAuto extends CommandGroup {
	
	public SimpleSideAuto( ) {
		addSequential(new DriveForDistanceCommand(500));
		}

}
