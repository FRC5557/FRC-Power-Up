package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.ExpellCommand;
import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;
import org.usfirst.frc.team5557.robot.commands.ShootBoxCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class MiddleRightSwitch extends CommandGroup {
	
	public MiddleRightSwitch( ) {
		addSequential(new TurnForAngleCommand(90));     
		addSequential(new DriveForDistanceCommand(300));
		addSequential(new TurnForAngleCommand(-90));
		addSequential(new DriveForDistanceCommand(550));
		
		addSequential(new RaiseArmCommand(2500));
		// Drives so chasis is touching switch
		addSequential(new DriveForDistanceCommand(10));
		// Shoots the cube
		addSequential(new ShootBoxCommand());
	}

}
