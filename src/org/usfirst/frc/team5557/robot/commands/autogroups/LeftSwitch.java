package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;
import org.usfirst.frc.team5557.robot.commands.ShootBoxCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class LeftSwitch extends CommandGroup {
	
	
	public LeftSwitch( ) {
		// goes to the center of the switch
		addSequential(new DriveForDistanceCommand(500));
		// turns to the switch
		addSequential(new TurnForAngleCommand(-90));
		// gets closer to the switch
		addSequential(new DriveForDistanceCommand(50));
		// raises arm 
		addSequential(new RaiseArmCommand(2500));
		// Drives so chasis is touching switch
		addSequential(new DriveForDistanceCommand(10));
		// Shoots the cube
		addSequential(new ShootBoxCommand());
	}

	
}
