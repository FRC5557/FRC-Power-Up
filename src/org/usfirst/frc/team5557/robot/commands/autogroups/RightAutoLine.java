package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;

import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class RightAutoLine extends CommandGroup{
	public RightAutoLine() {
		System.out.println("Stating Right auton");
		//addSequential(new DriveForDistanceCommand(314.8));// Drive forward for 10 ft
		addSequential(new TurnForAngleCommand(90));
	} 
}
