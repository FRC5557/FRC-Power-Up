package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.ExtendWristForTime;
import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchOnSameSide extends CommandGroup {
	public SwitchOnSameSide(){
		System.out.println("Stating Right auton");
		addSequential(new DriveForDistanceCommand(440));// Drive forward for 15 ft
		addSequential(new TurnForAngleCommand(-84));
		//addSequential(new TurnForAngleCommand(-180));
		addParallel(new DriveForDistanceCommand(35));
		addParallel(new RaiseArmCommand(1250));
		addSequential(new ExtendWristForTime());
	}
}
