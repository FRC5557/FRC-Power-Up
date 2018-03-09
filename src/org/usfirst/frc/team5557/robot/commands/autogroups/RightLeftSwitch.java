package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.ExpellCommand;
import org.usfirst.frc.team5557.robot.commands.RaiseArmCommand;
import org.usfirst.frc.team5557.robot.commands.ShootBoxCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class RightLeftSwitch extends CommandGroup {
	
	public RightLeftSwitch( ) {
		// turns to the switch
		addSequential(new TurnForAngleCommand(-90));
		
		// goes to the center of the switch
		addSequential(new DriveForDistanceCommand(600));
		// turns to the switch
		addSequential(new TurnForAngleCommand(90));
		
		
		// goes to the center of the switch
		addSequential(new DriveForDistanceCommand(550));
		addSequential(new TurnForAngleCommand(-90));
		// raises arm 
		
		addSequential(new RaiseArmCommand(2500));
		// Drives so chasis is touching switch
		addSequential(new DriveForDistanceCommand(10));
		// Shoots the cube
		addSequential(new ShootBoxCommand());
	}

}
