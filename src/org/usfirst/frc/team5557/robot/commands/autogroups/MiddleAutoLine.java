package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class MiddleAutoLine extends CommandGroup {
	
	public MiddleAutoLine(){
		;// Drive forward for 10 ft
		addSequential(new TurnForAngleCommand(90)); 
		addSequential(new DriveForDistanceCommand(198.12));
		addSequential(new TurnForAngleCommand(-90));
		addSequential(new DriveForDistanceCommand(304.8 + 152.4));
		 //to turn right - angle is positive, to turn left - angle is negative
	
		
	}
}
