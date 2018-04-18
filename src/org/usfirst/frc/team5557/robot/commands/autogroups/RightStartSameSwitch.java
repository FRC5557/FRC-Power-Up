package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.ExtendWristForTime;
import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightStartSameSwitch extends CommandGroup {
	public RightStartSameSwitch(){
		System.out.println("Stating Right auton");
		addSequential(new DriveForDistanceCommand(470));// Drive forward for 15 ft
		addSequential(new TurnForAngleCommand(40));
		addSequential(new TurnForAngleCommand(-180));
		addSequential(new DriveForDistanceCommand(-135));
		addSequential(new RaiseArmCommand(1250));
		addSequential(new ExtendWristForTime());
	}
}
